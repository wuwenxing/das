package com.gw.das.business.dao.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.dao.base.ImpalaDao;
import com.gw.das.business.dao.website.entity.DasFlowDetail;
import com.gw.das.business.dao.website.entity.DasFlowDetailSearchBean;
import com.gw.das.business.dao.website.entity.DasFlowDetailUrl;

@Repository
public class DasFlowDetailDao extends ImpalaDao {
	
	/**
	 * 不分页查询das_flow_detail表
	 * @throws Exception 
	 */
	public List<DasFlowDetail> queryForList(DasFlowDetailSearchBean model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, model, paramList);
		sql.append(" order by startTime desc");
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasFlowDetail> detailList = OrmUtil.reflectList(DasFlowDetail.class, list);
		return detailList;
	}
	
	/**
	 * 分页查询das_flow_detail表
	 * @throws Exception 
	 */
	public PageGrid<DasFlowDetailSearchBean> queryForListPage(PageGrid<DasFlowDetailSearchBean> pg) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		DasFlowDetailSearchBean model = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, model, paramList);
		pg.setSort("startTime");
		pg.setOrder("desc");
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<DasFlowDetail> detailList = OrmUtil.reflectList(DasFlowDetail.class, list);
		pg.setRows(detailList);
		return pg;
	}
	
	/**
	 * 分页查询das_flow_url_detail表
	 * @throws Exception 
	 */
	public PageGrid<DasFlowDetailUrl> queryForUrlListPage(PageGrid<DasFlowDetailUrl> pg) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		DasFlowDetailUrl model = pg.getSearchModel();
		sql.append("select rowKey,flowDetailUrl,utmcmd,utmcsr,visitTime,flowDetailId ,platformType " +
				" from das_flow_url_detail where 1 = 1 ");
		if(null != model){
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				sql.append(" and utmcsr like ? ");
				paramList.add("%" + model.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(model.getFlowDetailUrl())) {
				sql.append(" and flowDetailUrl like ? ");
				paramList.add("%" + model.getFlowDetailUrl() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				sql.append(" and utmcmd like ? ");
				paramList.add("%" + model.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(model.getFlowDetailId())) {
				sql.append(" and flowDetailId = ? ");
				paramList.add(model.getFlowDetailId());
			}
			if (null != model.getVisitTimeStart()) {
				sql.append(" and visitTime >= ? ");
				sql.append(" and partitionField >= ? ");
				paramList.add(model.getVisitTimeStart());
				paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(model.getVisitTimeStart())));
			}
			if (null != model.getVisitTimeEnd()) {
				sql.append(" and visitTime <= ? ");
				sql.append(" and partitionField <= ? ");
				paramList.add(model.getVisitTimeEnd());
				paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(model.getVisitTimeEnd())));
			}
			if(StringUtils.isNotBlank(model.getPlatformType())){
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType());
			}
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
		}
		pg.setSort(pg.getSort());
		pg.setOrder(pg.getOrder());
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<DasFlowDetailUrl> detailUrlList = OrmUtil.reflectList(DasFlowDetailUrl.class, list);
		pg.setRows(detailUrlList);
		return pg;
	}
	
	/**
	 * 分页查询das_flow_url_detail表
	 * @throws Exception 
	 */
	public List<DasFlowDetailUrl> queryForUrlList(DasFlowDetailUrl model) throws Exception{
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select rowKey,flowDetailUrl,utmcmd,utmcsr,visitTime,flowDetailId " +
				" from das_flow_url_detail where 1 = 1 ");
		if(null != model){
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				sql.append(" and utmcsr like ? ");
				paramList.add("%" + model.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(model.getFlowDetailUrl())) {
				sql.append(" and flowDetailUrl like ? ");
				paramList.add("%" + model.getFlowDetailUrl() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				sql.append(" and utmcmd like ? ");
				paramList.add("%" + model.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(model.getFlowDetailId())) {
				sql.append(" and flowDetailId = ? ");
				paramList.add(model.getFlowDetailId());
			}
			if (null != model.getVisitTimeStart()) {
				sql.append(" and visitTime >= ? ");
				sql.append(" and partitionField >= ? ");
				paramList.add(model.getVisitTimeStart());
				paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(model.getVisitTimeStart())));
			}
			if (null != model.getVisitTimeEnd()) {
				sql.append(" and visitTime <= ? ");
				sql.append(" and partitionField <= ? ");
				paramList.add(model.getVisitTimeEnd());
				paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStrByyyyMMdd(model.getVisitTimeEnd())));
			}
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform()+"");
		}
		sql.append(" order by visitTime desc");
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<DasFlowDetailUrl> detailUrlList = OrmUtil.reflectList(DasFlowDetailUrl.class, list);
		return detailUrlList;
	}
	
	private void getPubParam(StringBuffer sql, DasFlowDetailSearchBean model, List<Object> paramList){
		sql.append("select rowKey,userId,startTime,timeLength,behaviorDetail,behaviorType,ip," +
				"iphome,utmcsr,utmctr,utmccn,utmcct,utmcmd,visitCount,advisoryType,formatTime,platformtype " +
				" from das_flow_detail where 1 = 1 ");
		if(null != model){
			if (StringUtils.isNotBlank(model.getBehaviorType())) {
				sql.append(" and behaviorType = ? ");
				paramList.add(model.getBehaviorType());
			}
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				sql.append(" and utmcsr like ? ");
				paramList.add("%" + model.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				sql.append(" and utmcmd like ? ");
				paramList.add("%" + model.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(model.getIp())) {
				sql.append(" and ip like ? ");
				paramList.add("%" + model.getIp() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmccn())) {
				sql.append(" and utmccn like ? ");
				paramList.add("%" + model.getUtmccn() + "%");
			}
			if (StringUtils.isNotBlank(model.getUserId())) {
				sql.append(" and userId like ? ");
				paramList.add("%" + model.getUserId().toUpperCase() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmcct())) {
				sql.append(" and utmcct like ? ");
				paramList.add("%" + model.getUtmcct() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmctr())) {
				sql.append(" and utmctr like ? ");
				paramList.add("%" + model.getUtmctr() + "%");
			}
			if (StringUtils.isNotBlank(model.getStartTime())) {
				sql.append(" and startTime >= ? ");
				sql.append(" and partitionField >= ? ");
				paramList.add(model.getStartTime());
				paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStr(model.getStartTime())));
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				sql.append(" and startTime <= ? ");
				sql.append(" and partitionField <= ? ");
				paramList.add(model.getEndTime());
				paramList.add(DateUtil.formatDateToYYYYMMString(DateUtil.getDateFromStr(model.getEndTime())));
			}
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platformType = ? ");
				paramList.add(model.getPlatformType());
			}
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and businessPlatform = ? ");
				paramList.add(model.getBusinessPlatform()+"");
			}
		}
	}
	
}
