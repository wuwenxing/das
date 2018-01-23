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
 * 公司盈亏报表Dao(宽表处理)
 * 
 * @author darren
 *
 */
@Repository
public class DealprofithourWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;						
		sql.append("SELECT	row_number() over() as rowkey, ");
		//sql.append(" to_char(to_date(closetime_gmt8,'YYYY-MM-DD'),'YYYY-MM-DD') AS exectime, ");
		//sql.append(" date_part('hour', to_timestamp(closetime_gmt8,'YYYY-MM-DD HH24:MI:SS')) as hour, ");
		sql.append(" to_char(closetime_gmt8,'YYYY-MM-DD') AS exectime, ");
		sql.append(" date_part('hour', closetime_gmt8) as hour, ");
		sql.append(" SUM(profit) as profit, ");                                 //盈亏
		sql.append(" SUM(a.commission) as commission, ");                       //佣金
		sql.append(" SUM(a.swap) as swap, ");                                   //利息
		sql.append(" case SUM ((profit + commission + swap)) when 0 then 0  else SUM ((profit + commission + swap)) * -1 end AS companyprofit ");     //公司盈亏
		
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,platform ");
		}
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_close a ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_close a ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_close a ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_close a ");	
		}

		//sql.append("  FROM rds.fact_trans_close a ");	
		
		sql.append(" WHERE 1 = 1 ");	
		sql.append(" and a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		if(null != model){			
			if(model.getStartTime().equals(model.getEndTime())){
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.closetime_rpt = ?");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
				}
			}else{
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.closetime_rpt >= ?");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and a.closetime_rpt <= ?");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
				}
			}			
			if (StringUtils.isNotBlank(model.getCompanyid())) {
				sql.append(" and a.companyid = ? ");
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
			sql.append("group by exectime,hour");
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(", platform");
			}
		}		
	}
}
