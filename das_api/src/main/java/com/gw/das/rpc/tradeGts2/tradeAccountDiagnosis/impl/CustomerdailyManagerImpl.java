package com.gw.das.rpc.tradeGts2.tradeAccountDiagnosis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.tradeGts2.entity.CustomerdailyVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;
import com.gw.das.business.dao.tradeGts2.tradeAccountDiagnosis.CustomerdailyESDao;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.tradeGts2.tradeAccountDiagnosis.CustomerdailyManager;

/**
 * 结算日报表接口
 * 
 * @author darren
 *
 */
public class CustomerdailyManagerImpl extends ManagerImpl implements CustomerdailyManager {

	private static final Logger logger = LoggerFactory.getLogger(CustomerdailyManagerImpl.class);
	
	/**
	 * 
	 * 分页查询结算日报表记录-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findCustomerdailyPageList(String jsonStr) throws Exception{
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			
			CustomerdailyESDao customerdailyESDao = getService(CustomerdailyESDao.class);
			pg = customerdailyESDao.findCustomerdailyPageList(model);
			List<CustomerdailyVO> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("分页查询结算日报表记录失败:" + e.getMessage(), e);
			throw new Exception("分页查询结算日报表记录失败:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * 不分页查询结算日报表记录-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findCustomerdailyList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			CustomerdailyESDao customerdailyESDao = getService(CustomerdailyESDao.class);
			List<CustomerdailyVO> list = customerdailyESDao.findCustomerdailyList(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("不分页查询结算日报表记录失败:" + e.getMessage(), e);
			throw new Exception("不分页查询结算日报表记录失败:" + e.getMessage());
		}
	}


}
