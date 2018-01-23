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
import com.gw.das.dao.website.bean.DasFlowDetailUrl;
import com.gw.das.dao.website.bean.DasFlowDetailUrlSearchBean;
import com.gw.das.service.website.DasFlowDetailUrlService;

/**
 * 来源媒介效果业务处理类
 */
@Service
public class DasFlowDetailUrlServiceImpl implements DasFlowDetailUrlService {

	/**
	 * 用户详细-页面浏览详细列表
	 */
	public List<DasFlowDetailUrl> findList(DasFlowDetailUrlSearchBean model) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		if (model != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(model.getUserId())) {
				parameters.put("userId", model.getUserId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailId())) {
				parameters.put("flowDetailId", model.getFlowDetailId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailUrl())) {
				parameters.put("flowDetailUrl", model.getFlowDetailUrl());
			}
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				parameters.put("utmcsr", model.getUtmcsr());
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				parameters.put("utmcmd", model.getUtmcmd());
			}
			if (StringUtils.isNotBlank(model.getStartTime())) {
				parameters.put("startTime", model.getStartTime());
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				parameters.put("endTime", model.getEndTime());
			}
		}

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowDetailUrlList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasFlowDetailUrl> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowDetailUrl>>() {});
		return rowsList;
	}

	/**
	 * 用户详细-页面浏览详细列表分页
	 */
	public PageGrid<DasFlowDetailUrlSearchBean> findPageList(PageGrid<DasFlowDetailUrlSearchBean> pageGrid) throws Exception {
		DasFlowDetailUrlSearchBean model = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		if (model != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(model.getUserId())) {
				parameters.put("userId", model.getUserId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailId())) {
				parameters.put("flowDetailId", model.getFlowDetailId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailUrl())) {
				parameters.put("flowDetailUrl", model.getFlowDetailUrl());
			}
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				parameters.put("utmcsr", model.getUtmcsr());
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				parameters.put("utmcmd", model.getUtmcmd());
			}
			if (StringUtils.isNotBlank(model.getStartTime())) {
				parameters.put("startTime", model.getStartTime());
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				parameters.put("endTime", model.getEndTime());
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				// 访问客户端
				if (ClientEnum.pc.getLabelKey().equals(model.getPlatformType())) {
					parameters.put("platformType", "0");
				} else if (ClientEnum.mobile.getLabelKey().equals(model.getPlatformType())) {
					parameters.put("platformType", "1");
				}
			}
			
			parameters.put("sortName", "visitTime");
			parameters.put("sortDirection", "desc");
		}
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");

		RpcResult rpcResult = RpcUtils.post(Constants.DasFlowDetailUrlESPageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();

		List<DasFlowDetailUrl> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasFlowDetailUrl>>() {});
		PageGrid<DasFlowDetailUrlSearchBean> page = new PageGrid<DasFlowDetailUrlSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
}
