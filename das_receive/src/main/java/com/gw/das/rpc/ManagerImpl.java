package com.gw.das.rpc;

import com.gw.das.common.spring.ApplicationContextFactory;
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
