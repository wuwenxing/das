package com.gw.das.business.dao.tradeGts2.tradeBordereaux;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.tradeGts2.entity.TradeSearchModel;

/**
 * 交易情况记录报表Dao
 * 
 * @author darren
 *
 */
@Repository
public class TradeSituationWideDao extends TradeSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		TradeSearchModel model = (TradeSearchModel) t;		
		if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
			sql.append(" select to_char(d.active_time,'YYYY-MM-DD') as execdate, ");		
			if(model.isDeviceTypeChecked()){
				sql.append("   d.device_type as devicetype, ");	  //平台
			}
			if(model.isChannelgroupChecked()){
				sql.append("  d.channel_group as  channelgroup,  ");	 //渠道分组		
			}
			if(model.isChannellevelChecked()){
				sql.append("  d.channel_level as  channellevel,  ");	//渠道分级
			}
			if(model.isChannelChecked()){
				sql.append("  d.channel as  channel,  ");	 //渠道
			}
			if(model.isUtmsourceChecked()){
				sql.append("  d.utm_source as  utmsource,  ");	//来源
			}
			if(model.isUtmmediumChecked()){
				sql.append("  d.utm_medium as  utmmedium,  ");	 //媒件
			}
			if(model.isUtmcampaignChecked()){
				sql.append("  d.utm_campaign as  utmcampaign,  ");	//系列
			}
			if(model.isUtmcontentChecked()){
				sql.append("  d.utm_content as  utmcontent,  ");	//组
			}
			if(model.isUtmtermChecked()){
				sql.append("  d.utm_term as  utmterm,  ");	//关键字
			}
			
			sql.append(" count(distinct d.account_no) as accountactivecnt, "); //激活账户数
			sql.append(" sum(a.deposit_amt) as depositamt,  "); //总存款
			sql.append(" sum(a.withdraw_amt) as withdrawamt,  ");  //总取款			
			sql.append(" sum(deposit_amt-withdraw_amt) as cleandeposit,   "); //净入金
			sql.append(" sum(a.open_volume) as openvolume,   "); //开仓手数
			sql.append(" sum(a.close_volume) as closevolume,    ");  //平仓手数	
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.deposit_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgdepositamt,    ");  //人均存款
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.withdraw_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgwithdrawamt,     ");  //人均取款	
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.deposit_amt-a.withdraw_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgcleandeposit,    ");  //人均净入金
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.open_volume)/count(distinct d.account_no) as decimal(15,2)) || '' else '-' end as avgopenvolume,   ");  //人均开仓手数
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.close_volume)/count(distinct d.account_no) as decimal(15,2)) || '' else '-' end as avgclosevolume    ");  //人均平仓手数
			
			sql.append(" from rds.dim_account d join rds.fact_trans_agg_account a  ");
			sql.append(" on d.account_no=a.account_no and d.company_id=a.company_id and d.platform=a.platform  ");

			sql.append(" WHERE 1 = 1 ");
			sql.append(" and d.is_test=0 ");	
			sql.append(" and d.account_type='直客' ");
			sql.append(" and d.open_source= 1 ");
			if(null != model){
				if (StringUtils.isNotBlank(model.getCompanyid())) {
					sql.append(" and d.company_id = ? ");
					paramList.add(model.getCompanyid());
				}
				
				if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
					sql.append(" and d.business_platform = ? ");
					paramList.add(model.getBusinessPlatformInt());
				}	
					
				if (StringUtils.isNotBlank(model.getDeviceType())) {
					sql.append(" and d.device_type = ? ");
					paramList.add(model.getDeviceType());
				}
				
				if (StringUtils.isNotBlank(model.getChannelgroup()) && !"null".equals(model.getChannelgroup())) {
					sql.append(" and d.channel_group in ( ");
					String channelgroup = model.getChannelgroup();
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
				
				if (StringUtils.isNotBlank(model.getChannellevel()) && !"null".equals(model.getChannellevel())) {
					sql.append(" and d.channel_level in ( ");
					String channellevel = model.getChannellevel();
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
					sql.append(" and d.channel in ( ");
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
				
				if (StringUtils.isNotBlank(model.getUtmsource())) {
					sql.append(" and d.utm_source  like ?  ");
					paramList.add("%" + model.getUtmsource() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmmedium())) {
					sql.append(" and d.utm_medium  like ?  ");
					paramList.add("%" + model.getUtmmedium() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmcampaign())) {
					sql.append(" and d.utm_campaign  like ?  ");
					paramList.add("%" + model.getUtmcampaign() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmcontent())) {
					sql.append(" and d.utm_content  like ?  ");
					paramList.add("%" + model.getUtmcontent() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmterm())) {
					sql.append(" and d.utm_term  like ?  ");
					paramList.add("%" + model.getUtmterm() + "%");
				}						
				
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and date(d.active_time) >= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
					
					sql.append(" and ( a.execdate_rpt is null or a.execdate_rpt >= ? )");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));										
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and date(d.active_time) <= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
					
					sql.append(" and ( a.execdate_rpt is null or a.execdate_rpt <= ? ) ");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
				}
				
				sql.append("group by d.active_time ");			
				if(model.isDeviceTypeChecked()){
					sql.append("  ,d.device_type  ");	
				}
				if(model.isChannelgroupChecked()){
					sql.append("  ,d.channel_group  ");		
				}
				if(model.isChannellevelChecked()){
					sql.append("  ,d.channel_level  ");	
				}
				if(model.isChannelChecked()){
					sql.append("  ,d.channel  ");	
				}
				if(model.isUtmsourceChecked()){
					sql.append("  ,d.utm_source  ");	
				}
				if(model.isUtmmediumChecked()){
					sql.append("  ,d.utm_medium ");	
				}
				if(model.isUtmcampaignChecked()){
					sql.append("  ,d.utm_campaign  ");	
				}
				if(model.isUtmcontentChecked()){
					sql.append("  ,d.utm_content ");	
				}
				if(model.isUtmtermChecked()){
					sql.append("  ,d.utm_term ");	
				}
			}
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(" select to_char(da.week_start_date,'YYYY-MM-DD') as execdate, ");		
			if(model.isDeviceTypeChecked()){
				sql.append(" d.device_type as devicetype,  ");	  //平台
			}
			if(model.isChannelgroupChecked()){
				sql.append("  d.channel_group as  channelgroup,  ");	 //渠道分组		
			}
			if(model.isChannellevelChecked()){
				sql.append("  d.channel_level as  channellevel,  ");	//渠道分级
			}
			if(model.isChannelChecked()){
				sql.append("  d.channel as  channel,  ");	 //渠道
			}
			if(model.isUtmsourceChecked()){
				sql.append("  d.utm_source as  utmsource,  ");	//来源
			}
			if(model.isUtmmediumChecked()){
				sql.append("  d.utm_medium as  utmmedium,  ");	 //媒件
			}
			if(model.isUtmcampaignChecked()){
				sql.append("  d.utm_campaign as  utmcampaign,  ");	//系列
			}
			if(model.isUtmcontentChecked()){
				sql.append("  d.utm_content as  utmcontent,  ");	//组
			}
			if(model.isUtmtermChecked()){
				sql.append("  d.utm_term as  utmterm,  ");	//关键字
			}
			
			sql.append(" count(distinct d.account_no) as accountactivecnt, "); //激活账户数
			sql.append(" sum(a.deposit_amt) as depositamt,  "); //总存款
			sql.append(" sum(a.withdraw_amt) as withdrawamt,  ");  //总取款			
			sql.append(" sum(deposit_amt-withdraw_amt) as cleandeposit,   "); //净入金
			sql.append(" sum(a.open_volume) as openvolume,   "); //开仓手数
			sql.append(" sum(a.close_volume) as closevolume,    ");  //平仓手数	
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.deposit_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgdepositamt,    ");  //人均存款
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.withdraw_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgwithdrawamt,     ");  //人均取款	
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.deposit_amt-a.withdraw_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgcleandeposit,    ");  //人均净入金
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.open_volume)/count(distinct d.account_no) as decimal(15,2)) || '' else '-' end as avgopenvolume,   ");  //人均开仓手数
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.close_volume)/count(distinct d.account_no) as decimal(15,2)) || '' else '-' end as avgclosevolume    ");  //人均平仓手数
			
			sql.append(" from rds.dim_account d join rds.fact_trans_agg_account a  ");
			sql.append(" on d.account_no=a.account_no and d.company_id=a.company_id and d.platform=a.platform  ");
			sql.append(" join rds.dim_date da on date(d.active_time)=da.full_date ");
			sql.append(" WHERE 1 = 1 ");
			sql.append(" and d.is_test=0 ");	
			sql.append(" and d.account_type='直客' ");
			if(null != model){
				if (StringUtils.isNotBlank(model.getCompanyid())) {
					sql.append(" and d.company_id = ? ");
					paramList.add(model.getCompanyid());
				}
				
				if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
					sql.append(" and d.business_platform = ? ");
					paramList.add(model.getBusinessPlatformInt());
				}	
					
				if (StringUtils.isNotBlank(model.getDeviceType())) {
					sql.append(" and d.device_type = ? ");
					paramList.add(model.getDeviceType());
				}
				
				if (StringUtils.isNotBlank(model.getChannelgroup()) && !"null".equals(model.getChannelgroup())) {
					sql.append(" and d.channel_group in ( ");
					String channelgroup = model.getChannelgroup();
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
				
				if (StringUtils.isNotBlank(model.getChannellevel()) && !"null".equals(model.getChannellevel())) {
					sql.append(" and d.channel_level in ( ");
					String channellevel = model.getChannellevel();
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
					sql.append(" and d.channel in ( ");
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
				
				if (StringUtils.isNotBlank(model.getUtmsource())) {
					sql.append(" and d.utm_source  like ?  ");
					paramList.add("%" + model.getUtmsource() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmmedium())) {
					sql.append(" and d.utm_medium  like ?  ");
					paramList.add("%" + model.getUtmmedium() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmcampaign())) {
					sql.append(" and d.utm_campaign  like ?  ");
					paramList.add("%" + model.getUtmcampaign() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmcontent())) {
					sql.append(" and d.utm_content  like ?  ");
					paramList.add("%" + model.getUtmcontent() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmterm())) {
					sql.append(" and d.utm_term  like ?  ");
					paramList.add("%" + model.getUtmterm() + "%");
				}						
				
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and date(d.active_time) >= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
					
					sql.append(" and ( a.execdate_rpt is null or a.execdate_rpt >= ? )");
					paramList.add(DateUtil.getDateFromStr(model.getStartTime() + DateUtil.startTime));
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and date(d.active_time) <= ? ");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
					
					sql.append(" and ( a.execdate_rpt is null or a.execdate_rpt <= ? ) ");
					paramList.add(DateUtil.getDateFromStr(model.getEndTime() + DateUtil.endTime));
				}
				
				sql.append("group by da.week_start_date ");			
				if(model.isDeviceTypeChecked()){
					sql.append("  ,d.device_type  ");	
				}
				if(model.isChannelgroupChecked()){
					sql.append("  ,d.channel_group  ");		
				}
				if(model.isChannellevelChecked()){
					sql.append("  ,d.channel_level  ");	
				}
				if(model.isChannelChecked()){
					sql.append("  ,d.channel  ");	
				}
				if(model.isUtmsourceChecked()){
					sql.append("  ,d.utm_source  ");	
				}
				if(model.isUtmmediumChecked()){
					sql.append("  ,d.utm_medium ");	
				}
				if(model.isUtmcampaignChecked()){
					sql.append("  ,d.utm_campaign  ");	
				}
				if(model.isUtmcontentChecked()){
					sql.append("  ,d.utm_content ");	
				}
				if(model.isUtmtermChecked()){
					sql.append("  ,d.utm_term ");	
				}
			}
		}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
			sql.append(" select to_char(d.active_time,'YYYY-MM') as execdate, ");		
			if(model.isDeviceTypeChecked()){
				sql.append("   d.device_type as devicetype,  ");	  //平台
			}
			if(model.isChannelgroupChecked()){
				sql.append("  d.channel_group as  channelgroup,  ");	 //渠道分组		
			}
			if(model.isChannellevelChecked()){
				sql.append("  d.channel_level as  channellevel,  ");	//渠道分级
			}
			if(model.isChannelChecked()){
				sql.append("  d.channel as  channel,  ");	 //渠道
			}
			if(model.isUtmsourceChecked()){
				sql.append("  d.utm_source as  utmsource,  ");	//来源
			}
			if(model.isUtmmediumChecked()){
				sql.append("  d.utm_medium as  utmmedium,  ");	 //媒件
			}
			if(model.isUtmcampaignChecked()){
				sql.append("  d.utm_campaign as  utmcampaign,  ");	//系列
			}
			if(model.isUtmcontentChecked()){
				sql.append("  d.utm_content as  utmcontent,  ");	//组
			}
			if(model.isUtmtermChecked()){
				sql.append("  d.utm_term as  utmterm,  ");	//关键字
			}
			
			sql.append(" count(distinct d.account_no) as accountactivecnt, "); //激活账户数
			sql.append(" sum(a.deposit_amt) as depositamt,  "); //总存款
			sql.append(" sum(a.withdraw_amt) as withdrawamt,  ");  //总取款			
			sql.append(" sum(deposit_amt-withdraw_amt) as cleandeposit,   "); //净入金
			sql.append(" sum(a.open_volume) as openvolume,   "); //开仓手数
			sql.append(" sum(a.close_volume) as closevolume,    ");  //平仓手数	
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.deposit_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgdepositamt,    ");  //人均存款
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.withdraw_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgwithdrawamt,     ");  //人均取款	
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.deposit_amt-a.withdraw_amt)/count(distinct d.account_no)  as decimal(15,2)) || '' else '-' end as avgcleandeposit,    ");  //人均净入金
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.open_volume)/count(distinct d.account_no) as decimal(15,2)) || '' else '-' end as avgopenvolume,   ");  //人均开仓手数
			sql.append(" case when count(distinct d.account_no) != 0 then cast(sum(a.close_volume)/count(distinct d.account_no) as decimal(15,2)) || '' else '-' end as avgclosevolume    ");  //人均平仓手数
			
			sql.append(" from rds.dim_account d join rds.fact_trans_agg_account a  ");
			sql.append(" on d.account_no=a.account_no and d.company_id=a.company_id and d.platform=a.platform  ");
			sql.append(" WHERE 1 = 1 ");
			sql.append(" and d.is_test=0 ");	
			sql.append(" and d.account_type='直客' ");
			if(null != model){
				if (StringUtils.isNotBlank(model.getCompanyid())) {
					sql.append(" and d.company_id = ? ");
					paramList.add(model.getCompanyid());
				}
				
				if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
					sql.append(" and d.business_platform = ? ");
					paramList.add(model.getBusinessPlatformInt());
				}	
					
				if (StringUtils.isNotBlank(model.getDeviceType())) {
					sql.append(" and d.device_type = ? ");
					paramList.add(model.getDeviceType());
				}
				
				if (StringUtils.isNotBlank(model.getChannelgroup()) && !"null".equals(model.getChannelgroup())) {
					sql.append(" and d.channel_group in ( ");
					String channelgroup = model.getChannelgroup();
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
				
				if (StringUtils.isNotBlank(model.getChannellevel()) && !"null".equals(model.getChannellevel())) {
					sql.append(" and d.channel_level in ( ");
					String channellevel = model.getChannellevel();
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
					sql.append(" and d.channel in ( ");
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
				
				if (StringUtils.isNotBlank(model.getUtmsource())) {
					sql.append(" and d.utm_source  like ?  ");
					paramList.add("%" + model.getUtmsource() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmmedium())) {
					sql.append(" and d.utm_medium  like ?  ");
					paramList.add("%" + model.getUtmmedium() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmcampaign())) {
					sql.append(" and d.utm_campaign  like ?  ");
					paramList.add("%" + model.getUtmcampaign() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmcontent())) {
					sql.append(" and d.utm_content  like ?  ");
					paramList.add("%" + model.getUtmcontent() + "%");
				}
				
				if (StringUtils.isNotBlank(model.getUtmterm())) {
					sql.append(" and d.utm_term  like ?  ");
					paramList.add("%" + model.getUtmterm() + "%");
				}					
				
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and date(d.active_time) >= ? ");
					paramList.add(DateUtil.getFirstDayOfMonth(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM")));
					
					sql.append(" and ( a.execdate_rpt is null or a.execdate_rpt >= ? )");
					paramList.add(DateUtil.getFirstDayOfMonth(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM")));
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and date(d.active_time) <= ? ");
					paramList.add(DateUtil.getLastDayOfMonth(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM")));
					
					sql.append(" and ( a.execdate_rpt is null or a.execdate_rpt <= ? ) ");
					paramList.add(DateUtil.getLastDayOfMonth(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM")));
				}
				
				sql.append("group by to_char(d.active_time,'YYYY-MM') ");			
				if(model.isDeviceTypeChecked()){
					sql.append("  ,d.device_type  ");	
				}
				if(model.isChannelgroupChecked()){
					sql.append("  ,d.channel_group  ");		
				}
				if(model.isChannellevelChecked()){
					sql.append("  ,d.channel_level  ");	
				}
				if(model.isChannelChecked()){
					sql.append("  ,d.channel  ");	
				}
				if(model.isUtmsourceChecked()){
					sql.append("  ,d.utm_source  ");	
				}
				if(model.isUtmmediumChecked()){
					sql.append("  ,d.utm_medium ");	
				}
				if(model.isUtmcampaignChecked()){
					sql.append("  ,d.utm_campaign  ");	
				}
				if(model.isUtmcontentChecked()){
					sql.append("  ,d.utm_content ");	
				}
				if(model.isUtmtermChecked()){
					sql.append("  ,d.utm_term ");	
				}
			}
		}
	}
}
