package com.gw.das.business.dao.room;

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
import com.gw.das.business.dao.base.ReportDao;
import com.gw.das.business.dao.room.entity.DasRoomLoginStatisticsSearchBean;

/**
 * 访客汇总
 * 
 * @author wayne
 *
 */
@Repository
public class DasRoomLoginStatisticsDao extends ReportDao {

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
		DasRoomLoginStatisticsSearchBean model = (DasRoomLoginStatisticsSearchBean) t;
		sql.append("select row_number() over() as rowkey, t.datetime, t.account, t.usertype, t.open_account_time"
				+ ", t.active_time, t.latest_trade_time, t.live_room_count, t.live_room_count_min"
				+ ", t.vip_room_count, t.vip_room_count_min, t.businessplatform "
				+ "from das_room_login_statistics_hx_d t where t.businessplatform = ? ");
		paramList.add(model.getBusinessPlatform() + "");
		if (null != model) {
			if (StringUtils.isNotBlank(model.getAccount())) {
				sql.append(" and t.account like ? ");
				paramList.add("%" + model.getAccount() + "%");
			}
			if (StringUtils.isNotBlank(model.getUserType())) {
				sql.append(" and t.usertype = ? ");
				paramList.add(model.getUserType());
			}
			if (StringUtils.isNotBlank(model.getStartTime())) {
				sql.append(" and t.datetime >= ? ");
				paramList.add(model.getStartTime());
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				sql.append(" and t.datetime <= ? ");
				paramList.add(model.getEndTime());
			}
			if (StringUtils.isNotBlank(model.getOpenAccountTimeStart())) {
				sql.append(" and t.open_account_time >= ? ");
				paramList.add(model.getOpenAccountTimeStart() + DateUtil.startTime);
			}
			if (StringUtils.isNotBlank(model.getOpenAccountTimeEnd())) {
				sql.append(" and t.open_account_time <= ? ");
				paramList.add(model.getOpenAccountTimeEnd() + DateUtil.endTime);
			}
			if (StringUtils.isNotBlank(model.getActiveTimeStart())) {
				sql.append(" and t.active_time >= ? ");
				paramList.add(model.getActiveTimeStart() + DateUtil.startTime);
			}
			if (StringUtils.isNotBlank(model.getActiveTimeEnd())) {
				sql.append(" and t.active_time <= ? ");
				paramList.add(model.getActiveTimeEnd() + DateUtil.endTime);
			}
		}
	}

}
