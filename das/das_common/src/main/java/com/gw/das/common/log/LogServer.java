package com.gw.das.common.log;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogServer {
	private static final Logger logger = LoggerFactory.getLogger(LogServer.class);
	
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;

	/**
	 * 日志监听器
	 */
	private LogListener logListener = null;
	
	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
	}

	/**
	 * 添加多条日志
	 */
	public void addLogs(List<LogInfo> logInfos) {
		for (LogInfo logInfo : logInfos) {
			addLog(logInfo);
		}
	}
	
	/**
	 * 添加单条日志
	 */
	public void addLog(final LogInfo logInfo) {
		theadPool.execute(new Runnable() {
			public void run() {
				try {
					logListener.addLog(logInfo);
				} catch (Exception e) {
					logger.error("add log exception: " + e.getMessage());
				}
			}
		});
	}

	public LogListener getLogListener() {
		return logListener;
	}

	public void setLogListener(LogListener logListener) {
		this.logListener = logListener;
	}

}
