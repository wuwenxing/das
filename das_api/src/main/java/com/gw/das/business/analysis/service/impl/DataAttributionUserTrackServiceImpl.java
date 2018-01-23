package com.gw.das.business.analysis.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.business.analysis.service.IDataAttributionUserTrackService;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.dao.base.ImpalaDao;
import com.gw.das.business.dao.website.entity.DasFlowAttribution;

@Service
public class DataAttributionUserTrackServiceImpl implements IDataAttributionUserTrackService{
	@Autowired
	private ImpalaDao impalaDao;
	
	@Override
	public DasFlowAttribution getAttributionByData(
			DasFlowAttribution dasFlowAttribution) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from rrd_das_flow_attribution where 1 = 1 ");
		sql.append(" and dataTime = '" +  dasFlowAttribution.getDataTime() + "'");
		//sql.append(" and partitionField = '" +  DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(dasFlowAttribution.getDataTime())) + "'");
		sql.append(" and utmcsr = '" +  dasFlowAttribution.getUtmcsr() + "'");
		sql.append(" and utmcmd = '" +  dasFlowAttribution.getUtmcmd() + "'");
		sql.append(" and platformType = '" +  dasFlowAttribution.getPlatformType() + "'");
		sql.append(" and businessPlatform = '" +  dasFlowAttribution.getBusinessPlatform() + "'");
		sql.append(" order by dataTime asc");
		sql.append(" limit " + 1 + " offset " + 0);
		
		Map<String, Object> map = impalaDao.queryForMap(sql.toString());
		if(map == null) return null;
		
		DasFlowAttribution model = OrmUtil.reflect(DasFlowAttribution.class, map);
		return model;
	}

	@Override
	public void insertList(List<DasFlowAttribution> list) throws Exception {
		for (DasFlowAttribution dasFlowAttribution : list) {
			dasFlowAttribution.setRowKey(dasFlowAttribution.getDataTime() + "#" + dasFlowAttribution.getUtmcmd() + "#" + 
					 dasFlowAttribution.getUtmcsr() + "#" + dasFlowAttribution.getBusinessPlatform() + "#" + dasFlowAttribution.getPlatformType());
			
			setData(dasFlowAttribution);
		}
	}

	@Override
	public void batchUpdateList(List<DasFlowAttribution> list) throws Exception {
		for (DasFlowAttribution dasFlowAttribution : list) {
			setData(dasFlowAttribution);
		}
	}
	
	/**
	 * 设置数据
	 * @param dasFlowAttribution
	 * @throws Exception
	 */
	private void setData(DasFlowAttribution dasFlowAttribution) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into rdd_das_flow_attribution ");
		sql.append(" (");
		sql.append(" rowkey,datatime,utmcmd,utmcsr,demoCount,realCount,businessPlatform,platformType");
		sql.append(" ) ");
		sql.append(" values ");
		sql.append(" ( ");
		
		//8
		sql.append("'" + dasFlowAttribution.getRowKey() +"',");
		sql.append("'" + dasFlowAttribution.getDataTime() +"',");
		sql.append("'" + dasFlowAttribution.getUtmcmd() +"',");
		sql.append("'" + dasFlowAttribution.getUtmcsr() +"',");
		sql.append("" + dasFlowAttribution.getDemoCount() +",");
		sql.append("" + dasFlowAttribution.getRealCount() +",");
		sql.append("'" + dasFlowAttribution.getBusinessPlatform() +"',");
		sql.append("'" + dasFlowAttribution.getPlatformType() +"'");
		
		sql.append(" ) ");
		
		impalaDao.insertOrUpdate(sql.toString());
	}

}
