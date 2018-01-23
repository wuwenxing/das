package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.TimingEntity;

@Repository
public class TimingDao extends BaseDao {

	public List<TimingEntity> findList(TimingEntity timingEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, timingEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<TimingEntity> findPageList(PageGrid<TimingEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		TimingEntity timingEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, timingEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, TimingEntity timingEntity, List<Object> paramList) {
		hql.append("from TimingEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != timingEntity) {
			if (null != timingEntity.getSmsId()) {
				hql.append(" and smsId = ? ");
				paramList.add(timingEntity.getSmsId());
			}
			if (null != timingEntity.getEmailId()) {
				hql.append(" and emailId = ? ");
				paramList.add(timingEntity.getEmailId());
			}
			if (StringUtils.isNotBlank(timingEntity.getTimeSwitch())) {
				hql.append(" and timeSwitch = ? ");
				paramList.add(timingEntity.getTimeSwitch());
			}
		}
		return hql;
	}

	/**
	 * 根据条件删除
	 */
	public void deleteByCon(TimingEntity timingEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete TimingEntity where companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != timingEntity.getSmsId()) {
			hql.append(" and smsId = ? ");
			paramList.add(timingEntity.getSmsId());
		}else if(null != timingEntity.getEmailId()) {
			hql.append(" and emailId = ? ");
			paramList.add(timingEntity.getEmailId());
		}else{
			throw new Exception("删除条件错误");
		}
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
	/**
	 * 根据时间条件查询所有，指定时间的记录
	 * @param timingEntity
	 * @return
	 * @throws Exception
	 */
	public List<TimingEntity> findList(Date curDate, AccountTypeEnum typeEnum) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from TimingEntity where deleteFlag != 'Y' and companyId = ?");
		paramList.add(UserContext.get().getCompanyId());
		if(null != typeEnum){
			if(AccountTypeEnum.phone.equals(typeEnum.getLabelKey())){
				hql.append(" and smsId is not null ");
			}else if(AccountTypeEnum.email.equals(typeEnum.getLabelKey())){
				hql.append(" and emailId is not null ");
			}
		}
		if (null != curDate) {
			hql.append(" and startDate <= ? ");
			hql.append(" and endDate >= ? ");
			String yyyyMMdd = DateUtil.formatDateToString(curDate, "yyyy-MM-dd");
			Date date = DateUtil.stringToDate(yyyyMMdd, "yyyy-MM-dd");
			paramList.add(date);
			paramList.add(date);
			hql.append(" and hour = ? ");
			String hourStr = DateUtil.formatDateToString(curDate, "HH");
			paramList.add(Integer.parseInt(hourStr));
			hql.append(" and minute = ? ");
			String minuteStr = DateUtil.formatDateToString(curDate, "mm");
			paramList.add(Integer.parseInt(minuteStr));
		}
		return super.findListByHql(hql.toString(), paramList);
	}
	
}
