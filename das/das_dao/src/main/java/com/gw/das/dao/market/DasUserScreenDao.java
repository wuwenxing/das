package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.DasUserScreenEntity;

@Repository
public class DasUserScreenDao extends BaseDao {

	public List<DasUserScreenEntity> findList(DasUserScreenEntity dasUserScreenEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, dasUserScreenEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<DasUserScreenEntity> findPageList(PageGrid<DasUserScreenEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		DasUserScreenEntity dasUserScreenEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, dasUserScreenEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, DasUserScreenEntity dasUserScreenEntity,
			List<Object> paramList) {
		hql.append("from DasUserScreenEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != dasUserScreenEntity) {
			if (StringUtils.isNotBlank(dasUserScreenEntity.getBehaviorType())) {
				hql.append(" and behaviorType = ? ");
				paramList.add(dasUserScreenEntity.getBehaviorType());
			}
			if (StringUtils.isNotBlank(dasUserScreenEntity.getScreenName())) {
				hql.append(" and screenName like ? ");
				paramList.add("%" + dasUserScreenEntity.getScreenName() + "%");
			}
		}
		return hql;
	}

}
