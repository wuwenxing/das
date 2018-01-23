package com.gw.das.service.system.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.Constants;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.common.utils.MD5;
import com.gw.das.dao.system.DashboardUserDao;
import com.gw.das.dao.system.SystemUserDao;
import com.gw.das.dao.system.entity.SystemUserEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.system.SystemUserService;

@Service
public class SystemUserServiceImpl extends BaseService implements SystemUserService {

	@Autowired
	private SystemUserDao systemUserDao;
	@Autowired
	private DashboardUserDao dashboardUserDao;
	
	@Override
	public SystemUserEntity findById(Long id) throws Exception {
		return (SystemUserEntity) systemUserDao.findById(id, SystemUserEntity.class);
	}

	@Override
	public SystemUserEntity findByUserNo(String userNo) throws Exception {
		return systemUserDao.findByUserNo(userNo);
	}

	@Override
	public void saveOrUpdate(SystemUserEntity entity) throws Exception {
		if (null == entity.getUserId()) {
			entity.setPassword(MD5.getMd5(Constants.initPassword));
			systemUserDao.save(entity);
			dashboardUserDao.saveOrUpdate(entity);
		} else {
			SystemUserEntity oldEntity = findById(entity.getUserId());
			BeanUtils.copyProperties(entity, oldEntity);
			if(StringUtils.isBlank(entity.getCompanyIds())){
				oldEntity.setCompanyIds("");
			}
			systemUserDao.update(oldEntity);
			dashboardUserDao.saveOrUpdate(oldEntity);
		}
	}

	@Override
	public void updatePassword(Long userId, String password) throws Exception {
		SystemUserEntity entity = findById(userId);
		entity.setPassword(MD5.getMd5(password));
		systemUserDao.update(entity);
		dashboardUserDao.saveOrUpdate(entity);
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		systemUserDao.deleteAllByIdArray(idArray.split(","), SystemUserEntity.class);
		dashboardUserDao.delete(idArray);
	}

	@Override
	public boolean checkUserNo(String userNo, Long userId) throws Exception {
		return systemUserDao.checkUserNo(userNo, userId);
	}

	@Override
	public void resetPassword(Long userId) throws Exception {
		SystemUserEntity entity = findById(userId);
		// 重置为初始密码
		entity.setPassword(MD5.getMd5(Constants.initPassword));
		systemUserDao.update(entity);
		dashboardUserDao.saveOrUpdate(entity);
	}

	@Override
	public List<SystemUserEntity> findList(SystemUserEntity userEntity, String companyIds) throws Exception {
		return systemUserDao.findList(userEntity, companyIds);
	}

	@Override
	public PageGrid<SystemUserEntity> findPageList(PageGrid<SystemUserEntity> pageGrid, String companyIds) throws Exception {
		return systemUserDao.findPageList(pageGrid, companyIds);
	}

	@Override
	public List<SystemUserEntity> findUserListByRoleId(Long roleId) throws Exception {
		return systemUserDao.findUserListByRoleId(roleId);
	}

	@Override
	public void updateRoleIdByUserIdArray(Long roleId, String selectIds, String unSelectIds) throws Exception {
		systemUserDao.updateRoleIdByUserIdArray(roleId, selectIds, unSelectIds);
	}
	
	/**
	 * 返回有权限的菜单集合
	 * Map<key=user_id+","+menu_id, user_no>
	 * @param companyId
	 * @return
	 */
	@Override
	public Map<String, String> findUserMenuMap(){
		return systemUserDao.findUserMenuMap();
	}
}