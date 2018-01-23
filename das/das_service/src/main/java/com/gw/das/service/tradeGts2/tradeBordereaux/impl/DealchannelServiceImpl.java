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
import com.gw.das.dao.trade.bean.DealchannelVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeBordereaux.DealchannelService;

/**
 * GTS2下单途径比例报表接口
 * 
 * @author darren
 *
 */
@Service
public class DealchannelServiceImpl implements DealchannelService{
    
	/**
	 * 分页查询GTS2下单途径比例报表记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDealchannelPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findDealchannelPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DealchannelVO> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DealchannelVO>>() {
				});	
		
		for (DealchannelVO dealchannelVO : rowsList) {
			dealchannelVO.setTotalamount(dealchannelVO.getAndroidamount() + dealchannelVO.getIosamount() + 
					dealchannelVO.getSystemamount() + dealchannelVO.getPcamount() + dealchannelVO.getOtheramount());
		}
		
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(Integer.valueOf(total) >0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findDealchannelList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";
			Map<String, String> resultTotalMap = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {
			});
			String rowsTotal = resultTotalMap.get("rows");
			List<DealchannelVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DealchannelVO>>() {
			});
			
			
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());	
			Double androidamount = 0D;
			Double iosamount = 0D;
			Double systemamount = 0D;
			Double pcamount = 0D;
			Double otheramount = 0D;
			Double totalamount = 0D;
			for (DealchannelVO dealchannelVO : rowsListTotal) {
				androidamount += dealchannelVO.getAndroidamount();
				iosamount += dealchannelVO.getIosamount();
				systemamount += dealchannelVO.getSystemamount();
				pcamount += dealchannelVO.getPcamount();
				otheramount += dealchannelVO.getOtheramount();				
			}
			DealchannelVO dealchannelVOTotal = new DealchannelVO();
			dealchannelVOTotal.setPlatform(detail.getPlatformType());
			totalamount = dealchannelVOTotal.getAndroidamount() + dealchannelVOTotal.getIosamount() + 
					dealchannelVOTotal.getSystemamount() + dealchannelVOTotal.getPcamount() + dealchannelVOTotal.getOtheramount();
			dealchannelVOTotal.setAndroidamount(androidamount);
			dealchannelVOTotal.setIosamount(iosamount);
			dealchannelVOTotal.setSystemamount(systemamount);
			dealchannelVOTotal.setPcamount(pcamount);
			dealchannelVOTotal.setOtheramount(otheramount);
			dealchannelVOTotal.setTotalamount(totalamount);
			dealchannelVOTotal.setTotalamount(totalamount);
			
			DealchannelVO dealchannelVOMavg = new DealchannelVO();
			dealchannelVOMavg.setPlatform(detail.getPlatformType());
			dealchannelVOMavg.setAndroidamount(NumberUtil.NumberFormatDouble(String.valueOf(androidamount/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setIosamount(NumberUtil.NumberFormatDouble(String.valueOf(iosamount/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setSystemamount(NumberUtil.NumberFormatDouble(String.valueOf(systemamount/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setPcamount(NumberUtil.NumberFormatDouble(String.valueOf(pcamount/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setOtheramount(NumberUtil.NumberFormatDouble(String.valueOf(otheramount/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setTotalamount(NumberUtil.NumberFormatDouble(String.valueOf(totalamount/Double.valueOf(total)),businessplatform));
			
			List<DealchannelVO> listFooter = new ArrayList<DealchannelVO>();
			
			listFooter.add(dealchannelVOTotal);
			listFooter.add(dealchannelVOMavg);
			page.setFooter(listFooter);
		}
		
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;	
	}
	
	/**
	 * 不分页查询GTS2下单途径比例报表记录
	 */
	@Override
	public List<DealchannelVO> findDealchannelList(TradeSearchModel searchModel) throws Exception{																	
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String detailed = searchModel.getDetailed();
		RpcResult rpcResult = RpcUtils.post(Constants.findDealchannelList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DealchannelVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealchannelVO>>() {
		});		
		Integer total = rowsList.size();	
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		Double androidamountTotal = 0D;
		Double iosamountTotal = 0D;
		Double systemamountTotal = 0D;
		Double pcamountTotal = 0D;
		Double otheramountTotal = 0D;
		for (DealchannelVO dealchannelVO : rowsList) {
			Double androidamount = dealchannelVO.getAndroidamount();
			Double iosamount = dealchannelVO.getIosamount();
			Double systemamount = dealchannelVO.getSystemamount();
			Double pcamount = dealchannelVO.getPcamount();
			Double otheramount = dealchannelVO.getOtheramount();
			
			androidamountTotal += androidamount;
			iosamountTotal += iosamount;
			systemamountTotal += systemamount;
			pcamountTotal += pcamount;
			otheramountTotal += otheramount;
			
			dealchannelVO.setTotalamount(androidamount + iosamount + systemamount + pcamount + otheramount);
		}
		if(DetailedEnum.IS_DETAILED.getValue().equals(detailed)){
			DealchannelVO dealchannelVOTotal = new DealchannelVO();
			dealchannelVOTotal.setExectime("总数:");
			dealchannelVOTotal.setPlatform(searchModel.getPlatformType());
			dealchannelVOTotal.setAndroidamount(androidamountTotal);
			dealchannelVOTotal.setIosamount(iosamountTotal);
			dealchannelVOTotal.setSystemamount(systemamountTotal);
			dealchannelVOTotal.setPcamount(pcamountTotal);
			dealchannelVOTotal.setOtheramount(otheramountTotal);
			dealchannelVOTotal.setTotalamount(androidamountTotal + iosamountTotal + systemamountTotal + pcamountTotal + otheramountTotal);
			
			DealchannelVO dealchannelVOMavg = new DealchannelVO();
			dealchannelVOMavg.setExectime("平均:");
			dealchannelVOMavg.setPlatform(searchModel.getPlatformType());
			dealchannelVOMavg.setAndroidamount(NumberUtil.NumberFormatDouble(String.valueOf(androidamountTotal/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setIosamount(NumberUtil.NumberFormatDouble(String.valueOf(iosamountTotal/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setSystemamount(NumberUtil.NumberFormatDouble(String.valueOf(systemamountTotal/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setPcamount(NumberUtil.NumberFormatDouble(String.valueOf(pcamountTotal/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setOtheramount(NumberUtil.NumberFormatDouble(String.valueOf(otheramountTotal/Double.valueOf(total)),businessplatform));
			dealchannelVOMavg.setTotalamount(NumberUtil.NumberFormatDouble(String.valueOf((androidamountTotal + iosamountTotal + systemamountTotal + pcamountTotal + otheramountTotal)/Double.valueOf(total)),businessplatform));
			
			rowsList.add(dealchannelVOTotal);
			rowsList.add(dealchannelVOMavg);
		}
		
		return rowsList;		
	}

}
