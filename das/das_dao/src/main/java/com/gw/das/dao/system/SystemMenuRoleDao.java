package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemMenuRoleEntity;

@Repository
public class SystemMenuRoleDao extends BaseDao {

	public List<SystemMenuRoleEntity> findListByMenuId(Long menuId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuRoleEntity where deleteFlag != 'Y' and menuId = ? and companyId = ? ");
		paramList.add(menuId);
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<SystemMenuRoleEntity> findListByRoleId(Long roleId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuRoleEntity where deleteFlag != 'Y' and roleId = ? and companyId = ? ");
		paramList.add(roleId);
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public void deleteByRoleId(Long roleId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SystemMenuRoleEntity where roleId = ? and companyId = ? ");
		paramList.add(roleId);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	public void deleteByMenuId(Long menuId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SystemMenuRoleEntity where menuId = ? and companyId = ? ");
		paramList.add(menuId);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
}
