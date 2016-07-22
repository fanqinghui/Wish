package com.foundation.common.cache;

import com.foundation.cache.redis.JedisTemplate;
import com.foundation.cache.redis.JedisUtils;
import com.foundation.cache.redis.pool.JedisPool;
import com.foundation.cache.redis.pool.JedisPoolBuilder;
import com.foundation.common.io.PropertiesUtils;

/**
 * 简单的redis缓存模板单利工具类
 * Created by fqh on 2015/12/12
 */
public class RedisUtils {

    private RedisUtils(){}
    private static JedisTemplate template;

    static {
        try {
            String redisUrl =PropertiesUtils.getValue("redis.url");
            JedisPool pool = new JedisPoolBuilder().setUrl(redisUrl).buildPool();
            pool.getConnectionInfo().toString();
            if (!JedisUtils.ping(pool)) {
                throw new RuntimeException("请检查reids连接。ping不通redisredis");
            }
            template = new JedisTemplate(pool);
        }catch (Exception e){
            throw new RuntimeException("请检查reids配置文件");
        }
    }

    public static JedisTemplate getTemplate(){
        return template;
    }
}
