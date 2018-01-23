package com.gw.das.rpc.website.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.business.common.context.ClientUserContext;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.website.DasUserInfoDao;
import com.gw.das.business.dao.website.entity.DasUserInfo;
import com.gw.das.business.dao.website.entity.DasUserInfoSearchBean;
import com.gw.das.rpc.base.ManagerImpl;
import com.gw.das.rpc.website.DasUserInfoManager;

public class DasUserInfoManagerImpl extends ManagerImpl implements DasUserInfoManager {

	private static final Logger logger = LoggerFactory.getLogger(DasUserInfoManagerImpl.class);

	/**
	 * 分页查询das_user_info_base表
	 * 
	 * @param pg
	 */
	@Override
	public Map<String, String> dasUserInfoListPage(String jsonStr) throws Exception{
		try {
			DasUserInfoSearchBean model = JacksonUtil.readValue(jsonStr, DasUserInfoSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			PageGrid<DasUserInfoSearchBean> pg = new PageGrid<DasUserInfoSearchBean>();
			pg.setSearchModel(model);
			pg.setPageNumber(model.getPageNumber());
			pg.setPageSize(model.getPageSize());
			pg.setSort("createDate");
			pg.setOrder("desc");

			DasUserInfoDao dasUserInfoDao = getService(DasUserInfoDao.class);
			pg = dasUserInfoDao.dasUserInfoListPage(pg);
			List<DasUserInfo> list = pg.getRows();
			
			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("total", pg.getTotal()+"");
			resultMap.put("rows", JacksonUtil.toJSon(list));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[用户列表-查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[用户列表-查询]接口异常:" + e.getMessage());
		}
	}

	/**
	 * 根据条件刷选用户手机号及邮箱
	 * 
	 * @param DasUserInfoSearchBean
	 *            model
	 */
	@Override
	public Map<String, String> dasUserInfoScreen(String jsonStr) throws Exception{
		try {
			DasUserInfoSearchBean model = JacksonUtil.readValue(jsonStr, DasUserInfoSearchBean.class);
			model.setBusinessPlatform(ClientUserContext.get().getBusinessPlatform() + "");
			model.setSort("createDate");
			model.setOrder("desc");

			DasUserInfoDao dasUserInfoDao = getService(DasUserInfoDao.class);

			List<String> telList = dasUserInfoDao.dasUserInfoScreen(model, "tel");
			List<String> emailList = dasUserInfoDao.dasUserInfoScreen(model, "email");

			Map<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("tels", JacksonUtil.toJSon(telList));
			resultMap.put("emails", JacksonUtil.toJSon(emailList));
			return resultMap;
		} catch (Exception e) {
			logger.error("调用[筛选用户-查询]接口异常:" + e.getMessage(), e);
			throw new Exception("调用[筛选用户-查询]接口异常:" + e.getMessage());
		}
	}

}
