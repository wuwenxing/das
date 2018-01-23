package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemRoleEntity;

@Repository
public class SystemRoleDao extends BaseDao {

	public SystemRoleEntity findByRoleCode(String roleCode) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemRoleEntity where roleCode = ? ");
		paramList.add(roleCode);
		return (SystemRoleEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<SystemRoleEntity> findList(SystemRoleEntity roleEntity, String companyIds) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, roleEntity, companyIds, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SystemRoleEntity> findPageList(PageGrid<SystemRoleEntity> pageGrid, String companyIds) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SystemRoleEntity roleEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, roleEntity, companyIds, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}
	
	/**
	 * list与分页查询,公共方法
	 * @param hql
	 * @param entity
	 * @param companyIds 拥有的业务权限集合
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, SystemRoleEntity roleEntity, String companyIds, List<Object> paramList){
		hql.append("from SystemRoleEntity where deleteFlag != 'Y' ");
		if(!Constants.superAdmin.equals(UserContext.get().getLoginNo())){
			// 不为超级管理员，需要过滤角色数据，只能看到自己包含的业务权限数据
			if(StringUtils.isNotBlank(companyIds)){
				String subSql = "select roleId from SystemRoleEntity ";
				String[] companyIdAry = companyIds.split(",");
				for(int i=0; i<companyIdAry.length; i++){
					if(i == 0){
						subSql += " where ( ";
					}else{
						subSql += " or( ";
					}
					String companyId = companyIdAry[i];
					if(StringUtils.isNotBlank(companyId)){
						subSql += " companyIds like ? ";
						paramList.add("%" + companyId + "%");
					}
					subSql += " ) ";
				}
				hql.append(" and roleId in (" + subSql + ")");
			}
		}
		if (null != roleEntity) {
			if (StringUtils.isNotBlank(roleEntity.getRoleCode())) {
				hql.append(" and roleCode like ? ");
				paramList.add("%" + roleEntity.getRoleCode() + "%");
			}
			if (StringUtils.isNotBlank(roleEntity.getRoleName())) {
				hql.append(" and roleName like ? ");
				paramList.add("%" + roleEntity.getRoleName() + "%");
			}
		}
		return hql;
	}
	
	public boolean checkRoleCode(String roleCode, Long roleId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemRoleEntity where roleCode = ? ");
		paramList.add(roleCode);
		if(null != roleId && roleId > 0){
			hql.append(" and roleId != ? ");
			paramList.add(roleId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}
	
}
