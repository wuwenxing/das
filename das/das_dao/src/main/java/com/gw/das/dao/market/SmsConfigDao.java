package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.SmsConfigEntity;

@Repository
public class SmsConfigDao extends BaseDao {

	public SmsConfigEntity findBySign(String sign) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SmsConfigEntity where sign = ? and companyId = ? ");
		paramList.add(sign);
		paramList.add(UserContext.get().getCompanyId());
		return (SmsConfigEntity) super.findEntityByHql(hql.toString(), paramList);
	}
	
	public List<SmsConfigEntity> findList(SmsConfigEntity smsConfigEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, smsConfigEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SmsConfigEntity> findPageList(PageGrid<SmsConfigEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SmsConfigEntity smsConfigEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, smsConfigEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SmsConfigEntity smsConfigEntity, List<Object> paramList) {
		hql.append("from SmsConfigEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsConfigEntity) {
			if (StringUtils.isNotBlank(smsConfigEntity.getSign())) {
				hql.append(" and sign = ? ");
				paramList.add(smsConfigEntity.getSign());
			}
			if (StringUtils.isNotBlank(smsConfigEntity.getSmsChannel())) {
				hql.append(" and smsChannel = ? ");
				paramList.add(smsConfigEntity.getSmsChannel());
			}
		}
		return hql;
	}

	public boolean checkSmsConfig(String sign, String smsChannel, Long smsConfigId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from SmsConfigEntity where sign = ? and smsChannel = ? and companyId = ? ");
		paramList.add(sign);
		paramList.add(smsChannel);
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsConfigId && smsConfigId > 0) {
			hql.append(" and smsConfigId != ? ");
			paramList.add(smsConfigId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}

}
