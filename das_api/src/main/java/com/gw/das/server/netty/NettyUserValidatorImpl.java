package com.gw.das.server.netty;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.utils.SignUtil;

@Service
public class NettyUserValidatorImpl implements NettyUserValidator {

	private static final Logger logger = LoggerFactory.getLogger(NettyUserValidatorImpl.class);

	@Override
	public boolean validate(String timestamp, String sign, String sid) throws Exception {
		if (SignUtil.validate(timestamp, sign, sid)) {
			// 设置该登录用户的ThreadLocal信息
			this.setUserContext(sid);
			return true;
		}
		return false;
	}

	private void setUserContext(String sid) throws Exception {
		// 登录设置当前用户
		if (StringUtils.isNotBlank(sid)) {
			ClientUserContext context = new ClientUserContext();
			context.setSid(sid);
			context.setDate(new Date());
			if (SignUtil.GW_SID.equals(sid)) {
				// 营运平台为0
				context.setBusinessPlatform(BusinessPlatformEnum.gw.getLabelKeyInt());
			} else if (SignUtil.FX_SID.equals(sid)) {
				// 外汇平台为1
				context.setBusinessPlatform(BusinessPlatformEnum.Fx.getLabelKeyInt());
			} else if (SignUtil.PM_SID.equals(sid)) {
				// 贵金属平台为2
				context.setBusinessPlatform(BusinessPlatformEnum.Pm.getLabelKeyInt());
			} else if (SignUtil.HX_SID.equals(sid)) {
				// 恒信平台为3
				context.setBusinessPlatform(BusinessPlatformEnum.Hx.getLabelKeyInt());
			} else if (SignUtil.CF_SID.equals(sid)) {
				// 创富平台为4
				context.setBusinessPlatform(BusinessPlatformEnum.Cf.getLabelKeyInt());
			}
			context.setType("Netty"); // 使用Netty方式连接
			ClientUserContext.set(context);
			logger.info(context.toString());
		} else {
			logger.error("开发者账号为空!");
		}
	}

}
