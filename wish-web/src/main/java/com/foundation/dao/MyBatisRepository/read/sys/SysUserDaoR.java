package com.foundation.dao.MyBatisRepository.read.sys;

import com.foundation.common.annocation.orm.MyBatisRepository;
import com.foundation.dao.MyBatisRepository.MybatisBaseDao;
import com.foundation.dao.entry.sys.SysUser;

/**
 * <p>Created by: fqh
 * <p>Date: 15-12-8 下午4:26
 * <p>Version: 1.0
 */
@MyBatisRepository
public interface SysUserDaoR extends MybatisBaseDao<Long, SysUser> {
}

