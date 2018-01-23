package com.gw.das.service.market;

import com.gw.das.dao.market.entity.FlowConfigEntity;

public interface FlowConfigService {

	public FlowConfigEntity findById(Long id) throws Exception;
	public FlowConfigEntity findByCompanyId() throws Exception;
	public void saveOrUpdate(FlowConfigEntity entity) throws Exception;
	
}
