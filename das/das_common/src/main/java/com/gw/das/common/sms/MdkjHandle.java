package com.gw.das.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.SystemConfigUtil;

public class MdkjHandle {

	private static final Logger logger = LoggerFactory.getLogger(MdkjHandle.class);
	// 开发者账号信息 url\sid\token\resDataType
	private static String url = "";
	private static String sid = "";
	private static String token = "";
	private static String resDataType = "";
	// 获取开发者账号信息
	private static String operation_1 = "/query/accountInfo";
	// 短信邮会员营销短信接口
	private static String operation_2 = "/affMarkEmailSMS/sendEmailSMS";
	// 会员营销短信接口
	private static String operation_3 = "/affMarkSMS/sendSMS";
	// 短信邮验证码通知短信接口
	private static String operation_4 = "/industryEmailSMS/sendEmailSMS";
	// 验证码通知短信接口
	private static String operation_5 = "/industrySMS/sendSMS";
	// 语音验证码接口
	private static String operation_6 = "/call/voiceCode";

	static {
		url = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjUrl);
		sid = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjSid);
		token = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjToken);
		resDataType = SystemConfigUtil.getProperty(SystemConfigEnum.SmsMdkjResDataType);
	}

	/**
	 * Test
	 */
	public static void main(String[] args) {
		// 获取开发者账号信息
		// MdkjHandle.accountInfo();
		// 验证码通知短信接口
		// MdkjHandle.industrySMS();
		// 会员营销短信接口
		MdkjHandle.affMarkSMS();
		// 短信邮验证码通知短信接口
		// MdkjHandle.industryEmailSMS();
		// 短信邮验证码通知短信接口
		// MdkjHandle.affMarkEmailSMS();
		// 语音验证码
		// MdkjHandle.voiceCode();
	}

	/**
	 * 获取开发者账号信息
	 */
	public static void accountInfo() {
		String url = MdkjHandle.url + operation_1;
		String body = "accountSid=" + sid + MdkjHandle.createCommonParam();
		// 提交请求
		String result = MdkjHandle.post(url, body);
		logger.info("result:" + result);
	}

	/**
	 * 短信邮会员营销短信
	 */
	public static void affMarkEmailSMS() {
		String smsContent = "【秒嘀科技】您的优惠券就快过期啦！不想白白浪费，就快来使用吧！戳： m.miaodiyun.com 使用！回TD退订 。";
		String to = "13760291376";
		String url = MdkjHandle.url + operation_2;
		String body = "accountSid=" + sid + "&to=" + to + "&smsContent=" + smsContent
				+ MdkjHandle.createCommonParam();
		// 提交请求
		String result = MdkjHandle.post(url, body);
		logger.info("result:" + result);
	}

	/**
	 * 会员营销短信
	 */
	public static void affMarkSMS() {
		String smsContent = "【金道】引爆黄金跌势，看本周大非农如何演绎！退订回N";
		String to = "13760291376";
		String url = MdkjHandle.url + operation_3;
		String body = "accountSid=" + sid + "&to=" + to + "&smsContent=" + smsContent
				+ MdkjHandle.createCommonParam();
		// 提交请求
		String result = MdkjHandle.post(url, body);
		logger.info("result:" + result);
	}

	/**
	 * 短信邮验证码通知短信
	 */
	public static void industryEmailSMS() {
		String smsContent = "【秒嘀科技】您在秒嘀注册的验证码为54789。";
		String to = "13760291376";
		String url = MdkjHandle.url + operation_4;
		String body = "accountSid=" + sid + "&to=" + to + "&smsContent=" + smsContent
				+ MdkjHandle.createCommonParam();
		// 提交请求
		String result = MdkjHandle.post(url, body);
		logger.info("result:" + result);
	}

	/**
	 * 验证码通知短信
	 */
	public static void industrySMS() {
		String smsContent = "【秒嘀科技】您的验证码是345678，30分钟输入有效。";
		String to = "13760291376";
		String url = MdkjHandle.url + operation_5;
		String body = "accountSid=" + sid + "&to=" + to + "&smsContent=" + smsContent
				+ MdkjHandle.createCommonParam();
		// 提交请求
		String result = MdkjHandle.post(url, body);
		logger.info("result:" + result);
	}

	/**
	 * 语音验证码
	 */
	public static void voiceCode() {
		String verifyCode = "5678";
		String called = "13760291376";
		String callDisplayNumber = "";
		String playTimes = "2";
		String respUrl = "";
		String url = MdkjHandle.url + operation_6;
		String body = "accountSid=" + sid + "&verifyCode=" + verifyCode + "&called=" + called + "&callDisplayNumber="
				+ callDisplayNumber + "&playTimes=" + playTimes + "&respUrl=" + respUrl
				+ MdkjHandle.createCommonParam();

		// 提交请求
		String result = MdkjHandle.post(url, body);
		logger.info("result:" + result);
	}

	/**
	 * 构造通用参数timestamp、sig和respDataType
	 * 
	 * @return
	 */
	public static String createCommonParam() {
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		// 签名
		String sig = DigestUtils.md5Hex(sid + token + timestamp);
		return "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType=" + resDataType;
	}

	/**
	 * post请求
	 * 
	 * @param url
	 *            功能和操作
	 * @param body
	 *            要post的数据
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, String body) {
		logger.info("url:" + url);
		logger.info("body:" + body);

		String result = "";
		try {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);

			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
//					result += System.lineSeparator();
				}
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 回调测试工具方法
	 * 
	 * @param url
	 * @param reqStr
	 * @return
	 */
	public static String postHuiDiao(String url, String body) {
		String result = "";
		try {
			OutputStreamWriter out = null;
			BufferedReader in = null;
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();

			// 设置连接参数
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(20000);

			// 提交数据
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(body);
			out.flush();

			// 读取返回数据
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			boolean firstLine = true; // 读第一行不加换行符
			while ((line = in.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
//					result += System.lineSeparator();
				}
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
