package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.FlowConfigEntity;

@Repository
public class FlowConfigDao extends BaseDao {

	public FlowConfigEntity findByCompanyId() throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from FlowConfigEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		return (FlowConfigEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
}
