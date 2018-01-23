package com.gw.das.business.dao.appDataAnalysis;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.base.AppSiteReportDao;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * APP数据分析详情报表Dao
 * 
 * @author Darren
 *
 */
@Repository
public class AppDataAnalysisDetailsDao extends AppSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		AppDataAnalysisSearchModel model = (AppDataAnalysisSearchModel) t;								
		if(null != model){		
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
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
				
				sql.append("  FROM das_web_app_data_analysis_report_mavg a WHERE 1 = 1 ");	
				if(StringUtils.isNotBlank(model.getDtMonth())){
					sql.append(" and a.dt_month = ?");
					paramList.add(model.getDtMonth());
				}					
			}	
			
			if (StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())) {
				sql.append(" and a.channel = ? ");
				paramList.add(model.getChannel()+"");
			}	
			if (StringUtils.isNotBlank(model.getMobiletype()) && !"null".equals(model.getMobiletype())) {
				sql.append(" and a.devicetype = ? ");
				paramList.add(model.getMobiletype());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and a.businessplatform = ? ");
				paramList.add(model.getBusinessPlatform()+"");
			}	
			
			if(!model.isChannelChecked() && !model.isDevicetypeChecked()){
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					sql.append(" GROUP BY a.dt_month ");
				}						
			}
		}	
		
	}

}
