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
 * 直播间-登录次/人数、访问次/人数、发言次/人数  分析  统计数据
 */
@Repository
public class DasChartRoomUserTypeStatisticsDao extends ReportDao {
	
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
		sql.append("a.vipNumbers,");     //vip用户
		sql.append("a.vipCounts,");
		sql.append("a.realANumbers,");   //真实A用户
		sql.append("a.realACounts,");
		sql.append("a.realNNumbers,");   //真实N用户
		sql.append("a.realNCounts,");
		sql.append("a.demoNumbers,");    //模拟用户
		sql.append("a.demoCounts,");
		sql.append("a.registNumbers,");  //注册用户
		sql.append("a.registCounts,");
		sql.append("a.touristNumbers,"); //游客用户
		sql.append("a.touristCounts,");
		sql.append("a.analystNumbers,"); //分析师
		sql.append("a.analystCounts,");
		sql.append("a.adminNumbers,");   //管理员
		sql.append("a.adminCounts,");
		sql.append("a.cServiceNumbers,");//客服
		sql.append("a.cServiceCounts,");
		sql.append("a.businessplatform");
		
		if("1".equals(model.getUserTypeStatistics())){
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				sql.append(" FROM das_chart_room_usertype_login_statistics a ");
			}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				sql.append(",a.weeks ");
				sql.append(" FROM das_chart_room_usertype_login_statistics_weeks a ");
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
				sql.append(" FROM das_chart_room_usertype_login_statistics_months a ");
			}
		}else if("2".equals(model.getUserTypeStatistics())){
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				sql.append(" FROM das_chart_room_usertype_visit_statistics a ");
			}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				sql.append(",a.weeks ");
				sql.append(" FROM das_chart_room_usertype_visit_statistics_weeks a ");
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
				sql.append(" FROM das_chart_room_usertype_visit_statistics_months a ");
			}
		}else if("3".equals(model.getUserTypeStatistics())){
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				sql.append(" FROM das_chart_room_usertype_speak_statistics a ");
			}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				sql.append(",a.weeks ");
				sql.append(" FROM das_chart_room_usertype_speak_statistics_weeks a ");
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
				sql.append(" FROM das_chart_room_usertype_speak_statistics_months a ");
			}
		}
		sql.append(" WHERE 1 = 1 ");
		if(null != model){
			boolean startTimeFlag = StringUtils.isNotBlank(model.getStartTime());
			boolean endTimeFlag = StringUtils.isNotBlank(model.getEndTime());
			if(startTimeFlag){
				sql.append(" and a.datetime >= ?");
				sql.append(" and a.partitionfield >= ? ");
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM-dd"));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getFirstDayOfMonth(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM")));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy"));
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getFirstDayOfMonth(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM")));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy"));
				}
			}
			if(endTimeFlag){
				sql.append(" and a.datetime <= ?");
				sql.append(" and a.partitionfield <= ? ");
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM-dd"));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getLastDayOfMonth(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM")));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy"));
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getLastDayOfMonth(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM")));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy"));
				}
			}
			
			// 发言类型下面又分公聊、私聊
			if("3".equals(model.getUserTypeStatistics())){
				if (StringUtils.isNotBlank(model.getSpeakType())) {
					sql.append(" and a.speakType = ? ");
					paramList.add(model.getSpeakType());
				}else{
					sql.append(" and a.speakType = ? ");
					paramList.add("all");
				}
			}
			
			if(model.isCourseNameChecked()){
				sql.append(" and a.coursename != ? ");
				paramList.add("all");
				if(StringUtils.isNotBlank(model.getCourseName())){
					sql.append(" and a.coursename like ? ");
					paramList.add("%" + model.getCourseName() + "%");
				}
			}else{
				if(StringUtils.isNotBlank(model.getCourseName())){
					sql.append(" and a.coursename like ? ");
					paramList.add("%" + model.getCourseName() + "%");
				}else{
					sql.append(" and a.coursename = ? ");
					paramList.add("all");
				}
			}
			
			if(model.isTeacherNameChecked()){
				sql.append(" and a.teachername != ? ");
				paramList.add("all");
				if(StringUtils.isNotBlank(model.getTeacherName())){
					sql.append(" and a.teachername like ? ");
					paramList.add("%" + model.getTeacherName() + "%");
				}
			}else{
				if(StringUtils.isNotBlank(model.getTeacherName())){
					sql.append(" and a.teachername like ? ");
					paramList.add("%" + model.getTeacherName() + "%");
				}else{
					sql.append(" and a.teachername = ? ");
					paramList.add("all");
				}
			}
			
			if(model.isRoomNameChecked()){
				sql.append(" and a.roomname != ? ");
				paramList.add("all");
				if(StringUtils.isNotBlank(model.getRoomName())){
					sql.append(" and a.roomname like ? ");
					paramList.add("%" + model.getRoomName() + "%");
				}
			}else{
				if(StringUtils.isNotBlank(model.getRoomName())){
					sql.append(" and a.roomname like ? ");
					paramList.add("%" + model.getRoomName() + "%");
				}else{
					sql.append(" and a.roomname = ? ");
					paramList.add("all");
				}
			}
			
			if (StringUtils.isNotBlank(model.getUserSource())) {
				sql.append(" and a.usersource like ? ");
				paramList.add("%" + model.getUserSource() + "%");
			}else{
				sql.append(" and a.usersource = ? ");
				paramList.add("all");
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and a.platformtype = ? ");
				paramList.add(model.getPlatformType());
			}else{
				sql.append(" and a.platformtype = ? ");
				paramList.add("all");
			}
			if (StringUtils.isNotBlank(model.getOperateEntrance())) {
				sql.append(" and a.operateentrance like ? ");
				paramList.add("%" + model.getOperateEntrance() + "%");
			}else{
				sql.append(" and a.operateentrance = ? ");
				paramList.add("all");
			}
			sql.append(" and a.businessplatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
		}
	}
	
}
