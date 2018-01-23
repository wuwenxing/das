package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.BlacklistEntity;

@Repository
public class BlacklistDao extends BaseDao {

	public BlacklistEntity findByAccount(String account) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from BlacklistEntity where account = ? and companyId = ? ");
		paramList.add(account);
		paramList.add(UserContext.get().getCompanyId());
		return (BlacklistEntity) super.findEntityByHql(hql.toString(), paramList);
	}

	public List<BlacklistEntity> findList(BlacklistEntity blacklistEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, blacklistEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<BlacklistEntity> findPageList(PageGrid<BlacklistEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		BlacklistEntity blacklistEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, blacklistEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, BlacklistEntity blacklistEntity, List<Object> paramList) {
		hql.append("from BlacklistEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != blacklistEntity) {
			if (StringUtils.isNotBlank(blacklistEntity.getAccount())) {
				hql.append(" and account like ? ");
				paramList.add("%" + blacklistEntity.getAccount() + "%");
			}
			if (StringUtils.isNotBlank(blacklistEntity.getAccountType())) {
				hql.append(" and accountType = ? ");
				paramList.add(blacklistEntity.getAccountType());
			}
			if (StringUtils.isNotBlank(blacklistEntity.getBlacklistType())) {
				hql.append(" and blacklistType = ? ");
				paramList.add(blacklistEntity.getBlacklistType());
			}
		}
		return hql;
	}

	public boolean checkAccount(String account, Long blacklistId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from BlacklistEntity where account = ? and companyId = ? ");
		paramList.add(account);
		paramList.add(UserContext.get().getCompanyId());
		if (null != blacklistId && blacklistId > 0) {
			hql.append(" and blacklistId != ? ");
			paramList.add(blacklistId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}

}
