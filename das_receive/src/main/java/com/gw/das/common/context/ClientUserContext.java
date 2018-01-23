package com.gw.das.common.context;

import java.util.Date;

/**
 * 客户端连接server端成功后，使用此对象作为上下文参数
 * 
 * @author wayne
 */
public class ClientUserContext implements java.io.Serializable {

	private static final long serialVersionUID = -8265522652490711669L;

	private static transient ThreadLocal<ClientUserContext> threadLocal = new ThreadLocal<ClientUserContext>();

	private String ip; // 客户端ip
	private String sid; // 分配给客户端的开发者账号
	private String type; // 请求类型（netty方式连接/浏览器登录连接）
	private Date date; // 请求时间
	private int businessPlatform; // 调用的业务平台

	public static void set(ClientUserContext userContext) {
		threadLocal.set(userContext);
	}

	public static ClientUserContext get() {
		ClientUserContext userContext = threadLocal.get();
		if (userContext != null) {
			return userContext;
		}
		return null;
	}

	@Override
	public String toString() {
		return "ClientUserContext[ip=" + ip + ", sid=" + sid + ", type=" + type + ", date=" + date
				+ ", businessPlatform=" + businessPlatform + "]";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getBusinessPlatform() {
		return businessPlatform;
	}

	public void setBusinessPlatform(int businessPlatform) {
		this.businessPlatform = businessPlatform;
	}

}
