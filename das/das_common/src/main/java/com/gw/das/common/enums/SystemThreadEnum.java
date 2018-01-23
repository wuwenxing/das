package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：线程类型定义
 */
public enum SystemThreadEnum implements EnumIntf {
	emailSyncTask("邮件同步", "emailSyncTask"),
	syncSmsStatusTask("短信及流量状态同步", "syncSmsStatusTask"),
	smsAndEmailSendTask("定时发送邮件及短信", "smsAndEmailSendTask"),
	smsAndEmailCountTask("短信邮件发送次数统计", "smsAndEmailCountTask"),
	blackListTask("恢复免恼用户", "blackListTask")
	;
	
	private final String value;
	private final String labelKey;
	SystemThreadEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SystemThreadEnum> getList(){
		List<SystemThreadEnum> result = new ArrayList<SystemThreadEnum>();
		for(SystemThreadEnum ae : SystemThreadEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(SystemThreadEnum ae : SystemThreadEnum.values()){
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
