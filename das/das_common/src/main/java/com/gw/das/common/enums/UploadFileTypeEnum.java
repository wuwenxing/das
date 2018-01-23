package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传文件类型定义
 * @author wayne
 */
public enum UploadFileTypeEnum implements EnumIntf {
	imageFile("图片文件", "imageFile"),
	textFile("文本文件", "textFile"),
	excelFile("Excel文件", "excelFile");
	
	private final String value;
	private final String labelKey;
	UploadFileTypeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<UploadFileTypeEnum> getList(){
		List<UploadFileTypeEnum> result = new ArrayList<UploadFileTypeEnum>();
		for(UploadFileTypeEnum ae : UploadFileTypeEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(UploadFileTypeEnum ae : UploadFileTypeEnum.values()){
			if(ae.getLabelKey().equals(labelKey)){
				return ae.getValue();
			}
		}
		return labelKey;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
