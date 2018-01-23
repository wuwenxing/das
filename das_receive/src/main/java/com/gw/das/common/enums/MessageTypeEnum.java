package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息类型定义
 */
public enum MessageTypeEnum {
	
	web("网站消息", "web", "das_web_ods_kafka"),
	room("直播间消息", "room", "das_room_ods_kafka"),
	deposit("入金消息", "deposit", "das_deposit_ods_kafka"),
	app("手机APP消息", "appData", "das_app_ods_kafka"),
	;
	private final String value;
	private final String labelKey;
	private final String topic;// 对应kafka的唯一标示

	MessageTypeEnum(String _operator, String labelKey, String topic) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.topic = topic;
	}

	public static List<MessageTypeEnum> getList() {
		List<MessageTypeEnum> result = new ArrayList<MessageTypeEnum>();
		for (MessageTypeEnum ae : MessageTypeEnum.values()) {
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

	public String getTopic() {
		return topic;
	}

}
