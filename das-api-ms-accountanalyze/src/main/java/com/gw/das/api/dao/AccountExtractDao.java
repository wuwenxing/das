package com.gw.das.api.dao;

import java.util.Map;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;

/**
 * Dao
 * @author darren
 *
 */
public interface AccountExtractDao {
	
	/**
	 * 用户标签账户提取
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiPageResult getAccountExtractLabelListPage(ApiPageResult result, String sql, Map<String, Object> args) throws Exception;
	
	/**
	 * 用户标签账户提取
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiResult getAccountExtractLabelList(ApiResult result, String sql, Map<String, Object> args) throws Exception;
	
}
