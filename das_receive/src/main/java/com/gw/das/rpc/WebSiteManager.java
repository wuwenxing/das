package com.gw.das.rpc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.response.PutDataReponseModel;
import com.gw.das.common.server.ThreadServer;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.JsonUtil;
import com.gw.das.entity.ChartRoomBasic;
import com.gw.das.entity.UserTrackBasic;
import com.gw.das.service.RoomService;
import com.gw.das.service.WebSiteService;
import com.gw.das.service.impl.RoomServiceImpl;
import com.gw.das.service.impl.WebSiteServiceImpl;

/**
 * 网站收集数据rpc
 * 
 * @author wayne
 *
 */
public class WebSiteManager extends ManagerImpl {

	private static final Logger logger = LoggerFactory.getLogger(WebSiteManager.class);

	public String setData(String jsonStr) throws Exception {
		// jsonStr格式
		// {"userId":"GC6EA7D7DD2800001B3B351001540DA20","visitTime":"2016-04-1203:09:23","behaviorDetail":"N4121524","behaviorType":"4","ip":"219.90.123.138","utmcsr":"pb11","utmctr":"www.24k.hk","utmccn":"V1_82v3B","utmcct":"mian_title","utmcmd":"brand","url":"http://testweb1.24k.hk:9000/tw/realaccount_second.html","platformType":0,"businessPlatform":2,"operationTel":"86--13652395323","operationEmail":"sping.peng@gwtsz.net","advisoryType":"1"}
		logger.debug(jsonStr);
		PutDataReponseModel responseBody = new PutDataReponseModel();
		try {
			UserTrackBasic requestBody = JsonUtil.jsonToObject(jsonStr, UserTrackBasic.class);
			if (null == requestBody || null == requestBody.getBusinessPlatform()) {
				logger.error("数据或平台类型为空" + ",[jsonStr="+jsonStr+"]");
				responseBody.setRet_code("1");
				responseBody.setRet_msg("数据或平台类型为空");
				logger.debug("返回消息:(" + JacksonUtil.toJSon(responseBody) + ")");
				return "(" + JacksonUtil.toJSon(responseBody) + ")";
			}
			
			// 执行逻辑
			WebSiteService webSiteService = getService(WebSiteServiceImpl.class);
			ThreadServer<UserTrackBasic> threadServer = new ThreadServer<UserTrackBasic>();
			threadServer.setListener(webSiteService);
			threadServer.excute(requestBody);
		} catch (Exception e) {
			logger.error("出现异常:" + e.getMessage() + ",[jsonStr="+jsonStr+"]", e);
			responseBody.setRet_code("1");
			responseBody.setRet_msg("出现异常:" + e.getMessage());
		}
		// 返回消息
		logger.debug("返回消息:(" + JacksonUtil.toJSon(responseBody) + ")");
		return "(" + JacksonUtil.toJSon(responseBody) + ")";
	}

	public String setDataCallback(String jsonStr) throws Exception {
		// jsonStr两种格式
		// 1、{"data":"{\"userId\":\"wayne\",\"businessPlatform\",\"2\"}","callback":"funcs"}
		// 2、data={"behaviorType":1,"url":"https://gts2admin.hx9999.com/fundDepositOnline.do","userId":"HC7785103B990000139F0AA7013061553","utmcsr":"gts2app_android","utmctr":"android","utmccn":"gts2app_caidan","utmcct":"deposit","utmcmd":"referral","platformType":1,"businessPlatform":3,"browser":"Mozilla/5.0+(Linux;+Android+6.0.1;+KIW-AL10+Build/HONORKIW-AL10;+wv)+AppleWebKit/537.36+(KHTML,+like+Gecko)+Version/4.0+Chrome/53.0.2785.49+Mobile+MQQBrowser/6.2+TBS/043115+Safari/537.36","prevUrl":"https://gts2admin.hx9999.com/fundDepositOnline.do?utm_source=gts2app_android&utm_medium=referral&utm_term=android&utm_content=deposit&utm_campaign=gts2app_caidan","ip":"42.80.204.194"}&callback=callback
		logger.debug(jsonStr);
		PutDataReponseModel responseBody = new PutDataReponseModel();
		String data = "";
		String callback = "";
		try {
			Map<String, String> resultMap = new HashMap<String, String>();
			if(jsonStr.startsWith("data=")){
				int start = jsonStr.indexOf("data=");
				int end = jsonStr.indexOf("&callback=");
				String data_ = jsonStr.substring(start + 5, end);
				String callback_ = jsonStr.substring(end + 10);
				resultMap.put("data", data_);
				resultMap.put("callback", callback_);
			}else{
				resultMap = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {});
			}
			
			if(null != resultMap){
				if(null != resultMap.get("data")){
					data = resultMap.get("data");
				}
				if(null != resultMap.get("callback")){
					callback = resultMap.get("callback");
				}
			}
			
			UserTrackBasic requestBody = JsonUtil.jsonToObject(data, UserTrackBasic.class);
			if (null == requestBody || null == requestBody.getBusinessPlatform()) {
				logger.error("数据或平台类型为空" + ",[jsonStr="+jsonStr+"]");
				responseBody.setRet_code("1");
				responseBody.setRet_msg("数据或平台类型为空");
				// 返回消息
				if (StringUtils.isNotBlank(callback)) {
					logger.debug("返回消息:" + callback + "(" + JacksonUtil.toJSon(responseBody) + ")");
					return callback + "(" + JacksonUtil.toJSon(responseBody) + ")";
				} else {
					logger.debug("返回消息:(" + JacksonUtil.toJSon(responseBody) + ")");
					return "(" + JacksonUtil.toJSon(responseBody) + ")";
				}
			}

			// 执行逻辑
			WebSiteService webSiteService = getService(WebSiteServiceImpl.class);
			ThreadServer<UserTrackBasic> threadServer = new ThreadServer<UserTrackBasic>();
			threadServer.setListener(webSiteService);
			threadServer.excute(requestBody);
		} catch (Exception e) {
			logger.error("出现异常:" + e.getMessage() + ",[jsonStr="+jsonStr+"]", e);
			responseBody.setRet_code("1");
			responseBody.setRet_msg("出现异常:" + e.getMessage());
		}
		// 返回消息
		if (StringUtils.isNotBlank(callback)) {
			logger.debug("返回消息:" + callback + "(" + JacksonUtil.toJSon(responseBody) + ")");
			return callback + "(" + JacksonUtil.toJSon(responseBody) + ")";
		} else {
			logger.debug("返回消息:(" + JacksonUtil.toJSon(responseBody) + ")");
			return "(" + JacksonUtil.toJSon(responseBody) + ")";
		}
	}
	
	public String setChartRoomData(String jsonStr) throws Exception {
		// jsonStr格式
		// data={"userId":"C776779282A000011B316BD04B2F1A6B","userTel":"","userType":1,"userIp":"","userName":"","roomName":"GWFX在线教育","userSource":"web","useEquipment":"Mozilla/5.0 (iPhone; CPU iPhone OS 10_2_1 like Mac OS X) AppleWebKit/602.4.6 (KHTML, like Gecko) Mobile/14D27","tradingAccount":"","tradingPlatform":"","platformType":1,"businessPlatform":1,"operateEntrance":"web","touristId":"visitor_26907940","roomId":"fxstudio_11","sessionId":"aNfG-TAdeWVL1Cy4CHRjhb5WrgiiKFQJ","nickName":"游客_26907940","email":"","courseId":"","courseName":"","teacherId":"","teacherName":"","requestParams":"","operationType":6}&callback=?
		logger.debug(jsonStr);
		PutDataReponseModel responseBody = new PutDataReponseModel();
		String data = "";
		String callback = "";
		try {
			Map<String, String> resultMap = new HashMap<String, String>();
			if(jsonStr.startsWith("data=")){
				int start = jsonStr.indexOf("data=");
				int end = jsonStr.indexOf("&callback=");
				String data_ = jsonStr.substring(start + 5, end);
				String callback_ = jsonStr.substring(end + 10);
				resultMap.put("data", data_);
				resultMap.put("callback", callback_);
			}else{
				resultMap = JacksonUtil.readValue(jsonStr, new TypeReference<Map<String, String>>() {});
			}
			
			if(null != resultMap){
				if(null != resultMap.get("data")){
					data = resultMap.get("data");
				}
				if(null != resultMap.get("callback")){
					callback = resultMap.get("callback");
				}
			}
			
			ChartRoomBasic requestBody = JsonUtil.jsonToObject(data, ChartRoomBasic.class);
			if (null == requestBody || null == requestBody.getBusinessPlatform()) {
				logger.error("数据或平台类型为空" + ",[jsonStr="+jsonStr+"]");
				responseBody.setRet_code("1");
				responseBody.setRet_msg("数据或平台类型为空");
				// 返回消息
				if (StringUtils.isNotBlank(callback)) {
					logger.debug("返回消息:" + callback + "(" + JacksonUtil.toJSon(responseBody) + ")");
					return callback + "(" + JacksonUtil.toJSon(responseBody) + ")";
				} else {
					logger.debug("返回消息:(" + JacksonUtil.toJSon(responseBody) + ")");
					return "(" + JacksonUtil.toJSon(responseBody) + ")";
				}
			}
			if (StringUtils.isBlank(requestBody.getUserId())) {
				logger.error("userId为空" + ",[jsonStr="+jsonStr+"]");
				responseBody.setRet_code("1");
				responseBody.setRet_msg("userId为空");
				// 返回消息
				if (StringUtils.isNotBlank(callback)) {
					logger.debug("返回消息:" + callback + "(" + JacksonUtil.toJSon(responseBody) + ")");
					return callback + "(" + JacksonUtil.toJSon(responseBody) + ")";
				} else {
					logger.debug("返回消息:(" + JacksonUtil.toJSon(responseBody) + ")");
					return "(" + JacksonUtil.toJSon(responseBody) + ")";
				}
			}
			
			// 执行逻辑
			RoomService roomService = getService(RoomServiceImpl.class);
			ThreadServer<ChartRoomBasic> threadServer = new ThreadServer<ChartRoomBasic>();
			threadServer.setListener(roomService);
			threadServer.excute(requestBody);
		} catch (Exception e) {
			logger.error("出现异常:" + e.getMessage() + ",[jsonStr="+jsonStr+"]", e);
			responseBody.setRet_code("1");
			responseBody.setRet_msg("出现异常:" + e.getMessage());
		}
		// 返回消息
		if (StringUtils.isNotBlank(callback) && !"?".equals(callback.trim())) {
			logger.debug("返回消息:" + callback + "(" + JacksonUtil.toJSon(responseBody) + ")");
			return callback + "(" + JacksonUtil.toJSon(responseBody) + ")";
		} else {
			logger.debug("返回消息:(" + JacksonUtil.toJSon(responseBody) + ")");
			return "(" + JacksonUtil.toJSon(responseBody) + ")";
		}
	}
	
}
