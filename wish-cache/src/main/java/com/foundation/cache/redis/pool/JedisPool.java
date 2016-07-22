package com.foundation.cache.redis.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.Pool;

/**
 * Jedis Pool base class.
 * �̳�jedis pool����װ�ɻ�����jedis����
 * Created by fqh on 2015/12/13
 */
public abstract class JedisPool extends Pool<Jedis> {

	protected String poolName;

	protected HostAndPort address;

	protected ConnectionInfo connectionInfo;

	/**
	 * ��������redis�����ã�jredisPool��maxPoolSizeΪ8��̫С��
	 * �����idle�ļ��ʱ����Ĭ�ϵ�30���Ϊ10����
	 * ������idle����Ϊ0��Ĭ�ϵ���8
	 * ��idleʱ������60��
	 * ��idle�ǿ������ӣ�
	 */
	public static JedisPoolConfig createPoolConfig(int maxPoolSize) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(maxPoolSize);
		config.setMaxIdle(maxPoolSize);//����һ��pool����ж��ٸ�״̬Ϊidle(���е�)��jedisʵ��
		config.setTimeBetweenEvictionRunsMillis(600 * 1000);//60��
		return config;
	}

	/**
	 * Initialize the internal pool with connection info and pool config.
	 */
	protected void initInternalPool(HostAndPort address, ConnectionInfo connectionInfo, JedisPoolConfig config) {
		//this.poolName = poolName;
		this.address = address;
		this.connectionInfo = connectionInfo;
		JedisFactory factory = new JedisFactory(address.getHost(), address.getPort(), connectionInfo.getTimeout(),
				connectionInfo.getPassword(), connectionInfo.getDatabase());

		internalPool = new GenericObjectPool(factory, config);
	}

	/**
	 * Return a broken jedis connection back to pool.
	 * ��дjedis��returnBrokenResource
	 */
	@Override
	public void returnBrokenResource(final Jedis resource) {
		if (resource != null) {
			returnBrokenResourceObject(resource);
		}
	}

	/**
	 * Return a available jedis connection back to pool.
	 * ��дjedis��returnResource
	 */
	@Override
	public void returnResource(final Jedis resource) {
		if (resource != null) {
			resource.resetState();
			returnResourceObject(resource);
		}
	}

	public HostAndPort getAddress() {
		return address;
	}

	public ConnectionInfo getConnectionInfo() {
		return connectionInfo;
	}
}