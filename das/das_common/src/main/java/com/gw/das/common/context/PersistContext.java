package com.gw.das.common.context;

/**
 * phantomjs执行上下文对象
 */
public class PersistContext {
	
	private String persistId;//唯一标示
	private String flag = "N";//标示是否执行成功

	public PersistContext(String persistId) {
		this.persistId = persistId;
	}

	public String getPersistId() {
		return persistId;
	}

	public void setPersistId(String persistId) {
		this.persistId = persistId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}