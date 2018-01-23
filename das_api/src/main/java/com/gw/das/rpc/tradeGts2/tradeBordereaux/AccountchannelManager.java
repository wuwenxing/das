package com.gw.das.rpc.tradeGts2.tradeBordereaux;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 新开户_激活途径比例接口
 * 
 * @author darren
 *
 */
public interface AccountchannelManager  extends Manager{

	/**
	 * 分页查询新开户_激活途径比例报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findAccountchannelPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询新开户_激活途径比例报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findAccountchannelList(String jsonStr) throws Exception;
	
}
