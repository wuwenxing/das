package com.gw.das.rpc.dataSourceList;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 数据源列表接口
 * 
 * @author darren
 *
 */
public interface DataSourceListManager extends Manager {
	
	/**
	 * 官网行为数据源记录查询-分页-elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return
	 */
	public Map<String, String> findWebsiteBehaviorDataSourcePageList(String jsonStr) throws Exception;
	
	/**
	 * 官网行为数据源记录查询-不分页-elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return
	 */
	public Map<String, String> findWebsiteBehaviorDataSourceList(String jsonStr) throws Exception;
	
	/**
	 * 直播间数据源记录查询-分页-elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return
	 */
	public Map<String, String> findRoomDataSourcePageList(String jsonStr) throws Exception;
	
	/**
	 * 直播间数据源记录查询-不分页-elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 * @return
	 */
	public Map<String, String> findRoomDataSourceList(String jsonStr) throws Exception;

}
