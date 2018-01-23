package com.gw.das.service.system;

import java.util.List;
import java.util.Map;

import com.gw.das.common.easyui.TreeBean;
import com.gw.das.dao.system.entity.SystemMenuEntity;

public interface SystemMenuService {

	public SystemMenuEntity findById(Long id) throws Exception;
	public SystemMenuEntity findByMenuCode(String menuCode) throws Exception;
	public List<SystemMenuEntity> findByParentMenuCode(String parentMenuCode) throws Exception;
	public void saveOrUpdate(SystemMenuEntity entity) throws Exception;
	public void deleteByMenuCode(String menuCode) throws Exception;
	public boolean checkMenuCode(String menuCode, Long menuId) throws Exception;
	public List<SystemMenuEntity> findList(boolean hasFun) throws Exception;
	public List<SystemMenuEntity> findListByCompanyId(boolean hasFun, Long companyId) throws Exception;
	/**
	 * 根据条件查询菜单
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @return
	 */
	public List<SystemMenuEntity> findListByUserIdAndCompanyId(boolean hasTopTag, boolean hasMenu, boolean hasFun, Long userId, Long companyId) throws Exception;
	/**
	 * 提取树形菜单
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单
	 * @return
	 */
	public List<TreeBean> getMenuTreeJson(boolean hasTopTag, boolean hasMenu, boolean hasFun, String topTagMenuCode) throws Exception;
	/**
	 * 提取树形菜单,并通过角色id设置checkbox是否勾选
	 * @param roleId
	 * @param hasFun是否存在功能
	 * @return
	 */
	public List<TreeBean> getMenuTreeJson(Long roleId, boolean hasFun) throws Exception;
	
	/**
	 * 通过角色id查询拥有的菜单，并提取树形菜单
	 * @param roleId
	 * @return
	 */
	public List<TreeBean> getMenuTreeJsonByRoleId(Long roleId) throws Exception;
	
	/**
	 * 根据角色查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Map<String, SystemMenuEntity> findMenuMapByRoleId(Long roleId, Long companyId) throws Exception;

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
	public List<SystemMenuEntity> getMenuList(boolean hasTopTag, boolean hasMenu, boolean hasFun, boolean showFlag, Long userId, String topTagMenuCode) throws Exception;
	
	/**
	 * 通过userId提取树形菜单,并设置checkbox是否勾选
	 * @param hasTopTag 是否包含顶部页签
	 * @param hasMenu 是否包含菜单
	 * @param hasFun 是否包含功能
	 * @param showFlag 若没有权限，是否显示在树形结构里
	 * @param userId 选择的用户id
	 * @param loginUserId 登录用户的id
	 * @param topTagMenuCode 查询指定顶部页签节点下的菜单
	 * @return
	 */
	public List<TreeBean> getMenuTreeJsonByUserId(boolean hasTopTag, boolean hasMenu, boolean hasFun, boolean showFlag, Long userId, Long loginUserId, String topTagMenuCode) throws Exception;
	
	/**
	 * 登陆后-根据用户Id查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, SystemMenuEntity> findMenuMapByUserId(Long userId, Long companyId) throws Exception;
	
}
