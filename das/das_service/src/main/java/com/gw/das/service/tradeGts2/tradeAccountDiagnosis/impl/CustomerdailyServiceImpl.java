package com.gw.das.service.tradeGts2.tradeAccountDiagnosis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.response.ApiPageResult;
import com.gw.das.common.token.TokenCache;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.dao.trade.bean.CustomerdailyVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeAccountDiagnosis.CustomerdailyService;

/**
 * 查询账户结算日报表记录接口
 * 
 * @author darren
 *
 */
@Service
public class CustomerdailyServiceImpl implements CustomerdailyService{
    
	/**
	 * 分页查询账户结算日报表记录-elasticsearch
	 */
	@Override
	public PageGrid<TradeSearchModel> findCustomerdailyPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
		RpcResult rpcResult = RpcUtils.post(Constants.findCustomerdailyPageList, BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		List<CustomerdailyVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<CustomerdailyVO>>(){});
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
		for (CustomerdailyVO customerdailyVO : rowsList) {		
			customerdailyVO.setBalancePreviousbalance(NumberUtil.NumberFormat(customerdailyVO.getBalancePreviousbalance(), businessplatform));
			customerdailyVO.setBalance(NumberUtil.NumberFormat(customerdailyVO.getBalance(), businessplatform));
			customerdailyVO.setFloatingprofit(NumberUtil.NumberFormat(customerdailyVO.getFloatingprofit(),businessplatform));
			customerdailyVO.setMargin(NumberUtil.NumberFormat(customerdailyVO.getMargin(),businessplatform));
			customerdailyVO.setFreemargin(NumberUtil.NumberFormat(customerdailyVO.getFreemargin(),businessplatform));
			customerdailyVO.setDepositWithdraw(NumberUtil.NumberFormat(customerdailyVO.getDepositWithdraw(),businessplatform));
		}
		
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 	
		return page;	
	}
	
	/**
	 * 不分页查询账户结算日报表记录-elasticsearch
	 */
	@Override
	public List<CustomerdailyVO> findCustomerdailyList(TradeSearchModel searchBean)
			throws Exception {		
		RpcResult rpcResult = RpcUtils.post(Constants.findCustomerdailyList, BeanToMapUtil.toMap(searchBean), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		List<CustomerdailyVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<CustomerdailyVO>>(){});
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
		for (CustomerdailyVO customerdailyVO : rowsList) {		
			customerdailyVO.setBalancePreviousbalance(NumberUtil.NumberFormat(customerdailyVO.getBalancePreviousbalance(), businessplatform));
			customerdailyVO.setBalance(NumberUtil.NumberFormat(customerdailyVO.getBalance(), businessplatform));
			customerdailyVO.setFloatingprofit(NumberUtil.NumberFormat(customerdailyVO.getFloatingprofit(),businessplatform));
			customerdailyVO.setMargin(NumberUtil.NumberFormat(customerdailyVO.getMargin(),businessplatform));
			customerdailyVO.setFreemargin(NumberUtil.NumberFormat(customerdailyVO.getFreemargin(),businessplatform));
		}
		
		return 	rowsList;
	}
	
	/**
	 * 分页查询账户结算日报表记录-宽表查询
	 */
	@Override
	public PageGrid<TradeSearchModel> findCustomerdailyWithPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());	
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(UserContext.get().getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(UserContext.get().getCompanyId()+""));
		paramMap.put("accountNo", detail.getAccountno());
		//paramMap.put("platform", null);
		paramMap.put("startDate", detail.getStartTime());
		paramMap.put("endDate", detail.getEndTime());
		paramMap.put("sort", detail.getSort());
		paramMap.put("order", detail.getOrder());
		paramMap.put("pageNumber", String.valueOf(pageGrid.getPageNumber()));
		paramMap.put("pageSize", String.valueOf(pageGrid.getPageSize()));
				
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.customerdailyPageList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		int total = apiResult.getTotal();
		List<CustomerdailyVO> rowsList = new ArrayList<CustomerdailyVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
					
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<CustomerdailyVO>>(){});
		}		
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
		for (CustomerdailyVO customerdailyVO : rowsList) {		
			customerdailyVO.setBalancePreviousbalance(NumberUtil.NumberFormat(customerdailyVO.getBalancePreviousbalance(), businessplatform));
			customerdailyVO.setBalance(NumberUtil.NumberFormat(customerdailyVO.getBalance(), businessplatform));
			customerdailyVO.setFloatingprofit(NumberUtil.NumberFormat(customerdailyVO.getFloatingprofit(),businessplatform));
			customerdailyVO.setMargin(NumberUtil.NumberFormat(customerdailyVO.getMargin(),businessplatform));
			customerdailyVO.setFreemargin(NumberUtil.NumberFormat(customerdailyVO.getFreemargin(),businessplatform));
			customerdailyVO.setDeposit(NumberUtil.NumberFormat(customerdailyVO.getDeposit(),businessplatform));
			customerdailyVO.setWithdraw(NumberUtil.NumberFormat(customerdailyVO.getWithdraw(),businessplatform));
		}
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 	
		return page;
	}
	
	/**
	 * 不分页查询账户结算日报表记录-宽表查询
	 */
	@Override
	public List<CustomerdailyVO> findCustomerdailyWithList(TradeSearchModel searchBean)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(UserContext.get().getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(UserContext.get().getCompanyId()+""));
		paramMap.put("accountNo", searchBean.getAccountno());
		//paramMap.put("platform", null);
		paramMap.put("startDate", searchBean.getStartTime());
		paramMap.put("endDate", searchBean.getEndTime());
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.customerdailyList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		List<CustomerdailyVO> rowsList = new ArrayList<CustomerdailyVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
			
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<CustomerdailyVO>>(){});
		}
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
		for (CustomerdailyVO customerdailyVO : rowsList) {		
			customerdailyVO.setBalancePreviousbalance(NumberUtil.NumberFormat(customerdailyVO.getBalancePreviousbalance(), businessplatform));
			customerdailyVO.setBalance(NumberUtil.NumberFormat(customerdailyVO.getBalance(), businessplatform));
			customerdailyVO.setFloatingprofit(NumberUtil.NumberFormat(customerdailyVO.getFloatingprofit(),businessplatform));
			customerdailyVO.setMargin(NumberUtil.NumberFormat(customerdailyVO.getMargin(),businessplatform));
			customerdailyVO.setFreemargin(NumberUtil.NumberFormat(customerdailyVO.getFreemargin(),businessplatform));
			customerdailyVO.setDeposit(NumberUtil.NumberFormat(customerdailyVO.getDeposit(),businessplatform));
			customerdailyVO.setWithdraw(NumberUtil.NumberFormat(customerdailyVO.getWithdraw(),businessplatform));
		}
		return rowsList;
	}	


}
