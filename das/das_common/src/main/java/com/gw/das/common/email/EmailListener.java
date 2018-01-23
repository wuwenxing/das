package com.gw.das.common.email;

/**
 * 邮件服务监听器
 * @author wayne
 */
public interface EmailListener {
	/**
	 * 邮件发送以前做的操作
	 * @param emailInfo
	 */
	public abstract void updateBefore(EmailContext emailInfo);
	/**
	 * 邮件发送成功呢的操作
	 * @param emailContext
	 */
	public abstract void updateAfter(EmailContext emailContext);
	/**
	 * 发送邮件出现异常时进行的错误处理
	 */
	public abstract void updateAfterThrowable(EmailContext emailContext);
}
