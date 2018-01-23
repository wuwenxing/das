package com.gw.das.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.api.cache.ApiCacheManager;
import com.gw.das.api.common.context.Constants;
import com.gw.das.api.common.enums.CompanyEnum;
import com.gw.das.api.common.utils.DateUtil;
import com.gw.das.api.common.utils.NumberUtil;
import com.gw.das.api.service.AccountDiagnosisService;

/**
 * 账户诊断实现
 * 
 * @author darren
 *
 */
@Service
public class AccountDiagnosisServiceImpl implements AccountDiagnosisService {

	private static Logger logger = LoggerFactory.getLogger(AccountAnalyzeServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ApiCacheManager apiCacheManager;
	
    /**
     * PM HX -- 北京时间
     * FX CF -- 伦敦时间
     */
	@Override
	public List accountOverview(Integer companyId, String platform,Integer accountNo, String startDate, String endDate) throws Exception{
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>accountOverview[keys={}]", keys);
		List cache = (List) apiCacheManager.get(keys);
		if (cache == null) {
			StringBuffer sql = new StringBuffer();
			sql.append(" select ");
			sql.append(" a.account_no accountno, ");			
			sql.append(" case when a.totalprofit is null then 0 else a.totalprofit end totalprofit , ");
			sql.append(" case when a.profitnumber is null then 0 else a.profitnumber end profitnumber , ");
			sql.append(" case when a.totalloss is null then 0 else a.totalloss end totalloss , ");
			sql.append(" case when a.closeprofit is null then 0 else a.closeprofit end closeprofit , ");
			sql.append(" case when a.lossnumber is null then 0 else a.lossnumber end lossnumber , ");
			sql.append(" case when a.strongnumber is null then 0 else a.strongnumber end strongnumber , ");
			sql.append(" case when a.strongflatloss is null then 0 else a.strongflatloss end strongflatloss , ");
			sql.append(" case when a.winprofit is null then '-' else cast(a.winprofit*100 as decimal(15,2)) || '%' end winrate, ");
			sql.append(" case when b.tradesnumber is null then 0 else b.tradesnumber end tradesnumber , ");
			sql.append(" case when b.swap is null then 0 else b.swap end swap , ");
			sql.append(" case when b.actualprofit is null then 0 else b.actualprofit end actualprofit , ");
			sql.append(" case when b.netdeposit is null then 0 else b.netdeposit end netdeposit , ");
			sql.append(" case when d.maxprofitratio is null then '-' else cast(d.maxprofitratio*100 as decimal(15,2)) || '%' end maxprofitratio ");
			
			sql.append(" from ( ");
			sql.append(" select  account_no, sum(open_cnt+close_cnt) as tradesnumber, ");
			sql.append(" cast(sum(swap) as decimal(15,2)) as swap, ");
			sql.append(" cast(sum(profit+swap+commission) as decimal(15,2)) as actualprofit, ");
			sql.append(" cast(sum(deposit_amt - withdraw_amt) as decimal(15,2)) as netdeposit ");
			//sql.append(" cast(sum(case when reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 then profit end) as decimal(15,2)) closeprofit ");
			sql.append(" from rds.fact_trans_agg ");
			sql.append(" where 1=1 ");	
			sql.append(" and company_id = :companyId ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			sql.append(" and account_no = :accountNo ");
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and execdate_gmt8 >= :startDate ");
				sql.append(" and execdate_gmt8 <= :endDate ");
			}
			else {
				sql.append(" and execdate >= :startDate ");
				sql.append(" and execdate <= :endDate ");
			}
			sql.append(" group by account_no ");
			sql.append(" ) b ");
			sql.append(" left join ");				
			sql.append(" ( ");	
			sql.append(" select ");
			sql.append(" account_no, ");
			sql.append(" count(case when profit >0 then profit end ) profitnumber, ");
			sql.append(" count(case when profit <0 then profit end ) lossnumber	, ");
			sql.append(" cast(sum(case when profit >0 then profit end) as decimal(15,2)) totalprofit, ");
			sql.append(" cast(sum(case when profit <0 then profit end) as decimal(15,2)) totalloss, ");
			sql.append(" cast(sum(profit) as decimal(15,2)) closeprofit, ");
			sql.append(" count(case when reason =64 then reason end) as strongnumber, ");
			sql.append(" cast(sum(case when profit <=0 and reason = 64 then profit else 0 end) as decimal(15,2)) as strongflatloss, ");
			sql.append(" case when count(1) = 0 then null else cast(count(case when profit >0  then 1 end ) as numeric)/cast(count(1) as numeric) end  winprofit ");
			sql.append(" from rds.fact_trans_close ");
			sql.append(" WHERE 1 = 1 ");
			sql.append(" and company_id = :companyId ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			sql.append(" and account_no = :accountNo ");
			if(CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
			}else{
				sql.append(" and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
			}
			
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and closetime_gmt8 >= :startDate ");
				sql.append(" and closetime_gmt8 < :endcloseDate ");
			}
			else {
				sql.append(" and closetime >= :startDate ");
				sql.append(" and closetime < :endcloseDate ");
			}

			sql.append(" GROUP BY account_no ");
			sql.append(" ) a  on a.account_no = b.account_no ");
			sql.append(" left JOIN  ");
			sql.append(" ( ");
			sql.append(" select t1.account_no,case when t2.profit=0 or t2.profit is null then null else t1.profit/(-1*t2.profit) end as maxprofitratio from ");
			sql.append(" ( ");
			sql.append(" select account_no, sum(profit) profit from ( ");
			sql.append(" select account_no, profit from rds.fact_trans_close ");
			sql.append(" where 1=1  ");
			sql.append(" and company_id = :companyId ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			sql.append(" and account_no = :accountNo ");
			if(CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
			}else{
				sql.append(" and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
			}
			
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and closetime_gmt8 >= :startDate ");
				sql.append(" and closetime_gmt8 < :endcloseDate ");
			}
			else {
				sql.append(" and closetime >= :startDate ");
				sql.append(" and closetime < :endcloseDate ");
			}
			sql.append(" and profit >0 ");
			sql.append(" order by  profit desc limit 3) a group by account_no ");
			sql.append(" ) t1 join  ( ");
			sql.append(" select account_no, sum(profit) profit from ( ");
			sql.append(" select account_no, profit from rds.fact_trans_close ");
			sql.append(" where 1=1	 ");	
			sql.append(" and company_id = :companyId ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			sql.append(" and account_no = :accountNo ");
			if(CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
			}else{
				sql.append(" and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
			}
			
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and closetime_gmt8 >= :startDate ");
				sql.append(" and closetime_gmt8 < :endcloseDate ");
			}
			else {
				sql.append(" and closetime >= :startDate ");
				sql.append(" and closetime < :endcloseDate ");
			}
			sql.append(" and profit <0  ");
			sql.append(" order by  profit limit 3) b group by account_no ");
			sql.append(" ) t2 on t1.account_no = t2.account_no ");
			sql.append(" ) d on a.account_no = d.account_no ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					                    .setParameter("companyId", companyId)
					                    .setParameter("accountNo", accountNo)
					                    .setParameter("startDate", DateUtil.stringToDate(startDate))
					                    .setParameter("endDate", DateUtil.stringToDate(endDate))
					                    .setParameter("endcloseDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
			if(StringUtils.isNotBlank(platform)){
				query.setParameter("platform", platform);
			}
			List<Map> list = (List<Map>)query.getResultList();
			
			//单独查询浮动盈亏
			StringBuffer sqlFloating = new StringBuffer();
			sqlFloating.append(" select ");
			sqlFloating.append(" account_no accountno, cast(sum(floating_profit + commission_daily + swap_daily) as decimal(15,2)) as floatingprofit ");
			sqlFloating.append(" from rds.fact_trans_agg_acc  ");
			sqlFloating.append(" where 1=1 ");
			sqlFloating.append(" and company_id = :companyId ");
			if(StringUtils.isNotBlank(platform)){				
				sqlFloating.append(" and platform = :platform ");
			}
			sqlFloating.append(" and account_no = :accountNo ");
			sqlFloating.append(" and execdate_rpt = :endDate ");							
			sqlFloating.append(" GROUP BY account_no  ");
			Query queryFloating = em.createNativeQuery(sqlFloating.toString());
			queryFloating.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					                    .setParameter("companyId", companyId)
					                    .setParameter("accountNo", accountNo)
					                    .setParameter("endDate", DateUtil.stringToDate(endDate));
			if(StringUtils.isNotBlank(platform)){
				queryFloating.setParameter("platform", platform);
			}
			List<Map> listFloating = (List<Map>)queryFloating.getResultList();
			
			if(null != list && list.size() >0){
				for (int i = 0; i < list.size(); i++) {
					Map<String,String> map = list.get(i);
					if(null != listFloating && listFloating.size() >0){
						list.get(i).put("floatingprofit", String.valueOf(listFloating.get(0).get("floatingprofit")));
					}else{
						list.get(i).put("floatingprofit", "0");
					}
				}
				apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
				return list;	
			}else{
				List<Map> newList = new ArrayList<Map>();
				Map<String,String> newMap = new HashMap<String,String>();
				newMap.put("accountno", String.valueOf(accountNo));
				newMap.put("tradesnumber", "0");
				newMap.put("totalprofit", "0");
				newMap.put("profitnumber", "0");
				newMap.put("totalloss", "0");
				newMap.put("lossnumber", "0");
				newMap.put("closeprofit", "0");
				newMap.put("swap", "0");
				newMap.put("actualprofit", "0");
				newMap.put("strongnumber", "0");
				newMap.put("strongflatloss", "0");
				newMap.put("netdeposit", "0");				
				if(null != listFloating && listFloating.size() >0){
					newMap.put("floatingprofit", String.valueOf(listFloating.get(0).get("floatingprofit")));
				}else{
					newMap.put("floatingprofit", "0");
				}
				newMap.put("maxprofitratio", "0.00%");
				newMap.put("winrate", "0.00%");				
				newList.add(newMap);
				apiCacheManager.put(keys, newList,1 * 60 * 1000);
				return newList;	
			}
		  } else {
			return cache;
		}
	}

    /**
     * PM HX -- 北京时间
     * FX CF -- 伦敦时间
     */
	@Override
	public List profitStatistics(Integer companyId, String platform,Integer accountNo, String startDate, String endDate) throws Exception{
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>profitStatistics[keys={}]", keys);
		
		List cache = (List) apiCacheManager.get(keys);
		if (cache == null) {
			StringBuffer sql = new StringBuffer();
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" select execdate_gmt8 execdate, account_no accountno, cast(sum(profit + commission + swap) as decimal(15,2)) profit from rds.fact_trans_agg  ");
			}else{
				sql.append(" select execdate, account_no accountno, cast(sum(profit + commission + swap) as decimal(15,2)) profit from rds.fact_trans_agg  ");
			}
			
			sql.append(" WHERE 1 = 1 ");
			sql.append(" and company_id = :companyId ");
			sql.append(" and account_no = :accountNo ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}				
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and execdate_gmt8 >= :startDate ");
				sql.append(" and execdate_gmt8 <= :endDate ");
				sql.append(" GROUP BY execdate_gmt8,account_no ");
			}
			else {
				sql.append(" and execdate >= :startDate ");
				sql.append(" and execdate <= :endDate ");
				sql.append(" GROUP BY execdate,account_no ");
			}			
			Query query = em.createNativeQuery(sql.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					                    .setParameter("companyId", companyId)
					                    .setParameter("accountNo", accountNo)
					                    .setParameter("startDate", DateUtil.stringToDate(startDate))
					                    .setParameter("endDate", DateUtil.stringToDate(endDate));
			if(StringUtils.isNotBlank(platform)){
				query.setParameter("platform", platform);
			}
			List<Map> list = (List<Map>)query.getResultList();
			
			int daysBetween = DateUtil.daysBetween(DateUtil.stringToDate(startDate), DateUtil.stringToDate(endDate));//计算两个日期之间的天数
			String newDate = startDate;
			for (int i = 0; i <= daysBetween; i++) {					
				if(i!=0){
					newDate = DateUtil.formatDateToString(DateUtil.addDays(DateUtil.stringToDate(startDate), i));
				}
				boolean flag = false;
				for (int j = 0; j < list.size(); j++) {
					Map map = list.get(j);
					String execdate = String.valueOf(map.get("execdate"));
					if(newDate.equals(execdate)){
						flag = true;
						break;
					}
				}
				if(!flag){
					Map<String,String> newProfitStatisticsMap = new HashMap<String,String>();
					newProfitStatisticsMap.put("accountno", String.valueOf(accountNo));
					newProfitStatisticsMap.put("execdate", newDate);
					newProfitStatisticsMap.put("profit", "0");
					newProfitStatisticsMap.put("floatingprofit", "0");
					list.add(i,newProfitStatisticsMap);					
				}
			}			
			
			for (int i = 0; i < list.size(); i++) {						
				if(i == 0){
					list.get(i).put("cumulativeprofit", list.get(i).get("profit"));							
					continue;
				}else{
					Map  beforTrans = list.get(i-1);
					Map  trans = list.get(i);
					Double brforProfit = Double.valueOf(String.valueOf(beforTrans.get("cumulativeprofit")));
					Double profit = Double.valueOf(String.valueOf(trans.get("profit"))) ;	
					Double cumulativeProfit = 0D;
					if(profit != null){								
						 cumulativeProfit = NumberUtil.m2(profit + brforProfit);	
					}else{
						cumulativeProfit = NumberUtil.m2(brforProfit);	
					}
					list.get(i).put("cumulativeprofit", cumulativeProfit);		
				}
			}
			
			//单独查询浮动盈亏
			StringBuffer sqlFloating = new StringBuffer();
			sqlFloating.append(" select execdate_rpt execdate,account_no accountno, case when sum(floating_profit + commission_daily + swap_daily) = 0 then 0 else  cast(sum(floating_profit + commission_daily + swap_daily) as decimal(15,2)) end floatingprofit from rds.fact_trans_agg_acc ");
			sqlFloating.append(" where 1 = 1 ");
			sqlFloating.append(" and company_id = :companyId ");
			sqlFloating.append(" and account_no = :accountNo ");
			if(StringUtils.isNotBlank(platform)){				
				sqlFloating.append(" and platform = :platform ");
			}
			sqlFloating.append(" and execdate_rpt >= :startDate ");
			sqlFloating.append(" and execdate_rpt <= :endDate ");
			sqlFloating.append(" GROUP BY execdate_rpt,account_no ");
			Query queryFloating = em.createNativeQuery(sqlFloating.toString());
			queryFloating.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					                    .setParameter("companyId", companyId)
					                    .setParameter("accountNo", accountNo)
					                    .setParameter("startDate", DateUtil.stringToDate(startDate))
					                    .setParameter("endDate", DateUtil.stringToDate(endDate));
			if(StringUtils.isNotBlank(platform)){
				queryFloating.setParameter("platform", platform);
			}
			List<Map> listFloating = (List<Map>)queryFloating.getResultList();			
			for (int i = 0; i < list.size(); i++) {
				Map<String,String> map = list.get(i);
				String accountno = String.valueOf(map.get("accountno"));
				String execdate = String.valueOf(map.get("execdate"));
				if(null != listFloating && listFloating.size() >0){
					boolean flag = false;
					int index = 0;
					for (int j = 0; j < listFloating.size(); j++) {
						Map<String,String> mapFloating = listFloating.get(j);
						String accountnoFloating = String.valueOf(mapFloating.get("accountno"));
						String execdateFloating = String.valueOf(mapFloating.get("execdate"));
						if(accountno.equals(accountnoFloating) && execdate.equals(execdateFloating)){
							flag = true;
							index = j;
							break;
						}
					}
					if(flag){
						list.get(i).put("floatingprofit", String.valueOf(listFloating.get(index).get("floatingprofit")));
					}else{
						list.get(i).put("floatingprofit",0);
					}
				}else{
					list.get(i).put("floatingprofit", "0");
				}
			}
			apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
			return list;	
		  } else {
			return cache;
		}
	}

    /**
     * PM HX -- 北京时间
     * FX CF -- 伦敦时间
     */
	@Override
	public List profitAnalysis(Integer companyId, String platform,Integer accountNo, String startDate, String endDate) throws Exception{
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>profitAnalysis[keys={}]", keys);
		List cache = (List) apiCacheManager.get(keys);
		if (cache == null) {
			StringBuffer sql = new StringBuffer();
			
			sql.append(" select a.accountno , ");
			sql.append(" case when a.tradeprofitRatio is null then '-' else cast(a.tradeprofitRatio *100 as decimal(15,2)) || '%' end as tradeprofitratio , ");
			sql.append(" case when a.avgProfitLossRatio is null then '-' else cast(a.avgProfitLossRatio *100 as decimal(15,2)) || '%' end as avgprofitlossratio , ");
			sql.append(" cast((case when a.profitRatio is null then 0 else a.profitRatio end) * 100 as decimal(15,2)) || '%' as profitratio , ");
			sql.append(" case when a.buyNumber is null then 0 else a.buyNumber end as buynumber, ");
			sql.append(" case when a.sellNumber is null then 0 else a.sellNumber end as sellnumber, ");
			sql.append(" case when a.buyProfitRatio is null then '-' else cast(a.buyProfitRatio *100 as decimal(15,2)) || '%' end as buyprofitratio , ");
			sql.append(" case when a.sellProfitRatio is null then '-' else cast(a.sellProfitRatio *100 as decimal(15,2)) || '%' end as sellprofitratio , ");
			sql.append(" case when a.buyProfitNumber is null then 0 else a.buyProfitNumber end as buyprofitnumber, ");
			sql.append(" case when a.sellProfitNumber is null then 0 else a.sellProfitNumber end as sellprofitnumber, ");
			sql.append(" case when a.buyLossNumber is null then 0 else a.buyLossNumber end as buylossnumber, ");
			sql.append(" case when a.sellLossNumber is null then 0 else a.sellLossNumber end as selllossnumber, ");
			sql.append(" case when a.buyTallProfit is null then 0 else a.buyTallProfit end as buytallprofit, ");			
			sql.append(" case when a.sellTallProfit is null then 0 else a.sellTallProfit end as selltallprofit, ");
			sql.append(" a.positionIntervalTime as positionintervaltime, ");
			sql.append(" a.lossIntervalTime as lossintervaltime ");
			sql.append(" from ( ");
			
			sql.append(" select ");
			sql.append(" account_no accountno, ");
/*			sql.append(" CAST(sum(case when profit >0 then profit end)/sum(case when profit <0 then profit end) * 100 as decimal(15,2)) || '%' as tradeprofitRatio, ");
			sql.append(" CAST((sum(case when profit >0  then profit end)/count(case when profit >0  then 1 end ))/(sum(case when profit <0  then profit end)/count(case when profit <0  then 1 end )) *100 as decimal(15,2)) || '%' avgProfitLossRatio, ");
			sql.append(" cast(cast(count(case when profit >0  then 1 end ) as numeric)/cast(count(1) as numeric) * 100 as decimal(15,2)) || '%' profitRatio, ");
			sql.append(" count(case when direction = '1' then 1 end) buyNumber, ");
			sql.append(" count(case when direction = '2' then 1 end) sellNumber, ");
			sql.append(" cast(count(case when direction = '1' and profit >0  then 1 end )/count(case when direction = '1' then 1 end) *100 as decimal(15,2)) || '%' buyProfitRatio, ");
			sql.append(" cast(count(case when direction = '2' and profit >0  then 1 end )/count(case when direction = '2' then 1 end) *100 as decimal(15,2)) || '%' sellProfitRatio, ");
			sql.append(" count(case when direction = '1' and profit >0 then 1 end) buyProfitNumber, ");
			sql.append(" count(case when direction = '2' and profit >0 then 1 end) sellProfitNumber, ");
			sql.append(" count(case when direction = '1' and profit <0 then 1 end) buyLossNumber, ");
			sql.append(" count(case when direction = '2' and profit <0 then 1 end) sellLossNumber,  ");
			sql.append(" cast(sum(case when direction = '1' then profit end) as decimal(15,2)) buyTallProfit, ");
			sql.append(" cast(sum(case when direction = '2' then profit end) as decimal(15,2)) sellTallProfit, ");*/
			
			sql.append(" case when sum(case when profit <0 then profit end)=0 then null else sum(case when profit >0 then profit end)/sum(case when profit <0 then profit end) end as tradeprofitRatio, ");
			sql.append(" case when count(case when profit <0  then 1 end )=0 then null else (case when count(case when profit >0  then 1 end )=0 then 0 else sum(case when profit >0  then profit end)/count(case when profit >0  then 1 end ) end)/(sum(case when profit <0  then profit end)/count(case when profit <0  then 1 end )) end avgProfitLossRatio, ");
			sql.append(" cast(count(case when profit >0  then 1 end ) as numeric)/cast(count(1) as numeric)  profitRatio, ");
			sql.append(" count(case when direction = '1' then 1 end) buyNumber, ");
			sql.append(" count(case when direction = '2' then 1 end) sellNumber, ");
			sql.append(" case when count(case when direction = '1' then 1 end) = 0 then null else count(case when direction = '1' and profit >0  then 1 end )/count(case when direction = '1' then 1 end) end buyProfitRatio, ");
			sql.append(" case when count(case when direction = '2' then 1 end) = 0 then null else count(case when direction = '2' and profit >0  then 1 end )/count(case when direction = '2' then 1 end) end sellProfitRatio, ");
			sql.append(" count(case when direction = '1' and profit >0 then 1 end) buyProfitNumber, ");
			sql.append(" count(case when direction = '2' and profit >0 then 1 end) sellProfitNumber, ");
			sql.append(" count(case when direction = '1' and profit <0 then 1 end) buyLossNumber, ");
			sql.append(" count(case when direction = '2' and profit <0 then 1 end) sellLossNumber,  ");
			sql.append(" cast(sum(case when direction = '1' then profit end) as decimal(15,2)) buyTallProfit, ");
			sql.append(" cast(sum(case when direction = '2' then profit end) as decimal(15,2)) sellTallProfit, ");
			
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" cast(SUM (CASE WHEN profit > 0 THEN ");
				sql.append(" date_part('minutes',CAST (closetime_gmt8 AS TIMESTAMP) - CAST (opentime_gmt8 AS TIMESTAMP)) + ");
				sql.append(" date_part('hour',CAST (closetime_gmt8 AS TIMESTAMP) - CAST (opentime_gmt8 AS TIMESTAMP)) * 60 + ");
				sql.append(" date_part('day',CAST (closetime_gmt8 AS TIMESTAMP) - CAST (opentime_gmt8 AS TIMESTAMP)) * 24 * 60 END ");
				sql.append(" ) / COUNT (CASE WHEN profit > 0 THEN 1 END) as decimal(15,0)) positionIntervalTime, ");
				
				sql.append(" cast(SUM (CASE WHEN profit < 0 THEN ");
				sql.append(" date_part('minutes',CAST (closetime_gmt8 AS TIMESTAMP) - CAST (opentime_gmt8 AS TIMESTAMP)) +  ");
				sql.append(" date_part('hour',	CAST (closetime_gmt8 AS TIMESTAMP) - CAST (opentime_gmt8 AS TIMESTAMP)) * 60 +  ");
				sql.append(" date_part('day',CAST (closetime_gmt8 AS TIMESTAMP) - CAST (opentime_gmt8 AS TIMESTAMP)) * 24 * 60 END ");
				sql.append(" ) / COUNT (CASE WHEN profit < 0 THEN 1 END) as decimal(15,0)) lossIntervalTime ");
			}else{
				sql.append(" cast(SUM (CASE WHEN profit > 0 THEN ");
				sql.append(" date_part('minutes',CAST (closetime AS TIMESTAMP) - CAST (opentime AS TIMESTAMP)) + ");
				sql.append(" date_part('hour',CAST (closetime AS TIMESTAMP) - CAST (opentime AS TIMESTAMP)) * 60 + ");
				sql.append(" date_part('day',CAST (closetime AS TIMESTAMP) - CAST (opentime AS TIMESTAMP)) * 24 * 60 END ");
				sql.append(" ) / COUNT (CASE WHEN profit > 0 THEN 1 END) as decimal(15,0)) positionIntervalTime, ");
				
				sql.append(" cast(SUM (CASE WHEN profit < 0 THEN ");
				sql.append(" date_part('minutes',CAST (closetime AS TIMESTAMP) - CAST (opentime AS TIMESTAMP)) +  ");
				sql.append(" date_part('hour',	CAST (closetime AS TIMESTAMP) - CAST (opentime AS TIMESTAMP)) * 60 +  ");
				sql.append(" date_part('day',CAST (closetime AS TIMESTAMP) - CAST (opentime AS TIMESTAMP)) * 24 * 60 END ");
				sql.append(" ) / COUNT (CASE WHEN profit < 0 THEN 1 END) as decimal(15,0)) lossIntervalTime ");
			}			
			sql.append(" from rds.fact_trans_close  ");
			sql.append(" WHERE 1 = 1 ");
			sql.append(" and company_id = :companyId ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			sql.append(" and account_no = :accountNo ");
			//sql.append(" and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and closetime_gmt8 >= :startDate ");
				sql.append(" and closetime_gmt8 < :endDate ");
			}
			else {
				sql.append(" and closetime >= :startDate ");
				sql.append(" and closetime < :endDate ");
			}
			sql.append(" GROUP BY account_no ");
			sql.append(" ) a ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					                    .setParameter("companyId", companyId)
					                    .setParameter("accountNo", accountNo)
					                    .setParameter("startDate", DateUtil.stringToDate(startDate))
					                    .setParameter("endDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
			if(StringUtils.isNotBlank(platform)){
				query.setParameter("platform", platform);
			}
			List list = query.getResultList();
			apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
			return list;
	  } else {
		return cache;
	 }
	}

	@Override
	public List transactionProductStatistics(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate) throws Exception {
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>transactionProductStatistics[keys={}]", keys);
		List cache = (List) apiCacheManager.get(keys);
		if (cache == null) {
			StringBuffer sql = new StringBuffer();
			
		    sql.append(" select t.symbolName,  ");
		    sql.append(" cast(t.volume as decimal(15,2)) volume,  ");
		    sql.append(" t.closeCnt,  ");
		    sql.append(" cast(t.profit as decimal(15,2)) profit,  ");
		    sql.append(" case when t.trade_margin !=0 then cast(cast(t.profit as numeric)/cast(t.trade_margin as numeric) * 100 as decimal(15,2)) || '%' else '-' end profitRate,  ");
		    sql.append(" case when t.tallProfitCount !=0 then cast(CAST(t.profitCount as numeric)/CAST(t.tallProfitCount as numeric) * 100 as decimal(15,2)) || '%' else '-' end prodWinrate  ");
		    sql.append(" from ");
		    sql.append(" ( ");
		    sql.append(" select  ");
		    sql.append(" symbol_name symbolName, ");
		    sql.append(" sum(close_volume+open_volume) volume,  ");
		    sql.append(" sum(close_cnt) as closeCnt,  ");
		    if( CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
		    	sql.append(" sum(case when reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 then profit end) as profit,  ");
			    sql.append(" count(case when profit >0 and(reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162)  then 1 end ) as profitCount,  ");
			    sql.append(" count(case when reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 then 1 end ) as tallProfitCount,  ");
		    }else{
		    	sql.append(" sum(case when reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 then profit end) as profit,  ");
			    sql.append(" count(case when profit >0 and(reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162)  then 1 end ) as profitCount,  ");
			    sql.append(" count(case when reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 then 1 end ) as tallProfitCount,  ");
		    }
		    
		    sql.append(" sum(trade_margin) as trade_margin ");
		    sql.append(" from rds.fact_trans_agg ");
		    sql.append(" where 1=1  ");
			sql.append(" and company_id = :companyId ");
			sql.append(" and account_no = :accountNo ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and execdate_gmt8 >= :startDate ");
				sql.append(" and execdate_gmt8 <= :endDate ");
			}
			else {
				sql.append(" and execdate >= :startDate ");
				sql.append(" and execdate <= :endDate ");
			}
		    sql.append(" GROUP BY symbol_name  ");
		    sql.append(" ORDER BY volume desc  ");
		    sql.append(" ) t  ");
			
			Query query = em.createNativeQuery(sql.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					                    .setParameter("companyId", companyId)
					                    .setParameter("accountNo", accountNo)
					                    .setParameter("startDate", DateUtil.stringToDate(startDate))
					                    .setParameter("endDate", DateUtil.stringToDate(endDate));
			if(StringUtils.isNotBlank(platform)){
				query.setParameter("platform", platform);
			}
			List list = query.getResultList();
			apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
			return list;    
	      } else {
		return cache;
	  }
	}

	@Override
	public List positionTimeIntervalStatistics(Integer companyId, String platform, Integer accountNo, String startDate,
			String endDate) throws Exception {
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform +accountNo+ startDate + endDate;
		logger.debug("==>>positionTimeIntervalStatistics[keys={}]", keys);
		List cache = (List) apiCacheManager.get(keys);
		if (cache == null) {
			StringBuffer sql = new StringBuffer();
		    sql.append(" select  t.timeInterval, ");
		    sql.append(" cast(sum(case when profit>0 then profit end ) as decimal(15,2)) profit,  ");
		    sql.append(" cast(sum(case when profit<0 then profit end ) as decimal(15,2)) loss, ");
		    sql.append(" count(case when profit >0 then 1 end) profitCount, ");
		    sql.append(" count(case when profit <0 then 1 end) lossCount ");
		    sql.append(" from ");
		    sql.append(" ( ");
		    sql.append(" select  ");
		    sql.append(" case  ");
		    sql.append(" when  position_interval_time >=0 and position_interval_time <5  then '0min-5min'  ");
		    sql.append(" when  position_interval_time >=5 and position_interval_time <30  then '5min-30min'  ");
		    sql.append(" when  position_interval_time >=30 and position_interval_time <120  then '30min-2hr'  ");
		    sql.append(" when  position_interval_time >=120 and position_interval_time <300  then '2hr-5hr' ");
		    sql.append(" when  position_interval_time >=300 and position_interval_time <720  then '5hr-12hr'  ");
		    sql.append(" when  position_interval_time >=720 and position_interval_time <1440  then '12hr-24hr' ");
		    sql.append(" when  position_interval_time >=1400 then '>=24hr' ");
		    sql.append(" end timeInterval, ");
		    sql.append(" profit ");
		    sql.append(" from rds.fact_trans_agg ");
		    sql.append(" where 1=1  ");
			sql.append(" and company_id = :companyId ");
			sql.append(" and account_no = :accountNo ");
			if(StringUtils.isNotBlank(platform)){				
				sql.append(" and platform = :platform ");
			}
			if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				sql.append(" and execdate_gmt8 >= :startDate ");
				sql.append(" and execdate_gmt8 <= :endDate ");
			}
			else {
				sql.append(" and execdate >= :startDate ");
				sql.append(" and execdate <= :endDate ");
			}
			if(CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
				 sql.append(" and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 )  ");
			}else{
				 sql.append(" and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 )  ");
			}
		   
		    sql.append(" ) t GROUP BY t.timeInterval  ");
		    
			Query query = em.createNativeQuery(sql.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
					                    .setParameter("companyId", companyId)
					                    .setParameter("accountNo", accountNo)
					                    .setParameter("startDate", DateUtil.stringToDate(startDate))
					                    .setParameter("endDate", DateUtil.stringToDate(endDate));
			if(StringUtils.isNotBlank(platform)){
				query.setParameter("platform", platform);
			}
			List list = query.getResultList();
			apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
			return list;    
	   } else {
		return cache;
	  }				
	}

}
