package com.gw.das.common.netty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.SignUtil;
import com.gw.das.common.utils.SystemConfigUtil;

public class RpcUtils {

	/**
	 * 请求参数处理 根据companyId选择对应sid与token
	 */
	public static String requestParamHandler(Map<String, String> map, Long companyId) {
		// 时间戳
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timestamp = sdf.format(new Date());
		map.put("timestamp", timestamp);
		if (companyId == CompanyEnum.gw.getLabelKeyLong()) {
			map.put("sid", SignUtil.GW_SID);
			map.put("sign", SignUtil.getSign(timestamp, SignUtil.GW_SID));
			map.put("businessPlatform", CompanyEnum.gw.getLabelKey());
		} else if (companyId == CompanyEnum.fx.getLabelKeyLong()) {
			map.put("sid", SignUtil.FX_SID);
			map.put("sign", SignUtil.getSign(timestamp, SignUtil.FX_SID));
			map.put("businessPlatform", CompanyEnum.fx.getLabelKey());
		} else if (companyId == CompanyEnum.pm.getLabelKeyLong()) {
			map.put("sid", SignUtil.PM_SID);
			map.put("sign", SignUtil.getSign(timestamp, SignUtil.PM_SID));
			map.put("businessPlatform", CompanyEnum.pm.getLabelKey());
		} else if (companyId == CompanyEnum.hx.getLabelKeyLong()) {
			map.put("sid", SignUtil.HX_SID);
			map.put("sign", SignUtil.getSign(timestamp, SignUtil.HX_SID));
			map.put("businessPlatform", CompanyEnum.hx.getLabelKey());
		} else if (companyId == CompanyEnum.cf.getLabelKeyLong()) {
			map.put("sid", SignUtil.CF_SID);
			map.put("sign", SignUtil.getSign(timestamp, SignUtil.CF_SID));
			map.put("businessPlatform", CompanyEnum.cf.getLabelKey());
		}
		String str = JacksonUtil.toJSon(map);
		return str;
	}

	/**
	 * 发送post请求 根据companyId选择对应sid与token
	 */
	public static RpcResult post(String url, Map<String, String> map, Long companyId) throws Exception {
		String argsJson = RpcUtils.requestParamHandler(map, companyId);
		String returnStr = HttpUtil.getInstance()
				.doPostHessian(SystemConfigUtil.getProperty(SystemConfigEnum.dasApiUrl) + url, argsJson);
		RpcResult rpcResult = JacksonUtil.readValue(returnStr, RpcResult.class);
		return rpcResult;
	}

	public static void main(String[] args) {

	}

}
