package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典显示标示showFlag-子类型定义
 * @author wayne
 */
public enum EnableFlagEnum implements EnumIntf {
	enable("启用", "Y"),
	disable("禁用", "N");
	
	private final String value;
	private final String labelKey;
	EnableFlagEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<EnableFlagEnum> getList(){
		List<EnableFlagEnum> result = new ArrayList<EnableFlagEnum>();
		for(EnableFlagEnum ae : EnableFlagEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
