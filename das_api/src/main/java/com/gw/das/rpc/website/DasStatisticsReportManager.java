package com.gw.das.rpc.website;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 官网统计数据接口管理类
 * 
 * @author darren.qiu
 *
 */
public interface DasStatisticsReportManager extends Manager {

	/**
	 * 官网统计-日\月-分页查询
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> statisticsReportPage(String jsonStr) throws Exception;
	
	/**
	 * 官网统计-日\月-不分页查询
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> statisticsReportList(String jsonStr) throws Exception;
	
	/**
	 * 日报渠道-日\月-分页查询
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> dailyChannelPage(String jsonStr) throws Exception;
	
	/**
	 * 日报渠道-日\月-不分页查询
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> dailyChannelList(String jsonStr) throws Exception;

}
