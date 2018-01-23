package com.gw.das.api.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum businessEnum implements EnumIntf {
	
	fx("外汇", "1"),
	pm("贵金属", "2");
	
	private final String value;
	private final String labelKey;
	businessEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<businessEnum> getList(){
		List<businessEnum> result = new ArrayList<businessEnum>();
		for(businessEnum ae : businessEnum.values()){
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

	public Long getLabelKeyLong() {
		return Long.parseLong(labelKey);
	}
}
