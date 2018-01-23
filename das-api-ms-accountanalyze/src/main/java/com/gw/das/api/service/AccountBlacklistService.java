package com.gw.das.api.service;

import com.gw.das.api.common.response.ApiResult;

/**
 *  账户黑名单Service
 *  
 * @author darren
 *
 */
public interface AccountBlacklistService {
    
	/**
	 * 查询账户黑名单
	 * @param value
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public ApiResult findAccountBlacklist(String value, Integer type )throws Exception;
		
	/**
	 * 渠道预警
	 * 
	 * @param value
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public ApiResult channelWarning(String value, Integer type )throws Exception;
	
}
