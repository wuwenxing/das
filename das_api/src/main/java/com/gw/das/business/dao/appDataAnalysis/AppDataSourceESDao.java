package com.gw.das.business.dao.appDataAnalysis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.DeviceTypeEnum;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.ListPageUtil;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.appDataAnalysis.entity.DasAppOdsVO;

/**
 * app事件统计报表DAO
 * 
 * @author darren
 * @since 2017-04-28
 *
 */
@Repository
public class AppDataSourceESDao {
	
	private static final Logger logger = LoggerFactory.getLogger(AppDataSourceESDao.class);
	
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
		srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
	
		//设置分页信息 
		srb.setSize(0)
		// 设置是否按查询匹配度排序  
		.setExplain(true);
		srb.addSort("operationTime", SortOrder.DESC);
				
		BoolQueryBuilder  query = QueryBuilders.boolQuery();	
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getEndTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			   query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime())&& StringUtils.isNotBlank(model.getEndTime())){
				query.filter(QueryBuilders.rangeQuery("operationTime").lt(model.getEndTime()));
			}else{
				query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).lt(model.getEndTime()));	
			}			
		}
		
        TermsAggregationBuilder termsBuilder0 = AggregationBuilders.terms("eventCategoryAgg").field("eventCategory").size(1000000).missing(" "); //事件类别
        if(StringUtils.isNotBlank(model.getEventCategory())){
        	query.must(QueryBuilders.matchPhraseQuery("eventCategory",model.getEventCategory().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventCategory().toLowerCase()+"*").field("eventCategory"));
        }
		
		TermsAggregationBuilder termsBuilder1 = AggregationBuilders.terms("eventActionAgg").field("eventAction").size(1000000).missing(" "); //事件操作
		if(StringUtils.isNotBlank(model.getEventAction())){
        	query.must(QueryBuilders.matchPhraseQuery("eventAction",model.getEventAction().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventAction().toLowerCase()+"*").field("eventAction"));
        }
		
		TermsAggregationBuilder termsBuilder2 = AggregationBuilders.terms("eventLabelAgg").field("eventLabel").size(1000000).missing(" "); //事件标签
		if(StringUtils.isNotBlank(model.getEventLabel())){
        	query.must(QueryBuilders.matchPhraseQuery("eventLabel",model.getEventLabel().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventLabel().toLowerCase()+"*").field("eventLabel"));
        }
		
		TermsAggregationBuilder termsBuilder3 = AggregationBuilders.terms("eventValueAgg").field("eventValue").size(1000000).missing(" "); //事件参数
		if(StringUtils.isNotBlank(model.getEventValue())){
        	query.must(QueryBuilders.matchPhraseQuery("eventValue",model.getEventValue().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventValue().toLowerCase()+"*").field("eventValue"));
        }
		
		termsBuilder0.subAggregation(termsBuilder1);
	    termsBuilder1.subAggregation(termsBuilder2);
		termsBuilder2.subAggregation(termsBuilder3);
		
		TermsAggregationBuilder termsBuilder4 = null;
		if(StringUtils.isNotBlank(model.getDeviceType())){
			if(DeviceTypeEnum.ANDROID.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.ANDROID.getValue());
			}
			if(DeviceTypeEnum.IOS.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.IOS.getValue());
			}
			if(DeviceTypeEnum.PCUI.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.PCUI.getValue());
			}
			if(DeviceTypeEnum.WEBUI.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.WEBUI.getValue());
			}
			query.must(QueryBuilders.matchPhraseQuery("deviceType",model.getDeviceType()));
			
		}
		if(model.isDeviceTypeChecked()){
			termsBuilder4 = AggregationBuilders.terms("deviceTypeAgg").field("deviceType").size(1000000); //设备平台
			termsBuilder3.subAggregation(termsBuilder4);
		}
		
		TermsAggregationBuilder termsBuilder5 = null;
		if(StringUtils.isNotBlank(model.getPlatformType())){
			query.must(QueryBuilders.matchPhraseQuery("platformType",model.getPlatformType()));
			
		}
		if(model.isPlatformTypeChecked()){
			termsBuilder5 = AggregationBuilders.terms("platformTypeAgg").field("platformType").size(1000000); //交易平台
			if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder5);
			}else{
				termsBuilder3.subAggregation(termsBuilder5);
			}
		}
		
		TermsAggregationBuilder termsBuilder6 = null;
		if(StringUtils.isNotBlank(model.getPlatformName())){
			query.must(QueryBuilders.matchPhraseQuery("platformName",model.getPlatformName().toLowerCase()));
			//query.must(QueryBuilders.queryStringQuery("*"+model.getPlatformName().toLowerCase()+"*").field("platformName"));
		}
		if(model.isPlatformNameChecked()){
			termsBuilder6 = AggregationBuilders.terms("platformNameAgg").field("platformName.keyword").size(1000000); //马甲包
			if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder6);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder6);
			}else{
				termsBuilder3.subAggregation(termsBuilder6);
			}
		}
		
		TermsAggregationBuilder termsBuilder7 = null;
		if(StringUtils.isNotBlank(model.getPlatformVersion())){
			//query.must(QueryBuilders.matchPhraseQuery("platformVersion",model.getPlatformVersion().toLowerCase()));
			query.must(QueryBuilders.queryStringQuery("*"+model.getPlatformVersion().toLowerCase()+"*").field("platformVersion"));
		}
		if(model.isPlatformVersionChecked()){
			termsBuilder7 = AggregationBuilders.terms("platformVersionAgg").field("platformVersion").size(1000000); //版本
			if(termsBuilder6 != null){
				termsBuilder6.subAggregation(termsBuilder7);
			}else if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder7);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder7);
			}else{
				termsBuilder3.subAggregation(termsBuilder7);
			}
		}
		
		TermsAggregationBuilder termsBuilder8 = null;
		if(StringUtils.isNotBlank(model.getUserType())){
			query.must(QueryBuilders.matchPhraseQuery("userType",model.getUserType()));
		}
		if(model.isUserTypeChecked()){
			termsBuilder8 = AggregationBuilders.terms("userTypeAgg").field("userType").size(1000000); //客户类型
			if(termsBuilder7 != null){
				termsBuilder7.subAggregation(termsBuilder8);
			}else if(termsBuilder6 != null){
				termsBuilder6.subAggregation(termsBuilder8);
			}else if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder8);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder8);
			}else{
				termsBuilder3.subAggregation(termsBuilder8);
			}
		}
		
		TermsAggregationBuilder termsBuilder9 = null;
        if(StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())){
        	query.must(QueryBuilders.matchPhraseQuery("channel",model.getChannel().toLowerCase()));
		}
        if(model.isChannelChecked()){
        	termsBuilder9 = AggregationBuilders.terms("channelAgg").field("channel").size(1000000); //渠道
        	if(termsBuilder8 != null){
				termsBuilder8.subAggregation(termsBuilder9);
			}else if(termsBuilder7 != null){
				termsBuilder7.subAggregation(termsBuilder9);
			}else if(termsBuilder6 != null){
				termsBuilder6.subAggregation(termsBuilder9);
			}else if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder9);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder9);
			}else{
				termsBuilder3.subAggregation(termsBuilder9);
			}
        }

		srb.setQuery(query);
		
		TermsAggregationBuilder termsBuilder10 = AggregationBuilders.terms("deviceidAgg").field("deviceid").size(1000000); //设备唯一标识
		if(termsBuilder9 != null){
			termsBuilder9.subAggregation(termsBuilder10);
		}else if(termsBuilder8 != null){
			termsBuilder8.subAggregation(termsBuilder10);
		}else if(termsBuilder7 != null){
			termsBuilder7.subAggregation(termsBuilder10);
		}else if(termsBuilder6 != null){
			termsBuilder6.subAggregation(termsBuilder10);
		}else if(termsBuilder5 != null){
			termsBuilder5.subAggregation(termsBuilder10);
		}else if(termsBuilder4 != null){
			termsBuilder4.subAggregation(termsBuilder10);
		}else{
			termsBuilder3.subAggregation(termsBuilder10);
		}
		
		srb.addAggregation(termsBuilder0);
		
		long start = System.currentTimeMillis();
		
		SearchResponse sr = srb.execute().actionGet();
		
		List<DasAppOdsVO> list = new ArrayList<DasAppOdsVO>();
		
		Map<String, Aggregation> aggMap = sr.getAggregations().asMap();
		StringTerms eventCategoryTerms = (StringTerms) aggMap.get("eventCategoryAgg");
		Iterator<Bucket> eventCategoryBucketIt = eventCategoryTerms.getBuckets().iterator();
		while (eventCategoryBucketIt.hasNext()) {
			Bucket eventCategoryBucket = eventCategoryBucketIt.next();
			
			StringTerms eventActionTerms = (StringTerms) eventCategoryBucket.getAggregations().asMap().get("eventActionAgg");//事件操作
			Iterator<Bucket> eventActionBucketIt = eventActionTerms.getBuckets().iterator();
			while (eventActionBucketIt.hasNext()) {
				Bucket eventActionBucket = eventActionBucketIt.next();
				
				StringTerms eventLabelTerms = (StringTerms) eventActionBucket.getAggregations().asMap().get("eventLabelAgg");//事件标签
				Iterator<Bucket> eventLabelBucketIt = eventLabelTerms.getBuckets().iterator();
				while (eventLabelBucketIt.hasNext()) {
					Bucket eventLabelBucket = eventLabelBucketIt.next();
					
					StringTerms eventValueTerms = (StringTerms) eventLabelBucket.getAggregations().asMap().get("eventValueAgg");//事件参数
					Iterator<Bucket> eventValueBucketIt = eventValueTerms.getBuckets().iterator();
					while (eventValueBucketIt.hasNext()) {
						Bucket eventValueBucket = eventValueBucketIt.next();
						
						StringTerms deviceTypeTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("deviceTypeAgg");//设备平台
						if(null == deviceTypeTerms){
							StringTerms platformTypeTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("platformTypeAgg");//交易平台
							if(null == platformTypeTerms){
								StringTerms platformNameTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
								if(null == platformNameTerms){
									StringTerms platformVersionTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("platformVersionAgg");//版本
									if(null == platformVersionTerms){
										StringTerms userTypeTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
										if(null == userTypeTerms){
											StringTerms channelTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("channelAgg");//渠道
											if(null == channelTerms){
												StringTerms deviceidTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("deviceidAgg");
												Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
												long deviceidNum = 0;
												while (deviceidBucketIt.hasNext()) {
													Bucket deviceidBucket = deviceidBucketIt.next();
													deviceidNum+= deviceidBucket.getDocCount();				
												}
												
												DasAppOdsVO entity = new DasAppOdsVO();
												entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
												entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
												entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
												entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
												entity.setDeviceidNum(deviceidNum);
												entity.setDeviceidCount(deviceidTerms.getBuckets().size());
												list.add(entity);
											}else{
												Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
												while (channelBucketIt.hasNext()) {
													Bucket channelBucket = channelBucketIt.next();
													
													StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setChannel(String.valueOf(channelBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}
											}
											
										}else{
											Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
											while (userTypeBucketIt.hasNext()) {
												Bucket userTypeBucket = userTypeBucketIt.next();
												
												StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setUserType(String.valueOf(userTypeBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}
										}
										
									}else{
										Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
										while (platformVersionBucketIt.hasNext()) {
											Bucket platformVersionBucket = platformVersionBucketIt.next();
											
											StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
										
										}
									}
									
								}else{
									Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
									while (platformNameBucketIt.hasNext()) {
										Bucket platformNameBucket = platformNameBucketIt.next();
										
										StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
										if(null == platformVersionTerms){
											StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
											
										}else{
											Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
											while (platformVersionBucketIt.hasNext()) {
												Bucket platformVersionBucket = platformVersionBucketIt.next();
												
												StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}
										}
										
									}
								}
								
							}else{
								Iterator<Bucket> platformTypeBucketIt = platformTypeTerms.getBuckets().iterator();
								while (platformTypeBucketIt.hasNext()) {
									Bucket platformTypeBucket = platformTypeBucketIt.next();
									
									StringTerms platformNameTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
									if(null == platformNameTerms){
										StringTerms platformVersionTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformVersionAgg");//版本
										if(null == platformVersionTerms){
											StringTerms userTypeTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
											
										}else{
											Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
											while (platformVersionBucketIt.hasNext()) {
												Bucket platformVersionBucket = platformVersionBucketIt.next();
												
												StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){

													StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}
										}
										
									}else{
										Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
										while (platformNameBucketIt.hasNext()) {
											Bucket platformNameBucket = platformNameBucketIt.next();
											
											StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
											if(null == platformVersionTerms){
												StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
													
													}
												}
												
											}else{
												Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
												while (platformVersionBucketIt.hasNext()) {
													Bucket platformVersionBucket = platformVersionBucketIt.next();
													
													StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
														
														}
													}
												
												}
											}
											
										}
									}
									
								}
							}
							
						}else{
							Iterator<Bucket> deviceTypeBucketIt = deviceTypeTerms.getBuckets().iterator();
							while (deviceTypeBucketIt.hasNext()) {
								Bucket deviceTypeBucket = deviceTypeBucketIt.next();
								
								StringTerms platformTypeTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("platformTypeAgg");//交易平台
								if(null == platformTypeTerms){
									StringTerms platformNameTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
									if(null ==platformNameTerms){
										StringTerms platformVersionTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("platformVersionAgg");//版本
										if(null == platformVersionTerms){
											StringTerms userTypeTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
											
										}else{
											Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
											while (platformVersionBucketIt.hasNext()) {
												Bucket platformVersionBucket = platformVersionBucketIt.next();
												
												StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}
										}
										
									}else{
										Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
										while (platformNameBucketIt.hasNext()) {
											Bucket platformNameBucket = platformNameBucketIt.next();
											
											StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
											if(null == platformVersionTerms){
												StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){

													StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}else{
												Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
												while (platformVersionBucketIt.hasNext()) {
													Bucket platformVersionBucket = platformVersionBucketIt.next();
													
													StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
															
														}
													}
													
												}
											}
											
										}
									}
									
								}else{
									Iterator<Bucket> platformTypeBucketIt = platformTypeTerms.getBuckets().iterator();
									while (platformTypeBucketIt.hasNext()) {
										Bucket platformTypeBucket = platformTypeBucketIt.next();
										
										StringTerms platformNameTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
										if(null == platformNameTerms){
											StringTerms platformVersionTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformVersionAgg");//版本
											if(null == platformVersionTerms){
												StringTerms userTypeTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}else{
												Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
												while (platformVersionBucketIt.hasNext()) {
													Bucket platformVersionBucket = platformVersionBucketIt.next();
													
													StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
														
														}
													}
												
												}
											}
											
										}else{
											Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
											while (platformNameBucketIt.hasNext()) {
												Bucket platformNameBucket = platformNameBucketIt.next();
												
												StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
												if(null == platformVersionTerms){
													StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
															
														}
													}
												
												}else{
													Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
													while (platformVersionBucketIt.hasNext()) {
														Bucket platformVersionBucket = platformVersionBucketIt.next();
														
														StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
														if(null == userTypeTerms){
															StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
															
														}else{
															Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
															while (userTypeBucketIt.hasNext()) {
																Bucket userTypeBucket = userTypeBucketIt.next();
																
																StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
																if(null == channelTerms){
																	StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}else{
																	Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																	while (channelBucketIt.hasNext()) {
																		Bucket channelBucket = channelBucketIt.next();
																		
																		StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																		Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																		long deviceidNum = 0;
																		while (deviceidBucketIt.hasNext()) {
																			Bucket deviceidBucket = deviceidBucketIt.next();
																			deviceidNum+= deviceidBucket.getDocCount();				
																		}
																		
																		DasAppOdsVO entity = new DasAppOdsVO();
																		entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																		entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																		entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																		entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																		entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																		entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																		entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																		entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																		entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																		entity.setChannel(String.valueOf(channelBucket.getKey()));
																		entity.setDeviceidNum(deviceidNum);
																		entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																		list.add(entity);
																	}
																}
															
															}
														}
														
													}
												}
												
											}
										}
										
									}
								}
								
							}
						}
						
					}
				}
			}
		}
		
		page.setTotal(list.size());
		if(null != list && list.size() >0){
			ListPageUtil<DasAppOdsVO> pager = new ListPageUtil<DasAppOdsVO>(list, pageSize);
			List<DasAppOdsVO> pageList = pager.getPagedList(pageNo);
			page.setRows(pageList);
		}else{
			page.setRows(list);
		}
		
		long end = System.currentTimeMillis();
		logger.info("ES查询APP事件统计报表消耗时间:"+(end-start)/1000+"秒");
		
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
		srb.setSearchType(SearchType.DEFAULT);

		srb.setExplain(true);
		srb.addSort("operationTime", SortOrder.DESC);		
		srb.setFrom(0).setSize(300000);
		
		BoolQueryBuilder  query = QueryBuilders.boolQuery();	
		if(StringUtils.isNotBlank(model.getBusinessPlatform())){	
			query.must(QueryBuilders.matchPhraseQuery("businessPlatform", model.getBusinessPlatform()));//matchPhraseQuery精确查询
		}
		
		if(StringUtils.isNotBlank(model.getStartTime()) || StringUtils.isNotBlank(model.getEndTime())){	
			if(StringUtils.isNotBlank(model.getStartTime()) && StringUtils.isBlank(model.getEndTime())){
			   query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()));
			}else if(StringUtils.isBlank(model.getStartTime())&& StringUtils.isNotBlank(model.getEndTime())){
				query.filter(QueryBuilders.rangeQuery("operationTime").lte(model.getEndTime()));
			}else{
				query.filter(QueryBuilders.rangeQuery("operationTime").gte(model.getStartTime()).lt(model.getEndTime()));	
			}			
		}
		
        TermsAggregationBuilder termsBuilder0 = AggregationBuilders.terms("eventCategoryAgg").field("eventCategory").size(1000000).missing(" "); //事件类别
        if(StringUtils.isNotBlank(model.getEventCategory())){
        	query.must(QueryBuilders.matchPhraseQuery("eventCategory",model.getEventCategory().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventCategory().toLowerCase()+"*").field("eventCategory"));
        }
		
		TermsAggregationBuilder termsBuilder1 = AggregationBuilders.terms("eventActionAgg").field("eventAction").size(1000000).missing(" "); //事件操作
		if(StringUtils.isNotBlank(model.getEventAction())){
        	query.must(QueryBuilders.matchPhraseQuery("eventAction",model.getEventAction().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventAction().toLowerCase()+"*").field("eventAction"));
        }
		
		TermsAggregationBuilder termsBuilder2 = AggregationBuilders.terms("eventLabelAgg").field("eventLabel").size(1000000).missing(" "); //事件标签
		if(StringUtils.isNotBlank(model.getEventLabel())){
        	query.must(QueryBuilders.matchPhraseQuery("eventLabel",model.getEventLabel().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventLabel().toLowerCase()+"*").field("eventLabel"));
        }
		
		TermsAggregationBuilder termsBuilder3 = AggregationBuilders.terms("eventValueAgg").field("eventValue").size(1000000).missing(" "); //事件参数
		if(StringUtils.isNotBlank(model.getEventValue())){
        	query.must(QueryBuilders.matchPhraseQuery("eventValue",model.getEventValue().toLowerCase()));
        	//query.must(QueryBuilders.queryStringQuery("*"+model.getEventValue().toLowerCase()+"*").field("eventValue"));
        }
		
		termsBuilder0.subAggregation(termsBuilder1);
	    termsBuilder1.subAggregation(termsBuilder2);
		termsBuilder2.subAggregation(termsBuilder3);
		
		TermsAggregationBuilder termsBuilder4 = null;
		if(StringUtils.isNotBlank(model.getDeviceType())){
			if(DeviceTypeEnum.ANDROID.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.ANDROID.getValue());
			}
			if(DeviceTypeEnum.IOS.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.IOS.getValue());
			}
			if(DeviceTypeEnum.PCUI.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.PCUI.getValue());
			}
			if(DeviceTypeEnum.WEBUI.getLabelKey().equals(model.getDeviceType())){
				model.setDeviceType(DeviceTypeEnum.WEBUI.getValue());
			}
			query.must(QueryBuilders.matchPhraseQuery("deviceType",model.getDeviceType()));
			
		}
		if(model.isDeviceTypeChecked()){
			termsBuilder4 = AggregationBuilders.terms("deviceTypeAgg").field("deviceType").size(1000000); //设备平台
			termsBuilder3.subAggregation(termsBuilder4);
		}
		
		TermsAggregationBuilder termsBuilder5 = null;
		if(StringUtils.isNotBlank(model.getPlatformType())){
			query.must(QueryBuilders.matchPhraseQuery("platformType",model.getPlatformType()));
			
		}
		if(model.isPlatformTypeChecked()){
			termsBuilder5 = AggregationBuilders.terms("platformTypeAgg").field("platformType").size(1000000); //交易平台
			if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder5);
			}else{
				termsBuilder3.subAggregation(termsBuilder5);
			}
		}
		
		TermsAggregationBuilder termsBuilder6 = null;
		if(StringUtils.isNotBlank(model.getPlatformName())){
			query.must(QueryBuilders.matchPhraseQuery("platformName",model.getPlatformName().toLowerCase()));
			//query.must(QueryBuilders.queryStringQuery("*"+model.getPlatformName().toLowerCase()+"*").field("platformName"));
		}
		if(model.isPlatformNameChecked()){
			termsBuilder6 = AggregationBuilders.terms("platformNameAgg").field("platformName.keyword").size(1000000); //马甲包
			if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder6);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder6);
			}else{
				termsBuilder3.subAggregation(termsBuilder6);
			}
		}
		
		TermsAggregationBuilder termsBuilder7 = null;
		if(StringUtils.isNotBlank(model.getPlatformVersion())){
			//query.must(QueryBuilders.matchPhraseQuery("platformVersion",model.getPlatformVersion().toLowerCase()));
		    query.must(QueryBuilders.queryStringQuery("*"+model.getPlatformVersion().toLowerCase()+"*").field("platformVersion"));
		}
		if(model.isPlatformVersionChecked()){
			termsBuilder7 = AggregationBuilders.terms("platformVersionAgg").field("platformVersion").size(1000000); //版本
			if(termsBuilder6 != null){
				termsBuilder6.subAggregation(termsBuilder7);
			}else if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder7);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder7);
			}else{
				termsBuilder3.subAggregation(termsBuilder7);
			}
		}
		
		TermsAggregationBuilder termsBuilder8 = null;
		if(StringUtils.isNotBlank(model.getUserType())){
			query.must(QueryBuilders.matchPhraseQuery("userType",model.getUserType()));
		}
		if(model.isUserTypeChecked()){
			termsBuilder8 = AggregationBuilders.terms("userTypeAgg").field("userType").size(1000000); //客户类型
			if(termsBuilder7 != null){
				termsBuilder7.subAggregation(termsBuilder8);
			}else if(termsBuilder6 != null){
				termsBuilder6.subAggregation(termsBuilder8);
			}else if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder8);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder8);
			}else{
				termsBuilder3.subAggregation(termsBuilder8);
			}
		}
		
		TermsAggregationBuilder termsBuilder9 = null;
        if(StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())){
        	query.must(QueryBuilders.matchPhraseQuery("channel",model.getChannel().toLowerCase()));
		}
        if(model.isChannelChecked()){
        	termsBuilder9 = AggregationBuilders.terms("channelAgg").field("channel").size(1000000); //渠道
        	if(termsBuilder8 != null){
				termsBuilder8.subAggregation(termsBuilder9);
			}else if(termsBuilder7 != null){
				termsBuilder7.subAggregation(termsBuilder9);
			}else if(termsBuilder6 != null){
				termsBuilder6.subAggregation(termsBuilder9);
			}else if(termsBuilder5 != null){
				termsBuilder5.subAggregation(termsBuilder9);
			}else if(termsBuilder4 != null){
				termsBuilder4.subAggregation(termsBuilder9);
			}else{
				termsBuilder3.subAggregation(termsBuilder9);
			}
        }

		srb.setQuery(query);
		
		TermsAggregationBuilder termsBuilder10 = AggregationBuilders.terms("deviceidAgg").field("deviceid").size(1000000); //设备唯一标识
		if(termsBuilder9 != null){
			termsBuilder9.subAggregation(termsBuilder10);
		}else if(termsBuilder8 != null){
			termsBuilder8.subAggregation(termsBuilder10);
		}else if(termsBuilder7 != null){
			termsBuilder7.subAggregation(termsBuilder10);
		}else if(termsBuilder6 != null){
			termsBuilder6.subAggregation(termsBuilder10);
		}else if(termsBuilder5 != null){
			termsBuilder5.subAggregation(termsBuilder10);
		}else if(termsBuilder4 != null){
			termsBuilder4.subAggregation(termsBuilder10);
		}else{
			termsBuilder3.subAggregation(termsBuilder10);
		}
		
		srb.addAggregation(termsBuilder0);
		
		long start = System.currentTimeMillis();
		
		SearchResponse sr = srb.execute().actionGet();
		
		List<DasAppOdsVO> list = new ArrayList<DasAppOdsVO>();
		
		Map<String, Aggregation> aggMap = sr.getAggregations().asMap();
		StringTerms eventCategoryTerms = (StringTerms) aggMap.get("eventCategoryAgg");
		Iterator<Bucket> eventCategoryBucketIt = eventCategoryTerms.getBuckets().iterator();
		while (eventCategoryBucketIt.hasNext()) {
			Bucket eventCategoryBucket = eventCategoryBucketIt.next();
			
			StringTerms eventActionTerms = (StringTerms) eventCategoryBucket.getAggregations().asMap().get("eventActionAgg");//事件操作
			Iterator<Bucket> eventActionBucketIt = eventActionTerms.getBuckets().iterator();
			while (eventActionBucketIt.hasNext()) {
				Bucket eventActionBucket = eventActionBucketIt.next();
				
				StringTerms eventLabelTerms = (StringTerms) eventActionBucket.getAggregations().asMap().get("eventLabelAgg");//事件标签
				Iterator<Bucket> eventLabelBucketIt = eventLabelTerms.getBuckets().iterator();
				while (eventLabelBucketIt.hasNext()) {
					Bucket eventLabelBucket = eventLabelBucketIt.next();
					
					StringTerms eventValueTerms = (StringTerms) eventLabelBucket.getAggregations().asMap().get("eventValueAgg");//事件参数
					Iterator<Bucket> eventValueBucketIt = eventValueTerms.getBuckets().iterator();
					while (eventValueBucketIt.hasNext()) {
						Bucket eventValueBucket = eventValueBucketIt.next();
						
						StringTerms deviceTypeTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("deviceTypeAgg");//设备平台
						if(null == deviceTypeTerms){
							StringTerms platformTypeTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("platformTypeAgg");//交易平台
							if(null == platformTypeTerms){
								StringTerms platformNameTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
								if(null == platformNameTerms){
									StringTerms platformVersionTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("platformVersionAgg");//版本
									if(null == platformVersionTerms){
										StringTerms userTypeTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
										if(null == userTypeTerms){
											StringTerms channelTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("channelAgg");//渠道
											if(null == channelTerms){
												StringTerms deviceidTerms = (StringTerms) eventValueBucket.getAggregations().asMap().get("deviceidAgg");
												Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
												long deviceidNum = 0;
												while (deviceidBucketIt.hasNext()) {
													Bucket deviceidBucket = deviceidBucketIt.next();
													deviceidNum+= deviceidBucket.getDocCount();				
												}
												
												DasAppOdsVO entity = new DasAppOdsVO();
												entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
												entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
												entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
												entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
												entity.setDeviceidNum(deviceidNum);
												entity.setDeviceidCount(deviceidTerms.getBuckets().size());
												list.add(entity);
											}else{
												Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
												while (channelBucketIt.hasNext()) {
													Bucket channelBucket = channelBucketIt.next();
													
													StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setChannel(String.valueOf(channelBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}
											}
											
										}else{
											Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
											while (userTypeBucketIt.hasNext()) {
												Bucket userTypeBucket = userTypeBucketIt.next();
												
												StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setUserType(String.valueOf(userTypeBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}
										}
										
									}else{
										Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
										while (platformVersionBucketIt.hasNext()) {
											Bucket platformVersionBucket = platformVersionBucketIt.next();
											
											StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
										
										}
									}
									
								}else{
									Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
									while (platformNameBucketIt.hasNext()) {
										Bucket platformNameBucket = platformNameBucketIt.next();
										
										StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
										if(null == platformVersionTerms){
											StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
											
										}else{
											Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
											while (platformVersionBucketIt.hasNext()) {
												Bucket platformVersionBucket = platformVersionBucketIt.next();
												
												StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}
										}
										
									}
								}
								
							}else{
								Iterator<Bucket> platformTypeBucketIt = platformTypeTerms.getBuckets().iterator();
								while (platformTypeBucketIt.hasNext()) {
									Bucket platformTypeBucket = platformTypeBucketIt.next();
									
									StringTerms platformNameTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
									if(null == platformNameTerms){
										StringTerms platformVersionTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformVersionAgg");//版本
										if(null == platformVersionTerms){
											StringTerms userTypeTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
											
										}else{
											Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
											while (platformVersionBucketIt.hasNext()) {
												Bucket platformVersionBucket = platformVersionBucketIt.next();
												
												StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){

													StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}
										}
										
									}else{
										Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
										while (platformNameBucketIt.hasNext()) {
											Bucket platformNameBucket = platformNameBucketIt.next();
											
											StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
											if(null == platformVersionTerms){
												StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
													
													}
												}
												
											}else{
												Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
												while (platformVersionBucketIt.hasNext()) {
													Bucket platformVersionBucket = platformVersionBucketIt.next();
													
													StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
														
														}
													}
												
												}
											}
											
										}
									}
									
								}
							}
							
						}else{
							Iterator<Bucket> deviceTypeBucketIt = deviceTypeTerms.getBuckets().iterator();
							while (deviceTypeBucketIt.hasNext()) {
								Bucket deviceTypeBucket = deviceTypeBucketIt.next();
								
								StringTerms platformTypeTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("platformTypeAgg");//交易平台
								if(null == platformTypeTerms){
									StringTerms platformNameTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
									if(null ==platformNameTerms){
										StringTerms platformVersionTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("platformVersionAgg");//版本
										if(null == platformVersionTerms){
											StringTerms userTypeTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
											if(null == userTypeTerms){
												StringTerms channelTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
												if(null == channelTerms){
													StringTerms deviceidTerms = (StringTerms) deviceTypeBucket.getAggregations().asMap().get("deviceidAgg");
													Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
													long deviceidNum = 0;
													while (deviceidBucketIt.hasNext()) {
														Bucket deviceidBucket = deviceidBucketIt.next();
														deviceidNum+= deviceidBucket.getDocCount();				
													}
													
													DasAppOdsVO entity = new DasAppOdsVO();
													entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
													entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
													entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
													entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
													entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
													entity.setDeviceidNum(deviceidNum);
													entity.setDeviceidCount(deviceidTerms.getBuckets().size());
													list.add(entity);
												}else{
													Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
													while (channelBucketIt.hasNext()) {
														Bucket channelBucket = channelBucketIt.next();
														
														StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setChannel(String.valueOf(channelBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}
												}
												
											}else{
												Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
												while (userTypeBucketIt.hasNext()) {
													Bucket userTypeBucket = userTypeBucketIt.next();
													
													StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setUserType(String.valueOf(userTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}
											}
											
										}else{
											Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
											while (platformVersionBucketIt.hasNext()) {
												Bucket platformVersionBucket = platformVersionBucketIt.next();
												
												StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}
										}
										
									}else{
										Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
										while (platformNameBucketIt.hasNext()) {
											Bucket platformNameBucket = platformNameBucketIt.next();
											
											StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
											if(null == platformVersionTerms){
												StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){

													StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}else{
												Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
												while (platformVersionBucketIt.hasNext()) {
													Bucket platformVersionBucket = platformVersionBucketIt.next();
													
													StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
															
														}
													}
													
												}
											}
											
										}
									}
									
								}else{
									Iterator<Bucket> platformTypeBucketIt = platformTypeTerms.getBuckets().iterator();
									while (platformTypeBucketIt.hasNext()) {
										Bucket platformTypeBucket = platformTypeBucketIt.next();
										
										StringTerms platformNameTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformNameAgg");//马甲包
										if(null == platformNameTerms){
											StringTerms platformVersionTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("platformVersionAgg");//版本
											if(null == platformVersionTerms){
												StringTerms userTypeTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
												if(null == userTypeTerms){
													StringTerms channelTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
													if(null == channelTerms){
														StringTerms deviceidTerms = (StringTerms) platformTypeBucket.getAggregations().asMap().get("deviceidAgg");
														Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
														long deviceidNum = 0;
														while (deviceidBucketIt.hasNext()) {
															Bucket deviceidBucket = deviceidBucketIt.next();
															deviceidNum+= deviceidBucket.getDocCount();				
														}
														
														DasAppOdsVO entity = new DasAppOdsVO();
														entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
														entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
														entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
														entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
														entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
														entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
														entity.setDeviceidNum(deviceidNum);
														entity.setDeviceidCount(deviceidTerms.getBuckets().size());
														list.add(entity);
													}else{
														Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
														while (channelBucketIt.hasNext()) {
															Bucket channelBucket = channelBucketIt.next();
															
															StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setChannel(String.valueOf(channelBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}
													}
													
												}else{
													Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
													while (userTypeBucketIt.hasNext()) {
														Bucket userTypeBucket = userTypeBucketIt.next();
														
														StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setUserType(String.valueOf(userTypeBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}
												}
												
											}else{
												Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
												while (platformVersionBucketIt.hasNext()) {
													Bucket platformVersionBucket = platformVersionBucketIt.next();
													
													StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
														
														}
													}
												
												}
											}
											
										}else{
											Iterator<Bucket> platformNameBucketIt = platformNameTerms.getBuckets().iterator();
											while (platformNameBucketIt.hasNext()) {
												Bucket platformNameBucket = platformNameBucketIt.next();
												
												StringTerms platformVersionTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("platformVersionAgg");//版本
												if(null == platformVersionTerms){
													StringTerms userTypeTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
													if(null == userTypeTerms){
														StringTerms channelTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("channelAgg");//渠道
														if(null == channelTerms){
															StringTerms deviceidTerms = (StringTerms) platformNameBucket.getAggregations().asMap().get("deviceidAgg");
															Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
															long deviceidNum = 0;
															while (deviceidBucketIt.hasNext()) {
																Bucket deviceidBucket = deviceidBucketIt.next();
																deviceidNum+= deviceidBucket.getDocCount();				
															}
															
															DasAppOdsVO entity = new DasAppOdsVO();
															entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
															entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
															entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
															entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
															entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
															entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
															entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
															entity.setDeviceidNum(deviceidNum);
															entity.setDeviceidCount(deviceidTerms.getBuckets().size());
															list.add(entity);
														}else{
															Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
															while (channelBucketIt.hasNext()) {
																Bucket channelBucket = channelBucketIt.next();
																
																StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setChannel(String.valueOf(channelBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}
														}
														
													}else{
														Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
														while (userTypeBucketIt.hasNext()) {
															Bucket userTypeBucket = userTypeBucketIt.next();
															
															StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
															
														}
													}
												
												}else{
													Iterator<Bucket> platformVersionBucketIt = platformVersionTerms.getBuckets().iterator();
													while (platformVersionBucketIt.hasNext()) {
														Bucket platformVersionBucket = platformVersionBucketIt.next();
														
														StringTerms userTypeTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("userTypeAgg");//客户类型
														if(null == userTypeTerms){
															StringTerms channelTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("channelAgg");//渠道
															if(null == channelTerms){
																StringTerms deviceidTerms = (StringTerms) platformVersionBucket.getAggregations().asMap().get("deviceidAgg");
																Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																long deviceidNum = 0;
																while (deviceidBucketIt.hasNext()) {
																	Bucket deviceidBucket = deviceidBucketIt.next();
																	deviceidNum+= deviceidBucket.getDocCount();				
																}
																
																DasAppOdsVO entity = new DasAppOdsVO();
																entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																entity.setDeviceidNum(deviceidNum);
																entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																list.add(entity);
															}else{
																Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																while (channelBucketIt.hasNext()) {
																	Bucket channelBucket = channelBucketIt.next();
																	
																	StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setChannel(String.valueOf(channelBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}
															}
															
														}else{
															Iterator<Bucket> userTypeBucketIt = userTypeTerms.getBuckets().iterator();
															while (userTypeBucketIt.hasNext()) {
																Bucket userTypeBucket = userTypeBucketIt.next();
																
																StringTerms channelTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("channelAgg");//渠道
																if(null == channelTerms){
																	StringTerms deviceidTerms = (StringTerms) userTypeBucket.getAggregations().asMap().get("deviceidAgg");
																	Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																	long deviceidNum = 0;
																	while (deviceidBucketIt.hasNext()) {
																		Bucket deviceidBucket = deviceidBucketIt.next();
																		deviceidNum+= deviceidBucket.getDocCount();				
																	}
																	
																	DasAppOdsVO entity = new DasAppOdsVO();
																	entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																	entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																	entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																	entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																	entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																	entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																	entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																	entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																	entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																	entity.setDeviceidNum(deviceidNum);
																	entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																	list.add(entity);
																}else{
																	Iterator<Bucket> channelBucketIt = channelTerms.getBuckets().iterator();
																	while (channelBucketIt.hasNext()) {
																		Bucket channelBucket = channelBucketIt.next();
																		
																		StringTerms deviceidTerms = (StringTerms) channelBucket.getAggregations().asMap().get("deviceidAgg");
																		Iterator<Bucket> deviceidBucketIt = deviceidTerms.getBuckets().iterator();
																		long deviceidNum = 0;
																		while (deviceidBucketIt.hasNext()) {
																			Bucket deviceidBucket = deviceidBucketIt.next();
																			deviceidNum+= deviceidBucket.getDocCount();				
																		}
																		
																		DasAppOdsVO entity = new DasAppOdsVO();
																		entity.setEventCategory(String.valueOf(eventCategoryBucket.getKey()));
																		entity.setEventAction(String.valueOf(eventActionBucket.getKey()));
																		entity.setEventLabel(String.valueOf(eventLabelBucket.getKey()));
																		entity.setEventValue(String.valueOf(eventValueBucket.getKey()));
																		entity.setDeviceType(String.valueOf(deviceTypeBucket.getKey()));
																		entity.setPlatformType(String.valueOf(platformTypeBucket.getKey()));
																		entity.setPlatformName(String.valueOf(platformNameBucket.getKey()));
																		entity.setPlatformVersion(String.valueOf(platformVersionBucket.getKey()));
																		entity.setUserType(String.valueOf(userTypeBucket.getKey()));
																		entity.setChannel(String.valueOf(channelBucket.getKey()));
																		entity.setDeviceidNum(deviceidNum);
																		entity.setDeviceidCount(deviceidTerms.getBuckets().size());
																		list.add(entity);
																	}
																}
															
															}
														}
														
													}
												}
												
											}
										}
										
									}
								}
								
							}
						}
						
					}
				}
			}
		}
		
		long end = System.currentTimeMillis();
		logger.info("ES查询APP事件统计报表消耗时间:"+(end-start)/1000+"秒");	
		
		return list;
	}
	
}
