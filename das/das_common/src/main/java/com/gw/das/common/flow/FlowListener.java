package com.gw.das.common.flow;

/**
 * 流量服务监听器
 * @author wayne
 */
public abstract interface FlowListener {
	/**
	 * 流量充值以前做的操作
	 * @param emailInfo
	 */
	public abstract void updateBefore(FlowContext flowContext);
	/**
	 * 流量充值成功的操作
	 * @param emailContext
	 */
	public abstract void updateAfter(FlowContext flowContext);
	/**
	 * 流量充值异常时进行的错误处理
	 */
	public abstract void updateAfterThrowable(FlowContext flowContext);
}
