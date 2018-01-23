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
import com.gw.das.business.dao.trade.entity.DasTradeGts2Mt4Top10PositionStatisticsYears;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 十大交易统计-持仓时间
 */
@Repository
public class DasTradeGts2Mt4Top10PositionStatisticsYearsDao extends TradeSiteReportDao {
	
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		
	}
	
	/**
	 * 十大交易统计-持仓时间
	 */
	public List<DasTradeGts2Mt4Top10PositionStatisticsYears> findTop10VolumeStatistics(TradeSearchModel model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from ( ");
		sql.append(" SELECT ");
		sql.append(" 	t1.dateTime, ");
		sql.append(" 	t1.clean_profit, ");
		sql.append(" 	t1.interval_time as time_range, ");
		sql.append(" 	t1.cnt as dealamount, ");
		sql.append(" 	CASE WHEN t2.cnt > 0 THEN round(t1.cnt::numeric / t2.cnt::numeric, 4)*100 ELSE 0 END AS percent, ");
		sql.append(" 	t1.business_platform as businessplatform ");
		sql.append(" FROM ( ");
		sql.append(" 		SELECT ");
		sql.append(" 			to_char(A .closetime_rpt, 'yyyy-mm-dd') AS dateTime, ");
		sql.append(" 			CASE ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '0 MINUTE' AND (A.closetime-A.opentime) < INTERVAL '1 MINUTE' THEN ");
		sql.append(" 			'A' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) >= INTERVAL '1 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '5 MINUTE' THEN ");
		sql.append(" 			'B' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '5 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '15 MINUTE' THEN ");
		sql.append(" 			'C' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '15 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '60 MINUTE' THEN ");
		sql.append(" 			'D' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '60 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '360 MINUTE' THEN ");
		sql.append(" 			'E' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '360 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '1440 MINUTE' THEN ");
		sql.append(" 			'F' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '1440 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '4320 MINUTE' THEN ");
		sql.append(" 			'G' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '4320 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '10080 MINUTE' THEN ");
		sql.append(" 			'H' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '10080 MINUTE' AND (A.closetime-A.opentime) <= INTERVAL '43200 MINUTE' THEN ");
		sql.append(" 			'I' ");
		sql.append(" 		WHEN (A.closetime-A.opentime) > INTERVAL '43200 MINUTE' THEN ");
		sql.append(" 			'J' ");
		sql.append(" 		END AS interval_time, ");
		sql.append(" 		count(1) AS cnt, ");
		sql.append(" 		SUM (A .profit + A .commission + A .swap) AS clean_profit, ");
		sql.append(" 		A .business_platform ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_close A ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_close A ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_close A ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_close A ");	
		}
		sql.append(" WHERE A.account_type='直客' ");
		sql.append(" 	and A.is_test = 0 ");
		sql.append(" 	and (A.closetime-A.opentime) != INTERVAL '0 MINUTE' ");
		sql.append(" 	and A.business_platform = ? ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append("	    AND A.platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" 		AND A.closetime_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append(" 	GROUP BY ");
		sql.append(" 		A .closetime_rpt, ");
		sql.append(" 		A .business_platform, ");
		sql.append(" 		interval_time ");
		sql.append(" 	) t1, ( ");
		sql.append(" 		SELECT count(1) AS cnt ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_close B ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_close B ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_close B ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_close B ");	
		}
		sql.append(" WHERE B.account_type='直客' ");
		sql.append(" 	and B.is_test = 0 ");
		sql.append(" 	and (B.closetime-B.opentime) != INTERVAL '0 MINUTE' ");
		sql.append(" 	and B.business_platform = ? ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append("	    AND B.platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" 		AND B.closetime_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append(" 	) t2 ");
		
		sql.append(") d ");
		sql.append(SqlUtil.getOrderField(model.getSort(), model.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasTradeGts2Mt4Top10PositionStatisticsYears> detailList = (List<DasTradeGts2Mt4Top10PositionStatisticsYears>) OrmUtil.reflectList(DasTradeGts2Mt4Top10PositionStatisticsYears.class, list);
		return detailList;
	}
	
}
