package com.gw.das.business.dao.tradeGts2.tradeBordereaux;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * MT4/GTS2数据报表Dao (宽表处理)
 * 
 * @author darren
 *
 */
@Repository
public class DealprofitdetailMT4AndGtsDao extends TradeSiteReportDao {
    //公司实现盈亏 + 实现利息 - 返佣
	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;	
		sql.append("select t1.rowkey,to_char(t1.exectime, 'yyyy-mm-dd') exectime,t1.currency, ");
		sql.append(" t1.companyprofit,t1.swap,t1.sysclearzero,t1.commission,t1.adjustfixedamount,t1.closedvolume , ");
		sql.append(" t1.mt4volumeaxu,t1.mt4volumeaxg,t1.mt4volumeaxucnh,t1.mt4volumeaxgcnh, ");
		sql.append(" t1.gts2volumeaxu,t1.gts2volumeaxg,t1.gts2volumeaxucnh,t1.gts2volumeaxgcnh, ");
		sql.append(" t2.margin ");
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,t1.platform ");
		}
		sql.append(" from ( ");
		sql.append("SELECT	row_number() over() as rowkey, execdate_rpt as exectime,currency, ");
		sql.append(" SUM(a.profit)*-1 AS companyprofit, ");                                                    //公司实现盈亏
		sql.append(" case SUM(swap) when 0 then 0 else SUM(swap)*-1 end as swap, ");                           //利息
		sql.append(" SUM(clearzero) as sysclearzero, ");	                                                   //系统清零
		sql.append(" SUM(commission) as commission, ");                                                        //返佣
		sql.append(" SUM(adjust_amt) as adjustfixedamount, ");                                                 //调整
		sql.append(" SUM(close_volume) as closedvolume , ");                                                   //交易手數（平倉手）
		
		//MT4倫敦金平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axu else 0 end ) as mt4volumeaxu , ");
		}else{
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axu else 0 end ) as mt4volumeaxu , ");
		}
		//MT4倫敦銀平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axg else 0 end ) as mt4volumeaxg , ");
		}else{
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axg else 0 end ) as mt4volumeaxg , ");
		}
		//MT4人民幣金平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axucnh else 0 end ) as mt4volumeaxucnh , ");
		}else{
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axucnh else 0 end ) as mt4volumeaxucnh , ");
		}
		//MT4人民幣銀平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axgcnh else 0 end ) as mt4volumeaxgcnh , ");
		}else{
			sql.append(" sum(case when platform='MT4'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axgcnh else 0 end ) as mt4volumeaxgcnh , ");
		}
		//GTS2倫敦銀平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axu else 0 end ) as gts2volumeaxu , ");
		}else{
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axu else 0 end ) as gts2volumeaxu , ");
		}
		//GTS2倫敦金平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axg else 0 end ) as gts2volumeaxg , ");
		}else{
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axg else 0 end ) as gts2volumeaxg , ");
		}
		//GTS2人民幣金平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axucnh else 0 end ) as gts2volumeaxucnh , ");
		}else{
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axucnh else 0 end ) as gts2volumeaxucnh , ");
		}
		//GTS2人民幣銀平倉手數
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(String.valueOf(model.getBusinessPlatform()))){
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axgcnh else 0 end ) as gts2volumeaxgcnh , ");
		}else{
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axgcnh else 0 end ) as gts2volumeaxgcnh ");
		}
		
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,platform ");
		}	
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg a ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg a ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg a ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg a ");	
		}
		
		//sql.append("  FROM rds.fact_trans_agg a ");	
		sql.append(" WHERE 1 = 1 ");		
		if(null != model){	
			sql.append(" and account_type='直客' ");
			sql.append(" and is_test = 0 ");
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and execdate_rpt >= ?");
				paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and execdate_rpt <= ?");
				paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
			}	
			if (StringUtils.isNotBlank(model.getCompanyid())) {
				sql.append(" and company_id = ? ");
				paramList.add(model.getCompanyid());
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platform = ? ");
				paramList.add(model.getPlatformType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}	
			sql.append("group by execdate_rpt,currency ");
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(", platform");
			}
		}
			
		sql.append(" ) t1 join  ( ");  
		sql.append(" select execdate_rpt as exectime ,currency,");   
		sql.append(" SUM (margin) AS margin ");                       //保证金  
		
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,platform ");
		}
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_acc ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_acc ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_acc ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_acc ");	
		}
		
		//sql.append(" from rds.fact_trans_agg_acc   ");  
		sql.append(" WHERE 1 = 1 ");	
		sql.append(" and account_type='直客' ");
		sql.append(" and is_test = 0 ");
		if(null != model){				
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and execdate_rpt >= ?");
				paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and execdate_rpt <= ?");
				paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
			}	
			if (StringUtils.isNotBlank(model.getCompanyid())) {
				sql.append(" and company_id = ? ");
				paramList.add(model.getCompanyid());
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platform = ? ");
				paramList.add(model.getPlatformType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}	
			sql.append("group by execdate_rpt,currency ");
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(", platform");
			}
		}
		sql.append(" ) ");  
		sql.append(" t2 on 1=1 and t1.exectime = t2.exectime and t1.currency = t2.currency ");  
		
	}

}
