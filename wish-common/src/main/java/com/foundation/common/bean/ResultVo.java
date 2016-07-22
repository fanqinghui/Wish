package com.foundation.common.bean;

import org.apache.commons.lang.StringUtils;

/**
 * api返回数据信息Vo
 * Created by fqh on 2015/12/7.
 */
public class ResultVo extends  BaseVo{

    private String tip;//提示信息
    private Object data;//数据信息

    public ResultVo(String tip, Object data) {
        this.tip = tip;
        this.data = data;
    }

    public ResultVo() {
    }

    public String getTip() {
        if(StringUtils.isBlank(tip)){
            return "success";
        }
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
