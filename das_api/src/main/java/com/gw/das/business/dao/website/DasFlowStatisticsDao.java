package com.gw.das.business.dao.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.SqlUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.WebSiteReportDao;
import com.gw.das.business.dao.website.entity.DasFlowMonthAverage;
import com.gw.das.business.dao.website.entity.DasFlowStatistics;
import com.gw.das.business.dao.website.entity.DasFlowStatisticsAverage;

@Repository
public class DasFlowStatisticsDao extends WebSiteReportDao {
	
	/**
	 * 不分页查询das_flow_statistics表-行为按（天/客户端）统计
	 */
	public List<DasFlowStatistics> behaviorQueryForList(DasFlowStatistics model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParamByBehavior(sql, model, paramList);
		sql.append(SqlUtil.getOrderField(model.getSortName(), model.getSortDirection()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		return detailList;
	}

	/**
	 * 分页查询das_flow_statistics表-行为按（天/客户端）统计
	 */
	public PageGrid<DasFlowStatistics> behaviorQueryForPage(PageGrid<DasFlowStatistics> pg) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		DasFlowStatistics model = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		this.getPubParamByBehavior(sql, model, paramList);
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		pg.setRows(detailList);
		return pg;
	}
	
	/**
	 * 不分页查询das_flow_statistics表-行为按（时段/客户端）统计
	 * @throws Exception 
	 */
	public List<DasFlowStatistics> timeQueryForList(DasFlowStatistics model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParamByTime(sql, model, paramList);
		sql.append(SqlUtil.getOrderField(model.getSortName(), model.getSortDirection()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		return detailList;
	}
	
	/**
	 * 分页查询das_flow_statistics表-行为按（时段/客户端）统计
	 * @throws Exception 
	 */
	public PageGrid<DasFlowStatistics> timeQueryForPage(PageGrid<DasFlowStatistics> pg) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		DasFlowStatistics model = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		this.getPubParamByTime(sql, model, paramList);
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		pg.setRows(detailList);
		return pg;
	}
	
	/**
	 * 不分页查询das_flow_statistics表
	 * @throws Exception 
	 */
	public List<DasFlowStatistics> mediaQueryForList(DasFlowStatistics model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParamByMedia(sql, model, paramList);
		sql.append(SqlUtil.getOrderField(model.getSortName(), model.getSortDirection()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		return detailList;
	}
	
	/**
	 * 分页查询das_flow_statistics表
	 * @throws Exception 
	 */
	public PageGrid<DasFlowStatistics> mediaQueryForPage(PageGrid<DasFlowStatistics> pg) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		DasFlowStatistics model = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		this.getPubParamByMedia(sql, model, paramList);
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		pg.setRows(detailList);
		return pg;
	}
	
	private void getPubParamByBehavior(StringBuffer sql, DasFlowStatistics model, List<Object> paramList){
		sql.append("select datatime, sum(visitcount) as visitcount, sum(advisorycountlive800) as advisorycountlive800"
				+ ", sum(advisorycountqq) as advisorycountqq, sum(democount) as democount"
				+ ", sum(realcount) as realcount, sum(depositcount) as depositcount");
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(", platformtype");
		}
		sql.append(" from das_flow_statistics_d where 1 = 1 ");
		this.getPubParam(sql, model, paramList);
		sql.append("group by datatime");
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(", platformtype");
		}
	}
	
	private void getPubParamByTime(StringBuffer sql, DasFlowStatistics model, List<Object> paramList){
		sql.append("select formattime, utmcsr, utmcmd"
				+ ", sum(visitcount) as visitcount, sum(advisorycountlive800) as advisorycountlive800"
				+ ", sum(advisorycountqq) as advisorycountqq, sum(democount) as democount"
				+ ", sum(realcount) as realcount, sum(depositcount) as depositcount");
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(", platformtype");
		}
		sql.append(" from das_flow_statistics_h where 1 = 1 ");
		this.getPubParam(sql, model, paramList);
		sql.append("group by formattime, utmcsr, utmcmd");
		if(StringUtils.isNotBlank(model.getPlatformType())){
			sql.append(", platformtype");
		}
	}
	
	private void getPubParamByMedia(StringBuffer sql, DasFlowStatistics model, List<Object> paramList){
		sql.append(" select datatime, sum(visitcount) as visitcount, sum(advisorycountlive800) as advisorycountlive800"
				+ ", sum(advisorycountqq) as advisorycountqq, sum(democount) as democount"
				+ ", sum(realcount) as realcount, sum(depositcount) as depositcount");
		StringBuffer groupBySql = new StringBuffer(" group by datatime");
		// 标示是否都没勾选
		boolean temp = true;
		if(StringUtils.isBlank(model.getStartTimeCompare()) || StringUtils.isBlank(model.getEndTimeCompare())){
			if(model.isUtmcmdChecked()){//勾选媒介
				sql.append(", utmcmd");
				groupBySql.append(", utmcmd");
				temp = false;
			}
			if(model.isUtmcsrChecked()){// 勾选来源
				sql.append(", utmcsr");
				groupBySql.append(", utmcsr");
				temp = false;
			}
		}else{
			sql.append(", utmcmd");
			groupBySql.append(", utmcmd");
			
			sql.append(", utmcsr");
			groupBySql.append(", utmcsr");
			
		}
		
		if(model.isUtmccnChecked()){// 勾选系列
			sql.append(", utmccn");
			groupBySql.append(", utmccn");
			temp = false;
		}
		if(model.isUtmcctChecked()){// 勾选组
			sql.append(", utmcct");
			groupBySql.append(", utmcct");
			temp = false;
		}
		if(model.isUtmctrChecked()){// 勾选关键字
			sql.append(", utmctr");
			groupBySql.append(", utmctr");
			temp = false;
		}
		if(StringUtils.isNotBlank(model.getPlatformType())){// 是否选择客户端
			sql.append(", platformtype");
			groupBySql.append(", platformtype");
		}
		sql.append(" from das_flow_statistics_h where 1 = 1 ");
		this.getPubParam(sql, model, paramList);
		sql.append(groupBySql.toString());
	}
	
	/**
	 * 行为统计按天、时段统计、来源媒介统计公共查询条件
	 * @param sql
	 * @param model
	 * @param paramList
	 */
	private void getPubParam(StringBuffer sql, DasFlowStatistics model, List<Object> paramList){
		if(null != model){
			if(null != model.getUtmcsrList() && model.getUtmcsrList().length >0 && null != model.getUtmcmdList() && model.getUtmcmdList().length >0){
				// 渠道对应的来源及媒介
				String[] utmcsrList = model.getUtmcsrList();
				String[] utmcmdList = model.getUtmcmdList();
				sql.append(" and ( ");
				for (int i = 0; i < utmcsrList.length; i++) {
					String utmcsr = utmcsrList[i];
					String utmcmd = utmcmdList[i];
					if(i+1 == utmcsrList.length){
						sql.append(" (utmcsr = ? and utmcmd= ? ) ");
					}else{
						sql.append(" (utmcsr = ? and utmcmd= ? ) or ");
					}
					
					paramList.add(utmcsr);
					paramList.add(utmcmd);
				}
				sql.append(" ) ");			
			}else{
				// 来源、媒介
				if (StringUtils.isNotBlank(model.getUtmcsr())) {
					sql.append(" and utmcsr like ? ");
					paramList.add("%" + model.getUtmcsr() + "%");
				}
				if (StringUtils.isNotBlank(model.getUtmcmd())) {
					sql.append(" and utmcmd like ? ");
					paramList.add("%" + model.getUtmcmd() + "%");
				}
			}
			// 系列、组、关键字
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
			
			if (StringUtils.isNotBlank(model.getSearchType())) {
				if("behavior".equals(model.getSearchType())){ // "behavior"官网行为统计
					if (null != model.getDataTimeStart()) {
						sql.append(" and dataTime >= ? ");
						sql.append(" and partitionField >= ? ");
						paramList.add(model.getDataTimeStart());
						paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeStart(), "yyyy-MM-dd"), "yyyy_MM"));
					}
					if (null != model.getDataTimeEnd()) {
						sql.append(" and dataTime <= ? ");
						sql.append(" and partitionField <= ? ");
						paramList.add(model.getDataTimeEnd());
						paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeEnd(), "yyyy-MM-dd"), "yyyy_MM"));
					}
				}else if("time".equals(model.getSearchType())){ // "time"官网时段统计
					if (null != model.getDataTimeStart()) {
						sql.append(" and formatTime >= ? ");
						sql.append(" and partitionField >= ? ");
						paramList.add(model.getDataTimeStart());
						paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeStart(), "yyyy-MM-dd"), "yyyy_MM_dd"));
					}
					if (null != model.getDataTimeEnd()) {
						sql.append(" and formatTime <= ? ");
						sql.append(" and partitionField <= ? ");
						paramList.add(model.getDataTimeEnd());
						paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeEnd(), "yyyy-MM-dd"), "yyyy_MM_dd"));
					}
				}else if("media".equals(model.getSearchType())){ // "media"来源媒介统计
					if (null != model.getDataTimeStart()) {
						sql.append(" and dataTime >= ? ");
						sql.append(" and partitionField >= ? ");
						paramList.add(model.getDataTimeStart());
						paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeStart(), "yyyy-MM-dd"), "yyyy_MM_dd"));
					}
					if (null != model.getDataTimeEnd()) {
						sql.append(" and dataTime <= ? ");
						sql.append(" and partitionField <= ? ");
						paramList.add(model.getDataTimeEnd());
						paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeEnd(), "yyyy-MM-dd"), "yyyy_MM_dd"));
					}
				}
			}else{
				// 官网报表-按小时求和
				if (null != model.getDataTimeStart()) {
					sql.append(" and dataTime >= ? ");
					sql.append(" and partitionField >= ? ");
					paramList.add(model.getDataTimeStart());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeStart(), "yyyy-MM-dd"), "yyyy_MM_dd"));
				}
				if (null != model.getDataTimeEnd()) {
					sql.append(" and dataTime <= ? ");
					sql.append(" and partitionField <= ? ");
					paramList.add(model.getDataTimeEnd());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeEnd(), "yyyy-MM-dd"), "yyyy_MM_dd"));
				}
			}
			
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType());
			}
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
		}
	}
	
	/**
	 * 不分页查询das_flow_statistics表-行为按（小时数/客户端）统计
	 * @throws Exception 
	 */
	public List<DasFlowStatistics> statisticsHoursSumData(DasFlowStatistics model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select hours, sum(visitcount) as visitcount, sum(advisorycountlive800) as advisorycountlive800"
				+ ", sum(advisorycountqq) as advisorycountqq, sum(democount) as democount"
				+ ", sum(realcount) as realcount, sum(depositcount) as depositcount"
				+ " from das_flow_statistics_h where 1 = 1 ");
		this.getPubParam(sql, model, paramList);
		sql.append(" group by hours ");
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		return detailList;
	}
	
	/**
	 * 来源媒件环比分页查询
	 * @throws Exception 
	 */
	public List<DasFlowStatistics> mediaQueryForTreeList(DasFlowStatistics model) throws Exception{		
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();		
		sql.append(" select utmcmd, utmcsr,sum(visitcount) as visitcount ,"+
					"sum(advisorycountlive800) as advisorycountlive800,sum(advisorycountqq) as advisorycountqq," +
					"sum(democount) as democount,sum(realcount) as realcount,sum(depositcount) as depositcount  from das_flow_statistics_h where 1 = 1 ");
		this.getTreePubParamList(sql, model, paramList);
		sql.append(" group by utmcmd,utmcsr ");	
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		return detailList;		
	}
	
	private void getTreePubParamList(StringBuffer sql, DasFlowStatistics model, List<Object> paramList){
		if(null != model){
			
			/*if(null != model.getUtmcsrList() && model.getUtmcsrList().length >0 && null != model.getUtmcmdList() && model.getUtmcmdList().length >0){
				String[] utmcsrList = model.getUtmcsrList();
				String[] utmcmdList = model.getUtmcmdList();
				sql.append(" and ( ");
				for (int i = 0; i < utmcsrList.length; i++) {
					String utmcsr = utmcsrList[i];
					String utmcmd = utmcmdList[i];
					if(i+1 == utmcsrList.length){
						sql.append(" (utmcsr = ? and utmcmd= ? ) ");
					}else{
						sql.append(" (utmcsr = ? and utmcmd= ? ) or ");
					}
					
					paramList.add(utmcsr);
					paramList.add(utmcmd);
				}
				sql.append(" ) ");			
			}else{*/
				if (StringUtils.isNotBlank(model.getUtmcsr())) {
					sql.append(" and utmcsr like ? ");
					paramList.add("%" + model.getUtmcsr() + "%");
				}
				if (StringUtils.isNotBlank(model.getUtmcmd())) {
					sql.append(" and utmcmd like ? ");
					paramList.add("%" + model.getUtmcmd() + "%");
				}
			//}
			
			if (null != model.getDataTimeStart()) {
				sql.append(" and dataTime >= ? ");
				sql.append(" and partitionField >= ? ");
				paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeStart(), "yyyy-MM-dd"), "yyyy-MM-dd"));
				paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeStart(), "yyyy-MM-dd"), "yyyy_MM_dd"));
			}
			if (null != model.getDataTimeEnd()) {
				sql.append(" and dataTime <= ? ");
				sql.append(" and partitionField <= ? ");
				paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeEnd(), "yyyy-MM-dd"), "yyyy-MM-dd"));
				paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getDataTimeEnd(), "yyyy-MM-dd"), "yyyy_MM_dd"));
			}
			
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType());
			}
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
		}
	}
	
	/**
	 * 分页查询das_flow_statistics表
	 * @throws Exception 
	 */
	public PageGrid<DasFlowStatistics> mediaQueryForTreePage(PageGrid<DasFlowStatistics> pg) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		DasFlowStatistics model = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		sql.append(" select das.utmcmd, das.utmcsr,das.businessPlatform from  ");
		sql.append(" ( select utmcmd,utmcsr,dataTime,businessPlatform  from das_flow_statistics_h where 1 = 1 ");
		this.getTreePubParamPage(sql, model, paramList);
		sql.append(" order by dataTime desc");
		sql.append(" ) das where 1 = 1 and das.dataTime between ? and ? ");
		paramList.add(model.getDataTimeStart());
		paramList.add(model.getDataTimeEnd());
		sql.append(" or das.dataTime between ? and ? ");
		paramList.add(model.getStartTimeCompare());
		paramList.add(model.getEndTimeCompare());
		sql.append(" group by das.businessPlatform, das.utmcmd,das.utmcsr ");
		pg.setSort("businessPlatform");
		pg.setSort(pg.getSort());
		pg.setOrder(pg.getOrder());
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<DasFlowStatistics> detailList = OrmUtil.reflectList(DasFlowStatistics.class, list);
		pg.setRows(detailList);
		return pg;		
	}
	
	private void getTreePubParamPage(StringBuffer sql, DasFlowStatistics model, List<Object> paramList){
		if(null != model){
			
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType());
			}
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			
			if(null != model.getUtmcsrList() && model.getUtmcsrList().length >0 && null != model.getUtmcmdList() && model.getUtmcmdList().length >0){
				String[] utmcsrList = model.getUtmcsrList();
				String[] utmcmdList = model.getUtmcmdList();
				sql.append(" and ( ");
				for (int i = 0; i < utmcsrList.length; i++) {
					String utmcsr = utmcsrList[i];
					String utmcmd = utmcmdList[i];
					if(i+1 == utmcsrList.length){
						sql.append(" (utmcsr = ? and utmcmd= ? ) ");
					}else{
						sql.append(" (utmcsr = ? and utmcmd= ? ) or ");
					}
					
					paramList.add(utmcsr);
					paramList.add(utmcmd);
				}
				sql.append(" ) ");			
			}else{
				if (StringUtils.isNotBlank(model.getUtmcsr())) {
					sql.append(" and utmcsr like ? ");
					paramList.add("%" + model.getUtmcsr() + "%");
				}
				if (StringUtils.isNotBlank(model.getUtmcmd())) {
					sql.append(" and utmcmd like ? ");
					paramList.add("%" + model.getUtmcmd() + "%");
				}
			}
		}
	}
	
	/**
	 * 不分页查询das_flow_statistics表和das_flow_month_average表
	 */
	public List<DasFlowStatisticsAverage> statisticsAverageQueryForList(DasFlowStatisticsAverage model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		List<DasFlowStatisticsAverage> detailList = new ArrayList<DasFlowStatisticsAverage>();
		
		if(model.getBehaviorType().equals("3")){
			sql.append(" select dataTime,utmcmd,sum(democount) as accountCount,businessplatform " +
					"from  das_flow_statistics_h  where 1 = 1 and democount>=1");
			
			sql.append(" and dataTime = ? ");
			paramList.add(model.getDataTime());
			
			sql.append(" and partitionField = ? ");
			paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()), "yyyy_MM_dd"));
			
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType()+"");
			}
			sql.append(" group by dataTime,utmcmd,businessplatform");
			sql.append(" order by dataTime desc");
			
			List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
			detailList = OrmUtil.reflectList(DasFlowStatisticsAverage.class, list);
			if(detailList.size() > 0){
				StringBuffer sql2 = new StringBuffer();
				List<Object> paramList2 = new ArrayList<Object>();
				
				sql2.append(" select dataTime,utmcmd,sum(demoAvgCount) as demoAvgCount,businessplatform " +
						"from  das_flow_month_average  where 1 = 1");

				sql2.append(" and dataTime = ? ");
				paramList2.add(DateUtil.formatDateToYYYYMMString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1)));
				
				sql2.append(" and partitionField = ? ");
				paramList2.add(DateUtil.formatDateToString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1), "yyyy_MM"));
				
				sql2.append(" and businessPlatform = ? ");
				paramList2.add(model.getBusinessPlatform()+"");
				
				if(StringUtils.isNotBlank(model.getPlatformType())){
					sql2.append(" and platformType = ? ");
					paramList2.add(model.getPlatformType()+"");
				}
				
				for (int i = 0; i < detailList.size(); i++) {
					DasFlowStatisticsAverage dasFlowStatisticsAverage = detailList.get(i);
					if(i==0){
						sql2.append(" and ( ");
					}
					
					sql2.append(" utmcmd = ? ");
					
					if(i != (detailList.size() -1)){
						sql2.append("  or ");
					}
					if(i == (detailList.size() -1)){
						sql2.append(" ) ");
					}
					
					paramList2.add(dasFlowStatisticsAverage.getUtmcmd());
				}
				
				sql2.append(" group by dataTime,utmcmd,businessplatform");
				
				List<Map<String, Object>> list2 = super.queryForList(sql2.toString(), paramList2);
				List<DasFlowMonthAverage> dasFlowMonthAverageList = OrmUtil.reflectList(DasFlowMonthAverage.class, list2);
				
				for (DasFlowStatisticsAverage dasFlowStatisticsAverage : detailList) {
					for (DasFlowMonthAverage dasFlowMonthAverage : dasFlowMonthAverageList) {
						if(dasFlowStatisticsAverage.getUtmcmd().equals(dasFlowMonthAverage.getUtmcmd())){
							dasFlowStatisticsAverage.setLastMonthAvgCount(dasFlowMonthAverage.getDemoAvgCount());
						}
					}
				}
			}
		}else if(model.getBehaviorType().equals("4")){
			sql.append(" select dataTime,utmcmd,sum(realcount) as accountCount,businessplatform " +
					"from  das_flow_statistics_h  where 1 = 1 and realcount>=1");
			
			sql.append(" and dataTime = ? ");
			paramList.add(model.getDataTime());
			
			sql.append(" and partitionField = ? ");
			paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()), "yyyy_MM_dd"));
			
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType()+"");
			}
			
			sql.append(" group by dataTime,utmcmd,businessplatform");
			sql.append(" order by dataTime desc");
			
			List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
			detailList = OrmUtil.reflectList(DasFlowStatisticsAverage.class, list);
			if(detailList.size() > 0){
				StringBuffer sql2 = new StringBuffer();
				List<Object> paramList2 = new ArrayList<Object>();
				
				sql2.append(" select dataTime,utmcmd,sum(realAvgCount) as realAvgCount,businessplatform " +
						"from  das_flow_month_average  where 1 = 1");

				sql2.append(" and dataTime = ? ");
				paramList2.add(DateUtil.formatDateToYYYYMMString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1)));
				
				sql2.append(" and partitionField = ? ");
				paramList2.add(DateUtil.formatDateToString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1), "yyyy_MM"));
				
				sql2.append(" and businessPlatform = ? ");
				paramList2.add(model.getBusinessPlatform()+"");
				
				if(StringUtils.isNotBlank(model.getPlatformType())){
					sql2.append(" and platformType = ? ");
					paramList2.add(model.getPlatformType()+"");
				}
				
				for (int i = 0; i < detailList.size(); i++) {
					DasFlowStatisticsAverage dasFlowStatisticsAverage = detailList.get(i);
					if(i==0){
						sql2.append(" and ( ");
					}
					
					sql2.append(" utmcmd = ? ");
					
					if(i != (detailList.size() -1)){
						sql2.append("  or ");
					}
					if(i == (detailList.size() -1)){
						sql2.append(" ) ");
					}
					
					paramList2.add(dasFlowStatisticsAverage.getUtmcmd());
				}
				
				sql2.append(" group by dataTime,utmcmd,businessplatform");
				
				List<Map<String, Object>> list2 = super.queryForList(sql2.toString(), paramList2);
				List<DasFlowMonthAverage> dasFlowMonthAverageList = OrmUtil.reflectList(DasFlowMonthAverage.class, list2);
				
				for (DasFlowStatisticsAverage dasFlowStatisticsAverage : detailList) {
					for (DasFlowMonthAverage dasFlowMonthAverage : dasFlowMonthAverageList) {
						if(dasFlowStatisticsAverage.getUtmcmd().equals(dasFlowMonthAverage.getUtmcmd())){
							dasFlowStatisticsAverage.setLastMonthAvgCount(dasFlowMonthAverage.getRealAvgCount());
						}
					}
				}
			}
		}else if(model.getBehaviorType().equals("5")){
			sql.append(" select dataTime,utmcmd,sum(depositcount) as accountCount,businessplatform " +
					"from  das_flow_statistics_h  where 1 = 1 and depositcount>=1");
			
			sql.append(" and dataTime = ? ");
			paramList.add(model.getDataTime());
			
			sql.append(" and partitionField = ? ");
			paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()), "yyyy_MM_dd"));
			
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType()+"");
			}
			
			sql.append(" group by dataTime,utmcmd,businessplatform");
			sql.append(" order by dataTime desc");
			
			List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
			detailList = OrmUtil.reflectList(DasFlowStatisticsAverage.class, list);
			if(detailList.size() > 0){
				StringBuffer sql2 = new StringBuffer();
				List<Object> paramList2 = new ArrayList<Object>();
				
				sql2.append(" select dataTime,utmcmd,sum(depositAvgCount) as depositAvgCount,businessplatform " +
						"from  das_flow_month_average  where 1 = 1");

				sql2.append(" and dataTime = ? ");
				paramList2.add(DateUtil.formatDateToYYYYMMString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1)));
				
				sql2.append(" and partitionField = ? ");
				paramList2.add(DateUtil.formatDateToString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1), "yyyy_MM"));
				
				sql2.append(" and businessPlatform = ? ");
				paramList2.add(model.getBusinessPlatform()+"");
				
				if(StringUtils.isNotBlank(model.getPlatformType())){
					sql2.append(" and platformType = ? ");
					paramList2.add(model.getPlatformType()+"");
				}
				
				for (int i = 0; i < detailList.size(); i++) {
					DasFlowStatisticsAverage dasFlowStatisticsAverage = detailList.get(i);
					if(i==0){
						sql2.append(" and ( ");
					}
					
					sql2.append(" utmcmd = ? ");
					
					if(i != (detailList.size() -1)){
						sql2.append("  or ");
					}
					if(i == (detailList.size() -1)){
						sql2.append(" ) ");
					}
					
					paramList2.add(dasFlowStatisticsAverage.getUtmcmd());
				}
				
				sql2.append(" group by dataTime,utmcmd,businessplatform");
				
				List<Map<String, Object>> list2 = super.queryForList(sql2.toString(), paramList2);
				List<DasFlowMonthAverage> dasFlowMonthAverageList = OrmUtil.reflectList(DasFlowMonthAverage.class, list2);
				
				for (DasFlowStatisticsAverage dasFlowStatisticsAverage : detailList) {
					for (DasFlowMonthAverage dasFlowMonthAverage : dasFlowMonthAverageList) {
						if(dasFlowStatisticsAverage.getUtmcmd().equals(dasFlowMonthAverage.getUtmcmd())){
							dasFlowStatisticsAverage.setLastMonthAvgCount(dasFlowMonthAverage.getDepositAvgCount());
						}
					}
				}
			}
		}
		return detailList;
	}
	/**
	 * 分页查询das_flow_statistics表和das_flow_month_average表
	 */
	public PageGrid<DasFlowStatisticsAverage> statisticsAverageQueryForPage(PageGrid<DasFlowStatisticsAverage> pg) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		DasFlowStatisticsAverage model = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		if(model.getBehaviorType().equals("3")){
			sql.append(" select dataTime,utmcmd,sum(democount) as accountCount,businessplatform " +
					"from  das_flow_statistics_h  where 1 = 1 and democount>=1");
			
			sql.append(" and dataTime = ? ");
			paramList.add(model.getDataTime());
			
			sql.append(" and partitionField = ? ");
			paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()), "yyyy_MM_dd"));
			
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType()+"");
			}
			sql.append(" group by dataTime,utmcmd,businessplatform");
			
			pg.setSort(pg.getSort());
			pg.setOrder(pg.getOrder());
			pg = super.queryForListPage(pg, sql.toString(), paramList);
			List<Map<String, Object>> list = pg.getRows();
			List<DasFlowStatisticsAverage> detailList = OrmUtil.reflectList(DasFlowStatisticsAverage.class, list);
			if(detailList.size() > 0){
				StringBuffer sql2 = new StringBuffer();
				List<Object> paramList2 = new ArrayList<Object>();
				
				sql2.append(" select dataTime,utmcmd,sum(demoAvgCount) as demoAvgCount,businessplatform " +
						"from  das_flow_month_average  where 1 = 1");

				sql2.append(" and dataTime = ? ");
				paramList2.add(DateUtil.formatDateToYYYYMMString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1)));
				
				sql2.append(" and partitionField = ? ");
				paramList2.add(DateUtil.formatDateToString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1), "yyyy_MM"));
				
				sql2.append(" and businessPlatform = ? ");
				paramList2.add(model.getBusinessPlatform()+"");
				
				if(StringUtils.isNotBlank(model.getPlatformType())){
					sql2.append(" and platformType = ? ");
					paramList2.add(model.getPlatformType()+"");
				}
				
				for (int i = 0; i < detailList.size(); i++) {
					DasFlowStatisticsAverage dasFlowStatisticsAverage = detailList.get(i);
					if(i==0){
						sql2.append(" and ( ");
					}
					
					sql2.append(" utmcmd = ? ");
					
					if(i != (detailList.size() -1)){
						sql2.append("  or ");
					}
					if(i == (detailList.size() -1)){
						sql2.append(" ) ");
					}
					
					paramList2.add(dasFlowStatisticsAverage.getUtmcmd());
				}
				
				sql2.append(" group by dataTime,utmcmd,businessplatform");
				
				List<Map<String, Object>> list2 = super.queryForList(sql2.toString(), paramList2);
				List<DasFlowMonthAverage> dasFlowMonthAverageList = OrmUtil.reflectList(DasFlowMonthAverage.class, list2);
				
				for (DasFlowStatisticsAverage dasFlowStatisticsAverage : detailList) {
					for (DasFlowMonthAverage dasFlowMonthAverage : dasFlowMonthAverageList) {
						if(dasFlowStatisticsAverage.getUtmcmd().equals(dasFlowMonthAverage.getUtmcmd())){
							dasFlowStatisticsAverage.setLastMonthAvgCount(dasFlowMonthAverage.getDemoAvgCount());
						}
					}
				}
			}
			
			pg.setRows(detailList);
		}else if(model.getBehaviorType().equals("4")){
			sql.append(" select dataTime,utmcmd,sum(realcount) as accountCount,businessplatform " +
					"from  das_flow_statistics_h  where 1 = 1 and realcount>=1");
			
			sql.append(" and dataTime = ? ");
			paramList.add(model.getDataTime());
			
			sql.append(" and partitionField = ? ");
			paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()), "yyyy_MM_dd"));
			
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType()+"");
			}
			sql.append(" group by dataTime,utmcmd,businessplatform");
			
			pg.setSort(pg.getSort());
			pg.setOrder(pg.getOrder());
			pg = super.queryForListPage(pg, sql.toString(), paramList);
			List<Map<String, Object>> list = pg.getRows();
			List<DasFlowStatisticsAverage> detailList = OrmUtil.reflectList(DasFlowStatisticsAverage.class, list);
			if(detailList.size() > 0){
				StringBuffer sql2 = new StringBuffer();
				List<Object> paramList2 = new ArrayList<Object>();
				
				sql2.append(" select dataTime,utmcmd,sum(realAvgCount) as realAvgCount,businessplatform " +
						"from  das_flow_month_average  where 1 = 1");

				sql2.append(" and dataTime = ? ");
				paramList2.add(DateUtil.formatDateToYYYYMMString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1)));
				
				sql2.append(" and partitionField = ? ");
				paramList2.add(DateUtil.formatDateToString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1), "yyyy_MM"));
				
				sql2.append(" and businessPlatform = ? ");
				paramList2.add(model.getBusinessPlatform()+"");
				
				if(StringUtils.isNotBlank(model.getPlatformType())){
					sql2.append(" and platformType = ? ");
					paramList2.add(model.getPlatformType()+"");
				}
				
				for (int i = 0; i < detailList.size(); i++) {
					DasFlowStatisticsAverage dasFlowStatisticsAverage = detailList.get(i);
					if(i==0){
						sql2.append(" and ( ");
					}
					
					sql2.append(" utmcmd = ? ");
					
					if(i != (detailList.size() -1)){
						sql2.append("  or ");
					}
					if(i == (detailList.size() -1)){
						sql2.append(" ) ");
					}
					
					paramList2.add(dasFlowStatisticsAverage.getUtmcmd());
				}
				
				sql2.append(" group by dataTime,utmcmd,businessplatform");
				
				List<Map<String, Object>> list2 = super.queryForList(sql2.toString(), paramList2);
				List<DasFlowMonthAverage> dasFlowMonthAverageList = OrmUtil.reflectList(DasFlowMonthAverage.class, list2);
				
				for (DasFlowStatisticsAverage dasFlowStatisticsAverage : detailList) {
					for (DasFlowMonthAverage dasFlowMonthAverage : dasFlowMonthAverageList) {
						if(dasFlowStatisticsAverage.getUtmcmd().equals(dasFlowMonthAverage.getUtmcmd())){
							dasFlowStatisticsAverage.setLastMonthAvgCount(dasFlowMonthAverage.getRealAvgCount());
						}
					}
				}
			}
			
			pg.setRows(detailList);
		}else if(model.getBehaviorType().equals("5")){
			sql.append(" select dataTime,utmcmd,sum(depositcount) as accountCount,businessplatform " +
					"from  das_flow_statistics_h  where 1 = 1 and depositcount>=1");
			
			sql.append(" and dataTime = ? ");
			paramList.add(model.getDataTime());
			
			sql.append(" and partitionField = ? ");
			paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()), "yyyy_MM_dd"));
			
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
			
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType()+"");
			}
			sql.append(" group by dataTime,utmcmd,businessplatform");
			
			pg.setSort(pg.getSort());
			pg.setOrder(pg.getOrder());
			pg = super.queryForListPage(pg, sql.toString(), paramList);
			List<Map<String, Object>> list = pg.getRows();
			List<DasFlowStatisticsAverage> detailList = OrmUtil.reflectList(DasFlowStatisticsAverage.class, list);
			if(detailList.size() > 0){
				StringBuffer sql2 = new StringBuffer();
				List<Object> paramList2 = new ArrayList<Object>();
				
				sql2.append(" select dataTime,utmcmd,sum(depositAvgCount) as depositAvgCount,businessplatform " +
						"from  das_flow_month_average  where 1 = 1");

				sql2.append(" and dataTime = ? ");
				paramList2.add(DateUtil.formatDateToYYYYMMString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1)));
				
				sql2.append(" and partitionField = ? ");
				paramList2.add(DateUtil.formatDateToString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd(model.getDataTime()),-1), "yyyy_MM"));
				
				sql2.append(" and businessPlatform = ? ");
				paramList2.add(model.getBusinessPlatform()+"");
				
				if(StringUtils.isNotBlank(model.getPlatformType())){
					sql2.append(" and platformType = ? ");
					paramList2.add(model.getPlatformType()+"");
				}
				
				for (int i = 0; i < detailList.size(); i++) {
					DasFlowStatisticsAverage dasFlowStatisticsAverage = detailList.get(i);
					if(i==0){
						sql2.append(" and ( ");
					}
					
					sql2.append(" utmcmd = ? ");
					
					if(i != (detailList.size() -1)){
						sql2.append("  or ");
					}
					if(i == (detailList.size() -1)){
						sql2.append(" ) ");
					}
					
					paramList2.add(dasFlowStatisticsAverage.getUtmcmd());
				}
				
				sql2.append(" group by dataTime,utmcmd,businessplatform");
				
				List<Map<String, Object>> list2 = super.queryForList(sql2.toString(), paramList2);
				List<DasFlowMonthAverage> dasFlowMonthAverageList = OrmUtil.reflectList(DasFlowMonthAverage.class, list2);
				
				for (DasFlowStatisticsAverage dasFlowStatisticsAverage : detailList) {
					for (DasFlowMonthAverage dasFlowMonthAverage : dasFlowMonthAverageList) {
						if(dasFlowStatisticsAverage.getUtmcmd().equals(dasFlowMonthAverage.getUtmcmd())){
							dasFlowStatisticsAverage.setLastMonthAvgCount(dasFlowMonthAverage.getDepositAvgCount());
						}
					}
				}
			}
			
			pg.setRows(detailList);
		}
		
		return pg;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.formatDateToString(DateUtil.addMonths(DateUtil.getDateFromStrByyyyMMdd("2016-10-12"),-1),"yyyy-MM"));
	}

	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T searchModel, List<Object> paramList) {
		
	}
	
}
