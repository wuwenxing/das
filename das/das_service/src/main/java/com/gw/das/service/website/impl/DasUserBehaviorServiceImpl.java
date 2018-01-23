package com.gw.das.service.website.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.BehaviorTypeEnum;
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.website.bean.DasFlowDetail;
import com.gw.das.dao.website.bean.DasFlowDetailSearchBean;
import com.gw.das.dao.website.bean.DasFlowDetailUrl;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.website.DasUserBehaviorService;

@Service
public class DasUserBehaviorServiceImpl extends BaseService implements DasUserBehaviorService {
	
	/**
	 * 用户行为列表分页查询
	 */
	public PageGrid<DasFlowDetailSearchBean> findPageList(PageGrid<DasFlowDetailSearchBean> pageGrid) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		// 设置查询条件
		DasFlowDetailSearchBean detail = pageGrid.getSearchModel();
		if(detail != null){
			if(StringUtils.isNotBlank(detail.getBehaviorType())){
				// 属性[访问\咨询\模拟\真实\入金]
				if(BehaviorTypeEnum.visit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "1");
				}else if(BehaviorTypeEnum.live.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "2");
				}else if(BehaviorTypeEnum.demo.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "3");
				}else if(BehaviorTypeEnum.real.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "4");
				}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "5");
				}
			}
			parameters.put("ip", detail.getIp());
			parameters.put("url", detail.getUrl());
			parameters.put("userId", detail.getUserId());
			parameters.put("utmcsr", detail.getUtmcsr());// 来源
			parameters.put("utmcmd", detail.getUtmcmd());// 媒介
			parameters.put("utmccn", detail.getUtmccn());// 广告系列
			parameters.put("utmcct", detail.getUtmcct());// 广告组
			parameters.put("utmctr", detail.getUtmctr());// 关键词
			parameters.put("startTime", detail.getStartTime()); // 行为开始时间
			parameters.put("endTime", detail.getEndTime()); // 行为结束时间
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				parameters.put("platformType", "0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				parameters.put("platformType", "1");
			}
		}
		if(StringUtils.isNotBlank(detail.getSort()) && StringUtils.isNotBlank(detail.getOrder())){
			parameters.put("sort", pageGrid.getSort());
			parameters.put("order", pageGrid.getOrder());
		}
		parameters.put("pageNumber", pageGrid.getPageNumber()+"");
		parameters.put("pageSize", pageGrid.getPageSize()+"");
		
		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowDetailListESPageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		
		List<DasFlowDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowDetail>>(){});
		PageGrid<DasFlowDetailSearchBean> page = new PageGrid<DasFlowDetailSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 用户行为列表不分页查询
	 */
	public List<DasFlowDetail> findList(DasFlowDetailSearchBean detail) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		// 设置查询条件
		if(detail != null){
			if(StringUtils.isNotBlank(detail.getBehaviorType())){
				// 属性[访问\咨询\模拟\真实\入金]
				if(BehaviorTypeEnum.visit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "1");
				}else if(BehaviorTypeEnum.live.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "2");
				}else if(BehaviorTypeEnum.demo.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "3");
				}else if(BehaviorTypeEnum.real.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "4");
				}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "5");
				}
			}
			parameters.put("ip", detail.getIp());
			parameters.put("url", detail.getUrl());
			parameters.put("userId", detail.getUserId());
			parameters.put("utmcsr", detail.getUtmcsr());// 来源
			parameters.put("utmcmd", detail.getUtmcmd());// 媒介
			parameters.put("utmccn", detail.getUtmccn());// 广告系列
			parameters.put("utmcct", detail.getUtmcct());// 广告组
			parameters.put("utmctr", detail.getUtmctr());// 关键词
			parameters.put("startTime", detail.getStartTime()); // 行为开始时间
			parameters.put("endTime", detail.getEndTime()); // 行为结束时间
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				parameters.put("platformType", "0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				parameters.put("platformType", "1");
			}
			if(StringUtils.isNotBlank(detail.getSort()) && StringUtils.isNotBlank(detail.getOrder())){
				parameters.put("sort", detail.getSort());
				parameters.put("order", detail.getOrder());
			}
		}
		
		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowDetailESList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		List<DasFlowDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowDetail>>(){});
		return rowsList;
	}
	

	/**
	 * 用户行为列表url分页查询
	 */
	public PageGrid<DasFlowDetailSearchBean> findDetailUrlPageList(PageGrid<DasFlowDetailSearchBean> pageGrid) throws Exception{
		return null;
	}
	
	/**
	 * 用户行为列表url不分页查询
	 */
	public List<DasFlowDetailUrl> findDetailUrlList(DasFlowDetailSearchBean detail) throws Exception{
		return null;
	}
	
	
	
}
