package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.FlowStatusEnum;
import com.gw.das.dao.market.entity.FlowLogDetailEntity;

public interface FlowLogDetailService {

	public FlowLogDetailEntity findById(Long id) throws Exception;

	public void saveOrUpdate(FlowLogDetailEntity entity) throws Exception;

	public int updateFlowSendStatus(String batchNo, String phone, String code, FlowStatusEnum sendStatusEnum) throws Exception;
	
	public List<FlowLogDetailEntity> findList(FlowLogDetailEntity flowLogDetailEntity) throws Exception;

	public PageGrid<FlowLogDetailEntity> findPageList(PageGrid<FlowLogDetailEntity> pageGrid) throws Exception;

	public void deleteByIdArray(String idArray) throws Exception;
}
