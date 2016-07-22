package com.foundation.biz.app.impl;

import com.foundation.biz.app.IAppBiz;
import com.foundation.biz.vo.InitPo;
import com.foundation.dao.entry.app.AppVersion;
import com.foundation.service.app.IAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by fanqinghui on 2016/7/21.
 */
@Service
public class AppBizImpl implements IAppBiz {

    @Autowired
    IAppVersionService appService;

    @Override
    public InitPo initVersion(String type,String version) throws Exception{
        InitPo vo=new InitPo();
        //step1 读取展屏幕广告表
        vo.setAdImg("");
        vo.setAdTime(0);
        vo.setAdUrl("");
        vo.setAdStatus(0);//不展现广告
        //
        AppVersion appVersion =appService.getAppVersion(type, version);
        if(appVersion!=null){
            vo.setForceUpdate(appVersion.getForceUpdate());
            vo.setType(appVersion.getType());
            vo.setVersion(appVersion.getVersion());
            vo.setUrl(appVersion.getUrl());
        }
        return vo;
    }
}
