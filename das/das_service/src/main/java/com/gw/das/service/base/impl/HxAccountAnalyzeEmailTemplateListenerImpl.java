package com.gw.das.service.base.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.email.EmailContext;
import com.gw.das.common.email.EmailInfo;
import com.gw.das.common.email.EmailListener;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;
import com.gw.das.service.market.AccountAnalyzeService;

/**
 * service层公共方法
 * @author wayne
 */
@Service
public class HxAccountAnalyzeEmailTemplateListenerImpl implements EmailListener{

	private static final Logger logger = LoggerFactory.getLogger(HxAccountAnalyzeEmailTemplateListenerImpl.class);

	@Autowired
	private AccountAnalyzeService accountAnalyzeService;
	
	@Override
	public void updateBefore(EmailContext emailContext) {
		try{
			Object[] objAry = new Object[1];
			EmailInfo emailInfo = emailContext.getEmailInfo();
			logger.info("before[to=" + emailInfo.getTo()[0]+"]");
			AccountAnalyzeEntity entity = accountAnalyzeService.findById(emailContext.getEmailInfo().getTemplateId());
			entity.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 邮件发送状态-发送失败
			entity.setSendTime(new Date());
			accountAnalyzeService.update(entity, emailInfo.getLoginName(), emailInfo.getLoginIp());
			objAry[0] = entity;
			emailContext.setObjAry(objAry);
		}catch(Exception e){
			logger.error("邮件发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfter(EmailContext emailContext) {
		try{
			EmailInfo emailInfo = emailContext.getEmailInfo();
			logger.info("after[to=" + emailInfo.getTo()[0]+"]");
			AccountAnalyzeEntity entity = (AccountAnalyzeEntity) emailContext.getObjAry()[0];
			if(null != emailContext.getHxResponse()){
				String status = emailContext.getHxResponse().getStatus();
				if("0".equals(status)){
					entity.setSendStatus(SendStatusEnum.sendSuccess.getLabelKey());// 邮件发送状态-发送成功
				}else{
					entity.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 邮件发送状态-发送失败
					logger.error("调用恒信接口发送邮件失败，status=" + status);
				}
			}else{
				entity.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 邮件发送状态-发送失败
				logger.error("调用恒信接口发送邮件失败，response is null");
			}
			accountAnalyzeService.update(entity, emailInfo.getLoginName(), emailInfo.getLoginIp());
		}catch(Exception e){
			logger.error("邮件发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfterThrowable(EmailContext emailContext) {
		try{
			EmailInfo emailInfo = emailContext.getEmailInfo();
			logger.info("afterThrowable[to=" + emailInfo.getTo()[0]+"]");
			AccountAnalyzeEntity entity = (AccountAnalyzeEntity) emailContext.getObjAry()[0];
			entity.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 邮件发送状态-发送失败
			accountAnalyzeService.update(entity, emailInfo.getLoginName(), emailInfo.getLoginIp());
		}catch(Exception e){
			logger.error("邮件发送出现异常:" + e.getMessage(), e);
		}
	}
	
}
