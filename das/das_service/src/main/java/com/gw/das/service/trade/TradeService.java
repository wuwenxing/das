package com.gw.das.service.trade;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.trade.bean.DasTradeDealprofitdetailStatisticsYears;
import com.gw.das.dao.trade.bean.DasTradeDealprofithourStatisticsYears;
import com.gw.das.dao.trade.bean.DasTradeGts2Mt4Top10PositionStatisticsYears;
import com.gw.das.dao.trade.bean.DasTradeGts2Mt4Top10TargetDateYears;
import com.gw.das.dao.trade.bean.DasTradeGts2Mt4Top10VolumeStatisticsYears;
import com.gw.das.dao.trade.bean.DasTradeGts2cashandaccountdetailStatisticsYears;
import com.gw.das.dao.trade.bean.DimAccountBlackList;
import com.gw.das.dao.trade.bean.DimAccountBlackListSearchBean;
import com.gw.das.dao.trade.bean.DimBlackList;
import com.gw.das.dao.trade.bean.DimBlackListSearchBean;
import com.gw.das.dao.trade.bean.OpenAccountAndDepositSummary;
import com.gw.das.dao.trade.bean.TradeSearchModel;

public interface TradeService {
	
	/**
	 * 十大赢家、十大亏损-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10WinnerLoserYearsList(TradeSearchModel searchBean) throws Exception;

	/**
	 * 十大交易量客戶-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10TraderYearsList(TradeSearchModel searchModel) throws Exception;

	/**
	 * 十大存款、十大取款-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10CashinCashoutYearsList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 十大交易统计-持仓时间-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10PositionStatisticsYears> top10PositionStatisticsYearsList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 十大交易统计-手数-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10VolumeStatisticsYears> top10VolumeStatisticsYearsList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 存取款及账户-净入金-分页查询
	 */
	public PageGrid<TradeSearchModel> cashandaccountdetailStatisticsYearsPage(PageGrid<TradeSearchModel> pageGrid) throws Exception;

	/**
	 * 存取款及账户-净入金-不分页查询
	 */
	public List<DasTradeGts2cashandaccountdetailStatisticsYears> cashandaccountdetailStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception;
	
	/**
	 * 公司盈亏-不分页查询
	 */
	public List<DasTradeDealprofithourStatisticsYears> profithourStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception;
	
	/**
	 * 交易记录总结-不分页查询
	 */
	public List<DasTradeDealprofitdetailStatisticsYears> profitdetailStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception;

	/**
	 * 交易记录总结-当日
	 * 交易记录总结-当月累计
	 */
	public List<DasTradeDealprofitdetailStatisticsYears> findDaysOrMonthsSumByProfitdetail(TradeSearchModel searchModel)
			throws Exception;
	
	/**
	 * 存取款及账户-当日
	 * 存取款及账户-当月累计
	 */
	public List<DasTradeGts2cashandaccountdetailStatisticsYears> findDaysOrMonthsSumByCashandaccountdetail(TradeSearchModel searchModel)
			throws Exception;

	/**
	 * 开户及存款总结
	 */
	public List<OpenAccountAndDepositSummary> openAccountAndDepositSummaryList(TradeSearchModel searchModel) throws Exception;
	
	/**
	 * 账户黑名单列表-分页查询
	 */
	public PageGrid<DimAccountBlackListSearchBean> findDimAccountBlackListPage(PageGrid<DimAccountBlackListSearchBean> pageGrid) throws Exception;

	/**
	 * 账户黑名单列表-不分页查询
	 */
	public List<DimAccountBlackList> findDimAccountBlackList(DimAccountBlackListSearchBean searchModel) throws Exception;

	/**
	 * 风控要素-分页查询
	 */
	public PageGrid<DimBlackListSearchBean> findDimBlackListPage(PageGrid<DimBlackListSearchBean> pageGrid) throws Exception;

	/**
	 * 风控要素-不分页查询
	 */
	public List<DimBlackList> findDimBlackList(DimBlackListSearchBean searchModel) throws Exception;
	
}
