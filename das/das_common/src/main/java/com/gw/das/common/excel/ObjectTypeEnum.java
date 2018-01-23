package com.gw.das.common.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象类型定义 
 */
public enum ObjectTypeEnum {

	int_("整数", "int"),
	double_("小数", "double"),
	String_("字符串", "String"),
	Date_("日期", "Date");
	
	private final String value;
	private final String labelKey;//唯一标示
	ObjectTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ObjectTypeEnum> getList(){
		List<ObjectTypeEnum> result = new ArrayList<ObjectTypeEnum>();
		for(ObjectTypeEnum ae : ObjectTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(ObjectTypeEnum ae : ObjectTypeEnum.values()){
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
