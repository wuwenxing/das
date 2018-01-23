package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：流量充值状态类型定义
 */
public enum FlowStatusEnum implements EnumIntf {

	sendFail("充值失败", "sendFail"),
	sendSuccess("充值成功", "sendSuccess");
	
	private final String value;
	private final String labelKey;
	FlowStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<FlowStatusEnum> getList(){
		List<FlowStatusEnum> result = new ArrayList<FlowStatusEnum>();
		for(FlowStatusEnum ae : FlowStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(FlowStatusEnum ae : FlowStatusEnum.values()){
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
