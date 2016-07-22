package com.foundation.biz.app;

import com.foundation.biz.vo.InitPo;

import java.util.Map;

/**
 * Created by fanqinghui on 2016/7/21.
 */
public interface IAppBiz {

    /**
     * 初始化版本信息
     * @param requestParamsMaps
     * @return
     */
    InitPo initVersion(String type,String version) throws Exception;

}
