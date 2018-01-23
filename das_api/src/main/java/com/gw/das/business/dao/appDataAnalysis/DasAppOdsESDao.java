package com.gw.das.business.dao.appDataAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.appDataAnalysis.entity.DasAppOdsVO;

/**
 * 交易客户端数据源报表DAO
 * 
 * @author darren
 * @since 2017-04-28
 *
 */
@Repository
public class DasAppOdsESDao {
	
	private static final Logger logger = LoggerFactory.getLogger(DasAppOdsESDao.class);
	
	@Autowired
	private TransportClient client;
	
	/**
	 * 
	 * 分页查询app数据源报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid findDasAppOdsPageList(AppDataAnalysisSearchModel model)
			throws Exception {
		int pageNo = model.getPageNumber();
		int pageSize = model.getPageSize();
		PageGrid page = new PageGrid();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		SearchRequestBuilder srb = client.prepareSearch("gts2_app_ods");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setTypes("source");	
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();		
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
		}
		
		if(StringUtils.isNotBlank(model.getAccount())){	//用户帐号
			query.must(QueryBuilders.queryStringQuery("*"+model.getAccount()+"*").field("account"));
		}
		
		if(StringUtils.isNotBlank(model.getPlatformType())){	//交易平台
			query.must(QueryBuilders.matchPhraseQuery("platformType",model.getPlatformType()));
		}
		if(StringUtils.isNotBlank(model.getDeviceType())){	//设备类型
			query.must(QueryBuilders.matchPhraseQuery("deviceType",model.getDeviceType()));
		}
		if(StringUtils.isNotBlank(model.getUserType())){
			query.must(QueryBuilders.matchPhraseQuery("userType",model.getUserType()));
		}
		if(StringUtils.isNotBlank(model.getAccountType())){	//账号等级
			query.must(QueryBuilders.matchPhraseQuery("accountType",model.getAccountType()));
		}
		if(StringUtils.isNotBlank(model.getIdfa())){	//IDFA
			query.must(QueryBuilders.queryStringQuery("*"+model.getIdfa()+"*").field("idfa"));
		}
		if(StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())){	//渠道
			query.must(QueryBuilders.matchPhraseQuery("channel",model.getChannel().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getDeviceid())){	//设备唯一标识
			model.setDeviceid(QueryParser.escape(model.getDeviceid())); // 主要就是这一句把特殊字符都转义就可以识别
			query.must(QueryBuilders.queryStringQuery("*"+model.getDeviceid()+"*").field("deviceid"));//
		}
		if(StringUtils.isNotBlank(model.getCarrier())){	//设备厂商
			query.must(QueryBuilders.queryStringQuery("*"+model.getCarrier().toLowerCase()+"*").field("carrier"));
		}
		if(StringUtils.isNotBlank(model.getModel())){	//设备型号
			model.setModel(QueryParser.escape(model.getModel()));
			query.must(QueryBuilders.queryStringQuery("*"+model.getModel().toLowerCase()+"*").field("model"));
		}
		if(StringUtils.isNotBlank(model.getPlatformName())){	//应用名称
			query.must(QueryBuilders.matchPhraseQuery("platformName",model.getPlatformName().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getPlatformVersion())){	//版本号
			query.must(QueryBuilders.queryStringQuery("*"+model.getPlatformVersion().toLowerCase()+"*").field("platformVersion"));
		}
		if(StringUtils.isNotBlank(model.getEventCategory())){	//事件类别
			query.must(QueryBuilders.matchPhraseQuery("eventCategory",model.getEventCategory().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getEventAction())){	//事件操作
			query.must(QueryBuilders.matchPhraseQuery("eventAction",model.getEventAction().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getEventLabel())){	//事件标签
			query.must(QueryBuilders.matchPhraseQuery("eventLabel",model.getEventLabel().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getEventValue())){	//事件参数
			query.must(QueryBuilders.matchPhraseQuery("eventValue",model.getEventValue().toLowerCase()));
		}
		
		if(StringUtils.isNotBlank(model.getOperationType())){	//操作类型
			query.must(QueryBuilders.matchPhraseQuery("operationType",model.getOperationType()));
		}

		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getEndTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			   query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime())&& StringUtils.isNotBlank(model.getEndTime())){
				query.filter(QueryBuilders.rangeQuery("operationTime").lte(model.getEndTime()));
			}else{
				query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).lte(model.getEndTime()));	
			}			
		}
		srb.setQuery(query);
		
		//设置分页信息 
		srb.setFrom((pageNo - 1) * pageSize).setSize(pageSize)
		// 设置是否按查询匹配度排序  
		.setExplain(true);
		srb.addSort(model.getSort(), SortOrder.DESC);
		
		long start = System.currentTimeMillis();
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		page.setTotal((int)hits.getTotalHits());

		List<DasAppOdsVO> list = new ArrayList<DasAppOdsVO>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			DasAppOdsVO entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), DasAppOdsVO.class);
			list.add(entity);
		}
		page.setRows(list);
		
		long end = System.currentTimeMillis();
		logger.info("ES查询交易客户端数据源报表消耗时间:"+(end-start)/1000+"秒");
		
		return page;
	}
	
    /**
     * 不分页查询app数据源报表记录
     * 
     * @param businessplatform
     * @return
     * @throws Exception
     */
	public List<DasAppOdsVO> findDasAppOdsList(AppDataAnalysisSearchModel model)
			throws Exception {
		SearchRequestBuilder srb = client.prepareSearch("gts2_app_ods");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setTypes("source");	
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();		
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
		}
		
		if(StringUtils.isNotBlank(model.getAccount())){	//用户帐号
			query.must(QueryBuilders.queryStringQuery("*"+model.getAccount()+"*").field("account"));
		}
		
		if(StringUtils.isNotBlank(model.getPlatformType())){	//交易平台
			query.must(QueryBuilders.matchPhraseQuery("platformType",model.getPlatformType()));
		}
		if(StringUtils.isNotBlank(model.getDeviceType())){	//设备类型
			query.must(QueryBuilders.matchPhraseQuery("deviceType",model.getDeviceType()));
		}
		if(StringUtils.isNotBlank(model.getUserType())){
			query.must(QueryBuilders.matchPhraseQuery("userType",model.getUserType()));
		}
		if(StringUtils.isNotBlank(model.getAccountType())){	//账号等级
			query.must(QueryBuilders.matchPhraseQuery("accountType",model.getAccountType()));
		}
		if(StringUtils.isNotBlank(model.getIdfa())){	//IDFA
			query.must(QueryBuilders.queryStringQuery("*"+model.getIdfa()+"*").field("idfa"));
		}
		if(StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())){	//渠道
			query.must(QueryBuilders.matchPhraseQuery("channel",model.getChannel().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getDeviceid())){	//设备唯一标识
			model.setDeviceid(QueryParser.escape(model.getDeviceid())); // 主要就是这一句把特殊字符都转义就可以识别
			query.must(QueryBuilders.queryStringQuery("*"+model.getDeviceid()+"*").field("deviceid"));//
		}
		if(StringUtils.isNotBlank(model.getCarrier())){	//设备厂商
			query.must(QueryBuilders.queryStringQuery("*"+model.getCarrier().toLowerCase()+"*").field("carrier"));
		}
		if(StringUtils.isNotBlank(model.getModel())){	//设备型号
			model.setModel(QueryParser.escape(model.getModel()));
			query.must(QueryBuilders.queryStringQuery("*"+model.getModel().toLowerCase()+"*").field("model"));
		}
		if(StringUtils.isNotBlank(model.getPlatformName())){	//应用名称
			query.must(QueryBuilders.matchPhraseQuery("platformName",model.getPlatformName().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getPlatformVersion())){	//版本号
			query.must(QueryBuilders.queryStringQuery("*"+model.getPlatformVersion().toLowerCase()+"*").field("platformVersion"));
		}
		if(StringUtils.isNotBlank(model.getEventCategory())){	//事件类别
			query.must(QueryBuilders.matchPhraseQuery("eventCategory",model.getEventCategory().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getEventAction())){	//事件操作
			query.must(QueryBuilders.matchPhraseQuery("eventAction",model.getEventAction().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getEventLabel())){	//事件标签
			query.must(QueryBuilders.matchPhraseQuery("eventLabel",model.getEventLabel().toLowerCase()));
		}
		if(StringUtils.isNotBlank(model.getEventValue())){	//事件参数
			query.must(QueryBuilders.matchPhraseQuery("eventValue",model.getEventValue().toLowerCase()));
		}
		
		if(StringUtils.isNotBlank(model.getOperationType())){	//操作类型
			query.must(QueryBuilders.matchPhraseQuery("operationType",model.getOperationType()));
		}
		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getEndTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			   query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime())&& StringUtils.isNotBlank(model.getEndTime())){
				query.filter(QueryBuilders.rangeQuery("operationTime").lte(model.getEndTime()));
			}else{
				query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).lte(model.getEndTime()));	
			}			
		}
		srb.setQuery(query);
		
		// 设置是否按查询匹配度排序  
		srb.setExplain(true);
		srb.addSort(model.getSort(), SortOrder.DESC);
		srb.setFrom(0).setSize(300000);
		
		long start = System.currentTimeMillis();
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		
		List<DasAppOdsVO> list = new ArrayList<DasAppOdsVO>();
		
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			DasAppOdsVO entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), DasAppOdsVO.class);
			list.add(entity);
		}
		
		long end = System.currentTimeMillis();
		logger.info("ES查询交易客户端数据源报表消耗时间:"+(end-start)/1000+"秒");
		
		return list;
	}
	
}
