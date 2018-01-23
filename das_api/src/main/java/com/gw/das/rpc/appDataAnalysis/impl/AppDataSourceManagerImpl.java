package com.gw.das.rpc.appDataAnalysis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.appDataAnalysis.AppDataSourceDao;
import com.gw.das.business.dao.appDataAnalysis.AppDataSourceESDao;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.appDataAnalysis.entity.DasAppOdsVO;
import com.gw.das.rpc.appDataAnalysis.AppDataSourceManager;
import com.gw.das.rpc.base.ManagerImpl;

/**
 * app数据源报表接口
 * 
 * @author darren
 *
 */
public class AppDataSourceManagerImpl extends ManagerImpl implements AppDataSourceManager {

	private static final Logger logger = LoggerFactory.getLogger(AppDataSourceManagerImpl.class);

	/**
	 * 
	 * 分页查app数据源报表记录 -elasticsearch
	 * 
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, String> findAppDataSourcePageList(String jsonStr) throws Exception{
		try {
			PageGrid<AppDataAnalysisSearchModel> pg = new PageGrid<AppDataAnalysisSearchModel>();
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort(model.getSort());
			pg.setOrder(model.getOrder());
			
			if(StringUtils.isBlank(model.getReportType())){
				AppDataSourceESDao appDataSourceESDao = getService(AppDataSourceESDao.class);
				pg = appDataSourceESDao.findDasAppOdsPageList(model);
			}else{
				if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
					pg.setSort("datetime,hour");
					pg.setOrder("desc,asc");
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					pg.setSort("datetime,weeks");
					pg.setOrder("desc,asc");
				}else{
					pg.setSort("datetime");
					pg.setOrder("desc");
				}

				AppDataSourceDao appDataSourceDao = getService(AppDataSourceDao.class);
				DasAppOdsVO dasAppOdsVO = new DasAppOdsVO();
				pg = appDataSourceDao.queryForPage(pg, dasAppOdsVO);
			}

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
	public Map<String, String> findAppDataSourceList(String jsonStr) throws Exception{
		try {
			AppDataAnalysisSearchModel model = JacksonUtil.readValue(jsonStr, AppDataAnalysisSearchModel.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			List<DasAppOdsVO> list = new ArrayList<DasAppOdsVO>();
			if(StringUtils.isBlank(model.getReportType())){
				AppDataSourceESDao appDataSourceESDao = getService(AppDataSourceESDao.class);
				list = appDataSourceESDao.findDasAppOdsList(model);
			}else{
				if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
					model.setSort("datetime,hour");
					model.setOrder("desc,asc");
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					model.setSort("datetime,weeks");
					model.setOrder("desc,asc");
				}else{
					model.setSort("datetime");
					model.setOrder("desc");
				}
				AppDataSourceDao appDataSourceDao = getService(AppDataSourceDao.class);
				DasAppOdsVO dasAppOdsVO = new DasAppOdsVO();
				list = appDataSourceDao.queryForList(model,dasAppOdsVO);
			}

			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("不分页查询app数据源报表记录失败 :" + e.getMessage(), e);
			throw new Exception("不分页查询app数据源报表记录失败:" + e.getMessage());
		}
	}

}
