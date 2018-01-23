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
 * 十大存款、十大取款
 */
@Repository
public class DasTradeGts2Mt4Top10CashinCashoutYearsDao extends TradeSiteReportDao {
	
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		
	}
	
	/**
	 * 十大存取款排名
	 */
	public List<DasTradeGts2Mt4Top10TargetDateYears> findTop10CashinCashout(TradeSearchModel model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from (	");
		sql.append(" 		SELECT");
		sql.append("			row_number() over() as rowkey,");
		sql.append("			a.account_no,");
		sql.append("			a.deposit_amt as cashin,");
		sql.append("			a.withdraw_amt as cashout,");
		sql.append("			to_char(a.execdate_rpt, 'yyyy-mm-dd') as datetime,");
		sql.append("			a.platform,");
		sql.append("			a.business_platform as businessplatform,");
		sql.append("			b.deposit_amt as account_cashin,");
		sql.append("			b.withdraw_amt as account_cashout,");
		sql.append("			c.account_name_cn as chinese_name");
		sql.append("		from (SELECT");
		sql.append("			account_no,");
		sql.append("			sum(deposit_amt) as deposit_amt,");
		sql.append("			sum(withdraw_amt) as withdraw_amt,");
		sql.append("			execdate_rpt,");
		sql.append("			platform,");
		sql.append("			business_platform");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_account ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_account ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_account ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_account ");	
		}
		sql.append(" WHERE account_type='直客' ");
		sql.append(" 	and is_test = 0 ");
		sql.append("	and business_platform = ? ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(" and platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" and execdate_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append(" group by account_no, execdate_rpt, platform, business_platform ");
		/** 类型(0：十大存款、1：十大取款)
		 *  排序取前10条记录
		 */
		if(StringUtils.isNotBlank(model.getType())){
			if("0".equals(model.getType())){
				sql.append(" order by deposit_amt desc limit 10 ");
			}else if("1".equals(model.getType())){
				sql.append(" order by withdraw_amt desc limit 10 ");
			}
		}
		sql.append(" ) a ");
		
		sql.append(" LEFT JOIN (");
		sql.append(" select account_no, sum(deposit_amt) as deposit_amt, sum(withdraw_amt) as withdraw_amt, platform, business_platform ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg_acc ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg_acc ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg_acc ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg_acc ");	
		}
		sql.append(" WHERE account_type='直客' ");
		sql.append(" 	and is_test = 0 ");
		sql.append(" and business_platform = ? ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(" and platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" and execdate_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append(" group by account_no, platform, business_platform) b on (a.account_no = b.account_no and a.platform = b.platform and a.business_platform = b.business_platform)");
		
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
		sql.append(") c on (a.account_no = c.account_no and a.platform = c.platform and a.business_platform = c.business_platform)");
		sql.append(") d ");
		
		sql.append(SqlUtil.getOrderField(model.getSort(), model.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasTradeGts2Mt4Top10TargetDateYears> detailList = (List<DasTradeGts2Mt4Top10TargetDateYears>) OrmUtil.reflectList(DasTradeGts2Mt4Top10TargetDateYears.class, list);
		return detailList;
	}
	
	
}
