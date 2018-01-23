package com.gw.das.dao.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.common.utils.SqlUtil;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.trade.entity.RiskBlacklistEntity;

@Repository
public class RiskBlacklistDao extends BaseDao {

	public List<RiskBlacklistEntity> findList(RiskBlacklistEntity entity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, entity, paramList);
		hql.append(SqlUtil.getOrderField(entity.getSort(), entity.getOrder()));
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<RiskBlacklistEntity> findPageList(PageGrid<RiskBlacklistEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		RiskBlacklistEntity entity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, entity, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}

	/**
	 * list与分页查询,公共方法 该业务为集团业务，不过滤companyId
	 * 
	 * @param hql
	 * @param entity
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, RiskBlacklistEntity entity, List<Object> paramList) {
		hql.append("from RiskBlacklistEntity where deleteFlag != 'Y'");
		if (null != entity) {
			if (StringUtils.isNotBlank(entity.getStartDate())) {
				hql.append(" and riskTime >= ? ");
				paramList.add(entity.getStartDate());
			}
			if (StringUtils.isNotBlank(entity.getEndDate())) {
				hql.append(" and riskTime <= ? ");
				paramList.add(entity.getEndDate());
			}
			if (StringUtils.isNotBlank(entity.getAccountNo())) {
				hql.append(" and accountNo like ? ");
				paramList.add("%" + entity.getAccountNo() + "%");
			}
			if (StringUtils.isNotBlank(entity.getMobile())) {
				hql.append(" and mobile like ? ");
				paramList.add("%" + entity.getMobile() + "%");
			}
			if (StringUtils.isNotBlank(entity.getEmail())) {
				hql.append(" and email like ? ");
				paramList.add("%" + entity.getEmail() + "%");
			}
			if (StringUtils.isNotBlank(entity.getIp())) {
				hql.append(" and ip like ? ");
				paramList.add("%" + entity.getIp() + "%");
			}
			if (StringUtils.isNotBlank(entity.getDeviceInfo())) {
				hql.append(" and deviceInfo like ? ");
				paramList.add("%" + entity.getDeviceInfo() + "%");
			}
			if (StringUtils.isNotBlank(entity.getRiskType())) {
				hql.append(" and riskType = ? ");
				paramList.add(entity.getRiskType());
			}
			if (StringUtils.isNotBlank(entity.getRiskReason())) {
				hql.append(" and riskReason like ? ");
				paramList.add("%" + entity.getRiskReason() + "%");
			}
			if (StringUtils.isNotBlank(entity.getRiskRemark())) {
				hql.append(" and riskRemark like ? ");
				paramList.add("%" + entity.getRiskRemark() + "%");
			}
			if (StringUtils.isNotBlank(entity.getIdCard())) {
				hql.append(" and idCard like ? ");
				paramList.add("%" + entity.getIdCard() + "%");
			}
			if (StringUtils.isNotBlank(entity.getPlatform())) {
				hql.append(" and platform = ? ");
				paramList.add(entity.getPlatform());
			}
			if (StringUtils.isNotBlank(entity.getDeviceType())) {
				hql.append(" and deviceType = ? ");
				paramList.add(entity.getDeviceType());
			}
			// 不为空并且不是集团业务权限,因为集团业务不需要过滤公司
			if (null != entity.getCompanyId() && !CompanyEnum.gw.getLabelKey().equals(entity.getCompanyId()+"")) {
				hql.append(" and companyId = ? ");
				paramList.add(entity.getCompanyId());
			}
		}
		return hql;
	}

	/**
	 * 不调用封装方法保存,因为companyId是外部传入
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void saveOrUpdate(RiskBlacklistEntity entity) throws Exception {
		if (null == entity.getRiskBlacklistId()) {
			Date date = new Date();
			entity.setCreateUser(UserContext.get().getLoginName());
			entity.setCreateDate(date);
			entity.setCreateIp(UserContext.get().getLoginIp());
			entity.setUpdateUser(UserContext.get().getLoginName());
			entity.setUpdateDate(date);
			entity.setUpdateIp(UserContext.get().getLoginIp());
			entity.setVersionNo(0L);
			entity.setEnableFlag("Y");
			entity.setDeleteFlag("N");
			this.getCurrentSession().save(entity);
		} else {
			RiskBlacklistEntity oldEntity = (RiskBlacklistEntity) this.findById(entity.getRiskBlacklistId(),
					RiskBlacklistEntity.class);
			BeanUtils.copyProperties(entity, oldEntity);
			Date date = new Date();
			oldEntity.setUpdateUser(UserContext.get().getLoginName());
			oldEntity.setUpdateDate(date);
			oldEntity.setUpdateIp(UserContext.get().getLoginIp());
			oldEntity.setVersionNo((oldEntity.getVersionNo()==null? 0:oldEntity.getVersionNo()) + 1);
			this.getCurrentSession().update(oldEntity);
		}
	}
	
	/**
	 * 
	 * @param md5
	 * @return
	 * @throws Exception
	 */
	public RiskBlacklistEntity findByCon(String md5) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from RiskBlacklistEntity where deleteFlag != 'Y' ");
		hql.append(" and md5 = ? ");
		paramList.add(md5);
		return (RiskBlacklistEntity)super.findEntityByHql(hql.toString(), paramList);
	}

}
