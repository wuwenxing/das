package com.gw.das.rpc.website;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 留在总统计数据接口管理类
 * @author kirin.guan
 *
 */
public interface DasFlowStatisticsManager extends Manager {

	/**
	 * 总流量数据列表
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsTimeList(String jsonStr) throws Exception;
	/**
	 * 总流量数据列表 分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsTimePage(String jsonStr) throws Exception;
	
	/**
	 * 总流量数据列表
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsMediaList(String jsonStr) throws Exception;
	
	/**Object
	 * 总流量数据列表 分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsMediaPage(String jsonStr) throws Exception;
	
	/**Object
	 * 来源媒件环比分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsMediaTreePage(String jsonStr) throws Exception;
	
	/**
	 * 总流量数据列表
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsBehaviorList(String jsonStr) throws Exception;
	
	/**Object
	 * 总流量数据列表 分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsBehaviorPage(String jsonStr) throws Exception;
	
	/**
	 * 总流量数据列表
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsAverageList(String jsonStr) throws Exception;
	
	/**Object
	 * 总流量数据列表 分页
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsAveragePage(String jsonStr) throws Exception;
	
	/**
	 * 总流量数据列表
	 * @param parameters
	 * @return
	 */
	public Map<String, String> DasFlowStatisticsHoursSumData(String jsonStr) throws Exception;
}
