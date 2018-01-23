package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 手机流量通道枚举
 */
public enum FlowChannelEnum implements EnumIntf {

	ym("亿美", "ym"),
	lm("乐免", "lm"),
	rl("容联", "rl")
	;
	
	private final String value;
	private final String labelKey;
	FlowChannelEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<FlowChannelEnum> getList(){
		List<FlowChannelEnum> result = new ArrayList<FlowChannelEnum>();
		for(FlowChannelEnum ae : FlowChannelEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(FlowChannelEnum ae : FlowChannelEnum.values()){
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
