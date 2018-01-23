package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典对应code类型定义
 * @author wayne
 */
public enum DictCodeEnum implements EnumIntf {
	
	;
	
	private final String value;
	private final String labelKey;
	DictCodeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<DictCodeEnum> getList(){
		List<DictCodeEnum> result = new ArrayList<DictCodeEnum>();
		for(DictCodeEnum ae : DictCodeEnum.values()){
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
