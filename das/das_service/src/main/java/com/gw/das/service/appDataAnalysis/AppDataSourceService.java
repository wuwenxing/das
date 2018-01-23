package com.gw.das.service.appDataAnalysis;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.DasAppOdsVO;

/**
 * app数据源报表接口
 * 
 * @author darren
 *
 */
public interface AppDataSourceService {

	/**
	 * 分页查询app数据源报表记录
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<AppDataAnalysisSearchModel> findAppDataSourcePageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询app数据源报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<DasAppOdsVO> findAppDataSourceList(AppDataAnalysisSearchModel searchBean)
			throws Exception;
	
}
