package com.gw.das.business.dao.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.room.entity.DasChartFlowDetailES;
import com.gw.das.business.dao.room.entity.DasChartFlowDetailSearchBean;
import com.gw.das.business.dao.room.entity.DasChartFlowDetailUrl;

/**
 * 用户行为列表报表DAO
 * 
 * @author darren
 * @since 2017-07-13
 *
 */
@Repository
public class DasRoomDetailESDao {
	
	@Autowired
	private TransportClient client;
	
	/**
     * 不分页查询用户行为列表报表记录
     * 
     * @param model
     * @return
     * @throws Exception
     */
	public List<DasChartFlowDetailES> findDasFlowDetailList(DasChartFlowDetailSearchBean model)
			throws Exception {
		SearchRequestBuilder srb = client.prepareSearch("das_room_detail_index");
		
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		if(BusinessPlatformEnum.Fx.getValue().equals(model.getBusinessPlatform())){
			srb.setTypes("fx");
		}
		if(BusinessPlatformEnum.Pm.getValue().equals(model.getBusinessPlatform())){
			srb.setTypes("pm");
		}
		if(BusinessPlatformEnum.Hx.getValue().equals(model.getBusinessPlatform())){
			srb.setTypes("hx");
		}
        if(BusinessPlatformEnum.Cf.getValue().equals(model.getBusinessPlatform())){
        	srb.setTypes("cf");
		}
        
        BoolQueryBuilder query = QueryBuilders.boolQuery();			
		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getEndTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			   query.filter(QueryBuilders.rangeQuery("startTime").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
				query.filter(QueryBuilders.rangeQuery("endTime").lte(model.getEndTime()));
			}else{
				 query.filter(QueryBuilders.rangeQuery("startTime").gte(model.getStartTime()));	
				 query.filter(QueryBuilders.rangeQuery("endTime").lte(model.getEndTime()));
			}			
		}	
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){
        	query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
        }
		if(StringUtils.isNotBlank(model.getDevicetype())){//访问客户端
        	query.must(QueryBuilders.matchPhraseQuery("devicetype", model.getDevicetype()));
        }
		if(StringUtils.isNotBlank(model.getBehaviorType())){//行为类型
        	query.must(QueryBuilders.matchPhraseQuery("behaviorType", model.getBehaviorType()));
        }			
		if(StringUtils.isNotBlank(model.getUserId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserId()+"*").field("userId"));	
		}
        if (StringUtils.isNotBlank(model.getUtmcct())) {//组
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct()+"*").field("utmcct"));	
        }
        if (StringUtils.isNotBlank(model.getUtmccn())) {//系列
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn()+"*").field("utmccn"));	
        }
        if (StringUtils.isNotBlank(model.getUtmcmd())) {//媒介
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));	
        }
        if (StringUtils.isNotBlank(model.getUtmctr())) {//关键字
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr()+"*").field("utmctr"));
        }
        if (StringUtils.isNotBlank(model.getIp())) {//IP
		   query.must(QueryBuilders.queryStringQuery("*"+model.getIp()+"*").field("ip"));
        }
        if (StringUtils.isNotBlank(model.getUtmcsr())) {//来源
    		query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));
        }
        
        srb.setQuery(query);			
		
		// 设置是否按查询匹配度排序  
		srb.setExplain(true);
		srb.addSort(model.getSort(), SortOrder.DESC);
		srb.setFrom(0).setSize(300000);
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		List<DasChartFlowDetailES> list = new ArrayList<DasChartFlowDetailES>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();								
			DasChartFlowDetailES entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), DasChartFlowDetailES.class);
			
			String behaviorDetail = entity.getBehaviorDetail();
			if(StringUtils.isNotBlank(behaviorDetail)){
				if(entity.getBehaviorType().equals("3") ||
						entity.getBehaviorType().equals("4") ||
						entity.getBehaviorType().equals("5")){
					if(behaviorDetail.startsWith("D") 
							|| behaviorDetail.startsWith("N") 
							|| behaviorDetail.startsWith("R")){
						behaviorDetail = behaviorDetail.substring(1, behaviorDetail.length());
					}
				}
			}
			entity.setBehaviorDetail(behaviorDetail);
			entity.setRowKey(String.valueOf(source.get("rowkey")));
			list.add(entity);
		}
		return list;
	}
	
	/**
	 * 
	 * 分页查询用户行为列表报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid findDasFlowDetailPageList(DasChartFlowDetailSearchBean model)
			throws Exception {
		int pageNo = model.getPageNumber();
		int pageSize = model.getPageSize();
		PageGrid page = new PageGrid();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		SearchRequestBuilder srb = client.prepareSearch("das_room_detail_index");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("fx");
		}
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("pm");
		}
		if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("hx");
		}
        if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
        	srb.setTypes("cf");
		}
        
        BoolQueryBuilder query = QueryBuilders.boolQuery();			
		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getEndTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			   query.filter(QueryBuilders.rangeQuery("startTime").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getEndTime())){
				query.filter(QueryBuilders.rangeQuery("endTime").lte(model.getEndTime()));
			}else{
				 query.filter(QueryBuilders.rangeQuery("startTime").gte(model.getStartTime()));	
				 query.filter(QueryBuilders.rangeQuery("endTime").lte(model.getEndTime()));
			}			
		}	
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){
        	query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
        }
		if(StringUtils.isNotBlank(model.getDevicetype())){//访问客户端
        	query.must(QueryBuilders.matchPhraseQuery("devicetype", model.getDevicetype()));
        }
		if(StringUtils.isNotBlank(model.getBehaviorType())){//行为类型
        	query.must(QueryBuilders.matchPhraseQuery("behaviorType", model.getBehaviorType()));
        }			
		if(StringUtils.isNotBlank(model.getUserId())){	
			query.must(QueryBuilders.queryStringQuery("*"+model.getUserId()+"*").field("userId"));	
		}
        if (StringUtils.isNotBlank(model.getUtmcct())) {//组
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcct()+"*").field("utmcct"));	
        }
        if (StringUtils.isNotBlank(model.getUtmccn())) {//系列
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmccn()+"*").field("utmccn"));	
        }
        if (StringUtils.isNotBlank(model.getUtmcmd())) {//媒介
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));	
        }
        if (StringUtils.isNotBlank(model.getUtmctr())) {//关键字
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmctr()+"*").field("utmctr"));
        }
        if (StringUtils.isNotBlank(model.getIp())) {//IP
		   query.must(QueryBuilders.queryStringQuery("*"+model.getIp()+"*").field("ip"));
        }
        if (StringUtils.isNotBlank(model.getUtmcsr())) {//来源
    		query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));
        }     
        
        srb.setQuery(query);		
		
		//设置分页信息 
		srb.setFrom((pageNo - 1) * pageSize).setSize(pageSize)
		// 设置是否按查询匹配度排序  
		.setExplain(true);
		srb.addSort(model.getSort(), SortOrder.DESC);
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		page.setTotal((int)hits.getTotalHits());

		List<DasChartFlowDetailES> list = new ArrayList<DasChartFlowDetailES>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();								
			DasChartFlowDetailES entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), DasChartFlowDetailES.class);
			
			String behaviorDetail = entity.getBehaviorDetail();			
			if(StringUtils.isNotBlank(behaviorDetail)){
				if(entity.getBehaviorType().equals("3") ||
						entity.getBehaviorType().equals("4") ||
						entity.getBehaviorType().equals("5")){
					if(behaviorDetail.startsWith("D") 
							|| behaviorDetail.startsWith("N") 
							|| behaviorDetail.startsWith("R")){
						behaviorDetail = behaviorDetail.substring(1, behaviorDetail.length());
					}
				}
			}
			entity.setBehaviorDetail(behaviorDetail);
			entity.setRowKey(String.valueOf(source.get("rowkey")));
			list.add(entity);
		}
		page.setRows(list);
		return page;
	}
	
	/**
	 * 
	 * 用户详细-页面浏览详细列表分页
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid findDasflowUrlDetailPageList(DasChartFlowDetailUrl model)
			throws Exception {
		int pageNo = model.getPageNumber();
		int pageSize = model.getPageSize();
		PageGrid page = new PageGrid();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		SearchRequestBuilder srb = client.prepareSearch("das_room_detail_index");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("fx");
		}
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("pm");
		}
		if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("hx");
		}
        if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
        	srb.setTypes("cf");
		}
        
        BoolQueryBuilder query = QueryBuilders.boolQuery();			
		if(StringUtils.isNotBlank(model.getVisitTimeStart()) || StringUtils.isNotBlank(model.getVisitTimeEnd())){	
			if(StringUtils.isNotBlank(model.getVisitTimeStart()) && !"null".equals(model.getVisitTimeStart()) 
					&& StringUtils.isBlank(model.getVisitTimeEnd())  && "null".equals(model.getVisitTimeEnd()) ){
			   query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getVisitTimeStart()));
			}else if(StringUtils.isBlank(model.getVisitTimeStart())  && "null".equals(model.getVisitTimeStart()) 
					&& StringUtils.isNotBlank(model.getVisitTimeEnd()) && !"null".equals(model.getVisitTimeEnd())){
				query.filter(QueryBuilders.rangeQuery("visitTime").lte(model.getVisitTimeEnd()));
			}else{
				 query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getVisitTimeStart()).lte(model.getVisitTimeEnd()));	
			}			
		}			
		if(StringUtils.isNotBlank(model.getBusinessPlatform()) && !"null".equals(model.getBusinessPlatform())){
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
		}
		if(StringUtils.isNotBlank(model.getDevicetype()) && !"null".equals(model.getDevicetype())){//访问客户端
        	query.must(QueryBuilders.matchPhraseQuery("devicetype", model.getDevicetype()));
        }
		if(StringUtils.isNotBlank(model.getUtmcsr()) && !"null".equals(model.getUtmcsr())){	//来源
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));	
		}
        if (StringUtils.isNotBlank(model.getFlowDetailUrl()) && !"null".equals(model.getFlowDetailUrl())) { // 访问URL
			query.must(QueryBuilders.queryStringQuery("*"+model.getFlowDetailUrl()+"*").field("flowDetailUrl"));	
        }
        if (StringUtils.isNotBlank(model.getUtmcmd()) && !"null".equals(model.getUtmcmd())) { // 媒介
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));	
        }
        if (StringUtils.isNotBlank(model.getFlowDetailId()) && !"null".equals(model.getFlowDetailId())) { //访问ID
        	query.must(QueryBuilders.matchPhraseQuery("flowDetailId", model.getFlowDetailId())); //matchPhraseQuery精确查询
        }       
        
        srb.setQuery(query);	
		
		//设置分页信息 
		srb.setFrom((pageNo - 1) * pageSize).setSize(pageSize)
		// 设置是否按查询匹配度排序  
		.setExplain(true);
		srb.addSort(model.getSort(), SortOrder.DESC);
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		page.setTotal((int)hits.getTotalHits());

		List<DasChartFlowDetailUrl> list = new ArrayList<DasChartFlowDetailUrl>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			DasChartFlowDetailUrl entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), DasChartFlowDetailUrl.class);			
			list.add(entity);
		}
		page.setRows(list);
		return page;
	}
	
	/**
	 * 
	 * 用户详细-页面浏览详细列表分页
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DasChartFlowDetailUrl> findDasflowUrlDetailESList(DasChartFlowDetailUrl model)
			throws Exception {
		SearchRequestBuilder srb = client.prepareSearch("das_room_detail_index");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		if(BusinessPlatformEnum.Fx.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("fx");
		}
		if(BusinessPlatformEnum.Pm.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("pm");
		}
		if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
			srb.setTypes("hx");
		}
        if(BusinessPlatformEnum.Cf.getLabelKey().equals(model.getBusinessPlatform())){
        	srb.setTypes("cf");
		}
        
        BoolQueryBuilder query = QueryBuilders.boolQuery();			
		if(StringUtils.isNotBlank(model.getVisitTimeStart()) || StringUtils.isNotBlank(model.getVisitTimeEnd())){	
			if(StringUtils.isNotBlank(model.getVisitTimeStart()) && !"null".equals(model.getVisitTimeStart()) 
					&& StringUtils.isBlank(model.getVisitTimeEnd())  && "null".equals(model.getVisitTimeEnd()) ){
			   query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getVisitTimeStart()));
			}else if(StringUtils.isBlank(model.getVisitTimeStart())  && "null".equals(model.getVisitTimeStart()) 
					&& StringUtils.isNotBlank(model.getVisitTimeEnd()) && !"null".equals(model.getVisitTimeEnd())){
				query.filter(QueryBuilders.rangeQuery("visitTime").lte(model.getVisitTimeEnd()));
			}else{
				 query.filter(QueryBuilders.rangeQuery("visitTime").gte(model.getVisitTimeStart()).lte(model.getVisitTimeEnd()));	
			}			
		}			
		if(StringUtils.isNotBlank(model.getBusinessPlatform()) && !"null".equals(model.getBusinessPlatform())){
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
		}
		if(StringUtils.isNotBlank(model.getDevicetype()) && !"null".equals(model.getDevicetype())){//访问客户端
        	query.must(QueryBuilders.matchPhraseQuery("devicetype", model.getDevicetype()));
        }
		if(StringUtils.isNotBlank(model.getUtmcsr()) && !"null".equals(model.getUtmcsr())){	//来源
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcsr()+"*").field("utmcsr"));	
		}
        if (StringUtils.isNotBlank(model.getFlowDetailUrl()) && !"null".equals(model.getFlowDetailUrl())) { // 访问URL
			query.must(QueryBuilders.queryStringQuery("*"+model.getFlowDetailUrl()+"*").field("flowDetailUrl"));	
        }
        if (StringUtils.isNotBlank(model.getUtmcmd()) && !"null".equals(model.getUtmcmd())) { // 媒介
			query.must(QueryBuilders.queryStringQuery("*"+model.getUtmcmd()+"*").field("utmcmd"));	
        }
        if (StringUtils.isNotBlank(model.getFlowDetailId()) && !"null".equals(model.getFlowDetailId())) { //访问ID
        	query.must(QueryBuilders.matchPhraseQuery("flowDetailId", model.getFlowDetailId())); //matchPhraseQuery精确查询
        }       
        
        srb.setQuery(query);	
		
		srb.addSort(model.getSort(), SortOrder.DESC);
		srb.setFrom(0).setSize(300000);
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();

		List<DasChartFlowDetailUrl> list = new ArrayList<DasChartFlowDetailUrl>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			DasChartFlowDetailUrl entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), DasChartFlowDetailUrl.class);
			list.add(entity);
		}
		
		return list;
	}
	
}
