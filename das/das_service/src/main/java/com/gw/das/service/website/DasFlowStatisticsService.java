package com.gw.das.service.website;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.website.bean.DasFlowStatistics;
import com.gw.das.dao.website.bean.DasFlowStatisticsAverage;
import com.gw.das.dao.website.bean.DasFlowStatisticsSearchBean;
import com.gw.das.dao.website.bean.DasFlowStatisticsTreeMedia;

public interface DasFlowStatisticsService {

	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatistics> findList(DasFlowStatisticsSearchBean attribution) throws Exception;

	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findPageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception;

	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatistics> findMediaList(DasFlowStatisticsSearchBean attribution) throws Exception;

	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findMediaPageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception;
	
	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findMediaTreePageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception;

	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatistics> findTimeList(DasFlowStatisticsSearchBean attribution) throws Exception;

	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findTimePageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception;

	/**
	 * 流量统计列表
	 */
	public List<DasFlowStatisticsAverage> findAverageList(DasFlowStatisticsSearchBean attribution) throws Exception;

	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasFlowStatisticsSearchBean> findAveragePageList(PageGrid<DasFlowStatisticsSearchBean> pageGrid) throws Exception;

}
