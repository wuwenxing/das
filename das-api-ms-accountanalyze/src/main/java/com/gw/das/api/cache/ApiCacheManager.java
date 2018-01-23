package com.gw.das.api.cache;

import java.util.concurrent.TimeUnit;

/**
 * redis缓存
 * 
 * @author darren
 *
 */
public interface ApiCacheManager {

	/**
	 * 放入缓存，默认时间5分钟
	 * 
	 * @param key
	 * @param data
	 */
	void put(String key, Object data);
	
	/**
	 * 放入缓存
	 * 
	 * @param key
	 * @param data
	 * @param expire
	 */
	void put(String key, Object data, long expire);
	
	/**
	 * 放入缓存
	 * 
	 * @param key
	 * @param data
	 * @param expire
	 * @param timeUnit
	 */
	public void put(String key, Object data, long expire, TimeUnit timeUnit);

	/**
	 * 获取已缓存的值
	 * 
	 * @param key
	 * @return
	 */
	Object get(String key);
}
