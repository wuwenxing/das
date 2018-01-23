package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：请求提交状态类型定义
 */
public enum CommitStatusEnum implements EnumIntf {
	commitReady("准备提交", "commitReady"),
	commitFail("提交失败", "commitFail"),
	commitSuccess("提交成功", "commitSuccess");
	
	private final String value;
	private final String labelKey;
	CommitStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<CommitStatusEnum> getList(){
		List<CommitStatusEnum> result = new ArrayList<CommitStatusEnum>();
		for(CommitStatusEnum ae : CommitStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(CommitStatusEnum ae : CommitStatusEnum.values()){
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
