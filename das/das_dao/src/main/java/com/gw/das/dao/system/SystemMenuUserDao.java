package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemMenuUserEntity;

@Repository
public class SystemMenuUserDao extends BaseDao {

	public List<SystemMenuUserEntity> findListByMenuId(Long menuId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuUserEntity where deleteFlag != 'Y' and menuId = ? and companyId = ? ");
		paramList.add(menuId);
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<SystemMenuUserEntity> findListByUserId(Long userId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemMenuUserEntity where deleteFlag != 'Y' and userId = ? and companyId = ? ");
		paramList.add(userId);
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public void deleteByUserId(Long userId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SystemMenuUserEntity where userId = ? and companyId = ? ");
		paramList.add(userId);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	public void deleteByMenuId(Long menuId) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SystemMenuUserEntity where menuId = ? and companyId = ? ");
		paramList.add(menuId);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
}
