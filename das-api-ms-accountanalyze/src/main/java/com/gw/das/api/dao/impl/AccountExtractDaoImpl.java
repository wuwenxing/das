package com.gw.das.api.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gw.das.api.common.dao.BaseDao;
import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.dao.AccountExtractDao;

/**
 * Dao实现类
 * 
 * @author darren
 *
 */
@Repository
public class AccountExtractDaoImpl extends BaseDao implements AccountExtractDao {

	@Override
	public ApiPageResult getAccountExtractLabelListPage(ApiPageResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForListPage(result, sql, args);
	}

	@Override
	public ApiResult getAccountExtractLabelList(ApiResult result, String sql, Map<String, Object> args)
			throws Exception {		
		return queryForList(result, sql, args);
	}

}
