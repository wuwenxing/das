package com.gw.das.service.tradeGts2.tradeAccountDiagnosis.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.enums.ReasonEnum;
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
import com.gw.das.dao.trade.bean.AccountbalanceVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeAccountDiagnosis.AccountbalanceService;

/**
 * 查询所有账户余额变动记录接口
 * 
 * @author darren
 *
 */
@Service
public class AccountbalanceServiceImpl implements AccountbalanceService{

	private static final Logger logger = LoggerFactory.getLogger(AccountbalanceServiceImpl.class);
	
	/**
	 * 账户余额变动记录-elasticsearch
	 */
	@Override
	public PageGrid<TradeSearchModel> findAccountbalancePageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setSort(pageGrid.getSort());
		detail.setOrder(pageGrid.getOrder());
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
		RpcResult rpcResult = RpcUtils.post(Constants.findAccountbalancePageList, BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		List<AccountbalanceVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<AccountbalanceVO>>(){});
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		for (AccountbalanceVO accountbalanceVO : rowsList) {
			accountbalanceVO.setReason(ReasonEnum.getReason(accountbalanceVO.getReason()));
			accountbalanceVO.setAmount(NumberUtil.NumberFormat(accountbalanceVO.getAmount(),businessplatform));
		}
		
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 
		return page;
	}
	
	/**
	 * 不分页查询账户余额变动记录-elasticsearch
	 */
	@Override
	public List<AccountbalanceVO> findAccountbalanceList(TradeSearchModel searchBean)
			throws Exception {	
		RpcResult rpcResult = RpcUtils.post(Constants.findAccountbalanceList, BeanToMapUtil.toMap(searchBean), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		List<AccountbalanceVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<AccountbalanceVO>>(){});
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		for (AccountbalanceVO accountbalanceVO : rowsList) {
			accountbalanceVO.setReason(ReasonEnum.getReason(accountbalanceVO.getReason()));
			accountbalanceVO.setAmount(NumberUtil.NumberFormat(accountbalanceVO.getAmount(),businessplatform));
		}				
		return rowsList;
	}
	
	/**
	 * 分页查询所有账户余额变动记录-宽表查询
	 */
	@Override
	public PageGrid<TradeSearchModel> findAccountbalanceWithPageList(PageGrid<TradeSearchModel> pageGrid)
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
		String companyId = CompanyEnum.getAPICompanyId(UserContext.get().getCompanyId()+"");
		if(CompanyEnum.hx.getLabelKey().equals(String.valueOf(companyId)) || CompanyEnum.pm.getLabelKey().equals(String.valueOf(companyId))){
			paramMap.put("sort", "closetime_gmt8");
		}else{
			paramMap.put("sort", "closetime");
		}
		
		paramMap.put("order", detail.getOrder());
		paramMap.put("pageNumber", String.valueOf(pageGrid.getPageNumber()));
		paramMap.put("pageSize", String.valueOf(pageGrid.getPageSize()));
				
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.accountbalancePageList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		int total = apiResult.getTotal();
		List<AccountbalanceVO> rowsList = new ArrayList<AccountbalanceVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
					
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<AccountbalanceVO>>(){});
		}		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());	
		for (AccountbalanceVO accountbalanceVO : rowsList) {
			accountbalanceVO.setAmount(NumberUtil.NumberFormat(accountbalanceVO.getAmount(),businessplatform));
		}
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 	
		return page;
	}
	
	/**
	 * 不分页查询所有账户余额变动记录-宽表查询
	 */
	@Override
	public List<AccountbalanceVO> findAccountbalanceWithList(TradeSearchModel searchBean)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(UserContext.get().getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(UserContext.get().getCompanyId()+""));
		paramMap.put("accountNo", searchBean.getAccountno());
		//paramMap.put("platform", null);
		paramMap.put("startDate", searchBean.getStartTime());
		paramMap.put("endDate", searchBean.getEndTime());
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.accountbalanceList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		List<AccountbalanceVO> rowsList = new ArrayList<AccountbalanceVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
			
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<AccountbalanceVO>>(){});
		}
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());	
		for (AccountbalanceVO accountbalanceVO : rowsList) {
			accountbalanceVO.setAmount(NumberUtil.NumberFormat(accountbalanceVO.getAmount(),businessplatform));
		}
		return rowsList;
	}	

}
