package com.gw.das.common.sms;

import java.util.ArrayList;
import java.util.List;

/**
 * 摘要：短信接口-发财鱼-返回code定义
 */
public enum FCYResCodeEnum {
	P00000("交易请求成功", "P00000"),
	P00001("非法用户", "P00001"),
	P00002("参数错误", "P00002"),
	P00003("签权失败", "P00003"),
	P00004("系统异常", "P00004"),
	P00005("账户余额不足", "P00005"),
	P00009("其他未知错误", "P00009");
	
	private final String value;
	private final String labelKey;
	FCYResCodeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<FCYResCodeEnum> getList(){
		List<FCYResCodeEnum> result = new ArrayList<FCYResCodeEnum>();
		for(FCYResCodeEnum ae : FCYResCodeEnum.values()){
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
