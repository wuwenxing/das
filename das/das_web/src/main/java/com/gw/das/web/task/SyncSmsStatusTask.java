package com.gw.das.web.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.enums.FlowStatusEnum;
import com.gw.das.common.enums.RedisKeyEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SystemThreadEnum;
import com.gw.das.common.flow.LmFlowPush;
import com.gw.das.common.flow.YmFlowPush;
import com.gw.das.common.flow.YmFlowPushDetail;
import com.gw.das.common.sms.MdkjSmsPush;
import com.gw.das.common.sms.MdkjSmsPushDetail;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.system.entity.SystemThreadLogEntity;
import com.gw.das.service.market.FlowLogDetailService;
import com.gw.das.service.market.SmsDetailService;
import com.gw.das.service.market.SmsTemplateDetailService;
import com.gw.das.service.system.SystemThreadLogService;

/**
 * 每10分钟执行-同步短信发送的状态
 */
@Component
public class SyncSmsStatusTask {

	private static final Logger logger = LoggerFactory.getLogger(SyncSmsStatusTask.class);

	@Autowired
	private SmsDetailService smsDetailService;
	@Autowired
	private SmsTemplateDetailService smsTemplateDetailService;
	@Autowired
	private FlowLogDetailService flowLogDetailService;
	@Autowired
	private SystemThreadLogService systemThreadLogService;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行
	public void initDate() {
		logger.info("SyncSmsStatusTask-开始执行..");
		Date startTime = new Date();
		// 执行日志实体
		SystemThreadLogEntity entity = new SystemThreadLogEntity();
		entity.setStartExecuteTime(startTime);
		entity.setCode(SystemThreadEnum.syncSmsStatusTask.getLabelKey());
		try {

			// 1、设置当前为系统线程用户
			UserContext.setSystemThread(1L);
			// 2、执行业务逻辑
			this.dataProcessing();

			// 设置执行状态
			entity.setStatus("Y");
		} catch (Exception e) {
			// 3、设置执行状态
			entity.setStatus("N");
			entity.setRemark(e.getMessage());
			logger.error("SyncSmsStatusTask-执行出错:" + e.getMessage(), e);
		} finally {
			Date endTime = new Date();
			entity.setEndExecuteTime(endTime);
			try {
				// 记录执行日志
				systemThreadLogService.saveOrUpdate(entity);
			} catch (Exception e) {
				logger.error("记录执行日志出错:" + e.getMessage(), e);
			}
			long time = new Date().getTime() - startTime.getTime();
			logger.info("SyncSmsStatusTask-定时任务结束-耗时:" + time + "毫秒");
		}
	}

	private void dataProcessing() throws Exception {
		try {
			// 同步队列-mdkj
			mdkjHandler();
		} catch (Exception e) {
			logger.error("sync mdkj status exception:" + e.getMessage(), e);
		}
		try {
			// 同步队列-zzhl
			zzhlHandler();
		} catch (Exception e) {
			logger.error("sync zzhl status exception:" + e.getMessage(), e);
		}
		try {
			// 同步队列-ymFlow
			ymFlowHandler();
		} catch (Exception e) {
			logger.error("sync ymFlow status exception:" + e.getMessage(), e);
		}
		try {
			// 同步队列-lmFlow
			lmFlowHandler();
		} catch (Exception e) {
			logger.error("sync lmFlow status exception:" + e.getMessage(), e);
		}
		try {
			// 同步队列-rlFlow
			rlFlowHandler();
		} catch (Exception e) {
			logger.error("sync rlFlow status exception:" + e.getMessage(), e);
		}
		
	}

	private void mdkjHandler() throws Exception {
		// 同步队列中的回执信息并入库
		ListOperations<String, Object> list = redisTemplate.opsForList();
		Long size = list.size(RedisKeyEnum.mdkjSmsStatusData.getLabelKey());
		logger.info("sync mdkj status[size=" + size + "]");
		if (size <= 0) {
			return;
		}

		// 此时间段的回执消息汇总
		// Map<key=smsid_phone,value=smsStatus>
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++) {
			String postStr = list.rightPop(RedisKeyEnum.mdkjSmsStatusData.getLabelKey()) + "";
			if(StringUtils.isNotBlank(postStr)){
				try {
					MdkjSmsPush mdkjSmsPush = JacksonUtil.readValue(postStr, MdkjSmsPush.class);
					List<MdkjSmsPushDetail> tempList = mdkjSmsPush.getSmsResult();
					for (MdkjSmsPushDetail pushDetail : tempList) {
						String smsid = pushDetail.getSmsId();
						String phone = pushDetail.getPhone();
						String status = pushDetail.getStatus();
						map.put(smsid + "," + phone, status);
					}
				} catch (Exception e) {
					logger.error("sync mdkj status exception[postStr=" + postStr + "]" + e.getMessage(), e);
				}
			}else{
				logger.error("sync mdkj status postStr is null");
			}
		}

		// 更新
		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				String key = entry.getKey();
				String smsid = key.split(",")[0];
				String phone = key.split(",")[1];
				String status = entry.getValue();
				int num = 0;
				if ("0".equals(status)) {
					logger.info("sync mdkj status[smsid=" + smsid + "，phone=" + phone + "]，发送成功");
					num = smsDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendRecSuccess);
				} else {
					logger.info("sync mdkj status[smsid=" + smsid + "，phone=" + phone + "]，发送已失败");
					num = smsDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendFail);
				}
				logger.info("更新数量num=" + num);
				
				// 更新另外一张短信发送表
				if(num == 0){
					if ("0".equals(status)) {
						num = smsTemplateDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendRecSuccess);
					} else {
						num = smsTemplateDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendFail);
					}
					logger.info("更新数量2num=" + num);
				}
			} catch (Exception e) {
				logger.error("sync mdkj status exception:" + e.getMessage(), e);
			}
		}

		// 根据最后执行时间，查询失败或已发送的短信记录，主动获取短信发送状态并入库(备注：接口商-暂时不支持主动获取)
		logger.info("sync mdkj status Complete!");
	}

	private void zzhlHandler() throws Exception {
		// 同步队列中的回执信息并入库
		ListOperations<String, Object> list = redisTemplate.opsForList();
		Long size = list.size(RedisKeyEnum.zzhlSmsStatusData.getLabelKey());
		logger.info("sync zzhl status[size=" + size + "]");
		if (size <= 0) {
			return;
		}

		// 此时间段的回执消息汇总
		// Map<key=smsid_phone,value=smsStatus>
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++) {
			// 格式为："2,18576615293,0,F165246140224152846,20140428172543;"
			String postStr = list.rightPop(RedisKeyEnum.zzhlSmsStatusData.getLabelKey()) + "";
			if(StringUtils.isNotBlank(postStr)){
				try {
					String[] recordList = postStr.split(";");
					for (String record : recordList) {
						String[] recordAry = record.split(",");
						if (null != recordAry && recordAry.length == 5) {
							String type = recordAry[0];
							// 2代表状态报告
							if ("2".equals(type)) {
								String phone = recordAry[1];
								String resCode = recordAry[2];
								String smsid = recordAry[3];
								map.put(smsid + "," + phone, resCode);
							}
						}
					}
				} catch (Exception e) {
					logger.error("sync zzhl status exception[postStr=" + postStr + "]" + e.getMessage(), e);
				}
			}else{
				logger.error("sync zzhl status postStr is null");
			}
		}

		// 更新
		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				String key = entry.getKey();
				String smsid = key.split(",")[0];
				String phone = key.split(",")[1];
				String status = entry.getValue();
				int num = 0;
				if ("0".equals(status)) {
					logger.info("sync zzhl status[smsid=" + smsid + "，phone=" + phone + "]，发送成功");
					num = smsDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendRecSuccess);
				} else {
					logger.info("sync zzhl status[smsid=" + smsid + "，phone=" + phone + "]，发送已失败");
					num = smsDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendFail);
				}
				logger.info("更新数量num=" + num);
				
				// 更新另外一张短信发送表
				if(num == 0){
					if ("0".equals(status)) {
						num = smsTemplateDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendRecSuccess);
					} else {
						num = smsTemplateDetailService.updateSendStatus(smsid, phone, status, SendStatusEnum.sendFail);
					}
					logger.info("更新数量2num=" + num);
				}
			} catch (Exception e) {
				logger.error("sync zzhl status exception:" + e.getMessage(), e);
			}
		}

		// 根据最后执行时间，查询失败或已发送的短信记录，主动获取短信发送状态并入库(备注：接口商-暂时不支持主动获取)
		logger.info("sync zzhl status Complete!");
	}

	private void ymFlowHandler() throws Exception {
		// 同步队列中的回执信息并入库
		ListOperations<String, Object> list = redisTemplate.opsForList();
		Long size = list.size(RedisKeyEnum.ymFlowStatusData.getLabelKey());
		logger.info("sync ymFlow status[size=" + size + "]");
		if (size <= 0) {
			return;
		}

		// 此时间段的回执消息汇总
		// Map<key=返回批次号_phone,value=返回code>
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++) {
			String postStr = list.rightPop(RedisKeyEnum.ymFlowStatusData.getLabelKey()) + "";
			if(StringUtils.isNotBlank(postStr)){
				try {
					logger.info("sync ymFlow postStr=" + postStr);
					YmFlowPush ymFlowPush = JacksonUtil.readValue(postStr, YmFlowPush.class);
					List<YmFlowPushDetail> tempList = ymFlowPush.getErrorlist();
					for (YmFlowPushDetail pushDetail : tempList) {
						String phone = pushDetail.getMobile();
						String code = pushDetail.getCode() + "-" + pushDetail.getMessage();
						map.put(ymFlowPush.getBatchNo() + "," + phone, code);
					}
				} catch (Exception e) {
					logger.error("sync ymFlow status exception[postStr=" + postStr + "]" + e.getMessage(), e);
				}
			}else{
				logger.error("sync ymFlow status postStr is null");
			}
		}

		// 更新
		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				String key = entry.getKey();
				String batchNo = key.split(",")[0];
				String phone = key.split(",")[1];
				String code = entry.getValue();
				logger.info("sync ymFlow status[batchNo=" + batchNo + "，phone=" + phone + "，code=" + code + "]，充值已失败");
				int num = flowLogDetailService.updateFlowSendStatus(batchNo, phone, code, FlowStatusEnum.sendFail);
				logger.info("更新数量num=" + num);
			} catch (Exception e) {
				logger.error("sync ymFlow status exception:" + e.getMessage(), e);
			}
		}
		logger.info("sync ymFlow status Complete!");
	}
	
	private void lmFlowHandler() throws Exception {
		// 同步队列中的回执信息并入库
		ListOperations<String, Object> list = redisTemplate.opsForList();
		Long size = list.size(RedisKeyEnum.lmFlowStatusData.getLabelKey());
		logger.info("sync lmFlow status[size=" + size + "]");
		if (size <= 0) {
			return;
		}
		
		// 此时间段的回执消息汇总
		// Map<key=返回批次号_phone,value=返回code>
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++) {
			String postStr = list.rightPop(RedisKeyEnum.lmFlowStatusData.getLabelKey()) + "";
			if(StringUtils.isNotBlank(postStr)){
				try {
					logger.info("sync lmFlow postStr=" + postStr);
					List<LmFlowPush> lmFlowPushList = JacksonUtil.readValue(postStr, new TypeReference<List<LmFlowPush>>() {});
					for (LmFlowPush lmFlowPush : lmFlowPushList) {
						String phone = lmFlowPush.getMobile();
						String msgid = lmFlowPush.getMsgid();
						String status = lmFlowPush.getStatus();
						map.put(msgid + "," + phone, status);
					}
				} catch (Exception e) {
					logger.error("sync lmFlow status exception[postStr=" + postStr + "]" + e.getMessage(), e);
				}
			}else{
				logger.error("sync lmFlow status postStr is null");
			}
		}
		
		// 更新
		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				String key = entry.getKey();
				String msgid = key.split(",")[0];
				String phone = key.split(",")[1];
				String status = entry.getValue();
				int num = 0;
				if("00000".equals(status)){
					logger.info("sync lmFlow status[msgid=" + msgid + "，phone=" + phone + "，status=" + status + "]，充值成功");
					num = flowLogDetailService.updateFlowSendStatus(msgid, phone, status + "-充值成功", FlowStatusEnum.sendSuccess);
				}else{
					logger.info("sync lmFlow status[msgid=" + msgid + "，phone=" + phone + "，status=" + status + "]，充值已失败");
					num = flowLogDetailService.updateFlowSendStatus(msgid, phone, status + "-充值失败", FlowStatusEnum.sendFail);
				}
				logger.info("更新数量num=" + num);
			} catch (Exception e) {
				logger.error("sync lmFlow status exception:" + e.getMessage(), e);
			}
		}
		logger.info("sync lmFlow status Complete!");
	}
	
	private void rlFlowHandler() throws Exception {
		// 同步队列中的回执信息并入库
		ListOperations<String, Object> list = redisTemplate.opsForList();
		Long size = list.size(RedisKeyEnum.rlFlowStatusData.getLabelKey());
		logger.info("sync rlFlow status[size=" + size + "]");
		if (size <= 0) {
			return;
		}
		
		// 此时间段的回执消息汇总
		// Map<key=返回批次号_phone,value=返回code>
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < size; i++) {
			String postStr = list.rightPop(RedisKeyEnum.rlFlowStatusData.getLabelKey()) + "";
			if(StringUtils.isNotBlank(postStr)){
				try {
					logger.info("sync rlFlow postStr=" + postStr);
					/** data为xml格式：
						<?xml version="1.0" encoding="UTF-8"?>
						<Request>
							<appId>11112222333344445555666677778000</appId>
							<rechargeId>09c62d8f028b11e5a1610050568e55bd</rechargeId>
							<phoneNum>13800138000</phoneNum>
							<status>3</status>
							<msg>充值成功</msg>
							<customId>695136f5028d11e5a1610050568e55bd</customId>
						</Request>
					*/
					Document document = DocumentHelper.parseText(postStr);
					Element rootE = document.getRootElement();
					Element phoneNumE = rootE.element("phoneNum");
					Element customIdE = rootE.element("customId");
					Element statusE = rootE.element("status");
					
					String phone = phoneNumE.getTextTrim();
					String msgid = customIdE.getTextTrim();
					String status = statusE.getTextTrim();
					map.put(msgid + "," + phone, status);
				} catch (Exception e) {
					logger.error("sync rlFlow status exception[postStr=" + postStr + "]" + e.getMessage(), e);
				}
			}else{
				logger.error("sync rlFlow status postStr is null");
			}
		}
		
		// 更新
		for (Map.Entry<String, String> entry : map.entrySet()) {
			try {
				String key = entry.getKey();
				String msgid = key.split(",")[0];
				String phone = key.split(",")[1];
				String status = entry.getValue();
				int num = 0;
				if("3".equals(status)){ //充值结果-3,成功4,失败
					logger.info("sync rlFlow status[msgid=" + msgid + "，phone=" + phone + "，status=" + status + "]，充值成功");
					num = flowLogDetailService.updateFlowSendStatus(msgid, phone, status + "-充值成功", FlowStatusEnum.sendSuccess);
				}else{
					logger.info("sync rlFlow status[msgid=" + msgid + "，phone=" + phone + "，status=" + status + "]，充值已失败");
					num = flowLogDetailService.updateFlowSendStatus(msgid, phone, status + "-充值失败", FlowStatusEnum.sendFail);
				}
				logger.info("更新数量num=" + num);
			} catch (Exception e) {
				logger.error("sync rlFlow status exception:" + e.getMessage(), e);
			}
		}
		logger.info("sync rlFlow status Complete!");
	}
	
	public static void main(String[] args) {
		// 秒嘀
		List<MdkjSmsPushDetail> pushDetailList = new ArrayList<MdkjSmsPushDetail>();
		String postStr = "{\"smsResult\":["
				+ "{\"smsId\":\"cca80f0ae70c4104a04ed598aae45290\",\"phone\":\"13760291376\","
				+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"},"
				+ "{\"smsId\":\"cca80f0ae70c4104a04ed598aae45290\",\"phone\":\"18576615293\","
				+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"},"
				+ "{\"smsId\":\"19db1a643ea3475c96adcd736d97c2f9\",\"phone\":\"13760291376\","
				+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"},"
				+ "{\"smsId\":\"19db1a643ea3475c96adcd736d97c2f9\",\"phone\":\"15237112625\","
				+ "\"status\":\"0\",\"respMessage\":\"\",\"receiveTime\":\"2016-06-29 19:23:26\",\"chargingNum\":\"2\"}"
				+ "]}";
		MdkjSmsPush mdkjSmsPush = JacksonUtil.readValue(postStr, MdkjSmsPush.class);
		List<MdkjSmsPushDetail> tempList = mdkjSmsPush.getSmsResult();
		pushDetailList.addAll(tempList);
		logger.info(pushDetailList.size() + "");

		// 亿美
		String ymData ="{\"batchNo\":\"11\",\"successCount\":1,\"failCount\":2,\"errorlist\":[{\"mobile\":\"13760291376\",\"code\":\"N0002\",\"message\":\"运营商异常\"},{\"mobile\":\"15563736372\",\"code\":\"N0003\",\"message\":\"运营商异常\"},{\"mobile\":\"15563736373\",\"code\":\"N0002\",\"message\":\"运营商异常\"}]}";
		YmFlowPush ymFlowPush = JacksonUtil.readValue(ymData, YmFlowPush.class);
		logger.info(ymFlowPush.getErrorlist().size() + "");

		// 乐免
		String lmData = "[{\"mobile\":\"18576615293\",\"msgid\":\"1703011537534086\",\"time\":\"2017-03-02 15:33:10\",\"status\":\"00000\"},{\"mobile\":\"15510331875\",\"msgid\":\"1501081533080113\",\"time\":\"2015-01-08 15:33:09\",\"status\":\"00000\"}]";
		List<LmFlowPush> lmFlowPushList = JacksonUtil.readValue(lmData, new TypeReference<List<LmFlowPush>>() {});
		logger.info(lmFlowPushList.size() + "");
		
		// 容联
		try {
			String rlDate = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Request><appId>11112222333344445555666677778000</appId><rechargeId>09c62d8f028b11e5a1610050568e55bd</rechargeId><phoneNum>13800138000</phoneNum><status>3</status><msg>充值成功</msg><customId>695136f5028d11e5a1610050568e55bd</customId></Request>";
			Document document = DocumentHelper.parseText(rlDate);
			Element rootE = document.getRootElement();
			Element phoneNumE = rootE.element("phoneNum");
			Element customIdE = rootE.element("customId");
			Element statusE = rootE.element("status");
			String phone = phoneNumE.getTextTrim();
			String msgid = customIdE.getTextTrim();
			String status = statusE.getTextTrim();
			logger.info(phone);
			logger.info(msgid);
			logger.info(status);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}
