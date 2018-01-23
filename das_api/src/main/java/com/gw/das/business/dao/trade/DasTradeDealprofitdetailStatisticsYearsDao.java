package com.gw.das.business.dao.trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.context.Constants;
import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.NumberUtil;
import com.gw.das.business.common.utils.SqlUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.trade.entity.DasTradeDealprofitdetailStatisticsYears;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 交易记录总结
 */
@Repository
public class DasTradeDealprofitdetailStatisticsYearsDao extends TradeSiteReportDao {
	
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		
	}
	
	/**
	 * 交易记录总结-当日(宽表统计)
	 */
	public DasTradeDealprofitdetailStatisticsYears findDaysByProfitdetailWide(TradeSearchModel model) throws Exception{
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
		
		/** 浮动盈亏-20171012新需求(1、需要将周六、周日的数据计入周五；2、统计范围从当月第一个工作日开始计算；),当日结余不需要计入周六、周日的数据*/
		Double floatingprofit = getFloatingprofit(startTime, platform, businessplatform);
		
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT row_number() over() as rowkey,");
		sql.append(""
				+ "sum((a.profit + a.commission + a.swap + a.clearzero + a.adjust_amt))*-1 AS grossprofit, "  //毛利
				+ "sum(a.profit)*-1 AS companyprofit, "                         //盈亏
			    + "sum(a.profit + a.clearzero)*-1 as netCompanyprofit, "      //净盈亏
			    + "sum(a.swap)*-1 as swap, "                                                          //实现利息
			    + "sum(a.clearzero) as sysclearzero, "                                             //系统清零
			    + "sum(a.open_volume + a.close_volume) as volume, "                                //交易手数
			    + "sum(a.profit_axu)*-1 as goldprofit, "                                              //交易盈亏(金)
			    + "sum(a.profit_axg)*-1 as silverprofit, "                                            //交易盈亏(银)
			    + "sum(a.profit_axucnh)*-1 as xaucnhprofit, "                                         //交易盈亏(人民币金)
			    + "sum(a.profit_axgcnh)*-1 as xagcnhprofit, "                                         //交易盈亏(人民币银)
			    + "sum(a.volume_axu) as goldvolume, "                                              //交易手数(金)
			    + "sum(a.volume_axg) as silvervolume, "                                            //交易手数(银)
			    + "sum(a.volume_axucnh) as xaucnhvolume, "                                         //交易手数(人民币金)
			    + "sum(a.volume_axgcnh) as xagcnhvolume, "                                         //交易手数(人民币银)
		        + "sum(a.commission) as commission, "                                              //佣金(当日回赠)
		        + "sum(a.bonus) as bonus, "                                                        //推广贈金
		        + "sum(a.bonusclear) as bonusclear, "                                              //贈金活動清零
		        + "sum(a.adjust_amt) as adjustfixedamount ");                                      //调整
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account a ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(businessplatform);
		if(null != model){
			if(StringUtils.isNotBlank(startTime)){
				sql.append(" and a.execdate_rpt >= ?");
				paramList.add(DateUtil.getDateFromStr(startTime, "yyyy-MM-dd"));
			}
			if(StringUtils.isNotBlank(endTime)){
				sql.append(" and a.execdate_rpt <= ?");
				paramList.add(DateUtil.getDateFromStr(endTime, "yyyy-MM-dd"));
			}
			/** 平台类型(MT4、GTS2) */
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and a.platform = ? ");
				paramList.add(platform);
			}
		}
		sql.append(SqlUtil.getOrderField(model.getSort(), model.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasTradeDealprofitdetailStatisticsYears> detailList = (List<DasTradeDealprofitdetailStatisticsYears>) OrmUtil.reflectList(DasTradeDealprofitdetailStatisticsYears.class, list);
		DasTradeDealprofitdetailStatisticsYears tradeDealprofit = null;
		if(null != detailList && detailList.size() > 0){
			tradeDealprofit = detailList.get(0);
			tradeDealprofit.setFloatingprofit(floatingprofit);
		}else{
			tradeDealprofit = new DasTradeDealprofitdetailStatisticsYears();
			tradeDealprofit.setFloatingprofit(floatingprofit);
		}
		return tradeDealprofit;
	}
	
	/**
	 * 交易记录总结-当月累计(宽表统计)
	 */
	public DasTradeDealprofitdetailStatisticsYears findMonthsSumByProfitdetailWide(TradeSearchModel model) throws Exception{
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
		
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT row_number() over() as rowkey,");
		sql.append(""
				+ "sum((a.profit + a.commission + a.swap + a.clearzero + a.adjust_amt - a.bonus - a.bonusclear))*-1 AS grossprofit, " //当月毛利=（当月中每日毛利的总和）- 当月活动推广赠金 - 当月活动赠金清零
				+ "sum(a.profit)*-1 AS companyprofit, "                         //盈亏
			    + "sum(a.profit + a.clearzero)*-1 as netCompanyprofit, "      //净盈亏
			    + "sum(a.swap)*-1 as swap, "                                                          //实现利息
			    + "sum(a.clearzero) as sysclearzero, "                                             //系统清零
			    + "sum(a.open_volume + a.close_volume) as volume, "                                //交易手数
			    + "sum(a.profit_axu)*-1 as goldprofit, "                                              //交易盈亏(金)
			    + "sum(a.profit_axg)*-1 as silverprofit, "                                            //交易盈亏(银)
			    + "sum(a.profit_axucnh)*-1 as xaucnhprofit, "                                         //交易盈亏(人民币金)
			    + "sum(a.profit_axgcnh)*-1 as xagcnhprofit, "                                         //交易盈亏(人民币银)
			    + "sum(a.volume_axu) as goldvolume, "                                              //交易手数(金)
			    + "sum(a.volume_axg) as silvervolume, "                                            //交易手数(银)
			    + "sum(a.volume_axucnh) as xaucnhvolume, "                                         //交易手数(人民币金)
			    + "sum(a.volume_axgcnh) as xagcnhvolume, "                                         //交易手数(人民币银)
		        + "sum(a.commission) as commission, "                                              //佣金(当日回赠)
		        + "sum(a.bonus) as bonus, "                                                        //推广贈金
		        + "sum(a.bonusclear) as bonusclear, "                                              //贈金活動清零
		        + "sum(a.adjust_amt) as adjustfixedamount ");                                      //调整
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account a ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(businessplatform);
		if(null != model){
			if(StringUtils.isNotBlank(model.getDateTime())){
				sql.append(" and a.execdate_rpt >= ?");
				sql.append(" and a.execdate_rpt <= ?");
				paramList.add(DateUtil.getDateFromStr(startTime, "yyyy-MM-dd"));
				paramList.add(DateUtil.getDateFromStr(endTime, "yyyy-MM-dd"));
			}
			/** 平台类型(MT4、GTS2) */
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and a.platform = ? ");
				paramList.add(platform);
			}
		}

		sql.append(SqlUtil.getOrderField(model.getSort(), model.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasTradeDealprofitdetailStatisticsYears> detailList = (List<DasTradeDealprofitdetailStatisticsYears>) OrmUtil.reflectList(DasTradeDealprofitdetailStatisticsYears.class, list);
		DasTradeDealprofitdetailStatisticsYears tradeDealprofit = null;
		if(null != detailList && detailList.size() > 0){
			tradeDealprofit = detailList.get(0);
		}else{
			tradeDealprofit = new DasTradeDealprofitdetailStatisticsYears();
		}
		return tradeDealprofit;
	}
	
	/**
	 * 获取当日-浮动盈亏
	 * @return
	 */
	private Double getFloatingprofit(String endTime, String platform, Integer businessplatform) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sum(a.floating_profit)*-1 as floatingprofit ");
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
			if(null != map && null != map.get("floatingprofit")){
				// 四舍五入，保留两位有效数字
				Double value = NumberUtil.m2((Double)map.get("floatingprofit"));
				return value;
			}
		}
		return 0D;
	}
	
	public static void main(String[] args) {
		Date dateTime = DateUtil.getDateFromStr("2017-04-22", "yyyy-MM-dd");
		String firstDay = DateUtil.formatDateToString(DateUtil.getFirstDayOfMonth(dateTime), "yyyy-MM-dd");
		System.out.println(firstDay);
		String lastDay = DateUtil.formatDateToString(DateUtil.getLastDayOfMonth(dateTime), "yyyy-MM-dd");
		System.out.println(lastDay);
	}
}
