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
 * 交易类别数据Dao
 * 
 * @author darren
 *
 */
@Repository
public class DealcategoryWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;						
		sql.append("SELECT	row_number() over() as rowkey, ");
		
		sql.append(" to_char(t1.exectime, 'yyyy-mm-dd') exectime ,t1.currency ,t1.mt4volumeaxu ,t1.mt4volumeaxg ,t1.mt4volumeaxucnh ,t1.mt4volumeaxgcnh , ");
		sql.append(" t1.gts2volumeaxu ,t1.gts2volumeaxg ,t1.gts2volumeaxucnh ,t1.gts2volumeaxgcnh , ");
		sql.append(" t2.mt4floatingprofit,t2.gts2floatingprofit,t2.mt4balance,t2.gts2balance,t2.mt4dealamount,t2.gts2dealamount,t2.mt4useramount,t2.gts2useramount ");
		sql.append(" from ( ");
		sql.append(" select  execdate_rpt as exectime, currency, ");
		
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
		
		sql.append(" group by exectime, currency ");
		sql.append(" ) t1 ");
		
		sql.append("  join (  ");
		sql.append("  select  execdate_rpt as exectime, currency, ");	
		sql.append("  sum(case when platform='MT4' then floating_profit else 0 end) as mt4floatingprofit ,  ");	
		sql.append("  sum(case when platform='GTS2' then floating_profit else 0 end) as gts2floatingprofit ,  ");	
		sql.append("  sum(case when platform='MT4' then balance else 0 end) as mt4balance,  ");	
		sql.append("  sum(case when platform='GTS2' then balance else 0 end) as gts2balance,  ");	
		sql.append("  sum(case when platform='MT4' then close_volume else 0 end) as mt4dealamount,  ");	
		sql.append("  sum(case when platform='GTS2' then close_volume else 0 end) as gts2dealamount,  ");	
		sql.append("  count(case when platform='MT4' then account_no  end) as mt4useramount,  ");	
		sql.append("  count(case when platform='GTS2' then account_no end) as gts2useramount  ");	
		
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
		
		//sql.append("  from rds.fact_trans_agg_acc  ");	
		sql.append("  where 1=1   ");	
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
		
		sql.append("  group by exectime, currency   ");
		sql.append("  ) t2 on t1.exectime=t2.exectime and t1.currency=t2.currency  ");	
		
	}

}
