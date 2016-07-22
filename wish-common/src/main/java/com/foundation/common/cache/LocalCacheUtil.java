package com.foundation.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 基于guava Cache是单个应用运行时的本地缓存。它不把数据存放到文件或外部服务器
 * 如果是业务缓存，请用@RedisUtils工具类获取模版
 * Created by fanqinghui on 2016/7/20.
 */
public class LocalCacheUtil {

    static Logger logger = LoggerFactory.getLogger(LocalCacheUtil.class);

    public static Cache<String, String> cache;
    public static LoadingCache<String, String> localCache;

    static {
        localCache = CacheBuilder.newBuilder().refreshAfterWrite(10, TimeUnit.SECONDS)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        if (logger.isDebugEnabled()) {
                            logger.debug("localCache获取缓存key" + key);
                        }
                        return "";
                    }
                });
        cache=CacheBuilder.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS).build();
    }

    public static void main(String[] argus) throws Exception{
        localCache.put("fqh","test");
        System.out.println(localCache.get("fqh").toString());
        Thread.sleep(3000);
        System.out.println(localCache.get("fqh").toString());
        Thread.sleep(3000);
        System.out.println(localCache.get("fqh").toString());
        cache.put("fqh","test2");
        System.out.println(cache.get("fqh", new Callable<String>() {
            @Override
            public String call() throws Exception {
                if (logger.isDebugEnabled()) {
                    logger.debug("Cache获取缓存key为null");
                }
                return "";
            }
        }));
        Thread.sleep(Integer.MAX_VALUE);
    }

}
