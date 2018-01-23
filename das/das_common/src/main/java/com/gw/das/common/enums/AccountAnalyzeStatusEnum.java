package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：账户诊断生成状态定义
 */
public enum AccountAnalyzeStatusEnum implements EnumIntf {
	
	N("未生成", "N"),
	Y("已生成", "Y");

	private final String value;
	private final String labelKey;
	AccountAnalyzeStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<AccountAnalyzeStatusEnum> getList(){
		List<AccountAnalyzeStatusEnum> result = new ArrayList<AccountAnalyzeStatusEnum>();
		for(AccountAnalyzeStatusEnum ae : AccountAnalyzeStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(AccountAnalyzeStatusEnum ae : AccountAnalyzeStatusEnum.values()){
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
