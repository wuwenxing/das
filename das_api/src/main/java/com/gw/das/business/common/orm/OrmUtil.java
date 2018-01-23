package com.gw.das.business.common.orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gw.das.business.common.utils.NumberUtil;
import com.gw.das.business.dao.base.HBaseCommonDao;
import com.gw.das.business.dao.website.entity.DasFlowDetail;

public class OrmUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(OrmUtil.class);
	
	/**
	 * 将jdbcTemplate查询的map结果集 反射生成对应的bean
	 * 
	 * @param clazz
	 *            意向反射的实体.clazz
	 * @param jdbcMapResult
	 *            查询结果集 key is UpperCase
	 * @return
	 * @see
	 */
	public static <T> List<T> reflectList(Class<T> clazz, List<Map<String, Object>> jdbcMapResultList){
		try{
			// 获得
			Field[] fields = clazz.getDeclaredFields();
			
			// 存放field和column对应关系，该关系来自于实体类的 @Column配置
			Map<String/* field name in modelBean */, String/* column in db */> fieldHasColumnAnnoMap = new LinkedHashMap<String, String>();
			Annotation[] annotations = null;
			for (Field field : fields) {
				annotations = field.getAnnotations();
				for (Annotation an : annotations) {
					if (an instanceof Column) {
						Column column = (Column) an;
						fieldHasColumnAnnoMap.put(field.getName(), column.name());
					}
				}
			}
			
			List<T> list = new ArrayList<T>();
			for(Map<String, Object> jdbcMapResult: jdbcMapResultList){
				// 存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
				Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
				for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet()) {
					// 将column小写
					String key = en.getValue().toLowerCase();
	
					// 获得map的该field的属性值
					Object value = jdbcMapResult.get(key);
	
					// 确保value有效性，防止JSON reflect时异常
					if (value != null) {
						if (value instanceof Double){
							// 四舍五入，保留两位有效数字
							value = NumberUtil.m2((Double)value);
						}
						conCurrent.put(en.getKey(), value);
					}
					
				}
				// fastjson reflect to modelbean
				T t = JSON.parseObject(JSON.toJSONString(conCurrent), clazz);
				list.add(t);
			}
			return list;
		}catch(Exception e){
			logger.error("转换错误：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 将jdbcTemplate查询的map结果集 反射生成对应的bean
	 * 
	 * @param clazz
	 *            意向反射的实体.clazz
	 * @param jdbcMapResult
	 *            查询结果集 key is UpperCase
	 * @return
	 * @see
	 */
	public static <T> T reflect(Class<T> clazz, Map<String, Object> jdbcMapResult) {
		try{
			// 获得
			Field[] fields = clazz.getDeclaredFields();
	
			// 存放field和column对应关系，该关系来自于实体类的 @Column配置
			Map<String/* field name in modelBean */, String/* column in db */> fieldHasColumnAnnoMap = new LinkedHashMap<String, String>();
			Annotation[] annotations = null;
			for (Field field : fields) {
				annotations = field.getAnnotations();
				for (Annotation an : annotations) {
					if (an instanceof Column) {
						Column column = (Column) an;
						fieldHasColumnAnnoMap.put(field.getName(), column.name());
					}
				}
			}
			// 存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
			Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
			for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet()) {
				// 将column小写
				String key = en.getValue().toLowerCase();
	
				// 获得map的该field的属性值
				Object value = jdbcMapResult.get(key);

				// 确保value有效性，防止JSON reflect时异常
				if (value != null) {
					if (value instanceof Double){
						// 四舍五入，保留两位有效数字
						value = NumberUtil.m2((Double)value);
					}
					conCurrent.put(en.getKey(), value);
				}
			}
			// fastjson reflect to modelbean
			return JSON.parseObject(JSON.toJSONString(conCurrent), clazz);
		}catch(Exception e){
			logger.error("转换错误：" + e.getMessage());
		}
		return null;
	}

	/**
	 * 将实体的排序字段转化为数据的排序字段
	 */
	public static <T> String reflectColumn(Class<T> clazz, String sort) {
		try{
			// 获得
			Field[] fields = clazz.getDeclaredFields();
	
			// 存放field和column对应关系，该关系来自于实体类的 @Column配置
			Map<String/* field name in modelBean */, String/* column in db */> fieldHasColumnAnnoMap = new LinkedHashMap<String, String>();
			Annotation[] annotations = null;
			for (Field field : fields) {
				annotations = field.getAnnotations();
				for (Annotation an : annotations) {
					if (an instanceof Column) {
						Column column = (Column) an;
						fieldHasColumnAnnoMap.put(field.getName(), column.name());
					}
				}
			}
			
			String value = fieldHasColumnAnnoMap.get(sort);
			if(StringUtils.isNotBlank(value)){
				return value;
			}
		}catch(Exception e){
			logger.error("转换错误：" + e.getMessage());
		}
		return sort;
	}
	
	/**
	 * 将HBase查询的Result结果集 反射生成对应的bean
	 * 
	 * @param clazz
	 *            意向反射的实体.clazz
	 * @param jdbcMapResult
	 *            查询结果集 key is UpperCase
	 * @return
	 * @see
	 */
	public static <T> List<T> reflectList(Class<T> clazz, ResultScanner rs){
		try{
			// 获得
			Field[] fields = clazz.getDeclaredFields();
	
			// 存放field和column对应关系，该关系来自于实体类的 @Column配置
			Map<String/* field name in modelBean */, String/* column in db */> fieldHasColumnAnnoMap = new LinkedHashMap<String, String>();

			// 存放需要格式化小数的digitByPlatformtypeMap
//			boolean temp = false;
			// Map<ColumnName, Map<Platformtype, digit>>
//			Map<String, Map<String, String>> digitColumnNameMap = new LinkedHashMap<String, Map<String, String>>();
			
			Annotation[] annotations = null;
			for (Field field : fields) {
				annotations = field.getAnnotations();
				for (Annotation an : annotations) {
					if (an instanceof Column) {
						Column column = (Column) an;
						fieldHasColumnAnnoMap.put(field.getName(), column.name());
//						if(StringUtils.isNotBlank(column.digitByPlatformtype())){
//							temp = true;
//							// Map<Platformtype, digit>
//							Map<String, String> digitByPlatformtypeMap = new LinkedHashMap<String, String>();
//							String digitByPlatformtype = column.digitByPlatformtype();
//							String[] ary = digitByPlatformtype.split(";");
//							for(int i=0; i<ary.length; i++){
//								String str = ary[i];
//								if(StringUtils.isNoneBlank(str)){
//									digitByPlatformtypeMap.put(str.split(",")[0], str.split(",")[1]);
//								}
//							}
//							digitColumnNameMap.put(column.name(), digitByPlatformtypeMap);
//						}
					}
				}
			}
			
			List<T> list = new ArrayList<T>();
			Result result = null;
			while ((result = rs.next()) != null) {
				// 存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
				Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
				for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet()) {
					// 将column小写
					String key = en.getValue().toLowerCase();
		
					// 获得map的该field的属性值
					Object value = HBaseCommonDao.getValueByResult(result, key);
					// 确保value有效性，防止JSON reflect时异常
					if (value != null) {
						if(!(value.equals("") || value.equals("null"))){
//							if(temp){
//								for (Map.Entry<String, Map<String, String>> nameMap : digitColumnNameMap.entrySet()) {
//									if(key.equals(nameMap.getKey())){
//										Map<String, String> digitByPlatformtypeMap = nameMap.getValue();
//										// 获取必备平台标示属性
//										String businessplatform = HBaseCommonDao.getValueByResult(result, "businessplatform") + "";
//										String digitStr = digitByPlatformtypeMap.get(businessplatform);
//										int digit = 2;// 默认保留两位有效数字
//										if(StringUtils.isNotBlank(digitStr)){
//											digit = Integer.parseInt(digitStr);
//										}
//										value = NumberUtil.NumberFormat(key, digit);
//									}
//								}
//							}
							conCurrent.put(en.getKey(), value);
						}
					}
				}
				// fastjson reflect to modelbean
				T t = JSON.parseObject(JSON.toJSONString(conCurrent), clazz);
				list.add(t);
			}
			return list;
		}catch(Exception e){
			logger.error("转换错误：" + e.getMessage());
		}
		return null;
	}
	/**
	 * 将HBase查询的Result结果集 反射生成对应的bean
	 * 
	 * @param clazz
	 *            意向反射的实体.clazz
	 * @param jdbcMapResult
	 *            查询结果集 key is UpperCase
	 * @return
	 * @see
	 */
	public static <T> T reflect(Class<T> clazz, Result result) {
		try{
			// 获得
			Field[] fields = clazz.getDeclaredFields();
	
			// 存放field和column对应关系，该关系来自于实体类的 @Column配置
			Map<String/* field name in modelBean */, String/* column in db */> fieldHasColumnAnnoMap = new LinkedHashMap<String, String>();
			Annotation[] annotations = null;
			for (Field field : fields) {
				annotations = field.getAnnotations();
				for (Annotation an : annotations) {
					if (an instanceof Column) {
						Column column = (Column) an;
						fieldHasColumnAnnoMap.put(field.getName(), column.name());
					}
				}
			}
			// 存放field name 和 对应的来自map的该field的属性值，用于后续reflect成ModelBean
			Map<String, Object> conCurrent = new LinkedHashMap<String, Object>();
			for (Map.Entry<String, String> en : fieldHasColumnAnnoMap.entrySet()) {
				// 将column小写
				String key = en.getValue().toLowerCase();
	
				// 获得map的该field的属性值
				Object value = HBaseCommonDao.getValueByResult(result, key);
				// 确保value有效性，防止JSON reflect时异常
				if (value != null) {
					if(!(value.equals("") || value.equals("null"))){
						conCurrent.put(en.getKey(), value);
					}
				}
			}
			// fastjson reflect to modelbean
			return JSON.parseObject(JSON.toJSONString(conCurrent), clazz);
		}catch(Exception e){
			logger.error("转换错误：" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * test example
	 * 
	 * @param args
	 * @throws Exception
	 * @see
	 */
	public static void main(String[] args) throws Exception {
		// call reflect testing
		Map<String, Object> jdbcMapResult = new HashMap<String, Object>();
		jdbcMapResult.put("userid", "reflect");
		jdbcMapResult.put("formattime", "reflect123456");
		jdbcMapResult.put("starttime", "2016-09-01 08:08:08");
		DasFlowDetail detail = OrmUtil.reflect(DasFlowDetail.class, jdbcMapResult);

		System.out.println(detail.getUserId());
		System.out.println(detail.getFormatTime());
		System.out.println(detail.getStartTime());
	}
}
