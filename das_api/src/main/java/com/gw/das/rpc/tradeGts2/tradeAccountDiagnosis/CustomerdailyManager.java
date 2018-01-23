package com.gw.das.rpc.tradeGts2.tradeAccountDiagnosis;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 结算日记录报表接口
 * 
 * @author darren
 *
 */
public interface CustomerdailyManager extends Manager {
	
	/**
	 * 分页查询结算日报表记录-elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findCustomerdailyPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询结算日报表记录-elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findCustomerdailyList(String jsonStr) throws Exception;


}
