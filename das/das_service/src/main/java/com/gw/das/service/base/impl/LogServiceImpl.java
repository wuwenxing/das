package com.gw.das.service.base.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.enums.SystemLogEnum;
import com.gw.das.common.log.LogInfo;
import com.gw.das.common.log.LogListener;
import com.gw.das.common.log.LogServer;
import com.gw.das.service.base.LogService;

/**
 * service层公共方法
 * 
 * @author wayne
 */
@Service
public class LogServiceImpl implements LogService {

	private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);

	@Autowired
	private LogListener logListener;

	@Override
	public void addLog(final String url, final String param) {
		final Date date = new Date();
		final String loginIp = UserContext.get().getLoginIp();
		final String loginName = UserContext.get().getLoginName();
		final Long companyId = UserContext.get().getCompanyId();
		// 开启异步线程
		new Thread(new Runnable() {
			public void run() {
				try {
					LogServer server = new LogServer();
					server.setLogListener(logListener);
					LogInfo logInfo = new LogInfo();
					logInfo.setUrl(url);
					logInfo.setParam(param);
					logInfo.setLogType(SystemLogEnum.getLabelKeyByCon(url));
					logInfo.setCreateUser(loginName);
					logInfo.setCreateDate(date);
					logInfo.setCreateIp(loginIp);
					logInfo.setUpdateUser(loginName);
					logInfo.setUpdateDate(date);
					logInfo.setUpdateIp(loginIp);
					logInfo.setCompanyId(companyId);
					logInfo.setVersionNo(0L);
					logInfo.setEnableFlag("Y");
					logInfo.setDeleteFlag("N");
					server.addLog(logInfo);
				} catch (Exception e) {
					logger.error("开启异步线程error", e);
				}
			}
		}).start();
	}

}
