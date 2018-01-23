package com.gw.das.common.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.digest.DigestUtils;
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
public class MdkjSmsServer {
	private static final Logger logger = LoggerFactory.getLogger(MdkjSmsServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	// 开发者账号信息 url\sid\token\resDataType
	private static String url = "";
	private static String sid = "";
	private static String token = "";
	private static String resDataType = "";
	// 会员营销短信接口
	private static String operation_3 = "/affMarkSMS/sendSMS";
	/**
	 * 短信监听器
	 */
	private List<SmsListener> smsListeners = new ArrayList<SmsListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		url = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjUrl);
		sid = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjSid);
		token = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjToken);
		resDataType = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjResDataType);
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
					} else if (smsInfo.getCompanyId() == 2){
					} else if (smsInfo.getCompanyId() == 3){
						// 目前恒信不能使用该通道
						throw new Exception("目前恒信不能使用该通道");
					}
					String smsContent = smsInfo.getContent();
					String to = smsInfo.getPhones();
					String url = MdkjSmsServer.url + MdkjSmsServer.operation_3;
					// 提交请求
					Map<String, String> parameters = MdkjSmsServer.createCommonParam();
					parameters.put("accountSid", sid);
					parameters.put("to", to);
					parameters.put("smsContent", smsContent);
					parameters.put("Content", smsContent);
					String result = HttpUtil.getInstance().doPost(url, parameters);
					logger.error("result:" + result);
					MdkjSmsResponse response = JacksonUtil.readValue(result, MdkjSmsResponse.class);
					smsContext.setMdkjResponse(response);
					doAfter(smsContext);
				} catch (Exception e) {
					logger.error("mdkj发送短信出现异常", e);
					smsContext.setThrowable(e);
					doAfterThrowable(smsContext);
				}
			}
		});
	}

	/**
	 * 构造通用参数timestamp、sig和respDataType
	 * 
	 * @return
	 */
	private static Map<String, String> createCommonParam() {
		Map<String, String> parameters = new HashMap<String, String>();
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		// 签名
		String sig = DigestUtils.md5Hex(sid + token + timestamp);
		parameters.put("timestamp", timestamp);
		parameters.put("sig", sig);
		parameters.put("respDataType", resDataType);
		return parameters;
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
