package com.gw.das.common.email;

import com.gw.das.common.sms.HxApiResponse;

/**
 * 邮件发送执行的上下文信息-即存储开始到结束的数据
 * @author wayne
 */
public class EmailContext {

	private EmailInfo emailInfo;
	private Object[] objAry; // 保存的上下文对象
	private HxApiResponse hxResponse;
	private Throwable throwable;

	public EmailInfo getEmailInfo() {
		return emailInfo;
	}

	public void setEmailInfo(EmailInfo emailInfo) {
		this.emailInfo = emailInfo;
	}

	public Object[] getObjAry() {
		return objAry;
	}

	public void setObjAry(Object[] objAry) {
		this.objAry = objAry;
	}

	public HxApiResponse getHxResponse() {
		return hxResponse;
	}

	public void setHxResponse(HxApiResponse hxResponse) {
		this.hxResponse = hxResponse;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
}
