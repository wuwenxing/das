package com.gw.das.common.sms;

/**
 * 短信发送执行的上下文信息-即存储开始到结束的数据
 * 
 * @author wayne
 */
public class SmsContext {

	private SmsInfo smsInfo;// 待发送的短信实体
	private Object[] objAry; // 保存的上下文对象
	private ZqhlSmsResponse zqhlSmsResponse;// 发送后返回的实体对象-对应至臻互联短信通道
	private MdkjSmsResponse mdkjResponse;// 发送后返回的实体对象-对应秒嘀科技短信通道
	private FcySmsResponse fcyResponse;// 发送后返回的实体对象-对应发财鱼短信通道
	private HxApiResponse hxResponse;// 发送后返回的实体对象-对应恒信短信通道
	private Throwable throwable;// 异常情况对象

	public SmsInfo getSmsInfo() {
		return smsInfo;
	}

	public void setSmsInfo(SmsInfo smsInfo) {
		this.smsInfo = smsInfo;
	}

	public Object[] getObjAry() {
		return objAry;
	}

	public void setObjAry(Object[] objAry) {
		this.objAry = objAry;
	}

	public FcySmsResponse getFcyResponse() {
		return fcyResponse;
	}

	public void setFcyResponse(FcySmsResponse fcyResponse) {
		this.fcyResponse = fcyResponse;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public MdkjSmsResponse getMdkjResponse() {
		return mdkjResponse;
	}

	public void setMdkjResponse(MdkjSmsResponse mdkjResponse) {
		this.mdkjResponse = mdkjResponse;
	}

	public ZqhlSmsResponse getZqhlSmsResponse() {
		return zqhlSmsResponse;
	}

	public void setZqhlSmsResponse(ZqhlSmsResponse zqhlSmsResponse) {
		this.zqhlSmsResponse = zqhlSmsResponse;
	}

	public HxApiResponse getHxResponse() {
		return hxResponse;
	}

	public void setHxResponse(HxApiResponse hxResponse) {
		this.hxResponse = hxResponse;
	}

}
