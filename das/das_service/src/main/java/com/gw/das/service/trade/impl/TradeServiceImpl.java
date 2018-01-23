package com.gw.das.service.trade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.trade.TradeIndexDao;
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
import com.gw.das.dao.trade.entity.TradeIndexEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.trade.TradeService;

@Service
public class TradeServiceImpl extends BaseService implements TradeService {

	@Autowired
	private TradeIndexDao tradeIndexDao;
	
	/**
	 * 十大赢家、十大亏损-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10WinnerLoserYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.top10WinnerLoserYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeGts2Mt4Top10TargetDateYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeGts2Mt4Top10TargetDateYears>>() {
				});
		return rowsList;
	}

	/**
	 * 十大交易量客戶-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10TraderYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.top10TraderYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeGts2Mt4Top10TargetDateYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeGts2Mt4Top10TargetDateYears>>() {
				});
		return rowsList;
	}

	/**
	 * 十大存款、十大取款-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> top10CashinCashoutYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.top10CashinCashoutYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeGts2Mt4Top10TargetDateYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeGts2Mt4Top10TargetDateYears>>() {
				});
		return rowsList;
	}

	/**
	 * 十大交易统计-手数-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10VolumeStatisticsYears> top10VolumeStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.top10VolumeStatisticsYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeGts2Mt4Top10VolumeStatisticsYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeGts2Mt4Top10VolumeStatisticsYears>>() {
				});
		return rowsList;
	}
	
	/**
	 * 十大交易统计-持仓时间-不分页查询
	 */
	public List<DasTradeGts2Mt4Top10PositionStatisticsYears> top10PositionStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.top10PositionStatisticsYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeGts2Mt4Top10PositionStatisticsYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeGts2Mt4Top10PositionStatisticsYears>>() {
				});
		return rowsList;
	}
	
	/**
	 * 存取款及账户-净入金-分页查询
	 */
	public PageGrid<TradeSearchModel> cashandaccountdetailStatisticsYearsPage(PageGrid<TradeSearchModel> pageGrid) throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.cashandaccountdetailStatisticsYearsPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasTradeGts2cashandaccountdetailStatisticsYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeGts2cashandaccountdetailStatisticsYears>>() {
				});
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		page.setTotal(Integer.parseInt(total));
		return page;
	}
	
	/**
	 * 存取款及账户-净入金-不分页查询
	 */
	public List<DasTradeGts2cashandaccountdetailStatisticsYears> cashandaccountdetailStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.cashandaccountdetailStatisticsYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeGts2cashandaccountdetailStatisticsYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeGts2cashandaccountdetailStatisticsYears>>() {
				});
		return rowsList;
	}
	
	/**
	 * 公司盈亏-不分页查询
	 */
	public List<DasTradeDealprofithourStatisticsYears> profithourStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.profithourStatisticsYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeDealprofithourStatisticsYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeDealprofithourStatisticsYears>>() {
				});
		return rowsList;
	}
	
	/**
	 * 交易记录总结-不分页查询
	 */
	public List<DasTradeDealprofitdetailStatisticsYears> profitdetailStatisticsYearsList(TradeSearchModel searchModel)
			throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.profitdetailStatisticsYearsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasTradeDealprofitdetailStatisticsYears> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasTradeDealprofitdetailStatisticsYears>>() {
				});
		return rowsList;
	}
	
	/**
	 * 交易记录总结-当日
	 * 交易记录总结-当月累计
	 */
	public List<DasTradeDealprofitdetailStatisticsYears> findDaysOrMonthsSumByProfitdetail(TradeSearchModel searchModel)
			throws Exception{
		RpcResult rpcResult = RpcUtils.post(Constants.findDaysOrMonthsSumByProfitdetail, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		
		String days = resultMap.get("days");
		String months = resultMap.get("months");
		DasTradeDealprofitdetailStatisticsYears daysObj = JacksonUtil.readValue(days, DasTradeDealprofitdetailStatisticsYears.class);
		DasTradeDealprofitdetailStatisticsYears monthsObj = JacksonUtil.readValue(months, DasTradeDealprofitdetailStatisticsYears.class);
		
		// 查询手工录入的数据，写到对象中
		TradeIndexEntity indexDaysObj = tradeIndexDao.findByDateTimeDays(searchModel.getDateTime());//当日
		TradeIndexEntity indexMonthsObj = tradeIndexDao.findByDateTimeMonths(searchModel.getDateTime());//当月
		if(null != indexDaysObj){
			daysObj.setChannelPromotionCosts(indexDaysObj.getChannelPromotionCosts());
			daysObj.setSiteVisitsCount(indexDaysObj.getSiteVisitsCount());
			daysObj.setHandOperHedgeProfitAndLossGts2(indexDaysObj.getHandOperHedgeProfitAndLossGts2());
			daysObj.setHandOperHedgeProfitAndLossMt4(indexDaysObj.getHandOperHedgeProfitAndLossMt4());
			daysObj.setHandOperHedgeProfitAndLossGts(indexDaysObj.getHandOperHedgeProfitAndLossGts());
			daysObj.setHandOperHedgeProfitAndLossMt5(indexDaysObj.getHandOperHedgeProfitAndLossMt5());
			daysObj.setBonusOtherGts2(indexDaysObj.getBonusOtherGts2());
			daysObj.setBonusOtherMt4(indexDaysObj.getBonusOtherMt4());
			daysObj.setBonusOtherGts(indexDaysObj.getBonusOtherGts());
			daysObj.setBonusOtherMt5(indexDaysObj.getBonusOtherMt5());
		}else{
			daysObj.setChannelPromotionCosts(0D);
			daysObj.setSiteVisitsCount(0L);
			daysObj.setHandOperHedgeProfitAndLossGts2(0D);
			daysObj.setHandOperHedgeProfitAndLossMt4(0D);
			daysObj.setHandOperHedgeProfitAndLossGts(0D);
			daysObj.setHandOperHedgeProfitAndLossMt5(0D);
			daysObj.setBonusOtherGts2(0D);
			daysObj.setBonusOtherMt4(0D);
			daysObj.setBonusOtherGts(0D);
			daysObj.setBonusOtherMt5(0D);
		}
		if(null != indexMonthsObj){
			monthsObj.setChannelPromotionCosts(indexMonthsObj.getChannelPromotionCosts());
			monthsObj.setSiteVisitsCount(indexMonthsObj.getSiteVisitsCount());
			monthsObj.setHandOperHedgeProfitAndLossGts2(indexMonthsObj.getHandOperHedgeProfitAndLossGts2());
			monthsObj.setHandOperHedgeProfitAndLossMt4(indexMonthsObj.getHandOperHedgeProfitAndLossMt4());
			monthsObj.setHandOperHedgeProfitAndLossGts(indexMonthsObj.getHandOperHedgeProfitAndLossGts());
			monthsObj.setHandOperHedgeProfitAndLossMt5(indexMonthsObj.getHandOperHedgeProfitAndLossMt5());
			monthsObj.setBonusOtherGts2(indexMonthsObj.getBonusOtherGts2());
			monthsObj.setBonusOtherMt4(indexMonthsObj.getBonusOtherMt4());
			monthsObj.setBonusOtherGts(indexMonthsObj.getBonusOtherGts());
			monthsObj.setBonusOtherMt5(indexMonthsObj.getBonusOtherMt5());
		}else{
			monthsObj.setChannelPromotionCosts(0D);
			monthsObj.setSiteVisitsCount(0L);
			monthsObj.setHandOperHedgeProfitAndLossGts2(0D);
			monthsObj.setHandOperHedgeProfitAndLossMt4(0D);
			monthsObj.setHandOperHedgeProfitAndLossGts(0D);
			monthsObj.setHandOperHedgeProfitAndLossMt5(0D);
			monthsObj.setBonusOtherGts2(0D);
			monthsObj.setBonusOtherMt4(0D);
			monthsObj.setBonusOtherGts(0D);
			monthsObj.setBonusOtherMt5(0D);
		}

		// 返回
		List<DasTradeDealprofitdetailStatisticsYears> rowsList = new ArrayList<DasTradeDealprofitdetailStatisticsYears>();
		rowsList.add(daysObj);
		rowsList.add(monthsObj);
		return rowsList;
	}
	
	/**
	 * 存取款及账户-当日
	 * 存取款及账户-当月累计
	 */
	public List<DasTradeGts2cashandaccountdetailStatisticsYears> findDaysOrMonthsSumByCashandaccountdetail(TradeSearchModel searchModel)
			throws Exception{
		RpcResult rpcResult = RpcUtils.post(Constants.findDaysOrMonthsSumByCashandaccountdetail, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		
		String days = resultMap.get("days");
		String months = resultMap.get("months");
		DasTradeGts2cashandaccountdetailStatisticsYears daysObj = JacksonUtil.readValue(days, DasTradeGts2cashandaccountdetailStatisticsYears.class);
		DasTradeGts2cashandaccountdetailStatisticsYears monthsObj = JacksonUtil.readValue(months, DasTradeGts2cashandaccountdetailStatisticsYears.class);
		
		// 查询手工录入的数据，写到对象中
		TradeIndexEntity indexDaysObj = tradeIndexDao.findByDateTimeDays(searchModel.getDateTime());//当日
		TradeIndexEntity indexMonthsObj = tradeIndexDao.findByDateTimeMonths(searchModel.getDateTime());//当月
		if(null != indexDaysObj){
			daysObj.setAdvisoryCount(indexDaysObj.getAdvisoryCount());
			daysObj.setAdvisoryCustomerCount(indexDaysObj.getAdvisoryCustomerCount());
			daysObj.setBonusOtherGts2(indexDaysObj.getBonusOtherGts2());
			daysObj.setBonusOtherMt4(indexDaysObj.getBonusOtherMt4());
			daysObj.setBonusOtherGts(indexDaysObj.getBonusOtherGts());
			daysObj.setBonusOtherMt5(indexDaysObj.getBonusOtherMt5());
		}else{
			daysObj.setAdvisoryCount(0L);
			daysObj.setAdvisoryCustomerCount(0L);
			daysObj.setBonusOtherGts2(0D);
			daysObj.setBonusOtherMt4(0D);
			daysObj.setBonusOtherGts(0D);
			daysObj.setBonusOtherMt5(0D);
		}
		if(null != indexMonthsObj){
			monthsObj.setAdvisoryCount(indexMonthsObj.getAdvisoryCount());
			monthsObj.setAdvisoryCustomerCount(indexMonthsObj.getAdvisoryCustomerCount());
			monthsObj.setBonusOtherGts2(indexMonthsObj.getBonusOtherGts2());
			monthsObj.setBonusOtherMt4(indexMonthsObj.getBonusOtherMt4());
			monthsObj.setBonusOtherGts(indexMonthsObj.getBonusOtherGts());
			monthsObj.setBonusOtherMt5(indexMonthsObj.getBonusOtherMt5());
		}else{
			monthsObj.setAdvisoryCount(0L);
			monthsObj.setAdvisoryCustomerCount(0L);
			monthsObj.setBonusOtherGts2(0D);
			monthsObj.setBonusOtherMt4(0D);
			monthsObj.setBonusOtherGts(0D);
			monthsObj.setBonusOtherMt5(0D);
		}
		
		// 返回
		List<DasTradeGts2cashandaccountdetailStatisticsYears> rowsList = new ArrayList<DasTradeGts2cashandaccountdetailStatisticsYears>();
		rowsList.add(daysObj);
		rowsList.add(monthsObj);
		return rowsList;
	}
	
	/**
	 * 开户及存款总结
	 */
	public List<OpenAccountAndDepositSummary> openAccountAndDepositSummaryList(TradeSearchModel searchModel) throws Exception{
		RpcResult rpcResult = RpcUtils.post(Constants.openAccountAndDepositSummaryList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<OpenAccountAndDepositSummary> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<OpenAccountAndDepositSummary>>() {
				});
		
		// 转Map
		Map<String, OpenAccountAndDepositSummary> returnMap = new HashMap<String, OpenAccountAndDepositSummary>();
		for(OpenAccountAndDepositSummary entity: rowsList){
			returnMap.put(entity.getDatetime(), entity);
		}
		// 放入交易指标
		TradeIndexEntity indexentity = new TradeIndexEntity();
		indexentity.setStartDate(searchModel.getStartTime());
		indexentity.setEndDate(searchModel.getEndTime());
		List<TradeIndexEntity> list = tradeIndexDao.findList(indexentity);
		for(int i=0; i<list.size(); i++){
			TradeIndexEntity temp = list.get(i);
			String dateStr = temp.getDateTime();
			OpenAccountAndDepositSummary summaryEntity = returnMap.get(dateStr);
			summaryEntity.setAdvisoryCustomerCount(temp.getAdvisoryCustomerCount()==null?0L:temp.getAdvisoryCustomerCount());
			summaryEntity.setAdvisoryCount(temp.getAdvisoryCount()==null?0L:temp.getAdvisoryCount());
			summaryEntity.setCustomerPhoneCount(temp.getCustomerPhoneCount()==null?0L:temp.getCustomerPhoneCount());
		}

		// 将周六周日的数据统计至周五,除了结余
		for(int i=0; i<rowsList.size(); i++){
			OpenAccountAndDepositSummary temp = rowsList.get(i);
			String dateStr = temp.getDatetime();
			Date date = DateUtil.stringToDate(dateStr);
			String days = DateUtil.getWeekOfDate(date);
			if(Constants.weekDays[5].equals(days)){//星期五
				OpenAccountAndDepositSummary entity = returnMap.get(dateStr);
				OpenAccountAndDepositSummary entity_1 = returnMap.get(rowsList.get(i+1).getDatetime());//星期六数据
				OpenAccountAndDepositSummary entity_2 = returnMap.get(rowsList.get(i+2).getDatetime());//星期日数据
				// 将周六周日的数据统计至周五
				entity.setAdvisoryCustomerCount(entity.getAdvisoryCustomerCount() + entity_1.getAdvisoryCustomerCount() + entity_2.getAdvisoryCustomerCount());
				entity.setAdvisoryCount(entity.getAdvisoryCount() + entity_1.getAdvisoryCount() + entity_2.getAdvisoryCount());
				entity.setCustomerPhoneCount(entity.getCustomerPhoneCount() + entity_1.getCustomerPhoneCount() + entity_2.getCustomerPhoneCount());
				//星期六\星期日数据清零
				entity_1.setAdvisoryCustomerCount(0L);
				entity_1.setAdvisoryCount(0L);
				entity_1.setCustomerPhoneCount(0L);
				entity_2.setAdvisoryCustomerCount(0L);
				entity_2.setAdvisoryCount(0L);
				entity_2.setCustomerPhoneCount(0L);
			}
		}
		
		return rowsList;
	}
	
	/**
	 * 账户黑名单列表-分页查询
	 */
	public PageGrid<DimAccountBlackListSearchBean> findDimAccountBlackListPage(PageGrid<DimAccountBlackListSearchBean> pageGrid) throws Exception {
		// 设置查询条件
		DimAccountBlackListSearchBean detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
		RpcResult rpcResult = RpcUtils.post(Constants.findDimAccountBlackListPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DimAccountBlackList> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DimAccountBlackList>>() {
				});
		PageGrid<DimAccountBlackListSearchBean> page = new PageGrid<DimAccountBlackListSearchBean>();
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		page.setTotal(Integer.parseInt(total));
		return page;
	}

	/**
	 * 账户黑名单列表-不分页查询
	 */
	public List<DimAccountBlackList> findDimAccountBlackList(DimAccountBlackListSearchBean searchModel) throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.findDimAccountBlackList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DimAccountBlackList> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DimAccountBlackList>>() {
				});
		return rowsList;
	}
	
	/**
	 * 风控要素-分页查询
	 */
	public PageGrid<DimBlackListSearchBean> findDimBlackListPage(PageGrid<DimBlackListSearchBean> pageGrid) throws Exception {
		// 设置查询条件
		DimBlackListSearchBean detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
		RpcResult rpcResult = RpcUtils.post(Constants.findDimBlackListPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DimBlackList> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DimBlackList>>() {
				});
		PageGrid<DimBlackListSearchBean> page = new PageGrid<DimBlackListSearchBean>();
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		page.setTotal(Integer.parseInt(total));
		return page;
	}

	/**
	 * 风控要素-不分页查询
	 */
	public List<DimBlackList> findDimBlackList(DimBlackListSearchBean searchModel) throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.findDimBlackList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DimBlackList> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DimBlackList>>() {
				});
		return rowsList;
	}
	
}
