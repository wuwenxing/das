package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.utils.StringUtil;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemMenuEntity;

@Repository
public class SystemMenuDao extends BaseDao {
	
	public SystemMenuEntity findByMenuCode(String menuCode) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where menuCode = ? and companyId = ? ");
		paramList.add(menuCode);
		paramList.add(UserContext.get().getCompanyId());
		return (SystemMenuEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<SystemMenuEntity> findByParentMenuCode(String parentMenuCode) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where parentMenuCode = ? and companyId = ? ");
		paramList.add(parentMenuCode);
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<SystemMenuEntity> findByMenuCodes(String menuCodes) throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where menuCode in (:menuCodes) and companyId =:companyId ");
		paramMap.put("menuCodes", StringUtil.string2List(menuCodes));
		paramMap.put("companyId", UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramMap);
	}
	
	public boolean checkMenuCode(String menuCode, Long menuId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where menuCode = ? and companyId = ? ");
		paramList.add(menuCode);
		paramList.add(UserContext.get().getCompanyId());
		if(null != menuId && menuId > 0){
			hql.append(" and menuId != ? ");
			paramList.add(menuId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}
	
	public List<SystemMenuEntity> findListByCompanyId(boolean hasFun, Long companyId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where companyId = ? ");
		paramList.add(companyId);
		if(!hasFun){
			hql.append(" and menuType = 1 order by orderCode ");
		}else{
			hql.append(" order by orderCode ");
		}
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<SystemMenuEntity> findListByUserIdAndCompanyId(boolean hasTopTag, boolean hasMenu, boolean hasFun, Long userId, Long companyId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where menuId in("
				+ "select menuId from SystemMenuUserEntity where userId=? and companyId = ?"
				+ ") ");
		paramList.add(userId);
		paramList.add(companyId);
		if(!hasTopTag){
			hql.append(" and menuType != 0 ");
		}
		if(!hasMenu){
			hql.append(" and menuType != 1 ");
		}
		if(!hasFun){
			hql.append(" and menuType != 2 ");
		}
		hql.append(" order by orderCode ");
		return super.findListByHql(hql.toString(), paramList);
	}
	
	/**
	 * @param hasFun 是否包含功能
	 * @return
	 * @throws Exception
	 */
	public List<SystemMenuEntity> findList(boolean hasFun) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if(!hasFun){
			hql.append(" and menuType = 1 order by orderCode ");
		}else{
			hql.append(" order by orderCode ");
		}
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<SystemMenuEntity> findListByMenuIdList(List<Long> menuIdList) throws Exception{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where deleteFlag != 'Y' and menuId in (:menuIds) and companyId =:companyId ");
		paramMap.put("menuIds", menuIdList);
		paramMap.put("companyId", UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramMap);
	}
	
	public void updateParentMenuCode(String oldMenuCode, String menuCode) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("update SystemMenuEntity set parentMenuCode = ? where parentMenuCode = ? and companyId = ? ");
		paramList.add(menuCode);
		paramList.add(oldMenuCode);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	public void deleteByMenuCode(String menuCode) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SystemMenuEntity where menuCode = ? and companyId = ? ");
		paramList.add(menuCode);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	/**
	 * 根据角色查询拥有的菜单权限-return Map<menuUrl, entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Map<String, SystemMenuEntity> findMenuMapByRoleId(Long roleId, Long companyId) throws Exception {
		Map<String, SystemMenuEntity> map = new HashMap<String, SystemMenuEntity>();
		List<SystemMenuEntity> menuList = this.findMenuListByRoleId(roleId, companyId);
		for(SystemMenuEntity menu: menuList){
			if(StringUtils.isNotBlank(menu.getMenuUrl())){
				map.put(menu.getMenuUrl(), menu);
			}
		}
		return map;
	}
	
	/**
	 * 根据角色查询拥有的菜单权限-return List<entity>
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	private List<SystemMenuEntity> findMenuListByRoleId(Long roleId, Long companyId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuEntity where menuId in ("
				+ "select menuId from SystemMenuRoleEntity where deleteFlag != 'Y' and roleId = ? "
				+ ") and companyId = ? and deleteFlag != 'Y' and enableFlag = 'Y' ");
		paramList.add(roleId);
		paramList.add(companyId);
		return super.findListByHql(hql.toString(), paramList);
	}
	
}
