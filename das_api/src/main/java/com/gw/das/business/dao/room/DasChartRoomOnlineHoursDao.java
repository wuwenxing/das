package com.gw.das.business.dao.room;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.ReportDao;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.room.entity.DasChartRoomSearchModel;

/**
 * 直播间-用户在线时长统计
 */
@Repository
public class DasChartRoomOnlineHoursDao extends ReportDao {
	
	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		DasChartRoomSearchModel model = (DasChartRoomSearchModel) t;
		sql.append(" SELECT row_number() over() as rowkey,");
		sql.append("a.datetime,");
		if(null != model && model.isCourseNameChecked()){
			sql.append("	a .courseName,");
		}
		if(null != model && model.isTeacherNameChecked()){
			sql.append("	a .teachername,");
		}
		if(null != model && model.isRoomNameChecked()){
			sql.append("	a .roomname,");
		}
		sql.append("	SUM (a .seconds1) AS seconds1,");
		sql.append("	SUM (a .seconds2) AS seconds2,");
		sql.append("	SUM (a .seconds3) AS seconds3,");
		sql.append("	SUM (a .seconds4) AS seconds4,");
		sql.append("	SUM (a .seconds5) AS seconds5,");
		sql.append("	SUM (a .seconds6) AS seconds6,");
		sql.append("a.businessplatform");
		sql.append(" FROM das_chart_room_online_hours_statistics a ");
		sql.append(" WHERE 1 = 1 ");
		if(null != model){
			boolean startTimeFlag = StringUtils.isNotBlank(model.getStartTime());
			boolean endTimeFlag = StringUtils.isNotBlank(model.getEndTime());
			if(startTimeFlag){
				sql.append(" and a.datetime >= ?");
				paramList.add(model.getStartTime());
				sql.append(" and a.partitionfield >= ? ");
				paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
			}
			if(endTimeFlag){
				sql.append(" and a.datetime <= ?");
				paramList.add(model.getEndTime());
				sql.append(" and a.partitionfield <= ? ");
				paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
			}
			
			if(StringUtils.isNotBlank(model.getCourseName())){
				sql.append(" and a.coursename like ? ");
				paramList.add("%" + model.getCourseName() + "%");
			}
			if(StringUtils.isNotBlank(model.getTeacherName())){
				sql.append(" and a.teachername like ? ");
				paramList.add("%" + model.getTeacherName() + "%");
			}
			if(StringUtils.isNotBlank(model.getRoomName())){
				sql.append(" and a.roomname like ? ");
				paramList.add("%" + model.getRoomName() + "%");
			}
			if (StringUtils.isNotBlank(model.getUserType())) {
				sql.append(" and a.usertype = ? ");
				paramList.add(model.getUserType());
			}
			if (StringUtils.isNotBlank(model.getUserSource())) {
				sql.append(" and a.usersource like ? ");
				paramList.add("%" + model.getUserSource() + "%");
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and a.platformtype = ? ");
				paramList.add(model.getPlatformType());
			}
			if (StringUtils.isNotBlank(model.getOperateEntrance())) {
				sql.append(" and a.operateentrance like ? ");
				paramList.add("%" + model.getOperateEntrance() + "%");
			}
			sql.append(" and a.businessplatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
		}
		sql.append(" GROUP BY a .datetime ,a.businessplatform");
		if(null != model && model.isCourseNameChecked()){
			sql.append(", a .courseName");
		}
		if(null != model && model.isTeacherNameChecked()){
			sql.append(", a .teacherName");
		}
		if(null != model && model.isRoomNameChecked()){
			sql.append(", a .roomName");
		}
		
	}
	
}
