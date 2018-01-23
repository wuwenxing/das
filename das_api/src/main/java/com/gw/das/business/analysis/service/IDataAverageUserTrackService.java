package com.gw.das.business.analysis.service;

import java.util.List;
import java.util.Map;

import com.gw.das.business.dao.website.entity.DasFlowMonthAverage;

/**
 * 平均值统计接口
 * @author kirin.guan
 *
 */
public interface IDataAverageUserTrackService {

	/**
	 * 计算平均值
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,DasFlowMonthAverage> calculateAverageData(String year,String month) throws Exception;
	
	/**
	 * 根据时间获取平均表中的数据
	 * @param year
	 * @param month
	 * @return
	 */
	public List<DasFlowMonthAverage> getSourceAverageDataByTime(String year,String month) throws Exception;
	
	/**
	 * 保存
	 * @param userTrackDataAverage
	 */
	public void insert(DasFlowMonthAverage userTrackDataAverage) throws Exception;
	
	/**
	 * 修改
	 * @param userTrackDataAverage
	 */
	public void update(DasFlowMonthAverage userTrackDataAverage) throws Exception;
}
