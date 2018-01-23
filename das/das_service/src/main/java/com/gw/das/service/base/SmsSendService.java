package com.gw.das.service.base;

import com.gw.das.dao.market.entity.SmsEntity;
import com.gw.das.dao.market.entity.SmsTemplateEntity;
import com.gw.das.dao.market.entity.SmsTemplateLogEntity;

public interface SmsSendService {

	/**
	 * 开启异步线程发送 smsChannel:短信渠道
	 */
	public void asynThreading(final SmsTemplateEntity template, final SmsTemplateLogEntity reqLog, final String smsChannel);
	
	/**
	 * 开启异步线程发送 smsChannel:短信渠道
	 */
	public void asynThreading(final SmsEntity smsEntity, final String smsChannel);
	
}
