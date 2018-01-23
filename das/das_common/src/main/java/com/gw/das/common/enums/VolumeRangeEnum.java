package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 平仓手数范围枚举
 */
public enum VolumeRangeEnum implements EnumIntf {
	
	A("0-0.09", "A"),
	B("0.1-0.49", "B"),
	C("0.5-0.99", "C"),
	D("1-1.99", "D"),
	E("2-3.99", "E"),
	F("4-9.99", "F"),
	G("10-19.99", "G"),
	H("20-30", "H"),
	J("多于30", "J");
	
	private final String value;
	private final String labelKey;
	VolumeRangeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<VolumeRangeEnum> getList(){
		List<VolumeRangeEnum> result = new ArrayList<VolumeRangeEnum>();
		for(VolumeRangeEnum ae : VolumeRangeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(VolumeRangeEnum ae : VolumeRangeEnum.values()){
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
