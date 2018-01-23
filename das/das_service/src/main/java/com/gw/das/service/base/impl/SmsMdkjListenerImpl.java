package com.gw.das.service.base.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.enums.CommitStatusEnum;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.common.enums.SmsChannelEnum;
import com.gw.das.common.sms.MdkjSmsResponse;
import com.gw.das.common.sms.MdkjSmsResponseDetail;
import com.gw.das.common.sms.SmsContext;
import com.gw.das.common.sms.SmsInfo;
import com.gw.das.common.sms.SmsListener;
import com.gw.das.dao.market.entity.SmsDetailEntity;
import com.gw.das.service.market.SmsDetailService;
import com.gw.das.service.market.SmsService;

/**
 * service层公共方法
 * 
 * @author wayne
 */
@Service
public class SmsMdkjListenerImpl implements SmsListener {

	private static final Logger logger = LoggerFactory.getLogger(SmsMdkjListenerImpl.class);

	@Autowired
	private SmsService smsService;

	@Autowired
	private SmsDetailService smsDetailService;
	
	@Override
	public void updateBefore(SmsContext smsContext) {
		try{
			Object[] objAry = new Object[1];
			
			SmsInfo smsInfo = smsContext.getSmsInfo();
			logger.info("mdkj_sms-before[phones=" + smsInfo.getPhones() + "]");

			// 登录设置当前用户-系统接口
			Long companyId = smsInfo.getCompanyId();
			UserContext.setSystemInterface("127.0.0.1", companyId);
			
			String[] phoneAry = smsInfo.getPhones().split(",");
			Map<String, SmsDetailEntity> tempRecordMap = new HashMap<String, SmsDetailEntity>();
			for (String tempMobile : phoneAry) {
				SmsDetailEntity detailVo = new SmsDetailEntity();
				detailVo.setSmsId(smsInfo.getTemplateId());
				detailVo.setPhone(tempMobile);
				detailVo.setCommitStatus(CommitStatusEnum.commitReady.getLabelKey());// 短信提交状态
				detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信发送状态-发送失败
				detailVo.setInterfaceType(SmsChannelEnum.mdkj.getLabelKey());
				detailVo.setSmsid("");
				detailVo.setResCode("");
				smsDetailService.saveOrUpdate(detailVo);
				tempRecordMap.put(tempMobile, detailVo);
			}
			objAry[0] = tempRecordMap;
			smsContext.setObjAry(objAry);

			// 更新短信的总数
//			int totalNum = phoneAry.length;
//			this.updateTotal(smsInfo.getTemplateId(), totalNum, "totalNum");
			
		}catch(Exception e){
			logger.error("短信发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfter(SmsContext smsContext) {
		try{
			SmsInfo smsInfo = smsContext.getSmsInfo();
			logger.info("mdkj_sms-after[phones=" + smsInfo.getPhones() + "]");
			
			Map<String, SmsDetailEntity> tempRecordMap = (Map<String, SmsDetailEntity>)smsContext.getObjAry()[0];
			
			String[] phoneAry = smsInfo.getPhones().split(",");
			MdkjSmsResponse response = smsContext.getMdkjResponse();
			
			int successNum = 0;
			int loseNum = 0;
			if (null != response) {
				logger.error("mdkj_sms-返回respCode：" + response.getRespCode());
				// 00000请求发送成功
				if ("00000".equals(response.getRespCode())) {
					// 请求成功
					// 请求成功后，但其中也存在有失败的手机号码及原因
					List<MdkjSmsResponseDetail> failList = response.getFailList();
					Map<String, String> failMap = new HashMap<String, String>();
					loseNum = Integer.parseInt(response.getFailCount());
					for(MdkjSmsResponseDetail detail: failList){
						failMap.put(detail.getPhone(), detail.getRespCode());
					}
					// 更新
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						SmsDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 短信提交状态
						if(null != failMap.get(tempMobile)){
							logger.error("MdkjSmsTemplate-请求成功但发送失败phone：" + tempMobile);
							detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信状态-发送失败
						}else{
							detailVo.setSendStatus(SendStatusEnum.sendSuccess.getLabelKey());// 短信状态-发送成功
							successNum++;
						}
						detailVo.setSmsid(response.getSmsId());
						detailVo.setResCode(response.getRespCode());
						smsDetailService.saveOrUpdate(detailVo);
						successNum++;
					}
				} else {
					// 请求失败
					loseNum = phoneAry.length;
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						SmsDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 短信提交状态
						detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信状态-发送失败
						detailVo.setSmsid(response.getSmsId());
						detailVo.setResCode(response.getRespCode());
						smsDetailService.saveOrUpdate(detailVo);
					}
				}
			} else {
				loseNum = phoneAry.length;
				logger.error("mdkj_sms-请求异常response=null");
				for (int i = 0; i < phoneAry.length; i++) {
					String tempMobile = phoneAry[i];
					SmsDetailEntity detailVo = tempRecordMap.get(tempMobile);
					detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 短信提交状态
					detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信状态-发送失败
					smsDetailService.saveOrUpdate(detailVo);
				}
			}

			// 更新短信的失败与成功总数
//			this.updateTotal(smsInfo.getTemplateId(), successNum, "successNum");
//			this.updateTotal(smsInfo.getTemplateId(), loseNum, "failNum");
			
		}catch(Exception e){
			logger.error("短信发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfterThrowable(SmsContext smsContext) {
		try{
			SmsInfo smsInfo = smsContext.getSmsInfo();
			logger.info("mdkj_sms-afterThrowable[phones=" + smsInfo.getPhones() + "]");
			
			Map<String, SmsDetailEntity> tempRecordMap = (Map<String, SmsDetailEntity>)smsContext.getObjAry()[0];
			for(Map.Entry<String, SmsDetailEntity> entry: tempRecordMap.entrySet()){
				SmsDetailEntity detailVo = entry.getValue();
				detailVo.setCommitStatus(CommitStatusEnum.commitFail.getLabelKey());// 短信提交状态
				detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信状态-发送失败
				smsDetailService.saveOrUpdate(detailVo);
			}

			// 更新短信的失败总数
//			int loseNum = smsInfo.getPhones().split(",").length;
//			this.updateTotal(smsInfo.getTemplateId(), loseNum, "failNum");
		}catch(Exception e){
			logger.error("短信发送出现异常:" + e.getMessage(), e);
		}
	}

	/**
	 * 更新短信发送总数\成功总数\失败总数
	 * synchronized防止批量发送时，
	 * 更新数量出现问题 一旦某个进程抢得锁之后，其他的进程只有排队对待。
	 */
//	private void updateTotal(Long id, int num, String type) throws Exception{
//		if (num <= 0 || StringUtils.isBlank(type)) {
//			return;
//		}
//		// 让synchronized锁这个类对应的Class对象
//		synchronized (SmsMdkjListenerImpl.class) {
//			SmsEntity record = smsService.findById(id);
//			Long count = 0L;
//			if ("totalNum".equals(type)) {
//				if (null != record.getTotalNum()) {
//					count = record.getTotalNum() + num;
//				} else {
//					count = Long.parseLong(num + "");
//				}
//				record.setTotalNum(count);
//				logger.info("setTotalNum=" + count);
//			} else if ("successNum".equals(type)) {
//				if (null != record.getSuccessNum()) {
//					count = record.getSuccessNum() + num;
//				} else {
//					count = Long.parseLong(num + "");
//				}
//				record.setSuccessNum(count);
//				logger.info("setSuccessNum=" + count);
//			} else if ("failNum".equals(type)) {
//				if (null != record.getFailNum()) {
//					count = record.getFailNum() + num;
//				} else {
//					count = Long.parseLong(num + "");
//				}
//				record.setFailNum(count);
//				logger.info("setFailNum=" + count);
//			}
//			// 更新发送记录数
//			smsService.saveOrUpdate(record);
//			// 休眠300毫秒用于写入数据库
//			Thread.sleep(300);
//		}
//	}
	
}
