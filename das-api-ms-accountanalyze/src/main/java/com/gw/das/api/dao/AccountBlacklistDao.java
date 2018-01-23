package com.gw.das.api.dao;

import java.util.Map;

import com.gw.das.api.common.response.ApiResult;

/**
 * 账户黑名单Dao
 * @author darren
 *
 */
public interface AccountBlacklistDao {
   
	/**
	 * 查询账户黑名单
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiResult findAccountBlacklist(ApiResult result, String sql, Map<String, Object> args) throws Exception;
	
}
