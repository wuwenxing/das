package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.dao.base.BaseDao;

@Repository
public class EmailTemplateDetailContentDao extends BaseDao {

	/**
	 * 根据条件删除
	 */
	public void deleteByCon(Long templateId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete EmailTemplateDetailContentEntity where contentId in( ");
		hql.append(" select contentId from EmailTemplateDetailEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != templateId) {
			hql.append(" and templateId = ? )");
			paramList.add(templateId);
		}else{
			throw new Exception("删除条件错误");
		}
		super.executeUpdateByHql(hql.toString(), paramList);
	}
}
