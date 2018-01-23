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
public class AccountOpenchannelWideDao extends TradeSiteReportDao {

	/**
	 * 	--webPC数量   pc_website  pc_client,pc
	 *	--Android数量  WEBSITE_ANDROID android
	 *	--IOS数量  ios WEBSITE_IOS
	 *	--移动其他数量  wap_website,m  
	 *	--后台数量   BACKOFFICE
	 *	--转移数量 MIGRATE_ADMIN\
	 *	--金管家网站数量  GWAC_WEBSITE
	 *	--资料丢失数量 null 与 ''
	 *	--WEBSITE,WEBSITE_Other 
	 *	
	 */
	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;						
		sql.append(" SELECT to_char(account_open_time,'YYYY-MM-DD') AS exectime, ");
		sql.append(" COUNT(case when UPPER(client_type)='ANDROID' or UPPER(client_type)='WEBSITE_ANDROID' ");
		sql.append(" or UPPER(client_type)='IOS' or UPPER(client_type)='WEBSITE_IOS' ");
		sql.append(" or UPPER(client_type)='WAP_WEBSITE' or UPPER(client_type)='M' or UPPER(client_type)='WEBSITE_OTHER' then 1  end ) webothers, ");
		sql.append(" COUNT(case when UPPER(client_type)='BACKOFFICE'  ");
		sql.append(" or UPPER(client_type)='MIGRATE_ADMIN' ");
		sql.append(" or UPPER(client_type)='GWAC_WEBSITE' then 1  end ) others, ");
		sql.append(" COUNT(case when UPPER(client_type)='' or UPPER(client_type) is NULL then 1  end ) loseinfo, ");
		sql.append(" COUNT(case when UPPER(client_type)='PC_WEBSITE' or UPPER(client_type)='PC_CLIENT' or UPPER(client_type)='PC' or UPPER(client_type)='WEBSITE' then 1  end ) webpc ");
				
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
		sql.append(" and is_first_open = 1 ");
		if(null != model){				
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and account_open_time >= ?");
				paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and account_open_time <= ?");
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
