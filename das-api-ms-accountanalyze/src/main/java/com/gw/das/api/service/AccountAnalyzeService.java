package com.gw.das.api.service;

import java.util.List;

/**
 * 账户诊断相关实现
 * 
 * @author wayne
 */
public interface AccountAnalyzeService {

	/**
	 * 获取保证金水平走势
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List marginLevel(String companyId, String platform, String accountNo, String startDate, String endDate)throws Exception;

	/**
	 * 获取3笔最大盈利单与亏损单的记录信息
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @param type
	 *            1盈利单，0亏损单
	 * @return
	 */
	public List topProfitAndLoss(String companyId, String platform, String accountNo, String startDate, String endDate, int type);

	/**
	 * 平仓单的盈亏金额与持仓时间
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List profitAndLossAmountAndTime(String companyId, String platform, String accountNo, String startDate, String endDate);
}
