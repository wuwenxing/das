package com.gw.das.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import javax.persistence.Column;

/**
 * 反射工具类
 * 
 * @author darren.qiu
 *
 */
public class ReflectionUtil {
	/**
	 * 根据字段属性获取数据表字段
	 * 
	 * @param field
	 * @param T
	 * @return
	 */
	public static String getColumnName(String field, Class T) {
		Field[] fields = T.getDeclaredFields();
		for (Field f : fields) {
			String filedName = f.getName();
			if (filedName.equals(field)) {
				Annotation annotation = f.getAnnotation(Column.class);
				if (annotation != null) {
					Column column = (Column) annotation;
					return column.name();
				}
			}
		}
		return null;
	}
}
