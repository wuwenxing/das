package com.gw.das.service.base.impl;

import java.util.HashMap;
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
import com.gw.das.common.flow.RlFlowResponse;
import com.gw.das.dao.market.entity.FlowLogDetailEntity;
import com.gw.das.service.market.FlowLogDetailService;

/**
 * service层公共方法
 * 
 * @author wayne
 */
@Service
public class FlowRlListenerImpl implements FlowListener {

	private static final Logger logger = LoggerFactory.getLogger(FlowRlListenerImpl.class);

	@Autowired
	private FlowLogDetailService flowLogDetailService;

	@Override
	public void updateBefore(FlowContext flowContext) {
		try {
			Object[] objAry = new Object[1];

			FlowInfo flowInfo = flowContext.getFlowInfo();
			logger.info("rl-before[phones=" + flowInfo.getPhones() + "]");

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
				detailVo.setInterfaceType(FlowChannelEnum.rl.getLabelKey());
				detailVo.setFlowSize(flowInfo.getFlowSizeRl() + "M");
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
			logger.info("rl-after[phones=" + flowInfo.getPhones() + "]");

			Map<String, FlowLogDetailEntity> tempRecordMap = (Map<String, FlowLogDetailEntity>) flowContext
					.getObjAry()[0];

			String[] phoneAry = flowInfo.getPhones().split(",");
			RlFlowResponse response = flowContext.getRlFlowResponse();

			int successNum = 0;
			int loseNum = 0;
			if (null != response) {
				logger.error("rl-返回statusCode：" + response.getStatusCode());
				// 000000代表请求成功
				if ("000000".equals(response.getStatusCode())) {
					// 请求成功
					// 更新
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						FlowLogDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
						detailVo.setSendStatus(FlowStatusEnum.sendSuccess.getLabelKey());// 充值成功
						detailVo.setResBatchNo(response.getCustomId() + "");
						// 1,充值中4,失败（测试档位返回3.成功）
						if(response.getStatus() == 1 || response.getStatus() == 3){
							detailVo.setResCode(response.getStatus() + "-" + response.getStatusCode() + "-" + response.getStatusMsg());
							detailVo.setSendStatus(FlowStatusEnum.sendSuccess.getLabelKey());// 充值成功
							successNum++;
						}else{
							detailVo.setResCode(response.getStatus() + "-" + response.getStatusCode() + "-" + response.getStatusMsg());
							detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
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
						detailVo.setResBatchNo(response.getCustomId() + "");
						detailVo.setResCode(response.getStatus() + "-" + response.getStatusCode() + "-" + response.getStatusMsg());
						flowLogDetailService.saveOrUpdate(detailVo);
					}
				}
			} else {
				loseNum = phoneAry.length;
				logger.error("rl-请求异常response=null");
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
			logger.info("rl-afterThrowable[phones=" + flowInfo.getPhones() + "]");

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
