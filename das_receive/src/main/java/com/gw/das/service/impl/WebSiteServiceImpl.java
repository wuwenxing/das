package com.gw.das.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gw.das.common.enums.MessageTypeEnum;
import com.gw.das.common.flume.FlumeSendMsg;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.entity.UserTrackBasic;
import com.gw.das.mongodb.IdSeq;
import com.gw.das.mongodb.MongoDBBaseDao;
import com.gw.das.service.ISeqService;
import com.gw.das.service.WebSiteService;

/**
 * 网站数据接口实现
 */
@Service
public class WebSiteServiceImpl implements WebSiteService {

	private static final Logger logger = LoggerFactory.getLogger(WebSiteServiceImpl.class);

	@Resource(name = "mongoDBBaseDao")
	private MongoDBBaseDao mongoDBBaseDao;

	@Resource
	private ISeqService iSeqService;

	@Override
	public void excute(final UserTrackBasic requestBody, final String... str) {
		try {
			this.setData(requestBody);
		} catch (Exception e) {
			logger.error("website rec data exception!" + e.getMessage(), e);
		}
	}
	
	/**
	 * 官网-保存消息
	 */
	public void setData(UserTrackBasic requestBody) throws Exception {
		if (null == requestBody || null == requestBody.getBusinessPlatform()) {
			logger.error("数据或平台类型为空");
			throw new Exception("数据或平台类型为空");
		}
		try {
			requestBody.setId(iSeqService.getSeqId(IdSeq.UserTrackBasic));
			requestBody.setVisitTime(new Date());
			mongoDBBaseDao.add(requestBody);
		} catch (Exception e) {
			logger.error("数据放入MongoDB异常:" + e.getMessage(), e);
		}
		try {
			FlumeSendMsg.sendMessage(JacksonUtil.toJSon(requestBody), MessageTypeEnum.web.getLabelKey());
		} catch (Exception e) {
			logger.error("数据放入Flume异常:" + e.getMessage(), e);
		}
	}

}
