package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务标签内容
 */
public enum BusTagContentEnum implements EnumIntf {
	
	TAGCONTENT1("开始模拟开户流程","1"),
	TAGCONTENT2("开始真实开户流程","2"),
	TAGCONTENT3("开始入金流程","3"),
	TAGCONTENT4("开始资讯流程","4");
	
	private final String value;
	private final String labelKey;
	BusTagContentEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<BusTagContentEnum> getList(){
		List<BusTagContentEnum> result = new ArrayList<BusTagContentEnum>();
		for(BusTagContentEnum ae : BusTagContentEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(BusTagContentEnum ae : BusTagContentEnum.values()){
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
