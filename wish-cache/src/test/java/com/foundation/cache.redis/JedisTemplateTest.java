package com.foundation.cache.redis;

import com.foundation.cache.redis.pool.JedisPool;
import com.lordofthejars.nosqlunit.redis.*;
import com.lordofthejars.nosqlunit.redis.EmbeddedRedis.EmbeddedRedisRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by fqh
 * jedis模版类测试
 * https://github.com/lordofthejars/nosql-unit
 */
public class JedisTemplateTest {

	private JedisTemplate jedisTemplate;
	private Logger logger= LoggerFactory.getLogger(JedisTemplateTest.class);

	//构建默认的远程redis
	@Rule
	public RedisRule redisRule=new RedisRule(RemoteRedisConfigurationBuilder.newRemoteRedisConfiguration().host("127.0.0.1").port(6379).build());

	//Lifecycle 用nosqlunit的嵌入式redis ClassRule针对所有测试，只执行一次.
	@ClassRule
	public static EmbeddedRedis embeddedRedisRule =EmbeddedRedisRuleBuilder.newEmbeddedRedisRule().build();

	@Before
	public void beforeInit() {
		//获取内嵌jedis实例-本地127.0.0.1:6379
		Jedis embeddedJedis = EmbeddedRedisInstances.getInstance().getDefaultJedis();
		//创建mock对象，参数可以是类，也可以是接口
		JedisPool jedisPool = Mockito.mock(JedisPool.class);

		/*EmbeddedRedisBuilder embeddedRedisBuilder = new EmbeddedRedisBuilder();
		Jedis jedis = embeddedRedisBuilder.createEmbeddedJedis();*/

		//设置方法的预期返回值为embeddedJedis
		Mockito.when(jedisPool.getResource()).thenReturn(embeddedJedis);
        //Mockito.when(jedisPool.getResource().pipelined()).thenReturn(embeddedJedis.pipelined()); // TODO:pipeline获取报错：暂时不能测试pipeline。。。
		jedisTemplate = new JedisTemplate(jedisPool);
	}


	@Test
	public void testRedisConfig(){
		RedisConfiguration configuration=ManagedRedisConfigurationBuilder.newManagedRedisConfiguration().build();
		assertThat(configuration.getHost()).isEqualTo("127.0.0.1");
		assertThat(configuration.getPort()).isEqualTo(6379);
	}

	@Test
	public void stringActions() {/*
		String key = "fqh";
		String notExistKey = key + "notExist";
		String value = "123";

		// get/set
		jedisTemplate.set(key, value);
		assertThat(jedisTemplate.get(key)).isEqualTo(value);
		assertThat(jedisTemplate.get(notExistKey)).isNull();

		// getAsInt/getAsLong
		jedisTemplate.set(key, value);
		assertThat(jedisTemplate.getAsInt(key)).isEqualTo(123);
		assertThat(jedisTemplate.getAsInt(notExistKey)).isNull();

		jedisTemplate.set(key, value);
		assertThat(jedisTemplate.getAsLong(key)).isEqualTo(123L);
		assertThat(jedisTemplate.getAsLong(notExistKey)).isNull();

		// setnx
		assertThat(jedisTemplate.setnx(key, value)).isFalse();
		assertThat(jedisTemplate.setnx(key + "nx", value)).isTrue();

		// incr/decr
		jedisTemplate.incr(key);
		assertThat(jedisTemplate.get(key)).isEqualTo("124");
		jedisTemplate.decr(key);
		assertThat(jedisTemplate.get(key)).isEqualTo("123");

		// del
		assertThat(jedisTemplate.del(key)).isTrue();
		assertThat(jedisTemplate.del(notExistKey)).isFalse();

		//append
		jedisTemplate.set(key, value);
		assertThat(jedisTemplate.append(key,"luck")).isEqualTo(7);

		//rename 与exists
		jedisTemplate.set(key, value);
		String result=jedisTemplate.reName(key,"newKey");
		assertThat(jedisTemplate.exists("newKey")).isEqualTo(true);

        //batchAdd--证明是ok的
		*//*Map<String,String> map=new HashMap<String,String>();
		map.put("1","1");map.put("2","2");map.put("3","4");map.put("5","6");
		//Mockito.when(jedisPool.getResource().pipelined()).thenReturn(embeddedJedis.pipelined());
		List<Object> list=jedisTemplate.batchAddStr(map);*//*

		//expire 与 ttl
		jedisTemplate.set(key,value);
		jedisTemplate.expire(key,3);//10秒过期
		try {
			Thread.sleep(1000);
			assertThat(jedisTemplate.ttl(key)).isLessThan(3);
			logger.info("剩余时间"+jedisTemplate.ttl(key));
            logger.info(jedisTemplate.get(key));
            logger.info(""+jedisTemplate.exists(key));
			Thread.sleep(1000 * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        logger.info(""+jedisTemplate.exists(key));
        assertThat(jedisTemplate.ttl(key)).isEqualTo(-1);
	*/}

	@Test
	public void hashActions() {
		String key = "test.string.key";
		String field1 = "aa";
		String field2 = "bb";
		String notExistField = field1 + "not.exist";
		String value1 = "123";
		String value2 = "456";

		// hget/hset
		jedisTemplate.hset(key, field1, value1);
		assertThat(jedisTemplate.hget(key, field1)).isEqualTo(value1);
		assertThat(jedisTemplate.hget(key, notExistField)).isNull();

		// hmget/hmset
		Map<String, String> map = new HashMap<String, String>();
		map.put(field1, value1);
		map.put(field2, value2);
		jedisTemplate.hmset(key, map);

		assertThat(jedisTemplate.hmget(key, new String[] { field1, field2 })).containsExactly(value1, value2);

		// hkeys
		assertThat(jedisTemplate.hkeys(key)).contains(field1, field2);

		// hdel
		assertThat(jedisTemplate.hdel(key, field1));
		assertThat(jedisTemplate.hget(key, field1)).isNull();
	}

	@Test
	public void listActions() {
		String key = "test.list.key";
		String value = "123";
		String value2 = "456";

		// push/pop single element
		jedisTemplate.lpush(key, value);
		assertThat(jedisTemplate.llen(key)).isEqualTo(1);
		assertThat(jedisTemplate.rpop(key)).isEqualTo(value);
		assertThat(jedisTemplate.rpop(key)).isNull();

		// push/pop two elements
		jedisTemplate.lpush(key, value);
		jedisTemplate.lpush(key, value2);
		assertThat(jedisTemplate.llen(key)).isEqualTo(2);
		assertThat(jedisTemplate.rpop(key)).isEqualTo(value);
		assertThat(jedisTemplate.rpop(key)).isEqualTo(value2);

		// remove elements
		jedisTemplate.lpush(key, value);
		jedisTemplate.lpush(key, value);
		jedisTemplate.lpush(key, value);
		assertThat(jedisTemplate.llen(key)).isEqualTo(3);
		assertThat(jedisTemplate.lremFirst(key, value)).isTrue();
		assertThat(jedisTemplate.llen(key)).isEqualTo(2);
		assertThat(jedisTemplate.lremAll(key, value)).isTrue();
		assertThat(jedisTemplate.llen(key)).isEqualTo(0);
		assertThat(jedisTemplate.lremAll(key, value)).isFalse();
	}

	@Test
	public void orderedSetActions() {
		String key = "test.orderedSet.key";
		String member = "abc";
		String member2 = "def";
		double score1 = 1;
		double score11 = 11;
		double score2 = 2;

		// zadd
		assertThat(jedisTemplate.zadd(key, score1, member)).isTrue();
		assertThat(jedisTemplate.zadd(key, score2, member2)).isTrue();

		// zcard
		assertThat(jedisTemplate.zcard(key)).isEqualTo(2);
		assertThat(jedisTemplate.zcard(key + "not.exist")).isEqualTo(0);

		// zrem
		assertThat(jedisTemplate.zrem(key, member2)).isTrue();
		assertThat(jedisTemplate.zcard(key)).isEqualTo(1);
		assertThat(jedisTemplate.zrem(key, member2 + "not.exist")).isFalse();

		// unique & zscore
		assertThat(jedisTemplate.zadd(key, score11, member)).isFalse();
		assertThat(jedisTemplate.zcard(key)).isEqualTo(1);
		assertThat(jedisTemplate.zscore(key, member)).isEqualTo(score11);
		assertThat(jedisTemplate.zscore(key, member + "not.exist")).isNull();
	}
}
