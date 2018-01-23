package com.gw.das.service.appDataAnalysis;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisDetailsVO;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisVO;

/**
 * App分析报表接口
 * 
 * @author darren
 *
 */
public interface AppDataAnalysisService {

	/**
	 * 分页查询APP数据分析报表记录
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<AppDataAnalysisSearchModel> findAppDataAnalysisPageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询APP数据分析报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<AppDataAnalysisVO> findAppDataAnalysisList(AppDataAnalysisSearchModel searchModel) throws Exception;
	
	/**
	 * 分页查询APP数据分析详情报表记录
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<AppDataAnalysisSearchModel> findAppDataAnalysisDetailsPageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception;
	
	/**
	 * 不分页查询APP数据分析详情报表记录
	 * 
	 * @param searchModel
	 * @return
	 * @throws Exception
	 */
	public List<AppDataAnalysisDetailsVO> findAppDataAnalysisDetailsList(AppDataAnalysisSearchModel searchModel) throws Exception;
	
	/**
	 * 分页查询开户来源分析报表记录
	 * 
	 * @param pageGrid
	 * @return
	 * @throws Exception
	 */
	public PageGrid<AppDataAnalysisSearchModel> findOpenSourceAnalysisPageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception;
	
}
