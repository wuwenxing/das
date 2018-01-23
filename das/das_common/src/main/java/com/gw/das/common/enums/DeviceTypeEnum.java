package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机类型定义
 */
public enum DeviceTypeEnum implements EnumIntf {
	PC("PC", "0"),
	ANDROID("ANDROID", "1"),
	IOS("IOS", "2"),
	PCUI("PCUI", "3"),
	WEBUI("WEBUI", "4"),
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
	
	public static List<DeviceTypeEnum> getList2() {
		List<DeviceTypeEnum> result = new ArrayList<DeviceTypeEnum>();
		result.add(DeviceTypeEnum.ANDROID);
		result.add(DeviceTypeEnum.IOS);
		return result;
	}
	
	public static List<DeviceTypeEnum> getList3() {
		List<DeviceTypeEnum> result = new ArrayList<DeviceTypeEnum>();
		result.add(DeviceTypeEnum.PC);
		result.add(DeviceTypeEnum.ANDROID);
		result.add(DeviceTypeEnum.IOS);
		result.add(DeviceTypeEnum.PCUI);
		result.add(DeviceTypeEnum.MOBILE);
		return result;
	}
	
	public static List<DeviceTypeEnum> getList4() {
		List<DeviceTypeEnum> result = new ArrayList<DeviceTypeEnum>();
		result.add(DeviceTypeEnum.APP);
		result.add(DeviceTypeEnum.PCUI);
		result.add(DeviceTypeEnum.ANDROID);
		result.add(DeviceTypeEnum.IOS);
		return result;
	}
	
	public static List<DeviceTypeEnum> getList5() {
		List<DeviceTypeEnum> result = new ArrayList<DeviceTypeEnum>();
		result.add(DeviceTypeEnum.PC);
		result.add(DeviceTypeEnum.MOBILE);
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
