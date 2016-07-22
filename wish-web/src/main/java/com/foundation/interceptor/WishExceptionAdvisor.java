package com.foundation.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常信息统一处理
 * Created by fqh on 2015/12/18.
 */
public class WishExceptionAdvisor implements HandlerExceptionResolver {
    private ModelAndView modelAndView = new ModelAndView("errorPage");
    Logger logger= LoggerFactory.getLogger(WishExceptionAdvisor.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        if (e != null) {//记录错误日志
            System.out.println("============有异常信息================"+handler+"==="+e.getMessage());
            logger.error(handler+"");
            logger.error(e.getMessage(),e);
        }
        return modelAndView;
    }
}
