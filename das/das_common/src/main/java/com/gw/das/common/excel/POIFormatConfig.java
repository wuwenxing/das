package com.gw.das.common.excel;

public interface POIFormatConfig<T> {
	/**
	 * 格式值
	 * @param fieldName 字段名
	 * @param fieldValue 字段值
	 * @return 格式后的值
	 */
   Object fromatValue(String fieldName, Object fieldValue, T param);
}
