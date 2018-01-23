package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.EmailDetailEntity;

@Repository
public class EmailDetailDao extends BaseDao {

	public List<EmailDetailEntity> findList(EmailDetailEntity emailDetailEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, emailDetailEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<EmailDetailEntity> findPageList(PageGrid<EmailDetailEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		EmailDetailEntity emailDetailEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, emailDetailEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, EmailDetailEntity emailDetailEntity, List<Object> paramList) {
		hql.append("from EmailDetailEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != emailDetailEntity) {
			if (null != emailDetailEntity.getEmailId()) {
				hql.append(" and emailId = ? ");
				paramList.add(emailDetailEntity.getEmailId());
			}
			if (StringUtils.isNotBlank(emailDetailEntity.getCommitStatus())) {
				hql.append(" and commitStatus = ? ");
				paramList.add(emailDetailEntity.getCommitStatus());
			}
			if (StringUtils.isNotBlank(emailDetailEntity.getSendStatus())) {
				hql.append(" and sendStatus = ? ");
				paramList.add(emailDetailEntity.getSendStatus());
			}
			if (StringUtils.isNotBlank(emailDetailEntity.getRecEmail())) {
				hql.append(" and recEmail like ? ");
				paramList.add("%" + emailDetailEntity.getRecEmail() + "%");
			}
			if(null != emailDetailEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(emailDetailEntity.getStartDate());
			}
			if(null != emailDetailEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(emailDetailEntity.getEndDate());
			}
		}
		return hql;
	}


	/**
	 * 根据条件删除
	 */
	public void deleteByCon(Long emailId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete EmailDetailEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != emailId) {
			hql.append(" and emailId = ? ");
			paramList.add(emailId);
		}else{
			throw new Exception("删除条件错误");
		}
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
}
