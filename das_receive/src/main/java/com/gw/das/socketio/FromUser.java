package com.gw.das.socketio;

public class FromUser {
	private String socketId; // 发送者socketId
	private String uuid; // 发送者uuid

	public String getSocketId() {
		return socketId;
	}

	public void setSocketId(String socketId) {
		this.socketId = socketId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
