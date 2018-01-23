package com.gw.das.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * ResultCode类型定义
 * @author wayne
 */
public enum ResultCodeEnum implements EnumIntf {

	fail("操作失败", "fail"),
	exception("系统异常", "exception"),
	success("操作成功", "success"),
	addSuccess("新增成功", "success"),
	saveSuccess("保存成功", "success"),
	updateSuccess("更新成功", "success"),
	deleteSuccess("删除成功", "success");
	
	private final String value;
	private final String labelKey;
	ResultCodeEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ResultCodeEnum> getList(){
		List<ResultCodeEnum> result = new ArrayList<ResultCodeEnum>();
		for(ResultCodeEnum ae : ResultCodeEnum.values()){
			result.add(ae);
		}
		return result;
	}

	public String getValue() {
		return value;
	}

	public String getLabelKey() {
		return labelKey;
	}
	
}
