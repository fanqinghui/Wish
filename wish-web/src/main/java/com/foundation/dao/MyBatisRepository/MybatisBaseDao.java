package com.foundation.dao.MyBatisRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>Created by: fqh
 * <p>Date: 15-12-14 上午10:48
 * <p>Version: 1.0
 */
@Repository
public interface MybatisBaseDao<KEY, T> {

    public T queryById(KEY id);

    public Long queryPageCount(Map params);

    public T queryObject(Map params);

    public List<T> queryPage(Map params);

    public List<T> queryList(Map params);

    public void update(T t);

    public Long save(T t);

    public void batchUpdate(List<T> list);

    public void batchSave(List<T> list);


}
