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
import com.gw.das.common.enums.FlowChannelEnum;
import com.gw.das.common.enums.FlowStatusEnum;
import com.gw.das.common.flow.FlowContext;
import com.gw.das.common.flow.FlowInfo;
import com.gw.das.common.flow.FlowListener;
import com.gw.das.common.flow.LmFlowResponse;
import com.gw.das.common.flow.LmFlowResponseDetail;
import com.gw.das.dao.market.entity.FlowLogDetailEntity;
import com.gw.das.service.market.FlowLogDetailService;

/**
 * service层公共方法
 * 
 * @author wayne
 */
@Service
public class FlowLmListenerImpl implements FlowListener {

	private static final Logger logger = LoggerFactory.getLogger(FlowLmListenerImpl.class);

	@Autowired
	private FlowLogDetailService flowLogDetailService;

	@Override
	public void updateBefore(FlowContext flowContext) {
		try {
			Object[] objAry = new Object[1];

			FlowInfo flowInfo = flowContext.getFlowInfo();
			logger.info("lm-before[phones=" + flowInfo.getPhones() + "]");

			// 登录设置当前用户-系统接口
			Long companyId = flowInfo.getCompanyId();
			UserContext.setSystemInterface("127.0.0.1", companyId);

			String[] phoneAry = flowInfo.getPhones().split(",");
			Map<String, FlowLogDetailEntity> tempRecordMap = new HashMap<String, FlowLogDetailEntity>();
			for (String tempMobile : phoneAry) {
				FlowLogDetailEntity detailVo = new FlowLogDetailEntity();
				detailVo.setFlowLogId(flowInfo.getFlowLogId());
				detailVo.setPhone(tempMobile);
				detailVo.setCommitStatus(CommitStatusEnum.commitReady.getLabelKey());// 提交状态
				detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
				detailVo.setInterfaceType(FlowChannelEnum.lm.getLabelKey());
				detailVo.setFlowSize(flowInfo.getFlowSizeLm() + "M");
				detailVo.setResBatchNo("");
				detailVo.setResCode("");
				flowLogDetailService.saveOrUpdate(detailVo);
				tempRecordMap.put(tempMobile, detailVo);
			}
			objAry[0] = tempRecordMap;
			flowContext.setObjAry(objAry);

		} catch (Exception e) {
			logger.error("发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfter(FlowContext flowContext) {
		try {
			FlowInfo flowInfo = flowContext.getFlowInfo();
			logger.info("lm-after[phones=" + flowInfo.getPhones() + "]");

			Map<String, FlowLogDetailEntity> tempRecordMap = (Map<String, FlowLogDetailEntity>) flowContext
					.getObjAry()[0];

			String[] phoneAry = flowInfo.getPhones().split(",");
			LmFlowResponse response = flowContext.getLmFlowResponse();

			int successNum = 0;
			int loseNum = 0;
			if (null != response) {
				logger.error("lm-返回status：" + response.getStatus());
				// code="1"代表请求成功
				if ("1".equals(response.getStatus())) {
					// 请求成功
					
					// 订购结果列表
					List<LmFlowResponseDetail> list = response.getReports();
					// 订购结果列表-map
					Map<String, LmFlowResponseDetail> map = new HashMap<String, LmFlowResponseDetail>();
					for (int i = 0; i < list.size(); i++) {
						LmFlowResponseDetail detail = list.get(i);
						// 手机号
						String mobile = detail.getMobile();
						map.put(mobile, detail);
					}
					
					// 更新
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						FlowLogDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
						detailVo.setSendStatus(FlowStatusEnum.sendSuccess.getLabelKey());// 充值成功
						
						LmFlowResponseDetail detail = map.get(tempMobile);
						if(null != detail){
							// 任务编号
							String msgid = detail.getMsgid();
							detailVo.setResBatchNo(msgid);
							// 00000代表为成功
							if("00000".equals(detail.getStatus())){
								detailVo.setResCode(detail.getStatus() + "-" + response.getDescription());
								detailVo.setSendStatus(FlowStatusEnum.sendSuccess.getLabelKey());// 充值成功
								successNum++;
							}else{
								detailVo.setResCode(detail.getStatus() + "-" + response.getDescription());
								detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
								loseNum++;
							}
						}else{
							detailVo.setResBatchNo(response.getMsgid());
							detailVo.setResCode(response.getStatus() + "-" + response.getDescription());
							loseNum++;
						}
						flowLogDetailService.saveOrUpdate(detailVo);
					}
				} else {
					// 请求失败
					loseNum = phoneAry.length;
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						FlowLogDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
						detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
						detailVo.setResBatchNo(response.getMsgid());
						detailVo.setResCode(response.getStatus() + "-" + response.getDescription());
						flowLogDetailService.saveOrUpdate(detailVo);
					}
				}
			} else {
				loseNum = phoneAry.length;
				logger.error("lm-请求异常response=null");
				for (int i = 0; i < phoneAry.length; i++) {
					String tempMobile = phoneAry[i];
					FlowLogDetailEntity detailVo = tempRecordMap.get(tempMobile);
					detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
					detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
					flowLogDetailService.saveOrUpdate(detailVo);
				}
			}

		} catch (Exception e) {
			logger.error("发送出现异常:" + e.getMessage(), e);
		}
	}

	@Override
	public void updateAfterThrowable(FlowContext flowContext) {
		try {
			FlowInfo flowInfo = flowContext.getFlowInfo();
			logger.info("lm-afterThrowable[phones=" + flowInfo.getPhones() + "]");

			Map<String, FlowLogDetailEntity> tempRecordMap = (Map<String, FlowLogDetailEntity>) flowContext
					.getObjAry()[0];
			for (Map.Entry<String, FlowLogDetailEntity> entry : tempRecordMap.entrySet()) {
				FlowLogDetailEntity detailVo = entry.getValue();
				detailVo.setCommitStatus(CommitStatusEnum.commitFail.getLabelKey());// 提交状态
				detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
				flowLogDetailService.saveOrUpdate(detailVo);
			}

		} catch (Exception e) {
			logger.error("发送出现异常:" + e.getMessage(), e);
		}
	}

}
