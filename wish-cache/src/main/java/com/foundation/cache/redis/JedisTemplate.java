package com.foundation.cache.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.foundation.cache.redis.pool.JedisPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;

/**
 * JedisTemplate 提供了一个template方法，通过构造方法传递JedisPool来负责对Jedis连接的获取与归还，以及数据curd操作。
 * JedisAction<T> 和 JedisActionNoResult两种回调接口，适用于有无返回值两种情况。
 * PipelineAction 与 PipelineActionResult两种接口，适合于pipeline中批量传输命令的情况。
 *
 * 同时提供一些JedisOperation中定义的 最常用函数的封装, 如get/set/zadd等。几乎按照redis命令都进行了方法封装
 * 所有redis命令参考：http://doc.redisfans.com/,所有方法声明均参考此文档
 * 套用了建造者模式，根据是否返回结果等对jedis进行了内部封装
 * TODO： 另外Redis并不支持服务器端分片，不像memcached那样，不用关心具体怎么实现服务端数据分片。。。要实行这个功能必须用
 * TODO: 一致性的hashcode算法，使数据离散的分布到不通的服务器上。下一步需要优化这块
 * Created by fqh on 2015/12/13
 */
public class JedisTemplate {

	private static Logger logger = LoggerFactory.getLogger(JedisTemplate.class);

	private JedisPool jedisPool;

	public JedisTemplate(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	/**
	 * Callback interface for template.
	 */
	public interface JedisAction<T> {
		T action(Jedis jedis);
	}

	/**
	 * Callback interface for template without result.
	 */
	public interface JedisActionNoResult {
		void action(Jedis jedis);
	}

	/**
	 * Callback interface for template.
	 */
	public interface PipelineAction {
		List<Object> action(Pipeline Pipeline);
	}

	/**
	 * Callback interface for template without result.
	 */
	public interface PipelineActionNoResult {
		void action(Pipeline Pipeline);
	}

	/**
	 * Execute with a call back action with result.
	 */
	public <T> T execute(JedisAction<T> jedisAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			return jedisAction.action(jedis);
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * Execute with a call back action without result.
	 */
	public void execute(JedisActionNoResult jedisAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			jedisAction.action(jedis);
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * Execute with a call back action with result in pipeline.
	 */
	public List<Object> execute(PipelineAction pipelineAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeline = jedis.pipelined();
			pipelineAction.action(pipeline);
			return pipeline.syncAndReturnAll();
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * Execute with a call back action without result in pipeline.
	 */
	public void execute(PipelineActionNoResult pipelineAction) throws JedisException {
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisPool.getResource();
			Pipeline pipeline = jedis.pipelined();
			pipelineAction.action(pipeline);
			pipeline.sync();
		} catch (JedisException e) {
			broken = handleJedisException(e);
			throw e;
		} finally {
			closeResource(jedis, broken);
		}
	}

	/**
	 * 返回jedis连接池信息
	 */
	public JedisPool getJedisPool() {
		return jedisPool;
	}

	/**
	 *	jredis异常处理方法
	 */
	protected boolean handleJedisException(JedisException jedisException) {
		if (jedisException instanceof JedisConnectionException) {
			logger.error("Redis connection " + jedisPool.getAddress() + " lost.", jedisException);
		} else if (jedisException instanceof JedisDataException) {
			if ((jedisException.getMessage() != null) && (jedisException.getMessage().indexOf("READONLY") != -1)) {
				logger.error("Redis connection " + jedisPool.getAddress() + " are read-only slave.", jedisException);
			} else {
				// dataException, isBroken=false
				return false;
			}
		} else {
			logger.error("Jedis exception happen.", jedisException);
		}
		return true;
	}

	/**
	 * 把jedis连接返回给jedis连接池
	 */
	protected void closeResource(Jedis jedis, boolean conectionBroken) {
		try {
			if (conectionBroken) {
				jedisPool.returnBrokenResource(jedis);
			} else {
				jedisPool.returnResource(jedis);
			}
		} catch (Exception e) {
			logger.error("return back jedis failed, will fore close the jedis.", e);
			JedisUtils.destroyJedis(jedis);
		}

	}

	//========= Common Actions=================//

	/**
	 * 删除key,key可以传入多个
	 * return false if one of the key is not exist.
	 */
	public Boolean del(final String... keys) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.del(keys) == keys.length ? true : false;
			}
		});
	}

	/**
	 *清空redis（删除所有keys）
	 */
	public void flushDB() {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.flushDB();
			}
		});
	}

	// / String Actions ///

	/**
	 * 根据key获得string类型的value
	 */
	public String get(final String key) {
		return execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.get(key);
			}
		});
	}

	/**
	 * 如果确定存进去的key可以转换成Long，用此方法get
	 */
	public Long getAsLong(final String key) {
		String result = get(key);
		return result != null ? Long.valueOf(result) : null;
	}

	/**
	 *如果确定存进去的key可以转换成Integer，用此方法
	 */
	public Integer getAsInt(final String key) {
		String result = get(key);
		return result != null ? Integer.valueOf(result) : null;
	}

	/**
	 * 返回所有(一个或多个)给定 key 的值
	 * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。因此，该命令永不失败
	 */
	public List<String> mget(final String... keys) {
		return execute(new JedisAction<List<String>>() {
			@Override
			public List<String> action(Jedis jedis) {
				return jedis.mget(keys);
			}
		});
	}

	/**
	 * 将字符串值 value 关联到 key 。
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型
	 * ps:value虽然可以放入最多不超过1GB的字符串。但还是存入的越短越好
	 */
	public void set(final String key, final String value) {
		execute(new JedisActionNoResult() {

			@Override
			public void action(Jedis jedis) {
				jedis.set(key, value);
			}
		});
	}

	/**
	 * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)
	 */
	public void setex(final String key, final String value, final int seconds) {
		execute(new JedisActionNoResult() {

			@Override
			public void action(Jedis jedis) {
				jedis.setex(key, seconds, value);
			}
		});
	}

	/**
	 * 将 key 的值设为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。
	 * 只有设置成功（key不存在的情况），才会返回true
	 */
	public Boolean setnx(final String key, final String value) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.setnx(key, value) == 1 ? true : false;
			}
		});
	}

	/**
	 * 将key的值设置为value，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。若存在 将 key 的生存时间设为 seconds (以秒为单位)
	 * 该方法相当于sexex与setnx的组合命令方法
	 * {#setex(String, String, int) SETEX} + { #sexnx(String, String) SETNX}.
	 */
	public Boolean setnxex(final String key, final String value, final int seconds) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				String result = jedis.set(key, value, "NX", "EX", seconds);
				return JedisUtils.isStatusOk(result);
			}
		});
	}

	/**
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)
	 */
	public String getSet(final String key, final String value) {
		return execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.getSet(key, value);
			}
		});
	}

	/**
	 * 将 key 中储存的数字值增一。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
	 * @return的是返回自增操作以后的值。
	 */
	public Long incr(final String key) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.incr(key);
			}
		});
	}

	/**
	 * 将 key 所储存的值加上增量 increment 。（Long长整形类型增量）
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令
	 */
	public Long incrBy(final String key, final long increment) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.incrBy(key, increment);
			}
		});
	}


	/**
	 * 将 key 所储存的值加上 浮点型 增量 increment 。
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令
	 */
	public Double incrByFloat(final String key, final double increment) {
		return execute(new JedisAction<Double>() {
			@Override
			public Double action(Jedis jedis) {
				return jedis.incrByFloat(key, increment);
			}
		});
	}

	/**
	 * 将 key 中储存的数字值减一。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。
	 * @return 返回的是递减1之后的结果
	 */
	public Long decr(final String key) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.decr(key);
			}
		});
	}

	/**
	 * 将 key 所储存的值减去减量 decrement 。 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作
	 * @return 返回的是递减1之后的结果
	 */
	public Long decrBy(final String key, final long decrement) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.decrBy(key, decrement);
			}
		});
	}

	// / Hash Actions ///
	/**
	 * If key holds a hash, retrieve the value associated to the specified
	 * field.
	 * <p>
	 * If the field is not found or the key does not exist, a special 'nil' value is returned.
	 */
	public String hget(final String key, final String fieldName) {
		return execute(new JedisAction<String>() {
			@Override
			public String action(Jedis jedis) {
				return jedis.hget(key, fieldName);
			}
		});
	}

	public List<String> hmget(final String key, final String... fieldsNames) {
		return execute(new JedisAction<List<String>>() {
			@Override
			public List<String> action(Jedis jedis) {
				return jedis.hmget(key, fieldsNames);
			}
		});
	}

	public Map<String, String> hgetAll(final String key) {
		return execute(new JedisAction<Map<String, String>>() {
			@Override
			public Map<String, String> action(Jedis jedis) {
				return jedis.hgetAll(key);
			}
		});
	}

	public void hset(final String key, final String fieldName, final String value) {
		execute(new JedisActionNoResult() {

			@Override
			public void action(Jedis jedis) {
				jedis.hset(key, fieldName, value);
			}
		});
	}

	public void hmset(final String key, final Map<String, String> map) {
		execute(new JedisActionNoResult() {

			@Override
			public void action(Jedis jedis) {
				jedis.hmset(key, map);
			}
		});

	}

	public Boolean hsetnx(final String key, final String fieldName, final String value) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.hsetnx(key, fieldName, value) == 1 ? true : false;
			}
		});
	}

	public Long hincrBy(final String key, final String fieldName, final long increment) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.hincrBy(key, fieldName, increment);
			}
		});
	}

	public Double hincrByFloat(final String key, final String fieldName, final double increment) {
		return execute(new JedisAction<Double>() {
			@Override
			public Double action(Jedis jedis) {
				return jedis.hincrByFloat(key, fieldName, increment);
			}
		});
	}

	public Long hdel(final String key, final String... fieldsNames) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.hdel(key, fieldsNames);
			}
		});
	}

	public Boolean hexists(final String key, final String fieldName) {
		return execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				return jedis.hexists(key, fieldName);
			}
		});
	}

	public Set<String> hkeys(final String key) {
		return execute(new JedisAction<Set<String>>() {
			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.hkeys(key);
			}
		});
	}

	public Long hlen(final String key) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.hlen(key);
			}
		});
	}

	// / List Actions ///

	public Long lpush(final String key, final String... values) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.lpush(key, values);
			}
		});
	}

	public Long lpush(final byte[] key, final byte[] values) {
		return execute(new JedisAction<Long>() {
			@Override
			public Long action(Jedis jedis) {
				return jedis.lpush(key, values);
			}
		});
	}


	public String rpop(final String key) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.rpop(key);
			}
		});
	}

	public byte[] rpop(final byte[] key) {
		return execute(new JedisAction<byte[]>() {

			@Override
			public byte[] action(Jedis jedis) {
				return jedis.rpop(key);
			}
		});
	}

	public String brpop(final String key) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				List<String> nameValuePair = jedis.brpop(key);
				if (nameValuePair != null) {
					return nameValuePair.get(1);
				} else {
					return null;
				}
			}
		});
	}

	public String brpop(final int timeout, final String key) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				List<String> nameValuePair = jedis.brpop(timeout, key);
				if (nameValuePair != null) {
					return nameValuePair.get(1);
				} else {
					return null;
				}
			}
		});
	}

	/**
	 * Not support for sharding.
	 */
	public String rpoplpush(final String sourceKey, final String destinationKey) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.rpoplpush(sourceKey, destinationKey);
			}
		});
	}

	/**
	 * Not support for sharding.
	 */
	public String brpoplpush(final String source, final String destination, final int timeout) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.brpoplpush(source, destination, timeout);
			}
		});
	}

	public Long llen(final String key) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.llen(key);
			}
		});
	}

	public String lindex(final String key, final long index) {
		return execute(new JedisAction<String>() {

			@Override
			public String action(Jedis jedis) {
				return jedis.lindex(key, index);
			}
		});
	}

	public List<String> lrange(final String key, final int start, final int end) {
		return execute(new JedisAction<List<String>>() {

			@Override
			public List<String> action(Jedis jedis) {
				return jedis.lrange(key, start, end);
			}
		});
	}

	/**
	 * redis消息队列 pop方法
	 * @param key
	 * @param start
	 * @param end
     * @return
     */
	public List<byte[]> lrange(final byte[] key,final int start, final int end) {
		return execute(new JedisAction<List<byte[]>>() {

			@Override
			public List<byte[]> action(Jedis jedis) {
				return jedis.lrange(key,start,end);
			}
		});
	}

	/**
	 * redis消息队列 pop方法
	 * @param key
	 * @return
	 */
	public List<byte[]> lrange(final byte[] key) {
		return execute(new JedisAction<List<byte[]>>() {

			@Override
			public List<byte[]> action(Jedis jedis) {
				return jedis.lrange(key,0,-1);
			}
		});
	}



	public void ltrim(final String key, final int start, final int end) {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.ltrim(key, start, end);
			}
		});
	}

	public void ltrimFromLeft(final String key, final int size) {
		execute(new JedisActionNoResult() {
			@Override
			public void action(Jedis jedis) {
				jedis.ltrim(key, 0, size - 1);
			}
		});
	}

	public Boolean lremFirst(final String key, final String value) {
		return execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				Long count = jedis.lrem(key, 1, value);
				return (count == 1);
			}
		});
	}

	public Boolean lremAll(final String key, final String value) {
		return execute(new JedisAction<Boolean>() {
			@Override
			public Boolean action(Jedis jedis) {
				Long count = jedis.lrem(key, 0, value);
				return (count > 0);
			}
		});
	}

	// / Set Actions ///
	public Boolean sadd(final String key, final String member) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.sadd(key, member) == 1 ? true : false;
			}
		});
	}

	public Set<String> smembers(final String key) {
		return execute(new JedisAction<Set<String>>() {

			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.smembers(key);
			}
		});
	}

	//======================Ordered Set Actions ==================================//
	/**
	 * return true for add new element, false for only update the score.
	 */
	public Boolean zadd(final String key, final double score, final String member) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.zadd(key, score, member) == 1 ? true : false;
			}
		});
	}

	public Double zscore(final String key, final String member) {
		return execute(new JedisAction<Double>() {

			@Override
			public Double action(Jedis jedis) {
				return jedis.zscore(key, member);
			}
		});
	}

	public Long zrank(final String key, final String member) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.zrank(key, member);
			}
		});
	}

	public Long zrevrank(final String key, final String member) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.zrevrank(key, member);
			}
		});
	}

	public Long zcount(final String key, final double min, final double max) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.zcount(key, min, max);
			}
		});
	}

	public Set<String> zrange(final String key, final int start, final int end) {
		return execute(new JedisAction<Set<String>>() {

			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrange(key, start, end);
			}
		});
	}

	public Set<Tuple> zrangeWithScores(final String key, final int start, final int end) {
		return execute(new JedisAction<Set<Tuple>>() {

			@Override
			public Set<Tuple> action(Jedis jedis) {
				return jedis.zrangeWithScores(key, start, end);
			}
		});
	}

	public Set<String> zrevrange(final String key, final int start, final int end) {
		return execute(new JedisAction<Set<String>>() {

			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrevrange(key, start, end);
			}
		});
	}

	public Set<Tuple> zrevrangeWithScores(final String key, final int start, final int end) {
		return execute(new JedisAction<Set<Tuple>>() {

			@Override
			public Set<Tuple> action(Jedis jedis) {
				return jedis.zrevrangeWithScores(key, start, end);
			}
		});
	}

	public Set<String> zrangeByScore(final String key, final double min, final double max) {
		return execute(new JedisAction<Set<String>>() {

			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrangeByScore(key, min, max);
			}
		});
	}

	public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, final double max) {
		return execute(new JedisAction<Set<Tuple>>() {

			@Override
			public Set<Tuple> action(Jedis jedis) {
				return jedis.zrangeByScoreWithScores(key, min, max);
			}
		});
	}

	public Set<String> zrevrangeByScore(final String key, final double max, final double min) {
		return execute(new JedisAction<Set<String>>() {

			@Override
			public Set<String> action(Jedis jedis) {
				return jedis.zrevrangeByScore(key, max, min);
			}
		});
	}

	public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, final double min) {
		return execute(new JedisAction<Set<Tuple>>() {

			@Override
			public Set<Tuple> action(Jedis jedis) {
				return jedis.zrevrangeByScoreWithScores(key, max, min);
			}
		});
	}

	public Boolean zrem(final String key, final String member) {
		return execute(new JedisAction<Boolean>() {

			@Override
			public Boolean action(Jedis jedis) {
				return jedis.zrem(key, member) == 1 ? true : false;
			}
		});
	}

	public Long zremByScore(final String key, final double start, final double end) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.zremrangeByScore(key, start, end);
			}
		});
	}

	public Long zremByRank(final String key, final long start, final long end) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.zremrangeByRank(key, start, end);
			}
		});
	}

	public Long zcard(final String key) {
		return execute(new JedisAction<Long>() {

			@Override
			public Long action(Jedis jedis) {
				return jedis.zcard(key);
			}
		});
	}
}
