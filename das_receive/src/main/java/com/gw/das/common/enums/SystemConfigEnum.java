package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：系统配置参数类型定义
 */
public enum SystemConfigEnum {

	// 与SysConfig.properties对应
	webSocketUrl("websocket地址", "web.socket.url"),
	flumeWeb("flumeWeb地址", "flume.web"),
	flumeRoom("flumeRoom地址", "flume.room"),
	flumeDeposit("flumeDeposit地址", "flume.deposit"),
	flumeAppData("flumeAppData地址", "flume.appData"),

	kafkaBrokerIP_1("kafka服务器地址", "kafka.brokerIP_1"),
	
	hxApiUrl("hx短信接口url", "hx.api.url"),
	hxApiUsername("hx短信接口用户名", "hx.api.username"),
	hxApiPassword("hx短信接口密码", "hx.api.password")
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
