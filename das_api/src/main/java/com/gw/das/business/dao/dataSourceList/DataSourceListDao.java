package com.gw.das.business.dao.dataSourceList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.gw.das.business.dao.dataSourceList.entity.DataSourceListModel;
import com.gw.das.business.dao.dataSourceList.entity.RoomDataSource;
import com.gw.das.business.dao.dataSourceList.entity.WebsiteBehaviorDataSource;

/**
 * 数据源列表Dao实现类
 * 
 * @author darren
 *
 */
@Repository
public class DataSourceListDao {
	
	private static final Logger logger = LoggerFactory.getLogger(DataSourceListDao.class);
	
	@Autowired
	private TransportClient client;
	
	/**
	 * 官网行为数据源记录查询-分页-elasticsearch
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public PageGrid findWebsiteBehaviorDataSourcePageList(DataSourceListModel model)
			throws Exception {
		int pageNo = model.getPageNumber();
		int pageSize = model.getPageSize();
		PageGrid page = new PageGrid();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		SearchRequestBuilder srb = client.prepareSearch("web_logs_ods");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();		
		
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));
		}
		
		if(StringUtils.isNotBlank(model.getUserId())){	
			model.setUserId(QueryParser.escape(model.getUserId()));
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserId()+"*").field("userId"));//UUID
		}
		
		if(StringUtils.isNotBlank(model.getSessionId())){	
			model.setSessionId(QueryParser.escape(model.getSessionId())); // 主要就是这一句把特殊字符都转义就可以识别
			//query.must(QueryBuilders.queryStringQuery("*"+model.getSessionId()+"*").field("sessionId"));//会话标识
			query.must(QueryBuilders.matchPhraseQuery("sessionId", model.getSessionId().replace("-", "?")));
		}
		
		if(StringUtils.isNotBlank(model.getPlatformType())){	
			query.must(QueryBuilders.matchPhraseQuery("platformType", model.getPlatformType()));//终端类型
		}
		
		if(StringUtils.isNotBlank(model.getDeviceId())){	
			model.setDeviceId(QueryParser.escape(model.getDeviceId()));
			query.must(QueryBuilders.matchPhraseQuery("deviceId",model.getDeviceId()));//终端标识	
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));//来源(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));//媒介(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn()+"*").field("utmccn"));//系列(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct()+"*").field("utmcct"));//组(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr()+"*").field("utmctr"));//关键词(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr2()+"*").field("utmcsr2"));//来源(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd2()+"*").field("utmcmd2"));//媒介(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn2()+"*").field("utmccn2"));//系列(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct2()+"*").field("utmcct2"));//组(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr2()+"*").field("utmctr2"));//关键词(站内)
		}
		
		if(StringUtils.isNotBlank(model.getBehaviorDetail())){	
			query.must(QueryBuilders.matchPhraseQuery("behaviorDetail",model.getBehaviorDetail()));	// 行为明细
		}
				
		if(StringUtils.isNotBlank(model.getBehaviorType())){	
			query.must(QueryBuilders.matchPhraseQuery("behaviorType", model.getBehaviorType()));//行为类型
		}
		
		if(StringUtils.isNotBlank(model.getEventCategory())){	
			query.must(QueryBuilders.matchPhraseQuery("eventCategory",model.getEventCategory()));	// 事件对象
		}
		
		if(StringUtils.isNotBlank(model.getEventAction())){	
			query.must(QueryBuilders.matchPhraseQuery("eventAction",model.getEventAction()));// 互动类型	
		}
		
		if(StringUtils.isNotBlank(model.getBrowser())){	
			query.must(QueryBuilders.matchPhraseQuery("browser",model.getBrowser()));// 浏览器信息	
		}
		
		if(StringUtils.isNotBlank(model.getPrevUrl())){	
			model.setUrl(QueryParser.escape(model.getUrl())); // 主要就是这一句把特殊字符都转义就可以识别
		    query.must(QueryBuilders.matchPhraseQuery("prevUrl", model.getPrevUrl().replace(":", "?")));
		}
		
		if(StringUtils.isNotBlank(model.getUrl())){	
			model.setUrl(QueryParser.escape(model.getUrl())); // 主要就是这一句把特殊字符都转义就可以识别
		    query.must(QueryBuilders.matchPhraseQuery("url", model.getUrl().replace(":", "?")));
		}
		
		if(StringUtils.isNotBlank(model.getIp())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getIp()+"*").field("ip"));	//终端IP
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getStartTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("visitTime").lte(model.getEndTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getStartTime()).lte(model.getEndTime()).timeZone("Asia/Shanghai"));
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

		List<WebsiteBehaviorDataSource> list = new ArrayList<WebsiteBehaviorDataSource>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			WebsiteBehaviorDataSource entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), WebsiteBehaviorDataSource.class);			
			list.add(entity);
		}
		
		long end = System.currentTimeMillis();
		logger.info("ES查询官网行为数据源报表消耗时间:"+(end-start)/1000+"秒");
		
		page.setRows(list);
		return page;
	}
	
	
	
    /**
     * 官网行为数据源记录查询-不分页-elasticsearch
     * 
     * @param model
     * @return
     * @throws Exception
     */
	public List<WebsiteBehaviorDataSource> findWebsiteBehaviorDataSourceList(DataSourceListModel model)
			throws Exception {
		SearchRequestBuilder srb = client.prepareSearch("web_logs_ods");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
BoolQueryBuilder  query = QueryBuilders.boolQuery();		
		
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));
		}
		
		if(StringUtils.isNotBlank(model.getUserId())){	
			model.setUserId(QueryParser.escape(model.getUserId()));
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserId()+"*").field("userId"));//UUID
		}
		
		if(StringUtils.isNotBlank(model.getSessionId())){	
			model.setSessionId(QueryParser.escape(model.getSessionId())); // 主要就是这一句把特殊字符都转义就可以识别
			//query.must(QueryBuilders.queryStringQuery("*"+model.getSessionId()+"*").field("sessionId"));//会话标识
			query.must(QueryBuilders.matchPhraseQuery("sessionId", model.getSessionId().replace("-", "?")));
		}
		
		if(StringUtils.isNotBlank(model.getPlatformType())){	
			query.must(QueryBuilders.matchPhraseQuery("platformType", model.getPlatformType()));//终端类型
		}
		
		if(StringUtils.isNotBlank(model.getDeviceId())){	
			model.setDeviceId(QueryParser.escape(model.getDeviceId()));
			query.must(QueryBuilders.matchPhraseQuery("deviceId",model.getDeviceId()));//终端标识	
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));//来源(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));//媒介(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn()+"*").field("utmccn"));//系列(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct()+"*").field("utmcct"));//组(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr()+"*").field("utmctr"));//关键词(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr2()+"*").field("utmcsr2"));//来源(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd2()+"*").field("utmcmd2"));//媒介(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn2()+"*").field("utmccn2"));//系列(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct2()+"*").field("utmcct2"));//组(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr2()+"*").field("utmctr2"));//关键词(站内)
		}
		
		if(StringUtils.isNotBlank(model.getBehaviorDetail())){	
			query.must(QueryBuilders.matchPhraseQuery("behaviorDetail",model.getBehaviorDetail()));	// 行为明细
		}
				
		if(StringUtils.isNotBlank(model.getBehaviorType())){	
			query.must(QueryBuilders.matchPhraseQuery("behaviorType", model.getBehaviorType()));//行为类型
		}
		
		if(StringUtils.isNotBlank(model.getEventCategory())){	
			query.must(QueryBuilders.matchPhraseQuery("eventCategory",model.getEventCategory()));	// 事件对象
		}
		
		if(StringUtils.isNotBlank(model.getEventAction())){	
			query.must(QueryBuilders.matchPhraseQuery("eventAction",model.getEventAction()));// 互动类型	
		}
		
		if(StringUtils.isNotBlank(model.getBrowser())){	
			query.must(QueryBuilders.matchPhraseQuery("browser",model.getBrowser()));// 浏览器信息	
		}
		
		if(StringUtils.isNotBlank(model.getPrevUrl())){	
			model.setUrl(QueryParser.escape(model.getUrl())); // 主要就是这一句把特殊字符都转义就可以识别
		    query.must(QueryBuilders.matchPhraseQuery("prevUrl", model.getPrevUrl().replace(":", "?")));
		}
		
		if(StringUtils.isNotBlank(model.getUrl())){	
			model.setUrl(QueryParser.escape(model.getUrl())); // 主要就是这一句把特殊字符都转义就可以识别
		    query.must(QueryBuilders.matchPhraseQuery("url", model.getUrl().replace(":", "?")));
		}
		
		if(StringUtils.isNotBlank(model.getIp())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getIp()+"*").field("ip"));	//终端IP
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getStartTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("visitTime").lte(model.getEndTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getStartTime()).lte(model.getEndTime()).timeZone("Asia/Shanghai"));
		}

		srb.setQuery(query);
		
		// 设置是否按查询匹配度排序  
		srb.setExplain(true);
		srb.addSort(model.getSort(), SortOrder.DESC);
		srb.setFrom(0).setSize(300000);
		
		long start = System.currentTimeMillis();
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		List<WebsiteBehaviorDataSource> list = new ArrayList<WebsiteBehaviorDataSource>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			WebsiteBehaviorDataSource entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), WebsiteBehaviorDataSource.class);		
			list.add(entity);
		}
		
		long end = System.currentTimeMillis();
		logger.info("ES查询官网行为数据源报表消耗时间:"+(end-start)/1000+"秒");
		
		return list;
	}
	
	/**
	 * 直播间数据源记录查询-分页-elasticsearch
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public PageGrid findRoomDataSourcePageList(DataSourceListModel model)
			throws Exception {
		int pageNo = model.getPageNumber();
		int pageSize = model.getPageSize();
		PageGrid page = new PageGrid();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		SearchRequestBuilder srb = client.prepareSearch("room_logs_ods");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();		

		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));
		}
		
		if(StringUtils.isNotBlank(model.getUserId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserId()+"*").field("userId"));//UUID
		}
		
		if(StringUtils.isNotBlank(model.getSessionId())){	
			model.setSessionId(QueryParser.escape(model.getSessionId())); // 主要就是这一句把特殊字符都转义就可以识别
			query.must(QueryBuilders.matchPhraseQuery("sessionId", model.getSessionId()));//会话标识
		}
		
		if(StringUtils.isNotBlank(model.getPlatformType())){	
			query.must(QueryBuilders.matchPhraseQuery("platformType", model.getPlatformType()));//终端类型
		}
		
		if(StringUtils.isNotBlank(model.getDeviceId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getDeviceId()+"*").field("deviceId"));//终端标识
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));//来源(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));//媒介(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn()+"*").field("utmccn"));//系列(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct()+"*").field("utmcct"));//组(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr()+"*").field("utmctr"));//关键词(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr2()+"*").field("utmcsr2"));//来源(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd2()+"*").field("utmcmd2"));//媒介(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn2()+"*").field("utmccn2"));//系列(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct2()+"*").field("utmcct2"));//组(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr2()+"*").field("utmctr2"));//关键词(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUserTel())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserTel()+"*").field("userTel"));	// 电话号码
		}
				
		if(StringUtils.isNotBlank(model.getUserType())){	
			query.must(QueryBuilders.matchPhraseQuery("userType", model.getUserType()));//用户类型
		}
		
		if(StringUtils.isNotBlank(model.getUserName())){
			query.must(QueryBuilders.matchPhraseQuery("userName", model.getUserName()));	// 用户名称
		}
		
		if(StringUtils.isNotBlank(model.getRoomName())){	
			query.must(QueryBuilders.matchPhraseQuery("roomName",model.getRoomName()));// 房间名称
		}
		
		if(StringUtils.isNotBlank(model.getUserSource())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserSource()+"*").field("userSource"));//来源	
		}
		
		if(StringUtils.isNotBlank(model.getUseEquipment())){	
			query.must(QueryBuilders.matchPhraseQuery("useEquipment",model.getUseEquipment()));//使用设备
		}
		
		if(StringUtils.isNotBlank(model.getTradingAccount())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getTradingAccount()+"*").field("tradingAccount"));	//交易帐号
		}
		
		if(StringUtils.isNotBlank(model.getTradingPlatform())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getTradingPlatform()+"*").field("tradingPlatform"));	//交易平台
		}
		
		if(StringUtils.isNotBlank(model.getOperateEntrance())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getOperateEntrance()+"*").field("operateEntrance"));	//用户入口
		}
		
		if(StringUtils.isNotBlank(model.getOperationType())){	
			query.must(QueryBuilders.matchPhraseQuery("operationType", model.getOperationType()));//操作类型
		}
		
		if(StringUtils.isNotBlank(model.getTouristId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getTouristId()+"*").field("touristId"));	//访客ID
		}
		
		if(StringUtils.isNotBlank(model.getNickName())){	
			model.setNickName(QueryParser.escape(model.getNickName()));
			query.must(QueryBuilders.matchPhraseQuery("nickName",model.getNickName()));	//用户昵称
		}
		
		if(StringUtils.isNotBlank(model.getEmail())){	
			query.must(QueryBuilders.queryStringQuery("* "+model.getEmail()+"*").field("email"));	//用户邮箱
		}
		
		if(StringUtils.isNotBlank(model.getVideoName())){	
			query.must(QueryBuilders.matchPhraseQuery("videoName",model.getVideoName()));	//教学视频名称
		}
		
		if(StringUtils.isNotBlank(model.getCourseName())){	
			query.must(QueryBuilders.matchPhraseQuery("courseName",model.getCourseName()));	//课程名称
		}
		
		if(StringUtils.isNotBlank(model.getTeacherName())){	
			query.must(QueryBuilders.matchPhraseQuery("teacherName",model.getTeacherName()));	//老师名称
		}
		
		if(StringUtils.isNotBlank(model.getEventCategory())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getEventCategory()+"*").field("eventCategory"));	//事件对象
		}
		
		if(StringUtils.isNotBlank(model.getEventAction())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getEventAction()+"*").field("eventAction"));	//互动类型
		}
		
		if(StringUtils.isNotBlank(model.getUserIp())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserIp()+"*").field("userIp"));	//访问IP
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("operationTime").lte(model.getEndTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).lte(model.getEndTime()).timeZone("Asia/Shanghai"));
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

		List<RoomDataSource> list = new ArrayList<RoomDataSource>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			RoomDataSource entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), RoomDataSource.class);			
			list.add(entity);
		}
		
		long end = System.currentTimeMillis();
		logger.info("ES查询直播间数据源报表消耗时间:"+(end-start)/1000+"秒");
		
		page.setRows(list);
		return page;
	}
	
    /**
     * 直播间数据源记录查询-不分页-elasticsearch
     * 
     * @param model
     * @return
     * @throws Exception
     */
	public List<RoomDataSource> findRoomDataSourceList(DataSourceListModel model)
			throws Exception {
		SearchRequestBuilder srb = client.prepareSearch("room_logs_ods");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();		

		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));
		}
		
		if(StringUtils.isNotBlank(model.getUserId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserId()+"*").field("userId"));//UUID
		}
		
		if(StringUtils.isNotBlank(model.getSessionId())){	
			model.setSessionId(QueryParser.escape(model.getSessionId())); // 主要就是这一句把特殊字符都转义就可以识别
			query.must(QueryBuilders.matchPhraseQuery("sessionId", model.getSessionId()));//会话标识
		}
		
		if(StringUtils.isNotBlank(model.getPlatformType())){	
			query.must(QueryBuilders.matchPhraseQuery("platformType", model.getPlatformType()));//终端类型
		}
		
		if(StringUtils.isNotBlank(model.getDeviceId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getDeviceId()+"*").field("deviceId"));//终端标识
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));//来源(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));//媒介(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn()+"*").field("utmccn"));//系列(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct()+"*").field("utmcct"));//组(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr()+"*").field("utmctr"));//关键词(站外)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcsr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr2()+"*").field("utmcsr2"));//来源(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcmd2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd2()+"*").field("utmcmd2"));//媒介(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmccn2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn2()+"*").field("utmccn2"));//系列(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmcct2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct2()+"*").field("utmcct2"));//组(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUtmctr2())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr2()+"*").field("utmctr2"));//关键词(站内)
		}
		
		if(StringUtils.isNotBlank(model.getUserTel())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserTel()+"*").field("userTel"));	// 电话号码
		}
				
		if(StringUtils.isNotBlank(model.getUserType())){	
			query.must(QueryBuilders.matchPhraseQuery("userType", model.getUserType()));//用户类型
		}
		
		if(StringUtils.isNotBlank(model.getUserName())){
			query.must(QueryBuilders.matchPhraseQuery("userName", model.getUserName()));	// 用户名称
		}
		
		if(StringUtils.isNotBlank(model.getRoomName())){	
			query.must(QueryBuilders.matchPhraseQuery("roomName",model.getRoomName()));// 房间名称
		}
		
		if(StringUtils.isNotBlank(model.getUserSource())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserSource()+"*").field("userSource"));//来源	
		}
		
		if(StringUtils.isNotBlank(model.getUseEquipment())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUseEquipment()+"*").field("useEquipment"));	//使用设备
		}
		
		if(StringUtils.isNotBlank(model.getTradingAccount())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getTradingAccount()+"*").field("tradingAccount"));	//交易帐号
		}
		
		if(StringUtils.isNotBlank(model.getTradingPlatform())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getTradingPlatform()+"*").field("tradingPlatform"));	//交易平台
		}
		
		if(StringUtils.isNotBlank(model.getOperateEntrance())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getOperateEntrance()+"*").field("operateEntrance"));	//用户入口
		}
		
		if(StringUtils.isNotBlank(model.getOperationType())){	
			query.must(QueryBuilders.matchPhraseQuery("operationType", model.getOperationType()));//操作类型
		}
		
		if(StringUtils.isNotBlank(model.getTouristId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getTouristId()+"*").field("touristId"));	//访客ID
		}
		
		if(StringUtils.isNotBlank(model.getNickName())){	
			model.setNickName(QueryParser.escape(model.getNickName()));
			query.must(QueryBuilders.matchPhraseQuery("nickName",model.getNickName()));	//用户昵称
		}
		
		if(StringUtils.isNotBlank(model.getEmail())){	
			query.must(QueryBuilders.queryStringQuery("* "+model.getEmail()+"*").field("email"));	//用户邮箱
		}
		
		if(StringUtils.isNotBlank(model.getVideoName())){	
			query.must(QueryBuilders.matchPhraseQuery("videoName",model.getVideoName()));	//教学视频名称
		}
		
		if(StringUtils.isNotBlank(model.getCourseName())){	
			query.must(QueryBuilders.matchPhraseQuery("courseName",model.getCourseName()));	//课程名称
		}
		
		if(StringUtils.isNotBlank(model.getTeacherName())){	
			query.must(QueryBuilders.matchPhraseQuery("teacherName",model.getTeacherName()));	//老师名称
		}
		
		if(StringUtils.isNotBlank(model.getEventCategory())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getEventCategory()+"*").field("eventCategory"));	//事件对象
		}
		
		if(StringUtils.isNotBlank(model.getEventAction())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getEventAction()+"*").field("eventAction"));	//互动类型
		}
		
		if(StringUtils.isNotBlank(model.getUserIp())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserIp()+"*").field("userIp"));	//访问IP
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("operationTime").lte(model.getEndTime()).timeZone("Asia/Shanghai"));
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
			query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).lte(model.getEndTime()).timeZone("Asia/Shanghai"));
		}
		
		srb.setQuery(query);
		
		// 设置是否按查询匹配度排序  
		srb.setExplain(true);
		srb.addSort(model.getSort(), SortOrder.DESC);
		srb.setFrom(0).setSize(300000);
		
		long start = System.currentTimeMillis();
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		List<RoomDataSource> list = new ArrayList<RoomDataSource>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			RoomDataSource entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), RoomDataSource.class);		
			list.add(entity);
		}
		
		long end = System.currentTimeMillis();
		logger.info("ES查询直播间数据源报表消耗时间:"+(end-start)/1000+"秒");
		
		return list;
	}
	
	
}
