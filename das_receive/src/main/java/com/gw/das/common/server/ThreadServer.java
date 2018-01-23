package com.gw.das.common.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步执行server
 * @author wayne
 *
 */
public class ThreadServer<T> {
	private static final Logger logger = LoggerFactory.getLogger(ThreadServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	private ThreadListener<T> listener;

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
	}
	
	public void excute(final T param, final String... str){
		theadPool.execute(new Runnable() {
			public void run() {
				try {
					listener.excute(param, str);
				} catch (Exception e) {
					logger.error("出现异常", e);
				}
			}
		});
	}

	public void setListener(ThreadListener<T> listener) {
		this.listener = listener;
	}
	
}
