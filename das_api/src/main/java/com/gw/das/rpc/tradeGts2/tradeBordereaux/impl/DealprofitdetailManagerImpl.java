package com.gw.das.rpc.tradeGts2.tradeBordereaux.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.tradeGts2.entity.DealcategoryVO;
import com.gw.das.business.dao.tradeGts2.entity.DealprofitdetailMT4AndGts2VO;
import com.gw.das.business.dao.tradeGts2.entity.DealprofitdetailUseraAndDealamountVO;
import com.gw.das.business.dao.tradeGts2.entity.DealprofitdetailVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;
import com.gw.das.business.dao.tradeGts2.entity.TradeSituationVO;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.AverageTransactionVolumeWideDao;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.DealcategoryAxuAxgcnhWideDao;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.DealcategoryWideDao;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.DealprofitdetailMT4AndGtsDao;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.DealprofitdetailUseraAndDealamountDao;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.DealprofitdetailWideDao;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.TradeSituationWideDao;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.tradeGts2.tradeBordereaux.DealprofitdetailManager;

/**
 * 交易记录总结报表(包括了交易手数图表和毛利图表)/交易类别数据接口
 * 
 * @author darren
 *
 */
public class DealprofitdetailManagerImpl  extends ManagerImpl implements DealprofitdetailManager {

	private static final Logger logger = LoggerFactory.getLogger(DealprofitdetailManagerImpl.class);

	/**
	 * 分页查询交易人数/次数报表记录
	 */
	@Override
	public Map<String, String> findDealprofitdetailUseraAndDealamountPageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DealprofitdetailUseraAndDealamountDao dealprofitdetailDao = getService(DealprofitdetailUseraAndDealamountDao.class);
			pg = dealprofitdetailDao.queryForPage(pg, new DealprofitdetailUseraAndDealamountVO());
			List<DealprofitdetailUseraAndDealamountVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询分页查询交易人数/次数报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询分页查询交易人数/次数报表记录接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询交易人数/次数报表记录
	 */
	@Override
	public Map<String, String> findDealprofitdetailUseraAndDealamountList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DealprofitdetailUseraAndDealamountDao dealprofitdetailDao = getService(DealprofitdetailUseraAndDealamountDao.class);
			List<DealprofitdetailUseraAndDealamountVO> list = dealprofitdetailDao.queryForList(model,new DealprofitdetailUseraAndDealamountVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询不分页查询交易人数/次数接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询不分页查询交易人数/次数接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询MT4/GTS2数据报表记录
	 */
	@Override
	public Map<String, String> findDealprofitdetailMT4AndGts2PageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DealprofitdetailMT4AndGtsDao dealprofitdetailDao = getService(DealprofitdetailMT4AndGtsDao.class);
			pg = dealprofitdetailDao.queryForPage(pg, new DealprofitdetailMT4AndGts2VO());
			List<DealprofitdetailMT4AndGts2VO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询MT4/GTS2数据报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询MT4/GTS2数据报表记录接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询MT4/GTS2数据报表记录
	 */
	@Override
	public Map<String, String> findDealprofitdetailMT4AndGts2List(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DealprofitdetailMT4AndGtsDao dealprofitdetailDao = getService(DealprofitdetailMT4AndGtsDao.class);
			List<DealprofitdetailMT4AndGts2VO> list = dealprofitdetailDao.queryForList(model,new DealprofitdetailMT4AndGts2VO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询MT4/GTS2数据报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询MT4/GTS2数据报表记录异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 */
	@Override
	public Map<String, String> findDealprofitdetailPageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DealprofitdetailWideDao dealprofitdetailDao = getService(DealprofitdetailWideDao.class);
			pg = dealprofitdetailDao.queryForPage(pg, new DealprofitdetailVO());
			List<DealprofitdetailVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询交易记录总结报表(包括了交易手数图表和毛利图表)接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询交易记录总结报表(包括了交易手数图表和毛利图表)接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询交易记录总结报表(包括了交易手数图表和毛利图表)记录
	 */
	@Override
	public Map<String, String> findDealprofitdetailList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DealprofitdetailWideDao dealprofitdetailDao = getService(DealprofitdetailWideDao.class);
			List<DealprofitdetailVO> list = dealprofitdetailDao.queryForList(model,new DealprofitdetailVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询交易记录总结报表(包括了交易手数图表和毛利图表)接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询交易记录总结报表(包括了交易手数图表和毛利图表)异常:" + e.getMessage());
		}
	}

	/**
	 * 分页查询交易类别数据记录
	 */
	@Override
	public Map<String, String> findDealcategoryPageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DealcategoryWideDao dealcategoryDao = getService(DealcategoryWideDao.class);
			pg = dealcategoryDao.queryForPage(pg, new DealcategoryVO());
			List<DealcategoryVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询交易类别数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询交易类别数据接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询交易类别数据记录
	 */
	@Override
	public Map<String, String> findDealcategoryList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DealcategoryWideDao dealcategoryDao = getService(DealcategoryWideDao.class);
			List<DealcategoryVO> list = dealcategoryDao.queryForList(model,new DealcategoryVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询交易类别数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询交易类别数据异常:" + e.getMessage());
		}
	}

	/**
	 * 分页查询人均交易手数记录
	 */
	@Override
	public Map<String, String> findAverageTransactionVolumePageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			AverageTransactionVolumeWideDao dealprofitdetailDao = getService(AverageTransactionVolumeWideDao.class);
			pg = dealprofitdetailDao.queryForPage(pg, new DealprofitdetailVO());
			List<DealprofitdetailVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询人均交易手数记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询人均交易手数记录接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询人均交易手数记录
	 */
	@Override
	public Map<String, String> findAverageTransactionVolumeList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			AverageTransactionVolumeWideDao dealprofitdetailDao = getService(AverageTransactionVolumeWideDao.class);
			List<DealprofitdetailVO> list = dealprofitdetailDao.queryForList(model,new DealprofitdetailVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询人均交易手数记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询人均交易手数记录接口异常:" + e.getMessage());
		}
	}

	/**
	 * 分页查询交易类别倫敦/人民幣数据记录
	 */
	@Override
	public Map<String, String> findDealcategoryAxuAxgcnhPageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DealcategoryAxuAxgcnhWideDao dealcategoryDao = getService(DealcategoryAxuAxgcnhWideDao.class);
			pg = dealcategoryDao.queryForPage(pg, new DealcategoryVO());
			List<DealcategoryVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询交易类别数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询交易类别数据接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询交易类别倫敦/人民幣数据记录
	 */
	@Override
	public Map<String, String> findDealcategoryAxuAxgcnhList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DealcategoryAxuAxgcnhWideDao dealcategoryDao = getService(DealcategoryAxuAxgcnhWideDao.class);
			List<DealcategoryVO> list = dealcategoryDao.queryForList(model,new DealcategoryVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询交易类别数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询交易类别数据异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询交易情况记录
	 */
	@Override
	public Map<String, String> findTradeSituationPageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			TradeSituationWideDao tradeSituationWideDao = getService(TradeSituationWideDao.class);
			pg = tradeSituationWideDao.queryForPage(pg, new TradeSituationVO());
			List<TradeSituationVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询交易情况记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询交易情况记录数据接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询交易情况记录
	 */
	@Override
	public Map<String, String> findTradeSituationList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			TradeSituationWideDao tradeSituationWideDao = getService(TradeSituationWideDao.class);
			List<TradeSituationVO> list = tradeSituationWideDao.queryForList(model,new TradeSituationVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询交易情况记录数据接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询交易情况记录数据异常:" + e.getMessage());
		}
	}

	
	
}
