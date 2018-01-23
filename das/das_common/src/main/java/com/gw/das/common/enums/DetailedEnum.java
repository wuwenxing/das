package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：是否详细
 * 
 * @author darren
 *
 */
public enum DetailedEnum {
	IS_DETAILED("IS_DETAILED", "1"),
	NOT_DETAILED("NOT_DETAILED", "0");

	private final String labelKey;
	private final String value;
	DetailedEnum(String labelKey,String _operator) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<DetailedEnum> getList(){
		List<DetailedEnum> result = new ArrayList<DetailedEnum>();
		for(DetailedEnum ae : DetailedEnum.values()){
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
