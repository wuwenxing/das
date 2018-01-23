package com.gw.das.web.sessionbean;

import java.util.Date;

import com.gw.das.dao.system.entity.SystemUserEntity;

/**
 * 摘要：在线用户对象
 */
public class Client implements java.io.Serializable {

	private static final long serialVersionUID = -8265522652490711669L;

	/**
	 * 用户IP
	 */
	private String ip;

	/**
	 * sessionId
	 */
	private String sessionId;

	/**
	 * 登录时间
	 */
	private Date loginDate;

	/**
	 * 登录的客户端类型
	 */
	private String clientType;

	/**
	 * 登录用户实体
	 */
	private SystemUserEntity user;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public SystemUserEntity getUser() {
		return user;
	}

	public void setUser(SystemUserEntity user) {
		this.user = user;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

}
