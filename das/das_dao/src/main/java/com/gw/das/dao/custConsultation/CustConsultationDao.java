package com.gw.das.dao.custConsultation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.custConsultation.entity.CustConsultationEntity;

@Repository
public class CustConsultationDao extends BaseDao {

	public List<CustConsultationEntity> findList(CustConsultationEntity custConsultationEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, custConsultationEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<CustConsultationEntity> findPageList(PageGrid<CustConsultationEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		CustConsultationEntity custConsultationEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, custConsultationEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, CustConsultationEntity custConsultationEntity, List<Object> paramList) {
		hql.append("from CustConsultationEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		String reportType = custConsultationEntity.getReportType();
		String consulttationTime = custConsultationEntity.getConsulttationTime();
		if(StringUtils.isNotBlank(reportType)){
			hql.append(" and reportType = ? ");
			paramList.add(custConsultationEntity.getReportType());
		}
		if(StringUtils.isNotBlank(consulttationTime)){
			hql.append(" and consulttationTime >= ? ");
			paramList.add(custConsultationEntity.getConsulttationTime());
		}
		return hql;
	}
	
	public List<CustConsultationEntity> findByBusinessplatform(String reportType) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from CustConsultationEntity where companyId = ? and reportType = ? ");
		paramList.add(UserContext.get().getCompanyId());
		paramList.add(reportType);
		return  super.findListByHql(hql.toString(), paramList);
	}
	
	public CustConsultationEntity findCustConsultation(CustConsultationEntity custConsultationEntity) throws Exception {
		StringBuffer hql = new StringBuffer("");
		List<Object> paramList = new ArrayList<Object>();
		hql.append("from CustConsultationEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		String reportType = custConsultationEntity.getReportType();
		String consulttationTime = custConsultationEntity.getConsulttationTime();
		if(StringUtils.isNotBlank(reportType)){
			hql.append(" and reportType = ? ");
			paramList.add(custConsultationEntity.getReportType());
		}
		if(StringUtils.isNotBlank(consulttationTime)){
			hql.append(" and consulttationTime = ? ");
			paramList.add(custConsultationEntity.getConsulttationTime());
		}		
		
		return  (CustConsultationEntity)super.findEntityByHql(hql.toString(), paramList);
	}

}
