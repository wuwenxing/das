package com.gw.das.rpc.tradeGts2.tradeAccountDiagnosis;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 账户余额变动报表接口
 * 
 * @author darren
 *
 */
public interface AccountbalanceManager  extends Manager{

	/**
	 * 分页查询账户余额变动报表记录 -elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findAccountbalancePageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询账户余额变动报表记录 -elasticsearch
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findAccountbalanceList(String jsonStr) throws Exception;
	
}
