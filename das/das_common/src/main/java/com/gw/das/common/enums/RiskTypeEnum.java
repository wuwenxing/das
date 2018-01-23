package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 风险类型
 */
public enum RiskTypeEnum implements EnumIntf {
	
	bmd("白名单", "0"),
	fj("封禁", "1"),
	fx("风险", "2"),
	cxwxx("查询无信息", "3");
	
	private final String value;
	private final String labelKey;
	RiskTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<RiskTypeEnum> getList(){
		List<RiskTypeEnum> result = new ArrayList<RiskTypeEnum>();
		for(RiskTypeEnum ae : RiskTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(RiskTypeEnum ae : RiskTypeEnum.values()){
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
