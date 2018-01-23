package com.gw.das.business.dao.appDataAnalysis;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.base.AppSiteReportDao;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * APP数据分析报表Dao
 * 
 * @author Darren
 *
 */
@Repository
public class AppDataAnalysisDao extends AppSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		AppDataAnalysisSearchModel model = (AppDataAnalysisSearchModel) t;										
		if(null != model){		
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				sql.append("SELECT	row_number() over() as rowkey,");	
				if(!model.isChannelChecked() && !model.isDevicetypeChecked()){
					sql.append(" dt_day,dt_month, ");					
					sql.append(" SUM (a.activeuser) AS activeuser,");
					sql.append(" SUM (a.newuser) AS newuser,");
					sql.append(" SUM (a.olduser_demo) AS olduser_demo,");
					sql.append(" SUM (a.olduser_real) AS olduser_real,");
					sql.append(" SUM (a.olduser_activate) AS olduser_activate,");
					sql.append(" SUM (a.demo_account) AS demo_account,");
					sql.append(" SUM (a.demo_newuser_login) AS demo_newuser_login,");
					sql.append(" SUM (a.demo_newuser_trade) AS demo_newuser_trade,");
					sql.append(" SUM (a.demo_olduser_login) AS demo_olduser_login,");
					sql.append(" SUM (a.demo_olduser_trade) AS demo_olduser_trade,");
					sql.append(" SUM (a.demo_all_login) AS demo_all_login,");
					sql.append(" SUM (a.demo_all_trade) AS demo_all_trade,");
					sql.append(" SUM (a.demo_newuser_login_rate) AS demo_newuser_login_rate,");
					sql.append(" SUM (a.demo_newuser_trade_rate) AS demo_newuser_trade_rate,");
					sql.append(" SUM (a.demo_olduser_trade_rate) AS demo_olduser_trade_rate,");
					sql.append(" SUM (a.demo_all_trade_rate) AS demo_all_trade_rate,");
					sql.append(" SUM (a.real_account) AS real_account,");
					sql.append(" SUM (a.real_newuser_login) AS real_newuser_login,");
					sql.append(" SUM (a.real_newuser_deposit) AS real_newuser_deposit,");			
					sql.append(" SUM (a.real_newuser_trade) AS real_newuser_trade,");
					sql.append(" SUM (a.real_newuser_login_rate) AS real_newuser_login_rate,");
					sql.append(" SUM (a.real_newuser_deposit_rate) AS real_newuser_deposit_rate,");
					sql.append(" SUM (a.real_newuser_trade_rate) AS real_newuser_trade_rate ");
				}else{
					sql.append("  * ");
				}						
				sql.append("  FROM das_web_app_data_analysis_report_d a WHERE 1 = 1 ");	
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.dt_day >= ?");
					sql.append(" and A.partitionfield >= ? ");
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and a.dt_day <= ?");
					sql.append(" and A.partitionfield <= ? ");
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}	
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
				sql.append("SELECT	row_number() over() as rowkey, ");
				
				if(!model.isChannelChecked() && !model.isDevicetypeChecked()){
					sql.append(" dt_month, ");					
					sql.append(" SUM (a.activeuser) AS activeuser,");
					sql.append(" SUM (a.newuser) AS newuser,");
					sql.append(" SUM (a.olduser_demo) AS olduser_demo,");
					sql.append(" SUM (a.olduser_real) AS olduser_real,");
					sql.append(" SUM (a.olduser_activate) AS olduser_activate,");
					sql.append(" SUM (a.demo_account) AS demo_account,");
					sql.append(" SUM (a.demo_newuser_login) AS demo_newuser_login,");
					sql.append(" SUM (a.demo_newuser_trade) AS demo_newuser_trade,");
					sql.append(" SUM (a.demo_olduser_login) AS demo_olduser_login,");
					sql.append(" SUM (a.demo_olduser_trade) AS demo_olduser_trade,");
					sql.append(" SUM (a.demo_all_login) AS demo_all_login,");
					sql.append(" SUM (a.demo_all_trade) AS demo_all_trade,");
					sql.append(" SUM (a.demo_newuser_login_rate) AS demo_newuser_login_rate,");
					sql.append(" SUM (a.demo_newuser_trade_rate) AS demo_newuser_trade_rate,");
					sql.append(" SUM (a.demo_olduser_trade_rate) AS demo_olduser_trade_rate,");
					sql.append(" SUM (a.demo_all_trade_rate) AS demo_all_trade_rate,");
					sql.append(" SUM (a.real_account) AS real_account,");
					sql.append(" SUM (a.real_newuser_login) AS real_newuser_login,");
					sql.append(" SUM (a.real_newuser_deposit) AS real_newuser_deposit,");			
					sql.append(" SUM (a.real_newuser_trade) AS real_newuser_trade,");
					sql.append(" SUM (a.real_newuser_login_rate) AS real_newuser_login_rate,");
					sql.append(" SUM (a.real_newuser_deposit_rate) AS real_newuser_deposit_rate,");
					sql.append(" SUM (a.real_newuser_trade_rate) AS real_newuser_trade_rate ");
				}else{
					sql.append("  * ");
				}
				
				sql.append("  FROM das_web_app_data_analysis_report_m a WHERE 1 = 1 ");	
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.dt_month >= ?");
					paramList.add(model.getStartTime());
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and a.dt_month <= ?");
					paramList.add(model.getEndTime());
				}	
			}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				sql.append("SELECT	row_number() over() as rowkey, ");
				
				if(!model.isChannelChecked() && !model.isDevicetypeChecked()){
					sql.append(" dt_month,dt_week, ");					
					sql.append(" SUM (a.activeuser) AS activeuser,");
					sql.append(" SUM (a.newuser) AS newuser,");
					sql.append(" SUM (a.olduser_demo) AS olduser_demo,");
					sql.append(" SUM (a.olduser_real) AS olduser_real,");
					sql.append(" SUM (a.olduser_activate) AS olduser_activate,");
					sql.append(" SUM (a.demo_account) AS demo_account,");
					sql.append(" SUM (a.demo_newuser_login) AS demo_newuser_login,");
					sql.append(" SUM (a.demo_newuser_trade) AS demo_newuser_trade,");
					sql.append(" SUM (a.demo_olduser_login) AS demo_olduser_login,");
					sql.append(" SUM (a.demo_olduser_trade) AS demo_olduser_trade,");
					sql.append(" SUM (a.demo_all_login) AS demo_all_login,");
					sql.append(" SUM (a.demo_all_trade) AS demo_all_trade,");
					sql.append(" SUM (a.demo_newuser_login_rate) AS demo_newuser_login_rate,");
					sql.append(" SUM (a.demo_newuser_trade_rate) AS demo_newuser_trade_rate,");
					sql.append(" SUM (a.demo_olduser_trade_rate) AS demo_olduser_trade_rate,");
					sql.append(" SUM (a.demo_all_trade_rate) AS demo_all_trade_rate,");
					sql.append(" SUM (a.real_account) AS real_account,");
					sql.append(" SUM (a.real_newuser_login) AS real_newuser_login,");
					sql.append(" SUM (a.real_newuser_deposit) AS real_newuser_deposit,");			
					sql.append(" SUM (a.real_newuser_trade) AS real_newuser_trade,");
					sql.append(" SUM (a.real_newuser_login_rate) AS real_newuser_login_rate,");
					sql.append(" SUM (a.real_newuser_deposit_rate) AS real_newuser_deposit_rate,");
					sql.append(" SUM (a.real_newuser_trade_rate) AS real_newuser_trade_rate ");
				}else{
					sql.append("  * ");
				}
							
				sql.append("  FROM das_web_app_data_analysis_report_w a WHERE 1 = 1 ");	
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.dt_month >= ?");
					paramList.add(model.getStartTime());
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and a.dt_month <= ?");
					paramList.add(model.getEndTime());
				}	
			}			
			
			if(StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())){
				sql.append(" and a.channel in ( ");
				String channels = model.getChannel();
				String[] channelArray = channels.split(",");			
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
			
			if (StringUtils.isNotBlank(model.getDeviceType())) {
				sql.append(" and a.devicetype = ? ");
				paramList.add(model.getDeviceType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and a.businessplatform = ? ");
				paramList.add(model.getBusinessPlatform()+"");
			}	
			if(!model.isChannelChecked() && !model.isDevicetypeChecked()){
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					sql.append(" GROUP BY a.dt_day,dt_month ");
				}
				
				if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					sql.append(" GROUP BY a.dt_month ");
				}
				
				if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					sql.append(" GROUP BY a.dt_month,a.dt_week ");
				}

			}
			
		}	
		
	}

}
