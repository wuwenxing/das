package com.gw.das.service.tradeGts2.tradeBordereaux.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.DetailedEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.dao.trade.bean.AccountchannelVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeBordereaux.AccountchannelService;

/**
 * 新开户_激活途径比例接口
 * 
 * @author darren
 *
 */
@Service
public class AccountchannelServiceImpl implements AccountchannelService{
    
	/**
	 * 分页查询新开户_激活途径比例报表记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findAccountchannelPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findAccountchannelPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<AccountchannelVO> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<AccountchannelVO>>() {
				});	
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(Integer.valueOf(total) >0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findAccountchannelList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";
			Map<String, String> resultTotalMap = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {
			});
			String rowsTotal = resultTotalMap.get("rows");
			List<AccountchannelVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<AccountchannelVO>>() {
			});		
			
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());	
			Double webothers = 0D;
			Double others = 0D;
			Double loseinfo = 0D;
			Double webpc = 0D;
			
			Double pc = 0D;
			Double mobile = 0D;
		
			for (AccountchannelVO accountchannelVO : rowsListTotal) {
				webothers += accountchannelVO.getWebothers();
				others += accountchannelVO.getOthers();
				loseinfo += accountchannelVO.getLoseinfo();
				webpc += accountchannelVO.getWebpc();
				
				pc += accountchannelVO.getPc();
				mobile += accountchannelVO.getMobile();			
			}
			AccountchannelVO accountchannelVOTotal = new AccountchannelVO();
			accountchannelVOTotal.setWebothers(webothers);
			accountchannelVOTotal.setOthers(others);
			accountchannelVOTotal.setLoseinfo(loseinfo);
			accountchannelVOTotal.setWebpc(webpc);
			
			accountchannelVOTotal.setPc(pc);
			accountchannelVOTotal.setMobile(mobile);
			
			AccountchannelVO accountchannelVOMavg = new AccountchannelVO();
			accountchannelVOMavg.setWebothers(NumberUtil.NumberFormatDouble(String.valueOf(webothers/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setOthers(NumberUtil.NumberFormatDouble(String.valueOf(others/Double.valueOf(total)),businessplatform));	
			accountchannelVOMavg.setLoseinfo(NumberUtil.NumberFormatDouble(String.valueOf(loseinfo/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setWebpc(NumberUtil.NumberFormatDouble(String.valueOf(webpc/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setPc(NumberUtil.NumberFormatDouble(String.valueOf(pc/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setMobile(NumberUtil.NumberFormatDouble(String.valueOf(mobile/Double.valueOf(total)),businessplatform));
			
			List<AccountchannelVO> listFooter = new ArrayList<AccountchannelVO>();
			accountchannelVOTotal.setPlatform(detail.getPlatformType());
			accountchannelVOMavg.setPlatform(detail.getPlatformType());
			listFooter.add(accountchannelVOTotal);
			listFooter.add(accountchannelVOMavg);
			page.setFooter(listFooter);
		}
		
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;	
	}
	
	/**
	 * 不分页查询新开户_激活途径比例报表记录
	 */
	@Override
	public List<AccountchannelVO> findAccountchannelList(TradeSearchModel searchModel) throws Exception{																	
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String detailed = searchModel.getDetailed();
		RpcResult rpcResult = RpcUtils.post(Constants.findAccountchannelList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<AccountchannelVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<AccountchannelVO>>() {
		});		
		Integer total = rowsList.size();
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		Double webothers = 0D;
		Double others = 0D;
		Double loseinfo = 0D;
		Double webpc = 0D;
		
		Double pc = 0D;
		Double mobile = 0D;
		for (AccountchannelVO accountchannelVO : rowsList) {
			webothers += accountchannelVO.getWebothers();
			others += accountchannelVO.getOthers();
			loseinfo += accountchannelVO.getLoseinfo();
			webpc += accountchannelVO.getWebpc();
			
			pc += accountchannelVO.getPc();
			mobile += accountchannelVO.getMobile();			
		}
		if(DetailedEnum.IS_DETAILED.getValue().equals(detailed)){
			AccountchannelVO accountchannelVOTotal = new AccountchannelVO();
			accountchannelVOTotal.setExectime("总数:");
			accountchannelVOTotal.setPlatform(searchModel.getPlatformType());
			accountchannelVOTotal.setWebothers(webothers);
			accountchannelVOTotal.setOthers(others);
			accountchannelVOTotal.setLoseinfo(loseinfo);
			accountchannelVOTotal.setWebpc(webpc);
			
			accountchannelVOTotal.setPc(pc);
			accountchannelVOTotal.setMobile(mobile);
			
			AccountchannelVO accountchannelVOMavg = new AccountchannelVO();
			accountchannelVOMavg.setExectime("平均:");
			accountchannelVOMavg.setPlatform(searchModel.getPlatformType());
			accountchannelVOMavg.setWebothers(NumberUtil.NumberFormatDouble(String.valueOf(webothers/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setOthers(NumberUtil.NumberFormatDouble(String.valueOf(others/Double.valueOf(total)),businessplatform));	
			accountchannelVOMavg.setLoseinfo(NumberUtil.NumberFormatDouble(String.valueOf(loseinfo/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setWebpc(NumberUtil.NumberFormatDouble(String.valueOf(webpc/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setPc(NumberUtil.NumberFormatDouble(String.valueOf(pc/Double.valueOf(total)),businessplatform));
			accountchannelVOMavg.setMobile(NumberUtil.NumberFormatDouble(String.valueOf(mobile/Double.valueOf(total)),businessplatform));
			
			rowsList.add(accountchannelVOTotal);
			rowsList.add(accountchannelVOMavg);
		}
		
		return rowsList;		
	}

}
