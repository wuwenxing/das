package com.gw.das.rpc.room;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 详细数据接口管理类
 * 
 * @author wayne
 */
public interface DasChartRoomManager extends Manager {

	/**
	 * 直播间报表_登陆数据明细-分页查询
	 * @param pageNumber 
	 * @param pageSize 
	 * @param DasRoomLoginStatisticsSearchBean model
	 */
	public Map<String, String> roomLoginStatisticsPage(String jsonStr) throws Exception;

	/**
	 * 直播间报表_登陆数据明细-不分页查询
	 * @param DasRoomLoginStatisticsSearchBean model
	 */
	public Map<String, String> roomLoginStatisticsList(String jsonStr) throws Exception;
	
	/**
	 * 访客列表-分页查询
	 * @param pageNumber 
	 * @param pageSize 
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> visitorPage(String jsonStr) throws Exception;
	
	/**
	 * 访客列表-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> visitorList(String jsonStr) throws Exception;

	/**
	 * 访客明细列表-分页查询
	 * @param pageNumber 
	 * @param pageSize 
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> visitorDetailPage(String jsonStr) throws Exception;

	/**
	 * 访客明细列表-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> visitorDetailList(String jsonStr) throws Exception;
	
	/**
	 * 行为统计-时\日\周\月-分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsPage(String jsonStr) throws Exception;
	
	/**
	 * 行为统计-时\日\周\月-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsList(String jsonStr) throws Exception;
	
	/**
	 * 发言天数分析-分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsSpeakdaysPage(String jsonStr) throws Exception;

	/**
	 * 发言天数分析-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsSpeakdaysList(String jsonStr) throws Exception;
	
	/**
	 * 登录天数分析-分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsLogindaysPage(String jsonStr) throws Exception;

	/**
	 * 登录天数分析-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsLogindaysList(String jsonStr) throws Exception;
	
	/**
	 * 发言次数分析-日\周\月(reportType区分)统计-分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsSpeakcountsPage(String jsonStr) throws Exception;

	/**
	 * 发言次数分析-日\周\月(reportType区分)统计-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsSpeakcountsList(String jsonStr) throws Exception;
	
	/**
	 * 用户在线时长分析-分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsOnlineHoursPage(String jsonStr) throws Exception;
	
	/**
	 * 用户在线时长分析-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsOnlineHoursList(String jsonStr) throws Exception;
	
	/**
     * 直播间-登录次/人数、访问次/人数、发言次/人数  分析-分页查询
     * 根据用户级别进行统计
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsUserTypePage(String jsonStr) throws Exception;

	/**
     * 直播间-登录次/人数、访问次/人数、发言次/人数  分析-不分页查询
     * 根据用户级别进行统计
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsUserTypeList(String jsonStr) throws Exception;
	
	/**
	 * 注册用户统计周期(日、周、月-reportType区分)列表分析-日统计-分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsRegTouristUserPage(String jsonStr) throws Exception;

	/**
	 * 注册用户统计周期(日、周、月-reportType区分)列表分析-日统计-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsRegTouristUserList(String jsonStr) throws Exception;
	
	/**
	 * 访问记录统计周期(日、周、月-reportType区分)列表分析-日统计-分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsVisitcountsPage(String jsonStr) throws Exception;
	
	/**
	 * 访问记录统计周期(日、周、月-reportType区分)列表分析-日统计-不分页查询
	 * @param DasChartRoomSearchBean model
	 */
	public Map<String, String> statisticsVisitcountsList(String jsonStr) throws Exception;
	
	///////////
	
	/**
	 * 直播间开户统计列表-不分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindBehaviorCountList(String jsonStr) throws Exception;
	
	/**
	 * 直播间开户统计列表-分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindBehaviorCountPageList(String jsonStr) throws Exception;
	
	/**
	 * 直播间来源媒介统计-不分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindRoomSourceCountList(String jsonStr) throws Exception;
	
	/**
	 * 直播间来源媒介统计-分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindRoomSourceCountPageList(String jsonStr) throws Exception;
	
	/**
	 * 直播间来源媒介统计-分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasFindRoomSourceCountTreePageList(String jsonStr) throws Exception;
	
	
	/**
	 * 总流量数据列表
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasRoomStatisticsAverageList(String jsonStr) throws Exception;
	
	/**Object
	 * 总流量数据列表 分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> dasRoomStatisticsAveragePage(String jsonStr) throws Exception;
	
	/**
	 *  不分页查询用户行为列表报表记录 - elasticsearch
	 *  
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> dasFindBehaviorESList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询用户行为列表报表记录 - elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> dasFindBehaviorESPageList(String jsonStr) throws Exception;
	
	
	/**
	 * 用户详细-页面浏览详细列表不分页 - elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> dasFindFlowDetailUrlList(String jsonStr) throws Exception;
	
	/**
	 * 用户详细-页面浏览详细列表分页 - elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> dasFindFlowDetailUrlPageList(String jsonStr) throws Exception;
	
}
