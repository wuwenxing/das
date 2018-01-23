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
public class ChannelEffectWideDao extends WebSiteReportDao {

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
		if(model.isUtmcsrChecked()){
			sql.append(" utmcsr,  ");	//来源
		}
		if(model.isUtmcmdChecked()){
			sql.append(" utmcmd,  ");	//媒件
		}
		if(model.isUtmccnChecked()){
			sql.append(" utmccn,  ");	//系列
		}	
		if(model.isUtmcctChecked()){
			sql.append(" utmcct,  ");	//组
		}	
		if(model.isUtmctrChecked()){
			sql.append(" utmctr,  ");	//关键字
		}	
		if(model.isLandingPageChecked()){
			sql.append(" landing_page,  ");	//landing_page
		}	
		
		sql.append(" sum(new_device_count) as new_device_count, ");           //新增设备数
		sql.append(" sum(active_device_count) as active_device_count,  ");    //活跃设备数
		sql.append(" sum(device_login_count) as device_login_count,  ");      //登陆设备数 			
		sql.append(" sum(account_login_count) as account_login_count,   ");   //登陆账号数
		sql.append(" case when sum(active_device_count) > 0 then cast(sum(device_login_count)/sum(active_device_count) * 100 as decimal(15,2)) || '%' else '-' end as device_login_rate,   "); //设备登录率
		sql.append(" sum(time_len)  as   time_len ,  ");  //总停留时间				
		sql.append(" sum(visit_count) as visit_count, "); //会话数
		sql.append(" case when sum(visit_count) !=0 then cast(sum(time_len)/sum(visit_count) as decimal(15,2)) || '' else '-' end as avg_visit_time_len , ");//会话平均使用时间
		sql.append(" sum(event_advisory_count ) as event_advisory_count , ");//进入咨询会话数
		sql.append(" sum(event_demo_count) as event_demo_count, ");//进入模拟开户会话数
		sql.append(" sum(event_real_count) as event_real_count,  ");//进入真实开户会话数
		sql.append(" sum(event_active_count) as event_active_count, ");//进入入金流程会话数
		sql.append(" sum(event_device_advisory_count) as event_device_advisory_count, ");//进入咨询设备数
		sql.append(" sum(event_device_demo_count) as event_device_demo_count,  ");//进入模拟开户设备数
		sql.append(" sum(event_device_real_count) as event_device_real_count, ");//进入真实开户设备数
		sql.append(" sum(event_device_active_count) as event_device_active_count,  ");//进入入金流程设备数
		sql.append(" sum(demo_count) as demo_count, ");//模拟开户数
		sql.append(" sum(real_count) as real_count,  ");//真实开户数
		sql.append(" sum(active_count) as active_count,  ");//入金账户数			
		sql.append(" case when sum(new_device_count) != 0 then cast(sum(demo_count)/sum(new_device_count) * 100 as decimal(15,2)) || '%' else '-'  end as demo_count_rate, ");//模拟开户率
		sql.append(" case when sum(new_device_count) != 0 then cast(sum(real_count)/sum(new_device_count) * 100 as decimal(15,2)) || '%' else '-' end as real_count_rate, ");//真实开户率
		sql.append(" case when sum(visit_count) != 0 then cast(sum(demo_count)/sum(visit_count) * 100 as decimal(15,2)) || '%' else '-' end as demo_visit_rate, ");//模拟会话率
		sql.append(" case when sum(visit_count) != 0 then cast(sum(real_count)/sum(visit_count) * 100 as decimal(15,2)) || '%' else '-' end as real_visit_rate, ");//真实会话率
		sql.append(" case when sum(real_count) != 0 then cast(sum(active_count)/sum(real_count) * 100 as decimal(15,2)) || '%' else '-' end as active_rate  ");//入金率
	
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
			
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				sql.append(" and utmcsr like ? ");
				paramList.add("%" + model.getUtmcsr() + "%");
			}
			
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				sql.append(" and utmcmd like ? ");
				paramList.add("%" + model.getUtmcmd() + "%");
			}	
			
			if (StringUtils.isNotBlank(model.getUtmccn())) {
				sql.append(" and utmccn like ? ");
				paramList.add("%" + model.getUtmccn() + "%");
			}
			
			if (StringUtils.isNotBlank(model.getUtmcct())) {
				sql.append(" and utmcct like ? ");
				paramList.add("%" + model.getUtmcct() + "%");
			}	
			
			if (StringUtils.isNotBlank(model.getUtmctr())) {
				sql.append(" and utmctr like ? ");
				paramList.add("%" + model.getUtmctr() + "%");
			}
			
			if (StringUtils.isNotBlank(model.getLandingPage())) {
				sql.append(" and landing_page like ? ");
				paramList.add("%" + model.getLandingPage() + "%");
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
			if(model.isUtmcsrChecked()){
				sql.append("  ,utmcsr  ");	
			}
			if(model.isUtmcmdChecked()){
				sql.append("  ,utmcmd ");	
			}	
			if(model.isUtmccnChecked()){
				sql.append("  ,utmccn  ");	
			}
			if(model.isUtmcctChecked()){
				sql.append("  ,utmcct ");	
			}	
			if(model.isUtmctrChecked()){
				sql.append("  ,utmctr  ");	
			}
			if(model.isLandingPageChecked()){
				sql.append("  ,landing_page ");	
			}	
		}		
		
	}
}