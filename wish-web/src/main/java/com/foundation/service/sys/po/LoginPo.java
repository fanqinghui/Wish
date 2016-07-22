package com.foundation.service.sys.po;

import com.foundation.common.bean.BaseVo;

/**
 * Created by fqh on 2015/12/9.
 */
public class LoginPo extends BaseVo {

    private String uuid;//用户uid
    private String realname;//用户名
    private String appToken;//登陆令牌
    private String headImg;//头像url

    private Integer provinceId;//省id
    private String provinceName;//省名称
    private Integer cityId;//用户名
    private String cityName;//城市名
    private Integer countyId;//县id
    private String countyName;//县名
    private Integer allyId;//盟商id
    private String address;//盟商地址

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getCountyId() {
        return countyId;
    }

    public void setCountyId(Integer countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        StringBuffer sb = new StringBuffer();
        if (!isZXS()) {
            sb.append(this.getProvinceName()).append("");
        }
        sb.append(this.getCityName()).append(this.countyName);

        return sb.toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAllyId() {
        return allyId;
    }

    public void setAllyId(Integer allyId) {
        this.allyId = allyId;
    }

    private boolean isZXS() {
        if (provinceName.contains("北京") || provinceName.contains("天津") || provinceName.contains("上海") || provinceName.contains("重庆")) {//北京市（京）、天津市（津）、上海市（沪）、重庆市
            return true;
        }
        return false;
    }


    @Override
    public String toString() {
        return "LoginPo{" +
                "uuid='" + uuid + '\'' +
                ", realname='" + realname + '\'' +
                ", appToken='" + appToken + '\'' +
                ", headImg='" + headImg + '\'' +
                ", provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", countyId=" + countyId +
                ", countyName='" + countyName + '\'' +
                ", allyId=" + allyId +
                ", address='" + address + '\'' +
                '}';
    }
}
