package com.gw.das.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.api.cache.ApiCacheManager;
import com.gw.das.api.common.context.Constants;
import com.gw.das.api.common.enums.businessEnum;
import com.gw.das.api.common.response.ApiResult;
import com.gw.das.api.common.response.ApiStatusEnum;
import com.gw.das.api.common.utils.CheckUtil;
import com.gw.das.api.common.utils.FileUtil;
import com.gw.das.api.common.utils.JSONUtils;
import com.gw.das.api.common.utils.MD5;
import com.gw.das.api.dao.AccountBlacklistDao;
import com.gw.das.api.service.AccountBlacklistService;

/**
 * 
 * 账户黑名单Service实现类
 * 
 * @author darren
 *
 */
@Service
public class AccountBlacklistServiceImpl implements AccountBlacklistService {

	private static Logger logger = LoggerFactory.getLogger(AccountBlacklistServiceImpl.class);
	
	@Autowired
	private TransportClient client;

	@Autowired
	private ApiCacheManager apiCacheManager;
	
	@Autowired
	private AccountBlacklistDao accountBlacklistDao;
	
	private static Map<String,String> pmMap = new HashMap<String,String>();

	private static Map<String,String> fxMap = new HashMap<String,String>();
	
	
	private static Map<String,String> WhiteChannels_PM = new HashMap<String,String>();

	private static Map<String,String> WhiteChannels_FX = new HashMap<String,String>();
	
	static{
		//pmMap.put("pd112", "pd112");
		//pmMap.put("pmf101-1", "pmf101-1");
		
		//fxMap.put("mf101", "pd112");
		//fxMap.put("360daohang", "360daohang");
		
		WhiteChannels_PM.put("pd112", "pd112");
		WhiteChannels_PM.put("pmf101-1", "pmf101-1");
		
		WhiteChannels_FX.put("mf101", "pd112");
		WhiteChannels_FX.put("360daohang", "360daohang");
		
		String pmBlacklistStr =  FileUtil.readFromClasspathAsString("px_blacklist.json");
		pmMap = JSONUtils.JsonStringtoHashMap(pmBlacklistStr);
		String fxBlacklistStr =  FileUtil.readFromClasspathAsString("fx_blacklist.json");
		fxMap = JSONUtils.JsonStringtoHashMap(fxBlacklistStr);
		
	}
	
	/**
	 * 修改 查询二张表，dim_blacklist，dim_account,先从dim_blacklist表中查询，根据value查询，
	 * 
	 * 如果没有再从dim_account表中查询，查询 where mobile like '' or id_card like ''
	 * 
	 * 返回 {value:'',type:'',das:''}   das: 黑名单，已开户，正常
	 */
	@Override
	public ApiResult findAccountBlacklist(String value, Integer type ) throws Exception {
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + value + type ;
		logger.debug("==>>positionTimeIntervalStatistics[keys={}]", keys);
		ApiResult cache = (ApiResult) apiCacheManager.get(keys);
		StringBuffer sql = new StringBuffer();
		if (null == cache) {
			if (type == 0) {
/*				if (CheckUtil.checkCardNo(value)) {
					//String encrypt = MD5.encrypt(value);
					sql.append(" select type,risk_type risktype,remark from rds.dim_blacklist where value = :value  ");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("value", value);
					ApiResult result = new ApiResult(ApiStatusEnum.success);
					accountBlacklistDao.findAccountBlacklist(result, sql.toString(), params);
					apiCacheManager.put(keys, result);
					return result;
				} else {*/
					sql.append(" select type,risk_type,remark from rds.dim_blacklist where value = :value  ");
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("value", value);
					ApiResult result = new ApiResult(ApiStatusEnum.success);
					accountBlacklistDao.findAccountBlacklist(result, sql.toString(), params);
					apiCacheManager.put(keys, result);
					return result;
				//}
			} else {
				sql.append(" select ");
				sql.append(" account_no accountno, ");
				sql.append(" account_id accountid, ");
				sql.append(" company_id companyid, ");
				sql.append(" platform platform, ");
				sql.append(" issue_account_no issueaccountno, ");
				sql.append(" issue_account_name issueaccountname, ");
				sql.append(" issue_mobile issuemobile, ");
				sql.append(" issue_id_card_encrypt issueidcardencrypt, ");
				sql.append(" issue_id_card_md5 issueidcardmd5, ");
				sql.append(" issue_email issueemail, ");
				sql.append(" issue_ip issueip, ");
				sql.append(" list_risk_type listrisktype, ");
				sql.append(" list_remark listremark, ");
				sql.append(" open_id_card_double_cnt openidcarddoublecnt, ");
				sql.append(" open_mobile_double_cnt openmobiledoublecnt, ");
				sql.append(" active_id_card_double_cnt activeidcarddoublecnt, ");
				sql.append(" active_mobile_double_cnt activemobiledoublecnt, ");
				sql.append(" account_no_sk accountnosk, ");
				sql.append(" account_id_sk accountidsk ");
				sql.append(" from rds.dim_account_blacklist ");
				sql.append(" where account_no = :value ");
				
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("value", Integer.valueOf(value));
				ApiResult result = new ApiResult(ApiStatusEnum.success);
				accountBlacklistDao.findAccountBlacklist(result, sql.toString(), params);
				apiCacheManager.put(keys, result);
				return result;
			}
		} else {
			return cache;
		}
	}

	@Override
	public ApiResult channelWarning(String value, Integer type) throws Exception {
		if(StringUtils.isBlank(value)){
			ApiResult result = new ApiResult(ApiStatusEnum.success);
	        result.setResult(false);
			return result;
		}
		try {
			SearchRequestBuilder srb = client.prepareSearch("web_logs_ods");
			//设置查询类型  
			srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
			if(type == 1){
				BoolQueryBuilder  query = QueryBuilders.boolQuery();		
				if(StringUtils.isNotBlank(value)){
					query.must(QueryBuilders.matchPhraseQuery("behaviorDetail", value));
				}				
			   srb.setQuery(query);
			   SearchResponse response = srb.execute().actionGet();
			   SearchHits hits = response.getHits();
			   for (SearchHit searchHit : hits) {
					Map<String, Object> source = searchHit.getSource();
					String userId = (String) source.get("userId");
					String utmcsr = (String) source.get("utmcsr");
					Integer businessPlatform = Integer.valueOf(String.valueOf(source.get("businessPlatform")));
					if(StringUtils.isNotBlank(userId)){
						return userChannelWarning(type,userId,client,utmcsr,businessPlatform);
					}
			    }
			}else{
				return userChannelWarning(type,value,client,null,null);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}

		ApiResult result = new ApiResult(ApiStatusEnum.success);
        result.setResult(false);
		return result;
	}
		
    /**
     * 
     * @param type
     * @param value
     * @param client
     * @param accountUtmcsr
     * @param accounBusinessPlatform
     * @return
     */
	private ApiResult userChannelWarning(Integer type,String value,TransportClient client,String accountUtmcsr,Integer accounBusinessPlatform){
		SearchRequestBuilder srb = client.prepareSearch("web_logs_ods");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
	   BoolQueryBuilder  query = QueryBuilders.boolQuery();		
	   query = QueryBuilders.boolQuery();		
	   query.must(QueryBuilders.matchPhraseQuery("userId", value));	
	   srb.setQuery(query);
	   SearchResponse response = srb.execute().actionGet();
	   SearchHits userHits = response.getHits();
	   if(userHits.getTotalHits() == 0 && type == 1){
		   if(accounBusinessPlatform == Integer.valueOf(businessEnum.fx.getLabelKey())){
			   if(fxMap.containsKey(accountUtmcsr.toLowerCase())){
				   ApiResult result = new ApiResult(ApiStatusEnum.success);
		           result.setResult(true);
				   return result;
			   }
		   }else if(accounBusinessPlatform == Integer.valueOf(businessEnum.pm.getLabelKey())){
			   if(pmMap.containsKey(accountUtmcsr.toLowerCase())){
				   ApiResult result = new ApiResult(ApiStatusEnum.success);
		           result.setResult(true);
				   return result;
			   }
		   }else{
			   ApiResult result = new ApiResult(ApiStatusEnum.success);
	           result.setResult(false);
			   return result;
		   }
	   }
	   
	   boolean flag = true;
	   for (SearchHit searchUserHit : userHits) {
		   Map<String, Object> userSource = searchUserHit.getSource();
		   String utmcsr = (String) userSource.get("utmcsr");
		   Integer businessPlatform = Integer.valueOf(String.valueOf(userSource.get("businessPlatform")));
		   if(businessPlatform == Integer.valueOf(businessEnum.fx.getLabelKey())){
			   if(!fxMap.containsKey(utmcsr.toLowerCase())){
				   flag = false;
				   break;
			   }
		   }else if(businessPlatform == Integer.valueOf(businessEnum.pm.getLabelKey())){
			   if(!pmMap.containsKey(utmcsr.toLowerCase())){
				   flag = false;
				   break;
			   }
		   }else{
			   flag = false;
		   }
	   }
	   
	   if(userHits.getTotalHits() >0 && flag){
           ApiResult result = new ApiResult(ApiStatusEnum.success);
           result.setResult(true);
		   return result;
	   }

	   ApiResult result = new ApiResult(ApiStatusEnum.success);
       result.setResult(false);
	   return result;
	}
	
	/**
	 * 渠道预警
	 * @param value
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public ApiResult channelWarning2(String value, Integer type) throws Exception {
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + value + type ;
		logger.debug("==>>channelWarning[keys={}]", keys);
		
		Boolean resultState = false;
		if(StringUtils.isNotBlank(value)){
			SearchRequestBuilder srb = client.prepareSearch("web_logs_ods");
			//设置查询类型  
			srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
			if(type == 1){
			   BoolQueryBuilder  query = QueryBuilders.boolQuery();		
				
			   query.must(QueryBuilders.matchPhraseQuery("behaviorDetail", value));
			   srb.setQuery(query);
			   SearchResponse response = srb.execute().actionGet();
			   SearchHits hits = response.getHits();
			   
			   for (SearchHit searchHit : hits) {
					Map<String, Object> source = searchHit.getSource();
					String uuid = (String) source.get("userId");
					String utmcsr = (String) source.get("utmcsr");
					Integer businessPlatform = Integer.valueOf(String.valueOf(source.get("businessPlatform")));
					if(businessPlatform == Integer.valueOf(businessEnum.fx.getLabelKey())){
						if(!WhiteChannels_FX.containsKey(utmcsr.toLowerCase())) break;
					}else if(businessPlatform == Integer.valueOf(businessEnum.pm.getLabelKey())){
						if(!WhiteChannels_PM.containsKey(utmcsr.toLowerCase())) break;
					}
					
					if(StringUtils.isNotBlank(uuid)) return uuidChannelWarning(uuid);
				  }
				}else{
					return uuidChannelWarning(value);
				}
			}
		
		ApiResult result = new ApiResult(ApiStatusEnum.success);
        result.setResult(resultState);
		return result;
	}
	
	
	/**
	 * UUID渠道预警
	 * @param uuid
	 * @return
	 * @throws Exception
	 */
	public ApiResult uuidChannelWarning(String uuid) throws Exception{
		   Boolean resultState = false;
		   
		   SearchRequestBuilder srb = client.prepareSearch("web_logs_ods");
		   //设置查询类型  
		   srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		   BoolQueryBuilder  query = QueryBuilders.boolQuery();		
		   query = QueryBuilders.boolQuery();		
		   query.must(QueryBuilders.matchPhraseQuery("userId", uuid));	
		   srb.setQuery(query);
		   SearchResponse response = srb.execute().actionGet();
		   SearchHits userHits = response.getHits();
		   
		   for (SearchHit searchUserHit : userHits) {
			   Map<String, Object> userSource = searchUserHit.getSource();
			   String utmcsr = (String) userSource.get("utmcsr");
			   Integer businessPlatform = Integer.valueOf(String.valueOf(userSource.get("businessPlatform")));
			   if(businessPlatform == Integer.valueOf(businessEnum.fx.getLabelKey())){
				   if(!WhiteChannels_FX.containsKey(utmcsr.toLowerCase())){
					   resultState = false;
					   break;
				   }else{
					   resultState = true;
				   }
			   }else if(businessPlatform == Integer.valueOf(businessEnum.pm.getLabelKey())){
				   if(!WhiteChannels_PM.containsKey(utmcsr.toLowerCase())){
					   resultState = false;
					   break;
				   }else{
					   resultState = true;
				   }
			   }
		   }
	
		   ApiResult result = new ApiResult(ApiStatusEnum.success);
	       result.setResult(resultState);
		   return result;
	}
	
	/**
	 * 风险类型验证
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public ApiResult riskTypeValidation(String values) throws Exception {
		String clazz = Thread.currentThread().getStackTrace()[1].getClassName();
		String method = Thread.currentThread().getStackTrace()[1].getMethodName();
		String keys = Constants.projectName + clazz + method + values ;
		logger.debug("==>>riskTypeValidation[keys={}]", keys);
		ApiResult cache = (ApiResult) apiCacheManager.get(keys);
		StringBuffer sql = new StringBuffer();
		
		
		 ApiResult result = new ApiResult(ApiStatusEnum.success);
	     result.setResult(false);
		 return result;
	}
}
