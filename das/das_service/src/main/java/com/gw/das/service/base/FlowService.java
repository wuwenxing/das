package com.gw.das.service.base;

import com.gw.das.dao.market.entity.FlowLogEntity;

public interface FlowService {

	/**
	 * 开启异步线程
	 */
	public void asynThreading(final FlowLogEntity flowLog, final String flowChannel);

}
