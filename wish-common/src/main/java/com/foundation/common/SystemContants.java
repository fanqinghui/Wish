package com.foundation.common;

/**
 * 系统的一些常量定义
 * Created by fqh on 2015/12/22.
 */
public interface SystemContants {

    String SYSTEMBASEDIR=System.getProperty("user.dir");//tomc根目录
    String SYSTEMTEMPDIR=System.getProperty("java.io.tmpdir");//tomcat的temp文件夹
    Integer MYTenderStatus_ALL =0;//我的任务-全部
    Integer MYTenderStatus_Begin =1;//我的任务-尽调开始
    Integer MYTenderStatus_End =2;//我的任务-尽调结束
    String JSON_SUCCESS_CONTENT ="操作成功";//返回json操作成功判断
    String JSON_SUCCESS_CONTENT_CODE ="200";//返回json操作成功判断
    String JSON_OWEN_REQCOMMIT_CODE ="20107";//返回json操作成功判断
    String UPLOADFROM_MY ="1";//我的任务上传
    String UPLOADFROM_LIST ="2";//尽调列表上传
}
