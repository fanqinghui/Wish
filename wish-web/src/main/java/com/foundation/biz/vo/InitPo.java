package com.foundation.biz.vo;

import com.foundation.common.bean.BaseVo;

/**
 * Created by fqh on 2015/12/9.
 */
public class InitPo extends BaseVo {

    private Integer forceUpdate;//是否强制升级
    private String version;//版本号
    private Integer type;//该版本手机类型
    private String  url;//下载地址
    private Integer adStatus;//广告状态
    private Integer adTime;//单位秒
    private String adUrl;//广告连接
    private String adImg;//广告图片


    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAdTime() {
        return adTime;
    }

    public void setAdTime(Integer adTime) {
        this.adTime = adTime;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public Integer getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(Integer adStatus) {
        this.adStatus = adStatus;
    }

    @Override
    public String toString() {
        return "InitPo{" +
                "forceUpdate=" + forceUpdate +
                ", version='" + version + '\'' +
                ", type=" + type +
                ", url='" + url + '\'' +
                ", adStatus=" + adStatus +
                ", adTime=" + adTime +
                ", adUrl='" + adUrl + '\'' +
                ", adImg='" + adImg + '\'' +
                '}';
    }
}
