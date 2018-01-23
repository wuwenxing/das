package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：咨询类型定义
 */
public enum AdvisoryEnum implements EnumIntf {
	
	QQ("QQ咨询", "qq"),
	Live800("Live800咨询", "live800");

	private final String value;
	private final String labelKey;
	AdvisoryEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<AdvisoryEnum> getList(){
		List<AdvisoryEnum> result = new ArrayList<AdvisoryEnum>();
		for(AdvisoryEnum ae : AdvisoryEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
