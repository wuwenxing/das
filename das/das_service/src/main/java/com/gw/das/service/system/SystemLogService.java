package com.gw.das.service.system;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.system.entity.SystemLogEntity;

public interface SystemLogService {

	public SystemLogEntity findById(Long id) throws Exception;
	public void save(SystemLogEntity entity) throws Exception ;
	public void deleteByIdArray(String idArray) throws Exception;
	public List<SystemLogEntity> findList(SystemLogEntity systemLogEntity) throws Exception;
	public PageGrid<SystemLogEntity> findPageList(PageGrid<SystemLogEntity> pageGrid) throws Exception;
}
