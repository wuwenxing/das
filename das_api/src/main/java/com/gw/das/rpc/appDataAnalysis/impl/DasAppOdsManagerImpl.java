package com.gw.das.rpc.appDataAnalysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.appDataAnalysis.DasAppOdsESDao;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.appDataAnalysis.entity.DasAppOdsVO;
import com.gw.das.rpc.appDataAnalysis.DasAppOdsManager;
import com.gw.das.rpc.base.ManagerImpl;

/**
 * app数据源报表接口
 * 
 * @author darren
 *
 */
public class DasAppOdsManagerImpl extends ManagerImpl implements DasAppOdsManager {

	private static final Logger logger = LoggerFactory.getLogger(DasAppOdsManagerImpl.class);

	/**
	 * 
	 * 分页查app数据源报表记录 -elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findDasAppOdsPageList(String jsonStr) throws Exception{
		try {
			PageGrid<AppDataAnalysisSearchModel> pg = new PageGrid<AppDataAnalysisSearchModel>();
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			
			DasAppOdsESDao dasAppOdsESDao = getService(DasAppOdsESDao.class);
			pg = dasAppOdsESDao.findDasAppOdsPageList(model);
			List<DasAppOdsVO> list = pg.getRows();
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("分页查询app数据源报表记录失败 :" + e.getMessage(), e);
			throw new Exception("分页查询app数据源报表记录失败:" + e.getMessage());
		}
	}
	
	/**
	 * 
	 * 不分页查询app数据源报表记录-elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findDasAppOdsList(String jsonStr) throws Exception{
		try {
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			DasAppOdsESDao dasAppOdsESDao = getService(DasAppOdsESDao.class);
			List<DasAppOdsVO> list = dasAppOdsESDao.findDasAppOdsList(model);
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("不分页查询app数据源报表记录失败 :" + e.getMessage(), e);
			throw new Exception("不分页查询app数据源报表记录失败:" + e.getMessage());
		}
	}

}
