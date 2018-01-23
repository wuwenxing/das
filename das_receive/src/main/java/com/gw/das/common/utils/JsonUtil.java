package com.gw.das.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * 
 * <p>Title: Json工具类</p>
 * <p>Description: </p>
 * <p>Copyright:Copyright(c)2015</p>
 * <p>Company:Gateway Technology</p>
 * @date 2015-4-14
 * @author hugo
 * @version 1.0
 */
public final class JsonUtil {
    
    /**
     * 日志对象
     */
	private static final Logger LOGGER = Logger.getLogger(JsonUtil.class);
    
    /**
     * 转换器
     */
    private static final ObjectMapper MAPPER;
    
    /** 单例对象 */
    private static final JsonUtil INSTANCE = new JsonUtil();
    
    /**
     * 构造函数
     */
    private JsonUtil() {
    }
    
    static {
        MAPPER = new ObjectMapper();
        // 允许没有引号的字段名
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号字段名
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 自动给字段名加上引号
        MAPPER.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        // 时间以时间戳格式写
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置时间转换所使用的默认时区
        MAPPER.setTimeZone(TimeZone.getDefault());
        // 设置时间序列化及反序列化的格式，即被序列化时时间将变成设置的字字符串的格式
        // ，反序列化时，只识别指定格式的字符串反序列化为日期和时间
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;//允许出现特殊字符和转义符
    }
    
    /**
     * FIXME 方法注释信息(此标记由Eclipse自动生成,请填写注释信息删除此标记)
     * 
     * <pre>
     * 
     * </pre>
     * 
     * @return 获取 实例
     */
    public static JsonUtil getInstance() {
        return INSTANCE;
    }
    
    /**
     * json格式字符串转list
     * 
     * @param <T> 转换对象类型
     * @param data 数据
     * @param clazz 类型
     * @return json格式字符串转list
     */
    public static <T> List<T> jsonToList(String data, Class<T> clazz) {
        List<T> lstResult = new ArrayList<T>(0);
        @SuppressWarnings("unchecked")
        // 先转化为装有LinkedHashMap的List
        List<Object> lstObjs = jsonToObject(data, List.class);
        // 每个LinkedHashMap都转化为目标对象并装入结果集
        for (Iterator<Object> objIter = lstObjs.iterator(); objIter.hasNext();) {
            T objT = MAPPER.convertValue(objIter.next(), clazz);
            lstResult.add(objT);
        }
        return lstResult;
    }
    
    /**
     * lst格式字符串转json
     * 
     * @param lst 数据
     * @return json格式字符串
     * @see JsonUtil#objectToJson
     */
    public static String listToJson(List<? extends Object> lst) {
        return objectToJson(lst);
    }
    
    /**
     * JSON转对象
     * 
     * @param <T> 转换对象类型
     * @param json json字符串
     * @param t 转换对象类型
     * @return JSON转换的对象
     */
    public static <T> T jsonToObject(String json, Class<T> t) {
        try { 
            MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//解析单引号支持
            return MAPPER.readValue(json, t);
        } catch (JsonParseException e) {
            LOGGER.error("字符串：" + json + " 转化为对象" + t.getName() + " 异常", e);
            throw new RuntimeException("字符串：" + json + " 转化为对象" + t.getName() + " 异常", e);
        } catch (JsonMappingException e) {
            LOGGER.error("字符串：" + json + " 转化为对象" + t.getName() + " 异常", e);
            throw new RuntimeException("字符串：" + json + " 转化为对象" + t.getName() + " 异常", e);
        } catch (IOException e) {
            LOGGER.error("字符串：" + json + " 转化为对象" + t.getName() + " 异常", e);
            throw new RuntimeException("字符串：" + json + " 转化为对象" + t.getName() + " 异常", e);
        }
    }
    
    /**
     * JSON转对象
     * 
     * @param <T> 转换对象类型
     * @param json json字符串
     * @param t TypeReference
     * @return JSON转换的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToObject(String json, TypeReference<T> t) {
        try {
            MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//解析单引号支持
            return (T) MAPPER.readValue(json, t);
        } catch (JsonParseException e) {
            LOGGER.error("字符串：" + json + " 转化为对象" + t.getType() + " 异常", e);
            throw new RuntimeException("字符串：" + json + " 转化为对象" + t.getType() + " 异常", e);
        } catch (JsonMappingException e) {
            LOGGER.error("字符串：" + json + " 转化为对象" + t.getType() + " 异常", e);
            throw new RuntimeException("字符串：" + json + " 转化为对象" + t.getType() + " 异常", e);
        } catch (IOException e) {
            LOGGER.error("字符串：" + json + " 转化为对象" + t.getType() + " 异常", e);
            throw new RuntimeException("字符串：" + json + " 转化为对象" + t.getType() + " 异常", e);
        }
    }
    
    /**
     * 对象转JSON
     * 
     * @param t 数据对象，可以是集合
     * @return json格式字符串
     */
    public static String objectToJson(Object t) {
        try {
            return new ObjectMapper().writeValueAsString(t);
        } catch (JsonParseException e) {
            LOGGER.error(t.toString() + " 转化为对象JSON 异常", e);
            throw new RuntimeException(t.toString() + " 转化为对象JSON 异常", e);
        } catch (JsonMappingException e) {
            LOGGER.error(t.toString() + " 转化为对象JSON 异常", e);
            throw new RuntimeException(t.toString() + " 转化为对象JSON 异常", e);
        } catch (IOException e) {
            LOGGER.error(t.toString() + " 转化为对象JSON 异常", e);
            throw new RuntimeException(t.toString() + " 转化为对象JSON 异常", e);
        }
    }
   
    /**
     * 对象转JSON
     * 
     * @param t 数据对象，可以是集合
     * @param isQuote jason名是否加双引号
     * @return json格式字符串
     */
    public static String objectToJson(Object t, boolean isQuote) {
        try {
            if (isQuote) {
                MAPPER.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
            }
            return MAPPER.writeValueAsString(t);
        } catch (JsonParseException e) {
            LOGGER.error(t.toString() + " 转化为对象JSON 异常", e);
            throw new RuntimeException(t.toString() + " 转化为对象JSON 异常", e);
        } catch (JsonMappingException e) {
            LOGGER.error(t.toString() + " 转化为对象JSON 异常", e);
            throw new RuntimeException(t.toString() + " 转化为对象JSON 异常", e);
        } catch (IOException e) {
            LOGGER.error(t.toString() + " 转化为对象JSON 异常", e);
            throw new RuntimeException(t.toString() + " 转化为对象JSON 异常", e);
        }
    }
    
    /**
     * 将object转换为map
     * 
     * @param object object
     * @return map对象
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objectToMap(Object object) {
        return MAPPER.convertValue(object, Map.class);
    }
    
    /**
     * 将object转换为bean
     * 
     * @param object o
     * @param cls cls
     * @param <T> 泛型
     * @return T
     */
    public static <T> T objectToBean(Object object, Class<T> cls) {
        return MAPPER.convertValue(object, cls);
    }
}
