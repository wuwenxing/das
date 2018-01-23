package com.gw.das.business.dao.website;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.WebSiteReportDao;
import com.gw.das.business.dao.website.entity.DasStatisticsReportSearchModel;
/**
 * 官网统计-日\月
 * @author darren
 *
 */
@Repository
public class DasStatisticsReportDao extends WebSiteReportDao {
	
	@Override	
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		DasStatisticsReportSearchModel model = (DasStatisticsReportSearchModel) t;				
		if("1".equals(model.getTableType())){
			sql.append("SELECT	row_number() over() as rowkey, *");
			if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
				sql.append("  FROM das_web_channel_report_d a ");		
			}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
				sql.append("  FROM das_web_channel_report_avg_m a ");
			}
		}
		if("2".equals(model.getTableType())){
			sql.append("select dt_day,channel, sum(visit) as visit, sum(advisory) as advisory , sum(real) as real, sum(demo) as demo , sum(deposit) as deposit "
					+ ", sum(on_w_visit) as on_w_visit, sum(on_w_advisory) as on_w_advisory , sum(on_w_real) as on_w_real, sum(on_w_demo) as on_w_demo, sum(on_w_deposit) as on_w_deposit "
					+ ", sum(on_m_visit) as on_m_visit, sum(on_m_advisory) as on_m_advisory , sum(on_m_real) as on_m_real, sum(on_m_demo) as on_m_demo, sum(on_m_deposit) as on_m_deposit ");
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(", platformtype "); 
			}
			
			sql.append("  FROM das_web_channel_detail_report_all a ");	
		}
		
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
			
			if("2".equals(model.getTableType())){
				if(StringUtils.isNotBlank(model.getStartTime())){
					sql.append(" and a.dt_day >= ?");
					paramList.add(model.getStartTime());
				}
				if(StringUtils.isNotBlank(model.getEndTime())){
					sql.append(" and a.dt_day <= ?");
					paramList.add(model.getEndTime());
				}
				if (model.getChanneltype() != -1) {
					sql.append(" and a.channeltype = ? ");
					paramList.add(model.getChanneltype()+"");
				}	
				if (StringUtils.isNotBlank(model.getChannel()) && !"null".equals(model.getChannel())) {
					sql.append(" and a.channel = ? ");
					paramList.add(model.getChannel()+"");
				}	
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
		if("2".equals(model.getTableType())){
			sql.append(" group by a.dt_day, a.channel");		
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(", a.platformtype ");
			}
		}
		
	}
	
}
