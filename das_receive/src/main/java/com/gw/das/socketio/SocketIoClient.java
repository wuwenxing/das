package com.gw.das.socketio;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.entity.AppDataBasic;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIoClient {
	private final static Logger logger = Logger.getLogger(SocketIoClient.class);

	private static String url = "";
	private Socket socket = null;
	private boolean connected = false;
	// websocket获取数据后，通过该接口处理逻辑
	private SocketIoService socketIoService;

	static{
		url = SystemConfigUtil.getProperties().getProperty(SystemConfigEnum.webSocketUrl.getLabelKey());
	}
	
	public boolean isConnected() {
		return connected;
	}

	public void setSocketIoService(SocketIoService socketIoService) {
		this.socketIoService = socketIoService;
	}

	/**
	 * 连接server
	 */
	public void connect(){
		if (connected) {
			logger.info("Client already connected!");
			return;
		}
		
		try{
			// 连接
			logger.info("web socket连接开始...");
			socket = IO.socket(url);
			socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					logger.info("web socket连接成功...");
					connected = true;
					Map<String, String> roomMap = new HashMap<String, String>();
					roomMap.put("topicId", "appData");
					socket.emit("onTopic", roomMap);
					logger.info("已订阅消息（订阅号=appData）");
//					AppDataBasic appData = new AppDataBasic();
//					appData.setId("1");
//					appData.setDeviceid("sbwybs");
//					appData.setDeviceType("IOS");
//					appData.setUserIp("127.0.0.1");
//					appData.setUserType("2");
//					appData.setAccount("8000test");
//					appData.setAccountType("0");
//					appData.setPlatform("GTS");
//					appData.setChannel("channel");
//					appData.setModel("ss-a");
//					appData.setCarrier("APPLE");
//					appData.setBusinessPlatform("2");
//					appData.setOperationType("2");
//					appData.setOperationTime(new Date().getTime()+"");
//					appData.setVersion("V1.0.0");
//					String msgContent = JacksonUtil.toJSon(appData);
//					Map<String, String> msgMap = new HashMap<String, String>();
//					msgMap.put("topicId", "appData");
//					msgMap.put("msg", msgContent);
//					socket.emit("topic", msgMap);
//					logger.info("已发送消息（订阅号=appData）");
				}
			}).on("topic", new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					if (null != args && args.length > 0) {
						logger.info("recMsg:" + args[0]);
						RecMsg recMsg = JacksonUtil.readValue(args[0] + "", RecMsg.class);
						if (recMsg != null) {
							String date = DateUtil.formatDate(recMsg.getDate());
							String msgContent = recMsg.getMsgContent();
							logger.info("date[" + date + "],msgContent[" + msgContent +"]");
							socketIoService.setAppData(msgContent, date);
						}
					}
				}
			}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
				@Override
				public void call(Object... args) {
					connected = false;
					logger.info("连接已关闭.");
				}
			});
			socket.connect();
		}catch(Exception e){
			logger.error("连接出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 关闭连接
	 */
	public void disConnect(){
		socket.disconnect();
	}

	/**
	 * 发送消息
	 */
	public void sendMessage(){
		AppDataBasic appData = new AppDataBasic();
		appData.setId("1");
		appData.setDeviceid("sbwybs");
		appData.setDeviceType("IOS");
		appData.setUserIp("127.0.0.1");
		appData.setUserType("2");
		appData.setAccount("8000test");
		appData.setAccountType("0");
		appData.setPlatform("GTS");
		appData.setChannel("channel");
		appData.setModel("ss-a");
		appData.setCarrier("APPLE");
		appData.setBusinessPlatform("2");
		appData.setOperationType("2");
		appData.setOperationTime(new Date().getTime()+"");
		appData.setVersion("V1.0.0");
		String msgContent = JacksonUtil.toJSon(appData);
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("topicId", "appData");
		msgMap.put("msg", msgContent);
		socket.emit("topic", msgMap);
		logger.info("已发送消息（订阅号=appData）");
	}
	
	public static void main(String[] args) {
		SocketIoClient client = new SocketIoClient();
		client.connect();
		client.sendMessage();
	}
	
}
