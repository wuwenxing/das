package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典显示标示showFlag-子类型定义
 * @author wayne
 */
public enum ShowFlagEnum implements EnumIntf {
	
	show("显示", "Y"),
	hide("不显示", "N");
	
	private final String value;
	private final String labelKey;
	ShowFlagEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ShowFlagEnum> getList(){
		List<ShowFlagEnum> result = new ArrayList<ShowFlagEnum>();
		for(ShowFlagEnum ae : ShowFlagEnum.values()){
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
