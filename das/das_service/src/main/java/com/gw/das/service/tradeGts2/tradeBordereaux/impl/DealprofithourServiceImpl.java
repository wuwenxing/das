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
import com.gw.das.dao.trade.bean.DealprofithourVO;
import com.gw.das.dao.trade.bean.TradeSearchModel;
import com.gw.das.service.tradeGts2.tradeBordereaux.DealprofithourService;

/**
 * 公司盈亏报表接口
 * 
 * @author darren
 *
 */
@Service
public class DealprofithourServiceImpl implements DealprofithourService{
    
	/**
	 * 分页查询公司盈亏记录
	 */
	@Override
	public PageGrid<TradeSearchModel> findDealprofithourPage(PageGrid<TradeSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		TradeSearchModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findDealprofithourPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DealprofithourVO> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DealprofithourVO>>() {
				});	
		PageGrid<TradeSearchModel> page = new PageGrid<TradeSearchModel>();
		if(Integer.valueOf(total) > 0){
			//查总记录
			RpcResult rpcResultTotal = RpcUtils.post(Constants.findDealprofithourList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
			String resultTotal = rpcResultTotal.getResult() + "";

			Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {
			});
			String rowsTotal = resultMapTotal.get("rows");
			List<DealprofithourVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<DealprofithourVO>>() {
			});
			
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());
			for (DealprofithourVO dealprofithourVO : rowsList) {	
				dealprofithourVO.setCompanyprofit(NumberUtil.NumberFormat(dealprofithourVO.getCompanyprofit(),businessplatform));
			}
			
			Double companyprofit = 0.0;
			for (DealprofithourVO dealprofithourVO : rowsListTotal) {					
				companyprofit += Double.valueOf(dealprofithourVO.getCompanyprofit());								
				dealprofithourVO.setCompanyprofit(NumberUtil.NumberFormat(dealprofithourVO.getCompanyprofit(),businessplatform));
			}
			DealprofithourVO dealprofithourVOTotal = new DealprofithourVO();
			dealprofithourVOTotal.setPlatform(detail.getPlatformType());
			dealprofithourVOTotal.setCompanyprofit(NumberUtil.NumberFormat(String.valueOf(companyprofit),businessplatform));
			
			List<DealprofithourVO> listFooter = new ArrayList<DealprofithourVO>();
			listFooter.add(dealprofithourVOTotal);
			page.setFooter(listFooter);
		}	
		
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;	
	}
	
	/**
	 * 不分页查询公司盈亏记录
	 */
	@Override
	public List<DealprofithourVO> findDealprofithourList(TradeSearchModel searchModel) throws Exception{																	
		searchModel.setSort(searchModel.getSort());
		searchModel.setOrder(searchModel.getOrder());
		String detailed = searchModel.getDetailed();
		RpcResult rpcResult = RpcUtils.post(Constants.findDealprofithourList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DealprofithourVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DealprofithourVO>>() {
		});		
		
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());
		Double companyprofit = 0.0;
		for (DealprofithourVO dealprofithourVO : rowsList) {
			companyprofit += Double.valueOf(dealprofithourVO.getCompanyprofit());
			dealprofithourVO.setCompanyprofit(NumberUtil.NumberFormat(dealprofithourVO.getCompanyprofit(),businessplatform));
		}
		if(DetailedEnum.IS_DETAILED.getValue().equals(detailed)){
			DealprofithourVO dealprofithourVOTotal = new DealprofithourVO();
			dealprofithourVOTotal.setPlatform(searchModel.getPlatformType());
			dealprofithourVOTotal.setCompanyprofit(NumberUtil.NumberFormat(String.valueOf(companyprofit),businessplatform));
			dealprofithourVOTotal.setExectime("当日总盈亏：");
			rowsList.add(dealprofithourVOTotal);
		}
		
		return rowsList;		
	}

}
