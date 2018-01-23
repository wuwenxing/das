package com.gw.das.rpc.tradeGts2.tradeBordereaux;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * GTS2下单途径比例报表接口
 * 
 * @author darren
 *
 */
public interface DealchannelManager  extends Manager{

	/**
	 * 分页查询GTS2下单途径比例报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealchannelPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询GTS2下单途径比例报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealchannelList(String jsonStr) throws Exception;
	
}
