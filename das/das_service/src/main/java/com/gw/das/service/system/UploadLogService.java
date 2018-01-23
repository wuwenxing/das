package com.gw.das.service.system;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.system.entity.UploadLogEntity;

public interface UploadLogService {

	public UploadLogEntity findById(Long id) throws Exception;
	public void saveOrUpdate(UploadLogEntity entity) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public List<UploadLogEntity> findList(UploadLogEntity uploadLogEntity) throws Exception;
	public PageGrid<UploadLogEntity> findPageList(PageGrid<UploadLogEntity> pageGrid) throws Exception;
}
