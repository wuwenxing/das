package com.gw.das.common.token;

/**
 * Token对象
 * 
 * @author wayne
 *
 */
public class Token {
	
	private int expires; // 有效期
	private String token;// token值

	public int getExpires() {
		return expires;
	}

	public void setExpires(int expires) {
		this.expires = expires;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
