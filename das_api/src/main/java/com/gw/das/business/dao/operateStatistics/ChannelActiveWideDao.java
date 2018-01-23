package com.gw.das.business.dao.operateStatistics;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.DeviceTypeEnum;
import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.enums.srcTypeEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.WebSiteReportDao;
import com.gw.das.business.dao.operateStatistics.entity.OperateStatisticsModel;

/**
 * 渠道新增、活跃报表（client）报表Dao
 * 
 * @author darren
 *
 */
@Repository
public class ChannelActiveWideDao extends WebSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		OperateStatisticsModel model = (OperateStatisticsModel) t;
		sql.append(" select datetime, ");		
		if(model.isDeviceTypeChecked()){
			sql.append(" device_type, ");	    //设备类型
		}
		if(model.isChannelGroupChecked()){
			sql.append(" channel_grp,  ");	    //渠道分组		
		}
		if(model.isChannelLevelChecked()){
			sql.append(" channel_level,  ");	//渠道分级
		}
		if(model.isChannelChecked()){
			sql.append(" channel,  ");	        //渠道
		}
		if(model.isPlatformVersionChecked()){
			sql.append(" platform_version,  ");	//版本
		}
		if(model.isPlatformNameChecked()){
			sql.append(" platform_name,  ");	//马甲包
		}			
		
		sql.append(" sum(new_device_count) as new_device_count, ");           //新增设备数
		sql.append(" sum(active_device_count) as active_device_count,  ");    //活跃设备数
		sql.append(" sum(device_login_count) as device_login_count,  ");      //登陆设备数 			
		sql.append(" sum(account_login_count) as account_login_count,   ");   //登陆账号数
		sql.append(" case when sum(active_device_count) != 0 then cast(sum(device_login_count)/sum(active_device_count) * 100 as decimal(15,2)) || '%' else '-' end as device_login_rate, "); //设备登录率
		sql.append(" sum(time_len)  as   time_len   ");  //总停留时间		
		
		if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
			sql.append(" from das_behavior_event_channel_effect_d   ");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(" from das_behavior_event_channel_effect_w   ");
		}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
			sql.append(" from das_behavior_event_channel_effect_m   ");
		}

		sql.append(" WHERE 1 = 1 ");
		if(null != model){
			if (StringUtils.isNotBlank(model.getCompanyid())) {
				sql.append(" and company_id = ? ");
				paramList.add(model.getCompanyid());
			}
			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatform());
			}	
			
			if (StringUtils.isNotBlank(model.getSrcType())) {
				sql.append(" and src_type = ? ");
				paramList.add(model.getSrcType());
			}
				
			if(srcTypeEnum.WEB.getValue().equals(model.getSrcType())){
				if (StringUtils.isNotBlank(model.getDeviceType())) {
					sql.append(" and device_type = ? ");
					paramList.add(model.getDeviceType());
				}
			}else{
				if (StringUtils.isNotBlank(model.getDeviceType())) {
					sql.append(" and device_type = ? ");
					paramList.add(model.getDeviceType());
				}else{
					sql.append(" and device_type != ? ");
					paramList.add(String.valueOf(DeviceTypeEnum.APP.getLabelKey()));
				}
			}
			
			if (StringUtils.isNotBlank(model.getChannelGroup()) && !"null".equals(model.getChannelGroup())) {
				sql.append(" and channel_grp in ( ");
				String channelgroup = model.getChannelGroup();
				String[] channelgroupArray = channelgroup.split(",");			
				for(int i=0; i<channelgroupArray.length; i++){
					if(i+1 == channelgroupArray.length){
						sql.append(" ? ");
					}else{
						sql.append(" ?, ");
					}
					paramList.add(channelgroupArray[i]);
				}
				sql.append(" ) ");
			}
			
			if (StringUtils.isNotBlank(model.getChannelLevel()) && !"null".equals(model.getChannelLevel())) {
				sql.append(" and channel_level in ( ");
				String channellevel = model.getChannelLevel();
				String[] channellevelArray = channellevel.split(",");			
				for(int i=0; i<channellevelArray.length; i++){
					if(i+1 == channellevelArray.length){
						sql.append(" ? ");
					}else{
						sql.append(" ?, ");
					}
					paramList.add(channellevelArray[i]);
				}
				sql.append(" ) ");
			}
			
			if (StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())) {
				sql.append(" and channel in ( ");
				String channel = model.getChannel();
				String[] channelArray = channel.split(",");			
				for(int i=0; i<channelArray.length; i++){
					if(i+1 == channelArray.length){
						sql.append(" ? ");
					}else{
						sql.append(" ?, ");
					}
					paramList.add(channelArray[i]);
				}
				sql.append(" ) ");
			}
			
			if (StringUtils.isNotBlank(model.getPlatformVersion())) {
				sql.append(" and platform_version like ? ");
				paramList.add("%" + model.getPlatformVersion() + "%");
			}
			
			if (StringUtils.isNotBlank(model.getPlatformName())) {
				sql.append(" and platform_name like ? ");
				paramList.add("%" + model.getPlatformName() + "%");
			}												
			
			if(StringUtils.isNotBlank(model.getStartTime())){
				sql.append(" and datetime >= ? ");				
				sql.append(" and partitionfield >= ? ");
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}
				if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}
				if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}
			}
			if(StringUtils.isNotBlank(model.getEndTime())){
				sql.append(" and datetime <= ? ");
				sql.append(" and partitionfield <= ? ");
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}
				if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}
				if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}
			}
			
			sql.append("group by datetime ");		
			if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				sql.append("  ,weeks  ");	
			}
			if(model.isDeviceTypeChecked()){
				sql.append("  ,device_type  ");	
			}
			if(model.isChannelGroupChecked()){
				sql.append("  ,channel_grp  ");		
			}
			if(model.isChannelLevelChecked()){
				sql.append("  ,channel_level  ");	
			}
			if(model.isChannelChecked()){
				sql.append("  ,channel  ");	
			}
			if(model.isPlatformVersionChecked()){
				sql.append("  ,platform_version  ");	
			}
			if(model.isPlatformNameChecked()){
				sql.append("  ,platform_name ");	
			}				
		}
	}
}
