package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备类型定义
 */
public enum DeviceTypeEnum{
	PC("PC", "0"),
	ANDROID("ANDROID", "1"),
	IOS("IOS", "2"),
	PCUI("PCUI", "3"),
	WEBUI("WEBUI","4"),
	APP("APP", "5"),
	MOBILE("MOBILE", "6");
	
	private final String value;
	private final String labelKey;
	
	DeviceTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<DeviceTypeEnum> getList() {
		List<DeviceTypeEnum> result = new ArrayList<DeviceTypeEnum>();
		for (DeviceTypeEnum ae : DeviceTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey) {
		if (null != labelKey) {
			for (DeviceTypeEnum ae : DeviceTypeEnum.values()) {
				if (ae.getLabelKey().equals(labelKey)) {
					return ae.getValue();
				}
			}
		}
		return labelKey;
	}

	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
