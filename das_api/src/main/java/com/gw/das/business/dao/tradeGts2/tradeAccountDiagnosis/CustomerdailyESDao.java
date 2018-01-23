package com.gw.das.business.dao.tradeGts2.tradeAccountDiagnosis;

import java.util.ArrayList;
import java.util.List;
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
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.tradeGts2.entity.CustomerdailyVO;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 结算日报表DAO
 * 
 * @author darren
 * @since 2017-03-30
 *
 */
@Repository
public class CustomerdailyESDao {
	
	@Autowired
	private TransportClient client;
	
	/**
	 * 
	 * 分页查询结算日报表记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public PageGrid findCustomerdailyPageList(TradeSearchModel model)
			throws Exception {
		int pageNo = model.getPageNumber();
		int pageSize = model.getPageSize();
		PageGrid page = new PageGrid();
		page.setPageNumber(pageNo);
		page.setPageSize(pageSize);
		
		SearchRequestBuilder srb = client.prepareSearch("es_gts2customerdaily_d");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setTypes("es");
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();		
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessplatform", model.getBusinessPlatform()));
		}
		if(StringUtils.isNotBlank(model.getAccountno())){	
			//query.must(QueryBuilders.fuzzyQuery("accountno", model.getAccountno()));
			query.must(QueryBuilders.queryStringQuery("*"+model.getAccountno()+"*").field("accountno"));	
		}
		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getStartTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getStartTime())){
				query.filter(QueryBuilders.rangeQuery("reportdate").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getStartTime())){
				query.filter(QueryBuilders.rangeQuery("reportdate").lte(model.getStartTime()));
			}else{
				query.filter(QueryBuilders.rangeQuery("reportdate").gte(model.getStartTime()).lte(model.getEndTime()));
			}			
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
		List<CustomerdailyVO> list = new ArrayList<CustomerdailyVO>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			if(source.containsKey("deposit_withdraw")){
				source.put("depositWithdraw", source.get("deposit_withdraw"));
				source.remove("deposit_withdraw");
			}
			if(source.containsKey("balance_previousbalance")){
				source.put("balancePreviousbalance", source.get("balance_previousbalance"));
				source.remove("balance_previousbalance");
			}
			CustomerdailyVO entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), CustomerdailyVO.class);			
			list.add(entity);
		}
		page.setRows(list);
		return page;
	}

	/**
	 * 不分页查询结算日报表记录
	 * 
	 * @param businessplatform
	 * @param accountno
	 * @param startTime
	 * @param stopTime
	 * @return
	 * @throws Exception
	 */
	public List<CustomerdailyVO> findCustomerdailyList(TradeSearchModel model)
			throws Exception {
		SearchRequestBuilder srb = client.prepareSearch("es_gts2customerdaily_d");
		//设置查询类型  
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
		srb.setTypes("es");
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();		
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessplatform", model.getBusinessPlatform()));
		}
		if(StringUtils.isNotBlank(model.getAccountno())){	
			//query.must(QueryBuilders.fuzzyQuery("accountno", model.getAccountno()));
			query.must(QueryBuilders.queryStringQuery("*"+model.getAccountno()+"*").field("accountno"));	
		}
		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getStartTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getStartTime())){
				query.filter(QueryBuilders.rangeQuery("reportdate").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime()) && StringUtils.isNotBlank(model.getStartTime())){
				query.filter(QueryBuilders.rangeQuery("reportdate").lte(model.getStartTime()));
			}else{
				query.filter(QueryBuilders.rangeQuery("reportdate").gte(model.getStartTime()).lte(model.getEndTime()));
			}			
		}		
		srb.setQuery(query);
		
		// 设置是否按查询匹配度排序  
		srb.setExplain(true);
		srb.addSort("reportdate", SortOrder.DESC);
		srb.setFrom(0).setSize(300000);
		
		SearchResponse response = srb.execute().actionGet();
		SearchHits hits = response.getHits();
		List<CustomerdailyVO> list = new ArrayList<CustomerdailyVO>();
		for (SearchHit searchHit : hits) {
			Map<String, Object> source = searchHit.getSource();
			if(source.containsKey("deposit_withdraw")){
				source.put("depositWithdraw", source.get("deposit_withdraw"));
				source.remove("deposit_withdraw");
			}
			if(source.containsKey("balance_previousbalance")){
				source.put("balancePreviousbalance", source.get("balance_previousbalance"));
				source.remove("balance_previousbalance");
			}
			CustomerdailyVO entity = JacksonUtil.readValue(JacksonUtil.toJSon(source), CustomerdailyVO.class);	
			list.add(entity);
		}
		return list;
	}
	
}
