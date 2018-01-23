package com.gw.das.service.market;

import java.util.List;
import java.util.Map;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;

public interface AccountAnalyzeService {

	public AccountAnalyzeEntity findById(Long id) throws Exception;

	public void saveOrUpdate(AccountAnalyzeEntity entity) throws Exception;

	public void update(AccountAnalyzeEntity entity, String loginName, String loginIp) throws Exception;
	
	public void updateGenerateStatus(AccountAnalyzeEntity entity, String loginName, String loginIp) throws Exception;

	public void updateSendStatus(AccountAnalyzeEntity entity, String loginName, String loginIp) throws Exception;
	
	public void deleteByIdArray(String idArray) throws Exception;

	public List<AccountAnalyzeEntity> findList(AccountAnalyzeEntity accountAnalyzeEntity) throws Exception;

	public PageGrid<AccountAnalyzeEntity> findPageList(PageGrid<AccountAnalyzeEntity> pageGrid) throws Exception;

	/**
	 * 诊断接口地址-获取保证金水平走势
	 */
	public Object marginLevel(AccountAnalyzeEntity entity) throws Exception;
	/**
	 * 诊断接口地址-获取3笔最大盈利单
	 */
	public Object topProfit(AccountAnalyzeEntity entity) throws Exception;
	/**
	 * 诊断接口地址-获取3笔最大亏损单
	 */
	public Object topLoss(AccountAnalyzeEntity entity) throws Exception;
	/**
	 * 诊断接口地址-平仓单的盈亏金额与持仓时间
	 */
	public Object profitAndLossAmountAndTime(AccountAnalyzeEntity entity) throws Exception;
	/**
	 * 诊断接口地址-账户概况
	 */
	public Map accountOverview(AccountAnalyzeEntity entity) throws Exception;
	/**
	 * 诊断接口地址-收益统计
	 */
	public Object profitStatistics(AccountAnalyzeEntity entity) throws Exception;
	/**
	 * 诊断接口地址-收益分析
	 */
	public Map profitAnalysis(AccountAnalyzeEntity entity) throws Exception;
	
}
