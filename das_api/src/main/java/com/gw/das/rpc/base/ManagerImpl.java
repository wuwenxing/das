package com.gw.das.rpc.base;

import com.gw.das.server.web.ApplicationContextFactory;
/**
 * 公共API实现
 * @author kirin.guan
 *
 */
public abstract class ManagerImpl implements Manager {

	protected static <T> T getService(Class<T> t) {
		return ApplicationContextFactory.getApplicationContext().getBean(t);
	}

	@Override
	public String getManagerName(Object... args) {
		return this.getClass().getSimpleName();
	}

}
