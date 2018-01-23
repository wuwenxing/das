package com.gw.das.rpc.trade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.trade.DasTradeDealprofitdetailStatisticsYearsDao;
import com.gw.das.business.dao.trade.DasTradeDealprofithourStatisticsYearsDao;
import com.gw.das.business.dao.trade.DasTradeGts2Mt4Top10CashinCashoutYearsDao;
import com.gw.das.business.dao.trade.DasTradeGts2Mt4Top10PositionStatisticsYearsDao;
import com.gw.das.business.dao.trade.DasTradeGts2Mt4Top10TraderYearsDao;
import com.gw.das.business.dao.trade.DasTradeGts2Mt4Top10VolumeStatisticsYearsDao;
import com.gw.das.business.dao.trade.DasTradeGts2Mt4Top10WinnerLoserYearsDao;
import com.gw.das.business.dao.trade.DasTradeGts2cashandaccountdetailStatisticsYearsDao;
import com.gw.das.business.dao.trade.DimAccountBlackListDao;
import com.gw.das.business.dao.trade.DimBlackListDao;
import com.gw.das.business.dao.trade.entity.DasTradeDealprofitdetailStatisticsYears;
import com.gw.das.business.dao.trade.entity.DasTradeDealprofithourStatisticsYears;
import com.gw.das.business.dao.trade.entity.DasTradeGts2Mt4Top10PositionStatisticsYears;
import com.gw.das.business.dao.trade.entity.DasTradeGts2Mt4Top10TargetDateYears;
import com.gw.das.business.dao.trade.entity.DasTradeGts2Mt4Top10VolumeStatisticsYears;
import com.gw.das.business.dao.trade.entity.DasTradeGts2cashandaccountdetailStatisticsYears;
import com.gw.das.business.dao.trade.entity.DimAccountBlackList;
import com.gw.das.business.dao.trade.entity.DimAccountBlackListSearchBean;
import com.gw.das.business.dao.trade.entity.DimBlackList;
import com.gw.das.business.dao.trade.entity.DimBlackListSearchBean;
import com.gw.das.business.dao.trade.entity.OpenAccountAndDepositSummary;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.trade.TradeManager;

/**
 * 交易相关数据接口管理类
 * 1、MT4&GTS2十大排名及交易統計
 * @author wayne
 */
public class TradeManagerImpl extends ManagerImpl implements TradeManager {

	private static final Logger logger = LoggerFactory.getLogger(TradeManagerImpl.class);

	/**
	 * 十大赢家、十大亏损-不分页查询
	 */
	@Override
	public Map<String, String> top10WinnerLoserYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeGts2Mt4Top10TargetDateYears.class, model.getSort()));
			DasTradeGts2Mt4Top10WinnerLoserYearsDao dao = getService(DasTradeGts2Mt4Top10WinnerLoserYearsDao.class);
			List<DasTradeGts2Mt4Top10TargetDateYears> list = dao.findTop10WinnerLoser(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[十大赢家、十大亏损]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[十大赢家、十大亏损]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 十大交易量客戶-不分页查询
	 */
	@Override
	public Map<String, String> top10TraderYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeGts2Mt4Top10TargetDateYears.class, model.getSort()));
			DasTradeGts2Mt4Top10TraderYearsDao dao = getService(DasTradeGts2Mt4Top10TraderYearsDao.class);
			List<DasTradeGts2Mt4Top10TargetDateYears> list = dao.findTop10Trader(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[十大交易量客戶]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[十大交易量客戶]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 十大存款、十大取款-不分页查询
	 */
	@Override
	public Map<String, String> top10CashinCashoutYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeGts2Mt4Top10TargetDateYears.class, model.getSort()));
			DasTradeGts2Mt4Top10CashinCashoutYearsDao dao = getService(DasTradeGts2Mt4Top10CashinCashoutYearsDao.class);
			List<DasTradeGts2Mt4Top10TargetDateYears> list = dao.findTop10CashinCashout(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[十大存款、十大取款]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[十大存款、十大取款]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 十大交易统计-手数-不分页查询
	 */
	@Override
	public Map<String, String> top10VolumeStatisticsYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeGts2Mt4Top10VolumeStatisticsYears.class, model.getSort()));
			DasTradeGts2Mt4Top10VolumeStatisticsYearsDao dao = getService(DasTradeGts2Mt4Top10VolumeStatisticsYearsDao.class);
			List<DasTradeGts2Mt4Top10VolumeStatisticsYears> list = dao.findTop10VolumeStatistics(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[十大交易统计-手数]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[十大交易统计-手数]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 十大交易统计-持仓时间-不分页查询
	 */
	@Override
	public Map<String, String> top10PositionStatisticsYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeGts2Mt4Top10PositionStatisticsYears.class, model.getSort()));
			DasTradeGts2Mt4Top10PositionStatisticsYearsDao dao = getService(DasTradeGts2Mt4Top10PositionStatisticsYearsDao.class);
			List<DasTradeGts2Mt4Top10PositionStatisticsYears> list = dao.findTop10VolumeStatistics(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[十大交易统计-持仓时间]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[十大交易统计-持仓时间]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 存取款及账户-净入金-分页查询
	 */
	@Override
	public Map<String, String> cashandaccountdetailStatisticsYearsPage(String jsonStr) throws Exception{
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(OrmUtil.reflectColumn(DasTradeGts2cashandaccountdetailStatisticsYears.class, model.getSort()));
			pg.setOrder(model.getOrder());
			DasTradeGts2cashandaccountdetailStatisticsYearsDao dao = getService(DasTradeGts2cashandaccountdetailStatisticsYearsDao.class);
			pg = dao.queryForPage(pg, new DasTradeGts2cashandaccountdetailStatisticsYears());
			List<DasTradeGts2cashandaccountdetailStatisticsYears> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal() + "");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[存取款及账户]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[存取款及账户]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 存取款及账户-净入金-不分页查询
	 */
	@Override
	public Map<String, String> cashandaccountdetailStatisticsYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeGts2cashandaccountdetailStatisticsYears.class, model.getSort()));
			DasTradeGts2cashandaccountdetailStatisticsYearsDao dao = getService(DasTradeGts2cashandaccountdetailStatisticsYearsDao.class);
			List<DasTradeGts2cashandaccountdetailStatisticsYears> list = dao.queryForList(model, new DasTradeGts2cashandaccountdetailStatisticsYears());	
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[存取款及账户]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[存取款及账户]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 公司盈亏-不分页查询
	 */
	@Override
	public Map<String, String> profithourStatisticsYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeDealprofithourStatisticsYears.class, model.getSort()));
			DasTradeDealprofithourStatisticsYearsDao dao = getService(DasTradeDealprofithourStatisticsYearsDao.class);
			List<DasTradeDealprofithourStatisticsYears> list = dao.queryForList(model, new DasTradeDealprofithourStatisticsYears());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[公司盈亏]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[公司盈亏]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 交易记录总结-不分页查询
	 */
	@Override
	public Map<String, String> profitdetailStatisticsYearsList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeDealprofitdetailStatisticsYears.class, model.getSort()));
			DasTradeDealprofitdetailStatisticsYearsDao dao = getService(DasTradeDealprofitdetailStatisticsYearsDao.class);
			List<DasTradeDealprofitdetailStatisticsYears> list = dao.queryForList(model, new DasTradeDealprofitdetailStatisticsYears());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[交易记录总结]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[交易记录总结]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 交易记录总结-当日
	 * 交易记录总结-当月累计
	 */
	public Map<String, String> findDaysOrMonthsSumByProfitdetail(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeDealprofitdetailStatisticsYears.class, model.getSort()));
			DasTradeDealprofitdetailStatisticsYearsDao dao = getService(DasTradeDealprofitdetailStatisticsYearsDao.class);
			//DasTradeDealprofitdetailStatisticsYears days = dao.findDaysByProfitdetail(model);
			//DasTradeDealprofitdetailStatisticsYears months = dao.findMonthsSumByProfitdetail(model);
			
			DasTradeDealprofitdetailStatisticsYears days = dao.findDaysByProfitdetailWide(model);
			DasTradeDealprofitdetailStatisticsYears months = dao.findMonthsSumByProfitdetailWide(model);
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("days", JacksonUtil.toJSon(days));
			resultMap.put("months", JacksonUtil.toJSon(months));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[交易记录总结-当日-当月累计]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[交易记录总结-当日-当月累计]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 存取款及账户-当日
	 * 存取款及账户-当月累计
	 */
	public Map<String, String> findDaysOrMonthsSumByCashandaccountdetail(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(DasTradeGts2cashandaccountdetailStatisticsYears.class, model.getSort()));
			DasTradeGts2cashandaccountdetailStatisticsYearsDao dao = getService(DasTradeGts2cashandaccountdetailStatisticsYearsDao.class);
			DasTradeGts2cashandaccountdetailStatisticsYears days = dao.findDaysByCashandaccountdetail(model);
			DasTradeGts2cashandaccountdetailStatisticsYears months = dao.findMonthsSumByCashandaccountdetail(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("days", JacksonUtil.toJSon(days));
			resultMap.put("months", JacksonUtil.toJSon(months));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[存取款及账户-当日-当月累计]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[存取款及账户-当日-当月累计]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 账户黑名单列表-分页查询
	 */
	@Override
	public Map<String, String> findDimAccountBlackListPage(String jsonStr) throws Exception{
		try {
			PageGrid<DimAccountBlackListSearchBean> pg = new PageGrid<DimAccountBlackListSearchBean>();
			DimAccountBlackListSearchBean model = JacksonUtil.readValue(jsonStr, DimAccountBlackListSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(OrmUtil.reflectColumn(DimAccountBlackList.class, model.getSort()));
			pg.setOrder(model.getOrder());
			DimAccountBlackListDao dao = getService(DimAccountBlackListDao.class);
			pg = dao.queryForPage(pg, new DimAccountBlackList());
			List<DimAccountBlackList> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal() + "");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[账户黑名单列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[账户黑名单列表]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 账户黑名单列表-不分页查询
	 */
	@Override
	public Map<String, String> findDimAccountBlackList(String jsonStr) throws Exception{
		try {
			DimAccountBlackListSearchBean model = JacksonUtil.readValue(jsonStr, DimAccountBlackListSearchBean.class);
			model.setSort(OrmUtil.reflectColumn(DimAccountBlackList.class, model.getSort()));
			DimAccountBlackListDao dao = getService(DimAccountBlackListDao.class);
			List<DimAccountBlackList> list = dao.queryForList(model, new DimAccountBlackList());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[账户黑名单列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[账户黑名单列表]接口异常:" + e.getMessage());
		}
	}

	/**
	 * 风控要素列表-分页查询
	 */
	public Map<String, String> findDimBlackListPage(String jsonStr) throws Exception{
		try {
			PageGrid<DimBlackListSearchBean> pg = new PageGrid<DimBlackListSearchBean>();
			DimBlackListSearchBean model = JacksonUtil.readValue(jsonStr, DimBlackListSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(OrmUtil.reflectColumn(DimBlackList.class, model.getSort()));
			pg.setOrder(model.getOrder());
			DimBlackListDao dao = getService(DimBlackListDao.class);
			pg = dao.queryForPage(pg, new DimBlackList());
			List<DimBlackList> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal() + "");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[风控要素列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[风控要素列表]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 风控要素列表-不分页查询
	 */
	public Map<String, String> findDimBlackList(String jsonStr) throws Exception{
		try {
			DimBlackListSearchBean model = JacksonUtil.readValue(jsonStr, DimBlackListSearchBean.class);
			model.setSort(OrmUtil.reflectColumn(DimBlackList.class, model.getSort()));
			DimBlackListDao dao = getService(DimBlackListDao.class);
			List<DimBlackList> list = dao.queryForList(model, new DimBlackList());
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[风控要素列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[风控要素列表]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 净持仓列表-分页查询
	 */
	public Map<String, String> findNetPositionListPage(String jsonStr) throws Exception{
		try {
			return null;
		} catch (Exception e) {
			logger.error("调用[净持仓列表列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[净持仓列表列表]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 净持仓列表-不分页查询
	 */
	public Map<String, String> findNetPositionList(String jsonStr) throws Exception{
		try {
			return null;
		} catch (Exception e) {
			logger.error("调用[净持仓列表列表]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[净持仓列表列表]接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 开户及存款总结
	 */
	public Map<String, String> openAccountAndDepositSummaryList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setSort(OrmUtil.reflectColumn(OpenAccountAndDepositSummary.class, model.getSort()));
			DasTradeGts2cashandaccountdetailStatisticsYearsDao dao = getService(DasTradeGts2cashandaccountdetailStatisticsYearsDao.class);
			List<OpenAccountAndDepositSummary> list = dao.openAccountAndDepositSummaryList(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[开户及存款总结]接口异常:" + e.getMessage(), e);
			throw new Exception("调用开户及存款总结]接口异常:" + e.getMessage());
		}
	}
	
	
}
