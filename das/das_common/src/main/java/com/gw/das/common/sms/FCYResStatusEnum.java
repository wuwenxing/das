package com.gw.das.common.sms;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：短信接口-发财鱼-返回status定义
 */
public enum FCYResStatusEnum {
	status0("未处理", "0"),
	status1("处理中", "1"),
	status4("处理成功", "4"),
	status5("处理失败", "5");
	
	private final String value;
	private final String labelKey;
	FCYResStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<FCYResStatusEnum> getList(){
		List<FCYResStatusEnum> result = new ArrayList<FCYResStatusEnum>();
		for(FCYResStatusEnum ae : FCYResStatusEnum.values()){
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
