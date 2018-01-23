package com.gw.das.common.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 短信服务实现类
 * 
 * @author wayne
 */
public class HxSmsServer {
	private static final Logger logger = LoggerFactory.getLogger(HxSmsServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	// 恒信sid,通过接口获取
	private static String hxApiUrl = "";
	// 恒信推送短信和邮件接口url
	private static String sendUrl = "/notice/send/?sid=";
	
	/**
	 * 短信监听器
	 */
	private List<SmsListener> smsListeners = new ArrayList<SmsListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		hxApiUrl = SystemConfigUtil.getProperties().getProperty(SystemConfigEnum.hxApiUrl.getLabelKey());
	}

	/**
	 * 发送多条短信
	 */
	public void send(List<SmsInfo> smsInfos) {
		for (SmsInfo smsInfo : smsInfos) {
			send(smsInfo);
		}
	}

	/**
	 * 发送单条短信
	 */
	public void send(final SmsInfo smsInfo) {
		theadPool.execute(new Runnable() {
			public void run() {
				SmsContext smsContext = new SmsContext();
				smsContext.setSmsInfo(smsInfo);
				doBefore(smsContext);
				try {
					if (smsInfo.getCompanyId() == 1) {
						// 该通道为恒信专用的短信通道
						throw new Exception("该通道为恒信专用的短信通道");
					} else if (smsInfo.getCompanyId() == 2){
						// 该通道为恒信专用的短信通道
						throw new Exception("该通道为恒信专用的短信通道");
					} else if (smsInfo.getCompanyId() == 3){
					}
					smsInfo.setHxSid(HxSidCache.getHxSid());
					String smsContent = smsInfo.getContent();
					String to = smsInfo.getPhones();
					
					String url = HxSmsServer.hxApiUrl + HxSmsServer.sendUrl + smsInfo.getHxSid();
					// 提交请求
					Map<String, String> parameters = new HashMap<String, String>();
					parameters.put("type", "sms");
					parameters.put("to", to);
					parameters.put("content", smsContent);
					String result = HttpUtil.getInstance().doPost(url, parameters);
					logger.error("result:" + result);
					HxApiResponse response = JacksonUtil.readValue(result, HxApiResponse.class);
					smsContext.setHxResponse(response);
					doAfter(smsContext);
				} catch (Exception e) {
					logger.error("hx发送短信出现异常", e);
					smsContext.setThrowable(e);
					doAfterThrowable(smsContext);
				}
			}
		});
	}

	/**
	 * 给发送器添加监听器
	 * @param smsListener
	 */
	public void addSmsListener(SmsListener smsListener) {
		this.smsListeners.add(smsListener);
	}

	private void doBefore(SmsContext emailContext) {
		for (SmsListener smsListener : this.smsListeners) {
			smsListener.updateBefore(emailContext);
		}
	}

	private void doAfter(SmsContext smsContext) {
		for (SmsListener smsListener : this.smsListeners) {
			smsListener.updateAfter(smsContext);
		}
	}

	private void doAfterThrowable(SmsContext smsContext) {
		for (SmsListener smsListener : this.smsListeners) {
			smsListener.updateAfterThrowable(smsContext);
		}
	}
}
