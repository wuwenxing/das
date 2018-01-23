package com.gw.das.business.dao.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.SqlUtil;
import com.gw.das.business.dao.base.BaseModel;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.WebSiteReportDao;
import com.gw.das.business.dao.website.entity.DasWebLandingpageDetailSearchModel;

/**
 * Landingpage
 * 
 * @author wayne
 *
 */
@Repository
public class DasWebLandingpageDetailDao extends WebSiteReportDao {

	/**
	 * 不分页查询
	 * 
	 * @throws Exception
	 */
	public <T extends BaseSearchModel, E extends BaseModel> List<E> queryForList(T searchModel, E e) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, searchModel, paramList);
		sql.append(SqlUtil.getOrderField(searchModel.getSort(), searchModel.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<E> detailList = (List<E>) OrmUtil.reflectList(e.getClass(), list);
		return detailList;
	}

	/**
	 * 分页查询
	 * 
	 * @throws Exception
	 */
	public <T extends BaseSearchModel, E extends BaseModel> PageGrid<T> queryForPage(PageGrid<T> pg, E e)
			throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		BaseSearchModel searchModel = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, searchModel, paramList);
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<E> detailList = (List<E>) OrmUtil.reflectList(e.getClass(), list);
		pg.setRows(detailList);
		return pg;
	}

	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList) {
		DasWebLandingpageDetailSearchModel model = (DasWebLandingpageDetailSearchModel) t;
		
		// 标示是全部勾选
		boolean temp = model.isDeviceTypeChecked()&&model.isUtmcmdChecked()&&model.isUtmcsrChecked()
				&&model.isUtmccnChecked()&&model.isUtmcctChecked()
				&&model.isUtmctrChecked()&&model.isChannelChecked();
		StringBuffer subSql = new StringBuffer();
		StringBuffer groupBySql = new StringBuffer();
		// businessplatform, datatime, landingpage必须分组,其他某些根据勾选情况进行分组
		groupBySql.append(" group by businessplatform, datatime, landingpage");
		if(model.isDeviceTypeChecked()){//勾选设备类型
			subSql.append(", devicetype");
			groupBySql.append(", devicetype");
		}
		if(model.isUtmcmdChecked()){//勾选媒介
			subSql.append(", utmcmd");
			groupBySql.append(", utmcmd");
		}
		if(model.isUtmcsrChecked()){// 勾选来源
			subSql.append(", utmcsr");
			groupBySql.append(", utmcsr");
		}
		if(model.isUtmccnChecked()){// 勾选系列
			subSql.append(", utmccn");
			groupBySql.append(", utmccn");
		}
		if(model.isUtmcctChecked()){// 勾选组
			subSql.append(", utmcct");
			groupBySql.append(", utmcct");
		}
		if(model.isUtmctrChecked()){// 勾选关键字
			subSql.append(", utmctr");
			groupBySql.append(", utmctr");
		}
		if(model.isChannelChecked()){// 勾选渠道
			subSql.append(", channel");
			groupBySql.append(", channel");
		}
		
		if(temp){
			sql.append(" SELECT ");
			sql.append(" 	row_number() over() as rowkey, ");
			sql.append(" 	businessplatform, ");
			sql.append(" 	datatime, ");
			sql.append(" 	landingpage, ");
			sql.append(" 	lp_advisory_cnt, ");
			sql.append(" 	advisorycount, ");
			sql.append(" 	advisorycountqq, ");
			sql.append(" 	advisorycountlive800, ");
			sql.append(" 	visitcount, ");
			sql.append(" 	democount, ");
			sql.append(" 	realcount, ");
			sql.append(" 	depositcount, ");
			sql.append(" 	realbtncount, ");
			sql.append(" 	demobtncount, ");
			sql.append(" 	devicetype, ");
			sql.append(" 	avg_timelen, ");
			sql.append(" 	utmcmd, ");
			sql.append(" 	utmcsr, ");
			sql.append(" 	utmccn, ");
			sql.append(" 	utmcct, ");
			sql.append(" 	utmctr, ");
			sql.append(" 	channel ");
		}else{
			sql.append(" SELECT ");
			sql.append(" 	row_number() over() as rowkey, ");
			sql.append(" 	businessplatform, ");
			sql.append(" 	datatime, ");
			sql.append(" 	landingpage, ");
			sql.append(" 	sum(lp_advisory_cnt) as lp_advisory_cnt, ");
			sql.append(" 	sum(advisorycount) as advisorycount, ");
			sql.append(" 	sum(advisorycountqq) as advisorycountqq, ");
			sql.append(" 	sum(advisorycountlive800) as advisorycountlive800, ");
			sql.append(" 	sum(visitcount) as visitcount, ");
			sql.append(" 	sum(democount) as democount, ");
			sql.append(" 	sum(realcount) as realcount, ");
			sql.append(" 	sum(depositcount) as depositcount, ");
			sql.append(" 	sum(realbtncount) as realbtncount, ");
			sql.append(" 	sum(demobtncount) as demobtncount, ");
			sql.append(" 	avg(avg_timelen) as avg_timelen ");
			sql.append(subSql.toString());
		}
		sql.append(" FROM ");
		sql.append(" 	das_web_landingpage_detail_d ");
		sql.append(" WHERE 1=1 ");
		if (null != model) {
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				sql.append(" and utmcsr like ? ");
				paramList.add("%" + model.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				sql.append(" and utmcmd like ? ");
				paramList.add("%" + model.getUtmcmd() + "%");
			}
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
			if (StringUtils.isNotBlank(model.getChannel())) {
				sql.append(" and channel like ? ");
				paramList.add("%" + model.getChannel() + "%");
			}
			if (StringUtils.isNotBlank(model.getStartTime())) {
				sql.append(" and dataTime >= ? ");
				paramList.add(model.getStartTime());
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				sql.append(" and dataTime <= ? ");
				paramList.add(model.getEndTime());
			}
			if (StringUtils.isNotBlank(model.getDeviceType())) {
				sql.append(" and deviceType = ? ");
				paramList.add(model.getDeviceType());
			}
			if (StringUtils.isNotBlank(model.getLandingPage())) {
				sql.append(" and landingpage like ? ");
				paramList.add("%" + model.getLandingPage() + "%");
			}
			sql.append(" and businessPlatform = ? ");
			paramList.add(model.getBusinessPlatform() + "");
		}
		
		// 判断是否需加分组SQL
		if(!temp){
			sql.append(groupBySql.toString());
		}
		
	}

}
