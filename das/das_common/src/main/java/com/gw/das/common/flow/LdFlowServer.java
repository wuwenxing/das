package com.gw.das.common.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.SystemConfigUtil;

/**
 * 联动流量服务实现类
 * 
 * @author wayne
 */
public class LdFlowServer {
	private static final Logger logger = LoggerFactory.getLogger(LdFlowServer.class);
	private static final int POOL_SIZE = 5;
	private static ExecutorService theadPool;
	// 开发者账号信息
	private static String flowLdUrl = "";
	private static String flowLdAccount = "";
	private static String flowLdKpi = "";
	private static String systemActive = "";

	/**
	 * 流量监听器
	 */
	private List<FlowListener> flowListeners = new ArrayList<FlowListener>();

	/**
	 * 当类被载入时,静态代码块被执行,且只被执行一次
	 */
	static {
		theadPool = Executors.newFixedThreadPool(POOL_SIZE);
		flowLdUrl = SystemConfigUtil.getProperty(SystemConfigEnum.flowLdUrl);
		flowLdAccount = SystemConfigUtil.getProperty(SystemConfigEnum.flowLdAccount);
		flowLdKpi = SystemConfigUtil.getProperty(SystemConfigEnum.flowLdKpi);
		systemActive = SystemConfigUtil.getProperty(SystemConfigEnum.systemActive);
		logger.info(flowLdUrl + "," + flowLdAccount + "," + flowLdKpi + "," + systemActive);
	}

	/**
	 * 充值多条流量
	 */
	public void send(List<FlowInfo> flowInfos) {
		for (FlowInfo flowInfo : flowInfos) {
			send(flowInfo);
		}
	}

	/**
	 * 充值单条流量
	 */
	public void send(final FlowInfo flowInfo) {
		theadPool.execute(new Runnable() {
			public void run() {
				FlowContext flowContext = new FlowContext();
				flowContext.setFlowInfo(flowInfo);
				doBefore(flowContext);
				try {
					
					doAfter(flowContext);
				} catch (Exception e) {
					logger.error("ld-充值流量出现异常", e);
					flowContext.setThrowable(e);
					doAfterThrowable(flowContext);
				}
			}
		});
	}

	/**
	 * 给发送器添加监听器
	 * 
	 * @param flowListener
	 */
	public void addFlowListener(FlowListener flowListener) {
		this.flowListeners.add(flowListener);
	}

	private void doBefore(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateBefore(flowContext);
		}
	}

	private void doAfter(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateAfter(flowContext);
		}
	}

	private void doAfterThrowable(FlowContext flowContext) {
		for (FlowListener flowListener : this.flowListeners) {
			flowListener.updateAfterThrowable(flowContext);
		}
	}
}
