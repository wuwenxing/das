package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 来源类型定义 
 */
public enum SourceTypeEnum implements EnumIntf {
	
	input("手动输入或上传", "input"),
	userScreen("用户筛选", "userScreen"),
	userGroup("用户分组", "userGroup");
	
	private final String value;
	private final String labelKey;
	SourceTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SourceTypeEnum> getList(){
		List<SourceTypeEnum> result = new ArrayList<SourceTypeEnum>();
		for(SourceTypeEnum ae : SourceTypeEnum.values()){
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
