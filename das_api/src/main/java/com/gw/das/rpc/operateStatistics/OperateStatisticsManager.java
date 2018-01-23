package com.gw.das.rpc.operateStatistics;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 运营统计接口管理类
 * 
 * @author darren
 *
 */
public interface OperateStatisticsManager extends Manager {

	/**
	 * 分页查询渠道新增、活跃报表（client）记录
	 * 
	 * @return
	 */
	public Map<String, String> findChannelActivePageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询渠道新增、活跃报表（client）记录
	 * 
	 * @return
	 */
	public Map<String, String> findChannelActiveList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询渠道新增、活跃、效果报表（web）记录
	 * 
	 * @return
	 */
	public Map<String, String> findChannelEffectPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询渠道新增、活跃、效果报表（web）记录
	 * 
	 * @return
	 */
	public Map<String, String> findChannelEffectList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询获客质量（web、client）记录
	 * 
	 * @return
	 */
	public Map<String, String> findCustomerQualityPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询获客质量（web、client）记录
	 * 
	 * @return
	 */
	public Map<String, String> findCustomerQualityList(String jsonStr) throws Exception;
}
