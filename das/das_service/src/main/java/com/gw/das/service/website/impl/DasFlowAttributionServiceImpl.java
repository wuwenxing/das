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
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.website.bean.DasFlowAttribution;
import com.gw.das.dao.website.bean.DasFlowAttributionSearchBean;
import com.gw.das.service.website.DasFlowAttributionService;

/**
 * 来源媒介效果业务处理类
 */
@Service
public class DasFlowAttributionServiceImpl implements DasFlowAttributionService {

	/**
	 * 获取来源媒介效果列表
	 */
	public List<DasFlowAttribution> findList(DasFlowAttributionSearchBean attribution) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		if (attribution != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(attribution.getUtmcsr())) {
				parameters.put("utmcsr", attribution.getUtmcsr());
			}
			if (StringUtils.isNotBlank(attribution.getUtmcmd())) {
				parameters.put("utmcmd", attribution.getUtmcmd());
			}
			if (StringUtils.isNotBlank(attribution.getStartTime())) {
				parameters.put("startTime", attribution.getStartTime());
			}
			if (StringUtils.isNotBlank(attribution.getEndTime())) {
				parameters.put("endTime", attribution.getEndTime());
			}
			if (StringUtils.isNotBlank(attribution.getPlatformType())) {
				// 访问客户端
				if (ClientEnum.pc.getLabelKey().equals(attribution.getPlatformType())) {
					parameters.put("platformType", "0");
				} else if (ClientEnum.mobile.getLabelKey().equals(attribution.getPlatformType())) {
					parameters.put("platformType", "1");
				}
			}
		}

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowAttributionList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasFlowAttribution> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowAttribution>>() {});
		return rowsList;
	}
	
	/**
	 * 来源媒介效果-分页查询
	 */
	public PageGrid<DasFlowAttributionSearchBean> findPageList(PageGrid<DasFlowAttributionSearchBean> pageGrid) throws Exception {
		DasFlowAttributionSearchBean attribution = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		if (attribution != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(attribution.getUtmcsr())) {
				parameters.put("utmcsr", attribution.getUtmcsr());
			}
			if (StringUtils.isNotBlank(attribution.getUtmcmd())) {
				parameters.put("utmcmd", attribution.getUtmcmd());
			}
			if (StringUtils.isNotBlank(attribution.getStartTime())) {
				parameters.put("startTime", attribution.getStartTime());
			}
			if (StringUtils.isNotBlank(attribution.getEndTime())) {
				parameters.put("endTime", attribution.getEndTime());
			}
			if (StringUtils.isNotBlank(attribution.getPlatformType())) {
				// 访问客户端
				if (ClientEnum.pc.getLabelKey().equals(attribution.getPlatformType())) {
					parameters.put("platformType", "0");
				} else if (ClientEnum.mobile.getLabelKey().equals(attribution.getPlatformType())) {
					parameters.put("platformType", "1");
				}
			}
		}
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowAttributionListPage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult();

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasFlowAttribution> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowAttribution>>() {});
		PageGrid<DasFlowAttributionSearchBean> page = new PageGrid<DasFlowAttributionSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
}
