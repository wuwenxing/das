package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.FlowLogDao;
import com.gw.das.dao.market.entity.FlowLogEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.FlowLogService;

@Service
public class FlowLogServiceImpl extends BaseService implements FlowLogService {

	@Autowired
	private FlowLogDao flowLogDao;

	@Override
	public FlowLogEntity findById(Long id) throws Exception {
		return (FlowLogEntity) flowLogDao.findById(id, FlowLogEntity.class);
	}

	@Override
	public void saveOrUpdate(FlowLogEntity entity) throws Exception {
		if (null == entity.getFlowLogId()) {
			flowLogDao.save(entity);
		} else {
			FlowLogEntity oldEntity = findById(entity.getFlowLogId());
			BeanUtils.copyProperties(entity, oldEntity);
			flowLogDao.update(oldEntity);
		}
	}

	@Override
	public List<FlowLogEntity> findList(FlowLogEntity flowLogEntity) throws Exception {
		return flowLogDao.findList(flowLogEntity);
	}

	@Override
	public PageGrid<FlowLogEntity> findPageList(PageGrid<FlowLogEntity> pageGrid) throws Exception {
		return flowLogDao.findPageList(pageGrid);
	}

}