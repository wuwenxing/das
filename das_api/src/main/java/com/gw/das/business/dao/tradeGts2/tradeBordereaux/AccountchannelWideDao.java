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
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.ListPageUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.tradeGts2.entity.AccountchannelVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 新开户_激活途径比例报表Dao
 * 
 * @author darren
 *
 */
@Repository
public class AccountchannelWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		
	}
	
	/**
	 * 
	 *  --webPC数量   pc_website  pc_client,pc
		--Android数量  WEBSITE_ANDROID android
		--IOS数量  ios WEBSITE_IOS
		--移动其他数量  wap_website,m  
		--后台数量   BACKOFFICE
		--转移数量 MIGRATE_ADMIN\
		--金管家网站数量  GWAC_WEBSITE
		--资料丢失数量 null 与 ''
		--其他途径数量 WEBSITE,WEBSITE_Other 
		
		--激活 active_channel
		--PC途径数量 pc  PC 
		--MOBILE途径数量  mobile MOBILE wenxin
		--其他途径数量  
	 * 
	 */
	
	/**
	 * 激活途径比例
	 */
	public PageGrid<TradeSearchModel> findAccountchannelPageList(PageGrid<TradeSearchModel> pg,TradeSearchModel model) throws Exception{			
		List<AccountchannelVO> allResult = findAccountchannelList(model);				 			
		ListPageUtil<AccountchannelVO> pager = new ListPageUtil<AccountchannelVO>(allResult, model.getPageSize());		
		pg.setTotal(allResult.size());
		pg.setRows(pager.getPagedList(model.getPageNumber()));
		return pg;		
	}
	
	/**
	 * 激活途径比例
	 */
	public List<AccountchannelVO> findAccountchannelList(TradeSearchModel model) throws Exception{
		String type = model.getType();
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT	row_number() over() as rowkey, ");
		if("open".equals(type)){
			sql.append(" account_open_time as exectime, ");
			sql.append(" client_type, ");
		}else{
			sql.append(" active_time as exectime, ");
			sql.append(" active_channel, ");
		}

		sql.append(" COUNT (DISTINCT account_no) ");
		
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" ,platform ");
		}	
		
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_fx.mv_dim_account ");	
		}
		else if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_pm.mv_dim_account ");			
		}
		else if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_hx.mv_dim_account ");	
		}
		else if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
			sql.append("  FROM rds_cf.mv_dim_account ");	
		}
		
		sql.append(" WHERE 1 = 1  ");
		sql.append(" and account_type='直客' ");
		sql.append(" and is_test = 0 ");
		if(null != model){				
			if("open".equals(type)){
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and account_open_time >= ?");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and account_open_time <= ?");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
				}
			}else{
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and active_time >= ?");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and active_time <= ?");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
				}
			}
			
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platform = ? ");
				paramList.add(model.getPlatformType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
			}
			
			if("open".equals(type)){
				sql.append("group by exectime,client_type ");
			}else{
				sql.append("group by exectime,active_channel ");
			}
			
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
		List<AccountchannelVO> result = new ArrayList<AccountchannelVO>();
		if(null != list && list.size() > 0){
			Map<String,Map<String,Double>> resultMap = new HashMap<String,Map<String,Double>>();
			for (Map<String, Object> map : list) {
				String exectime = String.valueOf(map.get("exectime"));
				String clientType = String.valueOf(map.get("client_type"));
				if(StringUtils.isNotBlank(clientType)){
					clientType = clientType.toUpperCase();
				}
				String count = String.valueOf(map.get("count"));
				if(!resultMap.containsKey(exectime)){
					Map<String,Double> valueMap = new HashMap<String,Double>();
					if("open".equals(type)){
						if("PC_WEBSITE".equals(clientType) || "PC_CLIENT".equals(clientType) || "PC".equals(clientType)){							
							if(!valueMap.containsKey("webpc")){
								valueMap.put("webpc", Double.valueOf(count));
							}else{
								valueMap.put("webpc", valueMap.get("webpc") + Double.valueOf(count));
							}														
						}else if("WEBSITE_ANDROID".equals(clientType) || "ANDROID".equals(clientType)){
							if(!valueMap.containsKey("android")){
								valueMap.put("android", Double.valueOf(count));
							}else{
								valueMap.put("android", valueMap.get("android") + Double.valueOf(count));
							}							
						}else if("IOS".equals(clientType) || "WEBSITE_IOS".equals(clientType)){							
							if(!valueMap.containsKey("webios")){
								valueMap.put("webios", Double.valueOf(count));
							}else{
								valueMap.put("webios", valueMap.get("webios") + Double.valueOf(count));
							}							
						}else if("WAP_WEBSITE".equals(clientType) || "M".equals(clientType)){
							if(!valueMap.containsKey("webother")){
								valueMap.put("webother", Double.valueOf(count));
							}else{
								valueMap.put("webother", valueMap.get("webother") + Double.valueOf(count));
							}
						}else if("BACKOFFICE".equals(clientType)){
							if(!valueMap.containsKey("backoffice")){
								valueMap.put("backoffice", Double.valueOf(count));
							}else{
								valueMap.put("backoffice", valueMap.get("backoffice") + Double.valueOf(count));
							}
						}else if("MIGRATE_ADMIN".equals(clientType)){
							if(!valueMap.containsKey("trans")){
								valueMap.put("trans", Double.valueOf(count));
							}else{
								valueMap.put("trans", valueMap.get("trans") + Double.valueOf(count));
							}
						}else if("GWAC_WEBSITE".equals(clientType)){
							if(!valueMap.containsKey("kinweb")){
								valueMap.put("kinweb", Double.valueOf(count));
							}else{
								valueMap.put("kinweb", valueMap.get("kinweb") + Double.valueOf(count));
							}
						}else if(null == clientType || "".equals(clientType)){
							if(!valueMap.containsKey("loseinfo")){
								valueMap.put("loseinfo", Double.valueOf(count));
							}else{
								valueMap.put("loseinfo", valueMap.get("loseinfo") + Double.valueOf(count));
							}
						}else {
							if(!valueMap.containsKey("other")){
								valueMap.put("other", Double.valueOf(count));
							}else{
								valueMap.put("other", valueMap.get("other") + Double.valueOf(count));
							}
						}
					}else{
						if("PC".equals(clientType)){
							if(!valueMap.containsKey("pc")){
								valueMap.put("pc", Double.valueOf(count));
							}else{
								valueMap.put("pc", valueMap.get("pc") + Double.valueOf(count));
							}
						}else if("MOBILE".equals(clientType) || "WENXIN".equals(clientType)){
							if(!valueMap.containsKey("mobile")){
								valueMap.put("mobile", Double.valueOf(count));
							}else{
								valueMap.put("mobile", valueMap.get("mobile") + Double.valueOf(count));
							}
							
						}else {
							if(!valueMap.containsKey("other")){
								valueMap.put("other", Double.valueOf(count));
							}else{
								valueMap.put("other", valueMap.get("other") + Double.valueOf(count));
							}
						}
					}
					resultMap.put(exectime, valueMap);
					
				}else{
					Map<String,Double> valueMap = resultMap.get(exectime);
					if("open".equals(type)){
						if("PC_WEBSITE".equals(clientType) || "PC_CLIENT".equals(clientType)){
							if(!valueMap.containsKey("webpc")){								
								valueMap.put("webpc", Double.valueOf(count));
							}else{
								valueMap.put("webpc", valueMap.get("webpc")+Double.valueOf(count));
							}
						}else if("WEBSITE_ANDROID".equals(clientType) || "ANDROID".equals(clientType)){
							if(!valueMap.containsKey("android")){								
								valueMap.put("android", Double.valueOf(count));
							}else{
								valueMap.put("android", valueMap.get("android")+Double.valueOf(count));
							}
						}else if("IOS".equals(clientType) || "WEBSITE_IOS".equals(clientType)){
							if(!valueMap.containsKey("webios")){								
								valueMap.put("webios", Double.valueOf(count));
							}else{
								valueMap.put("webios", valueMap.get("webios")+Double.valueOf(count));
							}
						}else if("WAP_WEBSITE".equals(clientType)){
							if(!valueMap.containsKey("webother")){								
								valueMap.put("webother", Double.valueOf(count));
							}else{
								valueMap.put("webother", valueMap.get("webother")+Double.valueOf(count));
							}
						}else if("BACKOFFICE".equals(clientType)){
							if(!valueMap.containsKey("backoffice")){								
								valueMap.put("backoffice", Double.valueOf(count));
							}else{
								valueMap.put("backoffice", valueMap.get("backoffice")+Double.valueOf(count));
							}
						}else if("MIGRATE_ADMIN".equals(clientType)){
							if(!valueMap.containsKey("trans")){								
								valueMap.put("trans", Double.valueOf(count));
							}else{
								valueMap.put("trans", valueMap.get("trans")+Double.valueOf(count));
							}
						}else if("GWAC_WEBSITE".equals(clientType)){
							if(!valueMap.containsKey("kinweb")){								
								valueMap.put("kinweb", Double.valueOf(count));
							}else{
								valueMap.put("kinweb", valueMap.get("kinweb")+Double.valueOf(count));
							}
						}else if(null == clientType || "".equals(clientType)){
							if(!valueMap.containsKey("loseinfo")){								
								valueMap.put("loseinfo", Double.valueOf(count));
							}else{
								valueMap.put("loseinfo", valueMap.get("loseinfo")+Double.valueOf(count));
							}
						}else {
							if(!valueMap.containsKey("other")){								
								valueMap.put("other", Double.valueOf(count));
							}else{
								valueMap.put("other", valueMap.get("other")+Double.valueOf(count));
							}
						}
					}else{
						if("PC".equals(clientType)){
							if(!valueMap.containsKey("pc")){								
								valueMap.put("pc", Double.valueOf(count));
							}else{
								valueMap.put("pc", valueMap.get("pc")+Double.valueOf(count));
							}
						}else if("MOBILE".equals(clientType) || "WENXIN".equals(clientType)){
							if(!valueMap.containsKey("mobile")){								
								valueMap.put("mobile", Double.valueOf(count));
							}else{
								valueMap.put("mobile", valueMap.get("mobile")+Double.valueOf(count));
							}
						}else {
							if(!valueMap.containsKey("other")){								
								valueMap.put("other", Double.valueOf(count));
							}else{
								valueMap.put("other", valueMap.get("other")+Double.valueOf(count));
							}
						}
					}
					
					resultMap.put(exectime, valueMap);
				}				
			}
						
			for (Map.Entry<String,Map<String,Double>> entry : resultMap.entrySet()) {  
				String exectime = entry.getKey();
				Map<String,Double> valueMap = entry.getValue();
				AccountchannelVO accountchannelVO = new AccountchannelVO();	
				accountchannelVO.setExectime(exectime);
				accountchannelVO.setPlatform(model.getPlatformType());
				if("open".equals(type)){
					Double webpc = valueMap.get("webpc");
					Double android = valueMap.get("android");
					Double webios = valueMap.get("webios");
					Double webother = valueMap.get("webother");
					Double backoffice = valueMap.get("backoffice");				
					Double trans = valueMap.get("trans");
					Double kinweb = valueMap.get("kinweb");
					Double loseinfo = valueMap.get("loseinfo");
					Double other = valueMap.get("other");
	                if(null != webpc){
	                	accountchannelVO.setWebpc(webpc);
	                }
	                if(null != android){
	                	accountchannelVO.setAndroid(android);
	                }
	                if(null != webios){
	                	accountchannelVO.setWebios(webios);
	                }
	                if(null != webother){
	                	accountchannelVO.setWebother(webother);
	                }
	                if(null != backoffice){
	                	accountchannelVO.setBackoffice(backoffice);
	                }
	                if(null != trans){
	                	accountchannelVO.setTrans(trans);
	                }
	                if(null != kinweb){
	                	accountchannelVO.setKinweb(kinweb);
	                }
	                if(null != loseinfo){
	                	accountchannelVO.setLoseinfo(loseinfo);
	                }
	                if(null != other){
	                	accountchannelVO.setOther(other);
	                }	
				}else{
					Double pc = valueMap.get("pc");
					Double mobile = valueMap.get("mobile");
					Double other = valueMap.get("other");
					if(null != pc){
	                	accountchannelVO.setPc(pc);
	                }
	                if(null != mobile){
	                	accountchannelVO.setMobile(mobile);
	                }
	                if(null != other){
	                	accountchannelVO.setOther(other);
	                }
				}
							
				result.add(accountchannelVO);
			}			
		}
		
		Collections.sort(result, new Comparator<AccountchannelVO>(){  		  
            /*  
             * int compare(AccountchannelVO o1, AccountchannelVO o2) 返回一个基本类型的整型，  
             * 返回负数表示：o1 小于o2，  
             * 返回0 表示：o1和o2相等，  
             * 返回正数表示：o1大于o2。  
             */  
            public int compare(AccountchannelVO o1, AccountchannelVO o2) {  
                Date dt1 = DateUtil.stringToDate(o1.getExectime(), "yyyy-MM-dd HH:mm:ss");
                Date dt2 = DateUtil.stringToDate(o2.getExectime(), "yyyy-MM-dd HH:mm:ss");
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
