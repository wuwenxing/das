package com.gw.das.business.dao.tradeGts2.tradeBordereaux;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.enums.DetailedEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 交易人数/次数Dao (宽表处理)
 * 
 * @author darren
 *
 */
@Repository
public class DealprofitdetailUseraAndDealamountDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;	
		sql.append(" select t2.exectime,  ");
		sql.append(" case when t1.mt4dealamount is not null then t1.mt4dealamount  else 0 end as mt4dealamount, ");
		sql.append(" case when t1.mt4useramount is not null then t1.mt4useramount  else 0 end as mt4useramount, ");
		sql.append(" case when t1.gts2dealamount is not null then t1.gts2dealamount  else 0 end as gts2dealamount, ");
		sql.append(" case when t1.gts2useramount is not null then t1.gts2useramount  else 0 end as gts2useramount  ");				
		sql.append(" from ( ");
		sql.append(" select to_char(execdate_rpt,'YYYY-MM-DD') as exectime, ");					

		sql.append(" sum(case when platform='MT4' then close_cnt+open_cnt end) as mt4dealamount, ");
		sql.append(" count(DISTINCT case when platform='MT4' then account_no end)  as mt4useramount, ");
		sql.append(" sum(case when platform='GTS2' then close_cnt+open_cnt end) as gts2dealamount, ");
		sql.append(" count(DISTINCT case when platform='GTS2' then account_no end)  as gts2useramount ");					
		
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
		if(null != model){	
			sql.append(" WHERE (close_volume<>0 or open_volume<>0) ");
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
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}	
			sql.append("group by execdate_rpt ");			
		}
		sql.append(" ) t1 ");
		sql.append(" RIGHT JOIN ");
		sql.append(" (select  DISTINCT to_char(full_date, 'YYYY-MM-DD') as exectime from rds.dim_date  ");
		sql.append(" where 1 =1  ");
		if(StringUtils.isNotBlank(model.getStartTime())){
			sql.append(" and full_date >= ?");
			paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
		}
		if(StringUtils.isNotBlank(model.getEndTime())){
			sql.append(" and full_date <= ?");
			paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
		}
		sql.append("  ) t2 ");
		sql.append(" on t1.exectime = t2.exectime ");
	}

}
