package com.gw.das.rpc.tradeGts2.tradeBordereaux;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 公司盈亏报表接口
 * 
 * @author darren
 *
 */
public interface DealprofithourManager  extends Manager{

	/**
	 * 分页查询公司盈亏报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofithourPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询公司盈亏报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDealprofithourList(String jsonStr) throws Exception;
	
}
