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
 * 交易类别倫敦/人民幣数据Dao
 * 
 * @author darren
 *
 */
@Repository
public class DealcategoryAxuAxgcnhWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;
		sql.append(" select to_char(execdate_rpt,'YYYY-MM-DD') as exectime, ");
		
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
			sql.append(" sum(case when platform='GTS2'  and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) then volume_axgcnh else 0 end ) as gts2volumeaxgcnh  ");
		}
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg ");	
		}
		
		sql.append(" where 1=1  ");
		sql.append(" and account_type='直客' ");
		sql.append(" and is_test = 0 ");
		if(null != model){
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and execdate_rpt >= ? ");
				paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and execdate_rpt <= ? ");
				paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
			}
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}		
		}
		
		sql.append(" group by execdate_rpt ");		
	}

}
