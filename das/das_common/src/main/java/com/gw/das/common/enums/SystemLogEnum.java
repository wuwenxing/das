package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 摘要：系统日志类型定义
 */
public enum SystemLogEnum implements EnumIntf {
	webSite("官网报表", "webSite"),
	room("直播间报表", "room"),
	trade("交易报表", "trade"),
	appReport("APP报表", "appReport"),
	customerAdvice("客服咨询", "customerAdvice"),
	datasourceReport("数据源报表", "datasourceReport"),
	market("营销工具", "market"),
	system("系统管理", "system"),
	selfHelpReport("自助报表", "selfHelpReport"),
	windControl("风控", "windControl")
	;
	
//	static{
//		Map<String, String> webSite = new HashMap<String, String>();
//		webSite.put("HomePageChartController", "");
//		webSite.put("DasUserBehaviorController", "");
//		webSite.put("DasFlowStatisticsController", "");
//		webSite.put("DasFlowDetailUrlController", "");
//		webSite.put("DasFlowAttributionController", "");
//		webSite.put("DasStatisticsReport", "");
//		webSite.put("DasWebSiteController", "");
//		
//		Map<String, String> room = new HashMap<String, String>();
//		room.put("DasChartRoomController", "");
//		
//		Map<String, String> trade = new HashMap<String, String>();
//		trade.put("TradeController", "");
//		trade.put("TradeIndexController", "");
//		trade.put("tradeAccountDiagnosisController", "");
//		trade.put("tradeBordereauxController", "");
//		
//		Map<String, String> appReport = new HashMap<String, String>();
//		appReport.put("appDataAnalysisController", "");
//		
//		Map<String, String> customerAdvice = new HashMap<String, String>();
//		customerAdvice.put("dasAppOdsController", "");
//
//		Map<String, String> market = new HashMap<String, String>();
//		market.put("BlacklistController", "");
//		market.put("ChannelController", "");
//		market.put("DasUserInfoController", "");
//		market.put("DasUserScreenController", "");
//		market.put("EmailController", "");
//		market.put("EmailDetailController", "");
//		market.put("EmailTemplateController", "");
//		market.put("EmailTemplateDetailController", "");
//		market.put("EmailTemplateLogController", "");
//		market.put("SmsConfigController", "");
//		market.put("SmsController", "");
//		market.put("SmsDetailController", "");
//		market.put("SmsTemplateController", "");
//		market.put("SmsTemplateDetailController", "");
//		market.put("SmsTemplateLogController", "");
//		market.put("UserGroupDetailController", "");
//		market.put("UserGroupController", "");
//		market.put("UserGroupLogController", "");
//		market.put("FlowLogController", "");
//		market.put("FlowConfigController", "");
//		market.put("FlowLogDetailController", "");
//		market.put("AccountAnalyzeController", "");
//		
//		Map<String, String> datasourceReport = new HashMap<String, String>();
//		datasourceReport.put("dasAppOdsController", "");
//		
//		Map<String, String> system = new HashMap<String, String>();
//		system.put("LoginController", "");
//		system.put("SystemDictController", "");
//		system.put("SystemLogController", "");
//		system.put("SystemMenuController", "");
//		system.put("SystemRoleController", "");
//		system.put("SystemThreadLogController", "");
//		system.put("SystemUserController", "");
//		system.put("UploadController", "");
//		
//		Map<String, String> selfHelpReport = new HashMap<String, String>();
//		selfHelpReport.put("CboardLoginController", "");
//		
//		Map<String, String> windControl = new HashMap<String, String>();
//		windControl.put("DimAccountBlackListController", "");
//		windControl.put("RiskBlacklistController", "");
//	}
	
	private final String value;
	private final String labelKey;
	SystemLogEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<SystemLogEnum> getList(){
		List<SystemLogEnum> result = new ArrayList<SystemLogEnum>();
		for(SystemLogEnum ae : SystemLogEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public static String format(String labelKey){
		for(SystemLogEnum ae : SystemLogEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	/**
	 * 根据请求路径，获取对应系统模块
	 * @param requestPath
	 * @return
	 */
	public static String getLabelKeyByCon(String requestPath){
		if(StringUtils.isNotBlank(requestPath)){
			String[] args = requestPath.split("/");
			String path = args[0];
			if("HomePageChartController".equals(path)||"DasUserBehaviorController".equals(path)
					||"DasFlowStatisticsController".equals(path)||"DasFlowDetailUrlController".equals(path)
					||"DasFlowAttributionController".equals(path)||"DasStatisticsReport".equals(path)
					||"DasWebSiteController".equals(path)
					){
				return SystemLogEnum.webSite.getLabelKey();
			}else if("DasChartRoomController".equals(path)){
				return SystemLogEnum.room.getLabelKey();
			}else if("TradeController".equals(path)||"tradeAccountDiagnosisController".equals(path)
					||"tradeBordereauxController".equals(path)
					||"TradeIndexController".equals(path)){
				return SystemLogEnum.trade.getLabelKey();
			}else if("BlacklistController".equals(path)||"ChannelController".equals(path)
					||"DasUserInfoController".equals(path)||"DasUserScreenController".equals(path)
					||"EmailController".equals(path)||"EmailDetailController".equals(path)
					||"EmailTemplateController".equals(path)||"EmailTemplateDetailController".equals(path)
					||"EmailTemplateLogController".equals(path)||"SmsConfigController".equals(path)
					||"SmsController".equals(path)||"SmsDetailController".equals(path)
					||"SmsTemplateController".equals(path)||"SmsTemplateDetailController".equals(path)
					||"SmsTemplateLogController".equals(path)||"TagController".equals(path)
					||"UserGroupController".equals(path)||"UserGroupDetailController".equals(path)
					||"UserGroupLogController".equals(path)||"FlowConfigController".equals(path)
					||"FlowLogController".equals(path)||"FlowLogDetailController".equals(path)
					||"AccountAnalyzeController".equals(path)
					){
				return SystemLogEnum.market.getLabelKey();
			}else if("appDataAnalysisController".equals(path)
					){
				return SystemLogEnum.appReport.getLabelKey();
			}else if("CustConsultation".equals(path)
					){
				return SystemLogEnum.customerAdvice.getLabelKey();
			}else if("dasAppOdsController".equals(path)
					){
				return SystemLogEnum.datasourceReport.getLabelKey();
			}else if("LoginController".equals(path)||"SystemDictController".equals(path)
					||"SystemLogController".equals(path)||"SystemMenuController".equals(path)
					||"SystemRoleController".equals(path)||"SystemThreadLogController".equals(path)
					||"SystemUserController".equals(path)||"UploadController".equals(path)
					){
				return SystemLogEnum.system.getLabelKey();
			}else if("CboardLoginController".equals(path)
					){
				return SystemLogEnum.selfHelpReport.getLabelKey();
			}else if("DimAccountBlackListController".equals(path)||"RiskBlacklistController".equals(path)
					){
				return SystemLogEnum.windControl.getLabelKey();
			}
		}
		return "";
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
