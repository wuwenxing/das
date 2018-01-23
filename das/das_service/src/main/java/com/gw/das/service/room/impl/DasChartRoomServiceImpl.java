package com.gw.das.service.room.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
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
import com.gw.das.common.enums.ClientEnum;
import com.gw.das.common.enums.DeviceTypeEnum;
import com.gw.das.common.netty.RpcResult;
import com.gw.das.common.netty.RpcUtils;
import com.gw.das.common.utils.BeanToMapUtil;
import com.gw.das.common.utils.JacksonUtil;
import com.gw.das.dao.room.bean.DasChartFlowDetail;
import com.gw.das.dao.room.bean.DasChartFlowDetailSearchBean;
import com.gw.das.dao.room.bean.DasChartFlowDetailUrl;
import com.gw.das.dao.room.bean.DasChartFlowDetailUrlSearchBean;
import com.gw.das.dao.room.bean.DasChartFlowStatistics;
import com.gw.das.dao.room.bean.DasChartFlowStatisticsAverage;
import com.gw.das.dao.room.bean.DasChartFlowStatisticsSearchBean;
import com.gw.das.dao.room.bean.DasChartFlowStatisticsTreeMedia;
import com.gw.das.dao.room.bean.DasChartRoomDetail;
import com.gw.das.dao.room.bean.DasChartRoomDetailSum;
import com.gw.das.dao.room.bean.DasChartRoomOnlineHours;
import com.gw.das.dao.room.bean.DasChartRoomRegTouristUserStatistics;
import com.gw.das.dao.room.bean.DasChartRoomSearchModel;
import com.gw.das.dao.room.bean.DasChartRoomSpeakStatistics;
import com.gw.das.dao.room.bean.DasChartRoomStatistics;
import com.gw.das.dao.room.bean.DasChartRoomUserTypeStatistics;
import com.gw.das.dao.room.bean.DasRoomLoginStatistics;
import com.gw.das.dao.room.bean.DasRoomLoginStatisticsSearchBean;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.room.DasChartRoomService;

@Service
public class DasChartRoomServiceImpl extends BaseService implements DasChartRoomService {

	/**
	 * 恒信直播间登陆统计-分页查询
	 */
	public PageGrid<DasRoomLoginStatisticsSearchBean> findLoginStatisticsPage(PageGrid<DasRoomLoginStatisticsSearchBean> pageGrid) throws Exception {
		// 设置查询条件
		DasRoomLoginStatisticsSearchBean detail = pageGrid.getSearchModel();
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.roomLoginStatisticsPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasRoomLoginStatistics> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasRoomLoginStatistics>>() {
				});
		PageGrid<DasRoomLoginStatisticsSearchBean> page = new PageGrid<DasRoomLoginStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}

	/**
	 * 恒信直播间登陆统计-不分页查询
	 */
	public List<DasRoomLoginStatistics> findLoginStatisticsList(DasRoomLoginStatisticsSearchBean searchModel) throws Exception{
		RpcResult rpcResult = RpcUtils.post(Constants.roomLoginStatisticsList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasRoomLoginStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasRoomLoginStatistics>>() {
		});
		return rowsList;
	}
	
	/**
	 * 直播间-访客列表查询
	 */
	public PageGrid<DasChartRoomSearchModel> findVisitorPage(PageGrid<DasChartRoomSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomVisitorPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomDetailSum> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomDetailSum>>() {
				});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}

	/**
	 * 直播间-访客列表查询-不分页查询
	 */
	public List<DasChartRoomDetailSum> findVisitorList(DasChartRoomSearchModel searchModel) throws Exception{
		// 设置查询条件
		if (searchModel != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
				searchModel.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
				searchModel.setPlatformType("1");
			}
		}

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomVisitorList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomDetailSum> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomDetailSum>>() {
		});
		return rowsList;
	}
	
	/**
	 * 直播间-访客明细列表查询
	 */
	public PageGrid<DasChartRoomSearchModel> findVisitorDetailPage(PageGrid<DasChartRoomSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomVisitorDetailPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomDetail>>() {
		});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}

	/**
	 * 直播间-访客明细列表查询-不分页查询
	 */
	public List<DasChartRoomDetail> findVisitorDetailList(DasChartRoomSearchModel searchModel) throws Exception{
		// 设置查询条件
		if (searchModel != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
				searchModel.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
				searchModel.setPlatformType("1");
			}
		}

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomVisitorDetailList, BeanToMapUtil.toMap(searchModel),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomDetail>>() {
		});
		return rowsList;
	}
	
	/**
	 * 直播间-行为统计-时\日\周\月统计-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsPage(PageGrid<DasChartRoomSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			detail.setSort(pageGrid.getSort());
			detail.setOrder(pageGrid.getOrder());
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomStatistics> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomStatistics>>() {
				});

		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 直播间-行为统计-时\日\周\月统计-不分页查询
	 */
	public List<DasChartRoomStatistics> findStatisticsList(DasChartRoomSearchModel searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		searchModel.setSort("dateTime");
		searchModel.setOrder("desc");
		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomStatistics>>() {
		});
		return rowsList;		
	}

	/**
	 * 直播间-发言天/次数分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsSpeakPage(PageGrid<DasChartRoomSearchModel> pageGrid,
			String type) throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			detail.setSort(pageGrid.getSort());
			detail.setOrder(pageGrid.getOrder());
			detail.setSpeakTypeStatistics(type);
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		String postUrl = "";
		if ("1".equals(type)) {
			postUrl = Constants.dasChartRoomStatisticsSpeakdays;
		} else if ("2".equals(type)) {
			postUrl = Constants.dasChartRoomStatisticsSpeakcounts;
		}
		RpcResult rpcResult = RpcUtils.post(postUrl, BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomSpeakStatistics> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomSpeakStatistics>>() {
				});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		page.setTotal(Integer.parseInt(total));
		return page;
	}
	
	/**
	 * 直播间-发言天/次数分析-不分页查询
	 */
	public List<DasChartRoomSpeakStatistics> findStatisticsSpeakList(DasChartRoomSearchModel searchModel) throws Exception{
		// 设置查询条件
		String postUrl = "";
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		searchModel.setSort("dateTime");
		searchModel.setOrder("desc");
		if ("1".equals(searchModel.getSpeakTypeStatistics())) {
			postUrl = Constants.dasChartRoomStatisticsSpeakList;
		} else if ("2".equals(searchModel.getSpeakTypeStatistics())) {
			postUrl = Constants.dasChartRoomStatisticsSpeakcountsList;
		}
		RpcResult rpcResult = RpcUtils.post(postUrl, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomSpeakStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomSpeakStatistics>>() {
		});
		return rowsList;		
	}

	/**
	 * 直播间-登录天数分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsLogindaysPage(PageGrid<DasChartRoomSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			detail.setSort(pageGrid.getSort());
			detail.setOrder(pageGrid.getOrder());
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsLogindaysPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomSpeakStatistics> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomSpeakStatistics>>() {
				});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		page.setTotal(Integer.parseInt(total));
		return page;
	}
	
	/**
	 * 直播间-发言天/次数分析-不分页查询
	 */
	public List<DasChartRoomSpeakStatistics> findStatisticsLogindaysList(DasChartRoomSearchModel searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		searchModel.setSort("dateTime");
		searchModel.setOrder("desc");
		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsLogindaysList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomSpeakStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomSpeakStatistics>>() {
		});
		return rowsList;		
	}

	/**
	 * 直播间-用户在线时长分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsOnlineHoursPage(PageGrid<DasChartRoomSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			detail.setSort(pageGrid.getSort());
			detail.setOrder(pageGrid.getOrder());
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsOnlineHoursPage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomOnlineHours> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomOnlineHours>>() {
				});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 直播间-用户在线时长分析-不分页查询
	 */
	public List<DasChartRoomOnlineHours> findStatisticsOnlineHoursKList(DasChartRoomSearchModel searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		searchModel.setSort("dateTime");
		searchModel.setOrder("desc");
		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsOnlineHoursList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomOnlineHours> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomOnlineHours>>() {
		});
		return rowsList;		
	}

	/**
	 * 根据用户类型进行登录\访问\发言统计-跳转管理页面 对应type参数为1\2\3
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsByUserTypePage(PageGrid<DasChartRoomSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			detail.setSort(pageGrid.getSort());
			detail.setOrder(pageGrid.getOrder());
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsUserTypePage, BeanToMapUtil.toMap(detail),
				UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomUserTypeStatistics> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomUserTypeStatistics>>() {
				});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 根据用户类型进行登录\访问\发言统计-跳转管理页面 对应type参数为1\2\3
	 */
	public List<DasChartRoomUserTypeStatistics> findStatisticsByUserTypeList(DasChartRoomSearchModel searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		searchModel.setSort("dateTime");
		searchModel.setOrder("desc");
		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsUserTypeList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomUserTypeStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomUserTypeStatistics>>() {
		});
		return rowsList;		
	}

	/**
	 * 直播间-注册用户列表分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsRegTouristUserPage(
			PageGrid<DasChartRoomSearchModel> pageGrid) throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			detail.setSort(pageGrid.getSort());
			detail.setOrder(pageGrid.getOrder());
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsRegTouristUserPage,
				BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomRegTouristUserStatistics> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomRegTouristUserStatistics>>() {
				});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 直播间-注册用户记录分析-不分页查询
	 */
	public List<DasChartRoomRegTouristUserStatistics> findStatisticsRegTouristUserList(DasChartRoomSearchModel searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		searchModel.setSort("dateTime");
		searchModel.setOrder("desc");
		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsRegTouristUserList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomRegTouristUserStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomRegTouristUserStatistics>>() {
		});
		return rowsList;		
	}

	/**
	 * 直播间-访问记录分析-分页查询
	 */
	public PageGrid<DasChartRoomSearchModel> findStatisticsVisitcountsPage(PageGrid<DasChartRoomSearchModel> pageGrid)
			throws Exception {
		// 设置查询条件
		DasChartRoomSearchModel detail = pageGrid.getSearchModel();
		if (detail != null) {
			// 访问客户端
			if (ClientEnum.pc.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("0");
			} else if (ClientEnum.mobile.getLabelKey().equals(detail.getPlatformType())) {
				detail.setPlatformType("1");
			}
			detail.setSort(pageGrid.getSort());
			detail.setOrder(pageGrid.getOrder());
		}
		detail.setPageNumber(pageGrid.getPageNumber());
		detail.setPageSize(pageGrid.getPageSize());

		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsVisitcountsPage,
				BeanToMapUtil.toMap(detail), UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");

		List<DasChartRoomSpeakStatistics> rowsList = JacksonUtil.readValue(rows,
				new TypeReference<List<DasChartRoomSpeakStatistics>>() {
				});
		PageGrid<DasChartRoomSearchModel> page = new PageGrid<DasChartRoomSearchModel>();
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		page.setTotal(Integer.parseInt(total));
		return page;
	}
	
	/**
	 * 直播间-访问记录分析-不分页查询
	 */
	public List<DasChartRoomSpeakStatistics> findStatisticsVisitcountsList(DasChartRoomSearchModel searchModel) throws Exception{
		// 访问客户端
		if (ClientEnum.pc.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("0");
		} else if (ClientEnum.mobile.getLabelKey().equals(searchModel.getPlatformType())) {
			searchModel.setPlatformType("1");
		}
		searchModel.setSort("dateTime");
		searchModel.setOrder("desc");
		RpcResult rpcResult = RpcUtils.post(Constants.dasChartRoomStatisticsVisitcountsList, BeanToMapUtil.toMap(searchModel),UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {
		});
		String rows = resultMap.get("rows");
		List<DasChartRoomSpeakStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartRoomSpeakStatistics>>() {
		});
		return rowsList;		
	}
	
	/**
	 * 直播间开户统计列表-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findBehaviorCountPageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception{
		DasChartFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFindBehaviorCountPageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();

		List<DasChartFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowStatistics>>() {});
		PageGrid<DasChartFlowStatisticsSearchBean> page = new PageGrid<DasChartFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 直播间开户统计列表
	 */
	public List<DasChartFlowStatistics> findBehaviorCountList(DasChartFlowStatisticsSearchBean bean) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		RpcResult rpcResult = RpcUtils.post(Constants.DasFindBehaviorCountList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasChartFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowStatistics>>() {});
		return rowsList;
	}
	
	private void getArgs(DasChartFlowStatisticsSearchBean bean, Map<String, String> parameters){
		if (bean != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(bean.getUtmcsr())) {
				parameters.put("utmcsr", bean.getUtmcsr());
			}
			if (StringUtils.isNotBlank(bean.getUtmcmd())) {
				parameters.put("utmcmd", bean.getUtmcmd());
			}
			if (StringUtils.isNotBlank(bean.getUtmccn())) {
				parameters.put("utmccn", bean.getUtmccn());
			}
			if (StringUtils.isNotBlank(bean.getUtmcct())) {
				parameters.put("utmcct", bean.getUtmcct());
			}
			if (StringUtils.isNotBlank(bean.getUtmctr())) {
				parameters.put("utmctr", bean.getUtmctr());
			}
			if (StringUtils.isNotBlank(bean.getStartTime())) {
				parameters.put("startTime", bean.getStartTime());
			}
			if (StringUtils.isNotBlank(bean.getEndTime())) {
				parameters.put("endTime", bean.getEndTime());
			}
			if (StringUtils.isNotBlank(bean.getDevicetype())) {
				// 访问客户端
				if (DeviceTypeEnum.PC.getLabelKey().equals(bean.getDevicetype())) {
					parameters.put("devicetype", "0");
				} else if (DeviceTypeEnum.ANDROID.getLabelKey().equals(bean.getDevicetype())) {
					parameters.put("devicetype", "1");
				}else if (DeviceTypeEnum.IOS.getLabelKey().equals(bean.getDevicetype())) {
					parameters.put("devicetype", "2");
				}
			}
			parameters.put("dataTime", bean.getDataTime());
			parameters.put("behaviorType", bean.getBehaviorType());
			parameters.put("searchType", bean.getSearchType());
			parameters.put("sortName", bean.getSortName());
			parameters.put("sortDirection", bean.getSortDirection());
			parameters.put("utmcsrChecked", bean.isUtmcsrChecked() + "");
			parameters.put("utmcmdChecked", bean.isUtmcmdChecked() + "");
			parameters.put("utmccnChecked", bean.isUtmccnChecked() + "");
			parameters.put("utmcctChecked", bean.isUtmcctChecked() + "");
			parameters.put("utmctrChecked", bean.isUtmctrChecked() + "");
			if(StringUtils.isNotBlank(bean.getUtmcmdList())){
				parameters.put("utmcmdList", bean.getUtmcmdList());
			}
			if(StringUtils.isNotBlank(bean.getUtmcsrList())){
				parameters.put("utmcsrList", bean.getUtmcsrList());
			}
			if(StringUtils.isNotBlank(bean.getStartTimeCompare())){
				parameters.put("startTimeCompare", bean.getStartTimeCompare());
			}
			if(StringUtils.isNotBlank(bean.getEndTimeCompare())){
				parameters.put("endTimeCompare", bean.getEndTimeCompare());
			}
			
		}
	}
	
	/**
	 * 直播间来源媒介统计
	 */
	public List<DasChartFlowStatistics> findRoomSourceCountList(DasChartFlowStatisticsSearchBean bean) throws Exception{
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		RpcResult rpcResult = RpcUtils.post(Constants.DasFindRoomSourceCountList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasChartFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowStatistics>>() {});
		return rowsList;
	}

	/**
	 * 直播间来源媒介统计-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findRoomSourceCountPageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception{
		DasChartFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFindRoomSourceCountPageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();		

		List<DasChartFlowStatistics> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowStatistics>>() {});
		PageGrid<DasChartFlowStatisticsSearchBean> page = new PageGrid<DasChartFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 直播间来源媒介统计-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findRoomSourceCountTreePageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception{
		DasChartFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasFindRoomSourceCountTreePageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();
		List<DasChartFlowStatisticsTreeMedia> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowStatisticsTreeMedia>>() {});
		
		String contrastFront = resultMap.get("contrastFront");
		String contrastBack = resultMap.get("contrastBack");
		List<DasChartFlowStatisticsTreeMedia> contrastFrontList = null;
		List<DasChartFlowStatisticsTreeMedia> contrastBackList = null;
		if(StringUtils.isNotBlank(contrastFront)){
			 contrastFrontList = JacksonUtil.readValue(contrastFront, new TypeReference<List<DasChartFlowStatisticsTreeMedia>>() {});
		}
		if(StringUtils.isNotBlank(contrastFront)){
			contrastBackList = JacksonUtil.readValue(contrastBack, new TypeReference<List<DasChartFlowStatisticsTreeMedia>>() {});
		}		
		
		NumberFormat df = NumberFormat.getNumberInstance();
        df.setMaximumFractionDigits(6);
		List<DasChartFlowStatisticsTreeMedia> resultList = new ArrayList<DasChartFlowStatisticsTreeMedia>();
		int k = 0;
		for (int i = 0; i < rowsList.size(); i++)
		{
			k++;
			DasChartFlowStatisticsTreeMedia das = rowsList.get(i);
			DasChartFlowStatisticsTreeMedia dasBean = new DasChartFlowStatisticsTreeMedia();
			dasBean.setId(k);
			k++;
			dasBean.setUtmcsrAndUtmcmd(das.getUtmcsr()+"/"+das.getUtmcmd());
			dasBean.setState("open");
			dasBean.setRowKey(i+"");
			dasBean.setDevicetype("-");			
			List<DasChartFlowStatisticsTreeMedia> childrenList = new ArrayList<DasChartFlowStatisticsTreeMedia>();
			////////////////////
			double visitCount = 0,visitCountCompare = 0;
			double advisoryCountQQ = 0,advisoryCountQQCompare = 0;
			double advisoryCountLIVE800 = 0,advisoryCountLIVE800Compare = 0;
			double demoCount = 0,demoCountCompare = 0;
			double realCount = 0,realCountCompare = 0;
			double depositCount = 0,depositCountCompare = 0;
			double devicecount = 0,devicecountCompare = 0;
			DasChartFlowStatisticsTreeMedia dasFlowStatisticsTotal = new DasChartFlowStatisticsTreeMedia();
			DasChartFlowStatisticsTreeMedia dasFlowStatisticsBefore = null;
			DasChartFlowStatisticsTreeMedia dasFlowStatisticsAfter = null;
			if((null != contrastFrontList && contrastFrontList.size() >0)){	
				boolean contrastFrontFlag = false;
				for (DasChartFlowStatisticsTreeMedia dasFlowStatistics : contrastFrontList)
				{
					if(das.getUtmcmd().equals(dasFlowStatistics.getUtmcmd()) && das.getUtmcsr().equals(dasFlowStatistics.getUtmcsr())){
						contrastFrontFlag = true;
						visitCount += Integer.valueOf(dasFlowStatistics.getVisitCount());
						advisoryCountQQ += Integer.valueOf(dasFlowStatistics.getAdvisoryCountQQ());
						advisoryCountLIVE800 += Integer.valueOf(dasFlowStatistics.getAdvisoryCountLIVE800());
						demoCount += Integer.valueOf(dasFlowStatistics.getDemoCount());
						realCount += Integer.valueOf(dasFlowStatistics.getRealCount());
						depositCount += Integer.valueOf(dasFlowStatistics.getDepositCount());
						devicecount += Integer.valueOf(dasFlowStatistics.getDevicecount());
					}					
				}
				if(contrastFrontFlag){
					dasFlowStatisticsBefore = new DasChartFlowStatisticsTreeMedia();
					dasFlowStatisticsBefore.setId(Integer.valueOf(k));
					k++;
					//dasFlowStatisticsBefore.set_parentId(k);
					dasFlowStatisticsBefore.setUtmcsrAndUtmcmd(bean.getStartTime()+"---"+bean.getEndTime());
					dasFlowStatisticsBefore.setVisitCount(String.valueOf(visitCount));
					dasFlowStatisticsBefore.setAdvisoryCountQQ(String.valueOf(advisoryCountQQ));
					dasFlowStatisticsBefore.setAdvisoryCountLIVE800(String.valueOf(advisoryCountLIVE800));
					dasFlowStatisticsBefore.setDemoCount(String.valueOf(demoCount));
					dasFlowStatisticsBefore.setRealCount(String.valueOf(realCount));
					dasFlowStatisticsBefore.setDepositCount(String.valueOf(depositCount));
					dasFlowStatisticsBefore.setDevicecount(String.valueOf(devicecount));
					childrenList.add(dasFlowStatisticsBefore);
				}
			}
			if((null != contrastBackList && contrastBackList.size() >0)){	
				boolean contrastBackFlag = false;
				for (DasChartFlowStatisticsTreeMedia dasFlowStatistics : contrastBackList)
				{
					if(das.getUtmcmd().equals(dasFlowStatistics.getUtmcmd()) && das.getUtmcsr().equals(dasFlowStatistics.getUtmcsr())){
						contrastBackFlag = true;
						visitCountCompare += Integer.valueOf(dasFlowStatistics.getVisitCount());
						advisoryCountQQCompare += Integer.valueOf(dasFlowStatistics.getAdvisoryCountQQ());
						advisoryCountLIVE800Compare += Integer.valueOf(dasFlowStatistics.getAdvisoryCountLIVE800());
						demoCountCompare += Integer.valueOf(dasFlowStatistics.getDemoCount());
						realCountCompare += Integer.valueOf(dasFlowStatistics.getRealCount());
						depositCountCompare += Integer.valueOf(dasFlowStatistics.getDepositCount());
						devicecountCompare += Integer.valueOf(dasFlowStatistics.getDevicecount());
					}
				}
				if(contrastBackFlag){
					dasFlowStatisticsAfter = new DasChartFlowStatisticsTreeMedia();
					dasFlowStatisticsAfter.setId(Integer.valueOf(k));
					k++;
					//dasFlowStatisticsAfter.set_parentId(k);
					dasFlowStatisticsAfter.setUtmcsrAndUtmcmd(bean.getStartTimeCompare()+"---"+bean.getEndTimeCompare());
					dasFlowStatisticsAfter.setVisitCount(String.valueOf(visitCountCompare));
					dasFlowStatisticsAfter.setAdvisoryCountQQ(String.valueOf(advisoryCountQQCompare));
					dasFlowStatisticsAfter.setAdvisoryCountLIVE800(String.valueOf(advisoryCountLIVE800Compare));
					dasFlowStatisticsAfter.setDemoCount(String.valueOf(demoCountCompare));
					dasFlowStatisticsAfter.setRealCount(String.valueOf(realCountCompare));
					dasFlowStatisticsAfter.setDepositCount(String.valueOf(depositCountCompare));
					dasFlowStatisticsAfter.setDevicecount(String.valueOf(devicecountCompare));
					childrenList.add(dasFlowStatisticsAfter);
				}
			}
			
			if(null != dasFlowStatisticsBefore && null != dasFlowStatisticsAfter){
				dasFlowStatisticsTotal.setUtmcsrAndUtmcmd("变动百分比");
				dasFlowStatisticsTotal.setId(Integer.valueOf(k));
				k++;
				//dasFlowStatisticsTotal.set_parentId(k);
				dasFlowStatisticsTotal.setVisitCount(visitCountCompare == 0?"-":df.format((visitCount - visitCountCompare)/visitCountCompare * 100) + "%");
				dasFlowStatisticsTotal.setAdvisoryCountQQ(advisoryCountQQCompare == 0?"-":df.format((advisoryCountQQ - advisoryCountQQCompare)/advisoryCountQQCompare  * 100)+"%" );
				dasFlowStatisticsTotal.setAdvisoryCountLIVE800(advisoryCountLIVE800Compare == 0?"-":df.format((advisoryCountLIVE800 - advisoryCountLIVE800Compare)/advisoryCountLIVE800Compare  * 100) + "%" );
				dasFlowStatisticsTotal.setDemoCount(demoCountCompare == 0?"-":df.format((demoCount - demoCountCompare)/demoCountCompare  * 100) +"%" );
				dasFlowStatisticsTotal.setRealCount(realCountCompare == 0 ?"-":df.format((realCount - realCountCompare)/realCountCompare  * 100) +"%" );
				dasFlowStatisticsTotal.setDepositCount(depositCountCompare == 0?"-":df.format((depositCount - depositCountCompare)/depositCountCompare  * 100) +"%" );
				dasFlowStatisticsTotal.setDevicecount(devicecountCompare == 0?"-":df.format((devicecount - devicecountCompare)/devicecountCompare  * 100) +"%" );
			}else{
				dasFlowStatisticsTotal.setId(Integer.valueOf(k));
				k++;
				//dasFlowStatisticsTotal.set_parentId(k);
				dasFlowStatisticsTotal.setUtmcsrAndUtmcmd("变动百分比");
				dasFlowStatisticsTotal.setVisitCount("-");
				dasFlowStatisticsTotal.setAdvisoryCountQQ("-");
				dasFlowStatisticsTotal.setAdvisoryCountLIVE800("-");
				dasFlowStatisticsTotal.setDemoCount("-");
				dasFlowStatisticsTotal.setRealCount("-");
				dasFlowStatisticsTotal.setDepositCount("-");
				dasFlowStatisticsTotal.setDevicecount("-");
			}
			childrenList.add(dasFlowStatisticsTotal);
			///////////////////
			dasBean.setChildren(childrenList);	
			resultList.add(dasBean);
		}
						
		PageGrid<DasChartFlowStatisticsSearchBean> page = new PageGrid<DasChartFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(resultList);
		return page;
	}
	
	
	/**
	 * 流量统计列表
	 */
	public List<DasChartFlowStatisticsAverage> findAverageList(DasChartFlowStatisticsSearchBean bean) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		RpcResult rpcResult = RpcUtils.post(Constants.DasRoomStatisticsAverageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasChartFlowStatisticsAverage> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowStatisticsAverage>>() {});
		return rowsList;
	}
	
	/**
	 * 流量统计列表-分页查询
	 */
	public PageGrid<DasChartFlowStatisticsSearchBean> findAveragePageList(PageGrid<DasChartFlowStatisticsSearchBean> pageGrid) throws Exception {
		DasChartFlowStatisticsSearchBean bean = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		this.getArgs(bean, parameters);
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");
		parameters.put("sortName", pageGrid.getSort());
		parameters.put("sortDirection", pageGrid.getOrder());

		RpcResult rpcResult = RpcUtils.post(Constants.DasRoomStatisticsAveragePage, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();

		List<DasChartFlowStatisticsAverage> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowStatisticsAverage>>() {});
		PageGrid<DasChartFlowStatisticsSearchBean> page = new PageGrid<DasChartFlowStatisticsSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 用户行为列表分页查询
	 */
	public PageGrid<DasChartFlowDetailSearchBean> findBehaviorPageList(PageGrid<DasChartFlowDetailSearchBean> pageGrid) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		// 设置查询条件
		DasChartFlowDetailSearchBean detail = pageGrid.getSearchModel();
		if(detail != null){
			if(StringUtils.isNotBlank(detail.getBehaviorType())){
				// 属性[访问\咨询\模拟\真实\入金]
				if(BehaviorTypeEnum.visit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "1");
				}else if(BehaviorTypeEnum.live.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "2");
				}else if(BehaviorTypeEnum.demo.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "3");
				}else if(BehaviorTypeEnum.real.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "4");
				}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "5");
				}
			}
			parameters.put("ip", detail.getIp());
			parameters.put("url", detail.getUrl());
			parameters.put("userId", detail.getUserId());
			parameters.put("utmcsr", detail.getUtmcsr());// 来源
			parameters.put("utmcmd", detail.getUtmcmd());// 媒介
			parameters.put("utmccn", detail.getUtmccn());// 广告系列
			parameters.put("utmcct", detail.getUtmcct());// 广告组
			parameters.put("utmctr", detail.getUtmctr());// 关键词
			parameters.put("startTime", detail.getStartTime()); // 行为开始时间
			parameters.put("endTime", detail.getEndTime()); // 行为结束时间
			// 访问客户端
			if (DeviceTypeEnum.PC.getLabelKey().equals(detail.getDevicetype())) {
				parameters.put("devicetype", "0");
			} else if (DeviceTypeEnum.ANDROID.getLabelKey().equals(detail.getDevicetype())) {
				parameters.put("devicetype", "1");
			}else if (DeviceTypeEnum.IOS.getLabelKey().equals(detail.getDevicetype())) {
				parameters.put("devicetype", "2");
			}
		}
		if(StringUtils.isNotBlank(detail.getSort()) && StringUtils.isNotBlank(detail.getOrder())){
			parameters.put("sort", pageGrid.getSort());
			parameters.put("order", pageGrid.getOrder());
		}
		parameters.put("pageNumber", pageGrid.getPageNumber()+"");
		parameters.put("pageSize", pageGrid.getPageSize()+"");
		
		RpcResult rpcResult = RpcUtils.post(Constants.DasFindBehaviorESPageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total");
		
		List<DasChartFlowDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowDetail>>(){});
		PageGrid<DasChartFlowDetailSearchBean> page = new PageGrid<DasChartFlowDetailSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	
	/**
	 * 用户行为列表不分页查询
	 */
	public List<DasChartFlowDetail> findBehaviorList(DasChartFlowDetailSearchBean detail) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		// 设置查询条件
		if(detail != null){
			if(StringUtils.isNotBlank(detail.getBehaviorType())){
				// 属性[访问\咨询\模拟\真实\入金]
				if(BehaviorTypeEnum.visit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "1");
				}else if(BehaviorTypeEnum.live.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "2");
				}else if(BehaviorTypeEnum.demo.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "3");
				}else if(BehaviorTypeEnum.real.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "4");
				}else if(BehaviorTypeEnum.depesit.getLabelKey().equals(detail.getBehaviorType())){
					parameters.put("behaviorType", "5");
				}
			}
			parameters.put("ip", detail.getIp());
			parameters.put("url", detail.getUrl());
			parameters.put("userId", detail.getUserId());
			parameters.put("utmcsr", detail.getUtmcsr());// 来源
			parameters.put("utmcmd", detail.getUtmcmd());// 媒介
			parameters.put("utmccn", detail.getUtmccn());// 广告系列
			parameters.put("utmcct", detail.getUtmcct());// 广告组
			parameters.put("utmctr", detail.getUtmctr());// 关键词
			parameters.put("startTime", detail.getStartTime()); // 行为开始时间
			parameters.put("endTime", detail.getEndTime()); // 行为结束时间
			// 访问客户端
			if (DeviceTypeEnum.PC.getLabelKey().equals(detail.getDevicetype())) {
				parameters.put("devicetype", "0");
			} else if (DeviceTypeEnum.ANDROID.getLabelKey().equals(detail.getDevicetype())) {
				parameters.put("devicetype", "1");
			}else if (DeviceTypeEnum.IOS.getLabelKey().equals(detail.getDevicetype())) {
				parameters.put("devicetype", "2");
			}
			if(StringUtils.isNotBlank(detail.getSort()) && StringUtils.isNotBlank(detail.getOrder())){
				parameters.put("sort", detail.getSort());
				parameters.put("order", detail.getOrder());
			}
		}
		
		RpcResult rpcResult = RpcUtils.post(Constants.DasFindBehaviorESList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>(){});
		String rows = resultMap.get("rows");
		List<DasChartFlowDetail> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowDetail>>(){});
		return rowsList;
	}
	
	/**
	 * 用户详细-页面浏览详细列表
	 */
	public List<DasChartFlowDetailUrl> findFlowDetailUrlList(DasChartFlowDetailUrlSearchBean model) throws Exception {
		Map<String, String> parameters = new HashMap<String, String>();
		if (model != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(model.getUserId())) {
				parameters.put("userId", model.getUserId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailId())) {
				parameters.put("flowDetailId", model.getFlowDetailId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailUrl())) {
				parameters.put("flowDetailUrl", model.getFlowDetailUrl());
			}
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				parameters.put("utmcsr", model.getUtmcsr());
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				parameters.put("utmcmd", model.getUtmcmd());
			}
			if (StringUtils.isNotBlank(model.getStartTime())) {
				parameters.put("startTime", model.getStartTime());
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				parameters.put("endTime", model.getEndTime());
			}
		}

		RpcResult rpcResult = RpcUtils.post(Constants.DasFindFlowDetailUrlList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";
		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		List<DasChartFlowDetailUrl> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowDetailUrl>>() {});
		return rowsList;
	}

	/**
	 * 用户详细-页面浏览详细列表分页
	 */
	public PageGrid<DasChartFlowDetailUrlSearchBean> findFlowDetailUrlPageList(PageGrid<DasChartFlowDetailUrlSearchBean> pageGrid) throws Exception {
		DasChartFlowDetailUrlSearchBean model = pageGrid.getSearchModel();
		Map<String, String> parameters = new HashMap<String, String>();
		if (model != null) {
			// 设置查询条件
			if (StringUtils.isNotBlank(model.getUserId())) {
				parameters.put("userId", model.getUserId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailId())) {
				parameters.put("flowDetailId", model.getFlowDetailId());
			}
			if (StringUtils.isNotBlank(model.getFlowDetailUrl())) {
				parameters.put("flowDetailUrl", model.getFlowDetailUrl());
			}
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				parameters.put("utmcsr", model.getUtmcsr());
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				parameters.put("utmcmd", model.getUtmcmd());
			}
			if (StringUtils.isNotBlank(model.getStartTime())) {
				parameters.put("startTime", model.getStartTime());
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				parameters.put("endTime", model.getEndTime());
			}
			if (StringUtils.isNotBlank(model.getDevicetype())) {
				// 访问客户端
				if (DeviceTypeEnum.PC.getLabelKey().equals(model.getDevicetype())) {
					parameters.put("devicetype", "0");
				} else if (DeviceTypeEnum.ANDROID.getLabelKey().equals(model.getDevicetype())) {
					parameters.put("devicetype", "1");
				}else if (DeviceTypeEnum.IOS.getLabelKey().equals(model.getDevicetype())) {
					parameters.put("devicetype", "2");
				}
			}
			
			parameters.put("sortName", "visitTime");
			parameters.put("sortDirection", "desc");
		}
		parameters.put("pageNumber", pageGrid.getPageNumber() + "");
		parameters.put("pageSize", pageGrid.getPageSize() + "");

		RpcResult rpcResult = RpcUtils.post(Constants.DasFindFlowDetailUrlPageList, parameters, UserContext.get().getCompanyId());
		String result = rpcResult.getResult() + "";

		Map<String, String> resultMap = JacksonUtil.readValue(result, new TypeReference<Map<String, String>>() {});
		String rows = resultMap.get("rows");
		String total = resultMap.get("total").toString();

		List<DasChartFlowDetailUrl> rowsList = JacksonUtil.readValue(rows, new TypeReference<List<DasChartFlowDetailUrl>>() {});
		PageGrid<DasChartFlowDetailUrlSearchBean> page = new PageGrid<DasChartFlowDetailUrlSearchBean>();
		page.setTotal(Integer.parseInt(total));
		page.setPageNumber(pageGrid.getPageNumber());
		page.setPageSize(pageGrid.getPageSize());
		page.setRows(rowsList);
		return page;
	}
	

}
