package com.gw.das.service.website;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.website.bean.DasStatisticsDailyChannel;
import com.gw.das.dao.website.bean.DasStatisticsReport;
import com.gw.das.dao.website.bean.DasStatisticsReportSearchBean;

public interface DasStatisticsReportService {

	/**
	 * 官网统计  -- （日/月）分页查询
	 */
	public PageGrid<DasStatisticsReportSearchBean> findStatisticsReportPage(PageGrid<DasStatisticsReportSearchBean> pageGrid) throws Exception;
	
	/**
	 * 官网统计  -- （日/月）-不分页查询
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DasStatisticsReport> findStatisticsReportList(DasStatisticsReportSearchBean searchModel) throws Exception;
	
	/**
	 * 日报渠道统计  -- （日/月）分页查询
	 */
	public PageGrid<DasStatisticsReportSearchBean> findDailyChannelPage(PageGrid<DasStatisticsReportSearchBean> pageGrid) throws Exception;
	
	/**
	 * 日报渠道统计  -- （日/月）-不分页查询
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DasStatisticsDailyChannel> findDailyChannelList(DasStatisticsReportSearchBean searchModel) throws Exception;

}
