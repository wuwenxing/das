package com.gw.das.common.lazy;

import java.io.Serializable;

import org.apache.log4j.Logger;

public abstract class LazyRefreshable<T> implements Serializable{
	
	private static final long serialVersionUID = 1285037075915309276L;

	private final static Logger logger = Logger.getLogger(LazyRefreshable.class);
	
	private int intervalInMs;//精确度为毫秒
	private T obj;
	private long lastUpdateTime = 0;
	
	/**
	 * 初始缓存时间构造函数
	 * @param intervalInMs
	 */
	public LazyRefreshable(int intervalInMs){
		this.intervalInMs = intervalInMs;
	}

	/**
	 * 初始缓存时间及值构造函数及设置最后更新时间
	 * @param intervalInMs
	 */
	public LazyRefreshable(int intervalInMs, T obj){
		this.intervalInMs = intervalInMs;
		this.obj = obj;
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public T get() throws Exception{
		if (isExpired()){
			return _get();
		}else{
			return getInCache();	
		}
	}
	
	/**
	 * Synchronized method to prevent two threads load data at the same time.
	 * @return
	 */
	private synchronized T _get() throws Exception{
		if (isExpired()){
			return load();
		}
		return getInCache();
	}
	
	public boolean isExpired(){
		return getInCache() == null || lastUpdateTime < System.currentTimeMillis() - intervalInMs;
	}
	
	public synchronized T load() throws Exception{
		logger.debug("Loading resource...");
		saveInCache(refresh());
		lastUpdateTime = System.currentTimeMillis();
		return getInCache();
	}
	
	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	protected T getInCache(){
		return this.obj;
	}
	
	protected void saveInCache(T obj){
		this.obj = obj;
	}
	
	protected abstract T refresh() throws Exception;

}
