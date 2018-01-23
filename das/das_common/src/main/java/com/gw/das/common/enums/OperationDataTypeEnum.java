package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作类型
 */
public enum OperationDataTypeEnum implements EnumIntf {
	
	online("上线", "1"),
	publicChat("公聊", "2"),
	register("注册", "3"),
	login ("登录", "4"),
	out("退出", "5"),
	offline("下线", "6"),
	video("视频", "7"),
	privateChat("私聊", "8");
	private final String value;
	private final String labelKey;
	OperationDataTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<OperationDataTypeEnum> getList(){
		List<OperationDataTypeEnum> result = new ArrayList<OperationDataTypeEnum>();
		for(OperationDataTypeEnum ae : OperationDataTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(OperationDataTypeEnum ae : OperationDataTypeEnum.values()){
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
