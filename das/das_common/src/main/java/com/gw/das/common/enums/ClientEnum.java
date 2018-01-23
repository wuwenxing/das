package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：客户端访问类型定义
 */
public enum ClientEnum implements EnumIntf {
	
	pc("PC端", "pc"),
	mobile("移动端", "mobile");

	private final String value;
	private final String labelKey;
	ClientEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ClientEnum> getList(){
		List<ClientEnum> result = new ArrayList<ClientEnum>();
		for(ClientEnum ae : ClientEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey) {
		if (null != labelKey) {
			for (ClientEnum ae : ClientEnum.values()) {
				if (ae.getLabelKey().equals(labelKey)) {
					return ae.getValue();
				}
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
