package com.gw.das.service.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.TreeBean;
import com.gw.das.common.easyui.TreeBeanUtil;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.system.SystemMenuDao;
import com.gw.das.dao.system.SystemMenuRoleDao;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.dao.system.entity.SystemMenuRoleEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.cache.MenuCache;
import com.gw.das.service.system.SystemMenuService;

import net.sf.json.JSONObject;

@Service
public class SystemMenuServiceImpl extends BaseService implements SystemMenuService {

	@Autowired
	private SystemMenuDao systemMenuDao;
	@Autowired
	private SystemMenuRoleDao systemMenuRoleDao;
	
	@Override
	public SystemMenuEntity findById(Long id) throws Exception {
		return (SystemMenuEntity) systemMenuDao.findById(id, SystemMenuEntity.class);
	}

	@Override
	public SystemMenuEntity findByMenuCode(String menuCode) throws Exception {
		return systemMenuDao.findByMenuCode(menuCode);
	}

	@Override
	public List<SystemMenuEntity> findByParentMenuCode(String parentMenuCode) throws Exception{
		return systemMenuDao.findByParentMenuCode(parentMenuCode);
	}

	@Override
	public void saveOrUpdate(SystemMenuEntity entity) throws Exception {
		if (null == entity.getMenuId()) {
			systemMenuDao.save(entity);
		} else {
			SystemMenuEntity oldEntity = findById(entity.getMenuId());
			String oldMenuCode = oldEntity.getMenuCode();
			boolean flag = oldMenuCode.equals(entity.getMenuCode());
			BeanUtils.copyProperties(entity, oldEntity);
			systemMenuDao.update(oldEntity);
			
			if(!flag){
				// code变化,更新子节点父code
				systemMenuDao.updateParentMenuCode(oldMenuCode, entity.getMenuCode());
			}
		}
		// 刷新菜单缓存
		MenuCache.refresh();
	}

	@Override
	public void deleteByMenuCode(String menuCode) throws Exception{
		systemMenuDao.deleteByMenuCode(menuCode);
		// 刷新菜单缓存
		MenuCache.refresh();
	}

	@Override
	public boolean checkMenuCode(String menuCode, Long menuId) throws Exception {
		return systemMenuDao.checkMenuCode(menuCode, menuId);
	}

	@Override
	public List<SystemMenuEntity> findList(boolean hasFun) throws Exception{
		return systemMenuDao.findList(hasFun);
	}

	@Override
	public List<SystemMenuEntity> findListByCompanyId(boolean hasFun, Long companyId) throws Exception{
		return systemMenuDao.findListByCompanyId(hasFun, companyId);
	}
	
	/**
	 * 根据条件查询菜单
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @return
	 */
	@Override
	public List<SystemMenuEntity> findListByUserIdAndCompanyId(boolean hasTopTag, boolean hasMenu, boolean hasFun, Long userId, Long companyId) throws Exception{
		return systemMenuDao.findListByUserIdAndCompanyId(hasTopTag, hasMenu, hasFun, userId, companyId);
	}
	
	/**
	 * 提取树形菜单
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单,为空不过滤
	 * @return
	 */
	@Override
	public List<TreeBean> getMenuTreeJson(boolean hasTopTag, boolean hasMenu, boolean hasFun, String topTagMenuCode) throws Exception{
		// 1、查询所有菜单集合
		List<SystemMenuEntity> menuList = MenuCache.getMenuObjList();
		Map<String, String> menuCodeMap = new HashMap<String, String>();
		for(SystemMenuEntity menu : menuList){
			menuCodeMap.put(menu.getMenuCode(), menu.getParentMenuCode());
		}
		// 2、转MenuBean对象集合
		List<TreeBean> menuBeanList = new ArrayList<TreeBean>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuEntity row : menuList) {
				if(!hasTopTag && "0".equals(row.getMenuType())){
					// 如果不包含顶部页签，则跳过此次循环
					continue;
				}
				if(!hasMenu && "1".equals(row.getMenuType())){
					// 如果不包含菜单，则跳过此次循环
					continue;
				}
				if(!hasFun && "2".equals(row.getMenuType())){
					// 如果不包含功能，则跳过此次循环
					continue;
				}
				TreeBean menuBean = new TreeBean();
				menuBean.setText(row.getMenuName());
				menuBean.setId(row.getMenuCode());
				menuBean.setParentId(row.getParentMenuCode());
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("type", row.getMenuType());
				jsonObj.put("code", row.getMenuCode());
				jsonObj.put("url", row.getMenuUrl());
				menuBean.setAttributes(jsonObj);

				// 查询该节点的顶层节点，并判断是否是指定的topTagMenuCode
				if(StringUtils.isNotBlank(topTagMenuCode)){
					String parentCode = row.getMenuCode();
					while(StringUtils.isNotBlank(parentCode)){
						String temp = menuCodeMap.get(parentCode);
						if(StringUtils.isNotBlank(temp)){
							parentCode = temp;
						}else{
							if(topTagMenuCode.equals(parentCode)){
								menuBeanList.add(menuBean);
							}
							break;
						}
					}
				}else{
					menuBeanList.add(menuBean);
				}
				
			}
		}
		return TreeBeanUtil.formatTreeBean(menuBeanList, false);
	}
	
	/**
	 * 提取树形菜单,并通过角色id设置checkbox是否勾选
	 * @param roleId
	 * @param hasFun是否存在功能
	 * @return
	 */
	@Override
	public List<TreeBean> getMenuTreeJson(Long roleId, boolean hasFun) throws Exception{
		// 1、根据roleId，查询拥有的menuId集合
		List<SystemMenuRoleEntity> menuRolelist = systemMenuRoleDao.findListByRoleId(roleId);
		List<String> menuIdList = new ArrayList<String>();
		for(SystemMenuRoleEntity menuRole : menuRolelist){
			menuIdList.add(menuRole.getMenuId()+"");
		}
		// 2、查询所有菜单集合
		List<SystemMenuEntity> menuList = systemMenuDao.findList(hasFun);
		// 3、转MenuBean对象集合，如果在menuIdList里，设置勾选复选框
		List<TreeBean> menuBeanList = new ArrayList<TreeBean>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuEntity row : menuList) {
				TreeBean menuBean = new TreeBean();
				menuBean.setText(row.getMenuName());
				menuBean.setId(row.getMenuCode());
				menuBean.setParentId(row.getParentMenuCode());
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("type", row.getMenuType());
				jsonObj.put("code", row.getMenuCode());
				jsonObj.put("url", row.getMenuUrl());
				menuBean.setAttributes(jsonObj);
				if(menuIdList.contains(row.getMenuId()+"")){
					menuBean.setChecked(true);
				}
				menuBeanList.add(menuBean);
			}
		}
		return TreeBeanUtil.formatTreeBean(menuBeanList, false);
	}

	/**
	 * 通过角色id查询拥有的菜单，并提取树形菜单
	 * @param roleId
	 * @return
	 */
	@Override
	public List<TreeBean> getMenuTreeJsonByRoleId(Long roleId) throws Exception{
		// 1、根据roleId，查询拥有的menuId集合
		List<SystemMenuRoleEntity> menuRolelist = systemMenuRoleDao.findListByRoleId(roleId);
		List<String> menuIdList = new ArrayList<String>();
		for(SystemMenuRoleEntity menuRole : menuRolelist){
			menuIdList.add(menuRole.getMenuId()+"");
		}
		// 2、查询所有菜单集合
		List<SystemMenuEntity> menuList = systemMenuDao.findList(false);
		// 3、转MenuBean对象集合，如果在menuIdList里，设置勾选复选框
		List<TreeBean> menuBeanList = new ArrayList<TreeBean>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuEntity row : menuList) {
				if("Y".equals(row.getEnableFlag())){
					TreeBean menuBean = new TreeBean();
					menuBean.setText(row.getMenuName());
					menuBean.setId(row.getMenuCode());
					menuBean.setParentId(row.getParentMenuCode());
					JSONObject jsonObj = new JSONObject();
					jsonObj.put("type", row.getMenuType());
					jsonObj.put("code", row.getMenuCode());
					jsonObj.put("url", row.getMenuUrl());
					menuBean.setAttributes(jsonObj);
					if(menuIdList.contains(row.getMenuId()+"")){
						menuBean.setChecked(true);
						menuBeanList.add(menuBean);
					}
				}
			}
		}
		return TreeBeanUtil.formatTreeBean(menuBeanList, false);
	}
	
	/**
	 * 根据角色查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Map<String, SystemMenuEntity> findMenuMapByRoleId(Long roleId, Long companyId) throws Exception{
		return systemMenuDao.findMenuMapByRoleId(roleId, companyId);
	}
	
	/**
	 * 登陆后-通过userId提取菜单列表
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param showFlag 若没有权限，是否显示在菜单列表
	 * @param userId
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单,为空不过滤
	 * @return
	 */
	public List<SystemMenuEntity> getMenuList(boolean hasTopTag, boolean hasMenu, boolean hasFun, boolean showFlag, Long userId, String topTagMenuCode) throws Exception{
		// 1、根据userId，查询拥有的menuId集合
		List<SystemMenuEntity> menuUserList = MenuCache.getMenuObjListByUser2(userId+"");
		Map<String, String> menuCodeMap = new HashMap<String, String>();
		for(SystemMenuEntity menu : menuUserList){
			menuCodeMap.put(menu.getMenuCode(), menu.getParentMenuCode());
		}
		// 2、查询所有菜单集合
		List<SystemMenuEntity> menuList = MenuCache.getMenuObjList();
		// 3、转MenuBean对象集合，如果在menuIdList里，设置勾选复选框
		List<SystemMenuEntity> returnList = new ArrayList<SystemMenuEntity>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuEntity row : menuList) {
				if(!hasTopTag && "0".equals(row.getMenuType())){
					// 如果不包含顶部页签，则跳过此次循环
					continue;
				}
				if(!hasMenu && "1".equals(row.getMenuType())){
					// 如果不包含菜单，则跳过此次循环
					continue;
				}
				if(!hasFun && "2".equals(row.getMenuType())){
					// 如果不包含功能，则跳过此次循环
					continue;
				}
				// 若没有权限，是否显示在菜单列表
				if(!showFlag){
					if(null == menuCodeMap.get(row.getMenuCode())){
						// 如果没有权限，则跳过此次循环
						continue;
					}
				}
				
				// 查询该节点的顶层节点，并判断是否是指定的topTagMenuCode
				if(StringUtils.isNotBlank(topTagMenuCode)){
					String parentCode = row.getMenuCode();
					while(StringUtils.isNotBlank(parentCode)){
						String temp = menuCodeMap.get(parentCode);
						if(StringUtils.isNotBlank(temp)){
							parentCode = temp;
						}else{
							if(topTagMenuCode.equals(parentCode)){
								returnList.add(row);
							}
							break;
						}
					}
				}else{
					returnList.add(row);
				}
			}
		}
		return returnList;
	}
	
	/**
	 * 通过userId提取树形菜单,并设置checkbox是否勾选
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param showFlag 若没有权限，是否显示在树形结构里
	 * @param userId 选择的用户id
	 * @param loginUserId 登录用户的id
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单,为空不过滤
	 * @return
	 */
	@Override
	public List<TreeBean> getMenuTreeJsonByUserId(boolean hasTopTag, boolean hasMenu, boolean hasFun, boolean showFlag, Long userId, Long loginUserId, String topTagMenuCode) throws Exception{
		// 1、根据userId，查询拥有的menuId集合
		List<SystemMenuEntity> menuUserList = MenuCache.getMenuObjListByUser2(userId+"");
		Map<String, String> menuCodeMap = new HashMap<String, String>();
		for(SystemMenuEntity menu : menuUserList){
			menuCodeMap.put(menu.getMenuCode(), menu.getParentMenuCode());
		}
		
		// 2、查询菜单集合
		List<SystemMenuEntity> menuList = null;
		if(Constants.superAdmin.equals(UserContext.get().getLoginNo())){
			// 查询所有菜单集合
			menuList = MenuCache.getMenuObjList();
		}else{
			// 当前登录用户拥有的菜单集合
			menuList = MenuCache.getMenuObjListByUser2(loginUserId+"");
		}
		
		// 3、转MenuBean对象集合，如果在menuIdList里，设置勾选复选框
		List<TreeBean> menuBeanList = new ArrayList<TreeBean>();
		if (menuList != null && menuList.size() > 0) {
			for (SystemMenuEntity row : menuList) {
				if(!"Y".equals(row.getEnableFlag())){
					// 如果被禁用，则跳过此次循环
					continue;
				}
				if(!hasTopTag && "0".equals(row.getMenuType())){
					// 如果不包含顶部页签，则跳过此次循环
					continue;
				}
				if(!hasMenu && "1".equals(row.getMenuType())){
					// 如果不包含菜单，则跳过此次循环
					continue;
				}
				if(!hasFun && "2".equals(row.getMenuType())){
					// 如果不包含功能，则跳过此次循环
					continue;
				}
				// 若没有权限，是否显示在树形结构里
				if(!showFlag){
					if(null == menuCodeMap.get(row.getMenuCode())){
						// 如果没有权限，则跳过此次循环
						continue;
					}
				}
				
				TreeBean menuBean = new TreeBean();
				menuBean.setText(row.getMenuName());
				menuBean.setId(row.getMenuCode());
				menuBean.setParentId(row.getParentMenuCode());
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("type", row.getMenuType());
				jsonObj.put("code", row.getMenuCode());
				jsonObj.put("url", row.getMenuUrl());
				menuBean.setAttributes(jsonObj);
				if(null != menuCodeMap.get(row.getMenuCode())){
					menuBean.setChecked(true);
				}
				
				// 查询该节点的顶层节点，并判断是否是指定的topTagMenuCode
				if(StringUtils.isNotBlank(topTagMenuCode)){
					String parentCode = row.getMenuCode();
					while(StringUtils.isNotBlank(parentCode)){
						String temp = menuCodeMap.get(parentCode);
						if(StringUtils.isNotBlank(temp)){
							parentCode = temp;
						}else{
							if(topTagMenuCode.equals(parentCode)){
								menuBeanList.add(menuBean);
							}
							break;
						}
					}
				}else{
					menuBeanList.add(menuBean);
				}
				
			}
		}
		return TreeBeanUtil.formatTreeBean(menuBeanList, false);
	}

	/**
	 * 根据用户Id查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, SystemMenuEntity> findMenuMapByUserId(Long userId, Long companyId) throws Exception{
		Map<String, SystemMenuEntity> map = new HashMap<String, SystemMenuEntity>();
		List<SystemMenuEntity> menuList = MenuCache.getMenuObjListByUser2(userId+"", companyId+"");
		for(SystemMenuEntity menu: menuList){
			map.put(menu.getMenuUrl(), menu);
		}
		return map;
	}
	
}