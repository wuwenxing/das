package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.EmailEntity;

@Repository
public class EmailDao extends BaseDao {

	public List<EmailEntity> findList(EmailEntity emailEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, emailEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<EmailEntity> findPageList(PageGrid<EmailEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		EmailEntity emailEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, emailEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, EmailEntity emailEntity, List<Object> paramList) {
		hql.append("select new EmailEntity(emailId, title, content, inputNum, legalNum, illegalNum, "
				+ "sourceType, screenIds, groupIds, sendType, timeSwitch, updateDate) from EmailEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != emailEntity) {
			if (StringUtils.isNotBlank(emailEntity.getContent())) {
				hql.append(" and content like ? ");
				paramList.add("%" + emailEntity.getContent() + "%");
			}
			if (StringUtils.isNotBlank(emailEntity.getTitle())) {
				hql.append(" and title like ? ");
				paramList.add("%" + emailEntity.getTitle() + "%");
			}
			if (StringUtils.isNotBlank(emailEntity.getSendType())) {
				hql.append(" and sendType = ? ");
				paramList.add(emailEntity.getSendType());
			}
			if (null != emailEntity.getStartDate()) {
				hql.append(" and updateDate >= ? ");
				paramList.add(emailEntity.getStartDate());
			}
			if (null != emailEntity.getEndDate()) {
				hql.append(" and updateDate <= ? ");
				paramList.add(emailEntity.getEndDate());
			}
		}
		return hql;
	}

}
