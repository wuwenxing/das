package com.gw.das.api.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gw.das.api.common.dao.BaseDao;
import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.dao.TradeReportDao;
/**
 * 交易报表Dao实现类
 * 
 * @author darren
 *
 */
@Repository
public class TradeReportDaoImpl extends BaseDao implements TradeReportDao {
	
	public ApiResult tradeDetailCloseList(ApiResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForList(result, sql, args);
	}
	
	public ApiPageResult tradeDetailClosePageList(ApiPageResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForListPage(result, sql, args);
	}
	
	public ApiResult tradeDetailOpenList(ApiResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForList(result, sql, args);
	}
	
	public ApiPageResult tradeDetailOpenPageList(ApiPageResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForListPage(result, sql, args);
	}

	@Override
	public ApiResult accountbalanceList(ApiResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForList(result, sql, args);
	}

	@Override
	public ApiPageResult accountbalancePageList(ApiPageResult result, String sql, Map<String, Object> args)
			throws Exception {
		return queryForListPage(result, sql, args);
	}

	@Override
	public ApiResult customerdailyList(ApiResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForList(result, sql, args);
	}

	@Override
	public ApiPageResult customerdailyPageList(ApiPageResult result, String sql, Map<String, Object> args)
			throws Exception {
		return queryForListPage(result, sql, args);
	}
}
