package com.gw.das.rpc.tradeGts2.tradeBordereaux.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.tradeGts2.entity.AccountchannelVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.AccountActivechannelWideDao;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.AccountOpenchannelWideDao;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.tradeGts2.tradeBordereaux.AccountchannelManager;

/**
 * 新开户_激活途径比例接口
 * 
 * @author darren
 *
 */
public class AccountchannelManagerImpl extends ManagerImpl implements AccountchannelManager {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountchannelManagerImpl.class);
    
	/**
	 * 分页查询新开户_激活途径比例报表记录
	 */
	@Override
	public Map<String, String> findAccountchannelPageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			String type = model.getType();
			if("open".equals(type)){
				AccountOpenchannelWideDao accountOpenchannelWideDao = getService(AccountOpenchannelWideDao.class);	
				pg = accountOpenchannelWideDao.queryForPage(pg, new AccountchannelVO());
			}else{
				AccountActivechannelWideDao accountActivechannelWideDao = getService(AccountActivechannelWideDao.class);	
				pg = accountActivechannelWideDao.queryForPage(pg, new AccountchannelVO());
			}
			List<AccountchannelVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询新开户_激活途径比例报表接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询新开户_激活途径比例报表接口异常:" + e.getMessage());
		}
	}
    
	/**
	 * 不分页查询新开户_激活途径比例报表记录
	 */
	@Override
	public Map<String, String> findAccountchannelList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			String type = model.getType();
			List<AccountchannelVO> list = new ArrayList<>();
			if("open".equals(type)){
				AccountOpenchannelWideDao accountOpenchannelWideDao = getService(AccountOpenchannelWideDao.class);	
				list = accountOpenchannelWideDao.queryForList(model, new AccountchannelVO());
			}else{
				AccountActivechannelWideDao accountActivechannelWideDao = getService(AccountActivechannelWideDao.class);	
				list = accountActivechannelWideDao.queryForList(model, new AccountchannelVO());
			}
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询新开户_激活途径比例报表接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询新开户_激活途径比例报表接口异常:" + e.getMessage());
		}
	}

}
