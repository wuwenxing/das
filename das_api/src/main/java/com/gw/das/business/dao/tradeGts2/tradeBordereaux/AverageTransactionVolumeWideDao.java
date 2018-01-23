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
 * 人均交易手数记录Dao (宽表处理)
 * 
 * @author darren
 *
 */
@Repository
public class AverageTransactionVolumeWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;
		sql.append(" select t1.exectime,case when t1.mt4avgtransvolume is not null then t1.mt4avgtransvolume else 0 end as mt4avgtransvolume,case when t1.gts2avgtransvolume is not null then t1.gts2avgtransvolume else 0 end as gts2avgtransvolume ");
		sql.append(" from ( ");
		sql.append(" select to_char(execdate_rpt,'YYYY-MM-DD') as exectime, ");		
		sql.append(" SUM(case when platform='MT4' then a.volume end)/COUNT (DISTINCT case when platform='MT4' and (close_volume <> 0 OR open_volume <> 0) then account_no end) as mt4avgtransvolume , ");
		sql.append(" SUM(case when platform='GTS2' then a.volume end)/COUNT (DISTINCT case when platform='GTS2' and (close_volume <> 0 OR open_volume <> 0) then account_no end) as gts2avgtransvolume ");	
		
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
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platform = ? ");
				paramList.add(model.getPlatformType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}	
			sql.append("group by execdate_rpt ");			
		}
		sql.append(" ) t1 ");
	}

}
