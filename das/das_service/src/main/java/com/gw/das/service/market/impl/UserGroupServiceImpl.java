package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.UserGroupDao;
import com.gw.das.dao.market.entity.UserGroupEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.UserGroupService;

@Service
public class UserGroupServiceImpl extends BaseService implements UserGroupService {

	@Autowired
	private UserGroupDao userGroupDao;

	@Override
	public UserGroupEntity findById(Long id) throws Exception {
		return (UserGroupEntity) userGroupDao.findById(id, UserGroupEntity.class);
	}

	@Override
	public UserGroupEntity findByCode(String code) throws Exception {
		return userGroupDao.findByCode(code);
	}
	
	public UserGroupEntity findByCode(String code, Long companyId) throws Exception{
		return userGroupDao.findByCode(code, companyId);
	}

	@Override
	public void saveOrUpdate(UserGroupEntity entity) throws Exception {
		if (null == entity.getGroupId()) {
			userGroupDao.save(entity);
		} else {
			UserGroupEntity oldEntity = findById(entity.getGroupId());
			BeanUtils.copyProperties(entity, oldEntity);
			userGroupDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		userGroupDao.deleteAllByIdArray(idArray.split(","), UserGroupEntity.class);
	}

	@Override
	public boolean checkUserGroup(String code, Long groupId) throws Exception {
		return userGroupDao.checkUserGroup(code, groupId);
	}

	@Override
	public List<UserGroupEntity> findList(UserGroupEntity userGroupEntity) throws Exception {
		return userGroupDao.findList(userGroupEntity);
	}

	@Override
	public PageGrid<UserGroupEntity> findPageList(PageGrid<UserGroupEntity> pageGrid) throws Exception {
		return userGroupDao.findPageList(pageGrid);
	}

}