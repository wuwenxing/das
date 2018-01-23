package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 行为类型
 */
public enum UserTypeDataEnum implements EnumIntf {
	
	visit("游客", "1"),
	live("注册", "2"),
	demo("模拟用户", "3"),
	realA("真实A用户", "4"),
	realN("真实N用户", "5"),
	vip("VIP用户", "6"),
	analyst("分析师", "7"),
	admin("管理员", "8"),
	customer("客服","9");

	private final String value;
	private final String labelKey;
	UserTypeDataEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<UserTypeDataEnum> getList(){
		List<UserTypeDataEnum> result = new ArrayList<UserTypeDataEnum>();
		for(UserTypeDataEnum ae : UserTypeDataEnum.values()){
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
