package com.foundation.common.bean;

/**
 * 接受Vo
 * Created by fqh on 2015/12/9.
 *
 */
public class AcceptVo extends BaseVo {

    private String driverType;//手机系统类型1代表android 2代表IOS
    private String version;//版本号
    private String driverName;//设备名称
    private String loginName;//登陆用户名
    private String appToken;//登陆令牌

    public String getDriverType() {
        return driverType;
    }
    public void setDriverType(String driverType) {
        this.driverType = driverType;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    @Override
    public String toString() {
        return "InitVo{" +
                "driverType=" + driverType +
                ", version='" + version + '\'' +
                ", driverName='" + driverName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", appToken='" + appToken + '\'' +
                '}';
    }
}
