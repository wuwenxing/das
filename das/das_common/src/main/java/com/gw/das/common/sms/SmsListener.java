package com.gw.das.common.sms;

/**
 * 短信服务监听器
 * @author wayne
 */
public abstract interface SmsListener {
	/**
	 * 短信发送以前做的操作
	 * @param emailInfo
	 */
	public abstract void updateBefore(SmsContext smsContext);
	/**
	 * 短信发送成功呢的操作
	 * @param emailContext
	 */
	public abstract void updateAfter(SmsContext smsContext);
	/**
	 * 发送短信出现异常时进行的错误处理
	 */
	public abstract void updateAfterThrowable(SmsContext smsContext);
}
