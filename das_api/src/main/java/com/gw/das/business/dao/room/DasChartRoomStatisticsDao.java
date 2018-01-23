package com.gw.das.business.dao.room;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.ReportDao;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.room.entity.DasChartRoomSearchModel;
/**
 * 行为统计-时\日\周\月
 * @author darren
 *
 */
@Repository
public class DasChartRoomStatisticsDao extends ReportDao {
	
	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		DasChartRoomSearchModel model = (DasChartRoomSearchModel) t;
		sql.append(" SELECT");
		sql.append("	row_number() over() as rowkey,");
		sql.append("	A .datetime,");
		if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
			sql.append("	A .hours,");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append("	A .weeks,");
		}
		if(null != model && model.isRoomNameChecked()){
			sql.append("	A .roomName,");
		}
		if(null != model && model.isCourseNameChecked()){
			sql.append("	A .courseName,");
		}
		if(null != model && model.isTeacherNameChecked()){
			sql.append("	A .teacherName,");
		}
		sql.append("	SUM (A .publicspeakcount) AS publicspeakcount,");
		sql.append("	SUM (A .publicspeaknumber) AS publicspeaknumber,");
		sql.append("	SUM (A .privatespeakcount) AS privatespeakcount,");
		sql.append("	SUM (A .privatespeaknumber) AS privatespeaknumber,");
		sql.append("	SUM (A .visitcount) AS visitcount,");
		sql.append("	SUM (A .visitnumber) AS visitnumber,");
		sql.append("	SUM (A .logincount) AS logincount,");
		sql.append("	SUM (A .loginnumber) AS loginnumber");
		if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM das_chart_room_statistics_hours A ");
		}else if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM das_chart_room_statistics_days A ");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM das_chart_room_statistics_weeks A ");
		}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM das_chart_room_statistics_months A ");
		}
		sql.append(" WHERE 1 = 1 ");
		if(null != model){
			boolean startTimeFlag = StringUtils.isNotBlank(model.getStartTime());
			boolean endTimeFlag = StringUtils.isNotBlank(model.getEndTime());
			if(startTimeFlag){
				sql.append(" and A.datetime >= ?");
				sql.append(" and A.partitionfield >= ? ");
				paramList.add(model.getStartTime());
				if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy"));
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy"));
				}
			}
			if(endTimeFlag){
				sql.append(" and A.datetime <= ?");
				sql.append(" and A.partitionfield <= ? ");
				paramList.add(model.getEndTime());
				if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy"));
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy"));
				}
			}
			
			if (StringUtils.isNotBlank(model.getStartHours())) {
				sql.append(" and A.hours >= ? ");
				paramList.add(model.getStartHours().length() == 1 ? "0"+model.getStartHours():model.getStartHours());
			}
			if (StringUtils.isNotBlank(model.getEndHours())) {
				sql.append(" and A.hours <= ? ");
				paramList.add(model.getEndHours().length() == 1 ? "0"+model.getEndHours():model.getEndHours());
			}
			if (StringUtils.isNotBlank(model.getRoomName())) {
				sql.append(" and A.roomname like ? ");
				paramList.add("%" + model.getRoomName() + "%");
			}
			if (StringUtils.isNotBlank(model.getTeacherName())) {
				sql.append(" and A.teachername like ? ");
				paramList.add("%" + model.getTeacherName() + "%");
			}
			if (StringUtils.isNotBlank(model.getCourseName())) {
				sql.append(" and A.coursename like ? ");
				paramList.add("%" + model.getCourseName() + "%");
			}
			if (StringUtils.isNotBlank(model.getUserType())) {
				sql.append(" and A.usertype = ? ");
				paramList.add(model.getUserType());
			}
			if (StringUtils.isNotBlank(model.getUserSource())) {
				sql.append(" and A.usersource like ? ");
				paramList.add("%" + model.getUserSource() + "%");
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and A.platformtype = ? ");
				paramList.add(model.getPlatformType());
			}
			if (StringUtils.isNotBlank(model.getOperateEntrance())) {
				sql.append(" and A.operateentrance like ? ");
				paramList.add("%" + model.getOperateEntrance() + "%");
			}
			sql.append(" and A.businessplatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
		}
		sql.append(" GROUP BY A .datetime");
		if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
			sql.append(", A .hours");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(", A .weeks");
		}
		if(null != model && model.isCourseNameChecked()){
			sql.append(", A .courseName");
		}
		if(null != model && model.isTeacherNameChecked()){
			sql.append(", A .teacherName");
		}
		if(null != model && model.isRoomNameChecked()){
			sql.append(", A .roomName");
		}
	}
	
}
