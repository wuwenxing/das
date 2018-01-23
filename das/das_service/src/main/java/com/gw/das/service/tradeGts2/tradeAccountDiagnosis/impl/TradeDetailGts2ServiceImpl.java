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
import com.gw.das.common.enums.ReasonEnum;
import com.gw.das.common.enums.SystemConfigEnum;
import com.gw.das.common.response.ApiPageResult;
import com.gw.das.common.token.TokenCache;
import com.gw.das.common.utils.HttpUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.common.utils.SystemConfigUtil;
import com.gw.das.dao.trade.bean.DealVO;
import com.gw.das.dao.trade.bean.PositionVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeAccountDiagnosis.TradeDetailGts2Service;

/**
 * 交易记录接口
 * 
 * @author darren
 *
 */
@Service
public class TradeDetailGts2ServiceImpl implements TradeDetailGts2Service {


	/**
	 * 分页查询所有平仓交易记录-宽表查询
	 */
	@Override
	public PageGrid<TradeSearchModel> findTradeDetailCloseWithPageList(PageGrid<TradeSearchModel> pageGrid)
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
				
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.tradeDetailClosePageList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		int total = apiResult.getTotal();
		List<DealVO> rowsList = new ArrayList<DealVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
					
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealVO>>(){});
		}		
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
		for (DealVO dealVO : rowsList) {
			dealVO.setOpenprice(NumberUtil.NumberFormat(dealVO.getOpenprice(),businessplatform));
			dealVO.setOpenvolume(NumberUtil.NumberFormat(dealVO.getOpenvolume(),businessplatform));
			dealVO.setClosevolume(NumberUtil.NumberFormat(dealVO.getClosevolume(),businessplatform));
			dealVO.setVolume(NumberUtil.NumberFormat(dealVO.getVolume(),businessplatform));
			dealVO.setStoploss(NumberUtil.NumberFormat(dealVO.getStoploss(),businessplatform));
			dealVO.setTakeprofit(NumberUtil.NumberFormat(dealVO.getTakeprofit(),businessplatform));	
			dealVO.setCloseprice(NumberUtil.NumberFormat(dealVO.getCloseprice(),businessplatform));
			dealVO.setCommission(NumberUtil.NumberFormat(dealVO.getCommission(),businessplatform));
			dealVO.setSwap(NumberUtil.NumberFormat(dealVO.getSwap(),businessplatform));
			dealVO.setProfit(NumberUtil.NumberFormat(dealVO.getProfit(),businessplatform));		
			dealVO.setReason(ReasonEnum.getReason(dealVO.getReason()));
			dealVO.setProfitrase(NumberUtil.NumberFormat(dealVO.getProfitrase(),businessplatform));
		}
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 	
		return page;
	}
	
	/**
	 * 不分页查询所有平仓交易记录-宽表查询
	 */
	@Override
	public List<DealVO> findTradeDetailCloseWithList(TradeSearchModel searchBean)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(UserContext.get().getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(UserContext.get().getCompanyId()+""));
		paramMap.put("accountNo", searchBean.getAccountno());
		//paramMap.put("platform", null);
		paramMap.put("startDate", searchBean.getStartTime());
		paramMap.put("endDate", searchBean.getEndTime());
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.tradeDetailCloseList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		List<DealVO> rowsList = new ArrayList<DealVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
			
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealVO>>(){});
		}
		
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
		for (DealVO dealVO : rowsList) {
			dealVO.setOpenprice(NumberUtil.NumberFormat(dealVO.getOpenprice(),businessplatform));
			dealVO.setOpenvolume(NumberUtil.NumberFormat(dealVO.getOpenvolume(),businessplatform));
			dealVO.setClosevolume(NumberUtil.NumberFormat(dealVO.getClosevolume(),businessplatform));
			dealVO.setVolume(NumberUtil.NumberFormat(dealVO.getVolume(),businessplatform));
			dealVO.setStoploss(NumberUtil.NumberFormat(dealVO.getStoploss(),businessplatform));
			dealVO.setTakeprofit(NumberUtil.NumberFormat(dealVO.getTakeprofit(),businessplatform));	
			dealVO.setCloseprice(NumberUtil.NumberFormat(dealVO.getCloseprice(),businessplatform));
			dealVO.setCommission(NumberUtil.NumberFormat(dealVO.getCommission(),businessplatform));
			dealVO.setSwap(NumberUtil.NumberFormat(dealVO.getSwap(),businessplatform));
			dealVO.setProfit(NumberUtil.NumberFormat(dealVO.getProfit(),businessplatform));		
			dealVO.setReason(ReasonEnum.getReason(dealVO.getReason()));
			dealVO.setProfitrase(NumberUtil.NumberFormat(dealVO.getProfitrase(),businessplatform));
		}
		return rowsList;
	}	

	/**
	 * 分页查询所有开仓交易记录-宽表查询
	 */
	@Override
	public PageGrid<TradeSearchModel> findTradeDetailOpenWithPageList(PageGrid<TradeSearchModel> pageGrid)
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
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.tradeDetailOpenPageList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		int total = apiResult.getTotal();
		List<PositionVO> rowsList = new ArrayList<PositionVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
			
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<PositionVO>>(){});
		}
		
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());	
		for (PositionVO positionVO : rowsList) {
			positionVO.setStoploss(NumberUtil.NumberFormat(positionVO.getStoploss(),businessplatform));
			positionVO.setOpenvolume(NumberUtil.NumberFormat(positionVO.getOpenvolume(),businessplatform));
			positionVO.setVolume(NumberUtil.NumberFormat(positionVO.getVolume(),businessplatform));
			positionVO.setOpenprice(NumberUtil.NumberFormat(positionVO.getOpenprice(),businessplatform));
			positionVO.setTakeprofit(NumberUtil.NumberFormat(positionVO.getTakeprofit(),businessplatform));
			positionVO.setCommission(NumberUtil.NumberFormat(positionVO.getCommission(),businessplatform));
			positionVO.setSwap(NumberUtil.NumberFormat(positionVO.getSwap(),businessplatform));	
			positionVO.setReason(ReasonEnum.getReason(positionVO.getReason()));
		}
		
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList); 	
		return page;
	}
	
	/**
	 * 不分页查询所有开仓交易记录-宽表查询
	 */
	@Override
	public List<PositionVO> findTradeDetailOpenWithList(TradeSearchModel searchBean)
			throws Exception {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("token", TokenCache.getToken(UserContext.get().getCompanyId()+""));
		paramMap.put("companyId", CompanyEnum.getAPICompanyId(UserContext.get().getCompanyId()+""));
		paramMap.put("accountNo", searchBean.getAccountno());
		//paramMap.put("platform", null);
		paramMap.put("startDate", searchBean.getStartTime());
		paramMap.put("endDate", searchBean.getEndTime());
		String rpcResult = HttpUtil.getInstance().doPost(SystemConfigUtil.getProperty(SystemConfigEnum.accountAnalyzeApiUrl) + Constants.tradeDetailOpenList, paramMap);
		ApiPageResult apiResult = JacksonUtil.readValue(rpcResult, ApiPageResult.class);
		List<PositionVO> rowsList = new ArrayList<PositionVO>();
		if(null != apiResult.getResult() && !"".equals(apiResult.getResult())){
			List result = (List) apiResult.getResult();
			String rows = JacksonUtil.toJSon(result);
			
			rowsList = JacksonUtil.readValue(rows, new TypeReference<List<PositionVO>>(){});
		}
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
		for (PositionVO positionVO : rowsList) {
			positionVO.setStoploss(NumberUtil.NumberFormat(positionVO.getStoploss(),businessplatform));
			positionVO.setOpenvolume(NumberUtil.NumberFormat(positionVO.getOpenvolume(),businessplatform));
			positionVO.setVolume(NumberUtil.NumberFormat(positionVO.getVolume(),businessplatform));
			positionVO.setOpenprice(NumberUtil.NumberFormat(positionVO.getOpenprice(),businessplatform));
			positionVO.setTakeprofit(NumberUtil.NumberFormat(positionVO.getTakeprofit(),businessplatform));
			positionVO.setCommission(NumberUtil.NumberFormat(positionVO.getCommission(),businessplatform));
			positionVO.setSwap(NumberUtil.NumberFormat(positionVO.getSwap(),businessplatform));	
			positionVO.setReason(ReasonEnum.getReason(positionVO.getReason()));
		}
		return rowsList;
	}	

}
