package com.foundation.interceptor;

import com.foundation.common.Contants;
import com.foundation.cache.redis.JedisTemplate;
import com.foundation.common.cache.RedisUtils;
import com.foundation.common.date.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * APP必须登陆方法拦截
 * Created by fqh on 2015/12/10.
 */
public class LoginInterceptor implements HandlerInterceptor {

    private JedisTemplate template= RedisUtils.getTemplate();
    Logger logger= LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        logger.info(request.getRequestURI()+"===================>调用开始"+ DateUtils.getNowTime(DateUtils.DATE_LONG_STR));
        logger.info("===================>preHandle");
        String loginName=request.getParameter("loginName");
        String driverType=request.getParameter("driverType");
        String driverName=request.getParameter("driverName");
        String version=request.getParameter("version");
        String uuid=request.getParameter("uuid");
        String appToken=request.getParameter("appToken");
        if(StringUtils.isBlank(loginName)||StringUtils.isBlank(driverType)||StringUtils.isBlank(driverName)
                ||StringUtils.isBlank(version)||StringUtils.isBlank(uuid)||StringUtils.isBlank(appToken)){
            write(response,Contants.MISMATCHINGPARAM);//参数不齐整
            return false;
        }else{
            logger.info("request==========>" + appToken);
            String cacheAppToken=template.get(loginName);
            logger.info("cache get==========>"+cacheAppToken);
            //比对token
            if(!StringUtils.equals(appToken,cacheAppToken)){
                write(response,Contants.NEEDLOGIN);//需要登录
                return false;
            }
            /*logger.info("token匹配=================>ture");
            AcceptVo baseVo=new AcceptVo();
            baseVo.setVersion(version);
            baseVo.setLoginName(loginName);
            baseVo.setDriverName(driverName);
            baseVo.setDriverType(driverType);
            request.setAttribute("AcceptVo",baseVo);*/
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("==afterCompletion==="+o);
      /*  logger("==afterCompletion==="+o);
        logger("==afterCompletion==="+modelAndView);
        logger.info(httpServletResponse.toString());*/

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        logger.info(httpServletRequest.getRequestURI()+"===================>调用结束"+ DateUtils.getNowTime(DateUtils.DATE_LONG_STR));
        logger.info("==afterCompletion===");
        if (e != null) {//记录错误日志
            logger.info("============有异常信息================"+handler+"==="+e.getMessage());
            logger.error(handler+"");
            logger.error(e.getMessage(),e);
        }
    }


    private void write(HttpServletResponse response,String content) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(content);//参数不齐整
    }
}
