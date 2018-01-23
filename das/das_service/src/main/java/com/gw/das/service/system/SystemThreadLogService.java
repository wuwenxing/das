package com.gw.das.service.system;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.system.entity.SystemThreadLogEntity;

public interface SystemThreadLogService {

	public SystemThreadLogEntity findById(Long id) throws Exception;
	/**
	 * 返回该线程最后执行时间
	 */
	public SystemThreadLogEntity findLastRecordByCode(String code) throws Exception;
	public void saveOrUpdate(SystemThreadLogEntity entity) throws Exception;
	public List<SystemThreadLogEntity> findList(SystemThreadLogEntity systemThreadLogEntity) throws Exception;
	public PageGrid<SystemThreadLogEntity> findPageList(PageGrid<SystemThreadLogEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	
}
