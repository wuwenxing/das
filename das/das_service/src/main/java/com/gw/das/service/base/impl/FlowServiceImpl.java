package com.gw.das.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.Constants;
import com.gw.das.common.enums.FlowChannelEnum;
import com.gw.das.common.enums.FlowPackageEnum;
import com.gw.das.common.enums.MobileOperatorEnum;
import com.gw.das.common.flow.FlowInfo;
import com.gw.das.common.flow.FlowListener;
import com.gw.das.common.flow.LmFlowServer;
import com.gw.das.common.flow.RlFlowServer;
import com.gw.das.common.flow.YmFlowServer;
import com.gw.das.common.sms.SmsUtil;
import com.gw.das.common.utils.MD5;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.market.entity.FlowLogEntity;
import com.gw.das.service.base.FlowService;

/**
 * 流量接口实现
 * 
 * @author wayne
 */
@Service
public class FlowServiceImpl implements FlowService {

	private static final Logger logger = LoggerFactory.getLogger(FlowServiceImpl.class);

	@Autowired
	@Qualifier("flowYmListenerImpl")
	private FlowListener flowYmListener;
	@Autowired
	@Qualifier("flowLmListenerImpl")
	private FlowListener flowLmListener;
	@Autowired
	@Qualifier("flowRlListenerImpl")
	private FlowListener flowRlListener;

	/**
	 * 开启异步线程 flowChannel:流量渠道
	 */
	public void asynThreading(final FlowLogEntity flowLog, final String flowChannel) {
		if (null != flowChannel) {
			new Thread(new Runnable() {
				public void run() {
					try {
						send(flowLog, flowChannel);
					} catch (Exception e) {
						logger.error("开启异步线程error", e);
					}
				}
			}).start();
		}
	}

	/**
	 * 流量充值
	 */
	private void send(FlowLogEntity flowLog, String flowChannel) throws Exception {
		YmFlowServer ymFlowServer = null;
		LmFlowServer lmFlowServer = null;
		RlFlowServer rlFlowServer = null;
		// 流量充值-并添加监听器-统计发送数量及状态
		if (flowChannel.equals(FlowChannelEnum.ym.getLabelKey())) {
			ymFlowServer = new YmFlowServer();
			ymFlowServer.addFlowListener(flowYmListener);
		}else if(flowChannel.equals(FlowChannelEnum.lm.getLabelKey())){
			lmFlowServer = new LmFlowServer();
			lmFlowServer.addFlowListener(flowLmListener);
		}else if(flowChannel.equals(FlowChannelEnum.rl.getLabelKey())){
			rlFlowServer = new RlFlowServer();
			rlFlowServer.addFlowListener(flowRlListener);
		}
		
		// 超过50条，需要分成多批发送
		String phones = flowLog.getLegalPhones();// 待发送的合法手机号
		List<String> phoneList = StringUtil.string2List(phones);
		
		if (flowChannel.equals(FlowChannelEnum.ym.getLabelKey())) {
			List<List<String>> mobileBatches = SmsUtil.bigList2MultiSmallList(phoneList, Constants.batchSizeFlow);
			for (List<String> mobileBatch : mobileBatches) {
				// 流量实体封装
				FlowInfo flowInfo = new FlowInfo();
				flowInfo.setFlowLogId(flowLog.getFlowLogId());
				flowInfo.setPhones(SmsUtil.getMobile(mobileBatch));
				FlowPackageEnum flowPackage = FlowPackageEnum.check(flowLog.getFlowSize());
				flowInfo.setFlowPackage(flowPackage);
				flowInfo.setCompanyId(flowLog.getCompanyId());

				ymFlowServer.send(flowInfo);
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		}else if(flowChannel.equals(FlowChannelEnum.lm.getLabelKey())){
			// 乐免一次只能充值一个号码
			for (String mobile : phoneList) {
				// 充值流量包
				FlowPackageEnum flowPackage = FlowPackageEnum.check(flowLog.getFlowSize());
				
				// 号码段判断,进行流量拆分，多次充值
				MobileOperatorEnum operator = MobileOperatorEnum.checkOpeator(mobile);
				if(MobileOperatorEnum.cmcc.getLabelKey().equals(operator.getLabelKey())){
					String flowSizeAry = flowPackage.getCmccLm();
					String[] ary = flowSizeAry.split(",");
					for(String flowSize: ary){
						if(StringUtils.isNotBlank(flowSize)){
							// 流量实体封装
							FlowInfo flowInfo = new FlowInfo();
							flowInfo.setFlowLogId(flowLog.getFlowLogId());
							flowInfo.setPhones(mobile);
							flowInfo.setCompanyId(flowLog.getCompanyId());
							flowInfo.setFlowSizeLm(flowSize);
							lmFlowServer.send(flowInfo);
						}
					}
				}else if(MobileOperatorEnum.cucc.getLabelKey().equals(operator.getLabelKey())){
					String flowSizeAry = flowPackage.getCuccLm();
					String[] ary = flowSizeAry.split(",");
					for(String flowSize: ary){
						if(StringUtils.isNotBlank(flowSize)){
							// 流量实体封装
							FlowInfo flowInfo = new FlowInfo();
							flowInfo.setFlowLogId(flowLog.getFlowLogId());
							flowInfo.setPhones(mobile);
							flowInfo.setCompanyId(flowLog.getCompanyId());
							flowInfo.setFlowSizeLm(flowSize);
							lmFlowServer.send(flowInfo);
						}
					}
				}else if(MobileOperatorEnum.ctcc.getLabelKey().equals(operator.getLabelKey())){
					String flowSizeAry = flowPackage.getCtccLm();
					String[] ary = flowSizeAry.split(",");
					for(String flowSize: ary){
						if(StringUtils.isNotBlank(flowSize)){
							// 流量实体封装
							FlowInfo flowInfo = new FlowInfo();
							flowInfo.setFlowLogId(flowLog.getFlowLogId());
							flowInfo.setPhones(mobile);
							flowInfo.setCompanyId(flowLog.getCompanyId());
							flowInfo.setFlowSizeLm(flowSize);
							lmFlowServer.send(flowInfo);
						}
					}
				}
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		}else if (flowChannel.equals(FlowChannelEnum.rl.getLabelKey())) {
			// 容联一次只能充值一个号码
			for (String mobile : phoneList) {
				// 充值流量包
				FlowPackageEnum flowPackage = FlowPackageEnum.check(flowLog.getFlowSize());
				
				// 号码段判断,进行流量拆分，多次充值
				MobileOperatorEnum operator = MobileOperatorEnum.checkOpeator(mobile);
				if(MobileOperatorEnum.cmcc.getLabelKey().equals(operator.getLabelKey())){
					String flowSizeAry = flowPackage.getCmccRl();
					String[] ary = flowSizeAry.split(";");
					for(int i=0; i<ary.length; i++){
						String flowNoOrSize = ary[i];
						if(StringUtils.isNotBlank(flowNoOrSize)){
							String flowNo = flowNoOrSize.split(",")[0];
							String flowSize = flowNoOrSize.split(",")[1];
							if(StringUtils.isNotBlank(flowSize)){
								// 流量实体封装
								FlowInfo flowInfo = new FlowInfo();
								flowInfo.setFlowLogId(flowLog.getFlowLogId());
								flowInfo.setPhones(mobile);
								flowInfo.setCompanyId(flowLog.getCompanyId());
								flowInfo.setCustomIdRl(MD5.getMd5(flowLog.getFlowLogId() + mobile + i));//保证唯一即可
								flowInfo.setFlowNoRl(flowNo);
								flowInfo.setFlowSizeRl(flowSize);
								rlFlowServer.send(flowInfo);
							}
						}
					}
				}else if(MobileOperatorEnum.cucc.getLabelKey().equals(operator.getLabelKey())){
					String flowSizeAry = flowPackage.getCuccRl();
					String[] ary = flowSizeAry.split(";");
					for(int i=0; i<ary.length; i++){
						String flowNoOrSize = ary[i];
						if(StringUtils.isNotBlank(flowNoOrSize)){
							String flowNo = flowNoOrSize.split(",")[0];
							String flowSize = flowNoOrSize.split(",")[1];
							if(StringUtils.isNotBlank(flowSize)){
								// 流量实体封装
								FlowInfo flowInfo = new FlowInfo();
								flowInfo.setFlowLogId(flowLog.getFlowLogId());
								flowInfo.setPhones(mobile);
								flowInfo.setCompanyId(flowLog.getCompanyId());
								flowInfo.setCustomIdRl(MD5.getMd5(flowLog.getFlowLogId() + mobile + i));//保证唯一即可
								flowInfo.setFlowNoRl(flowNo);
								flowInfo.setFlowSizeRl(flowSize);
								rlFlowServer.send(flowInfo);
							}
						}
					}
				}else if(MobileOperatorEnum.ctcc.getLabelKey().equals(operator.getLabelKey())){
					String flowSizeAry = flowPackage.getCtccRl();
					String[] ary = flowSizeAry.split(";");
					for(int i=0; i<ary.length; i++){
						String flowNoOrSize = ary[i];
						if(StringUtils.isNotBlank(flowNoOrSize)){
							String flowNo = flowNoOrSize.split(",")[0];
							String flowSize = flowNoOrSize.split(",")[1];
							if(StringUtils.isNotBlank(flowSize)){
								// 流量实体封装
								FlowInfo flowInfo = new FlowInfo();
								flowInfo.setFlowLogId(flowLog.getFlowLogId());
								flowInfo.setPhones(mobile);
								flowInfo.setCompanyId(flowLog.getCompanyId());
								flowInfo.setCustomIdRl(MD5.getMd5(flowLog.getFlowLogId() + mobile + i));//保证唯一即可
								flowInfo.setFlowNoRl(flowNo);
								flowInfo.setFlowSizeRl(flowSize);
								rlFlowServer.send(flowInfo);
							}
						}
					}
				}
				// 每批次发送完等待200毫秒
				Thread.sleep(200);
			}
		}

	}

}
