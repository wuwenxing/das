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
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.website.bean.DasUserInfo;
import com.gw.das.dao.website.bean.DasUserInfoSearchBean;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.website.DasUserInfoService;

@Service
public class DasUserInfoServiceImpl extends BaseService implements DasUserInfoService {
	
	/**
	 * 用户列表查询
	 */
	public PageGrid<DasUserInfoSearchBean> findPageList(PageGrid<DasUserInfoSearchBean> pageGrid) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		//设置查询条件
		DasUserInfoSearchBean detail = pageGrid.getSearchModel();
		if(detail != null){
			if(StringUtils.isNotBlank(detail.getBehaviorType())){
				// 属性[模拟\真实\入金]
				if(BehaviorTypeEnum.demo.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "3");
				}else if(BehaviorTypeEnum.real.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "4");
				}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "5");
				}
			}
			// 来源
			parameters.put("utmcsr", detail.getUtmcsr());
			// 媒介
			parameters.put("utmcmd", detail.getUtmcmd());
			// 帐号
			if(StringUtils.isNotBlank(detail.getBehaviorDetail())){
				parameters.put("behaviorDetail", detail.getBehaviorDetail());
			}
			// 时间条件
			if(StringUtils.isNotBlank(detail.getStartTime())){
				parameters.put("startTime", detail.getStartTime());
			}
			if(StringUtils.isNotBlank(detail.getEndTime())){
				parameters.put("endTime", detail.getEndTime());
			}
			// 排重条件
			if("Y".equals(detail.getIsDemo())){
				parameters.put("isDemo", detail.getIsDemo());
			}
			if("Y".equals(detail.getIsReal())){
				parameters.put("isReal", detail.getIsReal());
			}
			if("Y".equals(detail.getIsDepesit())){
				parameters.put("isDepesit", detail.getIsDepesit());
			}
		}
		parameters.put("pageNumber", pageGrid.getPageNumber()+"");
		parameters.put("pageSize", pageGrid.getPageSize()+"");
		
		RpcResult rpcResult = RpcUtils.post(Constants.dasUserInfoListPage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		
		List<DasUserInfo> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasUserInfo>>(){});
		PageGrid<DasUserInfoSearchBean> page = new PageGrid<DasUserInfoSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
}
