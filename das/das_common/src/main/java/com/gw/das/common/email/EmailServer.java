package com.gw.das.common.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.sms.HxApiResponse;
import com.gw.das.common.sms.HxSidCache;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gwghk.gaims.mail.client.SendingEmailClient;

/**
 * 发送邮件调用Api实现服务类
 * @author wayne
 */
public class EmailServer {
	private static final Logger logger = LoggerFactory.getLogger(EmailServer.class);
	
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	private static String fxGoldEmailApiUrl;
	private static String pmGoldEmailApiUrl;
	private static String fxGoldEmailApiUrl2;
	private static String pmGoldEmailApiUrl2;
	private static String fxUsername;
	private static String fxDisplayUsername;
	private static String pmUsername;
	private static String pmDisplayUsername;
	// 恒信sid,通过接口获取
	private static String hxApiUrl = "";
	// 恒信推送短信和邮件接口url
	private static String sendUrl = "/notice/send/?sid=";
	
	/**
	 * 邮件监听器
	 */
	private List<EmailListener> emailListeners = new ArrayList<EmailListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		fxGoldEmailApiUrl = SystemConfigUtil.getProperty(SystemConfigEnum.FxGoldEmailApiUrl);
		fxUsername = SystemConfigUtil.getProperty(SystemConfigEnum.FxMailUserName);
		fxDisplayUsername = SystemConfigUtil.getProperty(SystemConfigEnum.FxMailDisplayUserName);
		pmGoldEmailApiUrl = SystemConfigUtil.getProperty(SystemConfigEnum.PmGoldEmailApiUrl);
		pmUsername = SystemConfigUtil.getProperty(SystemConfigEnum.PmMailUserName);
		pmDisplayUsername = SystemConfigUtil.getProperty(SystemConfigEnum.PmMailDisplayUserName);
		fxGoldEmailApiUrl2 = SystemConfigUtil.getProperty(SystemConfigEnum.FxGoldEmailApiUrl2);
		pmGoldEmailApiUrl2 = SystemConfigUtil.getProperty(SystemConfigEnum.PmGoldEmailApiUrl2);
		
		hxApiUrl = SystemConfigUtil.getProperties().getProperty(SystemConfigEnum.hxApiUrl.getLabelKey());
		
		logger.info("fxDisplayUsername=" + fxDisplayUsername + ", pmDisplayUsername=" + pmDisplayUsername);
	}

	/**
	 * 发送多条email
	 */
	public void send(List<EmailInfo> emailInfos) {
		for (EmailInfo emailInfo : emailInfos) {
			send(emailInfo);
		}
	}
	
	/**
	 * 发送单条email
	 */
	public void send(final EmailInfo emailInfo) {
		theadPool.execute(new Runnable() {
			public void run() {
				EmailContext emailContext = new EmailContext();
				emailContext.setEmailInfo(emailInfo);
				doBefore(emailContext);
				try {
					// 根据业务平台，使用不同发送邮箱
					Long companyId = emailInfo.getCompanyId();
					if(companyId == 1){
						String username = fxUsername;
						String displayName = "金道环球投资";
						// 引入公共邮件包，调用接口发送
						emailInfo.setFrom(username);
						if("1".equals(emailInfo.getUrlFlag())){
							SendingEmailClient.sendMail(fxGoldEmailApiUrl, displayName, emailInfo.getTitle(), emailInfo.getContent(), null, false, emailInfo.getFrom(), displayName, emailInfo.getTo()[0], null);
						}else{
							SendingEmailClient.sendMail(fxGoldEmailApiUrl2, displayName, emailInfo.getTitle(), emailInfo.getContent(), null, false, emailInfo.getFrom(), displayName, emailInfo.getTo()[0], null);
						}
					}else if(companyId == 2){
						String username = pmUsername;
						String displayName = "金道贵金属";
						// 引入公共邮件包，调用接口发送
						emailInfo.setFrom(username);
						if("1".equals(emailInfo.getUrlFlag())){
							SendingEmailClient.sendMail(pmGoldEmailApiUrl, displayName, emailInfo.getTitle(), emailInfo.getContent(), null, false, emailInfo.getFrom(), displayName, emailInfo.getTo()[0], null);
						}else{
							SendingEmailClient.sendMail(pmGoldEmailApiUrl2, displayName, emailInfo.getTitle(), emailInfo.getContent(), null, false, emailInfo.getFrom(), displayName, emailInfo.getTo()[0], null);
						}
					}else if(companyId == 3){
						String hxSid = HxSidCache.getHxSid();
						String title = emailInfo.getTitle();
						String content = emailInfo.getContent();
						String to = "";
						if(null != emailInfo.getTo() && emailInfo.getTo().length>0){
							to = StringUtil.array2String(emailInfo.getTo());
						}
						String url = EmailServer.hxApiUrl + EmailServer.sendUrl + hxSid;
						
						Map<String, String> parameters = new HashMap<String, String>();
						parameters.put("type", "email");
						parameters.put("to", to);
						parameters.put("title", title);
						parameters.put("content", content);
						// 提交请求
						String result = HttpUtil.getInstance().doPost(url, parameters);
						logger.error("result:" + result);
						HxApiResponse response = JacksonUtil.readValue(result, HxApiResponse.class);
						emailContext.setHxResponse(response);
					}
					doAfter(emailContext);
				} catch (Exception e) {
					logger.error("send email exception: " + e.getMessage(), e);
					emailContext.setThrowable(e);
					doAfterThrowable(emailContext);
				}
			}
		});
	}

	/**
	 * 给发送器添加监听器
	 * @param emailListener
	 */
	public void addEmailListener(EmailListener emailListener) {
		this.emailListeners.add(emailListener);
	}

	private void doBefore(EmailContext emailContext) {
		for (EmailListener emailListener : this.emailListeners) {
			emailListener.updateBefore(emailContext);
		}
	}

	private void doAfter(EmailContext emailContext) {
		for (EmailListener emailListener : this.emailListeners) {
			emailListener.updateAfter(emailContext);
		}
	}

	private void doAfterThrowable(EmailContext emailContext) {
		for (EmailListener emailListener : this.emailListeners) {
			emailListener.updateAfterThrowable(emailContext);
		}
	}
}
