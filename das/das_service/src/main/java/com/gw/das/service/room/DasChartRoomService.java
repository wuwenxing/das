package com.gw.das.service.room;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.room.bean.DasChartFlowDetail;
import com.gw.das.dao.room.bean.DasChartFlowDetailSearchBean;
import com.gw.das.dao.room.bean.DasChartFlowDetailUrl;
import com.gw.das.dao.room.bean.DasChartFlowDetailUrlSearchBean;
import com.gw.das.dao.room.bean.DasChartFlowStatistics;
import com.gw.das.dao.room.bean.DasChartFlowStatisticsAverage;
import com.gw.das.dao.room.bean.DasChartFlowStatisticsSearchBean;
import com.gw.das.dao.room.bean.DasChartRoomDetail;
import com.gw.das.dao.room.bean.DasChartRoomDetailSum;
import com.gw.das.dao.room.bean.DasChartRoomOnlineHours;
import com.gw.das.dao.room.bean.DasChartRoomRegTouristUserStatistics;
import com.gw.das.dao.room.bean.DasChartRoomSearchModel;
import com.gw.das.dao.room.bean.DasChartRoomSpeakStatistics;
import com.gw.das.dao.room.bean.DasChartRoomStatistics;
import com.gw.das.dao.room.bean.DasChartRoomUserTypeStatistics;
import com.gw.das.dao.room.bean.DasRoomLoginStatistics;
import com.gw.das.dao.room.bean.DasRoomLoginStatisticsSearchBean;

public interface DasChartRoomService {
	/**
	 * 恒信直播间登陆统计-分页查询
	 */
	public PageGrid<DasRoomLoginStatisticsSearchBean> findLoginStatisticsPage(PageGrid<DasRoomLoginStatisticsSearchBean> pageGrid) throws Exception;

	/**
	 * 恒信直播间登陆统计-不分页查询
	 */
	public List<DasRoomLoginStatistics> findLoginStatisticsList(DasRoomLoginStatisticsSearchBean searchModel) throws Exception;
	
	/**
	 * 直播间-访客列表-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findVisitorPage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;
	
	/**
	 * 直播间-访客列表-不分页查询
	 */
	public List<DasChartRoomDetailSum> findVisitorList(DasChartRoomSearchModel searchModel) throws Exception;
	
	/**
	 * 直播间访客明细列表查询
	 */
	public PageGrid<DasChartRoomSearchModel> findVisitorDetailPage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;

	/**
	 * 直播间-访客明细列表查询-不分页查询
	 */
	public List<DasChartRoomDetail> findVisitorDetailList(DasChartRoomSearchModel searchModel) throws Exception;
	
	/**
	 * 直播间-（时段/日/周/月）统计-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsPage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;
	
	/**
	 * 直播间-（时段/日/周/月）统计-不分页查询
	 */
	public List<DasChartRoomStatistics> findStatisticsList(DasChartRoomSearchModel searchModel) throws Exception;
	
	/**
	 * 直播间-发言天/次数分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsSpeakPage(PageGrid<DasChartRoomSearchModel> pageGrid, String type) throws Exception;
	
	/**
	 * 直播间-发言天/次数分析-不分页查询
	 */
	public List<DasChartRoomSpeakStatistics> findStatisticsSpeakList(DasChartRoomSearchModel searchBean) throws Exception;

	/**
	 * 直播间-登录天数分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsLogindaysPage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;
	
	/**
	 * 直播间-登录天数分析-不分页查询
	 */
	public List<DasChartRoomSpeakStatistics> findStatisticsLogindaysList(DasChartRoomSearchModel searchBean) throws Exception;
	
	/**
	 * 直播间-用户在线时长分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsOnlineHoursPage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;
	
	/**
	 * 直播间-用户在线时长分析-不分页查询
	 */
	public List<DasChartRoomOnlineHours> findStatisticsOnlineHoursKList(DasChartRoomSearchModel searchBean) throws Exception;

	/**
	 * 根据用户类型进行登录\访问\公聊\私聊统计-跳转管理页面
	 * 对应type参数为1\2\3\4
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsByUserTypePage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;
	
	/**
	 * 根据用户类型进行登录\访问\公聊\私聊统计-跳转管理页面 不分页查询
	 * 对应type参数为1\2\3\4
	 */
	public List<DasChartRoomUserTypeStatistics> findStatisticsByUserTypeList(DasChartRoomSearchModel searchBean) throws Exception;
	
	/**
	 * 直播间-用户注册列表分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsRegTouristUserPage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;
	
	/**
	 * 直播间-用户注册列表分析-不分页查询
	 */
	public List<DasChartRoomRegTouristUserStatistics> findStatisticsRegTouristUserList(DasChartRoomSearchModel searchBean) throws Exception;
	
	/**
	 * 直播间-访问记录分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsVisitcountsPage(PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception;
	
	/**
	 * 直播间-访问记录分析-不分页查询
	 */
	public List<DasChartRoomSpeakStatistics> findStatisticsVisitcountsList(DasChartRoomSearchModel searchBean) throws Exception;
	
	/////////////////
	
	/**
	 * 直播间开户统计列表-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findBehaviorCountPageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception;
	
	/**
	 * 直播间开户统计列表
	 */
	public List<DasChartFlowStatistics> findBehaviorCountList(DasChartFlowStatisticsSearchBean attribution) throws Exception;
	
	
	/**
	 * 直播间来源媒介统计
	 */
	public List<DasChartFlowStatistics> findRoomSourceCountList(DasChartFlowStatisticsSearchBean attribution) throws Exception;

	/**
	 * 直播间来源媒介统计-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findRoomSourceCountPageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception;
	
	/**
	 * 直播间来源媒介统计-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findRoomSourceCountTreePageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception;
	
	/**
	 * 直播间开户统计列表详情-不分页查询
	 */
	public List<DasChartFlowStatisticsAverage> findAverageList(DasChartFlowStatisticsSearchBean attribution) throws Exception;
	
	/**
	 * 直播间开户统计列表详情-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findAveragePageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception;
	
	/**
	 * 用户行为列表分页查询
	 */
	public PageGrid<DasChartFlowDetailSearchBean> findBehaviorPageList(PageGrid<DasChartFlowDetailSearchBean> pageGrid) throws Exception;
	
	/**
	 * 用户行为列表不分页查询
	 */
	public List<DasChartFlowDetail> findBehaviorList(DasChartFlowDetailSearchBean detail) throws Exception;
	
	/**
	 * 用户详细-页面浏览详细列表
	 */
	public List<DasChartFlowDetailUrl> findFlowDetailUrlList(DasChartFlowDetailUrlSearchBean attribution) throws Exception;

	/**
	 * 用户详细-页面浏览详细列表分页
	 */
	public PageGrid<DasChartFlowDetailUrlSearchBean> findFlowDetailUrlPageList(PageGrid<DasChartFlowDetailUrlSearchBean> pageGrid) throws Exception;
	
}
