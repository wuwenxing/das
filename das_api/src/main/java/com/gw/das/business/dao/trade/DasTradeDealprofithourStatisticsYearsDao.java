package com.gw.das.business.dao.trade;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;

/**
 * 公司盈亏
 */
@Repository
public class DasTradeDealprofithourStatisticsYearsDao extends TradeSiteReportDao {
	
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		
	}
	
}
