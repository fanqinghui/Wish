package com.foundation.dao.MyBatisRepository.write.sys;

import com.foundation.dao.MyBatisRepository.MybatisBaseDao;
import com.foundation.dao.entry.sys.SysUser;
import com.foundation.common.annocation.orm.MyBatisRepository;

/**
 * <p>Created by: fqh
 * <p>Date: 15-12-8 下午4:26
 * <p>Version: 1.0
 */
@MyBatisRepository
public interface SysUserDao extends MybatisBaseDao<Long, SysUser> {
}

