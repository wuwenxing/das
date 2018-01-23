package com.gw.das.rpc.appDataAnalysis.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.appDataAnalysis.AppDataAnalysisDao;
import com.gw.das.business.dao.appDataAnalysis.AppDataAnalysisDetailsDao;
import com.gw.das.business.dao.appDataAnalysis.OpenSourceAnalysisDao;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisDetailsVO;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisVO;
import com.gw.das.business.dao.appDataAnalysis.entity.OpenSourceAnalysisVO;
import com.gw.das.business.dao.website.entity.DasStatisticsReport;
import com.gw.das.rpc.appDataAnalysis.AppDataAnalysisManager;
import com.gw.das.rpc.base.ManagerImpl;

/**
 * App报表接口
 * 
 * @author darren
 *
 */
public class AppDataAnalysisManagerImpl extends ManagerImpl implements AppDataAnalysisManager {

	private static final Logger logger = LoggerFactory.getLogger(AppDataAnalysisManagerImpl.class);

	/**
	 * 分页查询APP数据分析报表记录
	 */
	@Override
	public Map<String, String> findAppDataAnalysisPageList(String jsonStr) throws Exception{
		try {
			PageGrid<AppDataAnalysisSearchModel> pg = new PageGrid<AppDataAnalysisSearchModel>();
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			AppDataAnalysisDao appDataAnalysisDao = getService(AppDataAnalysisDao.class);
			pg = appDataAnalysisDao.queryForPage(pg, new AppDataAnalysisVO());
			List<AppDataAnalysisVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询APP数据分析报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询APP数据分析报表记录接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 不分页查询APP数据分析报表记录
	 */
	@Override
	public Map<String, String> findAppDataAnalysisList(String jsonStr) throws Exception{
		try {
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			AppDataAnalysisDao appDataAnalysisDao = getService(AppDataAnalysisDao.class);
			List<AppDataAnalysisVO> list = appDataAnalysisDao.queryForList(model,
					new AppDataAnalysisVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询APP数据分析报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询APP数据分析报表记录接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询APP数据分析详情报表记录
	 */
	@Override
	public Map<String, String> findAppDataAnalysisDetailsPageList(String jsonStr) throws Exception{
		try {
			PageGrid<AppDataAnalysisSearchModel> pg = new PageGrid<AppDataAnalysisSearchModel>();
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			AppDataAnalysisDetailsDao appDataAnalysisDetailsDao = getService(AppDataAnalysisDetailsDao.class);
			pg = appDataAnalysisDetailsDao.queryForPage(pg, new AppDataAnalysisDetailsVO());
			List<AppDataAnalysisDetailsVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询APP数据分析详情报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询APP数据分析详情报表记录接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 不分页查询APP数据分析详情报表记录
	 */
	@Override
	public Map<String, String> findAppDataAnalysisDetailsList(String jsonStr) throws Exception{
		try {
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			AppDataAnalysisDetailsDao appDataAnalysisDetailsDao = getService(AppDataAnalysisDetailsDao.class);
			List<AppDataAnalysisDetailsVO> list = appDataAnalysisDetailsDao.queryForList(model,
					new AppDataAnalysisDetailsVO());
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用不分页查询APP数据分析详情报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用不分页查询APP数据分析详情报表记录接口异常:" + e.getMessage());
		}
	}
	
	/**
	 * 分页查询开户来源分析报表记录
	 */
	@Override
	public Map<String, String> findOpenSourceAnalysisPageList(String jsonStr) throws Exception{
		try {
			PageGrid<AppDataAnalysisSearchModel> pg = new PageGrid<AppDataAnalysisSearchModel>();
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setTableType("1");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			OpenSourceAnalysisDao openSourceAnalysisDao = getService(OpenSourceAnalysisDao.class);
			pg = openSourceAnalysisDao.queryForPage(pg, new DasStatisticsReport());
			List<OpenSourceAnalysisVO> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用分页查询开户来源分析报表记录接口异常:" + e.getMessage(), e);
			throw new Exception("调用分页查询开户来源分析报表记录接口异常:" + e.getMessage());
		}
	}

}
