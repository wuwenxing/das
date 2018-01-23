package com.gw.das.netty;

import java.util.ArrayList;
import java.util.List;

public enum RpcStatusEnum {
	success("请求成功", "0"),
	fail("请求失败", "1"),
	exception("接口异常", "2");
	
	private final String value;
	private final String labelKey;
	RpcStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<RpcStatusEnum> getList(){
		List<RpcStatusEnum> result = new ArrayList<RpcStatusEnum>();
		for(RpcStatusEnum ae : RpcStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(RpcStatusEnum ae : RpcStatusEnum.values()){
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
