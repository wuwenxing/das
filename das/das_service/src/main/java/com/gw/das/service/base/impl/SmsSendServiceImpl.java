package com.gw.das.service.base.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.Constants;
import com.gw.das.common.enums.SmsChannelEnum;
import com.gw.das.common.sms.FcySmsServer;
import com.gw.das.common.sms.HxSmsServer;
import com.gw.das.common.sms.MdkjSmsServer;
import com.gw.das.common.sms.SmsInfo;
import com.gw.das.common.sms.SmsListener;
import com.gw.das.common.sms.SmsUtil;
import com.gw.das.common.sms.ZqhlSmsServer;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.SmsEntity;
import com.gw.das.dao.market.entity.SmsTemplateEntity;
import com.gw.das.dao.market.entity.SmsTemplateLogEntity;
import com.gw.das.service.base.SmsSendService;

/**
 * 短信发送接口实现
 * 
 * @author wayne
 */
@Service
public class SmsSendServiceImpl implements SmsSendService {

	private static final Logger logger = LoggerFactory.getLogger(SmsSendServiceImpl.class);

	@Autowired
	@Qualifier("smsFcyListenerImpl")
	private SmsListener smsFcyListener;
	@Autowired
	@Qualifier("smsMdkjListenerImpl")
	private SmsListener smsMdkjListener;
	@Autowired
	@Qualifier("smsZqhlListenerImpl")
	private SmsListener smsZqhlListener;
	@Autowired
	@Qualifier("smsHxListenerImpl")
	private SmsListener smsHxListener;
	
	@Autowired
	@Qualifier("smsTemplateFcyListenerImpl")
	private SmsListener smsTemplateFcyListener;
	@Autowired
	@Qualifier("smsTemplateMdkjListenerImpl")
	private SmsListener smsTemplateMdkjListener;
	@Autowired
	@Qualifier("smsTemplateZqhlListenerImpl")
	private SmsListener smsTemplateZqhlListener;
	@Autowired
	@Qualifier("smsTemplateHxListenerImpl")
	private SmsListener smsTemplateHxListener;

	/**
	 * 开启异步线程发送 smsChannel:短信渠道
	 */
	public void asynThreading(final SmsTemplateEntity template, final SmsTemplateLogEntity reqLog, final String smsChannel) {
		if (null != smsChannel) {
			new Thread(new Runnable() {
				public void run() {
					try {
						send(template, reqLog, smsChannel);
					} catch (Exception e) {
						logger.error("开启异步线程发送error", e);
					}
				}
			}).start();
		}
	}
	
	/**
	 * 开启异步线程发送 smsChannel:短信渠道
	 */
	public void asynThreading(final SmsEntity smsEntity, final String smsChannel) {
		if (null != smsChannel) {
			new Thread(new Runnable() {
				public void run() {
					try {
						send(smsEntity, smsChannel);
					} catch (Exception e) {
						logger.error("开启异步线程发送error", e);
					}
				}
			}).start();
		}
	}

	/**
	 * 发送短信
	 */
	public void send(SmsTemplateEntity template, SmsTemplateLogEntity reqLog, String smsChannel) throws Exception {
		String content = "";
		String sendNo = reqLog.getSendNo();
		FcySmsServer fcySmsServer = null;
		MdkjSmsServer mdkjSmsServer = null;
		ZqhlSmsServer zqhlSmsServer = null;
		HxSmsServer hxSmsServer = null;
		if (smsChannel.equals(SmsChannelEnum.fcy.getLabelKey())) {
			content = template.getSmsSign() + template.getContent();
			content = SmsUtil.addSign(content);
			content = SmsUtil.addSuffix(content);
			// 短信发送-并添加监听器-统计发送数量及状态
			fcySmsServer = new FcySmsServer();
			fcySmsServer.addSmsListener(smsTemplateFcyListener);
		} else if (smsChannel.equals(SmsChannelEnum.mdkj.getLabelKey())) {
			content = template.getSmsSign() + template.getContent();
			content = SmsUtil.addSign(content);
			content = SmsUtil.addSuffix(content);
			// 短信发送-并添加监听器-统计发送数量及状态
			mdkjSmsServer = new MdkjSmsServer();
			mdkjSmsServer.addSmsListener(smsTemplateMdkjListener);
		} else if (smsChannel.equals(SmsChannelEnum.zqhl.getLabelKey())) {
			// 注意-start：使用至臻互联-短信接口-短信签名在短信平台已经固定了，故短信内容需要去掉签名
			content = template.getContent();
			content = SmsUtil.addSuffix(content);
			// 注意-end
			// 短信发送-并添加监听器-统计发送数量及状态
			zqhlSmsServer = new ZqhlSmsServer();
			zqhlSmsServer.addSmsListener(smsTemplateZqhlListener);
		} else if (smsChannel.equals(SmsChannelEnum.hx.getLabelKey())) {
			// 1、注意-start：使用Hx-短信接口-短信签名在短信平台已经固定了，故短信内容需要去掉签名
			// 2、退订回T，也不需要加
			content = template.getContent();
			// 注意-end
			// 短信发送-并添加监听器-统计发送数量及状态
			hxSmsServer = new HxSmsServer();
			hxSmsServer.addSmsListener(smsTemplateHxListener);
		}
		// 超过100条，需要分成多批发送
		String phones = reqLog.getLegalPhones();// 待发送的合法手机号
		List<String> phoneList = StringUtil.string2List(phones);
		List<List<String>> mobileBatches = SmsUtil.bigList2MultiSmallList(phoneList, Constants.batchSize);

		if (smsChannel.equals(SmsChannelEnum.fcy.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(template.getTemplateId());
				smsInfo.setTradeNo(template.getTemplateId() + "");
				smsInfo.setContent(content);
				smsInfo.setSendNo(sendNo);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(template.getCompanyId());

				fcySmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		} else if (smsChannel.equals(SmsChannelEnum.mdkj.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(template.getTemplateId());
				smsInfo.setTradeNo(template.getTemplateId() + "");
				smsInfo.setContent(content);
				smsInfo.setSendNo(sendNo);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(template.getCompanyId());

				mdkjSmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		} else if (smsChannel.equals(SmsChannelEnum.zqhl.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(template.getTemplateId());
				smsInfo.setTradeNo(template.getTemplateId() + "");
				smsInfo.setContent(content);
				smsInfo.setSendNo(sendNo);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(template.getCompanyId());

				zqhlSmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		} else if (smsChannel.equals(SmsChannelEnum.hx.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(template.getTemplateId());
				smsInfo.setTradeNo(template.getTemplateId() + "");
				smsInfo.setContent(content);
				smsInfo.setSendNo(sendNo);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(template.getCompanyId());

				hxSmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		}

	}
	
	
	/**
	 * 发送短信
	 */
	public void send(SmsEntity smsEntity, String smsChannel) throws Exception {
		String content = "";
		FcySmsServer fcySmsServer = null;
		MdkjSmsServer mdkjSmsServer = null;
		ZqhlSmsServer zqhlSmsServer = null;
		HxSmsServer hxSmsServer = null;
		if (smsChannel.equals(SmsChannelEnum.fcy.getLabelKey())) {
			content = smsEntity.getSmsSign() + smsEntity.getContent();
			content = SmsUtil.addSign(content);
			content = SmsUtil.addSuffix(content);
			// 短信发送-并添加监听器-统计发送数量及状态
			fcySmsServer = new FcySmsServer();
			fcySmsServer.addSmsListener(smsFcyListener);
		} else if (smsChannel.equals(SmsChannelEnum.mdkj.getLabelKey())) {
			content = smsEntity.getSmsSign() + smsEntity.getContent();
			content = SmsUtil.addSign(content);
			content = SmsUtil.addSuffix(content);
			// 短信发送-并添加监听器-统计发送数量及状态
			mdkjSmsServer = new MdkjSmsServer();
			mdkjSmsServer.addSmsListener(smsMdkjListener);
		} else if (smsChannel.equals(SmsChannelEnum.zqhl.getLabelKey())) {
			// 注意-start：使用至臻互联-短信接口-短信签名在短信平台已经固定了，故短信内容需要去掉签名
			content = smsEntity.getContent();
			content = SmsUtil.addSuffix(content);
			// 注意-end
			// 短信发送-并添加监听器-统计发送数量及状态
			zqhlSmsServer = new ZqhlSmsServer();
			zqhlSmsServer.addSmsListener(smsZqhlListener);
		} else if (smsChannel.equals(SmsChannelEnum.hx.getLabelKey())) {
			// 1、注意-start：使用Hx-短信接口-短信签名在短信平台已经固定了，故短信内容需要去掉签名
			// 2、退订回T，也不需要加
			content = smsEntity.getContent();
			// 注意-end
			// 短信发送-并添加监听器-统计发送数量及状态
			hxSmsServer = new HxSmsServer();
			hxSmsServer.addSmsListener(smsHxListener);
		}
		// 超过100条，需要分成多批发送
		String phones = smsEntity.getLegalPhones();// 待发送的合法手机号
		if(smsEntity.isFailPhonesFlag()){// 给发送失败的手机号再次发送
			phones = smsEntity.getFailPhones();
		}
		List<String> phoneList = StringUtil.string2List(phones);
		List<List<String>> mobileBatches = SmsUtil.bigList2MultiSmallList(phoneList, Constants.batchSize);

		if (smsChannel.equals(SmsChannelEnum.fcy.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(smsEntity.getSmsId());
				smsInfo.setTradeNo(smsEntity.getSmsId() + "");
				smsInfo.setContent(content);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(smsEntity.getCompanyId());

				fcySmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		} else if (smsChannel.equals(SmsChannelEnum.mdkj.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(smsEntity.getSmsId());
				smsInfo.setTradeNo(smsEntity.getSmsId() + "");
				smsInfo.setContent(content);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(smsEntity.getCompanyId());

				mdkjSmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		} else if (smsChannel.equals(SmsChannelEnum.zqhl.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(smsEntity.getSmsId());
				smsInfo.setTradeNo(smsEntity.getSmsId() + "");
				smsInfo.setContent(content);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(smsEntity.getCompanyId());

				zqhlSmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		} else if (smsChannel.equals(SmsChannelEnum.hx.getLabelKey())) {
			for (List<String> mobileBatch : mobileBatches) {
				// 短信实体封装
				SmsInfo smsInfo = new SmsInfo();
				smsInfo.setTemplateId(smsEntity.getSmsId());
				smsInfo.setTradeNo(smsEntity.getSmsId() + "");
				smsInfo.setContent(content);
				smsInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				smsInfo.setCompanyId(smsEntity.getCompanyId());

				hxSmsServer.send(smsInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		}

	}
	
	
	
	
	
}
