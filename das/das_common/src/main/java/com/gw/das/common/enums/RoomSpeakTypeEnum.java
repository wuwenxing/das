package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播间-发言类型定义 
 */
public enum RoomSpeakTypeEnum implements EnumIntf {

	publicSpeak("公聊", "public"),
	privateSpeak("私聊", "private");

	private final String value;
	private final String labelKey;
	RoomSpeakTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<RoomSpeakTypeEnum> getList(){
		List<RoomSpeakTypeEnum> result = new ArrayList<RoomSpeakTypeEnum>();
		for(RoomSpeakTypeEnum ae : RoomSpeakTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(RoomSpeakTypeEnum ae : RoomSpeakTypeEnum.values()){
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
