package com.gw.das.rpc.website;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 详细数据接口管理类
 * 
 * @author wayne
 */
public interface DasUserInfoManager extends Manager {

	/**
	 * 分页查询das_user_info_base表
	 * @param pageNumber 
	 * @param pageSize 
	 * @param DasUserInfoSeachBean model
	 */
	public Map<String, String> dasUserInfoListPage(String jsonStr) throws Exception;
	
	/**
	 * 根据条件刷选用户手机号及邮箱
	 * @param DasUserInfoSeachBean model
	 */
	public Map<String, String> dasUserInfoScreen(String jsonStr) throws Exception;

}
