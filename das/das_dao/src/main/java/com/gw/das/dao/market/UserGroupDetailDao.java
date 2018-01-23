package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.UserGroupDetailEntity;

@Repository
public class UserGroupDetailDao extends BaseDao {
	
	public UserGroupDetailEntity findOne(Long groupId, String account, Long companyId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from UserGroupDetailEntity where groupId = ? and account = ? and companyId = ? ");
		paramList.add(groupId);
		paramList.add(account);
		paramList.add(companyId);
		return (UserGroupDetailEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<UserGroupDetailEntity> findList(UserGroupDetailEntity userGroupDetailEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, userGroupDetailEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<UserGroupDetailEntity> findPageList(PageGrid<UserGroupDetailEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		UserGroupDetailEntity userGroupDetailEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, userGroupDetailEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, UserGroupDetailEntity userGroupDetailEntity, List<Object> paramList) {
		hql.append("from UserGroupDetailEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != userGroupDetailEntity) {
			if (null != userGroupDetailEntity.getGroupId()) {
				hql.append(" and groupId = ? ");
				paramList.add(userGroupDetailEntity.getGroupId());
			}
			if (StringUtils.isNotBlank(userGroupDetailEntity.getAccount())) {
				hql.append(" and account like ? ");
				paramList.add("%" + userGroupDetailEntity.getAccount() + "%");
			}
			if (StringUtils.isNotBlank(userGroupDetailEntity.getAccountType())) {
				hql.append(" and accountType = ? ");
				paramList.add(userGroupDetailEntity.getAccountType());
			}
		}
		return hql;
	}

}
