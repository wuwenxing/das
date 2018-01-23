package com.gw.das.rpc.base;

/**
 * 公共API接口，所有API接口必须继承该接口
 * 
 * @author wayne
 */
public interface Manager {

	/**
	 * 获取指定API接口管理类
	 * @param args
	 * @return
	 */
	public String getManagerName(Object... args);

}
