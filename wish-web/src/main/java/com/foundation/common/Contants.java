package com.foundation.common;

/**
 * 系统常量类
 * Created by fqh on 2015/12/11.
 */
public interface Contants {

    //RESULT TIP
    String NEEDLOGIN="{\"data\":{},\"tip\":\"NOTLOGGEDIN\"}";
    String MISMATCHINGPARAM="{\"data\":{},\"tip\":\"参数不齐整\"}";
    String UNIFYTHROWSERROR="{\"data\":{},\"tip\":\"程序出现异常\"}";
    String PARAMMISTIP="参数不齐整";
    String UNUPLOADWZXX="未获取到当前位置";
    String USERLOGINERROR="用户名或密码错误，请重新输入";
    String USERLOGINERROR_AUTHFAIL="用户名未激活，请联系客服";
    String PASSWORDLENTHERROR="密码不能少于6位";
    String LOGGOUTERROR ="注销失败";
    String FINDPASSWORDTIP ="找回密码，请联系客服" ;
    String MODIFYPASSWORDERROR ="修改密码失败";
    String GETUSERINFOERROR ="获取用户信息错误" ;
    String SETUSERHEARDERROR ="设置头像出错";
    String UPLOADUSERHEARDERROR ="上传头像出错";
}
