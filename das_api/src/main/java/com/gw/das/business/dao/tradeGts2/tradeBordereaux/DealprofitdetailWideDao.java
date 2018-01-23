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
 * 交易记录总结报表(包括了交易手数图表和毛利图表)Dao (宽表处理)
 * 
 * @author darren
 *
 */
@Repository
public class DealprofitdetailWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;	
		sql.append("select t1.rowkey,to_char(t1.exectime, 'yyyy-mm-dd') exectime,t1.currency,t1.grossprofit,t1.companyprofit,t1.swap,t1.sysclearzero, t1.bonus,t1.commission,t1.adjustfixedamount,t1.goldprofit, ");
		sql.append(" t1.silverprofit,t1.xaucnhprofit,t1.xagcnhprofit,t1.goldvolume,t1.silvervolume,t1.xaucnhvolume,t1.xagcnhvolume, t1.volume,t1.closedvolume, ");
		sql.append(" t1.useramount,t1.dealamount, ");
		sql.append(" t2.margin,t2.balance,t2.floatingprofit ");
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,t1.platform ");
		}
		sql.append(" from ( ");
		sql.append("SELECT	row_number() over() as rowkey, execdate_rpt as exectime,currency, ");             //盈亏 - 利息 - 佣金 - 调整 - 系统清零
		sql.append(" case sum((profit + commission + swap + clearzero + adjust_amt)) when 0 then 0 else sum((profit + commission + swap + clearzero + adjust_amt))*-1 end AS grossprofit, ");//毛利
		//sql.append(" SUM(profit + commission + swap) as companyprofit, ");                                   //公司实现盈亏
		sql.append(" SUM(a.profit)*-1 AS companyprofit, ");                                                    //公司实现盈亏
		sql.append(" case SUM(swap) when 0 then 0 else SUM(swap)*-1 end as swap, ");                           //利息
		sql.append(" SUM(clearzero) as sysclearzero, ");	                                                   //系统清零
		sql.append(" SUM(bonus) as bonus, ");                                                                  //推广贈金
		sql.append(" SUM(commission) as commission, ");                                                        //返佣
		sql.append(" SUM(adjust_amt) as adjustfixedamount, ");                                                 //调整
		
		sql.append(" case SUM(profit_axu) when 0 then 0 else SUM(profit_axu)*-1 end as goldprofit, ");         //伦敦金盈亏
		sql.append(" case SUM(profit_axg) when 0 then 0 else SUM(profit_axg)*-1 end as silverprofit, ");       //伦敦银盈亏		
		sql.append(" case SUM(profit_axucnh) when 0 then 0 else SUM(profit_axucnh)*-1 end as xaucnhprofit, "); //人民幣金盈虧
		sql.append(" case SUM(profit_axgcnh) when 0 then 0 else SUM(profit_axgcnh)*-1 end as xagcnhprofit, "); //人民幣銀盈虧 
		sql.append(" SUM(volume_axu) as goldvolume, ");                                                        //伦敦交易手數(金)
		sql.append(" SUM(volume_axg) as silvervolume, ");                                                      //伦敦交易手數(銀)
		sql.append(" SUM(volume_axucnh) as xaucnhvolume, ");                                                   //人民幣交易手數(人民幣金)
		sql.append(" SUM(volume_axgcnh) as xagcnhvolume, ");                                                   //人民幣交易手數(人民幣銀)
		sql.append(" SUM(a.volume) as volume, ");                                                              //交易手數
		sql.append(" SUM(close_volume) as closedvolume, ");                                                    //交易手數（平倉手）
		sql.append(" COUNT (DISTINCT case when close_volume <> 0 OR open_volume <> 0 then account_no end) AS useramount, ");   //交易人次 
		sql.append(" SUM (case when close_volume <> 0 OR open_volume <> 0  then (close_cnt + open_cnt) end ) AS dealamount "); //交易次數	
		
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,platform ");
		}	
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account a ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account a ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account a ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account a ");	
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
		sql.append(" select execdate_rpt as exectime ,currency,SUM (margin) AS margin,SUM (balance) AS balance ,SUM (floating_profit)*-1 AS floatingprofit ");  
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
