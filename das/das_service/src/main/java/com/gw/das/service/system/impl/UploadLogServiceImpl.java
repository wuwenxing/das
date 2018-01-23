package com.gw.das.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.system.UploadLogDao;
import com.gw.das.dao.system.entity.UploadLogEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.system.UploadLogService;

@Service
public class UploadLogServiceImpl extends BaseService implements UploadLogService {
	
	@Autowired
	private UploadLogDao uploadLogDao;

	@Override
	public UploadLogEntity findById(Long id) throws Exception {
		return (UploadLogEntity) uploadLogDao.findById(id, UploadLogEntity.class);
	}

	@Override
	public void saveOrUpdate(UploadLogEntity entity) throws Exception {
		if (null == entity.getUploadLogId()) {
			uploadLogDao.save(entity);
		} else {
			UploadLogEntity oldEntity = findById(entity.getUploadLogId());
			BeanUtils.copyProperties(entity, oldEntity);
			uploadLogDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception{
		uploadLogDao.deleteAllByIdArray(idArray.split(","), UploadLogEntity.class);
	}

	@Override
	public List<UploadLogEntity> findList(UploadLogEntity uploadLogEntity) throws Exception{
		return uploadLogDao.findList(uploadLogEntity);
	}
	
	@Override
	public PageGrid<UploadLogEntity> findPageList(PageGrid<UploadLogEntity> pageGrid) throws Exception {
		return uploadLogDao.findPageList(pageGrid);
	}

}