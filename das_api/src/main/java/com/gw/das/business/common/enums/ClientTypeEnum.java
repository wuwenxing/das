package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：客户端类型定义
 * 
 * @author darren
 *
 */
public enum ClientTypeEnum {
	WINDOWS("1", "Windows"),
	IOS("2", "IOS"),
	ANDROID("3", "Android"),
	WEBUI("4", "WEBUI"),
	MT4("5", "MT4"),
	MT5("6", "MT5"),
	MANAGER("7", "Manager"),
	WECHAT("8", "Wechat"),
	FO("9", "FO"),
	BO("10", "BO"),
	OTHER1("11", "OTHER1"),
	OTHER2("12", "OTHER2"),
	OTHER3("13", "OTHER3"),
	SYSTEM("14", "System"),
	SYSTEMAGENT("15", "SystemAgent");

	private final String labelKey;
	private final String value;
	ClientTypeEnum(String labelKey,String _operator) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ClientTypeEnum> getList(){
		List<ClientTypeEnum> result = new ArrayList<ClientTypeEnum>();
		for(ClientTypeEnum ae : ClientTypeEnum.values()){
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
