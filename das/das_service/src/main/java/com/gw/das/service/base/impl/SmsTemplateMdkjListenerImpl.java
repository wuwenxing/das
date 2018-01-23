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
import com.gw.das.dao.market.entity.SmsTemplateDetailEntity;
import com.gw.das.service.market.SmsTemplateDetailService;

/**
 * service层公共方法
 * 
 * @author wayne
 */
@Service
public class SmsTemplateMdkjListenerImpl implements SmsListener {

	private static final Logger logger = LoggerFactory.getLogger(SmsTemplateMdkjListenerImpl.class);

	@Autowired
	private SmsTemplateDetailService smsTemplateDetailService;
	
	@Override
	public void updateBefore(SmsContext smsContext) {
		try{
			Object[] objAry = new Object[1];
			
			SmsInfo smsInfo = smsContext.getSmsInfo();
			logger.info("mdkj_smsTemplate-before[phones=" + smsInfo.getPhones() + "]");

			// 登录设置当前用户-系统接口
			Long companyId = smsInfo.getCompanyId();
			UserContext.setSystemInterface("127.0.0.1", companyId);
			
			String[] phoneAry = smsInfo.getPhones().split(",");
			Map<String, SmsTemplateDetailEntity> tempRecordMap = new HashMap<String, SmsTemplateDetailEntity>();
			for (String tempMobile : phoneAry) {
				SmsTemplateDetailEntity detailVo = new SmsTemplateDetailEntity();
				detailVo.setTemplateId(smsInfo.getTemplateId());
				detailVo.setSendNo(smsInfo.getSendNo());
				detailVo.setPhone(tempMobile);
				detailVo.setContent(smsInfo.getContent());
				detailVo.setCommitStatus(CommitStatusEnum.commitReady.getLabelKey());// 短信提交状态
				detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信发送状态-发送失败
				detailVo.setInterfaceType(SmsChannelEnum.mdkj.getLabelKey());
				detailVo.setSmsid("");
				detailVo.setResCode("");
				smsTemplateDetailService.saveOrUpdate(detailVo);
				tempRecordMap.put(tempMobile, detailVo);
			}
			objAry[0] = tempRecordMap;
			smsContext.setObjAry(objAry);

			// 更新短信的总数
			int totleNum = phoneAry.length;
			
			
		}catch(Exception e){
			logger.error("短信发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfter(SmsContext smsContext) {
		try{
			SmsInfo smsInfo = smsContext.getSmsInfo();
			logger.info("mdkj_smsTemplate-after[phones=" + smsInfo.getPhones() + "]");
			
			Map<String, SmsTemplateDetailEntity> tempRecordMap = (Map<String, SmsTemplateDetailEntity>)smsContext.getObjAry()[0];
			
			String[] phoneAry = smsInfo.getPhones().split(",");
			MdkjSmsResponse response = smsContext.getMdkjResponse();
			
			int successNum = 0;
			int loseNum = 0;
			if (null != response) {
				logger.error("mdkj_smsTemplate-返回respCode：" + response.getRespCode());
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
						SmsTemplateDetailEntity detailVo = tempRecordMap.get(tempMobile);
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
						smsTemplateDetailService.saveOrUpdate(detailVo);
						successNum++;
					}
				} else {
					// 请求失败
					loseNum = phoneAry.length;
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						SmsTemplateDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 短信提交状态
						detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信状态-发送失败
						detailVo.setSmsid(response.getSmsId());
						detailVo.setResCode(response.getRespCode());
						smsTemplateDetailService.saveOrUpdate(detailVo);
					}
				}
			} else {
				loseNum = phoneAry.length;
				logger.error("mdkj_smsTemplate-请求异常response=null");
				for (int i = 0; i < phoneAry.length; i++) {
					String tempMobile = phoneAry[i];
					SmsTemplateDetailEntity detailVo = tempRecordMap.get(tempMobile);
					detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 短信提交状态
					detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信状态-发送失败
					smsTemplateDetailService.saveOrUpdate(detailVo);
				}
			}
			
			// 更新短信的失败与成功总数
			
			
		}catch(Exception e){
			logger.error("短信发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfterThrowable(SmsContext smsContext) {
		try{
			SmsInfo smsInfo = smsContext.getSmsInfo();
			logger.info("mdkj_smsTemplate-afterThrowable[phones=" + smsInfo.getPhones() + "]");
			
			Map<String, SmsTemplateDetailEntity> tempRecordMap = (Map<String, SmsTemplateDetailEntity>)smsContext.getObjAry()[0];
			for(Map.Entry<String, SmsTemplateDetailEntity> entry: tempRecordMap.entrySet()){
				SmsTemplateDetailEntity detailVo = entry.getValue();
				detailVo.setCommitStatus(CommitStatusEnum.commitFail.getLabelKey());// 短信提交状态
				detailVo.setSendStatus(SendStatusEnum.sendFail.getLabelKey());// 短信状态-发送失败
				smsTemplateDetailService.saveOrUpdate(detailVo);
			}

			// 更新短信的失败总数
			int loseNum = smsInfo.getPhones().split(",").length;
			
		}catch(Exception e){
			logger.error("短信发送出现异常:" + e.getMessage(), e);
		}
	}

}
