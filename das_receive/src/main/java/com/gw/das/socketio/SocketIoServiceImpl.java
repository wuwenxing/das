package com.gw.das.socketio;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gw.das.common.server.ThreadServer;
import com.gw.das.service.RoomAppService;

/**
 * websocket处理数据逻辑
 */
@Service
public class SocketIoServiceImpl implements SocketIoService {

	// 保存直播间APP操作日志接口
	@Resource
	private RoomAppService roomAppService;
	
	@Override
	public void setAppData(String msgContent, String date) {
		ThreadServer<String> threadServer = new ThreadServer<String>();
		threadServer.setListener(roomAppService);
		threadServer.excute(msgContent, date);
	}

}
