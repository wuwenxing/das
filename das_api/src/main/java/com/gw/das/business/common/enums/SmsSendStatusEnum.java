package com.gw.das.business.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：短信发送状态类型定义
 */
public enum SmsSendStatusEnum {
	SendSuccess("已发送", "0"),
	ReceiveSuccess("成功接收", "1"),
	SendFail("发送失败", "2");
	
	private final String value;
	private final String labelKey;
	SmsSendStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SmsSendStatusEnum> getList(){
		List<SmsSendStatusEnum> result = new ArrayList<SmsSendStatusEnum>();
		for(SmsSendStatusEnum ae : SmsSendStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(SmsSendStatusEnum ae : SmsSendStatusEnum.values()){
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
