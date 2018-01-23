package com.gw.das.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.dao.base.BaseEntity;
import com.gw.das.dao.system.SystemMenuDao;
import com.gw.das.dao.system.SystemMenuUserDao;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.dao.system.entity.SystemMenuUserEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.system.SystemMenuUserService;

@Service
public class SystemMenuUserServiceImpl extends BaseService implements SystemMenuUserService {

	@Autowired
	private SystemMenuDao systemMenuDao;
	@Autowired
	private SystemMenuUserDao systemMenuUserDao;

	/**
	 * 根据条件，更新userId与menuIds关联信息
	 * @param userId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateUserIdMenuIds(Long userId, String menuCodes) throws Exception {
		// 先删除现有关联
		systemMenuUserDao.deleteByUserId(userId);
		// 插入关联
		List<BaseEntity> menuUserList = new ArrayList<BaseEntity>();
		List<SystemMenuEntity> menuList = systemMenuDao.findByMenuCodes(menuCodes);
		for(SystemMenuEntity menu : menuList){
			SystemMenuUserEntity menuUser = new SystemMenuUserEntity();
			menuUser.setMenuId(menu.getMenuId());
			menuUser.setUserId(userId);
			menuUserList.add(menuUser);
		}
		systemMenuUserDao.saveAll(menuUserList);
	}
	
}