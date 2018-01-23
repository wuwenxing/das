package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 账号类型
 */
public enum DataAccountTypeEnum implements EnumIntf {
	
	mini("迷你","0"),
	standard("标准","1"),
	vip("VIP","2");
	
	private final String value;
	private final String labelKey;
	DataAccountTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<DataAccountTypeEnum> getList(){
		List<DataAccountTypeEnum> result = new ArrayList<DataAccountTypeEnum>();
		for(DataAccountTypeEnum ae : DataAccountTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(DataAccountTypeEnum ae : DataAccountTypeEnum.values()){
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
