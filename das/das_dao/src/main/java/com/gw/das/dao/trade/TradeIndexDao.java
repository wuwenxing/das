package com.gw.das.dao.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.common.utils.SqlUtil;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.trade.entity.TradeIndexEntity;

@Repository
public class TradeIndexDao extends BaseDao {

	public List<TradeIndexEntity> findList(TradeIndexEntity entity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, entity, paramList);
		hql.append(SqlUtil.getOrderField(entity.getSort(), entity.getOrder()));
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<TradeIndexEntity> findPageList(PageGrid<TradeIndexEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		TradeIndexEntity entity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, entity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, TradeIndexEntity entity, List<Object> paramList) {
		hql.append("from TradeIndexEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != entity) {
			if (StringUtils.isNotBlank(entity.getDateTime())) {
				hql.append(" and dateTime = ? ");
				paramList.add(entity.getDateTime());
			}
			if (StringUtils.isNotBlank(entity.getStartDate())) {
				hql.append(" and dateTime >= ? ");
				paramList.add(entity.getStartDate());
			}
			if (StringUtils.isNotBlank(entity.getEndDate())) {
				hql.append(" and dateTime <= ? ");
				paramList.add(entity.getEndDate());
			}
		}
		return hql;
	}

	public boolean checkDateTime(String dateTime, Long tradeIndexId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from TradeIndexEntity where dateTime = ? and companyId = ? ");
		paramList.add(dateTime);
		paramList.add(UserContext.get().getCompanyId());
		if (null != tradeIndexId && tradeIndexId > 0) {
			hql.append(" and tradeIndexId != ? ");
			paramList.add(tradeIndexId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取当日
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public TradeIndexEntity findByDateTime(String dateTime) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from TradeIndexEntity where deleteFlag != 'Y' and companyId = ? and dateTime = ? ");
		paramList.add(UserContext.get().getCompanyId());
		paramList.add(dateTime);
		return (TradeIndexEntity)super.findEntityByHql(hql.toString(), paramList);
	}

	/**
	 * 获取当日(当日是否周五，如果是周五，将日期增加两天)
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public TradeIndexEntity findByDateTimeDays(String dateTimeStr) throws Exception {
		// 选择的日期
		Date endTimeDate = DateUtil.stringToDate(dateTimeStr, "yyyy-MM-dd");
		// 统计当月数据时，结束时间=选择的日期
		String startTime = dateTimeStr;
		String endTime = dateTimeStr;
		// 判断当日是否周五，如果是周五，将日期增加两天；
		String endWeekDays = DateUtil.getWeekOfDate(endTimeDate);
		if(Constants.weekDays[5].equals(endWeekDays)){//星期五-加2天
			endTime = DateUtil.formatDateToString(DateUtil.addDays(endTimeDate, 2), "yyyy-MM-dd");
		}
		
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select "
				+ " sum(advisoryCustomerCount) as advisoryCustomerCount, "
				+ " sum(advisoryCount) as advisoryCount, "
				+ " sum(siteVisitsCount) as siteVisitsCount, "
				+ " sum(channelPromotionCosts) as channelPromotionCosts, "
				+ " sum(handOperHedgeProfitAndLossGts2) as handOperHedgeProfitAndLossGts2, "
				+ " sum(handOperHedgeProfitAndLossMt4) as handOperHedgeProfitAndLossMt4, "
				+ " sum(handOperHedgeProfitAndLossGts) as handOperHedgeProfitAndLossGts, "
				+ " sum(handOperHedgeProfitAndLossMt5) as handOperHedgeProfitAndLossMt5, "
				+ " sum(bonusOtherGts2) as bonusOtherGts2, "
				+ " sum(bonusOtherMt4) as bonusOtherMt4, "
				+ " sum(bonusOtherGts) as bonusOtherGts, "
				+ " sum(bonusOtherMt5) as bonusOtherMt5 "
				+ " from TradeIndexEntity where deleteFlag != 'Y' and companyId = ? "
				+ " and dateTime >= ? and dateTime <= ? ");
		paramList.add(UserContext.get().getCompanyId());
		paramList.add(startTime);
		paramList.add(endTime);
		
		List<Object[]> objList = super.findListByHql(hql.toString(), paramList);
		TradeIndexEntity entity = new TradeIndexEntity();
		if(null != objList && objList.size()>0){
			Object[] obj = objList.get(0);
			entity.setAdvisoryCustomerCount(obj[0] == null ? 0L:Long.parseLong(obj[0]+""));
			entity.setAdvisoryCount(obj[1] == null ? 0L:Long.parseLong(obj[1]+""));
			entity.setSiteVisitsCount(obj[2] == null ? 0L:Long.parseLong(obj[2]+""));
			entity.setChannelPromotionCosts(obj[3] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[3]+"")));
			entity.setHandOperHedgeProfitAndLossGts2(obj[4] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[4]+"")));
			entity.setHandOperHedgeProfitAndLossMt4(obj[5] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[5]+"")));
			entity.setHandOperHedgeProfitAndLossGts(obj[6] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[6]+"")));
			entity.setHandOperHedgeProfitAndLossMt5(obj[7] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[7]+"")));
			entity.setBonusOtherGts2(obj[8] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[8]+"")));
			entity.setBonusOtherMt4(obj[9] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[9]+"")));
			entity.setBonusOtherGts(obj[10] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[10]+"")));
			entity.setBonusOtherMt5(obj[11] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[11]+"")));
		}
		return entity;
	}
	
	/**
	 * 获取当月-指(当月第一个工作日-至-选择的日期)
	 * @param dateTime
	 * @return
	 * @throws Exception
	 */
	public TradeIndexEntity findByDateTimeMonths(String dateTimeStr) throws Exception {
		// 选择的日期
		Date endTimeDate = DateUtil.stringToDate(dateTimeStr, "yyyy-MM-dd");
		// 当月第一天
		Date startTimeDate = DateUtil.getFirstDayOfMonth(endTimeDate);
		String startTime = DateUtil.formatDateToString(startTimeDate, "yyyy-MM-dd");
		// 统计当月数据时，结束时间=选择的日期
		String endTime = dateTimeStr;
		
		// 20171012新需求(1、需要将周六、周日的数据计入周五；2、统计范围从当月第一个工作日开始计算；)
		// 判断当月第一天是否工作日，如果不是工作日，并判断是星期六还是星期日，将日期顺延；
		String startWeekDays = DateUtil.getWeekOfDate(startTimeDate);
		if(Constants.weekDays[0].equals(startWeekDays)){//星期日-加1天
			startTime = DateUtil.formatDateToString(DateUtil.addDays(startTimeDate, 1), "yyyy-MM-dd");
		}else if(Constants.weekDays[6].equals(startWeekDays)){//星期六-加2天
			startTime = DateUtil.formatDateToString(DateUtil.addDays(startTimeDate, 2), "yyyy-MM-dd");
		}
		// 判断当日是否周五，如果是周五，将日期增加两天；
		String endWeekDays = DateUtil.getWeekOfDate(endTimeDate);
		if(Constants.weekDays[5].equals(endWeekDays)){//星期五-加2天
			endTime = DateUtil.formatDateToString(DateUtil.addDays(endTimeDate, 2), "yyyy-MM-dd");
		}
		
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select "
				+ " sum(advisoryCustomerCount) as advisoryCustomerCount, "
				+ " sum(advisoryCount) as advisoryCount, "
				+ " sum(siteVisitsCount) as siteVisitsCount, "
				+ " sum(channelPromotionCosts) as channelPromotionCosts, "
				+ " sum(handOperHedgeProfitAndLossGts2) as handOperHedgeProfitAndLossGts2, "
				+ " sum(handOperHedgeProfitAndLossMt4) as handOperHedgeProfitAndLossMt4, "
				+ " sum(handOperHedgeProfitAndLossGts) as handOperHedgeProfitAndLossGts, "
				+ " sum(handOperHedgeProfitAndLossMt5) as handOperHedgeProfitAndLossMt5, "
				+ " sum(bonusOtherGts2) as bonusOtherGts2, "
				+ " sum(bonusOtherMt4) as bonusOtherMt4, "
				+ " sum(bonusOtherGts) as bonusOtherGts, "
				+ " sum(bonusOtherMt5) as bonusOtherMt5 "
				+ " from TradeIndexEntity where deleteFlag != 'Y' and companyId = ? "
				+ " and dateTime >= ? and dateTime <= ? ");
		paramList.add(UserContext.get().getCompanyId());
		paramList.add(startTime);
		paramList.add(endTime);
		
		List<Object[]> objList = super.findListByHql(hql.toString(), paramList);
		TradeIndexEntity entity = new TradeIndexEntity();
		if(null != objList && objList.size()>0){
			Object[] obj = objList.get(0);
			entity.setAdvisoryCustomerCount(obj[0] == null ? 0L:Long.parseLong(obj[0]+""));
			entity.setAdvisoryCount(obj[1] == null ? 0L:Long.parseLong(obj[1]+""));
			entity.setSiteVisitsCount(obj[2] == null ? 0L:Long.parseLong(obj[2]+""));
			entity.setChannelPromotionCosts(obj[3] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[3]+"")));
			entity.setHandOperHedgeProfitAndLossGts2(obj[4] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[4]+"")));
			entity.setHandOperHedgeProfitAndLossMt4(obj[5] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[5]+"")));
			entity.setHandOperHedgeProfitAndLossGts(obj[6] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[6]+"")));
			entity.setHandOperHedgeProfitAndLossMt5(obj[7] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[7]+"")));
			entity.setBonusOtherGts2(obj[8] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[8]+"")));
			entity.setBonusOtherMt4(obj[9] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[9]+"")));
			entity.setBonusOtherGts(obj[10] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[10]+"")));
			entity.setBonusOtherMt5(obj[11] == null ? 0D:NumberUtil.m2(Double.parseDouble(obj[11]+"")));
		}
		return entity;
	}
	
	
}
