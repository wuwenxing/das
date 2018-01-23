package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.UserGroupLogDao;
import com.gw.das.dao.market.entity.UserGroupLogEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.UserGroupLogService;

@Service
public class UserGroupLogServiceImpl extends BaseService implements UserGroupLogService {

	@Autowired
	private UserGroupLogDao userGroupLogDao;

	@Override
	public UserGroupLogEntity findById(Long id) throws Exception {
		return (UserGroupLogEntity) userGroupLogDao.findById(id, UserGroupLogEntity.class);
	}

	@Override
	public void saveOrUpdate(UserGroupLogEntity entity) throws Exception {
		if (null == entity.getLogId()) {
			userGroupLogDao.save(entity);
		} else {
			UserGroupLogEntity oldEntity = findById(entity.getLogId());
			BeanUtils.copyProperties(entity, oldEntity);
			userGroupLogDao.update(oldEntity);
		}
	}

	@Override
	public List<UserGroupLogEntity> findList(UserGroupLogEntity userGroupLogEntity) throws Exception {
		return userGroupLogDao.findList(userGroupLogEntity);
	}

	@Override
	public PageGrid<UserGroupLogEntity> findPageList(PageGrid<UserGroupLogEntity> pageGrid) throws Exception {
		return userGroupLogDao.findPageList(pageGrid);
	}

}