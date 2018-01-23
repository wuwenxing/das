package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public enum srcTypeEnum implements EnumIntf {
	
	WEB("web", "web"),
	CLIENT("client", "client");

	private final String value;
	private final String labelKey;
	srcTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<srcTypeEnum> getList(){
		List<srcTypeEnum> result = new ArrayList<srcTypeEnum>();
		for(srcTypeEnum ae : srcTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(srcTypeEnum ae : srcTypeEnum.values()){
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
