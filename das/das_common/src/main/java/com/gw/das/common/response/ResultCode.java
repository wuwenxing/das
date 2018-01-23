package com.gw.das.common.response;

import java.util.ArrayList;
import java.util.List;

import com.gw.das.common.enums.ResultCodeEnum;

/**
 * 返回的消息数据封装
 * @author wayne
 */
public class ResultCode {

	private String resultCode;
	private String resultMsg;
	private List<ErrorCode> errorInfoList = new ArrayList<ErrorCode>();

	/**
	 * 构造函数
	 */
	public ResultCode() {
		
	}
	
	/**
	 * 构造函数
	 */
	public ResultCode(String resultCode, String resultMsg) {
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

	/**
	 * 构造函数
	 */
	public ResultCode(ResultCodeEnum resultCodeEnum) {
		this.resultCode = resultCodeEnum.getLabelKey();
		this.resultMsg = resultCodeEnum.getValue();
	}
	
	/**
	 * 添加错误对象集合
	 * @param errorInfo
	 */
	public void addErrorInfo(ErrorCode errorInfo) {
		this.errorInfoList.add(errorInfo);
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public List<ErrorCode> getErrorInfoList() {
		return errorInfoList;
	}

	public void setErrorInfoList(List<ErrorCode> errorInfoList) {
		this.errorInfoList = errorInfoList;
	}

}
