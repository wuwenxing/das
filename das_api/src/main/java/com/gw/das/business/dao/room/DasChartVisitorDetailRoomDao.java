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
import com.gw.das.business.dao.base.ImpalaDao;
import com.gw.das.business.dao.room.entity.DasChartRoomSearchModel;

/**
 * 访客明细
 * 
 * @author wayne
 *
 */
@Repository
public class DasChartVisitorDetailRoomDao extends ImpalaDao {
	
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
		DasChartRoomSearchModel model = (DasChartRoomSearchModel) t;
		sql.append("select a.rowkey, " + "a.roomid, a.roomname, a.nickname, a.startTime, a.endtime, a.timelength, "
				+ "a.loginCount, a.userip, a.iphome, a.useequipment, "
				+ "a.publicspeakcount, a.privatespeakcount, a.platformtype, a.businessplatform, "
				+ "a.touristid, b.userid, b.usertype, b.usersource, b.usertel, b.username "
				+ "from das_chart_room_detail a " + "left join das_chart_room_register_user b on a.prowkey = b.rowkey "
				+ "where a.businessPlatform = ? ");
		paramList.add(model.getBusinessPlatform() + "");
		if (StringUtils.isNotBlank(model.getRowKey())) {
			sql.append(" and a.prowkey = ? ");
			paramList.add(model.getRowKey());
		}
		if (StringUtils.isNotBlank(model.getNickName())) {
			sql.append(" and a.nickname like ? ");
			paramList.add("%" + model.getNickName() + "%");
		}
		if (StringUtils.isNotBlank(model.getUserName())) {
			sql.append(" and b.userName like ? ");
			paramList.add("%" + model.getUserName() + "%");
		}
		if (StringUtils.isNotBlank(model.getUserTel())) {
			sql.append(" and b.userTel like ? ");
			paramList.add("%" + model.getUserTel() + "%");
		}
		if (StringUtils.isNotBlank(model.getRoomName())) {
			sql.append(" and a.roomName like ? ");
			paramList.add("%" + model.getRoomName() + "%");
		}
		if (StringUtils.isNotBlank(model.getUserType())) {
			// 如果查询为游客的用户，为空的数据也需要查询出来
			if ("1".equals(model.getUserType())) {
				sql.append(" and (b.userType = ? or b.userType is null)");
				paramList.add(model.getUserType());
			} else {
				sql.append(" and b.userType = ? ");
				paramList.add(model.getUserType());
			}
		}
		if (StringUtils.isNotBlank(model.getUserSource())) {
			sql.append(" and b.userSource = ? ");
			paramList.add(model.getUserSource());
		}
		if (StringUtils.isNotBlank(model.getPlatformType())) {
			sql.append(" and a.platformType = ? ");
			paramList.add(model.getPlatformType());
		}
		if (StringUtils.isNotBlank(model.getTimeLengthStart())) {
			sql.append(" and a.timeLength >= ? ");
			paramList.add(model.getTimeLengthStart());
		}
		if (StringUtils.isNotBlank(model.getTimeLengthEnd())) {
			sql.append(" and a.timeLength <= ? ");
			paramList.add(model.getTimeLengthEnd());
		}
		if (StringUtils.isNotBlank(model.getUpStartTime())) {
			sql.append(" and a.startTime >= ? ");
			sql.append(" and partitionField >= ? ");
			paramList.add(model.getUpStartTime());
			paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(model.getUpStartTime())));
		}
		if (StringUtils.isNotBlank(model.getUpEndTime())) {
			sql.append(" and a.startTime <= ? ");
			sql.append(" and partitionField <= ? ");
			paramList.add(model.getUpEndTime());
			paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(model.getUpEndTime())));
		}

		if (StringUtils.isNotBlank(model.getDownStartTime())) {
			sql.append(" and a.endTime >= ? ");
			paramList.add(model.getDownStartTime());
		}
		if (StringUtils.isNotBlank(model.getDownEndTime())) {
			sql.append(" and a.endTime <= ? ");
			paramList.add(model.getDownEndTime());
		}
	}

}
