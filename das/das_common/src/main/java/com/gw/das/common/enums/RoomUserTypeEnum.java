package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播间用户级别
 */
public enum RoomUserTypeEnum implements EnumIntf {
	
	tourist("游客", "1"),
	register("注册", "2"),
	dome("模拟用户", "3"),
	realAccount("真实A用户", "4"),
	realDeposit("真实N用户", "5"),
	vip("VIP用户", "6"),
	analysts("分析师", "7"),
	administrator("管理员", "8"),
	customerService("客服", "9");
	
	private final String value;
	private final String labelKey;

	RoomUserTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}

	public static List<RoomUserTypeEnum> getList() {
		List<RoomUserTypeEnum> result = new ArrayList<RoomUserTypeEnum>();
		for (RoomUserTypeEnum ae : RoomUserTypeEnum.values()) {
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		if(null != labelKey){
			for(RoomUserTypeEnum ae : RoomUserTypeEnum.values()){
				if(ae.getLabelKey().equals(labelKey)){
					return ae.getValue();
				}
			}
		}
		return labelKey;
	}
	
	public String getValue() {
		return this.value;
	}

	public String getLabelKey() {
		return this.labelKey;
	}
}
