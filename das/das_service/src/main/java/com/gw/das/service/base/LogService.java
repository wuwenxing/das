package com.gw.das.service.base;

public interface LogService {

	/**
	 * 开启异步线程
	 */
	public void addLog(final String url, final String param);
	
}
