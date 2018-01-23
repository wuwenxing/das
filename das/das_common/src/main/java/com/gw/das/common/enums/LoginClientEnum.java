package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：登录客户端类型定义
 */
public enum LoginClientEnum implements EnumIntf {

	systemThread("本系统任务", "systemThread"),
	systemInterface("系统外部接口", "systemInterface"),
	systemUserLogin("系统用户登录", "systemUserLogin"),
	nettyUserLogin("分配的netty用户登录", "nettyUserLogin");

	private final String value;
	private final String labelKey;
	LoginClientEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<LoginClientEnum> getList(){
		List<LoginClientEnum> result = new ArrayList<LoginClientEnum>();
		for(LoginClientEnum ae : LoginClientEnum.values()){
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
