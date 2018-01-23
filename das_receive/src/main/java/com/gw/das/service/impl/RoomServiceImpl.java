package com.gw.das.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gw.das.common.enums.MessageTypeEnum;
import com.gw.das.common.flume.FlumeSendMsg;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.entity.ChartRoomBasic;
import com.gw.das.mongodb.MongoDBBaseDao;
import com.gw.das.service.ISeqService;
import com.gw.das.service.RoomService;

/**
 * 直播间数据接口实现
 */
@Service
public class RoomServiceImpl implements RoomService {

	private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

	@Resource(name = "mongoDBBaseDao")
	private MongoDBBaseDao mongoDBBaseDao;

	@Resource
	private ISeqService iSeqService;

	@Override
	public void excute(final ChartRoomBasic requestBody, final String... str) {
		try {
			this.setChartRoomData(requestBody);
		} catch (Exception e) {
			logger.error("room rec data exception!" + e.getMessage(), e);
		}
	}

	/**
	 * 直播间-保存消息
	 */
	public void setChartRoomData(ChartRoomBasic requestBody) throws Exception {
		if (null == requestBody || null == requestBody.getBusinessPlatform()) {
			logger.error("数据或平台类型为空");
			throw new Exception("数据或平台类型为空");
		}
		if (StringUtils.isBlank(requestBody.getUserId())) {
			logger.error("userId为空");
			throw new Exception("userId为空");
		}

		try {
			requestBody.setOperationTime(DateUtil.formatDate(new Date()));
			mongoDBBaseDao.add(requestBody);
		} catch (Exception e) {
			logger.error("数据放入MongoDB异常:" + e.getMessage(), e);
		}
		try {
			FlumeSendMsg.sendMessage(JacksonUtil.toJSon(requestBody), MessageTypeEnum.room.getLabelKey());
		} catch (Exception e) {
			logger.error("数据放入Flume异常:" + e.getMessage(), e);
		}
	}

}
