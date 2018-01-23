package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.FlowLogEntity;

@Repository
public class FlowLogDao extends BaseDao {
	
	public List<FlowLogEntity> findList(FlowLogEntity flowLogEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, flowLogEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<FlowLogEntity> findPageList(PageGrid<FlowLogEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		FlowLogEntity flowLogEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, flowLogEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, FlowLogEntity flowLogEntity, List<Object> paramList) {
		hql.append("from FlowLogEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != flowLogEntity) {
			if (StringUtils.isNotBlank(flowLogEntity.getStatusCode())) {
				hql.append(" and statusCode = ? ");
				paramList.add(flowLogEntity.getStatusCode());
			}
			if(null != flowLogEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(flowLogEntity.getStartDate());
			}
			if(null != flowLogEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(flowLogEntity.getEndDate());
			}
		}
		return hql;
	}

}
