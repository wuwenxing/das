package com.gw.das.api.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：系统配置参数类型定义
 * 
 * @author wayne
 */
public enum SystemConfigEnum implements EnumIntf {
	// 与application.properties对应
	springProfilesActive("参数-指定配置文件名", "spring.profiles.active"),
	
	// 与application-(dev/prod/uat).properties对应
	serviceScretUrl("通用基础服务平台URL", "service.scret.url"),
	serviceScretType("数据平台从通用基础服务平台URL获取Token的type值", "service.scret.type")
	
	;
	
	private final String value;
	private final String labelKey;

	SystemConfigEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<SystemConfigEnum> getList() {
		List<SystemConfigEnum> result = new ArrayList<SystemConfigEnum>();
		for (SystemConfigEnum ae : SystemConfigEnum.values()) {
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
