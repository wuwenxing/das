package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：发送状态类型定义
 */
public enum SendStatusEnum implements EnumIntf {

	sendFail("发送失败", "sendFail"),
	sendSuccess("发送成功", "sendSuccess"),
	sendRecSuccess("成功接收", "sendRecSuccess");
	
	private final String value;
	private final String labelKey;
	SendStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SendStatusEnum> getList(){
		List<SendStatusEnum> result = new ArrayList<SendStatusEnum>();
		for(SendStatusEnum ae : SendStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static List<SendStatusEnum> getEmailStatusList(){
		List<SendStatusEnum> result = new ArrayList<SendStatusEnum>();
		result.add(SendStatusEnum.sendFail);
		result.add(SendStatusEnum.sendSuccess);
		return result;
	}
	
	public static String format(String labelKey){
		for(SendStatusEnum ae : SendStatusEnum.values()){
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
