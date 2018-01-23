package com.gw.das.rpc.appDataAnalysis;

import java.util.Map;

import com.gw.das.rpc.base.Manager;

/**
 * App报表接口
 * 
 * @author darren
 *
 */
public interface AppDataAnalysisManager  extends Manager{

	/**
	 * 分页查询APP数据分析报表记录
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findAppDataAnalysisPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询APP数据分析报表记录
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findAppDataAnalysisList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询APP数据分析详情报表记录
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findAppDataAnalysisDetailsPageList(String jsonStr) throws Exception;
	
	/**
	 * 不分页查询APP数据分析详情报表记录
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findAppDataAnalysisDetailsList(String jsonStr) throws Exception;
	
	/**
	 * 分页查询开户来源分析报表记录
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> findOpenSourceAnalysisPageList(String jsonStr) throws Exception;
	
}
