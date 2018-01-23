package com.gw.das.rpc.tradeGts2.tradeBordereaux.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.tradeGts2.entity.DealprofithourVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;
import com.gw.das.business.dao.tradeGts2.tradeBordereaux.DealprofithourWideDao;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.tradeGts2.tradeBordereaux.DealprofithourManager;

/**
 * 公司盈亏报表接口
 * 
 * @author darren
 *
 */
public class DealprofithourManagerImpl extends ManagerImpl implements DealprofithourManager{
	
	private static final Logger logger = LoggerFactory.getLogger(DealprofithourManagerImpl.class);
    
	/**
	 * 分页查询公司盈亏报表记录
	 */
	@Override
	public Map<String, String> findDealprofithourPageList(String jsonStr) throws Exception {
		try {
			PageGrid<TradeSearchModel> pg = new PageGrid<TradeSearchModel>();
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			DealprofithourWideDao dealprofithourDao = getService(DealprofithourWideDao.class);
			pg = dealprofithourDao.queryForPage(pg, new DealprofithourVO());
			List<DealprofithourVO> list = pg.getRows();			
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询公司盈亏报表接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询公司盈亏报表接口异常:" + e.getMessage());
		}
	}

	/**
	 * 不分页查询公司盈亏报表记录
	 */
	@Override
	public Map<String, String> findDealprofithourList(String jsonStr) throws Exception {
		try {
			TradeSearchModel model = JacksonUtil.readValue(jsonStr, TradeSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DealprofithourWideDao dealprofithourDao = getService(DealprofithourWideDao.class);
			List<DealprofithourVO> list = dealprofithourDao.queryForList(model,new DealprofithourVO());			
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询公司盈亏报表接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询公司盈亏报表接口异常:" + e.getMessage());
		}
	}

}
