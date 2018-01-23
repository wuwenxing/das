package com.gw.das.rpc.trade;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * 交易相关数据接口管理类
 * 1、MT4&GTS2十大排名及交易統計
 * @author wayne
 */
public interface TradeManager extends Manager {

	/**
	 * 十大赢家、十大亏损-不分页查询
	 */
	public Map<String, String> top10WinnerLoserYearsList(String jsonStr) throws Exception;
	
	/**
	 * 十大交易量客戶-不分页查询
	 */
	public Map<String, String> top10TraderYearsList(String jsonStr) throws Exception;
	
	/**
	 * 十大存款、十大取款-不分页查询
	 */
	public Map<String, String> top10CashinCashoutYearsList(String jsonStr) throws Exception;
	
	/**
	 * 十大交易统计-手数-不分页查询
	 */
	public Map<String, String> top10VolumeStatisticsYearsList(String jsonStr) throws Exception;
	
	/**
	 * 十大交易统计-持仓时间-不分页查询
	 */
	public Map<String, String> top10PositionStatisticsYearsList(String jsonStr) throws Exception;
	
	/**
	 * 存取款及账户-净入金-分页查询
	 */
	public Map<String, String> cashandaccountdetailStatisticsYearsPage(String jsonStr) throws Exception;
	
	/**
	 * 存取款及账户-净入金-不分页查询
	 */
	public Map<String, String> cashandaccountdetailStatisticsYearsList(String jsonStr) throws Exception;
	
	/**
	 * 公司盈亏-不分页查询
	 */
	public Map<String, String> profithourStatisticsYearsList(String jsonStr) throws Exception;
	
	/**
	 * 交易记录总结-不分页查询
	 */
	public Map<String, String> profitdetailStatisticsYearsList(String jsonStr) throws Exception;
	
	/**
	 * 交易记录总结-当日
	 * 交易记录总结-当月累计
	 */
	public Map<String, String> findDaysOrMonthsSumByProfitdetail(String jsonStr) throws Exception;
	
	/**
	 * 存取款及账户-当日
	 * 存取款及账户-当月累计
	 */
	public Map<String, String> findDaysOrMonthsSumByCashandaccountdetail(String jsonStr) throws Exception;
	
	/**
	 * 账户黑名单列表-分页查询
	 */
	public Map<String, String> findDimAccountBlackListPage(String jsonStr) throws Exception;
	
	/**
	 * 账户黑名单列表-不分页查询
	 */
	public Map<String, String> findDimAccountBlackList(String jsonStr) throws Exception;
	
	/**
	 * 风控要素列表-分页查询
	 */
	public Map<String, String> findDimBlackListPage(String jsonStr) throws Exception;
	
	/**
	 * 风控要素列表-不分页查询
	 */
	public Map<String, String> findDimBlackList(String jsonStr) throws Exception;

	/**
	 * 净持仓列表-分页查询
	 */
	public Map<String, String> findNetPositionListPage(String jsonStr) throws Exception;
	
	/**
	 * 净持仓列表-不分页查询
	 */
	public Map<String, String> findNetPositionList(String jsonStr) throws Exception;
	
}
