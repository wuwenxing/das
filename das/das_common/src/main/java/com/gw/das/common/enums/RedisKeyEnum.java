package com.gw.das.common.enums;

/**
 * 账号类型
 */
public enum RedisKeyEnum implements EnumIntf {

	fcySmsStatusData("发财鱼短信状态数据", "fcySmsStatusData"),
	mdkjSmsStatusData("秒嘀科技短信状态数据", "hxSmsStatusData"),
	zzhlSmsStatusData("至臻互联短信状态数据", "zzhlSmsStatusData"),
	ymFlowStatusData("亿美流量充值状态数据", "ymFlowStatusData"),
	lmFlowStatusData("乐免流量充值状态数据", "lmFlowStatusData"),
	rlFlowStatusData("容联流量充值状态数据", "rlFlowStatusData")
	;
	
	private final String value;
	private final String labelKey;
	RedisKeyEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
