package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.UserGroupLogEntity;

@Repository
public class UserGroupLogDao extends BaseDao {
	
	public List<UserGroupLogEntity> findList(UserGroupLogEntity userGroupLogEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, userGroupLogEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<UserGroupLogEntity> findPageList(PageGrid<UserGroupLogEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		UserGroupLogEntity userGroupLogEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, userGroupLogEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, UserGroupLogEntity userGroupLogEntity, List<Object> paramList) {
		hql.append("from UserGroupLogEntity where deleteFlag != 'Y' ");
		if (null != userGroupLogEntity) {
			if (null != userGroupLogEntity.getGroupId()) {
				hql.append(" and groupId = ? ");
				paramList.add(userGroupLogEntity.getGroupId());
			}
			if (StringUtils.isNotBlank(userGroupLogEntity.getStatusCode())) {
				hql.append(" and statusCode = ? ");
				paramList.add(userGroupLogEntity.getStatusCode());
			}
			if (null != userGroupLogEntity.getCompanyId()) {
				hql.append(" and companyId = ? ");
				paramList.add(userGroupLogEntity.getCompanyId());
			}else{
				hql.append(" and companyId is null ");
			}
		}
		return hql;
	}

}
