package com.gw.das.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.api.common.context.Constants;
import com.gw.das.api.common.enums.CompanyEnum;
import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.response.ApiStatusEnum;
import com.gw.das.api.common.utils.DateUtil;
import com.gw.das.api.dao.TradeReportDao;
import com.gw.das.api.service.TradeReportService;

/**
 * 交易报表Service实现类
 * 
 * @author darren
 *
 */
@Service
public class TradeReportServiceImpl implements TradeReportService{

	private static Logger logger = LoggerFactory.getLogger(TradeReportServiceImpl.class);
	
	@Autowired
	private TradeReportDao tradeReportDao;
	
	@Override
	public ApiPageResult tradeDetailClosePageList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate,String sort, String order,int pageNumber,int pageSize) throws Exception {
		String keys = Constants.projectName + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>tradeDetailClosePageList[keys={}]", keys);

		StringBuffer sql = new StringBuffer();
	    sql.append(" select  ");
	    sql.append(" a.order_id orderid, ");
	    sql.append(" a.deal_id dealid, ");
	    sql.append(" a.position_id positionid, ");
	    sql.append(" a.account_id accountid, ");
	    sql.append(" a.account_no accountno, ");
	    sql.append(" a.business_platform businessplatform, ");
	    sql.append(" a.company_id companyid, ");
	    sql.append(" a.company_name companyname, ");
	    sql.append(" a.platform platform, ");
	    sql.append(" a.rate rate, ");
	    sql.append(" a.currency currency, ");
	    sql.append(" a.symbol_name symbolname, ");
	    sql.append(" a.symbol_shortname symbol, ");
	    sql.append(" a.symbol_type symboltype, ");
	    sql.append(" to_char(a.opentime,'YYYY-MM-DD HH24:MI:SS') opentime, ");
	    sql.append(" to_char(a.opentime_gmt8,'YYYY-MM-DD HH24:MI:SS') opentimegmt8, ");
	    sql.append(" to_char(a.closetime,'YYYY-MM-DD HH24:MI:SS') closetime, ");
	    sql.append(" to_char(a.closetime_gmt8,'YYYY-MM-DD HH24:MI:SS')  closetimegmt8, ");
	    sql.append(" a.opentime_rpt opentimerpt, ");
	    sql.append(" a.closetime_rpt closetimerpt, ");
	    sql.append(" a.proposal_no proposalno,  ");
	    sql.append(" a.proposal_type proposaltype, ");
	    sql.append(" a.client_type clienttype, ");
	    sql.append(" a.account_type accounttype, ");
	    sql.append(" a.direction, ");
	    sql.append(" a.status, ");
	    sql.append(" a.reason, ");
	    sql.append(" a.order_type ordertype, ");
	    sql.append(" a.openprice, ");
	    sql.append(" a.closeprice, ");
	    sql.append(" a.sl stoploss, ");
	    sql.append(" a.tp takeprofit, ");
	    sql.append(" a.margin, ");
	    sql.append(" a.margin_maintenance marginmaintenance, ");
	    sql.append(" a.margin_stopout marginstopout, ");
	    sql.append(" a.volume closevolume, ");
	    sql.append(" a.volume_axu volumeaxu, ");
	    sql.append(" a.volume_axg volumeaxg, ");
	    sql.append(" a.volume_axucnh volumeaxucnh, ");
	    sql.append(" a.volume_axgcnh volumeaxgcnh, ");
	    sql.append(" a.commission, ");
	    sql.append(" a.swap, ");
	    sql.append(" a.bonus, ");
	    sql.append(" a.bonusclear, ");
	    sql.append(" a.clearzero, ");
	    sql.append(" a.adjust_amt adjustamt, ");
	    sql.append(" a.profit, ");
	    sql.append(" a.profit_axu profitaxu, ");
	    sql.append(" a.profit_axg profitaxg, ");
	    sql.append(" a.profit_axucnh profitaxucnh, ");
	    sql.append(" a.profit_axgcnh profitaxgcnh, ");
	    sql.append(" case when a.openprice !=0 then (a.closeprice - a.openprice)/a.openprice else '0.00000' end  profitrase  "); //盈亏比例
		sql.append(" from rds.fact_trans_close a ");
		sql.append(" where 1=1  ");
		sql.append(" and company_id = :companyId ");
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform ");
		}
		if(null != accountNo){				
			sql.append(" and account_no = :accountNo ");
		}
		if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime_gmt8 >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime_gmt8 < :endcloseDate ");
			}
		}
		else {
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime < :endcloseDate ");
			}
		}
		
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("companyId", companyId);
		if(StringUtils.isNotBlank(platform)){
			params.put("platform", platform);
		}
		if(null != accountNo){
			params.put("accountNo", accountNo);
		}
		if(StringUtils.isNotBlank(startDate)){				
			params.put("startDate", DateUtil.stringToDate(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){									
			params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
		}
		
		if(StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)){
			params.put("sort", sort);
			params.put("order", order);
		}
		
		ApiPageResult result = new ApiPageResult(ApiStatusEnum.success);
		result.setPageNumber(pageNumber);
		result.setPageSize(pageSize);
		tradeReportDao.tradeDetailClosePageList(result, sql.toString(), params);
				
		return result;			
	}		

	@Override
	public ApiResult tradeDetailCloseList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate) throws Exception {
		String keys = Constants.projectName + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>tradeDetailCloseList[keys={}]", keys);

		StringBuffer sql = new StringBuffer();
	    sql.append(" select  ");
	    sql.append(" a.order_id orderid, ");
	    sql.append(" a.deal_id dealid, ");
	    sql.append(" a.position_id positionid, ");
	    sql.append(" a.account_id accountid, ");
	    sql.append(" a.account_no accountno, ");
	    sql.append(" a.business_platform businessplatform, ");
	    sql.append(" a.company_id companyid, ");
	    sql.append(" a.company_name companyname, ");
	    sql.append(" a.platform platform, ");
	    sql.append(" a.rate rate, ");
	    sql.append(" a.currency currency, ");
	    sql.append(" a.symbol_name symbolname, ");
	    sql.append(" a.symbol_shortname symbol, ");
	    sql.append(" a.symbol_type symboltype, ");
	    sql.append(" to_char(a.opentime,'YYYY-MM-DD HH24:MI:SS') opentime, ");
	    sql.append(" to_char(a.opentime_gmt8,'YYYY-MM-DD HH24:MI:SS') opentimegmt8, ");
	    sql.append(" to_char(a.closetime,'YYYY-MM-DD HH24:MI:SS') closetime, ");
	    sql.append(" to_char(a.closetime_gmt8,'YYYY-MM-DD HH24:MI:SS')  closetimegmt8, ");
	    sql.append(" a.opentime_rpt opentimerpt, ");
	    sql.append(" a.closetime_rpt closetimerpt, ");
	    sql.append(" a.proposal_no proposalno,  ");
	    sql.append(" a.proposal_type proposaltype, ");
	    sql.append(" a.client_type clienttype, ");
	    sql.append(" a.account_type accounttype, ");
	    sql.append(" a.direction, ");
	    sql.append(" a.status, ");
	    sql.append(" a.reason, ");
	    sql.append(" a.order_type ordertype, ");
	    sql.append(" a.openprice, ");
	    sql.append(" a.closeprice, ");
	    sql.append(" a.sl stoploss, ");
	    sql.append(" a.tp takeprofit, ");
	    sql.append(" a.margin, ");
	    sql.append(" a.margin_maintenance marginmaintenance, ");
	    sql.append(" a.margin_stopout marginstopout, ");
	    sql.append(" a.volume closevolume, ");
	    sql.append(" a.volume_axu volumeaxu, ");
	    sql.append(" a.volume_axg volumeaxg, ");
	    sql.append(" a.volume_axucnh volumeaxucnh, ");
	    sql.append(" a.volume_axgcnh volumeaxgcnh, ");
	    sql.append(" a.commission, ");
	    sql.append(" a.swap, ");
	    sql.append(" a.bonus, ");
	    sql.append(" a.bonusclear, ");
	    sql.append(" a.clearzero, ");
	    sql.append(" a.adjust_amt adjustamt, ");
	    sql.append(" a.profit, ");
	    sql.append(" a.profit_axu profitaxu, ");
	    sql.append(" a.profit_axg profitaxg, ");
	    sql.append(" a.profit_axucnh profitaxucnh, ");
	    sql.append(" a.profit_axgcnh profitaxgcnh, ");
	    sql.append(" case when a.openprice !=0 then (a.closeprice - a.openprice)/a.openprice else '0.00000' end  profitrase  "); //盈亏比例
		sql.append(" from rds.fact_trans_close a  ");
		sql.append(" where 1=1  ");
		sql.append(" and company_id = :companyId ");
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform ");
		}
		if(null != accountNo){				
			sql.append(" and account_no = :accountNo ");
		}
		if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime_gmt8 >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime_gmt8 < :endcloseDate ");
			}
		}
		else {
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime < :endcloseDate ");
			}
		}
			
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("companyId", companyId);
		if(StringUtils.isNotBlank(platform)){
			params.put("platform", platform);
		}
		if(null != accountNo){
			params.put("accountNo", accountNo);
		}
		if(StringUtils.isNotBlank(startDate)){				
			params.put("startDate", DateUtil.stringToDate(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){									
			params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
		}
		
		ApiResult result = new ApiResult(ApiStatusEnum.success);
		tradeReportDao.tradeDetailCloseList(result, sql.toString(), params);			
		return result;		
		
	}

	@Override
	public ApiPageResult tradeDetailOpenPageList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate,String sort, String order,int pageNumber,int pageSize) throws Exception {
			String keys = Constants.projectName + companyId + platform +accountNo+ startDate + endDate;
			logger.debug("==>>tradeDetailOpenPageList[keys={}]", keys);

			StringBuffer sql = new StringBuffer();
		    sql.append(" select  ");   
		    sql.append(" position_id positionid,  ");
		    sql.append(" account_id accountid,  ");
		    sql.append(" account_no accountno,  ");
		    sql.append(" business_platform businessplatform,  ");
		    sql.append(" company_id companyid,  ");
		    sql.append(" company_name companyname,  ");
		    sql.append(" platform,  ");
		    sql.append(" rate,  ");
		    sql.append(" currency,  ");
		    sql.append(" symbol_name symbolname,  ");
		    sql.append(" symbol_shortname symbol,  ");
		    sql.append(" symbol_type symboltype,  ");
		    sql.append(" to_char(opentime,'YYYY-MM-DD HH24:MI:SS') opentime,  ");
		    sql.append(" to_char(opentime_gmt8,'YYYY-MM-DD HH24:MI:SS') opentimegmt8,  ");
		    sql.append(" to_char(closetime,'YYYY-MM-DD HH24:MI:SS') closetime,  ");
		    sql.append(" to_char(closetime_gmt8,'YYYY-MM-DD HH24:MI:SS')  closetimegmt8,  ");
		    sql.append(" opentime_rpt opentimerpt,  ");
		    sql.append(" closetime_rpt closetimerpt,  ");
		    sql.append(" proposal_no proposalno,  ");
		    sql.append(" proposal_type proposaltype,  ");
		    sql.append(" client_type clienttype,  ");
		    sql.append(" account_type accounttype,  ");
		    sql.append(" direction,  ");
		    sql.append(" status,  ");
		    sql.append(" order_type ordertype,  ");
		    sql.append(" openprice,  ");
		    sql.append(" closeprice,  ");
		    sql.append(" sl stoploss,  ");
		    sql.append(" tp takeprofit,  ");
		    sql.append(" margin,  ");
		    sql.append(" margin_maintenance marginmaintenance,  ");
		    sql.append(" margin_stopout marginstopout,  ");
		    sql.append(" volume,  ");
		    sql.append(" volume_axu volumeaxu,  ");
		    sql.append(" volume_axg volumeaxg,  ");
		    sql.append(" volume_axucnh volumeaxucnh,  ");
		    sql.append(" volume_axgcnh volumeaxgcnh,  ");
		    sql.append(" commission,  ");
		    sql.append(" swap,  ");
		    sql.append(" bonus,  ");
		    sql.append(" clearzero,  ");
		    sql.append(" adjust_amt adjustamt,  ");
		    sql.append(" profit,  ");
		    sql.append(" profit_axu profitaxu,  ");
		    sql.append(" profit_axg profitaxg,  ");
		    sql.append(" profit_axucnh profitaxucnh,  ");
		    sql.append(" profit_axgcnh profitaxgcnh,  ");
		    sql.append(" close_cnt closecnt,  ");
		    sql.append(" close_volume closevolume,  ");
		    sql.append(" volume-close_volume surplusvolume  "); //剩余手数
		    sql.append(" from rds.fact_trans_position  ");
		    sql.append(" where 1=1   ");
		    sql.append(" and company_id = :companyId  ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			if(null != accountNo){				
				sql.append(" and account_no = :accountNo ");
			}
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				if(StringUtils.isNotBlank(startDate)){				
					sql.append(" and opentime_gmt8 >= :startDate ");					
				}
				if(StringUtils.isNotBlank(endDate)){									
					sql.append(" and opentime_gmt8 < :endcloseDate ");
				}
			}
			else {
				if(StringUtils.isNotBlank(startDate)){				
					sql.append(" and opentime >= :startDate ");					
				}
				if(StringUtils.isNotBlank(endDate)){									
					sql.append(" and opentime < :endcloseDate ");
				}
			}
				
			Map<String, Object> params = new HashMap<String, Object>();	
			params.put("companyId", companyId);
			if(StringUtils.isNotBlank(platform)){
				params.put("platform", platform);
			}
			if(null != accountNo){
				params.put("accountNo", accountNo);
			}
			if(StringUtils.isNotBlank(startDate)){				
				params.put("startDate", DateUtil.stringToDate(startDate));
			}
			if(StringUtils.isNotBlank(endDate)){									
				params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
			}
			
			if(StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)){
				params.put("sort", sort);
				params.put("order", order);
			}
			
			ApiPageResult result = new ApiPageResult(ApiStatusEnum.success);
			result.setPageNumber(pageNumber);
			result.setPageSize(pageSize);
			tradeReportDao.tradeDetailOpenPageList(result, sql.toString(), params);
			return result;	
			
	}

	@Override
	public ApiResult tradeDetailOpenList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate) throws Exception {
		String keys = Constants.projectName + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>tradeDetailOpenList[keys={}]", keys);

		StringBuffer sql = new StringBuffer();
	    sql.append(" select  ");   
	    sql.append(" position_id positionid,  ");
	    sql.append(" account_id accountid,  ");
	    sql.append(" account_no accountno,  ");
	    sql.append(" business_platform businessplatform,  ");
	    sql.append(" company_id companyid,  ");
	    sql.append(" company_name companyname,  ");
	    sql.append(" platform,  ");
	    sql.append(" rate,  ");
	    sql.append(" currency,  ");
	    sql.append(" symbol_name symbolname,  ");
	    sql.append(" symbol_shortname symbol,  ");
	    sql.append(" symbol_type symboltype,  ");
	    sql.append(" to_char(opentime,'YYYY-MM-DD HH24:MI:SS') opentime,  ");
	    sql.append(" to_char(opentime_gmt8,'YYYY-MM-DD HH24:MI:SS') opentimegmt8,  ");
	    sql.append(" to_char(closetime,'YYYY-MM-DD HH24:MI:SS') closetime,  ");
	    sql.append(" to_char(closetime_gmt8,'YYYY-MM-DD HH24:MI:SS')  closetimegmt8,  ");
	    sql.append(" opentime_rpt opentimerpt,  ");
	    sql.append(" closetime_rpt closetimerpt,  ");
	    sql.append(" proposal_no proposalno,  ");
	    sql.append(" proposal_type proposaltype,  ");
	    sql.append(" client_type clienttype,  ");
	    sql.append(" account_type accounttype,  ");
	    sql.append(" direction,  ");
	    sql.append(" status,  ");
	    sql.append(" order_type ordertype,  ");
	    sql.append(" openprice,  ");
	    sql.append(" closeprice,  ");
	    sql.append(" sl stoploss,  ");
	    sql.append(" tp takeprofit,  ");
	    sql.append(" margin,  ");
	    sql.append(" margin_maintenance marginmaintenance,  ");
	    sql.append(" margin_stopout marginstopout,  ");
	    sql.append(" volume,  ");
	    sql.append(" volume_axu volumeaxu,  ");
	    sql.append(" volume_axg volumeaxg,  ");
	    sql.append(" volume_axucnh volumeaxucnh,  ");
	    sql.append(" volume_axgcnh volumeaxgcnh,  ");
	    sql.append(" commission,  ");
	    sql.append(" swap,  ");
	    sql.append(" bonus,  ");
	    sql.append(" clearzero,  ");
	    sql.append(" adjust_amt adjustamt,  ");
	    sql.append(" profit,  ");
	    sql.append(" profit_axu profitaxu,  ");
	    sql.append(" profit_axg profitaxg,  ");
	    sql.append(" profit_axucnh profitaxucnh,  ");
	    sql.append(" profit_axgcnh profitaxgcnh,  ");
	    sql.append(" close_cnt closecnt,  ");
	    sql.append(" close_volume closevolume,  ");
	    sql.append(" volume-close_volume surplusvolume  "); //剩余手数
	    sql.append(" from rds.fact_trans_position  ");
	    sql.append(" where 1=1   ");
	    sql.append(" and company_id = :companyId  ");
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform ");
		}
		if(null != accountNo){				
			sql.append(" and account_no = :accountNo ");
		}
		if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and opentime_gmt8 >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and opentime_gmt8 < :endcloseDate ");
			}
		}
		else {
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and opentime >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and opentime < :endcloseDate ");
			}
		}
	    
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("companyId", companyId);
		if(StringUtils.isNotBlank(platform)){
			params.put("platform", platform);
		}
		if(null != accountNo){
			params.put("accountNo", accountNo);
		}
		if(StringUtils.isNotBlank(startDate)){				
			params.put("startDate", DateUtil.stringToDate(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){									
			params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
		}
		
		ApiResult result = new ApiResult(ApiStatusEnum.success);
		tradeReportDao.tradeDetailOpenList(result, sql.toString(), params);			
		return result;	
		
	}

	@Override
	public ApiPageResult accountbalancePageList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate,String sort, String order,int pageNumber,int pageSize) throws Exception {
		String keys = Constants.projectName + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>tradeDetailOpenPageList[keys={}]", keys);

		StringBuffer sql = new StringBuffer();
	    sql.append(" select  ");   
	    sql.append(" to_char(  "); 
	    sql.append(" (CASE WHEN t.company_id = 2 THEN  t.closetime_gmt8  ");
	    sql.append(" WHEN t.company_id = 3 THEN t.closetime_gmt8  ");
	    sql.append(" WHEN t.company_id = 8 THEN t.closetime_gmt8 ");
	    sql.append(" ELSE t.closetime END)  ");
	    sql.append(" ,'YYYY-MM-DD HH24:MI:SS')  "); 
	    sql.append(" as datetime,  "); 
	    sql.append(" account_no accountno, order_id	as orderid ,reason ,  ");
	    sql.append(" (t.AmountDst - t.AmountSrc) as amount ,  ");
	    sql.append(" platform  ");
	    sql.append(" from rds.fact_trans_close t  ");		    
	    sql.append(" where 1=1   ");
	    sql.append(" and company_id = :companyId ");
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform ");
		}
		if(null != accountNo){				
			sql.append(" and account_no = :accountNo ");
		}
		if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId)) 
				|| CompanyEnum.cf.getLabelKey().equals(String.valueOf(companyId))){
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime_gmt8 >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime_gmt8 < :endcloseDate ");
			}
		}
		else {
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime < :endcloseDate ");
			}
		}
			
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("companyId", companyId);
		if(StringUtils.isNotBlank(platform)){
			params.put("platform", platform);
		}
		if(null != accountNo){
			params.put("accountNo", accountNo);
		}
		if(StringUtils.isNotBlank(startDate)){				
			params.put("startDate", DateUtil.stringToDate(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){									
			params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
		}
		
		if(StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)){
			params.put("sort", sort);
			params.put("order", order);
		}
		
		ApiPageResult result = new ApiPageResult(ApiStatusEnum.success);
		result.setPageNumber(pageNumber);
		result.setPageSize(pageSize);
		tradeReportDao.accountbalancePageList(result, sql.toString(), params);
		return result;	
		
	}

	@Override
	public ApiResult accountbalanceList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate) throws Exception {
		String keys = Constants.projectName + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>tradeDetailOpenList[keys={}]", keys);

		StringBuffer sql = new StringBuffer();
	    sql.append(" select  ");   
	    sql.append(" to_char(  "); 
	    sql.append(" (CASE WHEN t.company_id = 2 THEN  t.closetime_gmt8  ");
	    sql.append(" WHEN t.company_id = 3 THEN t.closetime_gmt8  ");
	    sql.append(" WHEN t.company_id = 8 THEN t.closetime_gmt8 ");
	    sql.append(" ELSE t.closetime END)  ");
	    sql.append(" ,'YYYY-MM-DD HH24:MI:SS')  "); 
	    sql.append(" as datetime,  "); 
	    sql.append(" account_no accountno, order_id	as orderid ,reason ,  ");
	    sql.append(" (t.AmountDst - t.AmountSrc) as amount ,  ");
	    sql.append(" platform  ");
	    sql.append(" from rds.fact_trans_close t  ");		    
	    sql.append(" where 1=1   ");
	    sql.append(" and company_id = :companyId ");
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform ");
		}
		if(null != accountNo){				
			sql.append(" and account_no = :accountNo ");
		}
		if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId)) 
				|| CompanyEnum.cf.getLabelKey().equals(String.valueOf(companyId))){
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime_gmt8 >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime_gmt8 < :endcloseDate ");
			}
		}
		else {
			if(StringUtils.isNotBlank(startDate)){				
				sql.append(" and closetime >= :startDate ");					
			}
			if(StringUtils.isNotBlank(endDate)){									
				sql.append(" and closetime < :endcloseDate ");
			}
		}
			
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("companyId", companyId);
		if(StringUtils.isNotBlank(platform)){
			params.put("platform", platform);
		}
		if(null != accountNo){
			params.put("accountNo", accountNo);
		}
		if(StringUtils.isNotBlank(startDate)){				
			params.put("startDate", DateUtil.stringToDate(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){									
			params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
		}						
		
		ApiResult result = new ApiResult(ApiStatusEnum.success);
		tradeReportDao.accountbalanceList(result, sql.toString(), params);			
		return result;	
		
	}

	@Override
	public ApiPageResult customerdailyPageList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate,String sort, String order,int pageNumber,int pageSize) throws Exception {
		String keys = Constants.projectName + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>tradeDetailOpenPageList[keys={}]", keys);

		StringBuffer sql = new StringBuffer();
	    sql.append(" select  "); 
	    sql.append(" t.execdate_rpt as execdaterpt, ");
	    sql.append(" t.account_no as accountno,  ");
	    sql.append(" t.balance,  ");
	    sql.append(" t.equity,  ");
		sql.append(" (t.deposit_amt-previous_deposit_amt) as deposit,  ");
		sql.append(" (t.withdraw_amt-previous_withdraw_amt) as withdraw,  ");
		sql.append(" (t.profit-t.previous_profit) as balancepreviousbalance,   ");
	    sql.append(" t.floating_profit as floatingprofit,  ");
	    sql.append(" t.margin,  ");
	    sql.append(" t.free_margin as freemargin,  ");
	    sql.append(" t.platform ");
	    sql.append(" from rds.fact_trans_agg_acc t ");
	    sql.append(" where 1=1   ");
	    sql.append(" and company_id = :companyId ");
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform ");
		}
		if(null != accountNo){				
			sql.append(" and account_no = :accountNo ");
		}
		if(StringUtils.isNotBlank(startDate)){				
			sql.append(" and execdate_rpt >= :startDate ");					
		}
		if(StringUtils.isNotBlank(endDate)){									
			sql.append(" and execdate_rpt < :endcloseDate ");
		}
			
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("companyId", companyId);
		if(StringUtils.isNotBlank(platform)){
			params.put("platform", platform);
		}
		if(null != accountNo){
			params.put("accountNo", accountNo);
		}
		if(StringUtils.isNotBlank(startDate)){				
			params.put("startDate", DateUtil.stringToDate(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){									
			params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
		}
		
		if(StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)){
			params.put("sort", sort);
			params.put("order", order);
		}
		
		ApiPageResult result = new ApiPageResult(ApiStatusEnum.success);
		result.setPageNumber(pageNumber);
		result.setPageSize(pageSize);
		tradeReportDao.customerdailyPageList(result, sql.toString(), params);
		return result;	
		
	}

	@Override
	public ApiResult customerdailyList(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate) throws Exception {
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>tradeDetailOpenList[keys={}]", keys);

		StringBuffer sql = new StringBuffer();
	    sql.append(" select  "); 
	    sql.append(" t.execdate_rpt as execdaterpt, ");
	    sql.append(" t.account_no as accountno,  ");
	    sql.append(" t.balance,  ");
	    sql.append(" t.equity,  ");
		sql.append(" (t.deposit_amt-previous_deposit_amt) as deposit,  ");
		sql.append(" (t.withdraw_amt-previous_withdraw_amt) as withdraw,  ");
		sql.append(" (t.profit-t.previous_profit) as balancepreviousbalance,   ");
	    sql.append(" t.floating_profit as floatingprofit,  ");
	    sql.append(" t.margin,  ");
	    sql.append(" t.free_margin as freemargin,  ");
	    sql.append(" t.platform ");
	    sql.append(" from rds.fact_trans_agg_acc t ");
	    sql.append(" where 1=1   ");
	    sql.append(" and company_id = :companyId ");
		if(StringUtils.isNotBlank(platform)){				
			sql.append(" and platform = :platform ");
		}
		if(null != accountNo){				
			sql.append(" and account_no = :accountNo ");
		}
		
		if(StringUtils.isNotBlank(startDate)){				
			sql.append(" and execdate_rpt >= :startDate ");					
		}
		if(StringUtils.isNotBlank(endDate)){									
			sql.append(" and execdate_rpt < :endcloseDate ");
		}
			
		Map<String, Object> params = new HashMap<String, Object>();	
		params.put("companyId", companyId);
		if(StringUtils.isNotBlank(platform)){
			params.put("platform", platform);
		}
		if(null != accountNo){
			params.put("accountNo", accountNo);
		}
		if(StringUtils.isNotBlank(startDate)){				
			params.put("startDate", DateUtil.stringToDate(startDate));
		}
		if(StringUtils.isNotBlank(endDate)){									
			params.put("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
		}
		
		ApiResult result = new ApiResult(ApiStatusEnum.success);
		tradeReportDao.customerdailyList(result, sql.toString(), params);			
		return result;	
		
	}

}
