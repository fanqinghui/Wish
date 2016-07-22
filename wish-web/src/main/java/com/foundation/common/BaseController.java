package com.foundation.common;

import com.google.common.collect.Maps;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * controller基类
 * Created by fqh on 15-12-7.
 */
public class BaseController {

    /** 当前请求 */
    private HttpServletRequest request;
    /** 当前响应 */
    private HttpServletResponse response;


    /**
     * 根据request请求，把传递的参数，转换成Map
     * @param request
     * @return
     */
    public Map<String,Object> getReqParamsMaps(HttpServletRequest request){
        Map resultMap= Maps.newConcurrentMap();
        Map parasMap=request.getParameterMap();
        Set keySet=parasMap.keySet();
        Iterator it=keySet.iterator();
        while (it.hasNext()){
            Object key=it.next();
            resultMap.put(key,request.getParameter(key.toString()));
        }
        return resultMap;
    }


    /**
     * @Description: <p> 从当前请求中获得参数值(单个)，封装框架中的beat.getRequest().getParameter(param) </p> 如果为空则返回空串
     * @param param
     * @return String
     * @throws
     */
    protected String getReqValByParam(String param) {
        String value = getRequest().getParameter(param);
        return (value == null) ? "" : value.trim();
    }



    /**
     * @Description:获取当前线程中的请求request
     * @return HttpServletRequest
     * @throws
     */
    public HttpServletRequest getRequest() {
        request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * @Description: 获取当前线程中的请求response
     * @return HttpServletResponse
     * @throws
     */
    public HttpServletResponse getResponse() {
        ServletWebRequest servletWebRequest = new ServletWebRequest(getRequest());
        servletWebRequest.getResponse();
        return response;
    }

    /**
     * @Description: 获取上下文路径
     * @return String
     * @throws
     */
    public String getCtxPath() {
        String ctxPath = getRequest().getContextPath();
        return ctxPath;
    }


}
