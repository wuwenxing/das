package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 计划时间类型定义 
 */
public enum PlanTimeTypeEnum implements EnumIntf {

	dayBefore("每天的前一天", "dayBefore"),
	day("每天", "day"),
	month("每月", "month"),
	year("每年", "year");

	private final String value;
	private final String labelKey;
	PlanTimeTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<PlanTimeTypeEnum> getList(){
		List<PlanTimeTypeEnum> result = new ArrayList<PlanTimeTypeEnum>();
		for(PlanTimeTypeEnum ae : PlanTimeTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(PlanTimeTypeEnum ae : PlanTimeTypeEnum.values()){
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
