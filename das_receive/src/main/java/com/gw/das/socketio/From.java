package com.gw.das.socketio;

public class From {
	private FromUser fromUser; // 发送者详情
	private String topicId; // 发送订阅号

	public FromUser getFromUser() {
		return fromUser;
	}

	public void setFromUser(FromUser fromUser) {
		this.fromUser = fromUser;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

}
