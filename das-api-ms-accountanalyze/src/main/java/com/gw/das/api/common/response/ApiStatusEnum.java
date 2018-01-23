package com.gw.das.api.common.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 接口请求状态枚举类型
 * @author wayne
 */
public enum ApiStatusEnum {
	success("请求成功", "0"),
	fail("请求失败", "1"),
	exception("接口异常", "2"),
	error_companyId_param("公司ID参数输入有误，请检查输入参数","3"),
	error_platform_param("平台参数输入有误，请检查输入参数","4"),
	error_accountNo_param("交易账号参数输入有误，请检查输入参数","5"),
	error_startDate_param("开始时间参数输入有误，请检查输入参数","6"),
	error_endDate_param("结束时间参数输入有误，请检查输入参数","7"),
	param_is_empty("接口输入参数为空，请检查输入参数","8"),
	param_is_int("接口输入参数不是整数，请检查输入参数","9");
	
	private final String value;
	private final String labelKey;
	ApiStatusEnum(String _operator, String labelKey) {
		this.value = _operator;
		this.labelKey = labelKey;
	}
	
	public static List<ApiStatusEnum> getList(){
		List<ApiStatusEnum> result = new ArrayList<ApiStatusEnum>();
		for(ApiStatusEnum ae : ApiStatusEnum.values()){
			result.add(ae);
		}
		return result;
	}
	
	public static String format(String labelKey){
		for(ApiStatusEnum ae : ApiStatusEnum.values()){
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
