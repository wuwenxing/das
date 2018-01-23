package com.gw.das.business.dao.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.context.Constants;
import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.NumberUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.trade.entity.DasTradeGts2cashandaccountdetailStatisticsYears;
import com.gw.das.business.dao.trade.entity.OpenAccountAndDepositSummary;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 存取款及账户
 */
@Repository
public class DasTradeGts2cashandaccountdetailStatisticsYearsDao extends TradeSiteReportDao {

	/**
	 * 净入金
	 */
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;
		sql.append(" SELECT row_number() over() as rowkey,");
		sql.append("to_char(execdate_rpt, 'yyyy-mm-dd') as exectime, SUM(deposit_amt-withdraw_amt) AS equitymdeposit");
		/** 平台类型(MT4、GTS2) */
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(", platform as platformtype");
		}
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account ");	
		}
		sql.append(" WHERE account_type='直客' ");
		sql.append(" and is_test = 0 ");
		if(null != model){
			/** 平台类型(MT4、GTS2) */
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platform = ? ");
				paramList.add(model.getPlatformType());
			}
			boolean startTimeFlag = StringUtils.isNotBlank(model.getStartTime());
			boolean endTimeFlag = StringUtils.isNotBlank(model.getEndTime());
			if(startTimeFlag){
				sql.append(" and execdate_rpt >= ?");
				paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getStartTime()));
			}
			if(endTimeFlag){
				sql.append(" and execdate_rpt <= ?");
				paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getEndTime()));
			}
			sql.append(" and business_platform = ? ");
			paramList.add(model.getBusinessPlatformInt());
			
			sql.append("group by execdate_rpt ");
			/** 平台类型(MT4、GTS2) */
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(", platform");
			}
		}
	}
	
	/**
	 * 存取款及账户-当日
	 */
	public DasTradeGts2cashandaccountdetailStatisticsYears findDaysByCashandaccountdetail(TradeSearchModel model) throws Exception{
		String platform = model.getPlatformType();
		Integer businessplatform = model.getBusinessPlatformInt();
		// 当日-选择的日期
		Date endTimeDate = DateUtil.getDateFromStrByyyyMMdd(model.getDateTime());
		String startTime = model.getDateTime();
		String endTime = model.getDateTime();
		
		// 20171012新需求(1、需要将周六、周日的数据计入周五；2、统计范围从当月第一个工作日开始计算；)
		// 判断当日是否周五，如果是周五，将日期增加两天；
		String endWeekDays = DateUtil.getWeekOfDate(endTimeDate);
		if(Constants.weekDays[5].equals(endWeekDays)){//星期五-加2天
			endTime = DateUtil.formatDateToString(DateUtil.addDays(endTimeDate, 2), "yyyy-MM-dd");
		}
		
		/**注意时间条件-当日当月-(当月指第一天-至-选择的日期)*/
		DasTradeGts2cashandaccountdetailStatisticsYears cashandaccountdetail = this.getTotalDeposits2(startTime, endTime, platform, businessplatform);
		// 累计总激活账户
		int totalActivationAccount_1 = this.getTotalActivationAccount("", endTime, platform, businessplatform);
		cashandaccountdetail.setSumactall(Long.parseLong(totalActivationAccount_1+""));
		// 当日总激活账户
		int totalActivationAccount_2 = this.getTotalActivationAccount(startTime, endTime, platform, businessplatform);
		cashandaccountdetail.setSumactday(Long.parseLong(totalActivationAccount_2+""));
		// 当日激活账户存款
		Double totalDeposits = this.getTotalDeposits1(startTime, endTime, platform, businessplatform);
		cashandaccountdetail.setSumnewaccount(totalDeposits);
		/** 当日结余-20171012新需求(1、需要将周六、周日的数据计入周五；2、统计范围从当月第一个工作日开始计算；),当日结余不需要计入周六、周日的数据*/
		Double abalance = this.getAbalance(startTime, platform, businessplatform);
		cashandaccountdetail.setPreviousbalance(abalance);
		return cashandaccountdetail;
	}
	
	/**
	 * 存取款及账户-当月累计
	 */
	public DasTradeGts2cashandaccountdetailStatisticsYears findMonthsSumByCashandaccountdetail(TradeSearchModel model) throws Exception{
		String platform = model.getPlatformType();
		Integer businessplatform = model.getBusinessPlatformInt();
		// 选择的日期
		Date endTimeDate = DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd");
		// 当月第一天
		Date startTimeDate = DateUtil.getFirstDayOfMonth(endTimeDate);
		String startTime = DateUtil.formatDateToString(startTimeDate, "yyyy-MM-dd");
		// 统计当月数据时，结束时间=选择的日期
		String endTime = model.getDateTime();
		
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
		
		/**注意时间条件-当日当月-(当月第一个工作日-至-选择的日期)*/
		DasTradeGts2cashandaccountdetailStatisticsYears cashandaccountdetail = this.getTotalDeposits2(startTime, endTime, platform, businessplatform);
		// 当月累计总开户
		int totalOpenAccount = this.getTotalOpenAccount(startTime, endTime, platform, businessplatform);
		cashandaccountdetail.setSumopenall(Long.parseLong(totalOpenAccount+""));
		// 当月总激活账户
		int totalActivationAccount = this.getTotalActivationAccount(startTime, endTime, platform, businessplatform);
		cashandaccountdetail.setSumactday(Long.parseLong(totalActivationAccount+""));
		// 当月激活账户存款
		Double totalDeposits = this.getTotalDeposits1(startTime, endTime, platform, businessplatform);
		cashandaccountdetail.setSumnewaccount(totalDeposits);
		return cashandaccountdetail;
	}

	/**
	 * 当月累计总开户个数
	 * @return
	 */
	private int getTotalOpenAccount(String startTime, String endTime, String platform, Integer businessplatform) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT (DISTINCT a.account_no) AS totalSum ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_fx.mv_dim_account a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_pm.mv_dim_account a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_hx.mv_dim_account a ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_cf.mv_dim_account a ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		sql.append(" and a.is_first_open = 1 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(businessplatform);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" AND a.account_open_time >= ? ");
			paramList.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" AND a.account_open_time <= ? ");
			paramList.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		/** 平台类型(gts2/mt4/mt5) */
		if(StringUtils.isNotBlank(platform)){
			sql.append(" AND a.platform = ? ");
			paramList.add(platform);
		}
		int i = super.queryForInt(sql.toString(), paramList);
		return i;
	}
	
	/**
	 * 累计总激活账户个数
	 * @return
	 */
	private int getTotalActivationAccount(String startTime, String endTime, String platform, Integer businessplatform) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT (DISTINCT a.account_no) AS totalSum ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_fx.mv_dim_account a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_pm.mv_dim_account a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_hx.mv_dim_account a ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_cf.mv_dim_account a ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		sql.append(" and a.is_first_active = 1 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(businessplatform);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" AND a.active_time >= ? ");
			paramList.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" AND a.active_time <= ? ");
			paramList.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		/** 平台类型(gts2/mt4/mt5) */
		if(StringUtils.isNotBlank(platform)){
			sql.append(" AND a.platform = ? ");
			paramList.add(platform);
		}
		int i = super.queryForInt(sql.toString(), paramList);
		return i;
	}
	
	/**
	 * 获取当日或当月-激活账户存款
	 * @return
	 */
	private Double getTotalDeposits1(String startTime, String endTime, String platform, Integer businessplatform) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ");
		sql.append(" 	SUM (a.deposit_amt) AS summdeposit ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account a left join rds_fx.mv_dim_account b ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account a left join rds_pm.mv_dim_account b ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account a left join rds_hx.mv_dim_account b ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account a left join rds_cf.mv_dim_account b ");	
		}
		sql.append(" on (a.account_no = b.account_no and a.platform = b.platform "
				+ "and a.company_id = b.company_id "
				+ "and a.execdate_rpt = date(to_char(active_time, 'yyyy-MM-dd')) "
				+ ") ");
		sql.append(" WHERE b.account_type = '直客' ");
		sql.append(" and b.is_test = 0 ");
		sql.append(" and b.is_first_active = 1 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(businessplatform);
		/** 平台类型(gts2/mt4/mt5) */
		if(StringUtils.isNotBlank(platform)){
			sql.append(" AND a.platform = ? ");
			paramList.add(platform);
		}
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" AND b.active_time >= ? ");
			paramList.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" AND b.active_time <= ? ");
			paramList.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		if(null != list && list.size()>0){
			Map<String, Object> map = list.get(0);
			if(null != map && null != map.get("summdeposit")){
				// 四舍五入，保留两位有效数字
				Double value = NumberUtil.m2((Double)map.get("summdeposit"));
				return value;
			}
		}
		return 0D;
	}

	/**
	 * 获取当日或当月-总存款/总取款/净存款
	 * @return
	 */
	private DasTradeGts2cashandaccountdetailStatisticsYears getTotalDeposits2(String startTime, String endTime, String platform, Integer businessplatform) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT row_number() over() as rowkey"
				+ ", sum(a.deposit_amt) AS summdeposit"
				+ ", sum(a.withdraw_amt) AS sumwithdraw "
				+ ", sum(a.deposit_amt - a.withdraw_amt) AS equitymdeposit "
				+ ", sum(a.bonus) AS bonus ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account a ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(businessplatform);
		if(StringUtils.isNotBlank(startTime)){
			sql.append(" AND a.execdate_rpt >= ? ");
			paramList.add(DateUtil.getDateFromStr(startTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" AND a.execdate_rpt <= ? ");
			paramList.add(DateUtil.getDateFromStr(endTime, "yyyy-MM-dd"));
		}
		/** 平台类型(gts2/mt4/mt5) */
		if(StringUtils.isNotBlank(platform)){
			sql.append(" AND a.platform = ? ");
			paramList.add(platform);
		}
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasTradeGts2cashandaccountdetailStatisticsYears> detailList = (List<DasTradeGts2cashandaccountdetailStatisticsYears>) OrmUtil.reflectList(DasTradeGts2cashandaccountdetailStatisticsYears.class, list);
		if(null != detailList && detailList.size() > 0){
			return detailList.get(0);
		}else{
			return new DasTradeGts2cashandaccountdetailStatisticsYears();
		}
	}

	/**
	 * 获取当日结余
	 * @return
	 */
	private Double getAbalance(String endTime, String platform, Integer businessplatform) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sum(a.balance) AS previousbalance ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_acc a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_acc a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_acc a ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(String.valueOf(businessplatform))){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_acc a ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(businessplatform);
		if(StringUtils.isNotBlank(endTime)){
			sql.append(" AND a.execdate_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(endTime, "yyyy-MM-dd"));
		}
		/** 平台类型(gts2/mt4/mt5) */
		if(StringUtils.isNotBlank(platform)){
			sql.append(" AND a.platform = ? ");
			paramList.add(platform);
		}
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		if(null != list && list.size()>0){
			Map<String, Object> map = list.get(0);
			if(null != map && null != map.get("previousbalance")){
				// 四舍五入，保留两位有效数字
				Double value = NumberUtil.m2((Double)map.get("previousbalance"));
				return value;
			}
		}
		return 0D;
	}
	

	/**
	 * 开户及存款总结
	 */
	public List<OpenAccountAndDepositSummary> openAccountAndDepositSummaryList(TradeSearchModel model) throws Exception{
		// 条件
		String businessplatform = model.getBusinessPlatform();
		String platform = model.getPlatformType();
		String startTime = model.getStartTime();
		String endTime = model.getEndTime();

		// 判断endTime是否周五，如果是周五，将日期增加两天；
		Date endTimeDate = DateUtil.getDateFromStrByyyyMMdd(model.getEndTime());
		String endWeekDays = DateUtil.getWeekOfDate(endTimeDate);
		if(Constants.weekDays[5].equals(endWeekDays)){//星期五-加2天
			endTime = DateUtil.formatDateToString(DateUtil.addDays(endTimeDate, 2), "yyyy-MM-dd");
		}
		
		// 1、获取每日结余
		List<Object> paramList_1 = new ArrayList<Object>();
		StringBuffer sql_1 = new StringBuffer();
		sql_1.append(" SELECT a.execdate_rpt, sum(a.balance) AS balance ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_1.append("  FROM rds_fx.mv_fact_trans_agg_acc a ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_1.append("  FROM rds_pm.mv_fact_trans_agg_acc a ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_1.append("  FROM rds_hx.mv_fact_trans_agg_acc a ");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_1.append("  FROM rds_cf.mv_fact_trans_agg_acc a ");
		}
		sql_1.append(" WHERE a.account_type='直客' ");
		sql_1.append(" and a.is_test = 0 ");
		sql_1.append(" and a.business_platform = ? ");
		paramList_1.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_1.append(" AND a.execdate_rpt >= ? ");
			paramList_1.add(DateUtil.getDateFromStr(startTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql_1.append(" AND a.execdate_rpt <= ? ");
			paramList_1.add(DateUtil.getDateFromStr(endTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_1.append(" AND a.platform = ? ");
			paramList_1.add(platform);
		}
		sql_1.append(" GROUP BY execdate_rpt ");
		List<Map<String, Object>> list_1 = super.queryForList(sql_1.toString(), paramList_1);
		
		// 2、每日总存款\总取款\净存款
		List<Object> paramList_2 = new ArrayList<Object>();
		StringBuffer sql_2 = new StringBuffer();
		sql_2.append(" SELECT a.execdate_rpt, "
				+ "SUM (a.deposit_amt) AS summdeposit, "
				+ "SUM (a.withdraw_amt) AS sumwithdraw, "
				+ "SUM (a.deposit_amt - a.withdraw_amt) AS equitymdeposit");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_2.append("  FROM rds_fx.mv_fact_trans_agg_account a ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_2.append("  FROM rds_pm.mv_fact_trans_agg_account a ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_2.append("  FROM rds_hx.mv_fact_trans_agg_account a ");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_2.append("  FROM rds_cf.mv_fact_trans_agg_account a ");
		}
		sql_2.append(" WHERE a.account_type='直客' ");
		sql_2.append(" and a.is_test = 0 ");
		sql_2.append(" and a.business_platform = ? ");
		paramList_2.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_2.append(" AND a.execdate_rpt >= ? ");
			paramList_2.add(DateUtil.getDateFromStr(startTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql_2.append(" AND a.execdate_rpt <= ? ");
			paramList_2.add(DateUtil.getDateFromStr(endTime, "yyyy-MM-dd"));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_2.append(" AND a.platform = ? ");
			paramList_2.add(platform);
		}
		sql_2.append(" GROUP BY a.execdate_rpt ");
		List<Map<String, Object>> list_2 = super.queryForList(sql_2.toString(), paramList_2);
		
		// 3、每日激活账户存款(當天激活而且当日存款的客戶)(當天A)
		List<Object> paramList_3 = new ArrayList<Object>();
		StringBuffer sql_3 = new StringBuffer();
		sql_3.append(" SELECT 	a.execdate_rpt, "
				+ "SUM (a.deposit_amt) AS sumnewaccountday ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_3.append("  FROM rds_fx.mv_fact_trans_agg_account a LEFT JOIN rds_fx.mv_dim_account b ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_3.append("  FROM rds_pm.mv_fact_trans_agg_account a LEFT JOIN rds_pm.mv_dim_account b ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_3.append("  FROM rds_hx.mv_fact_trans_agg_account a LEFT JOIN rds_hx.mv_dim_account b ");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_3.append("  FROM rds_cf.mv_fact_trans_agg_account a LEFT JOIN rds_cf.mv_dim_account b ");
		}
		sql_3.append(" ON (a.account_no = b.account_no "
				+ "AND a.platform = b.platform "
				+ "AND a.company_id = b.company_id "
				+ "AND a.execdate_rpt = DATE (to_char(b.active_time, 'yyyy-MM-dd'))) ");//存款时间等于激活时间
		sql_3.append(" WHERE b.account_type='直客' ");
		sql_3.append(" and b.is_test = 0 ");
		sql_3.append(" and b.is_first_active = 1 ");
		sql_3.append(" and a.business_platform = ? ");
		paramList_3.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_3.append(" AND b.active_time >= ? ");
			paramList_3.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql_3.append(" AND b.active_time <= ? ");
			paramList_3.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_3.append(" AND a.platform = ? ");
			paramList_3.add(platform);
		}
		sql_3.append(" GROUP BY a.execdate_rpt ");
		List<Map<String, Object>> list_3 = super.queryForList(sql_3.toString(), paramList_3);
		
		// 4、每日激活的客戶数(包括當天開戶和非當天開戶)(A)
		List<Object> paramList_4 = new ArrayList<Object>();
		StringBuffer sql_4 = new StringBuffer();
		sql_4.append(" SELECT to_char(a.active_time, 'yyyy-mm-dd') as execdate_rpt, "
				+ "COUNT (DISTINCT a.account_no) AS sumactdayall ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_4.append("  FROM rds_fx.mv_dim_account a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_4.append("  FROM rds_pm.mv_dim_account a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_4.append("  FROM rds_hx.mv_dim_account a");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_4.append("  FROM rds_cf.mv_dim_account a ");	
		}
		sql_4.append(" WHERE a.account_type='直客' ");
		sql_4.append(" and a.is_test = 0 ");
		sql_4.append(" and a.is_first_active = 1 ");
		sql_4.append(" and a.business_platform = ? ");
		paramList_4.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_4.append(" AND a.active_time >= ? ");
			paramList_4.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql_4.append(" AND a.active_time <= ? ");
			paramList_4.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_4.append(" AND a.platform = ? ");
			paramList_4.add(platform);
		}
		sql_4.append(" GROUP BY execdate_rpt ");
		List<Map<String, Object>> list_4 = super.queryForList(sql_4.toString(), paramList_4);
		
		// 5、每日激活的客戶数(當天開戶而且激活的客戶)(當天A)
		List<Object> paramList_5 = new ArrayList<Object>();
		StringBuffer sql_5 = new StringBuffer();
		sql_5.append(" SELECT to_char(a.active_time, 'yyyy-mm-dd') as execdate_rpt, "
				+ "COUNT (DISTINCT a.account_no) AS sumactday ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_5.append("  FROM rds_fx.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_5.append("  FROM rds_pm.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_5.append("  FROM rds_hx.mv_dim_account a");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_5.append("  FROM rds_cf.mv_dim_account a ");
		}
		sql_5.append(" WHERE a.account_type='直客' ");
		sql_5.append(" and a.is_test = 0 ");
		sql_5.append(" and a.is_first_active = 1 ");
		sql_5.append(" and DATE (to_char(a.account_open_time, 'yyyy-MM-dd')) = DATE (to_char(a.active_time, 'yyyy-MM-dd')) ");//开户时间等于激活时间
		sql_5.append(" and a.business_platform = ? ");
		paramList_5.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_5.append(" AND a.active_time >= ? ");
			paramList_5.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql_5.append(" AND a.active_time <= ? ");
			paramList_5.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_5.append(" AND a.platform = ? ");
			paramList_5.add(platform);
		}
		sql_5.append(" GROUP BY execdate_rpt ");
		List<Map<String, Object>> list_5 = super.queryForList(sql_5.toString(), paramList_5);
		
		// 6、先算出 第一日激活的客戶数(當天開戶而且激活的客戶)(當天A)，再根据  每日激活的客戶数(當天開戶而且激活的客戶)(當天A)  指标计算得出-->>累计激活的客戶数(當天開戶而且激活的客戶)(當天A)
		List<Object> paramList_6 = new ArrayList<Object>();
		StringBuffer sql_6 = new StringBuffer();
		sql_6.append(" SELECT COUNT (DISTINCT a.account_no) AS totalSum ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_6.append("  FROM rds_fx.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_6.append("  FROM rds_pm.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_6.append("  FROM rds_hx.mv_dim_account a");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_6.append("  FROM rds_cf.mv_dim_account a ");
		}
		sql_6.append(" WHERE a.account_type='直客' ");
		sql_6.append(" and a.is_test = 0 ");
		sql_6.append(" and a.is_first_active = 1 ");
		sql_6.append(" and DATE (to_char(a.account_open_time, 'yyyy-MM-dd')) = DATE (to_char(a.active_time, 'yyyy-MM-dd')) ");//开户时间等于激活时间
		sql_6.append(" and a.business_platform = ? ");
		paramList_6.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_6.append(" AND a.active_time <= ? ");
			paramList_6.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));//得出第一日激活的客戶数(當天開戶而且激活的客戶)(當天A)
		}
		if(StringUtils.isNotBlank(platform)){
			sql_6.append(" AND a.platform = ? ");
			paramList_6.add(platform);
		}
		int i_6 = super.queryForInt(sql_6.toString(), paramList_6);
		
		// 7、每日真实开户数(當天開戶)(當天A+N)
		List<Object> paramList_7 = new ArrayList<Object>();
		StringBuffer sql_7 = new StringBuffer();
		sql_7.append(" SELECT to_char(a.account_open_time, 'yyyy-mm-dd') as execdate_rpt, "
				+ "COUNT (DISTINCT a.account_no) AS sumopendayreal ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_7.append("  FROM rds_fx.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_7.append("  FROM rds_pm.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_7.append("  FROM rds_hx.mv_dim_account a");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_7.append("  FROM rds_cf.mv_dim_account a ");
		}
		sql_7.append(" WHERE a.account_type='直客' ");
		sql_7.append(" and a.is_test = 0 ");
		sql_7.append(" and a.is_first_open = 1 ");
		sql_7.append(" and a.business_platform = ? ");
		paramList_7.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_7.append(" AND a.account_open_time >= ? ");
			paramList_7.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(endTime)){
			sql_7.append(" AND a.account_open_time <= ? ");
			paramList_7.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_7.append(" AND a.platform = ? ");
			paramList_7.add(platform);
		}
		sql_7.append(" GROUP BY execdate_rpt ");
		List<Map<String, Object>> list_7 = super.queryForList(sql_7.toString(), paramList_7);
		
		// 8、先算出 第一日真实开户数(當天開戶)(當天A+N)，再根据  每日真实开户数(當天開戶)(當天A+N) 指标计算得出-->>累计真实开户数(當天開戶)(當天A+N)
		List<Object> paramList_8 = new ArrayList<Object>();
		StringBuffer sql_8 = new StringBuffer();
		sql_8.append(" SELECT COUNT (DISTINCT a.account_no) AS totalSum ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_8.append("  FROM rds_fx.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_8.append("  FROM rds_pm.mv_dim_account a ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_8.append("  FROM rds_hx.mv_dim_account a");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_8.append("  FROM rds_cf.mv_dim_account a ");
		}
		sql_8.append(" WHERE a.account_type='直客' ");
		sql_8.append(" and a.is_test = 0 ");
		sql_8.append(" and a.is_first_open = 1 ");
		sql_8.append(" and a.business_platform = ? ");
		paramList_8.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_8.append(" AND a.account_open_time <= ? ");
			paramList_8.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));//得出第一日真实开户数(當天開戶)(當天A+N)
		}
		if(StringUtils.isNotBlank(platform)){
			sql_8.append(" AND a.platform = ? ");
			paramList_8.add(platform);
		}
		int i_8 = super.queryForInt(sql_8.toString(), paramList_8);

		// 9、每日新开模拟账户数
		List<Object> paramList_9 = new ArrayList<Object>();
		StringBuffer sql_9 = new StringBuffer();
		sql_9.append(" SELECT to_char(a.account_open_time, 'yyyy-mm-dd') as execdate_rpt, "
				+ "COUNT (DISTINCT a.account_no) AS sumopenalldemo ");
		sql_9.append("  FROM rds.dim_account_demo a ");
		sql_9.append(" WHERE a.account_type='直客' ");
		sql_9.append(" and a.is_test = 0 ");
		sql_9.append(" and a.business_platform = ? ");
		paramList_9.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_9.append(" AND a.account_open_time >= ? ");
			paramList_9.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(startTime)){
			sql_9.append(" AND a.account_open_time <= ? ");
			paramList_9.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_9.append(" AND a.platform = ? ");
			paramList_9.add(platform);
		}
		sql_9.append(" GROUP BY execdate_rpt ");
		List<Map<String, Object>> list_9 = super.queryForList(sql_9.toString(), paramList_9);

		// 10、在線支付\收還款項
		List<Object> paramList_10 = new ArrayList<Object>();
		StringBuffer sql_10 = new StringBuffer();
		sql_10.append(" SELECT to_char(a.approve_date, 'yyyy-mm-dd') as execdate_rpt, "
				+ "sum(a.deposit) as onlinepayment, "
				+ "sum(case when a.withdraw < 0 then a.withdraw else 0 end) * -1 as reimbursement ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(businessplatform)){
			sql_10.append("  FROM rds_fx.mv_fact_cash_proposal a ");
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(businessplatform)){
			sql_10.append("  FROM rds_pm.mv_fact_cash_proposal a ");
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(businessplatform)){
			sql_10.append("  FROM rds_hx.mv_fact_cash_proposal a");
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(businessplatform)){
			sql_10.append("  FROM rds_cf.mv_fact_cash_proposal a ");
		}
		sql_10.append(" WHERE a.is_test = 0 ");
		sql_10.append(" and a.business_platform = ? ");
		paramList_10.add(Integer.parseInt(businessplatform));
		if(StringUtils.isNotBlank(startTime)){
			sql_10.append(" AND a.approve_date >= ? ");
			paramList_10.add(DateUtil.getDateFromStr(startTime + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(startTime)){
			sql_10.append(" AND a.approve_date <= ? ");
			paramList_10.add(DateUtil.getDateFromStr(endTime + DateUtil.endTime));
		}
		if(StringUtils.isNotBlank(platform)){
			sql_10.append(" AND a.platform = ? ");
			paramList_10.add(platform);
		}
		sql_10.append(" GROUP BY execdate_rpt ");
		List<Map<String, Object>> list_10 = super.queryForList(sql_10.toString(), paramList_10);
		
		// 根据条件得出时间区间集合
		List<String> dateList = DateUtil.getDateIntervalList(startTime, endTime);
		// 设置返回的对象,key=时间，在依次放入返回对象
		Map<String, OpenAccountAndDepositSummary> returnMap = new HashMap<String, OpenAccountAndDepositSummary>();
		for(String d: dateList){
			OpenAccountAndDepositSummary entity = new OpenAccountAndDepositSummary();
			entity.setDatetime(d);
			returnMap.put(d, entity);
		}
		
		// 设置查询结果到对象
		for(Map<String, Object> result: list_1){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Double balance = result.get("balance")==null? 0D : NumberUtil.m2((Double)result.get("balance"));
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setBalance(balance);
		}
		for(Map<String, Object> result: list_2){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Double summdeposit = result.get("summdeposit")==null? 0D : NumberUtil.m2((Double)result.get("summdeposit"));
			Double sumwithdraw = result.get("sumwithdraw")==null? 0D : NumberUtil.m2((Double)result.get("sumwithdraw"));
			Double equitymdeposit = result.get("equitymdeposit")==null? 0D : NumberUtil.m2((Double)result.get("equitymdeposit"));
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setSummdeposit(summdeposit);
			entity.setSumwithdraw(sumwithdraw);
			entity.setEquitymdeposit(equitymdeposit);
		}
		for(Map<String, Object> result: list_3){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Double sumnewaccountday = result.get("sumnewaccountday")==null? 0D : NumberUtil.m2((Double)result.get("sumnewaccountday"));
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setSumnewaccountday(sumnewaccountday);
		}
		for(Map<String, Object> result: list_4){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Long sumactdayall = result.get("sumactdayall")==null? 0L : Long.parseLong(result.get("sumactdayall") + "");
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setSumactdayall(sumactdayall);
		}
		for(Map<String, Object> result: list_5){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Long sumactday = result.get("sumactday")==null? 0L : Long.parseLong(result.get("sumactday") + "");
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setSumactday(sumactday);
		}
		for(Map<String, Object> result: list_7){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Long sumopendayreal = result.get("sumopendayreal")==null? 0L : Long.parseLong(result.get("sumopendayreal") + "");
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setSumopendayreal(sumopendayreal);
		}
		for(Map<String, Object> result: list_9){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Long sumopenalldemo = result.get("sumopenalldemo")==null? 0L : Long.parseLong(result.get("sumopenalldemo") + "");
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setSumopenalldemo(sumopenalldemo);
		}
		for(Map<String, Object> result: list_10){
			String execdate_rpt = result.get("execdate_rpt")==null? "" : result.get("execdate_rpt")+"";
			Double onlinepayment = result.get("onlinepayment")==null? 0D : NumberUtil.m2((Double)result.get("onlinepayment"));
			Double reimbursement = result.get("reimbursement")==null? 0D : NumberUtil.m2((Double)result.get("reimbursement"));
			OpenAccountAndDepositSummary entity = returnMap.get(execdate_rpt);
			entity.setOnlinepayment(onlinepayment);
			entity.setReimbursement(reimbursement);
		}
		
		// 将周六周日的数据统计至周五,除了结余
		for(int i=0; i<dateList.size(); i++){
			String dateStr = dateList.get(i);
			Date date = DateUtil.getDateFromStrByyyyMMdd(dateStr);
			String days = DateUtil.getWeekOfDate(date);
			if(Constants.weekDays[5].equals(days)){//星期五
				OpenAccountAndDepositSummary entity = returnMap.get(dateStr);
				OpenAccountAndDepositSummary entity_1 = returnMap.get(dateList.get(i+1));//星期六数据
				OpenAccountAndDepositSummary entity_2 = returnMap.get(dateList.get(i+2));//星期日数据
				// 将周六周日的数据统计至周五
				entity.setSummdeposit(entity.getSummdeposit() + entity_1.getSummdeposit() + entity_2.getSummdeposit());
				entity.setSumwithdraw(entity.getSumwithdraw() + entity_1.getSumwithdraw() + entity_2.getSumwithdraw());
				entity.setEquitymdeposit(entity.getEquitymdeposit() + entity_1.getEquitymdeposit() + entity_2.getEquitymdeposit());
				entity.setSumnewaccountday(entity.getSumnewaccountday() + entity_1.getSumnewaccountday() + entity_2.getSumnewaccountday());
				entity.setSumactdayall(entity.getSumactdayall() + entity_1.getSumactdayall() + entity_2.getSumactdayall());
				entity.setSumactday(entity.getSumactday() + entity_1.getSumactday() + entity_2.getSumactday());
				entity.setSumopendayreal(entity.getSumopendayreal() + entity_1.getSumopendayreal() + entity_2.getSumopendayreal());
				entity.setSumopenalldemo(entity.getSumopenalldemo() + entity_1.getSumopenalldemo() + entity_2.getSumopenalldemo());
				entity.setOnlinepayment(entity.getOnlinepayment() + entity_1.getOnlinepayment() + entity_2.getOnlinepayment());
				entity.setReimbursement(entity.getReimbursement() + entity_1.getReimbursement() + entity_2.getReimbursement());
				//星期六\星期日数据清零
				entity_1.setSummdeposit(0D);
				entity_1.setSumwithdraw(0D);
				entity_1.setEquitymdeposit(0D);
				entity_1.setSumnewaccountday(0D);
				entity_1.setSumactdayall(0L);
				entity_1.setSumactday(0L);
				entity_1.setSumopendayreal(0L);
				entity_1.setSumopenalldemo(0L);
				entity_1.setOnlinepayment(0D);
				entity_1.setReimbursement(0D);
				entity_2.setSummdeposit(0D);
				entity_2.setSumwithdraw(0D);
				entity_2.setEquitymdeposit(0D);
				entity_2.setSumnewaccountday(0D);
				entity_2.setSumactdayall(0L);
				entity_2.setSumactday(0L);
				entity_2.setSumopendayreal(0L);
				entity_2.setSumopenalldemo(0L);
				entity_2.setOnlinepayment(0D);
				entity_2.setReimbursement(0D);
			}
		}
		
		// 累计激活的客戶数(當天開戶而且激活的客戶)(當天A) 与 累计真实开户数(當天開戶)(當天A+N)
		// 设置第一日-->>第一日激活的客戶数(當天開戶而且激活的客戶)(當天A)
		// 设置第一日-->>第一日真实开户数(當天開戶)(當天A+N)
		OpenAccountAndDepositSummary first = returnMap.get(startTime);
		Long sumactall = Long.parseLong(i_6+"");
		Long sumopenallreal = Long.parseLong(i_8+"");
		first.setSumactall(sumactall);
		first.setSumopenallreal(sumopenallreal);
		for(int i=0; i<dateList.size(); i++){
			String dateStr = dateList.get(i);
			Date date = DateUtil.getDateFromStrByyyyMMdd(dateStr);
			String days = DateUtil.getWeekOfDate(date);
			// 周六周日的数据不统计
			if(!Constants.weekDays[6].equals(days) && !Constants.weekDays[0].equals(days)){
				if(i != 0){
					OpenAccountAndDepositSummary entity = returnMap.get(dateStr);
					sumactall = entity.getSumactday() + sumactall;
					sumopenallreal = entity.getSumopendayreal() + sumopenallreal;
					entity.setSumactall(sumactall);
					entity.setSumopenallreal(sumopenallreal);
				}
			}
		}
		
		// 返回list
		List<OpenAccountAndDepositSummary> returnList = new ArrayList<OpenAccountAndDepositSummary>();
		for(int i=0; i<dateList.size(); i++){
			returnList.add(returnMap.get(dateList.get(i)));
		}
		return returnList;
	}
	
	
	
}
