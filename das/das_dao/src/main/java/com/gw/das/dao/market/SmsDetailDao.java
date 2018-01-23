package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SendStatusEnum;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.SmsDetailEntity;

@Repository
public class SmsDetailDao extends BaseDao {

	/**
	 * 因smsid和phone联合是唯一
	 * 注：此方法不需过滤companyId
	 * @param smsid
	 * @param phone
	 * @param statusEnum
	 * @return
	 * @throws Exception
	 */
	public int updateSendStatus(String smsid, String phone, String resCode, SendStatusEnum statusEnum) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("update SmsDetailEntity set sendStatus=?, resCode=?, updateDate = ? where smsid=? and phone=?");
		paramList.add(statusEnum.getLabelKey());
		paramList.add(resCode);
		paramList.add(new Date());
		paramList.add(smsid);
		paramList.add(phone);
		return super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	public List<SmsDetailEntity> findList(SmsDetailEntity smsDetailEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, smsDetailEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<SmsDetailEntity> findPageList(PageGrid<SmsDetailEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		SmsDetailEntity smsDetailEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, smsDetailEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, SmsDetailEntity smsDetailEntity, List<Object> paramList) {
		hql.append("from SmsDetailEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsDetailEntity) {
			if (null != smsDetailEntity.getSmsId()) {
				hql.append(" and smsId = ? ");
				paramList.add(smsDetailEntity.getSmsId());
			}
			if (StringUtils.isNotBlank(smsDetailEntity.getCommitStatus())) {
				hql.append(" and commitStatus = ? ");
				paramList.add(smsDetailEntity.getCommitStatus());
			}
			if (StringUtils.isNotBlank(smsDetailEntity.getSendStatus())) {
				hql.append(" and sendStatus = ? ");
				paramList.add(smsDetailEntity.getSendStatus());
			}
			if (StringUtils.isNotBlank(smsDetailEntity.getPhone())) {
				hql.append(" and phone like ? ");
				paramList.add("%" + smsDetailEntity.getPhone() + "%");
			}
			if(null != smsDetailEntity.getStartDate()){
				hql.append(" and updateDate >= ? ");
				paramList.add(smsDetailEntity.getStartDate());
			}
			if(null != smsDetailEntity.getEndDate()){
				hql.append(" and updateDate <= ? ");
				paramList.add(smsDetailEntity.getEndDate());
			}
		}
		return hql;
	}

	/**
	 * 根据条件删除
	 */
	public void deleteByCon(Long smsId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete SmsDetailEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != smsId) {
			hql.append(" and smsId = ? ");
			paramList.add(smsId);
		}else{
			throw new Exception("删除条件错误");
		}
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
}
