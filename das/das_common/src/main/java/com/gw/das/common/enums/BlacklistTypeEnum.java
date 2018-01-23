package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典-黑名单类型blacklistType-子类型定义
 */
public enum BlacklistTypeEnum implements EnumIntf {

	blacklist("黑名单", "blacklist"),
	freeAngry("免扰", "freeAngry");

	private final String value;
	private final String labelKey;

	BlacklistTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<BlacklistTypeEnum> getList() {
		List<BlacklistTypeEnum> result = new ArrayList<BlacklistTypeEnum>();
		for (BlacklistTypeEnum ae : BlacklistTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(BlacklistTypeEnum ae : BlacklistTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public static String getValue(String labelKey){
		for(BlacklistTypeEnum ae : BlacklistTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return "";
	}

	public static String getKey(String value){
		for(BlacklistTypeEnum ae : BlacklistTypeEnum.values()){
			if(ae.getValue().equals(value)){
				return ae.getLabelKey();
			}
		}
		return "";
	}
	
	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
