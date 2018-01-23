package com.gw.das.common.flume;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.flume.event.JSONEvent;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.gw.das.common.enums.MessageTypeEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * Flume接收数据源工具类
 * 
 * @author kirin.guan
 */
public class FlumeSendMsg {

	final static Logger logger = Logger.getLogger(FlumeSendMsg.class);

	private static String flumeWeb = "";
	private static String flumeRoom = "";
	private static String flumeDeposit = "";
	private static String flumeAppData = "";

	static {
		flumeWeb = SystemConfigUtil.getProperty(SystemConfigEnum.flumeWeb);
		flumeRoom = SystemConfigUtil.getProperty(SystemConfigEnum.flumeRoom);
		flumeDeposit = SystemConfigUtil.getProperty(SystemConfigEnum.flumeDeposit);
		flumeAppData = SystemConfigUtil.getProperty(SystemConfigEnum.flumeAppData);
	}

	/**
	 * Map<String,String> head = new HashMap<String,String>();
	 * head.put("h1","v1"); head.put("h2","v2"); head.put("h3","v3");
	 * head.put("h4","v4"); String body =
	 * "\"userid\":\"1234\",\"username\":\"jimmy1\"";
	 * httpSendMsg.SendMessage("http://192.168.35.238:5555",head,body);
	 * 
	 * @param sendUrl
	 *            发送的url及端口 如 "http://192.168.35.238:5555"
	 * @param head
	 *            若无，可以为 null
	 * @param content
	 *            发送内容
	 * @throws Exception
	 */
	public static void sendMessage(String content, String messageType) throws Exception {
		// String url = sendUrl;//"https://selfsolve.apple.com/wcResults.do";
		try {
			Map<String, String> head = new HashMap<String, String>();
			head.put("h1", "v1");
			head.put("h2", "v2");
			head.put("h3", "v3");
			head.put("h4", "v4");

			HttpPost post = null;
			if (messageType.equals(MessageTypeEnum.web.getLabelKey())) {
				post = new HttpPost(flumeWeb);
			} else if (messageType.equals(MessageTypeEnum.room.getLabelKey())) {
				post = new HttpPost(flumeRoom);
			} else if (messageType.equals(MessageTypeEnum.deposit.getLabelKey())) {
				post = new HttpPost(flumeDeposit);
			} else if (messageType.equals(MessageTypeEnum.app.getLabelKey())) {
				post = new HttpPost(flumeAppData);
			}
			
			// 跨域设置
			post.addHeader("Access-Control-Allow-Origin", "*");
			post.addHeader("Access-Control-Allow-Headers", "X-Requested-With");
			post.addHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
			post.addHeader("X-Powered-By", "3.2.1");
			post.addHeader("P3P", "CP=CAO PSA OUR");// 处理ie跨域问题
			post.addHeader("Content-Type", "text/html;charset=utf-8");

			JSONEvent je = new JSONEvent();

			String body = content;
			je.setHeaders(head);
			je.setBody(body.getBytes());

			String sendContent = new GsonBuilder().disableHtmlEscaping().create().toJson(je, JSONEvent.class);
			logger.debug("send post:" + sendContent);

			post.setEntity(new StringEntity("[" + sendContent + "]", "UTF-8"));
			HttpResponse response = HttpUtil.getInstance().getHttpClient().execute(post);

			logger.debug("Post parameters : " + post.getEntity());
			logger.debug("Response Code : " + response.getStatusLine().getStatusCode());

			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			logger.debug(result.toString());
		} catch (Exception e) {
			logger.error("向flume发送数据异常:" + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		Map<String, String> head = new HashMap<String, String>();
		head.put("h1", "v1");
		head.put("h2", "v2");
		head.put("h3", "v3");
		head.put("h4", "v4");
		String body = "\"userid\":\"1234\",\"username\":\"jimmy1\"";
		FlumeSendMsg.sendMessage(body, "WEB");
	}

}
