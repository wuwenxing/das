package com.gw.das.common.log;

/**
 * 日志服务监听器
 * @author wayne
 */
public interface LogListener {
	/**
	 * 添加日志
	 * @param emailInfo
	 */
	public abstract void addLog(LogInfo logInfo);
}
