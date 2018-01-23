package com.gw.das.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.system.SystemThreadLogDao;
import com.gw.das.dao.system.entity.SystemThreadLogEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.system.SystemThreadLogService;

@Service
public class SystemThreadLogServiceImpl extends BaseService implements SystemThreadLogService {

	@Autowired
	private SystemThreadLogDao systemThreadLogDao;

	@Override
	public SystemThreadLogEntity findById(Long id) throws Exception {
		return (SystemThreadLogEntity) systemThreadLogDao.findById(id, SystemThreadLogEntity.class);
	}

	/**
	 * 返回该线程最后执行时间
	 */
	@Override
	public SystemThreadLogEntity findLastRecordByCode(String code) throws Exception {
		return systemThreadLogDao.findLastRecordByCode(code);
	}

	@Override
	public void saveOrUpdate(SystemThreadLogEntity entity) throws Exception {
		if (null == entity.getLogId()) {
			systemThreadLogDao.save(entity);
		} else {
			SystemThreadLogEntity oldEntity = findById(entity.getLogId());
			BeanUtils.copyProperties(entity, oldEntity);
			systemThreadLogDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		systemThreadLogDao.deleteAllByIdArray(idArray.split(","), SystemThreadLogEntity.class);
	}

	@Override
	public List<SystemThreadLogEntity> findList(SystemThreadLogEntity systemThreadLogEntity) throws Exception {
		return systemThreadLogDao.findList(systemThreadLogEntity);
	}

	@Override
	public PageGrid<SystemThreadLogEntity> findPageList(PageGrid<SystemThreadLogEntity> pageGrid) throws Exception {
		return systemThreadLogDao.findPageList(pageGrid);
	}

}