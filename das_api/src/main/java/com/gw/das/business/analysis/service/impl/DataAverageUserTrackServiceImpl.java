package com.gw.das.business.analysis.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.business.analysis.service.IDataAverageUserTrackService;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.MathUtils;
import com.gw.das.business.dao.base.ImpalaDao;
import com.gw.das.business.dao.website.entity.DasFlowMonthAverage;
import com.gw.das.business.dao.website.entity.DasFlowStatistics;

/**
 * 月平均统计实现类
 * @author kirin.guan
 *
 */
@Service
public class DataAverageUserTrackServiceImpl implements IDataAverageUserTrackService {
	@Autowired
	private ImpalaDao impalaDao;
	
	@Override
	public Map<String, DasFlowMonthAverage> calculateAverageData(String year,String month) throws Exception{
		double maxDay = DateUtil.getMaxDay(Integer.parseInt(year), Integer.parseInt(month));
		StringBuffer sql = new StringBuffer();
		sql.append(" select  dataTime,utmcmd,utmcsr,platformtype,businessplatform,sum(visitcount) as visitcount," +
				"sum(advisorycountlive800) as advisorycountlive800,sum(advisorycountqq) as advisorycountqq," +
				"sum(democount) as democount,sum(realcount) as realcount,sum(depositcount) as depositcount " +
				"from   das_flow_statistics  where 1 = 1 ");
		sql.append(" and dataTime >= '" +  year + "-" + month + "-01'");
		sql.append(" and dataTime <= '" +  year + "-" + month + "-" + DateUtil.getMaxDay(Integer.parseInt(year), Integer.parseInt(month)) + "'");
		sql.append(" and partitionField >= '" +  year + "-" + month+"'");
		sql.append(" and partitionField <= '" +  year + "-" + month+"'");
		sql.append("  group by dataTime,utmcmd,utmcsr,platformtype,businessplatform");
		
		List<Map<String, Object>> list = impalaDao.queryForList(sql.toString());
		List<DasFlowStatistics> result = OrmUtil.reflectList(DasFlowStatistics.class, list);
		//返回结果集
		Map<String,DasFlowMonthAverage> resultMap = new HashMap<String, DasFlowMonthAverage>();
		
		for (DasFlowStatistics dasFlowStatistics : result) {
			String str[] = dasFlowStatistics.getDataTime().split("-");
			String key = dasFlowStatistics.getUtmcmd() + "_" + dasFlowStatistics.getUtmcsr() + "_" +
						 str[0] + "-" + str[1] + "_" + dasFlowStatistics.getBusinessPlatform() + "_" + 
						 dasFlowStatistics.getPlatformType();
			
			DasFlowMonthAverage dasFlowMonthAverage = resultMap.get(key);
			
			if(dasFlowMonthAverage == null){
				dasFlowMonthAverage = new DasFlowMonthAverage();
				dasFlowMonthAverage.setDataTime(str[0] + "-" + str[1]);
				dasFlowMonthAverage.setFormatTime(dasFlowStatistics.getDataTime());
				dasFlowMonthAverage.setPlatformType(dasFlowStatistics.getPlatformType());
				dasFlowMonthAverage.setBusinessPlatform(dasFlowStatistics.getBusinessPlatform());
				dasFlowMonthAverage.setUtmcmd(dasFlowStatistics.getUtmcmd());
				dasFlowMonthAverage.setUtmcsr(dasFlowStatistics.getUtmcsr());
				
				dasFlowMonthAverage.setAdvisoryQQAvgCount(MathUtils.numberFormat(dasFlowStatistics.getAdvisoryCountQQ()/maxDay, 2));
				dasFlowMonthAverage.setAdvisoryLive800AvgCount(MathUtils.numberFormat(dasFlowStatistics.getAdvisoryCountLIVE800()/maxDay, 2));
				dasFlowMonthAverage.setDemoAvgCount(MathUtils.numberFormat(dasFlowStatistics.getDemoCount()/maxDay, 2));
				dasFlowMonthAverage.setDepositAvgCount(MathUtils.numberFormat(dasFlowStatistics.getDepositCount()/maxDay, 2));
				dasFlowMonthAverage.setRealAvgCount(MathUtils.numberFormat(dasFlowStatistics.getRealCount()/maxDay, 2));
				dasFlowMonthAverage.setVisitAvgCount(MathUtils.numberFormat(dasFlowStatistics.getVisitCount()/maxDay, 2));
				
			}else{
				dasFlowMonthAverage.setAdvisoryQQAvgCount(MathUtils.numberFormat(dasFlowMonthAverage.getAdvisoryQQAvgCount() + MathUtils.numberFormat(dasFlowStatistics.getAdvisoryCountQQ()/maxDay, 2),2));
				dasFlowMonthAverage.setAdvisoryLive800AvgCount(MathUtils.numberFormat(dasFlowMonthAverage.getAdvisoryLive800AvgCount() + MathUtils.numberFormat(dasFlowStatistics.getAdvisoryCountLIVE800()/maxDay, 2),2));
				dasFlowMonthAverage.setDemoAvgCount(MathUtils.numberFormat(dasFlowMonthAverage.getDemoAvgCount() + MathUtils.numberFormat(dasFlowStatistics.getDemoCount()/maxDay, 2),2));
				dasFlowMonthAverage.setDepositAvgCount(MathUtils.numberFormat(dasFlowMonthAverage .getDepositAvgCount() + MathUtils.numberFormat(dasFlowStatistics.getDepositCount()/maxDay, 2),2));
				dasFlowMonthAverage.setRealAvgCount(MathUtils.numberFormat(dasFlowMonthAverage.getRealAvgCount() + MathUtils.numberFormat(dasFlowStatistics.getRealCount()/maxDay, 2),2));
				dasFlowMonthAverage.setVisitAvgCount(MathUtils.numberFormat(dasFlowMonthAverage.getVisitAvgCount() + MathUtils.numberFormat(dasFlowStatistics.getVisitCount()/maxDay, 2),2));
			}
			
			resultMap.put(key, dasFlowMonthAverage);
		}
		
		return resultMap;
	}

	@Override
	public List<DasFlowMonthAverage> getSourceAverageDataByTime(String year,String month) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from das_flow_month_average where 1 = 1 ");
		sql.append(" and dataTime >= '" +  year + "-" + month + "'");
		sql.append(" and partitionField >= '" +  year + "-" + month + "'");
		sql.append(" order by dataTime desc");
		
		List<Map<String, Object>> list = impalaDao.queryForList(sql.toString());
		List<DasFlowMonthAverage> detailList = OrmUtil.reflectList(DasFlowMonthAverage.class, list);
		return detailList;
	}

	@Override
	public void insert(DasFlowMonthAverage dasFlowMonthAverage) throws Exception{
		dasFlowMonthAverage.setRowKey(dasFlowMonthAverage.getDataTime() + "_" + dasFlowMonthAverage.getUtmcmd() + "_" + 
				 dasFlowMonthAverage.getUtmcsr() + "_" + dasFlowMonthAverage.getBusinessPlatform() + "_" + dasFlowMonthAverage.getPlatformType());
		
		setData(dasFlowMonthAverage);
	}

	@Override
	public void update(DasFlowMonthAverage dasFlowMonthAverage) throws Exception{
		setData(dasFlowMonthAverage);
	}
	
	/**
	 * 设置数据
	 * @param dasFlowMonthAverage
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	private void setData(DasFlowMonthAverage dasFlowMonthAverage) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into rdd_das_flow_month_average ");
		sql.append(" (");
		sql.append(" rowkey,datatime,utmcmd,utmcsr,businessPlatform,platformType,");
		sql.append(" visitAvgCount,advisoryQQAvgCount,advisoryLive800AvgCount,demoAvgCount,realAvgCount,depositAvgCount");
		sql.append(" ) ");
		sql.append(" values ");
		sql.append(" ( ");
		
		//6
		sql.append("'" + dasFlowMonthAverage.getRowKey() +"',");
		sql.append("'" + dasFlowMonthAverage.getDataTime() +"',");
		sql.append("'" + dasFlowMonthAverage.getUtmcmd() +"',");
		sql.append("'" + dasFlowMonthAverage.getUtmcsr() +"',");
		sql.append("'" + dasFlowMonthAverage.getBusinessPlatform() +"',");
		sql.append("'" + dasFlowMonthAverage.getPlatformType() +"',");
		//6
		sql.append("" + dasFlowMonthAverage.getVisitAvgCount() +",");
		sql.append("" + dasFlowMonthAverage.getAdvisoryQQAvgCount() +",");
		sql.append("" + dasFlowMonthAverage.getAdvisoryLive800AvgCount() +",");
		sql.append("" + dasFlowMonthAverage.getDemoAvgCount() +",");
		sql.append("" + dasFlowMonthAverage.getRealAvgCount() +",");
		sql.append("" + dasFlowMonthAverage.getDepositAvgCount() +"");
		
		sql.append(" ) ");
		impalaDao.insertOrUpdate(sql.toString());
	}
}
