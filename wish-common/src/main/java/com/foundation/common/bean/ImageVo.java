package com.foundation.common.bean;

/**
 *  上传头像Po返回
 * Created by fqh on 2015/12/22.
 */
public class ImageVo extends BaseVo {
    private String photoResult;
    private String returnResult;
    private String returnSign;
    private String tip;


    public String getPhotoResult() {
        return photoResult;
    }

    public void setPhotoResult(String photoResult) {
        this.photoResult = photoResult;
    }

    public String getReturnSign() {
        return returnSign;
    }

    public void setReturnSign(String returnSign) {
        this.returnSign = returnSign;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = returnResult;
    }

    @Override
    public String toString() {
        return "ImageVo{" +
                "photoResult='" + photoResult + '\'' +
                ", returnResult='" + returnResult + '\'' +
                ", returnSign='" + returnSign + '\'' +
                ", tip='" + tip + '\'' +
                '}';
    }
}