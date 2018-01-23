package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.SmsTemplateEntity;

@Repository
public class SmsTemplateDao extends BaseDao {

	public SmsTemplateEntity findByCode(String code) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SmsTemplateEntity where code = ? and companyId = ? ");
		paramList.add(code);
		paramList.add(UserContext.get().getCompanyId());
		return (SmsTemplateEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<SmsTemplateEntity> findList(SmsTemplateEntity smsTemplateEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, smsTemplateEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SmsTemplateEntity> findPageList(PageGrid<SmsTemplateEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SmsTemplateEntity smsTemplateEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, smsTemplateEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SmsTemplateEntity smsTemplateEntity, List<Object> paramList) {
		hql.append("from SmsTemplateEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsTemplateEntity) {
			if (StringUtils.isNotBlank(smsTemplateEntity.getName())) {
				hql.append(" and name like ? ");
				paramList.add("%" + smsTemplateEntity.getName() + "%");
			}
			if (StringUtils.isNotBlank(smsTemplateEntity.getCode())) {
				hql.append(" and code like ? ");
				paramList.add("%" + smsTemplateEntity.getCode() + "%");
			}
		}
		return hql;
	}

	public boolean checkSmsTemplate(String code, Long templateId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SmsTemplateEntity where code = ? and companyId = ? ");
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
