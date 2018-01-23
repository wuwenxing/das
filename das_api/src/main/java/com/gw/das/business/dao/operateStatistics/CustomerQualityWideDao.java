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
public class CustomerQualityWideDao extends WebSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		OperateStatisticsModel model = (OperateStatisticsModel) t;
		sql.append(" select ");	
		
		if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
			sql.append(" to_char(execdate_rpt,'YYYY-MM-DD') dateTime,  ");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(" week_start_date dateTime, ");
			//sql.append(" week_of_year, ");
		}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){	
			sql.append(" year_month dateTime,  ");
			//sql.append(" month, ");
		}		
		
		if(model.isDeviceTypeChecked()){
			sql.append(" device_type , ");	  //平台
		}
		if(model.isChannelGroupChecked()){
			sql.append(" channel_grp ,  ");	 //渠道分组		
		}
		if(model.isChannelLevelChecked()){
			sql.append(" channel_level ,  ");	//渠道分级
		}
		if(model.isChannelChecked()){
			sql.append(" channel,  ");	 //渠道
		}
		if(model.isUtmcsrChecked()){
			sql.append(" utmcsr,  ");	//来源
		}
		if(model.isUtmcmdChecked()){
			sql.append(" utmcmd,  ");	 //媒件
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
			sql.append(" landing_page,  ");	//landingPage
		}
		
		if(model.isPlatformVersionChecked()){
			sql.append(" platform_version,  ");	//版本
		}
		if(model.isPlatformNameChecked()){
			sql.append(" platform_name,  ");	//马甲包
		}	
		
		sql.append(" sum(demo_open_cnt) as demo_open_cnt, "); //模拟开户数
		sql.append(" sum(account_open_cnt) as account_open_cnt,  "); //真实开户数
		sql.append(" sum(account_active_cnt) as account_active_cnt,  ");  //激活账户数		
		sql.append(" sum(demo_open_login_cnt) as demo_open_login_cnt,   "); //模拟开户登录数
		sql.append(" case when sum(demo_open_cnt) != 0 then cast(sum(demo_open_login_cnt)/sum(demo_open_cnt) * 100 as decimal(15,2)) || '%' else '-' end as demo_open_login_ratio , "); //模拟开户登录率
		sql.append(" sum(demo_to_real_cnt) as demo_to_real_cnt,  ");  //模拟开户转真实数	
		sql.append(" case when sum(demo_open_cnt) != 0 then cast(sum(demo_to_real_cnt)/sum(demo_open_cnt) * 100 as decimal(15,2)) || '%' else '-'  end as demo_to_real_ratio ,  ");  //模拟开户转真实率
		sql.append(" sum(demo_to_active_cnt ) as demo_to_active_cnt , ");  //模拟开户转激活数
		sql.append(" case when sum(demo_open_cnt) != 0 then cast(sum(demo_to_active_cnt)/sum(demo_open_cnt) * 100 as decimal(15,2)) || '%' else '-' end as demo_to_active_ratio, ");  //模拟开户转激活率
		sql.append(" sum(account_open_login_cnt) as account_open_login_cnt, ");  //真实开户登录数
		sql.append(" case when sum(account_open_cnt) != 0 then cast(sum(account_open_login_cnt)/sum(account_open_cnt) * 100 as decimal(15,2)) || '%' else '-' end as account_open_login_ratio, ");  //真实开户登录率			
		sql.append(" sum(account_open_active_cnt) as account_open_active_cnt, ");  //真实开户激活数
		sql.append(" case when sum(account_open_cnt) != 0 then cast(sum(account_open_active_cnt)/sum(account_open_cnt) * 100 as decimal(15,2)) || '%' else '-' end as account_open_active_ratio, ");  //真实开户激活率
		sql.append(" sum(account_open_order_cnt) as account_open_order_cnt, ");  //真实开户交易数
		sql.append(" case when sum(account_open_cnt) != 0 then cast(sum(account_open_order_cnt)/sum(account_open_cnt) * 100 as decimal(15,2)) || '%' else '-' end as account_open_order_ratio ");  //真实开户交易率
		
		if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
			sql.append(" from das_behavior_event_account_quality_d ");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(" from das_behavior_event_account_quality_w ");
		}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){	
			sql.append(" from das_behavior_event_account_quality_m ");
		}
		
		sql.append(" WHERE 1 = 1 ");
		if(null != model){
			if (StringUtils.isNotBlank(model.getCompanyid())) {
				sql.append(" and company_id = ? ");
				paramList.add(model.getCompanyid());
			}
			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and business_platform = ? ");
				paramList.add(model.getBusinessPlatformInt());
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
			/*if (StringUtils.isNotBlank(model.getDeviceType())) {
				sql.append(" and lower(device_type) = ? ");
				paramList.add(model.getDeviceType().toLowerCase());
			}*/
			
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
			
			if (StringUtils.isNotBlank(model.getPlatformVersion())) {
				sql.append(" and platform_version like ? ");
				paramList.add("%" + model.getPlatformVersion() + "%");
			}
			
			if (StringUtils.isNotBlank(model.getPlatformName())) {
				sql.append(" and platform_name like ? ");
				paramList.add("%" + model.getPlatformName() + "%");
			}	
			
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and execdate_rpt >= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));				
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and execdate_rpt <= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
				}
			}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and week_start_date >= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));				
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and week_start_date <= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
				}
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){	
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and year_month >= ? ");
					paramList.add(model.getStartTime());			
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and year_month <= ? ");
					paramList.add(model.getEndTime());
				}
			}

			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				sql.append("group by execdate_rpt ");	
			}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				sql.append("group by week_start_date ");	
				//sql.append(",week_of_year ");	
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){	
				sql.append("group by year_month ");	
				//sql.append(",month ");
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
			if(model.isPlatformVersionChecked()){
				sql.append("  ,platform_version  ");	
			}
			if(model.isPlatformNameChecked()){
				sql.append("  ,platform_name ");	
			}	
		}
	}
}
