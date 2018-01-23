package com.gw.das.business.dao.tradeGts2.tradeBordereaux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.enums.ClientTypeEnum;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.ListPageUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.tradeGts2.entity.DealchannelVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * GTS2下单途径比例报表Dao
 * 
 * @author darren
 *
 */
@Repository
public class DealchannelWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;						
		sql.append(" SELECT to_char(execdate_rpt,'YYYY-MM-DD') AS exectime,  ");
		sql.append(" sum(case when client_type='3' then (close_cnt + open_cnt)  end ) androidamount, ");
		sql.append(" sum(case when client_type='2' then (close_cnt + open_cnt)  end ) iosamount, ");
		sql.append(" sum(case when client_type='14' or client_type='15' or client_type='7' then (close_cnt + open_cnt)  end ) systemamount, ");
		sql.append(" sum(case when client_type='1' or client_type='4' then (close_cnt + open_cnt)  end ) pcamount, ");
		sql.append(" sum(case when client_type<> '1' and  client_type<> '2' and client_type<> '3' and client_type<> '4' ");
		sql.append(" and client_type<> '7' and client_type<> '14' and client_type<> '15' then (close_cnt + open_cnt)  end ) otheramount ");
					
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,platform ");
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
		
		sql.append(" WHERE 1 = 1  ");
		sql.append(" and account_type='直客' ");
		sql.append(" and is_test = 0 ");
		if(null != model){				
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and execdate_rpt >= ?");
				paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and execdate_rpt <= ?");
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
	
	/**
	 * GTS2下单途径比例报表
	 */
	public PageGrid<TradeSearchModel> findDealchannelPageList(PageGrid<TradeSearchModel> pg,TradeSearchModel model) throws Exception{
		List<DealchannelVO> allResult = findDealchannelList(model);				
		ListPageUtil<DealchannelVO> pager = new ListPageUtil<DealchannelVO>(allResult, model.getPageSize());		
		pg.setTotal(allResult.size());
		pg.setRows(pager.getPagedList(model.getPageNumber()));
		return pg;		
	}
	
	/**
	 * GTS2下单途径比例报表
	 */
	public List<DealchannelVO> findDealchannelList(TradeSearchModel model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT	row_number() over() as rowkey, client_type,execdate_rpt as exectime, ");
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" platform , ");
		}
		//sql.append(" SUM (volume + open_volume) as volume ");	
		//修改为开仓次数
		sql.append(" SUM (open_cnt) as volume ");	
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_fact_trans_agg a ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_fact_trans_agg a ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_fact_trans_agg a ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_fact_trans_agg a ");	
		}
		
		//sql.append("  FROM rds.fact_trans_agg a ");	

		sql.append(" WHERE 1 = 1 ");	
		sql.append(" and a.account_type='直客' ");
		sql.append(" and a.is_test = 0 ");
		if(null != model){					
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and a.execdate_rpt >= ?");
				paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getStartTime()));
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and a.execdate_rpt <= ?");
				paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getEndTime()));
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and a.platform = ? ");
				paramList.add(model.getPlatformType());
			}
			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and a.business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}	
			sql.append("group by exectime,client_type ");
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(", platform");
			}
			if(StringUtils.isNotBlank(model.getOrder())){
				String order = " order by "+ model.getSort() +" "+model.getOrder()+" ";
				sql.append(order);
			}else{
				String order = " order by exectime DESC ";
				sql.append(order);
			}
		}
				
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DealchannelVO> result = new ArrayList<DealchannelVO>();
		if(null != list && list.size() > 0){
			Map<String,Map<String,Double>> resultMap = new HashMap<String,Map<String,Double>>();
			for (Map<String, Object> map : list) {
				String exectime = String.valueOf(map.get("exectime"));
				String clientType = String.valueOf(map.get("client_type"));
				String volume = String.valueOf(map.get("volume"));
				if(!resultMap.containsKey(exectime)){
					Map<String,Double> valueMap = new HashMap<String,Double>();
					if(ClientTypeEnum.ANDROID.getLabelKey().equals(clientType)){
						if(!valueMap.containsKey("androidamount")){
							valueMap.put("androidamount", Double.valueOf(volume));
						}else{
							valueMap.put("androidamount", valueMap.get("androidamount") + Double.valueOf(volume));
						}	
					}else if(ClientTypeEnum.IOS.getLabelKey().equals(clientType)){
						if(!valueMap.containsKey("iosamount")){
							valueMap.put("iosamount", Double.valueOf(volume));
						}else{
							valueMap.put("iosamount", valueMap.get("iosamount") + Double.valueOf(volume));
						}	
					}else if(ClientTypeEnum.SYSTEM.getLabelKey().equals(clientType) 
							|| ClientTypeEnum.SYSTEMAGENT.getLabelKey().equals(clientType) || ClientTypeEnum.MANAGER.getLabelKey().equals(clientType)){
						if(!valueMap.containsKey("systemamount")){
							valueMap.put("systemamount", Double.valueOf(volume));
						}else{
							valueMap.put("systemamount", valueMap.get("systemamount") + Double.valueOf(volume));
						}						
					}else if(ClientTypeEnum.WINDOWS.getLabelKey().equals(clientType) || ClientTypeEnum.WEBUI.getLabelKey().equals(clientType)){						
						if(!valueMap.containsKey("pcamount")){
							valueMap.put("pcamount", Double.valueOf(volume));
						}else{
							valueMap.put("pcamount", valueMap.get("pcamount") + Double.valueOf(volume));
						}						
					}else {
						if(!valueMap.containsKey("otheramount")){
							valueMap.put("otheramount", Double.valueOf(volume));
						}else{
							valueMap.put("otheramount", valueMap.get("otheramount") + Double.valueOf(volume));
						}					
					}
					resultMap.put(exectime, valueMap);
				}else{
					Map<String,Double> valueMap = resultMap.get(exectime);
					if(ClientTypeEnum.ANDROID.getLabelKey().equals(clientType)){
						if(!valueMap.containsKey("androidamount")){
							valueMap.put("androidamount", Double.valueOf(volume));
						}else{
							valueMap.put("androidamount", valueMap.get("androidamount") + Double.valueOf(volume));
						}
					}else if(ClientTypeEnum.IOS.getLabelKey().equals(clientType)){
						if(!valueMap.containsKey("iosamount")){
							valueMap.put("iosamount", Double.valueOf(volume));
						}else{
							valueMap.put("iosamount", valueMap.get("iosamount") + Double.valueOf(volume));
						}
					}else if(ClientTypeEnum.SYSTEM.getLabelKey().equals(clientType) 
							|| ClientTypeEnum.SYSTEMAGENT.getLabelKey().equals(clientType) || ClientTypeEnum.MANAGER.getLabelKey().equals(clientType)){
						if(!valueMap.containsKey("systemamount")){
							valueMap.put("systemamount", Double.valueOf(volume));
						}else{
							valueMap.put("systemamount", valueMap.get("systemamount") + Double.valueOf(volume));
						}
					}else if(ClientTypeEnum.WINDOWS.getLabelKey().equals(clientType) || ClientTypeEnum.WEBUI.getLabelKey().equals(clientType)){
						if(!valueMap.containsKey("pcamount")){
							valueMap.put("pcamount", Double.valueOf(volume));
						}else{
							valueMap.put("pcamount", valueMap.get("pcamount") + Double.valueOf(volume));
						}
					}else {
						if(!valueMap.containsKey("otheramount")){
							valueMap.put("otheramount", Double.valueOf(volume));
						}else{
							valueMap.put("otheramount", valueMap.get("otheramount") + Double.valueOf(volume));
						}
					}
					resultMap.put(exectime, valueMap);
				}				
			}
						
			for (Map.Entry<String,Map<String,Double>> entry : resultMap.entrySet()) {  
				String exectime = entry.getKey();
				Map<String,Double> valueMap = entry.getValue();
				
				DealchannelVO dealchannelVO = new DealchannelVO();	
				dealchannelVO.setExectime(exectime);
				dealchannelVO.setPlatform(model.getPlatformType());
				Double androidamount = valueMap.get("androidamount");
				Double iosamount = valueMap.get("iosamount");
				Double systemamount = valueMap.get("systemamount");
				Double pcamount = valueMap.get("pcamount");
				Double otheramount = valueMap.get("otheramount");
				Double totalamount = 0D;
				if(null != androidamount){
					totalamount += androidamount;
					dealchannelVO.setAndroidamount(androidamount);
				}
				if(null != iosamount){
					totalamount += iosamount;
					dealchannelVO.setIosamount(iosamount);
				}
				if(null != systemamount){
					totalamount += systemamount;
					dealchannelVO.setSystemamount(systemamount);
				}
				if(null != pcamount){
					totalamount += pcamount;
					dealchannelVO.setPcamount(pcamount);
				}
				if(null != otheramount){
					totalamount += otheramount;
					dealchannelVO.setOtheramount(otheramount);
				}
				dealchannelVO.setTotalamount(totalamount);
				result.add(dealchannelVO);
			}			
		}
		Collections.sort(result, new Comparator<DealchannelVO>(){  		  
            /*  
             * int compare(DealchannelVO o1, DealchannelVO o2) 返回一个基本类型的整型，  
             * 返回负数表示：o1 小于o2，  
             * 返回0 表示：o1和o2相等，  
             * 返回正数表示：o1大于o2。  
             */  
            public int compare(DealchannelVO o1, DealchannelVO o2) {  
                Date dt1 = DateUtil.stringToDate(o1.getExectime(), "yyyy-MM-dd");
                Date dt2 = DateUtil.stringToDate(o2.getExectime(), "yyyy-MM-dd");
                if (dt1.getTime() > dt2.getTime()) {
                    return -1;
                } else if (dt1.getTime() < dt2.getTime()) {
                    return 1;
                } else {
                    return 0;
                } 
            }  
     }); 
	return result;
	}

}
