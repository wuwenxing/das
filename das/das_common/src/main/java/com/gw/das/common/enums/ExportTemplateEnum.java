package com.gw.das.common.enums;

/**
 * 导出模板文件定义
 * @author wayne
 */
public enum ExportTemplateEnum implements EnumIntf {

	systemUserInfo("系统用户列表", "systemUserInfo", "/template/systemUserInfo.xlsx"),
	systemUserInfoAuth("系统用户列表权限", "systemUserInfoAuth", ""),
	blacklistInfo("黑名单列表信息", "blacklistInfo", "/template/blacklistInfo.xlsx"),
	tagListInfo("标签列表信息", "tagListInfo", "/template/tagListInfo.xlsx"),
	businessTagListInfo("业务标签列表信息", "businessTagListInfo", "/template/businessTagListInfo.xlsx"),
	channelListInfo("渠道列表信息", "channelListInfo", "/template/channelListInfo.xlsx"),
	smsTemplateListInfo("短信模板列表信息", "smsTemplateListInfo", "/template/smsTemplateListInfo.xlsx"),
	smsListInfo("短信发送列表信息", "smsListInfo", "/template/smsListInfo.xlsx"),
	emailTemplateListInfo("邮件模板列表信息", "emailTemplateListInfo", "/template/emailTemplateListInfo.xlsx"),
	emailListInfo("邮件发送列表信息", "emailListInfo", "/template/emailListInfo.xlsx"),
	userGroupListInfo("用户分组列表信息", "userGroupListInfo", "/template/userGroupListInfo.xlsx"),
	detailSummaryInfo("行为详细汇总信息", "detailSummaryInfo", "/template/detailSummaryInfo.xlsx"),
	visitDetailInfo("浏览详细信息", "visitDetailInfo", "/template/visitDetailInfo.xlsx"),
	behaviorCount("行为统计", "behaviorCount", "/template/behaviorCount.xlsx"),
	behaviorOpenAccountCount("开户平均数统计", "behaviorOpenAccountCount", "/template/behaviorOpenAccountCount.xlsx"),
	behaviorOpenAccountCountDemo("模拟开户平均数统计", "behaviorOpenAccountCount", "/template/behaviorOpenAccountCountDemo.xlsx"),
	behaviorOpenAccountCountReal("真实开户平均数统计", "behaviorOpenAccountCount", "/template/behaviorOpenAccountCountReal.xlsx"),
	behaviorOpenAccountCountDeposit("入金开户平均数统计", "behaviorOpenAccountCount", "/template/behaviorOpenAccountCountDeposit.xlsx"),
	sourceMediumCount("来源媒介统计", "sourceMediumCount", "/template/sourceMediumCount.xlsx"),
	timeCount("时段统计", "timeCount", "/template/timeCount.xlsx"),
	attributionCount("来源媒介效果", "attributionCount", "/template/attributionCount.xlsx"),
	dasWebLandingpageDetail("Landingpage", "dasWebLandingpageDetail", "/template/website/dasWebLandingpageDetail.xlsx"),
	dasBehaviorEventChannelEffect("渠道效果统计", "dasBehaviorEventChannelEffect", "/template/website/dasBehaviorEventChannelEffect.xlsx"),
	dasBehaviorEventChannelEffectWeek("渠道效果统计", "dasBehaviorEventChannelEffectWeek", "/template/website/dasBehaviorEventChannelEffectWeek.xlsx"),
	loginStatistics("直播间登陆统计", "loginStatistics", "/template/room/loginStatistics.xlsx"),
	visitorList("访客列表", "visitorList", "/template/room/visitorList.xlsx"),
	visitorDetailList("访客明细列表", "visitorDetailList", "/template/room/visitorDetailList.xlsx"),
	statisticsSpeakdays("发言天数列表", "statisticsSpeakdays", "/template/room/statisticsSpeakdays.xlsx"),
	statisticsSpeakCounts("发言次数列表", "statisticsSpeakcounts", "/template/room/statisticsSpeakcounts.xlsx"),
	statisticsLogin("登录数据列表", "statisticsLogin", "/template/room/statisticsLogin.xlsx"),
	statisticsVisitor("访问数据列表", "statisticsVisitor", "/template/room/statisticsVisitor.xlsx"),
	statisticsSpeak("发言数据列表", "statisticsSpeak", "/template/room/statisticsSpeak.xlsx"),
	statisticsLogindays("登录天数数据列表", "statisticsLogindays", "/template/room/statisticsLogindays.xlsx"),
	statisticsVisitcounts("访问次数数据列表", "statisticsVisitcounts", "/template/room/statisticsVisitcounts.xlsx"),
	balanceDailyList("结算日报表", "balanceDailyList", "/template/trade/balanceDailyList.xlsx"),
	statisticsRegTouristUser("注册用户数据列表", "statisticsRegTouristUser", "/template/room/statisticsRegTouristUser.xlsx"),
	statisticsOnlineHours("在线时长数据列表", "statisticsOnlineHours", "/template/room/statisticsOnlineHours.xlsx"),
	statistics("行为数据列表", "statistics", "/template/room/statistics.xlsx"),
	custConsultation("新客服咨询列表信息", "custConsultation", "/template/custConsultation.xlsx"),
	statisticsMonthReport("官网月均报表信息", "statisticsMonthReport", "/template/statisticsMonthReport.xlsx"),
	statisticsDaysReport("官网日均报表信息", "statisticsDaysReport", "/template/statisticsDaysReport.xlsx"),
	statisticsDailyChannel("日渠道报表信息", "statisticsDailyChannel", "/template/statisticsDailyChannel.xlsx"),
	appDataAnalysisAll("APP数据分析报表", "appDataAnalysisAll", "/template/appDataAnalysisAll.xlsx"),
	appDataAnalysisWeekAll("APP数据分析报表", "appDataAnalysisWeekAll", "/template/appDataAnalysisWeekAll.xlsx"),
	appDataAnalysis("APP数据分析报表", "appDataAnalysis", "/template/appDataAnalysis.xlsx"),
	appDataAnalysisWeek("APP数据分析报表", "appDataAnalysisWeek", "/template/appDataAnalysisWeek.xlsx"),
	appDataAnalysisDetails("APP数据分析详情报表", "appDataAnalysisDetails", "/template/appDataAnalysisDetails.xlsx"),
	accountbalanceList("余额变动报表", "accountbalanceList", "/template/trade/accountbalanceList.xlsx"),
	customerdailyList("账户结算日报表", "customerdailyList", "/template/trade/customerdailyList.xlsx"),
	tradeDetailCloseList("平仓交易报表", "tradeDetailCloseList", "/template/trade/tradeDetailCloseList.xlsx"),
	tradeDetailOpenList("持仓交易报表", "tradeDetailOpenList", "/template/trade/tradeDetailOpenList.xlsx"),
	dimAccountBlackList("账户黑名单列表", "dimAccountBlackList", "/template/trade/dimAccountBlackList.xlsx"),
	dimBlackList("风控要素列表", "dimBlackList", "/template/trade/dimBlackList.xlsx"),
	riskBlacklist("风控黑名单列表", "riskBlacklist", "/template/trade/riskBlacklist.xlsx"),
	tradeIndexList("交易指标列表", "tradeIndexList", "/template/trade/tradeIndexList.xlsx"),
	
	dealchannelReport("下单途径比例报表", "dealchannelReport", "/template/trade/dealchannelReport.xlsx"),
	accountchannelOpenReport("新开账户途径比例报表", "accountchannelOpenReport", "/template/trade/accountchannelOpenReport.xlsx"),
	accountchannelActiveReport("新激活途径比例报表", "accountchannelActiveReport", "/template/trade/accountchannelActiveReport.xlsx"),
	dealprofithourReport("公司盈亏报表", "dealprofithourReport", "/template/trade/dealprofithourReport.xlsx"),
	dailyreportlReport("结余图表报表", "dailyreportlReport", "/template/trade/dailyreportlReport.xlsx"),
	dailyreportFloatingprofitReport("浮动盈亏报表", "dailyreportFloatingprofitReport", "/template/trade/dailyreportFloatingprofitReport.xlsx"),
	dailyreportPreviousbalanceReport("结余图表报表", "dailyreportPreviousbalanceReport", "/template/trade/dailyreportPreviousbalanceReport.xlsx"),
	dailyreportFloatingprofitRatioReport("浮动盈亏比例报表", "dailyreportFloatingprofitRatioReport", "/template/trade/dailyreportFloatingprofitRatioReport.xlsx"),
	dailyreportMarginRatioReport("保证金比例报表", "dailyreportMarginRatioReport", "/template/trade/dailyreportMarginRatioReport.xlsx"),
	dealcategoryReport("交易类别数据报表", "dealcategoryReport", "/template/trade/dealcategoryReport.xlsx"),
	dealcategoryGTS2Report("交易类别数据报表", "dealcategoryGTS2Report", "/template/trade/dealcategoryGTS2Report.xlsx"),
	dealcategoryMT4Report("交易类别数据报表", "dealcategoryMT4Report", "/template/trade/dealcategoryMT4Report.xlsx"),
	dealcateGoldReport("伦敦金平仓手数分布报表", "dealcateGoldReport", "/template/trade/dealcateGoldReport.xlsx"),	
	dealcateSilverReport("人民币金平仓手数报表", "dealcateSilverReport", "/template/trade/dealcateSilverReport.xlsx"),
	dealcateXagcnhReport("人民币银平仓手数报表", "dealcateXagcnhReport", "/template/trade/dealcateXagcnhReport.xlsx"),
	dealcateXaucnhReport("伦敦银平仓手数报表", "dealcateXaucnhReport", "/template/trade/dealcateXaucnhReport.xlsx"),
	
	dealprofitdetailMT4AndGts2("MT4/GTS2数据报表", "dealprofitdetailMT4AndGts2", "/template/trade/dealprofitdetailMT4AndGts2.xlsx"),
	dealprofitdetailGts2("GTS2数据报表", "dealprofitdetailGts2", "/template/trade/dealprofitdetailGts2.xlsx"),
	dealprofitdetailMT4("MT4数据报表", "dealprofitdetailMT4", "/template/trade/dealprofitdetailMT4.xlsx"),
	averageTransactionVolume("人均交易手数报表", "averageTransactionVolume", "/template/trade/averageTransactionVolume.xlsx"),
	
	companyequitymdepositReport("净入金报表", "companyequitymdepositReport", "/template/trade/companyequitymdepositReport.xlsx"),
	dealprofitdetailReport("交易记录总结报表", "dealprofitdetailReport", "/template/trade/dealprofitdetailReport.xlsx"),
	dealprofitdetailGrossprofitReport("毛利图表", "dealprofitdetailGrossprofitReport", "/template/trade/dealprofitdetailGrossprofitReport.xlsx"),
	dealprofitdetailVolumeReport("交易手数图表", "dealprofitdetailVolumeReport", "/template/trade/dealprofitdetailVolumeReport.xlsx"),
	dealprofitdetailUseramountReport("交易人数图表", "dealprofitdetailUseramountReport", "/template/trade/dealprofitdetailUseramountReport.xlsx"),
	dealprofitdetailDealamountReport("交易次数图表", "dealprofitdetailDealamountReport", "/template/trade/dealprofitdetailDealamountReport.xlsx"),
	tradeSituationReport("交易情况图表", "tradeSituationReport", "/template/trade/tradeSituationReport.xlsx"),
	tradeSituationWeekReport("交易情况图表", "tradeSituationWeekReport", "/template/trade/tradeSituationWeekReport.xlsx"),
	channelActiveReport("渠道新增、活跃报表", "channelActiveReport", "/template/operateStatistics/channelActiveReport.xlsx"),
	channelActiveWeekReport("渠道新增、活跃报表", "channelActiveReport", "/template/operateStatistics/channelActiveWeekReport.xlsx"),
	channelEffectReport("渠道新增、活跃、效果报表", "channelEffectReport", "/template/operateStatistics/channelEffectReport.xlsx"),
	channelEffectWeekReport("渠道新增、活跃、效果报表", "channelEffectWeekReport", "/template/operateStatistics/channelEffectWeekReport.xlsx"),
	customerQualityWebReport("获客质量报表", "customerQualityWebReport", "/template/operateStatistics/customerQualityWebReport.xlsx"),
	customerQualityWebWeekReport("获客质量报表", "customerQualityWebWeekReport", "/template/operateStatistics/customerQualityWebWeekReport.xlsx"),
	customerQualityClientReport("获客质量报表", "customerQualityClientReport", "/template/operateStatistics/customerQualityClientReport.xlsx"),
	customerQualityClientWeekReport("获客质量报表", "customerQualityClientWeekReport", "/template/operateStatistics/customerQualityClientWeekReport.xlsx"),
	
	dasAppOdsReport("app数据源报表", "dasAppOdsReport", "/template/dasAppOdsReport.xlsx"),
	appEventStatisticsReport("app事件统计报表", "appEventStatisticsReport", "/template/appEventStatisticsReport.xlsx"),
	appEventStatisticsDateReport("app事件统计报表", "appEventStatisticsDateReport", "/template/appEventStatisticsDateReport.xlsx"),
	
	roomBehaviorCount("直播间开户统计", "roomBehaviorCount", "/template/room/roomBehaviorCount.xlsx"),
	roomBehaviorOpenAccountCount("开户平均数统计", "behaviorOpenAccountCount", "/template/room/roomBehaviorOpenAccountCount.xlsx"),
	roomBehaviorOpenAccountCountDemo("模拟开户平均数统计", "behaviorOpenAccountCount", "/template/room/roomBehaviorOpenAccountCountDemo.xlsx"),
	roomBehaviorOpenAccountCountReal("真实开户平均数统计", "behaviorOpenAccountCount", "/template/room//roomBehaviorOpenAccountCountReal.xlsx"),
	roomBehaviorOpenAccountCountDeposit("入金开户平均数统计", "behaviorOpenAccountCount", "/template/room/roomBehaviorOpenAccountCountDeposit.xlsx"),
	roomSourceMediumCount("直播间来源媒介统计", "sourceMediumCount", "/template/room/roomSourceMediumCount.xlsx"),
	roomDetailSummaryInfo("行为详细汇总信息", "detailSummaryInfo", "/template/room/roomDetailSummaryInfo.xlsx"),
	roomVisitDetailInfo("浏览详细信息", "visitDetailInfo", "/template/room/roomVisitDetailInfo.xlsx"),
	websiteBehaviorDataSource("官网行为数据源报表", "websiteBehaviorDataSource", "/template/websiteBehaviorDataSource.xlsx"),
	roomDataSource("直播间数据源管理报表", "roomDataSource", "/template/roomDataSource.xlsx"),
	;
	
	private final String value;//模板名称
	private final String labelKey;//模板唯一标示
	private final String path;//模板路径
	ExportTemplateEnum(String _operator, String labelKey, String path) {
		this.value = _operator;
		this.labelKey = labelKey;
		this.path = path;
	}
	
	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}

	public String getPath() {
		return path;
	}
	
}
