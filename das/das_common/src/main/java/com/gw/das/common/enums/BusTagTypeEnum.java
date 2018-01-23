package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务标签类型
 */
public enum BusTagTypeEnum implements EnumIntf {
	
	PAGE("进入页面","1"),
	EVENT("触发事件","2");
	
	private final String value;
	private final String labelKey;
	BusTagTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<BusTagTypeEnum> getList(){
		List<BusTagTypeEnum> result = new ArrayList<BusTagTypeEnum>();
		for(BusTagTypeEnum ae : BusTagTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(BusTagTypeEnum ae : BusTagTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return null;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
