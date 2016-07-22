package com.foundation.dao.entry.app;

import com.foundation.common.bean.BasePo;

/**
 * Created by fqh on 2015/12/8.
 */
public class AppVersion extends BasePo {

    private Integer type;
    private String version;
    private String url;
    private Integer forceUpdate;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Integer forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "type=" + type +
                ", version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", forceUpdate=" + forceUpdate +
                "} " + super.toString();
    }
}
