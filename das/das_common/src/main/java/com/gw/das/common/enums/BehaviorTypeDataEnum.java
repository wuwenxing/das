package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 行为类型
 */
public enum BehaviorTypeDataEnum implements EnumIntf {
	
	visit("访问", "1"),
	live("咨询", "2"),
	demo("模拟", "3"),
	real("真实", "4"),
	event("事件","6");

	private final String value;
	private final String labelKey;
	BehaviorTypeDataEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<BehaviorTypeDataEnum> getList(){
		List<BehaviorTypeDataEnum> result = new ArrayList<BehaviorTypeDataEnum>();
		for(BehaviorTypeDataEnum ae : BehaviorTypeDataEnum.values()){
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
