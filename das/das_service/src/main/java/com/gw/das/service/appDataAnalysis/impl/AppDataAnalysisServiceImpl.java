package com.gw.das.service.appDataAnalysis.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.enums.ReportTypeEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.common.utils.NumberUtil;
import com.gw.das.common.utils.ReflectionUtil;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisDetailsVO;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisSearchModel;
import com.gw.das.dao.appDataAnalysis.bean.AppDataAnalysisVO;
import com.gw.das.dao.appDataAnalysis.bean.OpenSourceAnalysisVO;
import com.gw.das.dao.website.bean.DasStatisticsReport;
import com.gw.das.service.appDataAnalysis.AppDataAnalysisService;

/**
 * App分析报表接口
 * 
 * @author darren
 *
 */
@Service
public class AppDataAnalysisServiceImpl implements AppDataAnalysisService{
    
	/**
	 * 分页查询APP数据分析报表记录
	 */
	@Override
	public PageGrid<AppDataAnalysisSearchModel> findAppDataAnalysisPageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
				AppDataAnalysisSearchModel detail = pageGrid.getSearchModel();
				if (detail != null) {															
					
					if(ReportTypeEnum.days.getLabelKey().equals(detail.getReportType())){
							detail.setSort("dt_day");
					}
						
					if(ReportTypeEnum.months.getLabelKey().equals(detail.getReportType())){
							detail.setSort("dt_month");
					}
						
					if(ReportTypeEnum.weeks.getLabelKey().equals(detail.getReportType())){
							detail.setSort("dt_month");
					}
					
					if(DeviceTypeEnum.ANDROID.getLabelKey().equals(detail.getDeviceType())){
						detail.setDeviceType(DeviceTypeEnum.ANDROID.getValue());
					}
					else if(DeviceTypeEnum.IOS.getLabelKey().equals(detail.getDeviceType())){
						detail.setDeviceType(DeviceTypeEnum.IOS.getValue());
					}else{
						detail.setDeviceType(null);
					}
				}
				detail.setPageNumber(pageGrid.getPageNumber());
				detail.setPageSize(pageGrid.getPageSize());

				RpcResult rpcResult = RpcUtils.post(Constants.findAppDataAnalysisPageList, BeanToMapUtil.toMap(detail),
						UserContext.get().getCompanyId());
				String result = rpcResult.getResult() + "";

				Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
				});
				String rows = resultMap.get("rows");
				String total = resultMap.get("total");
				String businessplatform = String.valueOf(UserContext.get().getCompanyId());		
				List<AppDataAnalysisVO> rowsList = JacksonUtil.readValue(rows,
						new TypeReference<List<AppDataAnalysisVO>>() {
						});	
				for (AppDataAnalysisVO appDataAnalysisVO : rowsList) {
					appDataAnalysisVO.setDemoNewuserLoginRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysisVO.getDemoNewuserLoginRate()) * 100),businessplatform)+"%");
					appDataAnalysisVO.setDemoNewuserTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysisVO.getDemoNewuserTradeRate()) * 100),businessplatform)+"%");
					appDataAnalysisVO.setDemoOlduserTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysisVO.getDemoOlduserTradeRate()) * 100),businessplatform)+"%");
					appDataAnalysisVO.setDemoAllTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysisVO.getDemoAllTrade()) * 100),businessplatform)+"%");
					
					appDataAnalysisVO.setRealNewuserDepositRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysisVO.getRealNewuserDepositRate()) * 100),businessplatform)+"%");
					appDataAnalysisVO.setRealNewuserTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysisVO.getRealNewuserTradeRate()) * 100),businessplatform)+"%");
					appDataAnalysisVO.setRealNewuserLoginRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysisVO.getRealNewuserLoginRate()) * 100),businessplatform)+"%");
					
				}
				
				PageGrid<AppDataAnalysisSearchModel> page = new PageGrid<AppDataAnalysisSearchModel>();
				if(Integer.valueOf(total) >0){
					// 合计				
					RpcResult rpcResultTotal = RpcUtils.post(Constants.findAppDataAnalysisList, BeanToMapUtil.toMap(detail),UserContext.get().getCompanyId());
					String resultTotal = rpcResultTotal.getResult() + "";

					Map<String, String> resultMapTotal = JacksonUtil.readValue(resultTotal, new TypeReference<Map<String, String>>() {
					});
					String rowsTotal = resultMapTotal.get("rows");
					List<AppDataAnalysisVO> rowsListTotal = JacksonUtil.readValue(rowsTotal, new TypeReference<List<AppDataAnalysisVO>>() {
					});
					
					AppDataAnalysisVO footer = new AppDataAnalysisVO();
							
		            footer.setChannel("-");
					footer.setDevicetype("-");
					footer.setDemoNewuserLoginRate("-");
					footer.setDemoNewuserTradeRate("-");
					footer.setDemoOlduserTradeRate("-");
					footer.setDemoAllTradeRate("-");
					footer.setRealNewuserDepositRate("-");
					footer.setRealNewuserTradeRate("-");
					footer.setRealNewuserLoginRate("-");
					for(AppDataAnalysisVO appDataAnalysis: rowsListTotal){					
						// 合计								
						footer.setActiveuser(footer.getActiveuser()+appDataAnalysis.getActiveuser());
						footer.setNewuser(footer.getNewuser()+appDataAnalysis.getNewuser());
						footer.setOlduserDemo(footer.getOlduserDemo()+appDataAnalysis.getOlduserDemo());
						footer.setOlduserReal(footer.getOlduserReal()+appDataAnalysis.getOlduserReal());
						footer.setOlduserActivate(footer.getOlduserActivate()+appDataAnalysis.getOlduserActivate());
						footer.setDemoAccount(footer.getDemoAccount()+appDataAnalysis.getDemoAccount());
						footer.setDemoNewuserLogin(footer.getDemoNewuserLogin()+appDataAnalysis.getDemoNewuserLogin());
						footer.setDemoNewuserTrade(footer.getDemoNewuserTrade()+appDataAnalysis.getDemoNewuserTrade());
						footer.setDemoOlduserLogin(footer.getDemoOlduserLogin()+appDataAnalysis.getDemoOlduserLogin());
						footer.setDemoOlduserTrade(footer.getDemoOlduserTrade()+appDataAnalysis.getDemoOlduserTrade());
						footer.setDemoAllLogin(footer.getDemoAllLogin()+appDataAnalysis.getDemoAllLogin());
						footer.setDemoAllTrade(footer.getDemoAllTrade()+appDataAnalysis.getDemoAllTrade());																								
						
						footer.setRealAccount(footer.getRealAccount()+appDataAnalysis.getRealAccount());
						footer.setRealNewuserLogin(footer.getRealNewuserLogin()+appDataAnalysis.getRealNewuserLogin());
						footer.setRealNewuserDeposit(footer.getRealNewuserDeposit()+appDataAnalysis.getRealNewuserDeposit());
						footer.setRealNewuserTrade(footer.getRealNewuserTrade()+appDataAnalysis.getRealNewuserTrade());											
						
						footer.setFooter("footer");
				}				
				List<AppDataAnalysisVO> list = new ArrayList<AppDataAnalysisVO>();
				list.add(footer);
				page.setFooter(list); 	
			}				
			
			page.setTotal(Integer.parseInt(total));
			page.setPageNumber(pageGrid.getPageNumber());
			page.setPageSize(pageGrid.getPageSize());
			page.setRows(rowsList);
			
			return page;
	}
	
	/**
	 * 不分页查询APP数据分析报表记录
	 */
	@Override
	public List<AppDataAnalysisVO> findAppDataAnalysisList(AppDataAnalysisSearchModel searchModel) throws Exception{																	
		if(StringUtils.isBlank(searchModel.getSort())){
			if(ReportTypeEnum.days.getLabelKey().equals(searchModel.getReportType())){
				searchModel.setSort("dt_day");
			}
			
			if(ReportTypeEnum.months.getLabelKey().equals(searchModel.getReportType())){
				searchModel.setSort("dt_month");
			}
			
			if(ReportTypeEnum.weeks.getLabelKey().equals(searchModel.getReportType())){
				searchModel.setSort("dt_month");
			}
			searchModel.setOrder("desc");
		}else{				
			String sort = ReflectionUtil.getColumnName(searchModel.getSort(),AppDataAnalysisVO.class);
			if(StringUtils.isNotBlank(sort)){
				searchModel.setSort(sort);
				searchModel.setOrder(searchModel.getOrder());
			}			
		}				
		
		if(DeviceTypeEnum.ANDROID.getLabelKey().equals(searchModel.getDeviceType())){
			searchModel.setDeviceType(DeviceTypeEnum.ANDROID.getValue());
		}
		else if(DeviceTypeEnum.IOS.getLabelKey().equals(searchModel.getDeviceType())){
			searchModel.setDeviceType(DeviceTypeEnum.IOS.getValue());
		}else{
			searchModel.setDeviceType(null);
		}
		
		RpcResult rpcResult = RpcUtils.post(Constants.findAppDataAnalysisList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<AppDataAnalysisVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<AppDataAnalysisVO>>() {
		});
		if(null != rowsList && rowsList.size() >0){
			// 合计
			AppDataAnalysisVO footer = new AppDataAnalysisVO();
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());				
	        footer.setChannel("-");
			footer.setDevicetype("-");
			footer.setDemoNewuserLoginRate("-");
			footer.setDemoNewuserTradeRate("-");
			footer.setDemoOlduserTradeRate("-");
			footer.setDemoAllTradeRate("-");
			footer.setRealNewuserDepositRate("-");
			footer.setRealNewuserTradeRate("-");
			footer.setRealNewuserLoginRate("-");	
			footer.setDtDay("小计: ");
			for (AppDataAnalysisVO appDataAnalysis : rowsList) {
				if(!ReportTypeEnum.days.getLabelKey().equals(searchModel.getReportType())){
					appDataAnalysis.setDtDay(appDataAnalysis.getDtMonth());
				}			
				// 合计								
				footer.setActiveuser(footer.getActiveuser()+appDataAnalysis.getActiveuser());
				footer.setNewuser(footer.getNewuser()+appDataAnalysis.getNewuser());
				footer.setOlduserDemo(footer.getOlduserDemo()+appDataAnalysis.getOlduserDemo());
				footer.setOlduserReal(footer.getOlduserReal()+appDataAnalysis.getOlduserReal());
				footer.setOlduserActivate(footer.getOlduserActivate()+appDataAnalysis.getOlduserActivate());
				footer.setDemoAccount(footer.getDemoAccount()+appDataAnalysis.getDemoAccount());
				footer.setDemoNewuserLogin(footer.getDemoNewuserLogin()+appDataAnalysis.getDemoNewuserLogin());
				footer.setDemoNewuserTrade(footer.getDemoNewuserTrade()+appDataAnalysis.getDemoNewuserTrade());
				footer.setDemoOlduserLogin(footer.getDemoOlduserLogin()+appDataAnalysis.getDemoOlduserLogin());
				footer.setDemoOlduserTrade(footer.getDemoOlduserTrade()+appDataAnalysis.getDemoOlduserTrade());
				footer.setDemoAllLogin(footer.getDemoAllLogin()+appDataAnalysis.getDemoAllLogin());
				footer.setDemoAllTrade(footer.getDemoAllTrade()+appDataAnalysis.getDemoAllTrade());
													
				appDataAnalysis.setDemoNewuserLoginRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysis.getDemoNewuserLoginRate()) * 100),businessplatform)+"%");
				appDataAnalysis.setDemoNewuserTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysis.getDemoNewuserTradeRate()) * 100),businessplatform)+"%");
				appDataAnalysis.setDemoOlduserTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysis.getDemoOlduserTradeRate()) * 100),businessplatform)+"%");
				appDataAnalysis.setDemoAllTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysis.getDemoAllTrade()) * 100),businessplatform)+"%");
				
				
				footer.setRealAccount(footer.getRealAccount()+appDataAnalysis.getRealAccount());
				footer.setRealNewuserLogin(footer.getRealNewuserLogin()+appDataAnalysis.getRealNewuserLogin());
				footer.setRealNewuserDeposit(footer.getRealNewuserDeposit()+appDataAnalysis.getRealNewuserDeposit());
				footer.setRealNewuserTrade(footer.getRealNewuserTrade()+appDataAnalysis.getRealNewuserTrade());					
				
				appDataAnalysis.setRealNewuserDepositRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysis.getRealNewuserDepositRate()) * 100),businessplatform)+"%");
				appDataAnalysis.setRealNewuserTradeRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysis.getRealNewuserTradeRate()) * 100),businessplatform)+"%");
				appDataAnalysis.setRealNewuserLoginRate(NumberUtil.NumberFormat(String.valueOf(Double.valueOf(appDataAnalysis.getRealNewuserLoginRate()) * 100),businessplatform)+"%");
			}
			
			rowsList.add(footer);
		}
		
		return rowsList;		
	}

	
	/**
	 * 分页查询APP数据分析详情报表记录
	 */
	@Override
	public PageGrid<AppDataAnalysisSearchModel> findAppDataAnalysisDetailsPageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
			AppDataAnalysisSearchModel detail = pageGrid.getSearchModel();				
			detail.setPageNumber(pageGrid.getPageNumber());
			detail.setPageSize(pageGrid.getPageSize());
			String fieldParam = detail.getField();
	
			RpcResult rpcResult = RpcUtils.post(Constants.findAppDataAnalysisDetailsPageList, BeanToMapUtil.toMap(detail),
					UserContext.get().getCompanyId());
			String result = rpcResult.getResult() + "";
	
			Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
			});
			String rows = resultMap.get("rows");
			String total = resultMap.get("total");
	
			List<AppDataAnalysisDetailsVO> rowsList = JacksonUtil.readValue(rows,
					new TypeReference<List<AppDataAnalysisDetailsVO>>() {});
			String businessplatform = String.valueOf(UserContext.get().getCompanyId());	
			for(AppDataAnalysisDetailsVO appDataAnalysisDetailsVO:rowsList){
				appDataAnalysisDetailsVO.setParamValue(detail.getParamValue());				
				if(!detail.isChannelChecked() && !detail.isDevicetypeChecked()){
					if(!detail.isChannelChecked()){
						appDataAnalysisDetailsVO.setChannel("-");
					}
					if(!detail.isDevicetypeChecked()){
						appDataAnalysisDetailsVO.setDevicetype("-");
					}				
				}
				Field[] fields = appDataAnalysisDetailsVO.getClass().getDeclaredFields();
				for (Field field : fields) {
					String fieldName = field.getName();
					if(fieldName.equals(fieldParam)){
						field.setAccessible(true); 
				         Object val = field.get(appDataAnalysisDetailsVO);
				         if(null != val){
				        	 appDataAnalysisDetailsVO.setMavgValue(NumberUtil.NumberFormat(String.valueOf(val),businessplatform));
				         }
					}
				}
			}
														
			PageGrid<AppDataAnalysisSearchModel> page = new PageGrid<AppDataAnalysisSearchModel>();
			page.setTotal(Integer.parseInt(total));
			page.setPageNumber(pageGrid.getPageNumber());
			page.setPageSize(pageGrid.getPageSize());
			page.setRows(rowsList);
			return page;
	}
	
	/**
	 * 不分页查询APP数据分析详情报表记录
	 */
	@Override
	public List<AppDataAnalysisDetailsVO> findAppDataAnalysisDetailsList(AppDataAnalysisSearchModel searchModel) throws Exception{	
		String mobiletype = searchModel.getMobiletype();
		if(DeviceTypeEnum.ANDROID.getValue().equals(mobiletype)){
			searchModel.setMobiletype(DeviceTypeEnum.ANDROID.getLabelKey());
		}
		if(DeviceTypeEnum.IOS.getValue().equals(mobiletype)){
			searchModel.setMobiletype(DeviceTypeEnum.IOS.getLabelKey());
		}
		RpcResult rpcResult = RpcUtils.post(Constants.findAppDataAnalysisDetailsList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		String fieldParam = searchModel.getField();
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<AppDataAnalysisDetailsVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<AppDataAnalysisDetailsVO>>() {
		});
		String businessplatform = String.valueOf(UserContext.get().getCompanyId());	
		for(AppDataAnalysisDetailsVO appDataAnalysisDetailsVO:rowsList){
			appDataAnalysisDetailsVO.setParamValue(searchModel.getParamValue());				
			if(!searchModel.isChannelChecked() && !searchModel.isDevicetypeChecked()){
				if(!searchModel.isChannelChecked()){
					appDataAnalysisDetailsVO.setChannel("-");
				}
				if(!searchModel.isDevicetypeChecked()){
					appDataAnalysisDetailsVO.setDevicetype("-");
				}				
			}
			Field[] fields = appDataAnalysisDetailsVO.getClass().getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if(fieldName.equals(fieldParam)){
					field.setAccessible(true); 
			         Object val = field.get(appDataAnalysisDetailsVO);
			         if(null != val){
			        	 appDataAnalysisDetailsVO.setMavgValue(NumberUtil.NumberFormat(String.valueOf(val),businessplatform));
			         }
				}
			}
		}
		return rowsList;	
	}
	
	/**
	 * 分页查询开户来源分析报表记录
	 */
	@Override
	public PageGrid<AppDataAnalysisSearchModel> findOpenSourceAnalysisPageList(PageGrid<AppDataAnalysisSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
				AppDataAnalysisSearchModel detail = pageGrid.getSearchModel();				
				detail.setPageNumber(pageGrid.getPageNumber());
				detail.setPageSize(pageGrid.getPageSize());

				RpcResult rpcResult = RpcUtils.post(Constants.findOpenSourceAnalysisPageList, BeanToMapUtil.toMap(detail),
						UserContext.get().getCompanyId());
				String result = rpcResult.getResult() + "";

				Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
				});
				String rows = resultMap.get("rows");
				String total = resultMap.get("total");

				List<OpenSourceAnalysisVO> rowsList = JacksonUtil.readValue(rows,
						new TypeReference<List<OpenSourceAnalysisVO>>() {
						});	
				
				//test
				rowsList = new ArrayList<OpenSourceAnalysisVO>();
				for (int i = 0; i < 8; i++) {
					OpenSourceAnalysisVO openSourceAnalysisVO = new OpenSourceAnalysisVO();
					
					openSourceAnalysisVO.setDtMonth(DateUtil.formatDateToString(new Date()));
					openSourceAnalysisVO.setDtDay(RandomUtils.nextInt(1000));
					openSourceAnalysisVO.setPartitionfield(RandomUtils.nextInt(1000));
					openSourceAnalysisVO.setChannel(i);
					openSourceAnalysisVO.setMobiletype(i);					
					openSourceAnalysisVO.setActiveuser(RandomUtils.nextInt(1000));
					openSourceAnalysisVO.setNewuser(RandomUtils.nextInt(1000));
					
					rowsList.add(openSourceAnalysisVO);
				}
				
				// 合计
				OpenSourceAnalysisVO footer = new OpenSourceAnalysisVO();
				for(OpenSourceAnalysisVO openSourceAnalysisVO: rowsList){															
					// 合计
					footer.setDtDay(footer.getDtDay()+openSourceAnalysisVO.getDtDay());
					footer.setPartitionfield(footer.getPartitionfield()+openSourceAnalysisVO.getPartitionfield());	
					footer.setChannel(footer.getChannel()+openSourceAnalysisVO.getChannel());	
					footer.setMobiletype(footer.getMobiletype()+openSourceAnalysisVO.getMobiletype());	
					footer.setActiveuser(footer.getActiveuser()+openSourceAnalysisVO.getActiveuser());	
					footer.setNewuser(footer.getNewuser()+openSourceAnalysisVO.getNewuser());		            
			}
			
			List<OpenSourceAnalysisVO> list = new ArrayList<OpenSourceAnalysisVO>();
			list.add(footer);			
						
			PageGrid<AppDataAnalysisSearchModel> page = new PageGrid<AppDataAnalysisSearchModel>();
			page.setTotal(Integer.parseInt(total));
			page.setPageNumber(pageGrid.getPageNumber());
			page.setPageSize(pageGrid.getPageSize());
			page.setRows(rowsList);
			page.setFooter(list); 
			return page;
	}
}
