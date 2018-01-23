package com.gw.das.rpc.website;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 详细数据接口管理类
 * 
 * @author wayne
 */
public interface DasFlowDetailManager extends Manager {
	
	public Map<String, String> DasFlowDetailListNew(String jsonStr) throws Exception;

	public Map<String, String> DasFlowDetailListPageNew(String jsonStr) throws Exception;
	
	public Map<String, String> DasFlowDetailUrlListPage(String jsonStr) throws Exception;
	
	public Map<String, String> DasFlowDetailUrlList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询用户行为列表报表记录 - elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> DasFlowDetailListESPageList(String jsonStr) throws Exception;
	
	/**
	 *  不分页查询用户行为列表报表记录 - elasticsearch
	 *  
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> DasFlowDetailESList(String jsonStr) throws Exception;
	
	/**
	 * 用户详细-页面浏览详细列表分页 - elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> DasFlowDetailUrlESPageList(String jsonStr) throws Exception;
	
	/**
	 * 用户详细-页面浏览详细列表不分页 - elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> DasFlowDetailUrlESList(String jsonStr) throws Exception;

}
