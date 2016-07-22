package com.foundation.dao.MyBatisRepository.write.app;

import com.foundation.dao.MyBatisRepository.MybatisBaseDao;
import com.foundation.dao.entry.app.AppVersion;
import com.foundation.common.annocation.orm.MyBatisRepository;

/**
 * Created by fqh on 2015/12/8.
 */
@MyBatisRepository
public interface AppVersionDao extends MybatisBaseDao<Long, AppVersion> {
}
