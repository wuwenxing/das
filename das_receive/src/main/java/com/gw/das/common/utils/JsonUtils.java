package com.gw.das.common.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Json解析
 * @author kirin.guan
 *
 */
public class JsonUtils {
	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	public static DecimalFormat DF_UNIT = new DecimalFormat("0.##");
	public static DecimalFormat DF_POINT = new DecimalFormat("0.00");
	
	public static void main(String[] args) {
//		System.out.println(j2o("{'ss':153345.000}", Map.class).get("ss").getClass());
//		System.out.println((Long)j2o("{'ss':1215212521212}", Map.class).get("ss"));
//		System.out.println(j2o("{'ss':1215212521212}", Map.class).get("ss").getClass());
//		System.out.println(j2o("{'ss':2222}", Map.class).get("ss").getClass());
//		System.out.println(j2o("{'ss':345.55}", Map.class).get("ss").getClass());
//		System.out.println(Integer.MAX_VALUE);
		
		System.out.println(j2o("{\"iphone\":\"86--13652395323\",\"email\":\"sping.peng@gwtsz.net\",\"accountNo\":\"90283358\"}",Map.class).get("email"));
	}

	public static Object formatUnit(double d){
		if(d==0){
			return "--";
		}else if(d > 100000000){
			return DF_UNIT.format(d/100000000)+"亿";
		}else if(d > 10000){
			return DF_UNIT.format(d/10000)+"万";
		}else{
			return DF_UNIT.format(d);
		}
	}
	
	public static Object formatPoint(double d){
		if(d==0){
			return "--";
		}
		return DF_POINT.format(d);
	}

	/**
	 * json字符串转java对象(jackson方式)
	 */
	public static <T> T j2o(String jsonStr, Class<T> c){
	    ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//解析单引号支持
		try {
			return mapper.readValue(jsonStr, c);
		} catch (Exception e) {
			logger.error(jsonStr, e);
		}
		return null;
	}
	
	/**
	 * json字符串转java对象(jackson方式)
	 */
	public static <T> T j2o(String jsonStr, TypeReference<T> type){
	    ObjectMapper mapper = new ObjectMapper();
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//解析单引号支持
		try {
			return mapper.readValue(jsonStr, type);
		} catch (Exception e) {
			logger.error(jsonStr, e);
		}
		return null;
	}
	
	/**
	 * java对象转json字符串(jackson方式)
	 */
	public static String o2j(Object o){
		try {
			return new ObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			logger.error(o.toString(), e);
		}
		return null;
	}
	
	/**
	 * Map转java对象(jackson方式)
	 */
	public static <T> T m2o(Map<?, ?> o, Class<T> c){
		try {
			return j2o(o2j(o), c);
		} catch (Exception e) {
			logger.error(o.toString(), e);
		}
		return null;
	}
	
	/***
	 * 将对象转换为HashMap
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, Object> toHashMap(Object object) {
		Map<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = toJSONObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}

		return data;
	}
	
	/***
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 * @return
	 */
	public static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}
	
	// 返回非实体类型(Map<String,Object>)的List
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put((String) key, value);
			}
			list.add(map);
		}
		return list;	
	}
}