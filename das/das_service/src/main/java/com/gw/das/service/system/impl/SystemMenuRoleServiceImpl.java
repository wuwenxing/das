package com.gw.das.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.dao.base.BaseEntity;
import com.gw.das.dao.system.SystemMenuDao;
import com.gw.das.dao.system.SystemMenuRoleDao;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.dao.system.entity.SystemMenuRoleEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.system.SystemMenuRoleService;

@Service
public class SystemMenuRoleServiceImpl extends BaseService implements SystemMenuRoleService {

	@Autowired
	private SystemMenuDao systemMenuDao;
	@Autowired
	private SystemMenuRoleDao systemMenuRoleDao;

	/**
	 * 根据条件，更新roleId与menuIds关联信息
	 * @param roleId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateRoleIdMenuIds(Long roleId, String menuCodes) throws Exception {
		// 先删除现有关联
		systemMenuRoleDao.deleteByRoleId(roleId);
		// 插入关联
		List<BaseEntity> menuRoleList = new ArrayList<BaseEntity>();
		List<SystemMenuEntity> menuList = systemMenuDao.findByMenuCodes(menuCodes);
		for(SystemMenuEntity menu : menuList){
			SystemMenuRoleEntity menuRole = new SystemMenuRoleEntity();
			menuRole.setMenuId(menu.getMenuId());
			menuRole.setRoleId(roleId);
			menuRoleList.add(menuRole);
		}
		systemMenuRoleDao.saveAll(menuRoleList);
	}
	
	
}