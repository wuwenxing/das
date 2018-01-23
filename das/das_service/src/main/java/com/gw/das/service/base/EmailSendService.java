package com.gw.das.service.base;

import com.gw.das.dao.market.entity.EmailEntity;
import com.gw.das.dao.market.entity.EmailTemplateEntity;
import com.gw.das.dao.market.entity.EmailTemplateLogEntity;

public interface EmailSendService {

	/**
	 * 开启异步线程发送
	 * 特别注意：使用注册渠道邮件服务器发送
	 * 用于发送少量邮件
	 */
	public void asynThreading(final String title, final String content, final EmailTemplateEntity templateEntity,
			final EmailTemplateLogEntity logEntity);

	/**
	 * 开启异步线程发送
	 * 特别注意：使用推广渠道邮件服务器发送
	 * 用于发送大量邮件
	 */
	public void asynThreading(final EmailEntity emailEntity);

	/**
	 * 开启异步线程发送
	 * 特别注意：使用推广渠道邮件服务器发送
	 * 用于发送大量邮件
	 */
	public void asynThreading(final String batchIds, final EmailTemplateEntity template);
}
