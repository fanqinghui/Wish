package com.foundation.service.app;

import com.foundation.dao.entry.app.AppVersion;

import java.util.Map;

/**
 * Created by fqh on 2015/12/11.
 */
public interface IAppVersionService {


    /**
     * 根据type与version读取版本信息
     * @param type
     * @param version
     * @return
     */
    AppVersion getAppVersion(String type, String version) throws Exception;

    /**
     * 根据id读取版本信息
     * @param type
     * @param version
     * @return
     */
    AppVersion getAppVersion(Long id) throws Exception;

    /**
     * 保存版本
     * @param version
     * @return
     * @throws Exception
     */
    AppVersion save(AppVersion version) throws Exception;

}
