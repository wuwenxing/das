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
 * 结余图表报表Dao
 * 
 * @author darren
 *
 */
@Repository
public class DailyreportWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;						
		sql.append("SELECT	row_number() over() as rowkey, to_char(execdate_rpt, 'yyyy-mm-dd') as exectime,");
		if("business".equals(model.getType())){
			sql.append(" currency, ");
			sql.append(" case SUM(a.floating_profit) when 0 then 0 else SUM(a.floating_profit)*-1 end as floatingprofit, "); //浮动盈亏
			sql.append(" SUM(a.balance) as balance, "); //结余
			sql.append(" SUM(a.margin) as margin ");  //保证金
		}else{
			sql.append(" SUM(case when platform='MT4' then a.floating_profit end)*-1 as mt4floatingprofit, "); //浮动盈亏
			sql.append(" SUM(case when platform='MT4' then a.balance end) as mt4balance, "); //结余
			sql.append(" SUM(case when platform='MT4' then a.margin end) as mt4margin, ");  //保证金
			
			sql.append(" SUM(case when platform='GTS2' then a.floating_profit end)*-1 as gts2floatingprofit, "); //浮动盈亏
			sql.append(" SUM(case when platform='GTS2' then a.balance end) as gts2balance, "); //结余
			sql.append(" SUM(case when platform='GTS2' then a.margin end) as gts2margin ");  //保证金
		}
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_acc a ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_acc a ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_acc a ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_acc a ");	
		}	

		sql.append(" WHERE 1 = 1 ");
		sql.append(" and a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		if(null != model){
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and a.execdate_rpt >= ?");
				paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and a.execdate_rpt <= ?");
				paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
			}	
			if (StringUtils.isNotBlank(model.getCompanyid())) {
				sql.append(" and a.company_id = ? ");
				paramList.add(model.getCompanyid());
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and a.platform = ? ");
				paramList.add(model.getPlatformType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and a.business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}	
			
			sql.append("group by execdate_rpt ");	
			if("business".equals(model.getType())){
				sql.append(" ,currency ");
			}
		}
		
	}

}
