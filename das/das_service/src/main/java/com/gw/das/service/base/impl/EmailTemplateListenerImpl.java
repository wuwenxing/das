package com.gw.das.service.base.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.email.EmailContext;
import com.gw.das.common.email.EmailInfo;
import com.gw.das.common.email.EmailListener;
import com.gw.das.common.enums.CommitStatusEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.dao.market.entity.EmailTemplateDetailContentEntity;
import com.gw.das.dao.market.entity.EmailTemplateDetailEntity;
import com.gw.das.service.market.EmailTemplateDetailContentService;
import com.gw.das.service.market.EmailTemplateDetailService;

/**
 * service层公共方法
 * @author wayne
 */
@Service
public class EmailTemplateListenerImpl implements EmailListener{

	private static final Logger logger = LoggerFactory.getLogger(EmailTemplateListenerImpl.class);

	@Autowired
	private EmailTemplateDetailService emailTemplateDetailService;
	@Autowired
	private EmailTemplateDetailContentService emailTemplateDetailContentService;
	
	@Override
	public void updateBefore(EmailContext emailContext) {
		try{
			Object[] objAry = new Object[2];
			
			EmailInfo emailInfo = emailContext.getEmailInfo();
			logger.info("before[to=" + emailInfo.getTo()[0]+"]");

			// 登录设置当前用户-系统接口
			Long companyId = emailInfo.getCompanyId();
			UserContext.setSystemInterface("127.0.0.1", companyId);
			
			EmailTemplateDetailContentEntity detailContent = new EmailTemplateDetailContentEntity();
			detailContent.setContent(emailInfo.getContent());
			emailTemplateDetailContentService.saveOrUpdate(detailContent);
			objAry[0] = detailContent;
			
			EmailTemplateDetailEntity detail = new EmailTemplateDetailEntity();
			detail.setTemplateId(emailInfo.getTemplateId());
			detail.setTitle(emailInfo.getTitle());
			detail.setContentId(detailContent.getContentId());
			detail.setSendEmail(emailInfo.getFrom());
			detail.setRecEmail(emailInfo.getTo()[0]);
			detail.setCommitStatus(CommitStatusEnum.commitReady.getLabelKey());// 邮件提交状态
			detail.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 邮件发送状态-发送失败
			emailTemplateDetailService.saveOrUpdate(detail);
			objAry[1] = detail;
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
			
			EmailTemplateDetailEntity detail = (EmailTemplateDetailEntity) emailContext.getObjAry()[1];
			detail.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 邮件提交状态
			detail.setSendStatus(SendStatusEnum.sendSuccess.getLabelKey());// 邮件发送状态-发送成功
			emailTemplateDetailService.saveOrUpdate(detail);
		}catch(Exception e){
			logger.error("邮件发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfterThrowable(EmailContext emailContext) {
		try{
			EmailInfo emailInfo = emailContext.getEmailInfo();
			logger.info("afterThrowable[to=" + emailInfo.getTo()[0]+"]");
			
			EmailTemplateDetailEntity detail = (EmailTemplateDetailEntity) emailContext.getObjAry()[1];
			detail.setCommitStatus(CommitStatusEnum.commitFail.getLabelKey());// 邮件提交状态
			detail.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 邮件发送状态-发送失败
			emailTemplateDetailService.saveOrUpdate(detail);
		}catch(Exception e){
			logger.error("邮件发送出现异常:" + e.getMessage(), e);
		}
	}
	
}
