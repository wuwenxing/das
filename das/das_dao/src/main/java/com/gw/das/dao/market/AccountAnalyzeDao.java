package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.SqlUtil;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.AccountAnalyzeEntity;

@Repository
public class AccountAnalyzeDao extends BaseDao {

	public List<AccountAnalyzeEntity> findList(AccountAnalyzeEntity channelEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, channelEntity, paramList);
		hql.append(SqlUtil.getOrderField(channelEntity.getSort(), channelEntity.getOrder()));
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<AccountAnalyzeEntity> findPageList(PageGrid<AccountAnalyzeEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		AccountAnalyzeEntity channelEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, channelEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, AccountAnalyzeEntity channelEntity, List<Object> paramList) {
		hql.append("from AccountAnalyzeEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != channelEntity) {
			if (StringUtils.isNotBlank(channelEntity.getBatchNo())) {
				hql.append(" and batchNo like ? ");
				paramList.add("%" + channelEntity.getBatchNo() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getAccountNo())) {
				hql.append(" and accountNo like ? ");
				paramList.add("%" + channelEntity.getAccountNo() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getEmail())) {
				hql.append(" and email like ? ");
				paramList.add("%" + channelEntity.getEmail() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getGenerateStatus())) {
				hql.append(" and generateStatus = ? ");
				paramList.add(channelEntity.getGenerateStatus());
			}
			if (StringUtils.isNotBlank(channelEntity.getSendStatus())) {
				hql.append(" and sendStatus = ? ");
				paramList.add(channelEntity.getSendStatus());
			}
			if (null != channelEntity.getGenerateTimeStart()) {
				hql.append(" and generateTime >= ? ");
				paramList.add(channelEntity.getGenerateTimeStart());
			}
			if (null != channelEntity.getGenerateTimeEnd()) {
				hql.append(" and generateTime <= ? ");
				paramList.add(channelEntity.getGenerateTimeEnd());
			}
			if (null != channelEntity.getSendTimeStart()) {
				hql.append(" and sendTime >= ? ");
				paramList.add(channelEntity.getSendTimeStart());
			}
			if (null != channelEntity.getSendTimeEnd()) {
				hql.append(" and sendTime <= ? ");
				paramList.add(channelEntity.getSendTimeEnd());
			}
			if (StringUtils.isNotBlank(channelEntity.getStartDate())) {
				hql.append(" and updateDate >= ? ");
				paramList.add(DateUtil.stringToDate(channelEntity.getStartDate(), "yyyy-MM-dd HH:mm:ss"));
			}
			if (StringUtils.isNotBlank(channelEntity.getEndDate())) {
				hql.append(" and updateDate <= ? ");
				paramList.add(DateUtil.stringToDate(channelEntity.getEndDate(), "yyyy-MM-dd HH:mm:ss"));
			}
		}
		return hql;
	}
	
}
