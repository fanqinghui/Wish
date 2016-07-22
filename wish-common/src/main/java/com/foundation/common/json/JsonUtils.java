package com.foundation.common.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.foundation.common.bean.BasePo;

import java.util.Map;

/**
 * <p>Created by: qingHui
 * <p>Date: 15-1-9 下午3:33
 * <p>Version: 1.0
 */
public class JsonUtils{

    /**
     * po类转换成json String
     * @param po
     * @return
     */
    public static String formateObject(BasePo po) {
        //String result = JSON.toJSONString(po);
        String result=JSON.toJSONString(po, SerializerFeature.WriteMapNullValue);
        return result;
    }

    /**
     * 把json数据转换成类
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
      return JSON.parseObject(jsonString,clazz);
    }

    /**
     * po类转换成json String
     * @param po
     * @return
     */
    public static String formateMap(Map po) {
        String result=JSON.toJSONString(po,SerializerFeature.WriteMapNullValue);
        System.out.println(result);
        return result;
    }
}
