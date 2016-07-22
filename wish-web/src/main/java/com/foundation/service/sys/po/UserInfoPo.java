package com.foundation.service.sys.po;

import com.foundation.common.bean.BaseVo;

/**
 * Created by fqh on 2015/12/9.
 */
public class UserInfoPo extends BaseVo {

    private Integer uid;//用户id
    private String userName;//用户名
    private String address;//盟商区域
    private String photo;//	用户头像路径

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}
