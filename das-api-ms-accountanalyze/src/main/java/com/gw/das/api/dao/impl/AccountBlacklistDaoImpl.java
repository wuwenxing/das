package com.gw.das.api.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.gw.das.api.common.dao.BaseDao;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.dao.AccountBlacklistDao;

/**
 * 账户黑名单Dao实现类
 * 
 * @author darren
 *
 */
@Repository
public class AccountBlacklistDaoImpl extends BaseDao implements AccountBlacklistDao {

	@Override
	public ApiResult findAccountBlacklist(ApiResult result, String sql, Map<String, Object> args) throws Exception {
		return queryForList(result, sql, args);
	}

}
