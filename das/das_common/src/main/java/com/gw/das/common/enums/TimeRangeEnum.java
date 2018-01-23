package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 持仓时间范围枚举
 */
public enum TimeRangeEnum implements EnumIntf {
	
	A("1分钟以下", "A"),
	B("1-5分钟", "B"),
	C("6-15分钟", "C"),
	D("16-60分钟", "D"),
	E("1-6小时", "E"),
	F("7小时-1日", "F"),
	G("1日-3日", "G"),
	H("4日-7日", "H"),
	I("8日-1个月", "I"),
	J("多于1个月", "J");
	
	private final String value;
	private final String labelKey;
	TimeRangeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<TimeRangeEnum> getList(){
		List<TimeRangeEnum> result = new ArrayList<TimeRangeEnum>();
		for(TimeRangeEnum ae : TimeRangeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(TimeRangeEnum ae : TimeRangeEnum.values()){
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
