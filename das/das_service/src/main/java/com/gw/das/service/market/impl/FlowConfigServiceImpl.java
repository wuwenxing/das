package com.gw.das.service.market.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.FlowConfigDao;
import com.gw.das.dao.market.entity.FlowConfigEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.FlowConfigService;

@Service
public class FlowConfigServiceImpl extends BaseService implements FlowConfigService {

	@Autowired
	private FlowConfigDao flowConfigDao;

	@Override
	public FlowConfigEntity findById(Long id) throws Exception {
		return (FlowConfigEntity) flowConfigDao.findById(id, FlowConfigEntity.class);
	}
	
	@Override
	public FlowConfigEntity findByCompanyId() throws Exception {
		return flowConfigDao.findByCompanyId();
	}
	
	@Override
	public void saveOrUpdate(FlowConfigEntity entity) throws Exception {
		if (null == entity.getFlowConfigId()) {
			flowConfigDao.save(entity);
		} else {
			FlowConfigEntity oldEntity = findById(entity.getFlowConfigId());
			BeanUtils.copyProperties(entity, oldEntity);
			flowConfigDao.update(oldEntity);
		}
	}

}