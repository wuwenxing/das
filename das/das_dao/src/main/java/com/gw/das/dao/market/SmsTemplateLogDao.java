package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.SmsTemplateLogEntity;

@Repository
public class SmsTemplateLogDao extends BaseDao {
	
	public List<SmsTemplateLogEntity> findList(SmsTemplateLogEntity smsTemplateLogEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, smsTemplateLogEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SmsTemplateLogEntity> findPageList(PageGrid<SmsTemplateLogEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SmsTemplateLogEntity smsTemplateLogEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, smsTemplateLogEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SmsTemplateLogEntity smsTemplateLogEntity, List<Object> paramList) {
		hql.append("from SmsTemplateLogEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsTemplateLogEntity) {
			if (null != smsTemplateLogEntity.getTemplateId()) {
				hql.append(" and templateId = ? ");
				paramList.add(smsTemplateLogEntity.getTemplateId());
			}
			if (StringUtils.isNotBlank(smsTemplateLogEntity.getStatusCode())) {
				hql.append(" and statusCode = ? ");
				paramList.add(smsTemplateLogEntity.getStatusCode());
			}
			if(null != smsTemplateLogEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(smsTemplateLogEntity.getStartDate());
			}
			if(null != smsTemplateLogEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(smsTemplateLogEntity.getEndDate());
			}
			if (null != smsTemplateLogEntity.getCompanyId()) {
				hql.append(" and companyId = ? ");
				paramList.add(smsTemplateLogEntity.getCompanyId());
			}else{
				hql.append(" and companyId is null ");
			}
		}
		return hql;
	}

}
