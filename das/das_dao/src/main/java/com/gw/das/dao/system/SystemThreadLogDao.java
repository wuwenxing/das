package com.gw.das.dao.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.system.entity.SystemThreadLogEntity;

@Repository
public class SystemThreadLogDao extends BaseDao {

	/**
	 * 根据code，查询最后线程执行的日志
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SystemThreadLogEntity findLastRecordByCode(String code) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SystemThreadLogEntity where code = ? order by startExecuteTime desc ");
		paramList.add(code);
		return (SystemThreadLogEntity) super.findEntityByHql(hql.toString(), paramList);
	}

	public List<SystemThreadLogEntity> findList(SystemThreadLogEntity systemThreadLogEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, systemThreadLogEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SystemThreadLogEntity> findPageList(PageGrid<SystemThreadLogEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SystemThreadLogEntity systemThreadLogEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, systemThreadLogEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SystemThreadLogEntity systemThreadLogEntity, List<Object> paramList) {
		hql.append("from SystemThreadLogEntity where deleteFlag != 'Y' ");
		if (null != systemThreadLogEntity) {
			if (StringUtils.isNotBlank(systemThreadLogEntity.getCode())) {
				hql.append(" and code = ? ");
				paramList.add(systemThreadLogEntity.getCode());
			}
			if (StringUtils.isNotBlank(systemThreadLogEntity.getStatus())) {
				hql.append(" and status = ? ");
				paramList.add(systemThreadLogEntity.getStatus());
			}
			if(null != systemThreadLogEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(systemThreadLogEntity.getStartDate());
			}
			if(null != systemThreadLogEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(systemThreadLogEntity.getEndDate());
			}
		}
		return hql;
	}

}
