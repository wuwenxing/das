package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 星期
 */
public enum weekDaysEnum implements EnumIntf {
	
	SUNDAY("星期日", "0"),
	MONDAY("星期一", "1"),
	TUESDAY("星期二", "2"),
	WEDNESDAY("星期三", "3"),
	THURSDAY("星期四", "4"),
	FRIDAY("星期五", "5"),
	SATURDAY("星期六", "6");
	
	private final String labelKey;
	private final String value;
	weekDaysEnum(String key, String value) {
		this.labelKey = key;
		this.value = value;
	}
	
	public static List<weekDaysEnum> getList(){
		List<weekDaysEnum> result = new ArrayList<weekDaysEnum>();
		for(weekDaysEnum ae : weekDaysEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(weekDaysEnum ae : weekDaysEnum.values()){
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
