package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.EmailTemplateLogEntity;

@Repository
public class EmailTemplateLogDao extends BaseDao {
	
	public List<EmailTemplateLogEntity> findList(EmailTemplateLogEntity emailTemplateLogEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, emailTemplateLogEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<EmailTemplateLogEntity> findPageList(PageGrid<EmailTemplateLogEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		EmailTemplateLogEntity emailTemplateLogEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, emailTemplateLogEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, EmailTemplateLogEntity emailTemplateLogEntity, List<Object> paramList) {
		hql.append("from EmailTemplateLogEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != emailTemplateLogEntity) {
			if (null != emailTemplateLogEntity.getTemplateId()) {
				hql.append(" and templateId = ? ");
				paramList.add(emailTemplateLogEntity.getTemplateId());
			}
			if (StringUtils.isNotBlank(emailTemplateLogEntity.getStatusCode())) {
				hql.append(" and statusCode = ? ");
				paramList.add(emailTemplateLogEntity.getStatusCode());
			}
			if(null != emailTemplateLogEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(emailTemplateLogEntity.getStartDate());
			}
			if(null != emailTemplateLogEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(emailTemplateLogEntity.getEndDate());
			}
			if (null != emailTemplateLogEntity.getCompanyId()) {
				hql.append(" and companyId = ? ");
				paramList.add(emailTemplateLogEntity.getCompanyId());
			}else{
				hql.append(" and companyId is null ");
			}
		}
		return hql;
	}

}
