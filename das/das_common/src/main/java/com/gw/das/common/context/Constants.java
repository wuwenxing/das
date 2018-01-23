package com.gw.das.common.context;

/**
 * 公共常量类
 */
public class Constants {

	/**
	 * 超级管理员用户-默认用户账户-拥有全部权限
	 */
	public final static String superAdmin = "superAdmin";

	public final static String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
	
	/**
	 * 加密结尾
	 */
	public static final String END_WITH_STR = "_ENCRY";
	
	/**
	 * 用户初始密码-默认111
	 */
	public final static String initPassword = "111";

	/**
	 * 最大上传文件大小
	 */
	public static final int MAX_UPLOAD_FILE_SIZE = 10 * 1024 * 1024;
	
	/**
	 * 一批流量最多充值50个手机号码-亿美
	 */
	public final static int batchSizeFlow = 50;
	
	/**
	 * 一批最多发送100个邮箱或手机号码
	 */
	public final static int batchSize = 100;
	
	/**
	 * 一次上传最多上传50000个邮箱或手机号码
	 */
	public final static int uploadSize = 50000;

	/**
	 * 设置诊断报告所在跟目录
	 */
	public final static String accountAnalyzePath = "/web/accountAnalyze/report";

	/**
	 * 设置诊断报告访问跟目录
	 */
	public final static String accountAnalyzeLinkPath = "/report";
	
	/**
	 * 诊断报告批次号前缀
	 */
	public final static String batchNoPrefix = "A";

	/**
	 * loc
	 */
	public final static String loc = "loc";
	/**
	 * uat
	 */
	public final static String uat = "uat";
	/**
	 * real
	 */
	public final static String real = "real";
	
	/**
	 * 诊断接口地址-获取保证金水平走势
	 */
	public final static String marginLevel = "/accountAnalyze/marginLevel";
	/**
	 * 诊断接口地址-获取3笔最大盈利单
	 */
	public final static String topProfit = "/accountAnalyze/topProfit";
	/**
	 * 诊断接口地址-获取3笔最大亏损单
	 */
	public final static String topLoss = "/accountAnalyze/topLoss";
	/**
	 * 诊断接口地址-平仓单的盈亏金额与持仓时间
	 */
	public final static String profitAndLossAmountAndTime = "/accountAnalyze/profitAndLossAmountAndTime";
	
	/**
	 * 诊断接口地址-账户概况
	 */
	public final static String accountOverview = "/accountAnalyze/accountOverview";
	
	/**
	 * 诊断接口地址-收益统计
	 */
	public final static String profitStatistics = "/accountAnalyze/profitStatistics";
	
	/**
	 * 诊断接口地址-收益分析
	 */
	public final static String profitAnalysis = "/accountAnalyze/profitAnalysis";
	
	/**
	 * 诊断接口地址-平仓交易分页查询
	 */
	public final static String tradeDetailClosePageList = "/tradeReport/getTradeDetailClosePageList";
	
	/**
	 * 诊断接口地址-平仓交易不分页查询
	 */
	public final static String tradeDetailCloseList = "/tradeReport/getTradeDetailCloseList";
	
	/**
	 * 诊断接口地址-持仓交易分页查询
	 */
	public final static String tradeDetailOpenPageList = "/tradeReport/getTradeDetailOpenPageList";
	
	/**
	 * 诊断接口地址-持仓交易不分页查询
	 */
	public final static String tradeDetailOpenList = "/tradeReport/getTradeDetailOpenList";
	
	/**
	 * 诊断接口地址-平仓交易分页查询
	 */
	public final static String accountbalancePageList = "/tradeReport/getAccountbalancePageList";
	
	/**
	 * 诊断接口地址-平仓交易不分页查询
	 */
	public final static String accountbalanceList = "/tradeReport/getAccountbalanceList";
	
	/**
	 * 诊断接口地址-持仓交易分页查询
	 */
	public final static String customerdailyPageList = "/tradeReport/getCustomerdailyPageList";
	
	/**
	 * 诊断接口地址-持仓交易不分页查询
	 */
	public final static String customerdailyList = "/tradeReport/getCustomerdailyList";
	
	/**
	 * 用户刷选接口地址
	 */
	public final static String dasUserInfoScreen = "/DasUserInfo/dasUserInfoScreen";

	/**
	 * 用户列表接口地址
	 */
	public static final String dasUserInfoListPage = "/DasUserInfo/dasUserInfoListPage";

	/**
	 * 用户流量详细不分页查询接口地址
	 */
	public static final String DasFlowDetailList = "/DasFlowDetail/DasFlowDetailListNew";
	
	/**
	 * 用户流量详细不分页查询接口地址 - elasticsearch
	 */
	public static final String DasFlowDetailESList = "/DasFlowDetail/DasFlowDetailESList";
	
	/**
	 * 用户流量详细分页查询接口地址
	 */
	public final static String DasFlowDetailListPage = "/DasFlowDetail/DasFlowDetailListPageNew";
	
	/**
	 * 用户流量详细分页查询接口地址 -elasticsearch
	 */
	public final static String DasFlowDetailListESPageList = "/DasFlowDetail/DasFlowDetailListESPageList";
	
	/**
	 * 用户访问URL详细不分页查询接口地址
	 */
	public static final String DasFlowDetailUrlList = "/DasFlowDetail/DasFlowDetailUrlList";
	
	/**
	 * 用户访问URL详细不分页查询接口地址-elasticsearch
	 */
	public static final String DasFlowDetailUrlESList = "/DasFlowDetail/DasFlowDetailUrlESList";
	
	/**
	 * 用户访问URL详细分页查询接口地址
	 */
	public static final String DasFlowDetailUrlListPage = "/DasFlowDetail/DasFlowDetailUrlListPage";
	
	/**
	 * 用户访问URL详细分页查询接口地址-elasticsearch
	 */
	public static final String DasFlowDetailUrlESPageList = "/DasFlowDetail/DasFlowDetailUrlESPageList";

	/**
	 * 归因报表URL详细分页查询接口地址
	 */
	public static final String DasFlowAttributionListPage = "/DasFlowAttribution/DasFlowAttributionListPage";
	
	/**
	 * 归因报表URL详细不分页查询接口地址
	 */
	public static final String DasFlowAttributionList = "/DasFlowAttribution/DasFlowAttributionList";
	
	/**
	 * 流量统计查询接口地址-不分页
	 */
	public static final String DasFlowStatisticsBehaviorList = "/DasFlowStatistics/DasFlowStatisticsBehaviorList";

	/**
	 * 流量统计查询接口地址-分页
	 */
	public static final String DasFlowStatisticsBehaviorPage = "/DasFlowStatistics/DasFlowStatisticsBehaviorPage";

	/**
	 * 流量统计查询接口地址-不分页
	 */
	public static final String DasFlowStatisticsMediaList = "/DasFlowStatistics/DasFlowStatisticsMediaList";

	/**
	 * 流量统计查询接口地址-分页
	 */
	public static final String DasFlowStatisticsMediaPage = "/DasFlowStatistics/DasFlowStatisticsMediaPage";
	
	/**
	 * 来源媒件统计查询环比接口地址-分页
	 */
	public static final String DasFlowStatisticsMediaTreePage = "/DasFlowStatistics/DasFlowStatisticsMediaTreePage";

	/**
	 * 流量统计查询接口地址-不分页
	 */
	public static final String DasFlowStatisticsTimeList = "/DasFlowStatistics/DasFlowStatisticsTimeList";

	/**
	 * 流量统计查询接口地址-分页
	 */
	public static final String DasFlowStatisticsTimePage = "/DasFlowStatistics/DasFlowStatisticsTimePage";

	/**
	 * 流量统计查询接口地址-不分页
	 */
	public static final String DasFlowStatisticsAveragePage = "/DasFlowStatistics/DasFlowStatisticsAveragePage";

	/**
	 * 流量统计查询接口地址-分页
	 */
	public static final String DasFlowStatisticsAverageList = "/DasFlowStatistics/DasFlowStatisticsAverageList";

	/**
	 * 首页图表接口地址
	 */
	public static final String DasFlowStatisticsHoursSumData = "/DasFlowStatistics/DasFlowStatisticsHoursSumData";

	/**
	 * Landingpage
	 */
	public static final String dasWebLandingpageDetailList = "/DasWebSite/dasWebLandingpageDetailList";

	/**
	 * Landingpage
	 */
	public static final String dasWebLandingpageDetailPage = "/DasWebSite/dasWebLandingpageDetailPage";

	/**
	 * das_behavior_event_channel_effect_d
	 */
	public static final String dasBehaviorEventChannelEffectList = "/DasWebSite/dasBehaviorEventChannelEffectList";

	/**
	 * das_behavior_event_channel_effect_d
	 */
	public static final String dasBehaviorEventChannelEffectPage = "/DasWebSite/dasBehaviorEventChannelEffectPage";

	/**
	 * 直播间-访客列表-分页查询
	 */
	public static final String dasChartRoomVisitorPage = "/DasChartRoom/visitorPage";
	
	/**
	 * 直播间-访客列表-不分页查询
	 */
	public static final String dasChartRoomVisitorList = "/DasChartRoom/visitorList";
	
	/**
	 * 直播间-访客明细列表-分页查询
	 */
	public static final String dasChartRoomVisitorDetailPage = "/DasChartRoom/visitorDetailPage";
	
	/**
	 * 直播间-访客明细列表-不分页查询
	 */
	public static final String dasChartRoomVisitorDetailList = "/DasChartRoom/visitorDetailList";
	
	/**
	 * 直播间-行为统计-时\日\周\月统计-分页查询
	 */
	public static final String dasChartRoomStatisticsPage = "/DasChartRoom/statisticsPage";
	
	/**
	 * 直播间-行为统计-时\日\周\月统计-不分页查询
	 */
	public static final String dasChartRoomStatisticsList = "/DasChartRoom/statisticsList";
	
	/**
	 * 直播间-发言天数分析-月统计-分页查询
	 */
	public static final String dasChartRoomStatisticsSpeakdays = "/DasChartRoom/statisticsSpeakdaysPage";
	
	/**
	 * 直播间-发言天数分析-月统计-不分页查询
	 */
	public static final String dasChartRoomStatisticsSpeakList = "/DasChartRoom/statisticsSpeakdaysList";
	
	/**
	 * 直播间-发言次数分析-日\周\月统计-分页查询
	 */
	public static final String dasChartRoomStatisticsSpeakcounts = "/DasChartRoom/statisticsSpeakcountsPage";
	
	/**
	 * 直播间-发言次数分析-日\周\月统计-不分页查询
	 */
	public static final String dasChartRoomStatisticsSpeakcountsList = "/DasChartRoom/statisticsSpeakcountsList";

	/**
	 * 直播间-登录天数分析-月统计-分页查询
	 */
	public static final String dasChartRoomStatisticsLogindaysPage = "/DasChartRoom/statisticsLogindaysPage";
	
	/**
	 * 直播间-登录天数分析-月统计-不分页查询
	 */
	public static final String dasChartRoomStatisticsLogindaysList = "/DasChartRoom/statisticsLogindaysList";
	
	/**
	 * 直播间-用户在线时长分析-分页查询
	 */
	public static final String dasChartRoomStatisticsOnlineHoursPage = "/DasChartRoom/statisticsOnlineHoursPage";
	
	/**
	 * 直播间-用户在线时长分析-不分页查询
	 */
	public static final String dasChartRoomStatisticsOnlineHoursList = "/DasChartRoom/statisticsOnlineHoursList";
	
	/**
	 * 直播间-登录次/人数、访问次/人数、发言次/人数  分析-分页查询
	 */
	public static final String dasChartRoomStatisticsUserTypePage = "/DasChartRoom/statisticsUserTypePage";
	
	/**
	 * 直播间-登录次/人数、访问次/人数、发言次/人数  分析-不分页查询
	 */
	public static final String dasChartRoomStatisticsUserTypeList = "/DasChartRoom/statisticsUserTypeList";
	
	/**
	 * 直播间-注册用户列表 -日统计-分页查询
	 */
	public static final String dasChartRoomStatisticsRegTouristUserPage = "/DasChartRoom/statisticsRegTouristUserPage";
	
	/**
	 * 直播间-注册用户列表 -日统计-不分页查询
	 */
	public static final String dasChartRoomStatisticsRegTouristUserList = "/DasChartRoom/statisticsRegTouristUserList";
	
	/**
	 * 直播间-访问记录列表 -日统计-分页查询
	 */
	public static final String dasChartRoomStatisticsVisitcountsPage = "/DasChartRoom/statisticsVisitcountsPage";
	
	/**
	 * 直播间-访问记录列表 -日统计-不分页查询
	 */
	public static final String dasChartRoomStatisticsVisitcountsList = "/DasChartRoom/statisticsVisitcountsList";
	
	/**
	 * 恒信直播间登陆统计-分页查询
	 */
	public static final String roomLoginStatisticsPage = "/DasChartRoom/roomLoginStatisticsPage";
	
	/**
	 * 恒信直播间登陆统计-不分页查询
	 */
	public static final String roomLoginStatisticsList = "/DasChartRoom/roomLoginStatisticsList";
	
	/**
	 * 官网统计  -- （日/月）分页查询
	 */
	public static final String DasStatisticsReportPage = "/DasStatisticsReport/statisticsReportPage";
	
	/**
	 * 官网统计  -- （日/月）不分页查询
	 */
	public static final String DasStatisticsReportList = "/DasStatisticsReport/statisticsReportList";
	
	/**
	 * 官网统计  -- （日/月）分页查询
	 */
	public static final String DasStatisticsDailyChannelPage = "/DasStatisticsReport/dailyChannelPage";
	
	/**
	 * 官网统计  -- （日/月）不分页查询
	 */
	public static final String DasStatisticsDailyChannelList = "/DasStatisticsReport/dailyChannelList";
	
	/**
	 * 结算日报表查询-分页-elasticsearch
	 */
	public static final String findCustomerdailyPageList = "/Customerdaily/findCustomerdailyPageList";
	
	/**
	 * 结算日报表查询-不分页-elasticsearch
	 */
	public static final String findCustomerdailyList = "/Customerdaily/findCustomerdailyList";

	/**
	 * 账户余额变动查询-分页-elasticsearch
	 */
	public static final String findAccountbalancePageList = "/Accountbalance/findAccountbalancePageList";
	
	/**
	 * 账户余额变动查询-不分页-elasticsearch
	 */
	public static final String findAccountbalanceList = "/Accountbalance/findAccountbalanceList";
	
	/**
	 * APP数据分析报表查询-分页
	 */
	public static final String findAppDataAnalysisPageList = "/AppDataAnalysis/findAppDataAnalysisPageList";
	
	/**
	 * App数据分析报表查询-不分页
	 */
	public static final String findAppDataAnalysisList = "/AppDataAnalysis/findAppDataAnalysisList";
	
	/**
	 * App数据分析详情报表查询-分页
	 */
	public static final String findAppDataAnalysisDetailsPageList = "/AppDataAnalysis/findAppDataAnalysisDetailsPageList";
	
	/**
	 * App数据分析详情报表查询-不分页
	 */
	public static final String findAppDataAnalysisDetailsList = "/AppDataAnalysis/findAppDataAnalysisDetailsList";
	
	/**
	 * 开户来源分析报表查询-分页
	 */
	public static final String findOpenSourceAnalysisPageList = "/AppDataAnalysis/findOpenSourceAnalysisPageList";
	
	/**
	 * app数据源报表查询-分页 -elasticsearch
	 */
	public static final String findDasAppOdsPageList = "/DasAppOds/findDasAppOdsPageList";
	
	/**
	 * app数据源报表查询-不分页 -elasticsearch
	 */
	public static final String findDasAppOdsList = "/DasAppOds/findDasAppOdsList";
	
	
	/**
	 * app数据源报表查询-分页 -elasticsearch
	 */
	public static final String findAppDataSourcePageList = "/AppDataSource/findAppDataSourcePageList";
	
	/**
	 * app数据源报表查询-不分页 -elasticsearch
	 */
	public static final String findAppDataSourceList = "/AppDataSource/findAppDataSourceList";
	
	/**
	 * 公司盈亏报表报表查询-分页
	 */
	public static final String findDealprofithourPageList = "/Dealprofithour/findDealprofithourPageList";
	
	/**
	 * 公司盈亏报表报表查询-不分页
	 */
	public static final String findDealprofithourList = "/Dealprofithour/findDealprofithourList";
	
	/**
	 * GTS2下单途径比例报表查询-分页
	 */
	public static final String findDealchannelPageList = "/Dealchannel/findDealchannelPageList";
	
	/**
	 * GTS2下单途径比例报表查询-不分页
	 */
	public static final String findDealchannelList = "/Dealchannel/findDealchannelList";
	
	/**
	 * 新开户_激活途径比例报表查询-分页
	 */
	public static final String findAccountchannelPageList = "/Accountchannel/findAccountchannelPageList";
	
	/**
	 * 新开户_激活途径比例报表查询-不分页
	 */
	public static final String findAccountchannelList = "/Accountchannel/findAccountchannelList";
	
	/**
	 * 结余图表报表查询-分页
	 */
	public static final String findDailyreportPageList = "/Dailyreport/findDailyreportPageList";
	
	/**
	 * 结余图表报表查询-不分页
	 */
	public static final String findDailyreportList = "/Dailyreport/findDailyreportList";
	
	/**
	 * 分页查询交易人数/次数报表记录-分页
	 */
	public static final String findDealprofitdetailUseraAndDealamountPageList = "/Dealprofitdetail/findDealprofitdetailUseraAndDealamountPageList";
	
	/**
	 * 不分页查询交易人数/次数报表记录-不分页
	 */
	public static final String findDealprofitdetailUseraAndDealamountList = "/Dealprofitdetail/findDealprofitdetailUseraAndDealamountList";
	
	/**
	 * 交易记录总结报表(包括了交易手数图表和毛利图表)查询-分页
	 */
	public static final String findDealprofitdetailMT4AndGts2PageList = "/Dealprofitdetail/findDealprofitdetailMT4AndGts2PageList";
	
	/**
	 * 不分页查询MT4/GTS2数据报表记录-不分页
	 */
	public static final String findDealprofitdetailMT4AndGts2List = "/Dealprofitdetail/findDealprofitdetailMT4AndGts2List";
	
	/**
	 * 交易记录总结报表(包括了交易手数图表和毛利图表)查询-分页
	 */
	public static final String findDealprofitdetailPageList = "/Dealprofitdetail/findDealprofitdetailPageList";
	
	/**
	 * 交易记录总结报表(包括了交易手数图表和毛利图表)查询-不分页
	 */
	public static final String findDealprofitdetailList = "/Dealprofitdetail/findDealprofitdetailList";
	
	/**
	 * 交易类别数据查询-分页
	 */
	public static final String findDealcategoryPageList = "/Dealprofitdetail/findDealcategoryPageList";
	
	/**
	 * 交易类别数据查询-不分页
	 */
	public static final String findDealcategoryList = "/Dealprofitdetail/findDealcategoryList";
	
	/**
	 * 交易类别数据倫敦/人民幣查询-分页
	 */
	public static final String findDealcategoryAxuAxgcnhPageList = "/Dealprofitdetail/findDealcategoryAxuAxgcnhPageList";
	
	/**
	 * 交易类别数据倫敦/人民幣查询-不分页
	 */
	public static final String findDealcategoryAxuAxgcnhList = "/Dealprofitdetail/findDealcategoryAxuAxgcnhList";
	
	/**
	 * 查询人均交易手数记录-不分页
	 */
	public static final String findAverageTransactionVolumePageList = "/Dealprofitdetail/findAverageTransactionVolumePageList";
	
	/**
	 * 查询人均交易手数记录-分页
	 */
	public static final String findAverageTransactionVolumeList = "/Dealprofitdetail/findAverageTransactionVolumeList";
	
	/**
	 * 查询交易情况记录-不分页
	 */
	public static final String findTradeSituationList = "/Dealprofitdetail/findTradeSituationList";
	
	/**
	 * 查询交易情况记录-分页
	 */
	public static final String findTradeSituationPageList = "/Dealprofitdetail/findTradeSituationPageList";
	
	/**
	 *查询渠道新增、活跃报表（client）记录-不分页
	 */
	public static final String findChannelActiveList = "/OperateStatistics/findChannelActiveList";
	
	/**
	 * 查询渠道新增、活跃报表（client）记录-分页
	 */
	public static final String findChannelActivePageList = "/OperateStatistics/findChannelActivePageList";
	
	/**
	 * 查询渠道新增、活跃、效果报表（web）记录-不分页
	 */
	public static final String findChannelEffectList = "/OperateStatistics/findChannelEffectList";
	
	/**
	 * 查询渠道新增、活跃、效果报表（web）记录-分页
	 */
	public static final String findChannelEffectPageList = "/OperateStatistics/findChannelEffectPageList";
	
	/**
	 * 查询获客质量（web、client）记录-不分页
	 */
	public static final String findCustomerQualityList = "/OperateStatistics/findCustomerQualityList";
	
	/**
	 * 查询获客质量（web、client）记录-分页
	 */
	public static final String findCustomerQualityPageList = "/OperateStatistics/findCustomerQualityPageList";
	
	/** 十大赢家、十大亏损-不分页查询 */
	public static final String top10WinnerLoserYearsList = "/Trade/top10WinnerLoserYearsList";

	/** 十大交易量客戶-不分页查询 */
	public static final String top10TraderYearsList = "/Trade/top10TraderYearsList";

	/** 十大存款、十大取款-不分页查询 */
	public static final String top10CashinCashoutYearsList = "/Trade/top10CashinCashoutYearsList";

	/** 十大交易统计-手数-不分页查询 */
	public static final String top10VolumeStatisticsYearsList = "/Trade/top10VolumeStatisticsYearsList";

	/** 十大交易统计-持仓时间-不分页查询 */
	public static final String top10PositionStatisticsYearsList = "/Trade/top10PositionStatisticsYearsList";
	
	/** 存取款及账户-净入金-分页查询 */
	public static final String cashandaccountdetailStatisticsYearsPage = "/Trade/cashandaccountdetailStatisticsYearsPage";

	/** 存取款及账户-净入金-不分页查询 */
	public static final String cashandaccountdetailStatisticsYearsList = "/Trade/cashandaccountdetailStatisticsYearsList";

	/** 公司盈亏-不分页查询 */
	public static final String profithourStatisticsYearsList = "/Trade/profithourStatisticsYearsList";

	/** 交易记录总结-不分页查询 */
	public static final String profitdetailStatisticsYearsList = "/Trade/profitdetailStatisticsYearsList";
	
	/**
	 * 交易记录总结-当日
	 * 交易记录总结-当月累计
	 */
	public static final String findDaysOrMonthsSumByProfitdetail = "/Trade/findDaysOrMonthsSumByProfitdetail";
	
	/**
	 * 存取款及账户-当日
	 * 存取款及账户-当月累计
	 */
	public static final String findDaysOrMonthsSumByCashandaccountdetail = "/Trade/findDaysOrMonthsSumByCashandaccountdetail";
	
	/**
	 * 开户及存款总结
	 */
	public static final String openAccountAndDepositSummaryList = "/Trade/openAccountAndDepositSummaryList";
	
	/** 账户黑名单列表-分页查询 */
	public static final String findDimAccountBlackListPage = "/Trade/findDimAccountBlackListPage";

	/** 账户黑名单列表-不分页查询 */
	public static final String findDimAccountBlackList = "/Trade/findDimAccountBlackList";

	/** 风控要素-分页查询 */
	public static final String findDimBlackListPage = "/Trade/findDimBlackListPage";

	/** 风控要素-不分页查询 */
	public static final String findDimBlackList = "/Trade/findDimBlackList";
	
	/////////////////////
	
	/**
	 * 直播间开户统计列表-不分页
	 */
	public static final String DasFindBehaviorCountList = "/DasChartRoom/dasFindBehaviorCountList";

	/**
	 * 直播间开户统计列表-分页
	 */
	public static final String DasFindBehaviorCountPageList = "/DasChartRoom/dasFindBehaviorCountPageList";

	/**
	 * 直播间来源媒介统计-不分页
	 */
	public static final String DasFindRoomSourceCountList = "/DasChartRoom/dasFindRoomSourceCountList";

	/**
	 * 直播间来源媒介统计-分页
	 */
	public static final String DasFindRoomSourceCountPageList = "/DasChartRoom/dasFindRoomSourceCountPageList";
	
	/**
	 * 直播间来源媒介统计-分页
	 */
	public static final String DasFindRoomSourceCountTreePageList = "/DasChartRoom/dasFindRoomSourceCountTreePageList";
	
	/**
	 * 流量统计查询接口地址-不分页
	 */
	public static final String DasRoomStatisticsAveragePage = "/DasChartRoom/dasRoomStatisticsAveragePage";

	/**
	 * 流量统计查询接口地址-分页
	 */
	public static final String DasRoomStatisticsAverageList = "/DasChartRoom/dasRoomStatisticsAverageList";
	
	/**
	 * 用户流量详细不分页查询接口地址 - elasticsearch
	 */
	public static final String DasFindBehaviorESList = "/DasChartRoom/dasFindBehaviorESList";
	
	/**
	 * 用户流量详细分页查询接口地址 -elasticsearch
	 */
	public final static String DasFindBehaviorESPageList = "/DasChartRoom/dasFindBehaviorESPageList";
	
	
	/**
	 * 用户访问URL详细不分页查询接口地址-elasticsearch
	 */
	public static final String DasFindFlowDetailUrlList = "/DasChartRoom/dasFindFlowDetailUrlList";
	
	
	/**
	 * 用户访问URL详细分页查询接口地址-elasticsearch
	 */
	public static final String DasFindFlowDetailUrlPageList = "/DasChartRoom/dasFindFlowDetailUrlPageList";
	
	/**
	 * 官网行为数据源记录查询-分页-elasticsearch
	 */
	public static final String findWebsiteBehaviorDataSourcePageList = "/DataSourceList/findWebsiteBehaviorDataSourcePageList";
	
	/**
	 * 官网行为数据源记录查询-不分页-elasticsearch
	 */
	public static final String findWebsiteBehaviorDataSourceList = "/DataSourceList/findWebsiteBehaviorDataSourceList";
	
	/**
	 * 直播间数据源记录查询-分页-elasticsearch
	 */
	public static final String findRoomDataSourcePageList = "/DataSourceList/findRoomDataSourcePageList";
	
	/**
	 * 直播间数据源记录查询-不分页-elasticsearch
	 */
	public static final String findRoomDataSourceList = "/DataSourceList/findRoomDataSourceList";
}
