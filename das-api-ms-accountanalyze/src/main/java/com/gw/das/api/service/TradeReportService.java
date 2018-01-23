package com.gw.das.api.service;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;

/**
 * 交易报表Service
 * 
 * @author darren
 *
 */
public interface TradeReportService {
	
	/**
	 * 平仓交易记录分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiPageResult tradeDetailClosePageList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate,String sort, String order,int pageNumber,int pageSize)throws Exception;

	/**
	 * 平仓交易记录不分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiResult tradeDetailCloseList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;

	/**
	 * 持仓交易记录分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiPageResult tradeDetailOpenPageList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate,String sort, String order,int pageNumber,int pageSize)throws Exception;
	
	/**
	 * 持仓交易记录不分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiResult tradeDetailOpenList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;
	
	/**
	 * 账户余额变动记录分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiPageResult accountbalancePageList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate,String sort, String order,int pageNumber,int pageSize)throws Exception;
	
	/**
	 * 账户余额变动记录不分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiResult accountbalanceList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;
	
	/**
	 * 账户结算日报表记录分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiPageResult customerdailyPageList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate,String sort, String order,int pageNumber,int pageSize)throws Exception;
	
	/**
	 * 账户结算日报表记录不分页查询
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ApiResult customerdailyList(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;
	
	
}
