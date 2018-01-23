package com.gw.das.common.token;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 验证与获取Token工具类
 * 
 * @author wayne
 *
 */
public class TokenUtil {

	private static Logger logger = LoggerFactory.getLogger(TokenUtil.class);

	/**
	 * 数据平台获取Token的type为固定值
	 */
	public static String serviceScretType = "";

	/**
	 * 通用基础服务平台URL
	 */
	public static String serviceScretUrl = "";

	/**
	 * 获取Token的url
	 */
	public static final String getTokenUrl = "/sac/getToken";

	/**
	 * 验证Token的url
	 */
	public static final String verifyTokenUrl = "/sac/verifyToken";

	static {
		serviceScretUrl = SystemConfigUtil.getProperty(SystemConfigEnum.serviceScretUrl);
		serviceScretType = SystemConfigUtil.getProperty(SystemConfigEnum.serviceScretType);
		logger.info("==>>static[serviceScretUrl={},serviceScretType={}]", serviceScretUrl, serviceScretType);
	}

	/**
	 * 获取Token具体的值,返回String
	 */
	public static String getTokenStr(String appId, String appScret) {
		TokenResult result = getToken(appId, appScret);
		if (null != result && "0".equals(result.getCode())) {
			if (null != result.getData()) {
				return result.getData().getToken();
			}
		}
		return null;
	}

	/**
	 * 获取Token,返回结果对象
	 */
	public static TokenResult getToken(String appId, String appScret) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("appId", appId);
		parameters.put("appScret", appScret);
		// 提交请求
		TokenResult result = null;
		try {
			String jsonStr = HttpUtil.getInstance().doPost(serviceScretUrl + getTokenUrl, parameters);
			logger.info("==>>getToken[jsonStr={}]", jsonStr);
			result = JacksonUtil.readValue(jsonStr, TokenResult.class);
		} catch (IOException e) {
			logger.error("==>>getToken error!", e);
		}
		return result;
	}

	/**
	 * 验证Token,返回boolean
	 */
	public static boolean isCheckToken(String interfaceUrl, String token) {
		VerifyTokenResult result = checkToken(interfaceUrl, token);
		if (null != result && "0".equals(result.getCode())) {
			return true;
		}
		return false;
	}

	/**
	 * 验证Token,返回结果对象
	 */
	public static VerifyTokenResult checkToken(String interfaceUrl, String token) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("type", serviceScretType);
		parameters.put("uri", interfaceUrl);
		parameters.put("token", token);
		// 提交请求
		VerifyTokenResult result = null;
		try {
			String jsonStr = HttpUtil.getInstance().doPost(serviceScretUrl + verifyTokenUrl, parameters);
			logger.info("==>>checkToken[jsonStr={}]", jsonStr);
			result = JacksonUtil.readValue(jsonStr, VerifyTokenResult.class);
		} catch (IOException e) {
			logger.error("==>>checkToken error!", e);
		}
		return result;
	}

}
