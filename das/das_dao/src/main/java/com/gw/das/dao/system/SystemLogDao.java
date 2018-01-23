package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemLogEntity;

@Repository
public class SystemLogDao extends BaseDao {

	public List<SystemLogEntity> findList(SystemLogEntity systemLogEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, systemLogEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SystemLogEntity> findPageList(PageGrid<SystemLogEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SystemLogEntity systemLogEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, systemLogEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SystemLogEntity systemLogEntity, List<Object> paramList) {
		hql.append("from SystemLogEntity where deleteFlag != 'Y' ");
		if (null != systemLogEntity) {
			if (StringUtils.isNotBlank(systemLogEntity.getLogType())) {
				hql.append(" and logType = ? ");
				paramList.add(systemLogEntity.getLogType());
			}
			if (StringUtils.isNotBlank(systemLogEntity.getUrl())) {
				hql.append(" and url like ? ");
				paramList.add("%" + systemLogEntity.getUrl() + "%");
			}
			if (StringUtils.isNotBlank(systemLogEntity.getParam())) {
				hql.append(" and param like ? ");
				paramList.add("%" + systemLogEntity.getParam() + "%");
			}
			if (StringUtils.isNotBlank(systemLogEntity.getRemark())) {
				hql.append(" and remark like ? ");
				paramList.add("%" + systemLogEntity.getRemark() + "%");
			}
			if (StringUtils.isNotBlank(systemLogEntity.getUpdateUser())) {
				hql.append(" and updateUser like ? ");
				paramList.add("%" + systemLogEntity.getUpdateUser() + "%");
			}
			if (StringUtils.isNotBlank(systemLogEntity.getUpdateIp())) {
				hql.append(" and updateIp like ? ");
				paramList.add("%" + systemLogEntity.getUpdateIp() + "%");
			}
			if (null != systemLogEntity.getStartDate()) {
				hql.append(" and updateDate >= ? ");
				paramList.add(systemLogEntity.getStartDate());
			}
			if (null != systemLogEntity.getEndDate()) {
				hql.append(" and updateDate <= ? ");
				paramList.add(systemLogEntity.getEndDate());
			}
			if (null != systemLogEntity.getCompanyId()) {
				hql.append(" and companyId = ? ");
				paramList.add(systemLogEntity.getCompanyId());
			}
		}
		return hql;
	}

}
