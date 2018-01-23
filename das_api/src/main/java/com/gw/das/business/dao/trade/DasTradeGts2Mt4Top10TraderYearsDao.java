package com.gw.das.business.dao.trade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.SqlUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.trade.entity.DasTradeGts2Mt4Top10TargetDateYears;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 十大交易量客戶
 */
@Repository
public class DasTradeGts2Mt4Top10TraderYearsDao extends TradeSiteReportDao {
	
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		
	}
	
	/**
	 * 十大交易量排名
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> findTop10Trader(TradeSearchModel model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from (	");
		sql.append(" select row_number() over() as rowkey"
				+ ", e.account_no"
				+ ", k.account_name_cn as chinese_name"
				+ ", to_char(e.execdate_rpt, 'yyyy-mm-dd') as datetime"
				+ ", e.closedvolume, f.account_clean_profit, e.platform"
				+ ", e.business_platform as businessplatform "
				+ " from (select * from (");
		sql.append(" select a.account_no, a.execdate_rpt, sum(a.close_volume) as closedvolume, a.platform, a.business_platform");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account a ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account a ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account a ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		sql.append(" and a.business_platform = ? ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(" and a.platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" and a.execdate_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append(" group by a.account_no, a.execdate_rpt, a.platform, a.business_platform");
		
		sql.append(" ) b order by b.closedvolume desc limit 10) e");
		
		sql.append(" left join (");
		sql.append(" select c.account_no, c.execdate_rpt, sum(c.profit + c.commission + c.swap) as account_clean_profit, c.platform, c.business_platform");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_acc c ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_acc c ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_acc c ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_acc c ");	
		}
		sql.append(" WHERE c.account_type='直客' ");
		sql.append(" and c.is_test = 0 ");
		sql.append(" and c.business_platform = ?");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(" and c.platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" and c.execdate_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append(" group by c.account_no, c.execdate_rpt, c.platform, c.business_platform) f on (e.account_no = f.account_no and e.platform = f.platform and e.business_platform = f.business_platform)");
		sql.append(" LEFT JOIN (");
		sql.append(" select account_no, account_name_cn, platform, business_platform ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_dim_account ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_dim_account ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_dim_account ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_dim_account ");	
		}
		sql.append(" WHERE account_type='直客' ");
		sql.append(" and is_test = 0 ");
		sql.append(" and business_platform = ? ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(" and platform = ? ");
			paramList.add(model.getPlatformType());
		}
		sql.append(") k on (e.account_no = k.account_no and e.platform = k.platform and e.business_platform = k.business_platform)");
		sql.append(") d ");
		sql.append(SqlUtil.getOrderField(model.getSort(), model.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasTradeGts2Mt4Top10TargetDateYears> detailList = (List<DasTradeGts2Mt4Top10TargetDateYears>) OrmUtil.reflectList(DasTradeGts2Mt4Top10TargetDateYears.class, list);
		return detailList;
	}
	
}
