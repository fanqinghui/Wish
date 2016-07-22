package com.foundation.common.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据库basePO
 * Created by joey on 15-1-7.
 */
public class BasePo implements Serializable {

    private Long id;
    private Integer disabled;
    private Long createDate;
    private Long updateDate;
    private String createUserName;
    private String updateUserName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    @Override
    public String toString() {
        return "BasePo{" +
                "id=" + id +
                ", disabled=" + disabled +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createUserName='" + createUserName + '\'' +
                ", updateUserName='" + updateUserName + '\'' +
                '}';
    }
}
