package com.foundation.dao.MyBatisRepository.read.app;

import com.foundation.common.annocation.orm.MyBatisRepository;
import com.foundation.dao.MyBatisRepository.MybatisBaseDao;
import com.foundation.dao.entry.app.AppVersion;

/**
 * Created by fqh on 2015/12/8.
 */
@MyBatisRepository
public interface AppVersionDaoR extends MybatisBaseDao<Long, AppVersion> {
}
