package com.gw.das.business.dao.appDataAnalysis;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.dao.appDataAnalysis.entity.AppDataAnalysisSearchModel;
import com.gw.das.business.dao.base.AppSiteReportDao;
import com.gw.das.business.dao.base.BaseSearchModel;

/**
 * Gts2开户来源分析报表Dao
 * 
 * @author Darren
 *
 */
@Repository
public class OpenSourceAnalysisDao extends AppSiteReportDao {

	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		AppDataAnalysisSearchModel model = (AppDataAnalysisSearchModel) t;						
		sql.append("SELECT	row_number() over() as rowkey, * ");
			
		sql.append("  FROM das_web_app_data_analysis_report_d a ");	

		sql.append(" WHERE 1 = 1 ");		
		if(null != model){		
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.dt_day >= ?");
					paramList.add(model.getStartTime());
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and a.dt_day <= ?");
					paramList.add(model.getEndTime());
				}	
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.dt_month >= ?");
					paramList.add(model.getStartTime());
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and a.dt_month <= ?");
					paramList.add(model.getEndTime());
				}	
			}
			
			if (StringUtils.isNotBlank(model.getChannel())) {
				sql.append(" and a.channel = ? ");
				paramList.add(model.getChannel()+"");
			}	
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and a.platformtype = ? ");
				paramList.add(model.getPlatformType());
			}			
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and a.businessplatform = ? ");
				paramList.add(model.getBusinessPlatform()+"");
			}				
		}	
		
	}

}
