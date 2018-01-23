package com.gw.das.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParamUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ParamUtils.class);

	/**
	 * 将url参数转换成map
	 * 
	 * @param param
	 *            aa=11&bb=22&cc=33
	 * @return
	 */
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			int start = params[i].indexOf("=");
			String key = params[i].substring(0, start);
			String value = params[i].substring(start + 1);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * 将map转换成url
	 * 
	 * @param map
	 * @return
	 */
	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}
	
	public static void main(String[] args) {
		String param = "data={\"userId\":\"GC771F44792000001768CFE8118B8106C\",\"userTel\":\"13977373648\",\"userType\":4,\"userIp\":\"111.58.128.84\",\"userName\":\"柔情\",\"roomName\":\"直播大厅\",\"userSource\":\"web\",\"useEquipment\":\"Mozilla/5.0 (Windows NT 6.1; Trident/7.0; rv:11.0) like Gecko\",\"tradingAccount\":\"\",\"tradingPlatform\":\"\",\"platformType\":0,\"businessPlatform\":2,\"operateEntrance\":\"web\",\"touristId\":\"visitor_59567679\",\"roomId\":\"studio_3\",\"sessionId\":\"wih16EhHtjogQ1E8KyiQjIv1WAuto99Y\",\"nickName\":\"柔情亮剑\",\"email\":\"\",\"courseId\":\"2017-03-31_08:30_studio_3\",\"courseName\":\"金日前瞻_李呱呱_视频直播\",\"teacherId\":\"tonylee\",\"teacherName\":\"李呱呱\",\"requestParams\":\"utm_source=pn11\"}";
		Map<String, Object> resultMap = ParamUtils.getUrlParams(param);
		String data = "";
		String callback = "";
		if(null != resultMap){
			if(null != resultMap.get("data")){
				data = resultMap.get("data") + "";
			}
			if(null != resultMap.get("callback")){
				callback = resultMap.get("callback") + "";
			}
		}
		logger.info(data);
		logger.info(callback);
	}
	
}
