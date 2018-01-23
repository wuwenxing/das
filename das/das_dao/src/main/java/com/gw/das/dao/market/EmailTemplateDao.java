package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.EmailTemplateEntity;

@Repository
public class EmailTemplateDao extends BaseDao {

	public EmailTemplateEntity findByCode(String code) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from EmailTemplateEntity where code = ? and companyId = ? ");
		paramList.add(code);
		paramList.add(UserContext.get().getCompanyId());
		return (EmailTemplateEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<EmailTemplateEntity> findList(EmailTemplateEntity emailTemplateEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, emailTemplateEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<EmailTemplateEntity> findPageList(PageGrid<EmailTemplateEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		EmailTemplateEntity emailTemplateEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, emailTemplateEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, EmailTemplateEntity emailTemplateEntity, List<Object> paramList) {
		hql.append("from EmailTemplateEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != emailTemplateEntity) {
			if (StringUtils.isNotBlank(emailTemplateEntity.getName())) {
				hql.append(" and name like ? ");
				paramList.add("%" + emailTemplateEntity.getName() + "%");
			}
			if (StringUtils.isNotBlank(emailTemplateEntity.getCode())) {
				hql.append(" and code like ? ");
				paramList.add("%" + emailTemplateEntity.getCode() + "%");
			}
			if (StringUtils.isNotBlank(emailTemplateEntity.getTitle())) {
				hql.append(" and title like ? ");
				paramList.add("%" + emailTemplateEntity.getTitle() + "%");
			}
		}
		return hql;
	}

	public boolean checkEmailTemplate(String code, Long templateId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from EmailTemplateEntity where code = ? and companyId = ? ");
		paramList.add(code);
		paramList.add(UserContext.get().getCompanyId());
		if (null != templateId && templateId > 0) {
			hql.append(" and templateId != ? ");
			paramList.add(templateId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}

}
