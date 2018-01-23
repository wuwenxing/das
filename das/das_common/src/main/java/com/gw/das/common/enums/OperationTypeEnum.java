package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作类型
 */
public enum OperationTypeEnum implements EnumIntf {
	
	start("启动", "1"),
	login("登陆", "2"),
	transaction("交易", "3"),
	Cancel("注销", "4"),
	out("退出", "5");
	
	private final String value;
	private final String labelKey;
	OperationTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<OperationTypeEnum> getList(){
		List<OperationTypeEnum> result = new ArrayList<OperationTypeEnum>();
		for(OperationTypeEnum ae : OperationTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(OperationTypeEnum ae : OperationTypeEnum.values()){
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
