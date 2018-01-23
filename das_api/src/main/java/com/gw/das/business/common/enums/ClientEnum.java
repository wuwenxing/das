package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：客户端访问类型定义
 */
public enum ClientEnum {
	PC("PC端", "0", "pc"),
	MOBILE("手机端", "1", "mobile");

	private final String value;
	private final String labelKey;
	private final String labelKey_2;
	ClientEnum(String _operator, String labelKey, String labelKey_2) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.labelKey_2 = labelKey_2;
	}
	
	public static List<ClientEnum> getList(){
		List<ClientEnum> result = new ArrayList<ClientEnum>();
		for(ClientEnum ae : ClientEnum.values()){
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

	public String getLabelKey_2() {
		return labelKey_2;
	}
	
}
