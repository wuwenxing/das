package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户类型
 */
public enum UserTypeEnum implements EnumIntf {
	
	tourist("游客","1"),
	dome("模拟","2"),
	real("真实","3");
	
	private final String value;
	private final String labelKey;
	UserTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<UserTypeEnum> getList(){
		List<UserTypeEnum> result = new ArrayList<UserTypeEnum>();
		for(UserTypeEnum ae : UserTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(UserTypeEnum ae : UserTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
