package com.gw.das.api.service.impl;

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
import com.gw.das.api.service.AccountAnalyzeService;

/**
 * 账户诊断相关实现
 * 
 * @author wayne
 */
@Service
public class AccountAnalyzeServiceImpl implements AccountAnalyzeService {

	private static Logger logger = LoggerFactory.getLogger(AccountAnalyzeServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ApiCacheManager apiCacheManager;

	/**
	 * 获取保证金水平走势
	 * 
	 * @Cacheable(value = "marginLevel", sync = true)
	 * @CacheEvict(value = "marginLevel", beforeInvocation =
	 *                   true)beforeInvocation=true时，Spring会在调用该方法之前清除缓存中的指定元素
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List marginLevel(String companyId, String platform, String accountNo, String startDate, String endDate)throws Exception {
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform + accountNo + startDate + endDate;
		logger.debug("==>>marginLevel[keys={}]", keys);
		List o = (List) apiCacheManager.get(keys);
		if (o == null) {
					StringBuffer sql = new StringBuffer();
					sql.append(" SELECT ");
					sql.append(" 	A .execdate_rpt execdateRpt, ");// 报表时间
					sql.append(" 	A .company_id companyId, ");// 公司id
					sql.append(" 	A .platform platform, ");// 平台
					sql.append(" 	A .account_no accountNo, ");// 账号
					sql.append(" 	A .currency, ");// 币种
					sql.append(" 	SUM ((case when A .floating_profit is null then 0 else A .floating_profit end)  + ");
					sql.append("    (case when A .commission_daily is null then 0 else A .commission_daily end) + ");
					sql.append("    (case when A .swap_daily is null then 0 else A .swap_daily end)) as floatingProfit, ");// 浮动盈亏 = 浮动盈亏 +日结单的佣金+日结单的利息
					sql.append(" 	SUM (A .balance) AS balance, ");// 结余
					sql.append(" 	SUM (A .margin) AS margin, ");// 保证金
					sql.append("   case when SUM(A .margin) > 0 then ");
					sql.append(" 		cast((SUM(A .equity) / SUM (A .margin))*100 as decimal(15,2)) || ''  ");
					sql.append("   else '-' end AS marginRatio "); //保证金水平= 净值\保证金（margin）
					sql.append(" FROM ");
					sql.append(" 	rds.fact_trans_agg_acc A ");
					sql.append(" WHERE ");
					sql.append(" A .company_id = :companyId ");
					if(StringUtils.isNotBlank(platform)){
						sql.append(" AND A .platform = :platform ");
					}
					sql.append(" AND A .account_no = :accountNo ");
					sql.append(" AND A .execdate_rpt BETWEEN :startDate AND :endDate ");
					sql.append(" GROUP BY ");
					sql.append(" 	execdate_rpt, ");
					sql.append(" 	company_id, ");
					sql.append(" 	platform, ");
					sql.append("    account_no, ");
					sql.append(" 	currency ");
					sql.append(" ORDER BY ");
					//sql.append(" 	execdate_rpt DESC ");
					sql.append(" 	execdate_rpt ");
					Query query = em.createNativeQuery(sql.toString());
					query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
							.setParameter("companyId", Long.parseLong(companyId))
							.setParameter("accountNo", Long.parseLong(accountNo))
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
							String execdate = String.valueOf(map.get("execdaterpt"));
							if(newDate.equals(execdate)){
								flag = true;
								break;
							}
						}
						if(!flag){
							Map<String,String> newProfitStatisticsMap = new HashMap<String,String>();
							newProfitStatisticsMap.put("companyid", companyId);
							newProfitStatisticsMap.put("accountno", String.valueOf(accountNo));
							newProfitStatisticsMap.put("platform", platform);
							newProfitStatisticsMap.put("currency", "");
							newProfitStatisticsMap.put("balance", "0");							
							newProfitStatisticsMap.put("floatingprofit", "0");
							newProfitStatisticsMap.put("margin", "0");
							newProfitStatisticsMap.put("marginratio", "0");
							newProfitStatisticsMap.put("execdaterpt", newDate);
							list.add(i,newProfitStatisticsMap);					
						}
					}	
					
					apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
					return list;
				} else {
					return o;
				}					
	}

	/**
	 * 获取3笔最大盈利单与亏损单的记录信息
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @param type
	 *            1盈利单，0亏损单
	 * @return
	 */
	public List topProfitAndLoss(String companyId, String platform, String accountNo, String startDate, String endDate,
			int type) {
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform + accountNo + startDate + endDate
				+ type;
		logger.debug("==>>topProfitAndLoss[keys={}]", keys);
		List o = (List) apiCacheManager.get(keys);
		if (o == null) {
					StringBuffer sql = new StringBuffer();
					sql.append(" select a.companyId,a.platform,a.accountId,a.accountNo,a.currency,a.dealId,a.symbolShortname,a.positionId, ");
					sql.append(" a.direction,a.opentime,a.closetime,a.openprice,a.closeprice,a.sl,a.tp,a.ykds,a.volume,a.commission,a.swap,a.jyk,a.reason ,b.digits  ");
					sql.append(" from ( ");					
					sql.append(" SELECT ");
					sql.append(" t1.symbol_name, ");
					sql.append(" 	t1.company_id companyId, ");// 公司
					sql.append(" 	t1.platform platform, ");// 平台
					sql.append(" 	t1.account_id accountId, ");// 账号id
					sql.append(" 	t1.account_no accountNo, ");// 账号no
					sql.append(" 	t1.currency, ");// 币种
					sql.append("    t1.deal_id dealId, ");// 成交订单号
					sql.append("    t1.symbol_shortname symbolShortname, ");// 交易产品名称
					sql.append("    t1.position_id positionId, ");// 持仓单号
					sql.append(" 	t1.direction, ");// 买卖方向
					if(companyId.equals(CompanyEnum.fx.getLabelKey()) || companyId.equals(CompanyEnum.cf.getLabelKey())){
						sql.append(" to_char(t1.opentime,'YYYY-MM-DD HH24:MI:SS') as opentime, ");// 开仓时间
						sql.append(" to_char(t1.closetime,'YYYY-MM-DD HH24:MI:SS') as closetime, ");// 平仓时间
					} else {
						sql.append(" to_char(t1.opentime_gmt8,'YYYY-MM-DD HH24:MI:SS') as opentime, ");// 开仓时间
						sql.append(" to_char(t1.closetime_gmt8,'YYYY-MM-DD HH24:MI:SS') as closetime, ");// 平仓时间
					}
					//sql.append(" 	case when t1.company_id = 2 or t1.company_id = 3 then cast(t1.openprice as decimal(15,2)) else cast(t1.openprice as decimal(15,5)) end   openprice, ");// 开仓价格
					//sql.append(" 	case when t1.company_id = 2 or t1.company_id = 3 then cast(t1.closeprice as decimal(15,2)) else cast(t1.closeprice as decimal(15,5)) end  closeprice, ");// 平仓价格
					sql.append(" 	t1.openprice  openprice, ");// 开仓价格
					sql.append(" 	t1.closeprice  closeprice, ");// 平仓价格				
					sql.append("    case when t1.company_id = 2 or t1.company_id = 3 then cast(t1.sl as decimal(15,2)) else cast(t1.sl as decimal(15,5)) end  sl , ");// 止损设置
					sql.append("    case when t1.company_id = 2 or t1.company_id = 3 then cast(t1.tp as decimal(15,2)) else cast(t1.tp as decimal(15,5)) end  tp, ");// 止盈设置
					sql.append("    cast((t1.openprice - t1.closeprice)*1000 as decimal(15,0)) as ykds, ");// 盈亏点数
					sql.append(" 	cast(t1.volume as decimal(15,2)) volume, ");// 手数
					sql.append(" 	cast(t1.commission as decimal(15,2)) as commission, ");// 佣金
					sql.append(" 	cast(t1.swap as decimal(15,2)) as swap, ");// 过夜利息					
					sql.append(" 	cast((t1.profit + t1.commission + t1.swap) as decimal(15,2)) AS jyk, ");// 净盈亏
					sql.append("   case when t1.reason = 8 then '市价平仓' ");
					sql.append("   when t1.reason = 16 then '止损平仓' ");
					sql.append("   when t1.reason = 17 then '自动替换' ");
					sql.append("   when t1.reason = 32 then '止盈平仓' ");
					sql.append("   when t1.reason = 64 then '强平' ");
					sql.append("   when t1.reason = 128 then '部分平仓' ");
					sql.append("   when t1.reason = 160 then '到期Expired' ");
					sql.append("   when t1.reason = 161 then 'admin平仓' ");
					sql.append("   when t1.reason = 162 then '结算平仓Settle_close' ");
					sql.append("   else '' end reason ");// 平仓类型
					// 8市价平仓；16止损平仓；32止盈平仓；64强平；128部分平仓；160到期Expired；161admin平仓；162结算平仓Settle_close
					sql.append(" FROM ");
					sql.append(" 	rds.fact_trans_close t1 ");
					sql.append(" WHERE ");
					sql.append(" 	t1.company_id = :companyId ");
					if(StringUtils.isNotBlank(platform)){
						sql.append(" AND t1.platform = :platform ");
					}
					sql.append(" AND t1.account_no = :accountNo ");
					if(type == 1){
						sql.append(" AND (t1.profit + t1.commission + t1.swap) >0 ");
					}else{
						sql.append(" AND (t1.profit + t1.commission + t1.swap) <0 ");
					}
					if(CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
						sql.append(" and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
					}else{
						sql.append(" and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
					}
					
					if(companyId.equals(CompanyEnum.fx.getLabelKey()) || companyId.equals(CompanyEnum.cf.getLabelKey())){
						sql.append(" AND t1.closetime ");
					} else {
						sql.append(" AND t1.closetime_gmt8 ");
					}
					sql.append("  BETWEEN :startDate AND :endDate");
					sql.append(" ORDER BY jyk ");
					if (type == 1) {
						// 前几笔盈利单
						sql.append(" DESC ");
					} else {
						// 前几笔亏损单
						sql.append(" ASC ");
					}
					sql.append(" limit 3 ");
					sql.append(" ) a left join (select digits,symbol_name from rds.dim_symbol ) b on a.symbol_name = b.symbol_name ");
					
					Query query = em.createNativeQuery(sql.toString());
					query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
							.setParameter("companyId", Long.parseLong(companyId))
							.setParameter("accountNo", Long.parseLong(accountNo))
							.setParameter("startDate", DateUtil.stringToDate(startDate))
							.setParameter("endDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
					if(StringUtils.isNotBlank(platform)){
						query.setParameter("platform", platform);
					}
					List list = query.getResultList();
					apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
					return list;
				} else {
					return o;
				}			
	}

	/**
	 * 平仓单的盈亏金额与持仓时间
	 * 
	 * @param companyId
	 * @param platform
	 * @param accountNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List profitAndLossAmountAndTime(String companyId, String platform, String accountNo, String startDate,
			String endDate) {
		// 获得当前类名、方法名
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + companyId + platform + accountNo + startDate + endDate;
		logger.debug("==>>profitAndLossAmountAndTime[keys={}]", keys);
		List o = (List) apiCacheManager.get(keys);
		if (o == null) {
					StringBuffer sql = new StringBuffer();
					sql.append(" SELECT ");
					if(companyId.equals(CompanyEnum.fx.getLabelKey()) || companyId.equals(CompanyEnum.cf.getLabelKey())){
						sql.append(" to_char(t1.closetime,'YYYY-MM-DD HH24:MI:SS') as closetime, ");// 平仓时间
					} else {
						sql.append(" to_char(t1.closetime_gmt8,'YYYY-MM-DD HH24:MI:SS')	as closetime, ");// 平仓时间
					}
					sql.append(" 	t1.company_id companyId, ");// 公司id
					sql.append(" 	t1.platform platform, ");// 平台
					sql.append(" 	t1.account_id accountId, ");// 账号id
					sql.append(" 	t1.account_no accountNo, ");// 账号no
					sql.append(" 	t1.currency, ");// 币种
					sql.append(" 	((case when t1.profit is null then 0 else t1.profit end) + (case when t1.commission is null then 0 else t1.commission end ) + (case when t1.swap is null then 0 else t1.swap end)) AS jyk,  ");// 净盈亏
					//sql.append(" CAST (date_part('minutes', cast(closetime as timestamp) - cast(opentime as timestamp)) AS DECIMAL (15, 2)) AS positionIntervalTime ");//持仓时间=平仓时间-开仓时间,粒度分钟
					sql.append(" date_part('day', CAST (closetime AS TIMESTAMP)-CAST (opentime AS TIMESTAMP)) * 24 * 60 + date_part('hour', CAST (closetime AS TIMESTAMP)-CAST (opentime AS TIMESTAMP)) * 60 + date_part('minute', CAST (closetime AS TIMESTAMP)-CAST (opentime AS TIMESTAMP)) as positionIntervalTime ");//持仓时间=平仓时间-开仓时间,粒度分钟
					
					sql.append(" FROM ");
					sql.append(" 	rds.fact_trans_close t1 ");
					sql.append(" WHERE ");
					sql.append(" 	t1.company_id = :companyId ");
					if(StringUtils.isNotBlank(platform)){
						sql.append(" AND t1.platform = :platform ");
					}
					sql.append(" AND t1.account_no = :accountNo ");
					if(CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
						sql.append(" and (reason = 8 or reason = 16 or reason = 17 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
					}else{
						sql.append(" and (reason = 8 or reason = 16 or reason = 32 or reason = 64 or reason = 128 or reason = 160 or reason = 161 or reason = 162 ) ");
					}
					
					if(companyId.equals(CompanyEnum.fx.getLabelKey()) || companyId.equals(CompanyEnum.cf.getLabelKey())){
						sql.append(" AND t1.closetime ");// 平仓时间
					} else {
						sql.append(" AND t1.closetime_gmt8 ");// 平仓时间
					}
					sql.append("  BETWEEN :startDate AND :endDate ");
					if(companyId.equals(CompanyEnum.fx.getLabelKey()) || companyId.equals(CompanyEnum.cf.getLabelKey())){
						sql.append(" ORDER BY closetime ");// 平仓时间
					} else {
						sql.append(" ORDER BY closetime_gmt8 ");// 平仓时间
					}
					//sql.append(" limit 3 ");
					Query query = em.createNativeQuery(sql.toString());
					query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
							.setParameter("companyId", Long.parseLong(companyId))
							.setParameter("accountNo", Long.parseLong(accountNo))
							.setParameter("startDate", DateUtil.stringToDate(startDate))
							.setParameter("endDate", DateUtil.addDays(DateUtil.stringToDate(endDate),1));
					if(StringUtils.isNotBlank(platform)){
						query.setParameter("platform", platform);
					}
					List list = query.getResultList();
					apiCacheManager.put(keys, list, 15, TimeUnit.DAYS);
					return list;
				} else {
					return o;
				}			
	}

}
