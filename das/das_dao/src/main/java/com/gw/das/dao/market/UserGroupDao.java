package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.UserGroupEntity;

@Repository
public class UserGroupDao extends BaseDao {

	public UserGroupEntity findByCode(String code) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from UserGroupEntity where code = ? and companyId = ? ");
		paramList.add(code);
		paramList.add(UserContext.get().getCompanyId());
		return (UserGroupEntity) super.findEntityByHql(hql.toString(), paramList);
	}

	public UserGroupEntity findByCode(String code, Long companyId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from UserGroupEntity where code = ? and companyId = ? ");
		paramList.add(code);
		paramList.add(companyId);
		return (UserGroupEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<UserGroupEntity> findList(UserGroupEntity userGroupEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, userGroupEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<UserGroupEntity> findPageList(PageGrid<UserGroupEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		UserGroupEntity userGroupEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, userGroupEntity, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}

	/**
	 * list与分页查询,公共方法
	 * 
	 * @param hql
	 * @param entity
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, UserGroupEntity userGroupEntity, List<Object> paramList) {
		hql.append("from UserGroupEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != userGroupEntity) {
			if (StringUtils.isNotBlank(userGroupEntity.getName())) {
				hql.append(" and name like ? ");
				paramList.add("%" + userGroupEntity.getName() + "%");
			}
			if (StringUtils.isNotBlank(userGroupEntity.getCode())) {
				hql.append(" and code like ? ");
				paramList.add("%" + userGroupEntity.getCode() + "%");
			}
		}
		return hql;
	}

	public boolean checkUserGroup(String code, Long groupId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from UserGroupEntity where code = ? and companyId = ? ");
		paramList.add(code);
		paramList.add(UserContext.get().getCompanyId());
		if (null != groupId && groupId > 0) {
			hql.append(" and groupId != ? ");
			paramList.add(groupId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}

}
