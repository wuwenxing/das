package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行计划类型定义 
 */
public enum SendTypeEnum implements EnumIntf {

	sendNow("即时发送", "sendNow"),
	setTime("定时发送", "setTime");

	private final String value;
	private final String labelKey;
	SendTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SendTypeEnum> getList(){
		List<SendTypeEnum> result = new ArrayList<SendTypeEnum>();
		for(SendTypeEnum ae : SendTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(SendTypeEnum ae : SendTypeEnum.values()){
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
