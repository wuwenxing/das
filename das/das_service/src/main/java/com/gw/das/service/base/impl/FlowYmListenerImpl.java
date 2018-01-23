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
import com.gw.das.common.flow.YmFlowResponse;
import com.gw.das.dao.market.entity.FlowLogDetailEntity;
import com.gw.das.service.market.FlowLogDetailService;

/**
 * service层公共方法
 * 
 * @author wayne
 */
@Service
public class FlowYmListenerImpl implements FlowListener {

	private static final Logger logger = LoggerFactory.getLogger(FlowYmListenerImpl.class);

	@Autowired
	private FlowLogDetailService flowLogDetailService;

	@Override
	public void updateBefore(FlowContext flowContext) {
		try {
			Object[] objAry = new Object[1];

			FlowInfo flowInfo = flowContext.getFlowInfo();
			logger.info("ym-before[phones=" + flowInfo.getPhones() + "]");

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
				detailVo.setInterfaceType(FlowChannelEnum.ym.getLabelKey());
				detailVo.setFlowSize(flowInfo.getFlowPackage().getLabelKey());
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
			logger.info("ym-after[phones=" + flowInfo.getPhones() + "]");

			Map<String, FlowLogDetailEntity> tempRecordMap = (Map<String, FlowLogDetailEntity>) flowContext
					.getObjAry()[0];

			String[] phoneAry = flowInfo.getPhones().split(",");
			YmFlowResponse response = flowContext.getYmFlowResponse();

			int successNum = 0;
			int loseNum = 0;
			if (null != response) {
				logger.error("ym-返回code：" + response.getCode());
				// code="M0001"代表请求成功
				if ("M0001".equals(response.getCode())) {
					// 请求成功
					// 更新
					// 不能处理的手机号码
					List<String> errorMobiles = response.getErrorMobiles();
					if(errorMobiles.size() > 0){
						for(int i = 0; i < errorMobiles.size(); i++){
							String mobiles = errorMobiles.get(i);
							FlowLogDetailEntity detailVo = tempRecordMap.get(mobiles);
							detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
							detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
							detailVo.setResBatchNo(response.getBatchNo());
							detailVo.setResCode(response.getCode() + "-" + response.getMsg());
							flowLogDetailService.saveOrUpdate(detailVo);
							loseNum++;
						}
					}
					// 其他处理成功的手机号码
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						if(errorMobiles.size()>0 && errorMobiles.contains(tempMobile)){
							continue;
						}
						FlowLogDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
						detailVo.setSendStatus(FlowStatusEnum.sendSuccess.getLabelKey());// 充值成功
						detailVo.setResBatchNo(response.getBatchNo());
						detailVo.setResCode(response.getCode() + "-" + response.getMsg());
						flowLogDetailService.saveOrUpdate(detailVo);
						successNum++;
					}
				} else {
					// 请求失败
					loseNum = phoneAry.length;
					for (int i = 0; i < phoneAry.length; i++) {
						String tempMobile = phoneAry[i];
						FlowLogDetailEntity detailVo = tempRecordMap.get(tempMobile);
						detailVo.setCommitStatus(CommitStatusEnum.commitSuccess.getLabelKey());// 提交状态
						detailVo.setSendStatus(FlowStatusEnum.sendFail.getLabelKey());// 充值失败
						detailVo.setResBatchNo(response.getBatchNo());
						detailVo.setResCode(response.getCode() + "-" + response.getMsg());
						flowLogDetailService.saveOrUpdate(detailVo);
					}
				}
			} else {
				loseNum = phoneAry.length;
				logger.error("ym-请求异常response=null");
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
			logger.info("ym-afterThrowable[phones=" + flowInfo.getPhones() + "]");

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
