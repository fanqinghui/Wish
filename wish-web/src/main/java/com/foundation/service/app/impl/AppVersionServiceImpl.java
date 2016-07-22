package com.foundation.service.app.impl;

import com.foundation.dao.MyBatisRepository.read.app.AppVersionDaoR;
import com.foundation.dao.MyBatisRepository.write.app.AppVersionDao;
import com.foundation.dao.entry.app.AppVersion;
import com.foundation.service.app.IAppVersionService;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by fqh on 15-12-17.
 */
@Service
@CacheConfig(cacheNames = {"appVersion"})
public class AppVersionServiceImpl implements IAppVersionService {

    Logger logger= LoggerFactory.getLogger(AppVersionServiceImpl.class);

    @Autowired(required = false)
    AppVersionDao appVersionDao;
    @Autowired(required = false)
    AppVersionDaoR appVersionDaoR;


    @Cacheable(key = "'type_'+#type+'_version_'+#version")
    @Override
    public AppVersion getAppVersion(String type, String version) throws Exception {
        final Map<String,String> params= ImmutableMap.of("type",type,"version",version);
        logger.info("从数据库里查找" + params.toString());
        System.out.println("从数据库里查找" + params.toString());
      return appVersionDaoR.queryObject(params);
    }

    @Cacheable(key = "'id_'+#id")
    @Override
    public AppVersion getAppVersion(Long id) throws Exception {
        final ImmutableMap<String, Long> params= ImmutableMap.of("id", id);
        logger.info("从数据库里查找" + params.toString());
        System.out.println("从数据库里查找" + params.toString());
        return appVersionDaoR.queryObject(params);
    }

    @CachePut(key = "'type_'+#version.type+'_version_'+#version.version")
    @Override
    public AppVersion save(AppVersion version) throws Exception {
         Long id=appVersionDao.save(version);
         logger.info("save end id is:"+id);
        version.setId(id);
        return version;
    }
}
