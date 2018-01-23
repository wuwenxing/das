package com.gw.das.common.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 短信服务实现类
 * 
 * @author wayne
 */
public class ZqhlSmsServer {
	private static final Logger logger = LoggerFactory.getLogger(ZqhlSmsServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	// 开发者账号信息
	private static String mainUrl = "";
	private static String spareUrl = "";
	private static String pmUsername = "";
	private static String pmPassword = "";
	private static String fxUsername = "";
	private static String fxPassword = "";
	private static String hxUsername = "";
	private static String hxPassword = "";
	/**
	 * 短信监听器
	 */
	private List<SmsListener> smsListeners = new ArrayList<SmsListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		mainUrl = SystemConfigUtil.getProperty(SystemConfigEnum.smsZqhlMainUrl);
		spareUrl = SystemConfigUtil.getProperty(SystemConfigEnum.smsZqhlSpareUrl);
		pmUsername = SystemConfigUtil.getProperty(SystemConfigEnum.smsPmZqhlUsername);
		pmPassword = SystemConfigUtil.getProperty(SystemConfigEnum.smsPmZqhlPassword);
		fxUsername = SystemConfigUtil.getProperty(SystemConfigEnum.smsFxZqhlUsername);
		fxPassword = SystemConfigUtil.getProperty(SystemConfigEnum.smsFxZqhlPassword);
		logger.info(spareUrl + "," + hxUsername + "," + hxPassword);
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
					String smsContent = smsInfo.getContent();
					String to = smsInfo.getPhones();

					Map<String, String> parameters = new HashMap<String, String>();
					if (smsInfo.getCompanyId() == 1) {
						parameters.put("UserName", ZqhlSmsServer.fxUsername);
						parameters.put("UserPass", ZqhlSmsServer.fxPassword);
						parameters.put("Subid", "");
						parameters.put("Mobile", to);
						parameters.put("Content", smsContent);
					} else if (smsInfo.getCompanyId() == 2){
						parameters.put("UserName", ZqhlSmsServer.pmUsername);
						parameters.put("UserPass", ZqhlSmsServer.pmPassword);
						parameters.put("Subid", "");
						parameters.put("Mobile", to);
						parameters.put("Content", smsContent);
					} else if (smsInfo.getCompanyId() == 3){
						// 目前恒信不能使用该通道
						throw new Exception("目前恒信不能使用该通道");
					}
					// 提交请求
					String result = HttpUtil.getInstance().doPost(ZqhlSmsServer.mainUrl, parameters);
					logger.info("result:" + result);
					// "状态码，响应ID"格式
					String resCode = "";
					String resId = "";
					if(StringUtils.isNotBlank(result)){
						String[] res = result.split(",");
						if(null != res){
							if(res.length == 2){
								resCode = res[0];//状态码
								resId = res[1];//响应ID
							}else if(res.length == 1){
								resCode = res[0];//状态码
							}
						}
					}
					ZqhlSmsResponse response = new ZqhlSmsResponse();
					response.setResCode(resCode);
					response.setResId(resId);
					smsContext.setZqhlSmsResponse(response);
					doAfter(smsContext);
				} catch (Exception e) {
					logger.error("zqhl发送短信出现异常", e);
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
