package com.foundation.common;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by fqh on 2015/12/11.
 */
public class BussessUtils {


    public static String getReqTokenName(HttpServletRequest request){
        String loginName=request.getParameter("loginName");
        String driverType=request.getParameter("driverType");
        String version=request.getParameter("version");
        String driverName=request.getParameter("driverName");

        loginName="18612000126";
        driverType="1";
        version="1.0";
        driverName="M-UI";

        StringBuffer valueMd5Pre=new StringBuffer().append(loginName).append("-"+driverType).append("-"+version).append("-" + driverName);
        return valueMd5Pre.toString();
    }



}
