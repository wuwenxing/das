package com.gw.das.service.operateStatistics.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.operateStatistics.bean.ChannelActiveEffectVO;
import com.gw.das.dao.operateStatistics.bean.CustomerQualityVO;
import com.gw.das.dao.operateStatistics.bean.OperateStatisticsModel;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.operateStatistics.OperateStatisticsService;

@Service
public class OperateStatisticsServiceImpl extends BaseService implements OperateStatisticsService {

	@Override
	public PageGrid<OperateStatisticsModel> findChannelActivePageList(PageGrid<OperateStatisticsModel> pageGrid) throws Exception {
		// 设置查询条件
		OperateStatisticsModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findChannelActivePageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<ChannelActiveEffectVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<ChannelActiveEffectVO>>() {});		
		
		PageGrid<OperateStatisticsModel> page = new PageGrid<OperateStatisticsModel>();		
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;					
	}

	@Override
	public List<ChannelActiveEffectVO> findChannelActiveList(OperateStatisticsModel searchModel) throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.findChannelActiveList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<ChannelActiveEffectVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<ChannelActiveEffectVO>>() {});	

		return rowsList;	
	}

	@Override
	public PageGrid<OperateStatisticsModel> findChannelEffectPageList(PageGrid<OperateStatisticsModel> pageGrid) throws Exception {
		// 设置查询条件
		OperateStatisticsModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.findChannelEffectPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<ChannelActiveEffectVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<ChannelActiveEffectVO>>() {});		
		
		PageGrid<OperateStatisticsModel> page = new PageGrid<OperateStatisticsModel>();		
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;				
	}

	@Override
	public List<ChannelActiveEffectVO> findChannelEffectList(OperateStatisticsModel searchModel) throws Exception {
		RpcResult rpcResult = RpcUtils.post(Constants.findChannelEffectList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<ChannelActiveEffectVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<ChannelActiveEffectVO>>() {});	

		return rowsList;	
	}

	@Override
	public PageGrid<OperateStatisticsModel> findCustomerQualityPageList(PageGrid<OperateStatisticsModel> pageGrid)
			throws Exception {
		// 设置查询条件
		OperateStatisticsModel detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());
		
/*		String deviceType = detail.getDeviceType();
		if(DeviceTypeEnum.PC.getLabelKey().equals(deviceType)){
			detail.setDeviceType(DeviceTypeEnum.PC.getValue());
		}
		if(DeviceTypeEnum.ANDROID.getLabelKey().equals(deviceType)){
			detail.setDeviceType(DeviceTypeEnum.ANDROID.getValue());
		}
		if(DeviceTypeEnum.IOS.getLabelKey().equals(deviceType)){
			detail.setDeviceType(DeviceTypeEnum.IOS.getValue());
		}
		if(DeviceTypeEnum.PCUI.getLabelKey().equals(deviceType)){
			detail.setDeviceType(DeviceTypeEnum.PCUI.getValue());
		}
		if(DeviceTypeEnum.APP.getLabelKey().equals(deviceType)){
			detail.setDeviceType(DeviceTypeEnum.APP.getValue());
		}
		if(DeviceTypeEnum.MOBILE.getLabelKey().equals(deviceType)){
			detail.setDeviceType(DeviceTypeEnum.MOBILE.getValue());
		}*/

		RpcResult rpcResult = RpcUtils.post(Constants.findCustomerQualityPageList, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		Integer total = Integer.valueOf(resultMap.get("total"));

		List<CustomerQualityVO> rowsList = JacksonUtil.readValue(rows,new TypeReference<List<CustomerQualityVO>>() {});		
		
		PageGrid<OperateStatisticsModel> page = new PageGrid<OperateStatisticsModel>();		
		
		page.setTotal(total);
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		
		return page;					
	}

	@Override
	public List<CustomerQualityVO> findCustomerQualityList(OperateStatisticsModel searchModel) throws Exception {
/*		String deviceType = searchModel.getDeviceType();
		if(DeviceTypeEnum.PC.getLabelKey().equals(deviceType)){
			searchModel.setDeviceType(DeviceTypeEnum.PC.getValue());
		}
		if(DeviceTypeEnum.ANDROID.getLabelKey().equals(deviceType)){
			searchModel.setDeviceType(DeviceTypeEnum.ANDROID.getValue());
		}
		if(DeviceTypeEnum.IOS.getLabelKey().equals(deviceType)){
			searchModel.setDeviceType(DeviceTypeEnum.IOS.getValue());
		}
		if(DeviceTypeEnum.PCUI.getLabelKey().equals(deviceType)){
			searchModel.setDeviceType(DeviceTypeEnum.PCUI.getValue());
		}
		if(DeviceTypeEnum.APP.getLabelKey().equals(deviceType)){
			searchModel.setDeviceType(DeviceTypeEnum.APP.getValue());
		}
		if(DeviceTypeEnum.MOBILE.getLabelKey().equals(deviceType)){
			searchModel.setDeviceType(DeviceTypeEnum.MOBILE.getValue());
		}*/
		RpcResult rpcResult = RpcUtils.post(Constants.findCustomerQualityList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<CustomerQualityVO> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<CustomerQualityVO>>() {});	

		return rowsList;	
	}

}