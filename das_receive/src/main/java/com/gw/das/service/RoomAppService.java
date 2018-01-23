package com.gw.das.service;

import com.gw.das.common.server.ThreadListener;

/**
 * 直播间APP接口
 */
public interface RoomAppService extends ThreadListener<String>{
	
	/**
	 * 保存APP操作日志
	 */
	public void setAppData(String msgContent, String date);
	
}
