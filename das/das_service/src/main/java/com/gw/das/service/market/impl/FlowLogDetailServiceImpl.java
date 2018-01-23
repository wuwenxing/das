package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.FlowStatusEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.FlowLogDetailDao;
import com.gw.das.dao.market.entity.FlowLogDetailEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.FlowLogDetailService;

@Service
public class FlowLogDetailServiceImpl extends BaseService implements FlowLogDetailService {

	@Autowired
	private FlowLogDetailDao flowLogDetailDao;

	@Override
	public FlowLogDetailEntity findById(Long id) throws Exception {
		return (FlowLogDetailEntity) flowLogDetailDao.findById(id, FlowLogDetailEntity.class);
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		flowLogDetailDao.deleteAllByIdArray(idArray.split(","), FlowLogDetailEntity.class);
	}

	@Override
	public void saveOrUpdate(FlowLogDetailEntity entity) throws Exception {
		if (null == entity.getDetailId()) {
			flowLogDetailDao.save(entity);
		} else {
			FlowLogDetailEntity oldEntity = findById(entity.getDetailId());
			BeanUtils.copyProperties(entity, oldEntity);
			flowLogDetailDao.update(oldEntity);
		}
	}

	@Override
	public int updateFlowSendStatus(String batchNo, String phone, String code, FlowStatusEnum sendStatusEnum)
			throws Exception {
		return flowLogDetailDao.updateFlowSendStatus(batchNo, phone, code, sendStatusEnum);
	}

	@Override
	public List<FlowLogDetailEntity> findList(FlowLogDetailEntity flowLogDetailEntity) throws Exception {
		return flowLogDetailDao.findList(flowLogDetailEntity);
	}

	@Override
	public PageGrid<FlowLogDetailEntity> findPageList(PageGrid<FlowLogDetailEntity> pageGrid) throws Exception {
		return flowLogDetailDao.findPageList(pageGrid);
	}

}