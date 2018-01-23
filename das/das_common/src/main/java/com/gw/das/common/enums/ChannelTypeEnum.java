package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

public enum ChannelTypeEnum implements EnumIntf {
	
	webSite("网站渠道", "webSite"),
	app("app渠道", "app"),
	room("直播间渠道", "room");

	private final String value;
	private final String labelKey;
	ChannelTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ChannelTypeEnum> getList(){
		List<ChannelTypeEnum> result = new ArrayList<ChannelTypeEnum>();
		for(ChannelTypeEnum ae : ChannelTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static ChannelTypeEnum format(String labelKey){
		for(ChannelTypeEnum ae : ChannelTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae;
			}
		}
		return null;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
