package com.gw.das.common.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 短信服务实现类
 * 
 * @author wayne
 */
public class FcySmsServer {
	private static final Logger logger = LoggerFactory.getLogger(FcySmsServer.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	private static String url;// 短信接口地址
	private static String username;// 短信接口用户名
	private static String password;// 短信接口密码
	private static String key;// 短信接口密匙
	private static WebClient client;
	/**
	 * 短信监听器
	 */
	private List<SmsListener> smsListeners = new ArrayList<SmsListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);

		url = SystemConfigUtil.getProperty(SystemConfigEnum.SmsFcyUrl);
		username = SystemConfigUtil.getProperty(SystemConfigEnum.SmsFcyUsername);
		password = SystemConfigUtil.getProperty(SystemConfigEnum.SmsFcyPassword);
		key = SystemConfigUtil.getProperty(SystemConfigEnum.SmsFcyKey);

		client = WebClient.create(url);
		client.type("application/json;charset=UTF-8");// 非常重要
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
					String body = buildSmsMessage(smsInfo);
					Response response = client.post(body);
					smsContext.setFcyResponse(trans(response));
					doAfter(smsContext);
				} catch (Exception e) {
					logger.error("fcy发送短信出现异常", e);
					smsContext.setThrowable(e);
					doAfterThrowable(smsContext);
				}
			}
		});
	}

	/**
	 * 将response转换为FCYSmsResponse实体对象
	 * 
	 * @param response
	 * @return
	 */
	private static FcySmsResponse trans(Response response) {
		try {
			InputStream stream = (InputStream) response.getEntity();
			int available = 0;
			available = stream.available();

			if (available == 0) {
				logger.info("nothing returned, response code: " + response.getStatus());
				return null;
			}
			JsonNode responseNode = objectMapper.readTree(stream);
			String jsonStr = objectMapper.writeValueAsString(responseNode);
			FcySmsResponse fCYSmsResponse = JacksonUtil.readValue(jsonStr, FcySmsResponse.class);
			return fCYSmsResponse;
		} catch (IOException e) {
			logger.info("trans to FCYSmsResponse exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 短信参数封装
	 */
	private String buildSmsMessage(SmsInfo smsInfo) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("tradeNo", smsInfo.getTradeNo());// 必填参数
		parameters.put("userName", username);// 必填参数
		parameters.put("userPassword", password);// 必填参数
		parameters.put("phones", smsInfo.getPhones());
		parameters.put("content", smsInfo.getContent());
		parameters.put("etnumber", "");
		String sign = FcyAESUtil.encrypt(objectMapper.writeValueAsString(parameters), key);
		parameters.put("sign", sign);
		parameters.put("userPassword", FcyAESUtil.MD5(password));// 密码加密

		String body = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(parameters);
		logger.info("发送短信请求报文：[" + body + "]");
		return body;
	}

	/**
	 * 给发送器添加监听器
	 * 
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
