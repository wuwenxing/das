package com.gw.das.api.service;

import java.util.List;

/**
 * 账户诊断
 * 
 * @author darren
 *
 */
public interface AccountDiagnosisService {

	/**
	 * 账户概况
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List accountOverview(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;

	/**
	 * 收益统计
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List profitStatistics(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;
	
	/**
	 * 收益分析
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List profitAnalysis(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;
	
	/**
	 * 交易产品统计
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List transactionProductStatistics(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;
	
	/**
	 * 持仓时长分布统计
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List positionTimeIntervalStatistics(Integer companyId, String platform, Integer accountNo,String startDate, String endDate)throws Exception;
	
}
