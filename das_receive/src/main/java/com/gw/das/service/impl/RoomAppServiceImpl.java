package com.gw.das.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gw.das.common.enums.MessageTypeEnum;
import com.gw.das.common.flume.FlumeSendMsg;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.JsonUtil;
import com.gw.das.entity.AppDataBasic;
import com.gw.das.mongodb.IdSeq;
import com.gw.das.mongodb.MongoDBBaseDao;
import com.gw.das.service.ISeqService;
import com.gw.das.service.RoomAppService;

/**
 * 直播间APP接口实现类
 */
@Service
public class RoomAppServiceImpl implements RoomAppService {
	
	private static final Logger logger = LoggerFactory.getLogger(RoomAppServiceImpl.class);
	
	@Resource(name = "mongoDBBaseDao")
	private MongoDBBaseDao mongoDBBaseDao;
	
	@Resource
	private ISeqService  iSeqService;
	
	@Override
	public void excute(final String obj, final String... str) {
		try {
			this.setAppData(obj, str[0]);
		} catch (Exception e) {
			logger.error("website rec data exception!" + e.getMessage(), e);
		}
	}
	
	/**
	 * 保存APP操作日志
	 */
	public void setAppData(String msgContent, String date){
		AppDataBasic data = JacksonUtil.readValue(msgContent, AppDataBasic.class);
		if(data != null){
			data.setOperationTime(date);
			// 操作类型 1：启动 、2：登陆 、3：交易、4：注销、5:退出
			// 根据操作类型设置-事件类别//事件操作//事件标签//事件参数
			if("1".equals(data.getOperationType())){
				data.setEventCategory("Main");
				data.setEventAction("Start");
				data.setEventLabel("");
				data.setEventValue("");
			}else if("2".equals(data.getOperationType())){
				data.setEventCategory("Main");
				data.setEventAction("Login");
				data.setEventLabel("");
				data.setEventValue("");
			}else if("3".equals(data.getOperationType())){
				data.setEventCategory("Trade");
				data.setEventAction("Success");
				data.setEventLabel("");
				data.setEventValue("");
			}else if("4".equals(data.getOperationType())){
				data.setEventCategory("Main");
				data.setEventAction("Logout");
				data.setEventLabel("");
				data.setEventValue("");
			}else if("5".equals(data.getOperationType())){
				data.setEventCategory("Main");
				data.setEventAction("Quit");
				data.setEventLabel("");
				data.setEventValue("");
			}else{
				logger.error("操作类型非法值为："+data.getOperationType());
				return;
			}
			
			try {
				FlumeSendMsg.sendMessage(JsonUtil.objectToJson(data), MessageTypeEnum.app.getLabelKey());
			} catch (Exception e) {
				logger.error("订阅消息（appData）放入Flume异常:" + e.getMessage(), e);
			}
			try {
				data.setId(iSeqService.getSeqId(IdSeq.AppOperLog));
				mongoDBBaseDao.add(data);
			} catch (Exception e) {
				logger.error("订阅消息（appData）放入MongoDB异常:" + e.getMessage(), e);
			}
		}else{
			logger.error("订阅消息（appData）转换对象异常:" + msgContent);
		}
	}
	
}
