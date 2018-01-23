package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.EmailTemplateDetailEntity;

@Repository
public class EmailTemplateDetailDao extends BaseDao {
	
	public EmailTemplateDetailEntity findOne(Long detailId, String account, Long companyId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from EmailTemplateDetailEntity where detailId = ? and account = ? and companyId = ? ");
		paramList.add(detailId);
		paramList.add(account);
		paramList.add(companyId);
		return (EmailTemplateDetailEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<EmailTemplateDetailEntity> findList(EmailTemplateDetailEntity emailTemplateDetailEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, emailTemplateDetailEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<EmailTemplateDetailEntity> findPageList(PageGrid<EmailTemplateDetailEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		EmailTemplateDetailEntity emailTemplateDetailEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, emailTemplateDetailEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, EmailTemplateDetailEntity emailTemplateDetailEntity, List<Object> paramList) {
		hql.append("from EmailTemplateDetailEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != emailTemplateDetailEntity) {
			if (null != emailTemplateDetailEntity.getTemplateId()) {
				hql.append(" and templateId = ? ");
				paramList.add(emailTemplateDetailEntity.getTemplateId());
			}
			if (StringUtils.isNotBlank(emailTemplateDetailEntity.getCommitStatus())) {
				hql.append(" and commitStatus = ? ");
				paramList.add(emailTemplateDetailEntity.getCommitStatus());
			}
			if (StringUtils.isNotBlank(emailTemplateDetailEntity.getSendStatus())) {
				hql.append(" and sendStatus = ? ");
				paramList.add(emailTemplateDetailEntity.getSendStatus());
			}
			if (StringUtils.isNotBlank(emailTemplateDetailEntity.getRecEmail())) {
				hql.append(" and recEmail like ? ");
				paramList.add("%" + emailTemplateDetailEntity.getRecEmail() + "%");
			}
			if (StringUtils.isNotBlank(emailTemplateDetailEntity.getTitle())) {
				hql.append(" and title like ? ");
				paramList.add("%" + emailTemplateDetailEntity.getTitle() + "%");
			}
			if(null != emailTemplateDetailEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(emailTemplateDetailEntity.getStartDate());
			}
			if(null != emailTemplateDetailEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(emailTemplateDetailEntity.getEndDate());
			}
		}
		return hql;
	}

	/**
	 * 根据条件删除
	 */
	public void deleteByCon(Long templateId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete EmailTemplateDetailEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != templateId) {
			hql.append(" and templateId = ? ");
			paramList.add(templateId);
		}else{
			throw new Exception("删除条件错误");
		}
		super.executeUpdateByHql(hql.toString(), paramList);
	}

}
