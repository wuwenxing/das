package com.gw.das.business.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 签名生成与验证工具类
 * @author wayne
 *
 */
public class SignUtil {

	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

	/**运营平台sid、token*/
	public static String GW_SID = "fa573c78eaa8402cb6c84dabfcce7150";
	public static String GW_TOKEN = "8867af2616da47d7927ff0df7ea60660";
	
	/**外汇平台sid、token*/
	public static String FX_SID = "fa573c78eaa8402cb6c84dabfcce7158";
	public static String FX_TOKEN = "8867af2616da47d7927ff0df7ea60668";
	
	/**贵金属平台sid、token*/
	public static String PM_SID = "fa573c78eaa8402cb6c84dabfcce7159";
	public static String PM_TOKEN = "8867af2616da47d7927ff0df7ea60669";
	
	/**恒信平台sid、token*/
	public static String HX_SID = "fa573c78eaa8402cb6c84dabfcce7160";
	public static String HX_TOKEN = "8867af2616da47d7927ff0df7ea60670";
	
	/**创富平台sid、token*/
	public static String CF_SID = "fa573c78eaa8402cb6c84dabfcce7161";
	public static String CF_TOKEN = "8867af2616da47d7927ff0df7ea60671";
	
	/**
	 * 获取时间戳
	 */
	public static String getTimestamp() {
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		return timestamp;
	}
	
	/**
	 * 生成签名加密串
	 */
	public static String getSign(String timestamp, String sid) {
		if(StringUtils.isNotBlank(sid)){
			if(sid.equals(SignUtil.GW_SID)){
				return DigestUtils.md5Hex(SignUtil.GW_SID + SignUtil.GW_TOKEN + timestamp);
			}else if(sid.equals(SignUtil.FX_SID)){
				return DigestUtils.md5Hex(SignUtil.FX_SID + SignUtil.FX_TOKEN + timestamp);
			}else if(sid.equals(SignUtil.PM_SID)){
				return DigestUtils.md5Hex(SignUtil.PM_SID + SignUtil.PM_TOKEN + timestamp);
			}else if(sid.equals(SignUtil.HX_SID)){
				return DigestUtils.md5Hex(SignUtil.HX_SID + SignUtil.HX_TOKEN + timestamp);
			}else if(sid.equals(SignUtil.CF_SID)){
				return DigestUtils.md5Hex(SignUtil.CF_SID + SignUtil.CF_TOKEN + timestamp);
			}
		}
		return "";
	}

	/**
	 * 验证签名
	 */
	public static boolean validate(String timestamp, String sign, String sid) throws Exception {
		logger.info("签权验证开始[timestamp=" + timestamp + ", sign=" + sign + ", sid=" + sid + "]");
		if (StringUtils.isNotBlank(timestamp) && StringUtils.isNotBlank(sign) && StringUtils.isNotBlank(sid)) {
			String serverSign = SignUtil.getSign(timestamp, sid);
			if (sign.equals(serverSign)) {
				logger.info("签权验证成功");
				return true;
			}
		}
		logger.info("签权验证失败");
		return false;
	}
	
	public static void main(String[] args) {
		logger.info(SignUtil.getSign("20161008140800", SignUtil.GW_SID));
		logger.info(SignUtil.getSign("20161008140800", SignUtil.FX_SID));
		logger.info(SignUtil.getSign("20161008140800", SignUtil.PM_SID));
		logger.info(SignUtil.getSign("20161008140800", SignUtil.HX_SID));
		logger.info(SignUtil.getSign("20161008140800", SignUtil.CF_SID));
	}
	
}
