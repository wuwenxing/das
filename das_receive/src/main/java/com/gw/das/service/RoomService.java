package com.gw.das.service;

import com.gw.das.common.server.ThreadListener;
import com.gw.das.entity.ChartRoomBasic;

/**
 * 直播间数据接口
 */
public interface RoomService extends ThreadListener<ChartRoomBasic>{

	/**
	 * 直播间-保存消息
	 */
	public void setChartRoomData(ChartRoomBasic requestBody) throws Exception;
	
}
