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
 * 新开户_激活途径比例报表Dao
 * 
 * @author darren
 *
 */
@Repository
public class AccountActivechannelWideDao extends TradeSiteReportDao {

	/**
	 * 
	 * 	--激活 active_channel
	 * 	--PC途径数量 pc  PC 
	 * 	--MOBILE途径数量  mobile MOBILE wenxin
	 * 	--其他途径数量  
	 * 
	 */
	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;						
		sql.append(" SELECT to_char(active_time,'YYYY-MM-DD') AS exectime, ");
		sql.append(" COUNT(case when UPPER(active_channel)='PC' then 1  end ) pc, ");
		sql.append(" COUNT(case when UPPER(active_channel)='MOBILE' or UPPER(active_channel)='WENXIN' then 1  end ) mobile ");
		
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,platform ");
		}	
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_dim_account ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_dim_account ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_dim_account ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_dim_account ");	
		}
		
		sql.append(" WHERE 1 = 1  ");
		sql.append(" and account_type='直客' ");
		sql.append(" and is_test = 0 ");
		sql.append(" and is_first_active = 1 ");
		if(null != model){				
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and active_time >= ?");
				paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and active_time <= ?");
				paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
			}
			
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platform = ? ");
				paramList.add(model.getPlatformType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}
			
			sql.append("group by exectime ");
			
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(", platform");
			}		
		}
		
	}

}
