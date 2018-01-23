package com.gw.das.business.dao.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.enums.ReportTypeEnum;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.SqlUtil;
import com.gw.das.business.dao.base.BaseModel;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.WebSiteReportDao;
import com.gw.das.business.dao.website.entity.DasBehaviorEventChannelEffectSearchModel;

/**
 * BehaviorEventChannelEffect
 * 
 * @author wayne
 *
 */
@Repository
public class DasBehaviorEventChannelEffectDao extends WebSiteReportDao {

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
		DasBehaviorEventChannelEffectSearchModel model = (DasBehaviorEventChannelEffectSearchModel) t;
		
		// 标示是全部勾选
		boolean temp = model.isDeviceTypeChecked()&&model.isUtmcmdChecked()&&model.isUtmcsrChecked()
				&&model.isUtmccnChecked()&&model.isUtmcctChecked()
				&&model.isUtmctrChecked()&&model.isChannelChecked()
				&&model.isChannelGrpChecked()&&model.isChannelLevelChecked()
				&&model.isPlatformNameChecked()&&model.isPlatformVersionChecked();
		StringBuffer subSql = new StringBuffer();
		StringBuffer groupBySql = new StringBuffer();
		
		if(temp){
			// 当选择周统计时，周数字段也需聚合统计
			if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				subSql.append(", weeks");
				groupBySql.append("");
			}
		}else{
			// business_platform, datetime, landing_page必须分组,其他某些根据勾选情况进行分组
			groupBySql.append(" group by business_platform, datetime, landing_page");
			// 当选择周统计时，周数字段也需聚合统计
			if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
				subSql.append(", weeks");
				groupBySql.append(", weeks");
			}
			if(model.isDeviceTypeChecked()){//勾选设备类型
				subSql.append(", device_type");
				groupBySql.append(", device_type");
			}
			if(model.isChannelGrpChecked()){//勾选渠道分组
				subSql.append(", channel_grp");
				groupBySql.append(", channel_grp");
			}
			if(model.isChannelLevelChecked()){//勾选渠道级别
				subSql.append(", channel_level");
				groupBySql.append(", channel_level");
			}
			if(model.isPlatformNameChecked()){//马甲包
				subSql.append(", platform_name");
				groupBySql.append(", platform_name");
			}
			if(model.isPlatformVersionChecked()){//版本号
				subSql.append(", platform_version");
				groupBySql.append(", platform_version");
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
		}
		
		// 以下指标计算方式，注意HX与GW的区别
		// 模拟开户率HX-［模拟开户数］／［新增设备数］
		// 真实开户率HX-［真实开户数］／［新增设备数］
		// 模拟开户率GW-［模拟开户数］／［会话数］
		// 真实开户率GW-［真实开户数］／［会话数］
		// 入金率	 -［入金账户数］／［真实账户数］
		if(temp){
			sql.append(" SELECT ");
			sql.append(" 	row_number() over() as rowkey, ");
			sql.append(" 	business_platform, ");
			sql.append(" 	datetime, ");
			sql.append(" 	landing_page, ");
			sql.append(" 	new_device_count, ");
			sql.append(" 	active_device_count, ");
			sql.append(" 	device_login_count, ");
			sql.append(" 	account_login_count, ");
			sql.append(" 	time_len, ");
			sql.append(" 	visit_count, ");
			sql.append(" 	avg_visit_time_len, ");
			sql.append(" 	event_advisory_count, ");
			sql.append(" 	event_demo_count, ");
			sql.append(" 	event_real_count, ");
			sql.append(" 	event_active_count, ");
			sql.append(" 	event_device_advisory_count, ");
			sql.append(" 	event_device_demo_count, ");
			sql.append(" 	event_device_real_count, ");
			sql.append(" 	event_device_active_count, ");
			sql.append(" 	demo_count, ");
			sql.append(" 	real_count, ");
			sql.append(" 	active_count, ");
			if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
				sql.append(" case when new_device_count>0 then demo_count/new_device_count else 0 end as demo_rate, ");
				sql.append(" case when new_device_count>0 then real_count/new_device_count else 0 end as real_rate, ");
			}else{
				sql.append(" case when visit_count>0 then demo_count/visit_count else 0 end as demo_rate, ");
				sql.append(" case when visit_count>0 then real_count/visit_count else 0 end as real_rate, ");
			}
			sql.append(" case when real_count>0 then active_count/real_count else 0 end as active_rate, ");
			sql.append(" 	device_type, ");
			sql.append(" 	channel_grp, ");
			sql.append(" 	channel_level, ");
			sql.append(" 	platform_name, ");//马甲包
			sql.append(" 	platform_version, ");//版本号
			sql.append(" 	utmcmd, ");
			sql.append(" 	utmcsr, ");
			sql.append(" 	utmccn, ");
			sql.append(" 	utmcct, ");
			sql.append(" 	utmctr, ");
			sql.append(" 	channel ");
			sql.append(subSql.toString());
		}else{
			sql.append(" SELECT ");
			sql.append(" 	row_number() over() as rowkey, ");
			sql.append(" 	business_platform, ");
			sql.append(" 	datetime, ");
			sql.append(" 	landing_page, ");
			sql.append(" 	sum(new_device_count) as new_device_count, ");
			sql.append(" 	sum(active_device_count) as active_device_count, ");
			sql.append(" 	sum(device_login_count) as device_login_count, ");
			sql.append(" 	sum(account_login_count) as account_login_count, ");
			sql.append(" 	sum(time_len) as time_len, ");
			sql.append(" 	sum(visit_count) as visit_count, ");
			sql.append(" 	sum(avg_visit_time_len) as avg_visit_time_len, ");
			sql.append(" 	sum(event_advisory_count) as event_advisory_count, ");
			sql.append(" 	sum(event_demo_count) as event_demo_count, ");
			sql.append(" 	sum(event_real_count) as event_real_count, ");
			sql.append(" 	sum(event_active_count) as event_active_count, ");
			sql.append(" 	sum(event_device_advisory_count) as event_device_advisory_count, ");
			sql.append(" 	sum(event_device_demo_count) as event_device_demo_count, ");
			sql.append(" 	sum(event_device_real_count) as event_device_real_count, ");
			sql.append(" 	sum(event_device_active_count) as event_device_active_count, ");
			sql.append(" 	sum(demo_count) as demo_count, ");
			sql.append(" 	sum(real_count) as real_count, ");
			sql.append(" 	sum(active_count) as active_count, ");
			if(BusinessPlatformEnum.Hx.getLabelKey().equals(model.getBusinessPlatform())){
				sql.append(" case when sum(new_device_count)>0 then sum(demo_count)/sum(new_device_count) else 0 end as demo_rate, ");
				sql.append(" case when sum(new_device_count)>0 then sum(real_count)/sum(new_device_count) else 0 end as real_rate, ");
			}else{
				sql.append(" case when sum(visit_count)>0 then sum(demo_count)/sum(visit_count) else 0 end as demo_rate, ");
				sql.append(" case when sum(visit_count)>0 then sum(real_count)/sum(visit_count) else 0 end as real_rate, ");
			}
			sql.append(" case when sum(real_count)>0 then sum(active_count)/sum(real_count) else 0 end as active_rate ");
			sql.append(subSql.toString());
		}
		if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM das_behavior_event_channel_effect_d a ");
		}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM das_behavior_event_channel_effect_w a ");
		}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
			sql.append(" FROM das_behavior_event_channel_effect_m a ");
		}
		sql.append(" WHERE src_type='client' ");// 字段src_type='client'
		if (null != model) {
			
			if (StringUtils.isNotBlank(model.getStartTime())) {
				sql.append(" and datetime >= ? ");
				sql.append(" and a.partitionfield >= ? ");
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM-dd"), "yyyy_MM"));
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getStartTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getStartTime(), "yyyy-MM"), "yyyy_MM"));
				}
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				sql.append(" and datetime <= ? ");
				sql.append(" and a.partitionfield <= ? ");
				if(ReportTypeEnum.days.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}else if(ReportTypeEnum.weeks.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM-dd"), "yyyy_MM"));
				}else if(ReportTypeEnum.months.getLabelKey().equals(model.getReportType())){
					paramList.add(model.getEndTime());
					paramList.add(DateUtil.formatDateToString(DateUtil.getDateFromStr(model.getEndTime(), "yyyy-MM"), "yyyy_MM"));
				}
			}
			
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
			if (StringUtils.isNotBlank(model.getDeviceType())) {
				sql.append(" and device_type = ? ");
				paramList.add(model.getDeviceType());
			}else{
				// 为空时，不查询APP项，对应数字为5
				sql.append(" and device_type != '5' ");
			}
			if (StringUtils.isNotBlank(model.getChannelGrp())) {
				sql.append(" and channel_grp like ? ");
				paramList.add("%" + model.getChannelGrp() + "%");
			}
			if (StringUtils.isNotBlank(model.getChannelLevel())) {
				sql.append(" and channel_level like ? ");
				paramList.add("%" + model.getChannelLevel() + "%");
			}
			if (StringUtils.isNotBlank(model.getLandingPage())) {
				sql.append(" and landing_page like ? ");
				paramList.add("%" + model.getLandingPage() + "%");
			}
			if (StringUtils.isNotBlank(model.getPlatformName())) {
				sql.append(" and platform_name like ? ");
				paramList.add("%" + model.getPlatformName() + "%");
			}
			if (StringUtils.isNotBlank(model.getPlatformVersion())) {
				sql.append(" and platform_version like ? ");
				paramList.add("%" + model.getPlatformVersion() + "%");
			}
			sql.append(" and business_platform = ? ");
			paramList.add(model.getBusinessPlatform() + "");
		}
		
		// 加分组SQL
		sql.append(groupBySql.toString());
	}

}
