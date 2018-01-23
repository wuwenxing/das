package com.gw.das.common.server;

/**
 * 异步执行监听器
 * @author wayne
 */
public abstract interface ThreadListener<T> {
	
	/**
	 * 执行方法
	 */
	public void excute(final T param, final String... str);
	
}
