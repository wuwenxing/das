package com.gw.das.service.system.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.log.LogInfo;
import com.gw.das.common.log.LogListener;
import com.gw.das.dao.system.SystemLogDao;
import com.gw.das.dao.system.entity.SystemLogEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.system.SystemLogService;

@Service
public class SystemLogServiceImpl extends BaseService implements SystemLogService, LogListener {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogServiceImpl.class);
	
	@Autowired
	private SystemLogDao systemLogDao;

	@Override
	public SystemLogEntity findById(Long id) throws Exception {
		return (SystemLogEntity) systemLogDao.findById(id, SystemLogEntity.class);
	}

	@Override
	public void save(SystemLogEntity entity) throws Exception {
		systemLogDao.save(entity);
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception{
		systemLogDao.deleteAllByIdArray(idArray.split(","), SystemLogEntity.class);
	}

	@Override
	public List<SystemLogEntity> findList(SystemLogEntity systemLogEntity) throws Exception{
		return systemLogDao.findList(systemLogEntity);
	}
	
	@Override
	public PageGrid<SystemLogEntity> findPageList(PageGrid<SystemLogEntity> pageGrid) throws Exception {
		return systemLogDao.findPageList(pageGrid);
	}

	@Override
	public void addLog(LogInfo logInfo) {
		try{
			SystemLogEntity entity = new SystemLogEntity();
			entity.setUrl(logInfo.getUrl());
			entity.setParam(logInfo.getParam());
			entity.setLogType(logInfo.getLogType());
			entity.setRemark(logInfo.getRemark());
			entity.setCreateUser(logInfo.getCreateUser());
			entity.setCreateDate(logInfo.getCreateDate());
			entity.setCreateIp(logInfo.getCreateIp());
			entity.setUpdateUser(logInfo.getUpdateUser());
			entity.setUpdateDate(logInfo.getUpdateDate());
			entity.setUpdateIp(logInfo.getUpdateIp());
			entity.setCompanyId(logInfo.getCompanyId());
			entity.setVersionNo(logInfo.getVersionNo());
			entity.setEnableFlag(logInfo.getEnableFlag());
			entity.setDeleteFlag(logInfo.getDeleteFlag());
			systemLogDao.getCurrentSession().save(entity);
		}catch(Exception e){
			logger.error("添加日志出现异常:" + e.getMessage(), e);
		}
	}

}