package com.gw.das.common.flow;

/**
 * 流量充值执行的上下文信息-即存储开始到结束的数据
 * 
 * @author wayne
 */
public class FlowContext {

	private FlowInfo flowInfo;// 待流量充值实体
	private Object[] objAry; // 保存的上下文对象
	private YmFlowResponse ymFlowResponse;// 发送后返回的实体对象-亿美
	private LmFlowResponse lmFlowResponse;// 发送后返回的实体对象-乐免
	private RlFlowResponse rlFlowResponse;// 发送后返回的实体对象-容联
	private Throwable throwable;// 异常情况对象

	public FlowInfo getFlowInfo() {
		return flowInfo;
	}

	public void setFlowInfo(FlowInfo flowInfo) {
		this.flowInfo = flowInfo;
	}

	public Object[] getObjAry() {
		return objAry;
	}

	public void setObjAry(Object[] objAry) {
		this.objAry = objAry;
	}

	public YmFlowResponse getYmFlowResponse() {
		return ymFlowResponse;
	}

	public void setYmFlowResponse(YmFlowResponse ymFlowResponse) {
		this.ymFlowResponse = ymFlowResponse;
	}

	public LmFlowResponse getLmFlowResponse() {
		return lmFlowResponse;
	}

	public void setLmFlowResponse(LmFlowResponse lmFlowResponse) {
		this.lmFlowResponse = lmFlowResponse;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public RlFlowResponse getRlFlowResponse() {
		return rlFlowResponse;
	}

	public void setRlFlowResponse(RlFlowResponse rlFlowResponse) {
		this.rlFlowResponse = rlFlowResponse;
	}

}
