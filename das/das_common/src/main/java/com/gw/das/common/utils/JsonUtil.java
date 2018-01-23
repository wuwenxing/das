package com.gw.das.common.utils;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON工具类
 */
public class JsonUtil {

	/**
	 * 将JSON字符串转换成list集合
	 */
	@SuppressWarnings("rawtypes")
	public static List json2List(String jsonString, Object obj) {
		return JSON.parseArray(jsonString, obj.getClass());
	}

	/**
	 * java对象转换成json对象
	 */
	public static Object toJSON(Object obj) {
		return JSON.toJSON(obj);
	}
	
	/**
	 * java对象转换成json String字符串
	 */
	public static String toJSONString(Object obj) {
		return JSON.toJSONString(obj);
	}

	/**
	 * enum对象转换成json String字符串时，得到enum类型的序号，序号从0开始
	 */
	public static String toJSONStringByEnum(Object obj, SerializerFeature[] features) {
		SerializeWriter writer = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(writer);
        for (com.alibaba.fastjson.serializer.SerializerFeature feature : features) {
            serializer.config(feature, true);
        }
        serializer.config(SerializerFeature.WriteEnumUsingToString, false);
        serializer.write(obj);
        return writer.toString();
	}
	
}
