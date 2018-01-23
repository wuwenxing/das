package com.gw.das.service;

import com.gw.das.common.server.ThreadListener;
import com.gw.das.entity.UserTrackBasic;

/**
 * 网站数据接口
 */
public interface WebSiteService extends ThreadListener<UserTrackBasic>{

	/**
	 * 官网-保存消息
	 */
	public void setData(UserTrackBasic requestBody) throws Exception;
	
}
