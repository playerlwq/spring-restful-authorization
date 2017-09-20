package com.scienjus.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.scienjus.authorization.model.TokenModel;

public class JSONUtils {

	  /* ----------- tool methods --------- */
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.SortField);
    }
 
    public static <T> T parseJson(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }
 
    public static List<String> toJsonList(Collection<?> values) {
        if (values == null) {
            return null;
        }
 
        List<String> result = new ArrayList<String>();
        for (Object obj : values) {
            result.add(toJson(obj));
        }
        return result;
    }
     
    public static <T> List<T> parseJsonList(List<String> list, Class<T> clazz) {
        if (list == null) {
            return null;
        }
 
        List<T> result = new ArrayList<T>();
        for (String s : list) {
            result.add(parseJson(s, clazz));
        }
        return result;
    }
    /**
     * 
     * @param json
     * @param clazz
     * @return 
     * @return Object
     */
    public static  <T> Object   parseJson2Clazz(String json, Class<T> clazz ) {
    	T parseObject = JSONObject.parseObject(json, clazz);
        return parseObject;
    }
    
}
