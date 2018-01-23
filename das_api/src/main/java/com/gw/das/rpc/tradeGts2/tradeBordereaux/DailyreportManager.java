package com.gw.das.rpc.tradeGts2.tradeBordereaux;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 结余图表报表接口
 * 
 * @author darren
 *
 */
public interface DailyreportManager  extends Manager{

	/**
	 * 分页查询结余图表报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDailyreportPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询结余图表报表记录
	 * 
	 * @return
	 */
	public Map<String, String> findDailyreportList(String jsonStr) throws Exception;
	
}
