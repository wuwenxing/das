package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.FlowLogEntity;

public interface FlowLogService {

	public FlowLogEntity findById(Long id) throws Exception;

	public void saveOrUpdate(FlowLogEntity entity) throws Exception;

	public List<FlowLogEntity> findList(FlowLogEntity flowLogService) throws Exception;

	public PageGrid<FlowLogEntity> findPageList(PageGrid<FlowLogEntity> pageGrid) throws Exception;

}
