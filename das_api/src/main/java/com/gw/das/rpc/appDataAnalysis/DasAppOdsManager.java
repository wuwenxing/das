package com.gw.das.rpc.appDataAnalysis;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * app数据源报表接口
 * 
 * @author darren
 *
 */
public interface DasAppOdsManager  extends Manager{

	/**
	 * 分页查询app数据源报表记录 -elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findDasAppOdsPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询app数据源报表记录 -elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findDasAppOdsList(String jsonStr) throws Exception;
	
}
