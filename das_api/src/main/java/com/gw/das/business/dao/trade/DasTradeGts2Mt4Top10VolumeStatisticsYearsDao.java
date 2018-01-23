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
import com.gw.das.business.dao.trade.entity.DasTradeGts2Mt4Top10VolumeStatisticsYears;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 十大交易统计-手数
 */
@Repository
public class DasTradeGts2Mt4Top10VolumeStatisticsYearsDao extends TradeSiteReportDao {
	
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		
	}
	
	/**
	 * 十大交易统计-手数
	 */
	public List<DasTradeGts2Mt4Top10VolumeStatisticsYears> findTop10VolumeStatistics(TradeSearchModel model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT row_number() over() as rowkey, d.* from (	");
		sql.append("  SELECT  ");
		sql.append("   t1.dateTime,  ");
		sql.append("   t1.clean_profit, ");
		sql.append("   t1.closedvolume, ");
		sql.append("   t1.volume_range, ");
		sql.append("   CASE WHEN t2.countclosevolume > 0 THEN round(t1.closedvolume::numeric / t2.countclosevolume::numeric, 4)*100 ELSE 0 END AS percent, ");
		sql.append("   t1.business_platform as businessplatform ");
		sql.append("  FROM (  ");
		sql.append("  		SELECT  ");
		sql.append("  			to_char(A .closetime_rpt, 'yyyy-mm-dd') AS dateTime,  ");
		sql.append("  			CASE  ");
		sql.append("  		WHEN A .volume >= 0 AND A .volume < 0.1 THEN  ");
		sql.append("  			'A'  ");
		sql.append("  		WHEN A .volume >= 0.1 AND A .volume < 0.5 THEN  ");
		sql.append("  			'B'  ");
		sql.append("  		WHEN A .volume >= 0.5 AND A .volume < 1 THEN  ");
		sql.append("  			'C'  ");
		sql.append("  		WHEN A .volume >= 1 AND A .volume < 2 THEN  ");
		sql.append("  			'D'  ");
		sql.append("  		WHEN A .volume >= 2 AND A .volume < 4 THEN  ");
		sql.append("  			'E'  ");
		sql.append("  		WHEN A .volume >= 4 AND A .volume < 10 THEN  ");
		sql.append("  			'F'  ");
		sql.append("  		WHEN A .volume >= 10 AND A .volume < 20 THEN  ");
		sql.append("  			'G'  ");
		sql.append("  		WHEN A .volume >= 20 AND A .volume <= 30 THEN  ");
		sql.append("  			'H'  ");
		sql.append(" 		ELSE ");
		sql.append(" 			'J' ");
		sql.append("  		END AS volume_range,  ");
		sql.append("     SUM (A .volume) as closedvolume, ");
		sql.append("  		SUM (A .profit + A .commission + A .swap) AS clean_profit,  ");
		sql.append("  		A .business_platform  ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_close A ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_close A ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_close A ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_close A ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append("  	and a.is_test = 0  ");
		sql.append("  	and a.business_platform = ?  ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append("	    AND a.platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" 		AND a.closetime_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append("  	GROUP BY  ");
		sql.append("  		A .closetime_rpt,  ");
		sql.append("  		A .business_platform,  ");
		sql.append("  		volume_range ");
		sql.append("  	) t1, (  ");
		sql.append("  		SELECT SUM (volume) AS countclosevolume  ");
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_close A ");	
		}else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_close A ");			
		}else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_close A ");	
		}else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_close A ");	
		}
		sql.append(" WHERE a.account_type='直客' ");
		sql.append("  	and a.is_test = 0  ");
		sql.append("  	and A .business_platform = ?  ");
		paramList.add(model.getBusinessPlatformInt());
		/** 平台类型(MT4、GTS2) */
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append("	    AND a.platform = ? ");
			paramList.add(model.getPlatformType());
		}
		/** 指定日期 */
		if(StringUtils.isNotBlank(model.getDateTime())){
			sql.append(" 		AND a.closetime_rpt = ? ");
			paramList.add(DateUtil.getDateFromStr(model.getDateTime(), "yyyy-MM-dd"));
		}
		sql.append("  	) t2  ");
		sql.append(") d ");
		sql.append(SqlUtil.getOrderField(model.getSort(), model.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasTradeGts2Mt4Top10VolumeStatisticsYears> detailList = (List<DasTradeGts2Mt4Top10VolumeStatisticsYears>) OrmUtil.reflectList(DasTradeGts2Mt4Top10VolumeStatisticsYears.class, list);
		return detailList;
	}
	
}
