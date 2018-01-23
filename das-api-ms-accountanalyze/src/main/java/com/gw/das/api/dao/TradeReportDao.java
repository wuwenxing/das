package com.gw.das.api.dao;

import java.util.Map;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;

/**
 * 交易报表Dao实现类
 * 
 * @author darren
 *
 */
public interface TradeReportDao{
	
	/**
	 * 平仓交易记录不分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiResult tradeDetailCloseList(ApiResult result, String sql, Map<String, Object> args) throws Exception ;
	
	/**
	 * 平仓交易记录分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiPageResult tradeDetailClosePageList(ApiPageResult result, String sql, Map<String, Object> args) throws Exception;
	
	/**
	 * 持仓交易记录不分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiResult tradeDetailOpenList(ApiResult result, String sql, Map<String, Object> args) throws Exception ;
	
	/**
	 * 持仓交易记录分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiPageResult tradeDetailOpenPageList(ApiPageResult result, String sql, Map<String, Object> args) throws Exception;
	
	/**
	 * 账户余额变动记录不分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiResult accountbalanceList(ApiResult result, String sql, Map<String, Object> args) throws Exception ;
	
	/**
	 * 账户余额变动记录分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiPageResult accountbalancePageList(ApiPageResult result, String sql, Map<String, Object> args) throws Exception;
	
	/**
	 * 账户结算日报表记录不分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiResult customerdailyList(ApiResult result, String sql, Map<String, Object> args) throws Exception ;
	
	/**
	 * 账户结算日报表记录分页查询
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiPageResult customerdailyPageList(ApiPageResult result, String sql, Map<String, Object> args) throws Exception;
}
