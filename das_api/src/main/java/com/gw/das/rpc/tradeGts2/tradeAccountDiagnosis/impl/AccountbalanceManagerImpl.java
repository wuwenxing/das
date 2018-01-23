package com.gw.das.rpc.tradeGts2.tradeAccountDiagnosis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.tradeGts2.entity.AccountbalanceVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;
import com.gw.das.business.dao.tradeGts2.tradeAccountDiagnosis.AccountbalanceESDao;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.tradeGts2.tradeAccountDiagnosis.AccountbalanceManager;

/**
 * 账户余额变动报表接口
 * 
 * @author darren
 *
 */
public class AccountbalanceManagerImpl extends ManagerImpl implements AccountbalanceManager {

	private static final Logger logger = LoggerFactory.getLogger(AccountbalanceManagerImpl.class);

	/**
	 * 
	 * 分页查询账户余额变动报表记录 -elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findAccountbalancePageList(String jsonStr) throws Exception{
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			
			AccountbalanceESDao accountbalanceESDao = getService(AccountbalanceESDao.class);
			pg = accountbalanceESDao.findAccountbalancePageList(model);
			List<AccountbalanceVO> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("分页查询账户余额变动报表记录失败 :" + e.getMessage(), e);
			throw new Exception("分页查询账户余额变动报表记录失败:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * 不分页查询账户余额变动报表记录-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findAccountbalanceList(String jsonStr) throws Exception{
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			AccountbalanceESDao accountbalanceESDao = getService(AccountbalanceESDao.class);
			List<AccountbalanceVO> list = accountbalanceESDao.findAccountbalanceList(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("不分页查询账户余额变动报表记录失败 :" + e.getMessage(), e);
			throw new Exception("不分页查询账户余额变动报表记录失败:" + e.getMessage());
		}
	}
	

}
