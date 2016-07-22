package com.foundation.cache.redis.pool;

import redis.clients.jedis.Protocol;

/**
 * redis ������Ϣ����
 *  Created by fqh on 2015/12/13
 */
public class ConnectionInfo {

	public static final String DEFAULT_PASSWORD = null;

	private int database = Protocol.DEFAULT_DATABASE;//����Ĭ�ϵ�jredis�����ݿ�0
	private String password = DEFAULT_PASSWORD;
	private int timeout = Protocol.DEFAULT_TIMEOUT;//jredisĬ�ϳ�ʱʱ��Ϊ2000����

	public ConnectionInfo() {
	}

	public ConnectionInfo(int database, String password, int timeout) {
		this.timeout = timeout;
		this.password = password;
		this.database = database;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	public String toString() {
		return "ConnectionInfo [database=" + database + ", password=" + password + ", timeout=" + timeout + "]";
	}
}
