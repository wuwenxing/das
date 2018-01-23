package com.gw.das.common.context;

import java.util.HashMap;
import java.util.Map;

import com.gw.das.common.enums.UploadFileTypeEnum;

/**
 * 上传文件的上下文信息-即存储开始到结束的数据
 * @author wayne
 */
public class UploadContext {

	// 上传属性信息
	private String fileName; // 上传文件名称
	private UploadFileTypeEnum fileType; // 上传文件类型
	
	// 返回属性信息
	private String resCode; // 上传返回Code
	private String resMsg; // 上传返回描述信息
	private String resUrl; // 上传返回文件的url(相对路径)
	private Map<String, Object> resParameters = new HashMap<String, Object>();// 上传返回其他参数信息
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public UploadFileTypeEnum getFileType() {
		return fileType;
	}
	public void setFileType(UploadFileTypeEnum fileType) {
		this.fileType = fileType;
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	public String getResUrl() {
		return resUrl;
	}
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	public Map<String, Object> getResParameters() {
		return resParameters;
	}
	public void setResParameters(Map<String, Object> resParameters) {
		this.resParameters = resParameters;
	}
	
}
