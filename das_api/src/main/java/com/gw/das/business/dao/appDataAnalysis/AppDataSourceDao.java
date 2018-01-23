package com.gw.das.business.dao.appDataAnalysis;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.base.AppSiteReportDao;
import com.gw.das.business.dao.base.BaseSearchModel;

@Repository
public class AppDataSourceDao extends AppSiteReportDao{
	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		AppDataAnalysisSearchModel model = (AppDataAnalysisSearchModel) t;
		sql.append(" SELECT row_number() over() as rowkey,");	
				
		sql.append("a.datetime,");
		
        if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
        	sql.append("a.hour,");
		}

		if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append("a.weeks,");
		}
		
		if(model.isEventCategoryChecked()){
			sql.append("a.eventCategory,");	
		}
		
        if(model.isEventActionChecked()){
        	sql.append("a.eventAction,");
		}
        if(model.isEventLabelChecked()){
        	sql.append("a.eventLabel,");
		}
        if(model.isEventValueChecked()){
        	sql.append("a.eventValue,");
		}
        
        if(model.isDeviceTypeChecked()){
        	sql.append("a.deviceType,");
		}
        if(model.isPlatformTypeChecked()){
        	sql.append("a.platformType,");
		}
        if(model.isPlatformNameChecked()){
        	sql.append("a.platformName,");
		}
        if(model.isPlatformVersionChecked()){
        	sql.append("a.platformVersion,");
		}
        if(model.isUserTypeChecked()){
        	sql.append("a.userType,");
		}
        if(model.isChannelChecked()){
        	sql.append("a.channel,");
		}
		
		sql.append(" devicecount as deviceidCount, ");
		sql.append(" eventcount as deviceidNum ");
		if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM gts2_app_ods_analysis_report_h a ");
		}else if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM gts2_app_ods_analysis_report_d a ");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM gts2_app_ods_analysis_report_w a ");
		}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM gts2_app_ods_analysis_report_m a ");
		}
		
		sql.append(" WHERE 1 = 1 ");
		if(null != model){
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and a.datetime >= ?");
				sql.append(" and a.partitionfield >= ? ");
				if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
					sql.append(" and a.hour >= ?");
					paramList.add(model.getStartTime().split(" ")[0]);
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime().split(" ")[0], "yyyy-MM-dd"), "yyyy_MM_dd"));
					paramList.add(model.getStartTime().split(" ")[1]);
					
				}else if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM-dd"), "yyyy_MM_dd"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					sql.append(" and a.weeks >= ?");
					Date date = DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM-dd");
					int week = DateUtil.getWeek(date);
					String year = DateUtil.formatDateToString(date,"yyyy");
					
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
					paramList.add(year+"-"+week);
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and a.datetime < ?");
				sql.append(" and a.partitionfield <= ? ");
				if(ReportTypeEnum.hours.getLabelKey().equals(model.getReportType())){
					sql.append(" and a.hour < ?");
					paramList.add(model.getEndTime().split(" ")[0]);
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime().split(" ")[0], "yyyy-MM-dd"), "yyyy_MM_dd"));
					paramList.add(model.getEndTime().split(" ")[1]);
					
				}else if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM-dd"), "yyyy_MM_dd"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					sql.append(" and a.weeks < ?");
					Date date = DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM-dd");
					int week = DateUtil.getWeek(date);
					String year = DateUtil.formatDateToString(date,"yyyy");
					
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
					paramList.add(year+"-"+week);
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}
			}
			
			if (!model.isEventCategoryChecked()) {
				sql.append(" and a.eventCategory = ? ");
				paramList.add("ALL");
			}else if (model.isEventCategoryChecked()) {
				if (StringUtils.isNotBlank(model.getEventCategory())) {
					sql.append(" and a.eventCategory = ? ");
					paramList.add(model.getEventCategory());
				}else{
					sql.append(" and a.eventCategory != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isEventActionChecked()) {
				sql.append(" and a.eventAction = ? ");
				paramList.add("ALL");
			}else if (model.isEventActionChecked()) {
				if (StringUtils.isNotBlank(model.getEventAction())) {
					sql.append(" and a.eventAction = ? ");
					paramList.add(model.getEventAction());
				}else{
					sql.append(" and a.eventAction != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isEventLabelChecked()) {
				sql.append(" and a.eventLabel = ? ");
				paramList.add("ALL");
			}else if (model.isEventLabelChecked()) {
				if (StringUtils.isNotBlank(model.getEventLabel())) {
					sql.append(" and a.eventLabel = ? ");
					paramList.add(model.getEventLabel());
				}else{
					sql.append(" and a.eventLabel != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isEventValueChecked()) {
				sql.append(" and a.eventValue = ? ");
				paramList.add("ALL");
			}else if (model.isEventValueChecked()) {
				if (StringUtils.isNotBlank(model.getEventValue())) {
					sql.append(" and a.eventValue = ? ");
					paramList.add(model.getEventValue());
				}else{
					sql.append(" and a.eventValue != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isDeviceTypeChecked()) {
				sql.append(" and a.deviceType = ? ");
				paramList.add("ALL");
			}else if (model.isDeviceTypeChecked()) {
				if (StringUtils.isNotBlank(model.getDeviceType())) {
					sql.append(" and a.deviceType = ? ");
					paramList.add(model.getDeviceType());
				}else{
					sql.append(" and a.deviceType != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isPlatformTypeChecked()) {
				sql.append(" and a.platformType = ? ");
				paramList.add("ALL");
			}else if (model.isPlatformTypeChecked()) {
				if (StringUtils.isNotBlank(model.getPlatformType())) {
					sql.append(" and a.platformType = ? ");
					paramList.add(model.getPlatformType());
				}else{
					sql.append(" and a.platformType != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isPlatformNameChecked()) {
				sql.append(" and a.platformName = ? ");
				paramList.add("ALL");
			}else if (model.isPlatformNameChecked()) {
				if (StringUtils.isNotBlank(model.getPlatformName())) {
					sql.append(" and a.platformName = ? ");
					paramList.add(model.getPlatformName());
				}else{
					sql.append(" and a.platformName != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isPlatformVersionChecked()) {
				sql.append(" and a.platformVersion = ? ");
				paramList.add("ALL");
			}else if (model.isPlatformVersionChecked()) {
				if (StringUtils.isNotBlank(model.getPlatformVersion())) {
					sql.append(" and a.platformVersion = ? ");
					paramList.add(model.getPlatformVersion());
				}else{
					sql.append(" and a.platformVersion != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isUserTypeChecked()) {
				sql.append(" and a.userType = ? ");
				paramList.add("ALL");
			}else if (model.isUserTypeChecked()) {
				if (StringUtils.isNotBlank(model.getUserType())) {
					sql.append(" and a.userType = ? ");
					paramList.add(model.getUserType());
				}else{
					sql.append(" and a.userType != ? ");
					paramList.add("ALL");
				}
			}
			
			if (!model.isChannelChecked()) {
				sql.append(" and a.channel = ? ");
				paramList.add("ALL");
			}else if (model.isChannelChecked()) {
				if (StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())) {
					sql.append(" and a.channel = ? ");
					paramList.add(model.getChannel());
				}else{
					sql.append(" and a.channel != ? ");
					paramList.add("ALL");
				}
			}

			sql.append(" and a.businessplatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			
		}
	}
}
