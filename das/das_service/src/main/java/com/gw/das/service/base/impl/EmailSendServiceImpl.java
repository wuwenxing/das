package com.gw.das.service.base.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.email.EmailInfo;
import com.gw.das.common.email.EmailListener;
import com.gw.das.common.email.EmailServer;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;
import com.gw.das.dao.market.entity.EmailEntity;
import com.gw.das.dao.market.entity.EmailTemplateEntity;
import com.gw.das.dao.market.entity.EmailTemplateLogEntity;
import com.gw.das.service.base.EmailSendService;
import com.gw.das.service.market.AccountAnalyzeService;

@Service
public class EmailSendServiceImpl implements EmailSendService {

	private static final Logger logger = LoggerFactory.getLogger(EmailSendServiceImpl.class);

	@Autowired
	@Qualifier("emailListenerImpl")
	private EmailListener emailListener;
	@Autowired
	@Qualifier("emailTemplateListenerImpl")
	private EmailListener emailTemplateListener;
	@Autowired
	@Qualifier("hxEmailListenerImpl")
	private EmailListener hxEmailListener;
	@Autowired
	@Qualifier("hxEmailTemplateListenerImpl")
	private EmailListener hxEmailTemplateListener;
	@Autowired
	@Qualifier("accountAnalyzeEmailTemplateListenerImpl")
	private EmailListener accountAnalyzeEmailTemplateListener;
	@Autowired
	@Qualifier("hxAccountAnalyzeEmailTemplateListenerImpl")
	private EmailListener hxAccountAnalyzeEmailTemplateListener;
	@Autowired
	private AccountAnalyzeService accountAnalyzeService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	
	/**
	 * 开启异步线程发送
	 */
	public void asynThreading(final String title, final String content, final EmailTemplateEntity templateEntity,
			final EmailTemplateLogEntity logEntity) {
		new Thread(new Runnable() {
			public void run() {
				try {
					send(title, content, templateEntity, logEntity);
				} catch (Exception e) {
					logger.error("开启异步线程发送error", e);
				}
			}
		}).start();
	}

	/**
	 * 开启异步线程发送
	 */
	public void asynThreading(final EmailEntity emailEntity) {
		new Thread(new Runnable() {
			public void run() {
				try {
					send(emailEntity);
				} catch (Exception e) {
					logger.error("开启异步线程发送error", e);
				}
			}
		}).start();
	}

	/**
	 * 开启异步线程发送
	 */
	public void asynThreading(final String batchIds, final EmailTemplateEntity template) {
		threadPoolTaskExecutor.execute(new sendEmailThread(accountAnalyzeService, template, batchIds, UserContext.get().getLoginName(), UserContext.get().getLoginIp(), UserContext.get().getCompanyId()){});
	}
	
	public void send(String title, String content, EmailTemplateEntity template, EmailTemplateLogEntity reqLog) {
		// 接收的邮箱
		String emails = reqLog.getLegalEmails();
		String[] emailAry = emails.split(",");

		// 邮件实体封装
		// 根据业务平台，使用不同发送邮箱
		String from = "";
		Long companyId = template.getCompanyId();
		if (companyId == CompanyEnum.fx.getLabelKeyLong()) {
			from = SystemConfigUtil.getProperty(SystemConfigEnum.FxMailUserName);
		} else if(companyId == CompanyEnum.pm.getLabelKeyLong()){
			from = SystemConfigUtil.getProperty(SystemConfigEnum.PmMailUserName);
		} else if(companyId == CompanyEnum.hx.getLabelKeyLong()){
			from = "cs@hx9999.com";
		}

		List<EmailInfo> emailInfoList = new ArrayList<EmailInfo>();
		for (String email : emailAry) {
			EmailInfo emailInfo = new EmailInfo();
			emailInfo.setTemplateId(template.getTemplateId());
			emailInfo.setTitle(title);
			emailInfo.setContent(content);
			emailInfo.setFrom(from);
			emailInfo.setTo(new String[] { email });
			emailInfo.setCompanyId(template.getCompanyId());
			emailInfoList.add(emailInfo);
		}
		// 邮件批量发送-并添加监听器-统计发送数量及状态
		EmailServer emailServer = new EmailServer();
		if(companyId == CompanyEnum.hx.getLabelKeyLong()){
			emailServer.addEmailListener(hxEmailTemplateListener);
		}else{
			emailServer.addEmailListener(emailTemplateListener);
		}
		emailServer.send(emailInfoList);
	}
	
	public void send(EmailEntity emailEntity) {
		String content = emailEntity.getContent();
		// 接收的邮箱
		String emails = emailEntity.getLegalEmails();
		if(emailEntity.isFailEmailsFlag()){// 给发送失败的邮箱再次发送
			emails = emailEntity.getFailEmails();
		}
		String[] emailAry = emails.split(",");

		// 邮件实体封装
		// 根据业务平台，使用不同发送邮箱
		String from = "";
		Long companyId = emailEntity.getCompanyId();
		if (companyId == CompanyEnum.fx.getLabelKeyLong()) {
			from = SystemConfigUtil.getProperty(SystemConfigEnum.FxMailUserName);
		} else if(companyId == CompanyEnum.pm.getLabelKeyLong()){
			from = SystemConfigUtil.getProperty(SystemConfigEnum.PmMailUserName);
		} else if(companyId == CompanyEnum.hx.getLabelKeyLong()){
			from = "cs@hx9999.com";
		}

		List<EmailInfo> emailInfoList = new ArrayList<EmailInfo>();
		for (String email : emailAry) {
			EmailInfo emailInfo = new EmailInfo();
			emailInfo.setTemplateId(emailEntity.getEmailId());
			emailInfo.setTitle(emailEntity.getTitle());
			emailInfo.setContent(content);
			emailInfo.setFrom(from);
			emailInfo.setTo(new String[] { email });
			emailInfo.setCompanyId(emailEntity.getCompanyId());
			emailInfoList.add(emailInfo);
		}
		// 邮件批量发送-并添加监听器-统计发送数量及状态
		EmailServer emailServer = new EmailServer();
		if(companyId == CompanyEnum.hx.getLabelKeyLong()){
			emailServer.addEmailListener(hxEmailListener);
		}else{
			emailServer.addEmailListener(emailListener);
		}
		emailServer.send(emailInfoList);
	}

	/**
	 * 发送账号诊断报告邮件
	 */
	private class sendEmailThread implements Runnable {
		private AccountAnalyzeService accountAnalyzeService;
		private EmailTemplateEntity template;
        private String batchIds;
        private String loginName;
        private String loginIp;
        private Long companyId;
        private sendEmailThread(AccountAnalyzeService accountAnalyzeService, EmailTemplateEntity template, String batchIds, String loginName, String loginIp, Long companyId) {
            super();
            this.accountAnalyzeService = accountAnalyzeService;
            this.template = template;
            this.batchIds = batchIds;
            this.loginName = loginName;
            this.loginIp = loginIp;
            this.companyId = companyId;
        }
        @Override
        public void run() {
        	// 依次发送邮件
        	try {
				// 根据业务平台，使用不同发送邮箱
				String from = "";
				if (companyId == CompanyEnum.fx.getLabelKeyLong()) {
					from = SystemConfigUtil.getProperty(SystemConfigEnum.FxMailUserName);
				} else if(companyId == CompanyEnum.pm.getLabelKeyLong()){
					from = SystemConfigUtil.getProperty(SystemConfigEnum.PmMailUserName);
				} else if(companyId == CompanyEnum.hx.getLabelKeyLong()){
					from = "cs@hx9999.com";
				}
        		
        		List<EmailInfo> emailInfoList = new ArrayList<EmailInfo>();
        		String[] batchIdArray = batchIds.split(",");
        		for(int i=0; i<batchIdArray.length; i++){
        			String batchId = batchIdArray[i];
        			if(StringUtils.isNotBlank(batchId)){
            			AccountAnalyzeEntity entity = accountAnalyzeService.findById(Long.parseLong(batchId));
            			if(null != entity && !SendStatusEnum.sendSuccess.getLabelKey().equals(entity.getSendStatus())){
            				StringBuffer sb = new StringBuffer();
            				String path = entity.getPath();
            				if(StringUtils.isNotBlank(path)){
            					File file = new File(path);
            					//构造一个BufferedReader类来读取文件
            					@SuppressWarnings("resource")
            					BufferedReader br = new BufferedReader(new FileReader(file));
            					String s = null;
            					while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            						sb.append(s);
            					}
            					// 接收的邮箱
            					String email = entity.getEmail();
            					// 邮件实体封装
            					EmailInfo emailInfo = new EmailInfo();
            					emailInfo.setTemplateId(entity.getBatchId());
            					emailInfo.setTitle(template.getTitle());
            					emailInfo.setContent(sb.toString());
            					emailInfo.setFrom(from);
            					emailInfo.setTo(new String[] { email });
            					emailInfo.setLoginIp(loginIp);
            					emailInfo.setLoginName(loginName);
            					emailInfo.setCompanyId(companyId);
            					emailInfoList.add(emailInfo);
            				}
            			}
        			}
        		}
				// 邮件批量发送-并添加监听器-统计发送数量及状态
				EmailServer emailServer = new EmailServer();
				if(companyId == CompanyEnum.hx.getLabelKeyLong()){
					emailServer.addEmailListener(hxAccountAnalyzeEmailTemplateListener);
				}else{
					emailServer.addEmailListener(accountAnalyzeEmailTemplateListener);
				}
				emailServer.send(emailInfoList);
			} catch (Exception e) {
				logger.error("系统出现异常:" + e.getMessage(), e);
			}
        }
    }
	
}
