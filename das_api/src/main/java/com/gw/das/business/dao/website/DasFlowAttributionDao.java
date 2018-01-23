package com.gw.das.business.dao.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.SqlUtil;
import com.gw.das.business.dao.base.BaseModel;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.WebSiteReportDao;

/**
 * 归因报表
 * 
 * @author wayne
 *
 */
@Repository
public class DasFlowAttributionDao extends WebSiteReportDao {
	
	/**	
	 * 不分页查询
	 * 
	 * @throws Exception
	 */
	public <T extends BaseSearchModel, E extends BaseModel> List<E> queryForList(T searchModel, E e) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, searchModel, paramList);
		sql.append(SqlUtil.getOrderField(searchModel.getSort(), searchModel.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<E> detailList = (List<E>) OrmUtil.reflectList(e.getClass(), list);
		return detailList;
	}

	/**
	 * 分页查询
	 * 
	 * @throws Exception
	 */
	public <T extends BaseSearchModel, E extends BaseModel> PageGrid<T> queryForPage(PageGrid<T> pg, E e)
			throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		BaseSearchModel searchModel = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, searchModel, paramList);
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<E> detailList = (List<E>) OrmUtil.reflectList(e.getClass(), list);
		pg.setRows(detailList);
		return pg;
	}
	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList) {
		DasChartWebSiteSearchModel model = (DasChartWebSiteSearchModel) t;
		sql.append("select dataTime,utmcsr,utmcmd,demoCount,realCount ,platformtype from das_flow_attribution where 1 = 1 ");
		if (null != model) {
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				sql.append(" and utmcsr like ? ");
				paramList.add("%" + model.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				sql.append(" and utmcmd like ? ");
				paramList.add("%" + model.getUtmcmd() + "%");
			}
			if (null != model.getDataTimeStart()) {
				sql.append(" and dataTime >= ? ");
				paramList.add(DateUtil.formatDateToString(model.getDataTimeStart()));
			}
			if (null != model.getDataTimeEnd()) {
				sql.append(" and dataTime <= ? ");
				paramList.add(DateUtil.formatDateToString(model.getDataTimeEnd()));
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType());
			}
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform() + "");
		}
	}

}
