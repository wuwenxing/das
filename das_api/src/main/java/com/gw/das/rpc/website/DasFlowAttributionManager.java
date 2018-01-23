package com.gw.das.rpc.website;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 归因数据接口管理类
 * 
 * @author kirin.guan
 *
 */
public interface DasFlowAttributionManager extends Manager {

	/**
	 * 归因数据列表
	 */
	public Map<String, String> DasFlowAttributionList(String jsonStr) throws Exception;

	/**
	 * 归因数据列表 分页
	 */
	public Map<String, String> DasFlowAttributionListPage(String jsonStr) throws Exception;

}
