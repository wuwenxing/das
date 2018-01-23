package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.SmsEntity;

@Repository
public class SmsDao extends BaseDao {

	public List<SmsEntity> findList(SmsEntity smsEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, smsEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SmsEntity> findPageList(PageGrid<SmsEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SmsEntity smsEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, smsEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SmsEntity smsEntity, List<Object> paramList) {
		hql.append("select new SmsEntity(smsId,smsSign,content,inputNum,"
				+ "legalNum,illegalNum,sourceType,screenIds,groupIds,sendType,timeSwitch,updateDate)"
				+ " from SmsEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsEntity) {
			if (StringUtils.isNotBlank(smsEntity.getContent())) {
				hql.append(" and content like ? ");
				paramList.add("%" + smsEntity.getContent() + "%");
			}
			if (StringUtils.isNotBlank(smsEntity.getSendType())) {
				hql.append(" and sendType = ? ");
				paramList.add(smsEntity.getSendType());
			}
			if(null != smsEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(smsEntity.getStartDate());
			}
			if(null != smsEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(smsEntity.getEndDate());
			}
		}
		return hql;
	}
	
}
