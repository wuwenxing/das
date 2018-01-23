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
import com.gw.das.dao.trade.bean.DailyreportVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeBordereaux.DailyreportService;

/**
 * 结余图表报表接口
 * 
 * @author darren
 *
 */
@Service
public class DailyreportServiceImpl implements DailyreportService{
    
	/**
	 * 分页查询结余图表报表记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDailyreportPageList(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		String type = detail.getType();
		RpcResult rpcResult = RpcUtils.post(Constants.findDailyreportPageList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DailyreportVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<DailyreportVO>>() {});	
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(Integer.valueOf(total) > 0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findDailyreportList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";

			Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {});
			String rowsTotal = resultMapTotal.get("rows");
			List<DailyreportVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DailyreportVO>>() {});
			
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());			
			
			Double floatingprofitTotal = 0.0;
			Double balanceTotal = 0.0;
			Double marginTotal = 0.0;
			
			Double mt4floatingprofitTotal = 0.0;
			Double mt4balanceTotal = 0.0;
			Double mt4marginTotal = 0.0;
			Double gts2floatingprofitTotal = 0.0;
			Double gts2balanceTotal = 0.0;
			Double gts2marginTotal = 0.0;
			
			for (DailyreportVO dailyreportVO : rowsListTotal) {	
				if(!"business".equals(type)){
					mt4floatingprofitTotal += NumberUtil.StringToDouble(dailyreportVO.getMt4floatingprofit());
					mt4balanceTotal += NumberUtil.StringToDouble(dailyreportVO.getMt4balance());
					mt4marginTotal += NumberUtil.StringToDouble(dailyreportVO.getMt4margin());	
					gts2floatingprofitTotal += NumberUtil.StringToDouble(dailyreportVO.getGts2floatingprofit());
					gts2balanceTotal += NumberUtil.StringToDouble(dailyreportVO.getGts2balance());
					gts2marginTotal += NumberUtil.StringToDouble(dailyreportVO.getGts2margin());			
					
					dailyreportVO.setMt4floatingprofit(NumberUtil.NumberFormat(dailyreportVO.getMt4floatingprofit(),businessplatform));
					dailyreportVO.setMt4balance(NumberUtil.NumberFormat(dailyreportVO.getMt4balance(),businessplatform));
					dailyreportVO.setMt4margin(NumberUtil.NumberFormat(dailyreportVO.getMt4margin(),businessplatform));
					dailyreportVO.setGts2floatingprofit(NumberUtil.NumberFormat(dailyreportVO.getGts2floatingprofit(),businessplatform));
					dailyreportVO.setGts2balance(NumberUtil.NumberFormat(dailyreportVO.getGts2balance(),businessplatform));
					dailyreportVO.setGts2margin(NumberUtil.NumberFormat(dailyreportVO.getGts2margin(),businessplatform));
				}else{
					floatingprofitTotal += NumberUtil.StringToDouble(dailyreportVO.getFloatingprofit());
					balanceTotal += NumberUtil.StringToDouble(dailyreportVO.getBalance());
					marginTotal += NumberUtil.StringToDouble(dailyreportVO.getMargin());
				}
			}
			DailyreportVO dailyreportVOTotal = new DailyreportVO();	
			if(!"business".equals(type)){
				dailyreportVOTotal.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitTotal + gts2floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setBalance(NumberUtil.NumberFormat(String.valueOf(mt4balanceTotal + gts2balanceTotal),businessplatform));
				dailyreportVOTotal.setMargin(NumberUtil.NumberFormat(String.valueOf(mt4marginTotal + gts2marginTotal),businessplatform));
				
				dailyreportVOTotal.setMt4floatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setMt4balance(NumberUtil.NumberFormat(String.valueOf(mt4balanceTotal),businessplatform));
				dailyreportVOTotal.setMt4margin(NumberUtil.NumberFormat(String.valueOf(mt4marginTotal),businessplatform));
				dailyreportVOTotal.setGts2floatingprofit(NumberUtil.NumberFormat(String.valueOf(gts2floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setGts2balance(NumberUtil.NumberFormat(String.valueOf(gts2balanceTotal),businessplatform));
				dailyreportVOTotal.setGts2margin(NumberUtil.NumberFormat(String.valueOf(gts2marginTotal),businessplatform));
			}else{
				dailyreportVOTotal.setCurrency("-");
				dailyreportVOTotal.setPlatform(detail.getPlatformType());
				dailyreportVOTotal.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setBalance(NumberUtil.NumberFormat(String.valueOf(balanceTotal),businessplatform));
				dailyreportVOTotal.setMargin(NumberUtil.NumberFormat(String.valueOf(marginTotal),businessplatform));
			}
			
			List<DailyreportVO> listFooter = new ArrayList<DailyreportVO>();
			listFooter.add(dailyreportVOTotal);
			page.setFooter(listFooter);
			
			for (DailyreportVO dailyreportVO : rowsList) {	
				if(!"business".equals(type)){
					//浮动盈亏比例:浮动盈亏   /总结余 
					//保证金比例  :占用保证金 /总结余
					//2017-09-22 公式更改如下:
					//浮盈比例 ＝ ［浮动盈亏］／［结余］
					//占用保证金比例＝［占用保证金］／［结余］				
					
					Double mt4floatingprofit = NumberUtil.StringToDouble(dailyreportVO.getMt4floatingprofit());				
					Double mt4margin = NumberUtil.StringToDouble(dailyreportVO.getMt4margin());					
					Double mt4balance = NumberUtil.StringToDouble(dailyreportVO.getMt4balance());			
					Double mt4floatingprofitRatio = 0.0;
					Double mt4marginRatio = 0.0;
					if(mt4balance != 0){
						mt4floatingprofitRatio = mt4floatingprofit/mt4balance * 100;
						mt4marginRatio = mt4margin/mt4balance * 100;
					}
					String mt4floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitRatio),businessplatform)+"%";
					String mt4marginStr = NumberUtil.NumberFormat(String.valueOf(mt4marginRatio),businessplatform)+"%";	
					dailyreportVO.setMt4floatingprofitRatio(mt4floatingprofitStr);
					dailyreportVO.setMt4marginRatio(mt4marginStr);
					
					
					Double gts2floatingprofit = NumberUtil.StringToDouble(dailyreportVO.getGts2floatingprofit());	
					Double gts2margin = NumberUtil.StringToDouble(dailyreportVO.getGts2margin());	
					Double gts2balance = NumberUtil.StringToDouble(dailyreportVO.getGts2balance());				
					Double gts2floatingprofitRatio = 0.0;
					Double gts2marginRatio = 0.0;
					if(gts2balance != 0){
						gts2floatingprofitRatio = gts2floatingprofit/gts2balance * 100;
						gts2marginRatio = gts2margin/gts2balance * 100;
					}
					String gts2floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(gts2floatingprofitRatio),businessplatform)+"%";
					String gts2marginStr = NumberUtil.NumberFormat(String.valueOf(gts2marginRatio),businessplatform)+"%";	
					dailyreportVO.setGts2floatingprofitRatio(gts2floatingprofitStr);
					dailyreportVO.setGts2marginRatio(gts2marginStr);
					
					String floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitRatio + gts2floatingprofitRatio),businessplatform)+"%";
					String marginStr = NumberUtil.NumberFormat(String.valueOf(mt4marginRatio + gts2marginRatio),businessplatform)+"%";
					dailyreportVO.setFloatingprofitRatio(floatingprofitStr);
					dailyreportVO.setMarginRatio(marginStr);
					
					dailyreportVO.setFloatingprofit(NumberUtil.NumberFormat(dailyreportVO.getFloatingprofit(),businessplatform));
					dailyreportVO.setBalance(NumberUtil.NumberFormat(dailyreportVO.getBalance(),businessplatform));
					dailyreportVO.setMargin(NumberUtil.NumberFormat(dailyreportVO.getMargin(),businessplatform));
					
					dailyreportVO.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofit + gts2floatingprofit),businessplatform));
					dailyreportVO.setBalance(NumberUtil.NumberFormat(String.valueOf(mt4balance + gts2balance),businessplatform));
					
					dailyreportVO.setMt4floatingprofit(NumberUtil.NumberFormat(dailyreportVO.getMt4floatingprofit(),businessplatform));
					dailyreportVO.setMt4balance(NumberUtil.NumberFormat(dailyreportVO.getMt4balance(),businessplatform));
					dailyreportVO.setMt4margin(NumberUtil.NumberFormat(dailyreportVO.getMt4margin(),businessplatform));
					dailyreportVO.setGts2floatingprofit(NumberUtil.NumberFormat(dailyreportVO.getGts2floatingprofit(),businessplatform));
					dailyreportVO.setGts2balance(NumberUtil.NumberFormat(dailyreportVO.getGts2balance(),businessplatform));
					dailyreportVO.setGts2margin(NumberUtil.NumberFormat(dailyreportVO.getGts2margin(),businessplatform));
				}else{
					//浮动盈亏比例:占用保证金  /总结余
					//保证金比例  :浮动盈亏  /总结余
					//2017-09-22 公式更改如下:
					//浮盈比例 ＝ ［浮动盈亏］／［结余］
					//占用保证金比例＝［占用保证金］／［结余］
					Double floatingprofit = NumberUtil.StringToDouble(dailyreportVO.getFloatingprofit());				
					Double margin = NumberUtil.StringToDouble(dailyreportVO.getMargin());					
					Double balance = NumberUtil.StringToDouble(dailyreportVO.getBalance());				
					Double floatingprofitRatio = 0.0;
					Double marginRatio = 0.0;
					if(balance != 0){
						floatingprofitRatio = floatingprofit/balance * 100;
						marginRatio = margin/balance * 100;
					}				
					String floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(floatingprofitRatio),businessplatform)+"%";
					String marginStr = NumberUtil.NumberFormat(String.valueOf(marginRatio),businessplatform)+"%";				
					dailyreportVO.setFloatingprofitRatio(floatingprofitStr);
					dailyreportVO.setMarginRatio(marginStr);
					dailyreportVO.setPlatform(detail.getPlatformType());
				}			
			}
		}
		
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;	
	}
	
	/**
	 * 不分页查询结余图表报表记录
	 */
	@Override
	public List<DailyreportVO> findDailyreportList(TradeSearchModel searchModel) throws Exception{																	
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String detailed = searchModel.getDetailed();
		String reportType = searchModel.getReportType();
		String type = searchModel.getType();
		RpcResult rpcResult = RpcUtils.post(Constants.findDailyreportList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DailyreportVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DailyreportVO>>() {});

		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		
		Double floatingprofitTotal = 0.0;
		Double balanceTotal = 0.0;
		Double marginTotal = 0.0;
		
		Double mt4floatingprofitTotal = 0.0;
		Double mt4balanceTotal = 0.0;
		Double mt4marginTotal = 0.0;
		Double gts2floatingprofitTotal = 0.0;
		Double gts2balanceTotal = 0.0;
		Double gts2marginTotal = 0.0;
		
		for (DailyreportVO dailyreportVO : rowsList) {	
			if(!"business".equals(type)){
				mt4floatingprofitTotal += NumberUtil.StringToDouble(dailyreportVO.getMt4floatingprofit());
				mt4balanceTotal += NumberUtil.StringToDouble(dailyreportVO.getMt4balance());
				mt4marginTotal += NumberUtil.StringToDouble(dailyreportVO.getMt4margin());	
				gts2floatingprofitTotal += NumberUtil.StringToDouble(dailyreportVO.getGts2floatingprofit());
				gts2balanceTotal += NumberUtil.StringToDouble(dailyreportVO.getGts2balance());
				gts2marginTotal += NumberUtil.StringToDouble(dailyreportVO.getGts2margin());
				
				//浮动盈亏比例:占用保证金  /总结余
				//保证金比例  :浮动盈亏  /总结余
				//2017-09-22 公式更改如下:
				//浮盈比例 ＝ ［浮动盈亏］／［结余］
				//占用保证金比例＝［占用保证金］／［结余］
				Double mt4floatingprofit = NumberUtil.StringToDouble(dailyreportVO.getMt4floatingprofit());				
				Double mt4margin = NumberUtil.StringToDouble(dailyreportVO.getMt4margin());					
				Double mt4balance = NumberUtil.StringToDouble(dailyreportVO.getMt4balance());			
				Double mt4floatingprofitRatio = 0.0;
				Double mt4marginRatio = 0.0;
				if(mt4balance != 0){
					mt4floatingprofitRatio = mt4floatingprofit/mt4balance * 100;
					mt4marginRatio = mt4margin/mt4balance * 100;
				}
				String mt4floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitRatio),businessplatform)+"%";
				String mt4marginStr = NumberUtil.NumberFormat(String.valueOf(mt4marginRatio),businessplatform)+"%";	
				dailyreportVO.setMt4floatingprofitRatio(mt4floatingprofitStr);
				dailyreportVO.setMt4marginRatio(mt4marginStr);
				
				
				Double gts2floatingprofit = NumberUtil.StringToDouble(dailyreportVO.getGts2floatingprofit());	
				Double gts2margin = NumberUtil.StringToDouble(dailyreportVO.getGts2margin());	
				Double gts2balance = NumberUtil.StringToDouble(dailyreportVO.getGts2balance());				
				Double gts2floatingprofitRatio = 0.0;
				Double gts2marginRatio = 0.0;
				if(gts2balance != 0){
					gts2floatingprofitRatio = gts2floatingprofit/gts2balance * 100;
					gts2marginRatio = gts2margin/gts2balance * 100;
				}
				String gts2floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(gts2floatingprofitRatio),businessplatform)+"%";
				String gts2marginStr = NumberUtil.NumberFormat(String.valueOf(gts2marginRatio),businessplatform)+"%";	
				dailyreportVO.setGts2floatingprofitRatio(gts2floatingprofitStr);
				dailyreportVO.setGts2marginRatio(gts2marginStr);	
				
				String floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitRatio + gts2floatingprofitRatio),businessplatform)+"%";
				String marginStr = NumberUtil.NumberFormat(String.valueOf(mt4marginRatio + gts2marginRatio),businessplatform)+"%";
				dailyreportVO.setFloatingprofitRatio(floatingprofitStr);
				dailyreportVO.setMarginRatio(marginStr);
				
				dailyreportVO.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofit + gts2floatingprofit),businessplatform));
				dailyreportVO.setBalance(NumberUtil.NumberFormat(String.valueOf(mt4balance + gts2balance),businessplatform));
				dailyreportVO.setMargin(NumberUtil.NumberFormat(String.valueOf(mt4margin + gts2margin),businessplatform));
				
				dailyreportVO.setMt4floatingprofit(NumberUtil.NumberFormat(dailyreportVO.getMt4floatingprofit(),businessplatform));
				dailyreportVO.setMt4balance(NumberUtil.NumberFormat(dailyreportVO.getMt4balance(),businessplatform));
				dailyreportVO.setMt4margin(NumberUtil.NumberFormat(dailyreportVO.getMt4margin(),businessplatform));
				dailyreportVO.setGts2floatingprofit(NumberUtil.NumberFormat(dailyreportVO.getGts2floatingprofit(),businessplatform));
				dailyreportVO.setGts2balance(NumberUtil.NumberFormat(dailyreportVO.getGts2balance(),businessplatform));
				dailyreportVO.setGts2margin(NumberUtil.NumberFormat(dailyreportVO.getGts2margin(),businessplatform));
			}else{
				//浮动盈亏比例:占用保证金  /总结余
				//保证金比例  :浮动盈亏  /总结余
				//2017-09-22 公式更改如下:
				//浮盈比例 ＝ ［浮动盈亏］／［结余］
				//占用保证金比例＝［占用保证金］／［结余］
				Double floatingprofit = NumberUtil.StringToDouble(dailyreportVO.getFloatingprofit());				
				Double margin = NumberUtil.StringToDouble(dailyreportVO.getMargin());					
				Double balance = NumberUtil.StringToDouble(dailyreportVO.getBalance());				
				Double floatingprofitRatio = 0.0;
				Double marginRatio = 0.0;
				if(balance != 0){
					floatingprofitRatio = floatingprofit/balance * 100;
					marginRatio = margin/balance * 100;
				}				
				String floatingprofitStr = NumberUtil.NumberFormat(String.valueOf(floatingprofitRatio),businessplatform)+"%";
				String marginStr = NumberUtil.NumberFormat(String.valueOf(marginRatio),businessplatform)+"%";				
				dailyreportVO.setFloatingprofitRatio(floatingprofitStr);
				dailyreportVO.setMarginRatio(marginStr);
				
				floatingprofitTotal += NumberUtil.StringToDouble(dailyreportVO.getFloatingprofit());
				balanceTotal += NumberUtil.StringToDouble(dailyreportVO.getBalance());
				marginTotal += NumberUtil.StringToDouble(dailyreportVO.getMargin());
				dailyreportVO.setPlatform(searchModel.getPlatformType());
			}		
		}
		
		if(DetailedEnum.IS_DETAILED.getValue().equals(detailed)){
			DailyreportVO dailyreportVOTotal = new DailyreportVO();
			if(!"business".equals(type)){
				dailyreportVOTotal.setPlatform(searchModel.getPlatformType());
				dailyreportVOTotal.setExectime("小计:");
				dailyreportVOTotal.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitTotal + gts2floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setBalance(NumberUtil.NumberFormat(String.valueOf(mt4balanceTotal + gts2balanceTotal),businessplatform));
				dailyreportVOTotal.setMargin(NumberUtil.NumberFormat(String.valueOf(mt4marginTotal + gts2marginTotal),businessplatform));
				
				dailyreportVOTotal.setMt4floatingprofit(NumberUtil.NumberFormat(String.valueOf(mt4floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setMt4balance(NumberUtil.NumberFormat(String.valueOf(mt4balanceTotal),businessplatform));
				dailyreportVOTotal.setMt4margin(NumberUtil.NumberFormat(String.valueOf(mt4marginTotal),businessplatform));
				dailyreportVOTotal.setGts2floatingprofit(NumberUtil.NumberFormat(String.valueOf(gts2floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setGts2balance(NumberUtil.NumberFormat(String.valueOf(gts2balanceTotal),businessplatform));
				dailyreportVOTotal.setGts2margin(NumberUtil.NumberFormat(String.valueOf(gts2marginTotal),businessplatform));
			}else{
				dailyreportVOTotal.setPlatform(searchModel.getPlatformType());
				dailyreportVOTotal.setExectime("小计:");
				dailyreportVOTotal.setCurrency("-");
				
				dailyreportVOTotal.setFloatingprofit(NumberUtil.NumberFormat(String.valueOf(floatingprofitTotal),businessplatform));
				dailyreportVOTotal.setBalance(NumberUtil.NumberFormat(String.valueOf(balanceTotal),businessplatform));
				dailyreportVOTotal.setMargin(NumberUtil.NumberFormat(String.valueOf(marginTotal),businessplatform));
			}
			
			if(!"4".equals(reportType) && !"5".equals(reportType)){
				rowsList.add(dailyreportVOTotal);
			}
		}
		
		return rowsList;		
	}

}
