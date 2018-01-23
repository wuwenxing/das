package com.gw.das.business.dao.website;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gw.das.business.common.enums.AdvisoryEnum;
import com.gw.das.business.common.enums.AreaEnum;
import com.gw.das.business.common.enums.BrowserEnum;
import com.gw.das.business.common.enums.ClientEnum;
import com.gw.das.business.common.enums.SmsSendStatusEnum;
import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.JacksonUtil;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.ImpalaDao;
import com.gw.das.business.dao.website.entity.DasUserInfo;
import com.gw.das.business.dao.website.entity.DasUserInfoSearchBean;

@Repository
public class DasUserInfoDao extends ImpalaDao {

	/**
	 * 分页查询das_user_info_base表
	 * @param pg
	 */
	public PageGrid<DasUserInfoSearchBean> dasUserInfoListPage(PageGrid<DasUserInfoSearchBean> pg) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select rowKey, utmcsr, utmcmd, guestName, tel, email, behaviorType, behaviorDetail, createDate, platformType, iphome, ip from das_user_info_base where 1 = 1 ");
		DasUserInfoSearchBean model = pg.getSearchModel();
		if(null != model){
			// 属性[模拟\真实\入金]
			if (StringUtils.isNotBlank(model.getBehaviorType())) {
				sql.append(" and behaviortype = ? ");
				paramList.add(model.getBehaviorType());
			}
			// 账户
			if (StringUtils.isNotBlank(model.getBehaviorDetail())) {
				sql.append(" and behaviordetail like ? ");
				paramList.add("%" + model.getBehaviorDetail() + "%");
			}
			// 渠道条件[广告来源，广告媒介]
			if (StringUtils.isNotBlank(model.getUtmcsr())) {
				sql.append(" and utmcsr like ? ");
				paramList.add("%" + model.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(model.getUtmcmd())) {
				sql.append(" and utmcmd like ? ");
				paramList.add("%" + model.getUtmcmd() + "%");
			}
			// 时间条件
			if (StringUtils.isNotBlank(model.getStartTime())) {
				sql.append(" and createdate >= ? ");
				sql.append(" and partitionField >= ? ");
				paramList.add(model.getStartTime());
				paramList.add(DateUtil.formatDateToYYYYString(DateUtil.getDateFromStr(model.getStartTime())));
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				sql.append(" and createdate <= ? ");
				sql.append(" and partitionField <= ? ");
				paramList.add(model.getEndTime());
				paramList.add(DateUtil.formatDateToYYYYString(DateUtil.getDateFromStr(model.getEndTime())));
			}
			// 排重条件
			if("Y".equals(model.getIsDemo()) || "1".equals(model.getIsDemo())){
				if("3".equals(model.getBehaviorType())){
					sql.append(" and behaviortype != \"3\" ");
				}else{
					sql.append(" and tel not in (select tel from das_user_info_base where behaviortype = \"3\") ");
				}
			}
			if("Y".equals(model.getIsReal())|| "1".equals(model.getIsReal())){
				if("4".equals(model.getBehaviorType())){
					sql.append(" and behaviortype != \"4\" ");
				}else{
					sql.append(" and tel not in (select tel from das_user_info_base where behaviortype = \"4\") ");
				}
			}
			if("Y".equals(model.getIsDepesit()) || "1".equals(model.getIsDepesit())){
				if("5".equals(model.getBehaviorType())){
					sql.append(" and behaviortype != \"5\" ");
				}else{
					sql.append(" and tel not in (select tel from das_user_info_base where behaviortype = \"5\") ");
				}
			}
			// 业务平台
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and businessplatform = ? ");
				paramList.add(model.getBusinessPlatform());
			}
		}
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<DasUserInfo> returnList = OrmUtil.reflectList(DasUserInfo.class, list);
		pg.setRows(returnList);
		return pg;
	}
	
	/**
	 * 根据条件刷选用户手机号及邮箱
	 * @param DasUserInfoSearchBean model
	 */
	public List<String> dasUserInfoScreen(DasUserInfoSearchBean model, String type) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, model, paramList, type);
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		
		List<String> returnList = new ArrayList<String>();
		if("tel".equals(type)){
			for(Map<String, Object> map: list){
				Object obj = map.get("tel");
				if(null != obj){
					returnList.add(obj.toString());
				}
			}
		}else{
			for(Map<String, Object> map: list){
				Object obj = map.get("email");
				if(null != obj){
					returnList.add(obj.toString());
				}
			}
		}
		return returnList;
	}
	
	private void getPubParam(StringBuffer sql, DasUserInfoSearchBean model, List<Object> paramList, String type){
		
		if("tel".equals(type)){
			sql.append("select distinct tel from das_user_info_base where 1 = 1 ");
		}else{
			sql.append("select distinct email from das_user_info_base where 1 = 1 ");
		}
		
		if(null != model){
			// userId
			if (StringUtils.isNotBlank(model.getUserId())) {
				sql.append(" and userid = ? ");
				paramList.add(model.getUserId());
			}
			// 属性[模拟\真实\入金]
			if (StringUtils.isNotBlank(model.getBehaviorType())) {
				sql.append(" and behaviortype = ? ");
				paramList.add(model.getBehaviorType());
			}
			// 性别
			if (StringUtils.isNotBlank(model.getGender())) {
				sql.append(" and gender = ? ");
				paramList.add(model.getGender());
			}
			// 开户客户端类型
			if (StringUtils.isNotBlank(model.getPlatformType())) {
				sql.append(" and platformtype = ? ");
				paramList.add(model.getPlatformType());
			}
			// 时间条件
			if (StringUtils.isNotBlank(model.getStartTime())) {
				sql.append(" and createdate >= ? ");
				sql.append(" and partitionField >= ? ");
				paramList.add(model.getStartTime());
				paramList.add(DateUtil.formatDateToYYYYString(DateUtil.getDateFromStr(model.getStartTime())));
			}
			if (StringUtils.isNotBlank(model.getEndTime())) {
				sql.append(" and createdate <= ? ");
				sql.append(" and partitionField <= ? ");
				paramList.add(model.getEndTime());
				paramList.add(DateUtil.formatDateToYYYYString(DateUtil.getDateFromStr(model.getEndTime())));
			}
			// 渠道条件[广告来源，广告媒介]
			if (StringUtils.isNotBlank(model.getChannel())) {
				List<Map<String, String>> channelMapList = JacksonUtil.readValue(model.getChannel(), new TypeReference<List<Map<String, String>>>(){});
				if(channelMapList.size() >= 0){
					sql.append(" and (");
					for(int i=0; i<channelMapList.size(); i++){
						if(i != 0){
							sql.append(" or ");
						}
						Map<String, String> channelMap = channelMapList.get(i);
						String utmcsr = channelMap.get("utmcsr");
						String utmcmd = channelMap.get("utmcmd");
						if(StringUtils.isNotBlank(utmcsr) && StringUtils.isNotBlank(utmcmd)){
							sql.append(" (utmcsr = ? and utmcmd = ?) ");
							paramList.add(utmcsr);
							paramList.add(utmcmd);
						}else if(StringUtils.isNotBlank(utmcsr) && !StringUtils.isNotBlank(utmcmd)){
							sql.append(" (utmcsr = ?) ");
							paramList.add(utmcsr);
						}else if(!StringUtils.isNotBlank(utmcsr) && StringUtils.isNotBlank(utmcmd)){
							sql.append(" (utmcmd = ?) ");
							paramList.add(utmcmd);
						}else{
							sql.append(" (utmcsr = '' and utmcmd = '') ");
						}
					}
					sql.append(" ) ");
				}
			}
			// 标签条件
			if(StringUtils.isNotBlank(model.getUrl())){
				String[] urlAry = model.getUrl().split(",");
				List<String> urlList = new ArrayList<String>();
				for(int i=0; i<urlAry.length; i++){
					String url = urlAry[i];
					if(StringUtils.isNotBlank(url)){
						urlList.add(url);
					}
				}
				if(urlList.size() >= 0){
					StringBuffer subSql = new StringBuffer();
					subSql.append(" select userid from das_user_info_ext where 1=1 ");
					subSql.append(" and ( ");
					for(int i=0; i<urlList.size(); i++){
						if(i != 0){
							subSql.append(" or ");
						}
						subSql.append(" (tagUrlList like '%"+ urlList.get(i) +"%') ");
					}
					subSql.append(" ) ");
					// 合并子查询
					sql.append(" and userid in ( ");
					sql.append(subSql.toString());
					sql.append(" ) ");
				}
			}
			
			// 子查询-1-处理start===================
			boolean subSqlFlag = false; // 是否启用子查询标识
			StringBuffer subSql = new StringBuffer(); // 子查询SQL
			
			// 子查询-1-访问客户端类型、访问次数条件
			boolean visitClientFlag = StringUtils.isNotBlank(model.getVisitClient());
			boolean visitCountGtFlag = StringUtils.isNotBlank(model.getVisitCountGt());
			boolean visitCountLtFlag = StringUtils.isNotBlank(model.getVisitCountLt());
			if(visitClientFlag || visitCountGtFlag || visitCountLtFlag){
				subSqlFlag = true;
				subSql.append(" select userid from das_user_info_ext where 1=1 ");
				
				int visitCountGt = 0;
				int visitCountLt = 0;
				if(visitCountGtFlag){
					visitCountGt = Integer.parseInt(model.getVisitCountGt());
				}
				if(visitCountLtFlag){
					visitCountLt = Integer.parseInt(model.getVisitCountLt());
				}
				if(visitClientFlag){
					if(ClientEnum.PC.getLabelKey().equals(model.getVisitClient())){
						if(visitCountLtFlag){
							subSql.append(" and visitCountPC > ? and visitCountPC < ? ");
							paramList.add(visitCountGt);
							paramList.add(visitCountLt);
						}else{
							subSql.append(" and visitCountPC > ? ");
							paramList.add(visitCountGt);
						}
					}else if(ClientEnum.MOBILE.getLabelKey().equals(model.getVisitClient())){
						if(visitCountLtFlag){
							subSql.append(" and visitCountMobile > ? and visitCountMobile < ? ");
							paramList.add(visitCountGt);
							paramList.add(visitCountLt);
						}else{
							subSql.append(" and visitCountMobile > ? ");
							paramList.add(visitCountGt);
						}
					}
				}else{
					if(visitCountGtFlag && visitCountLtFlag){
						subSql.append(" and (visitCountPC + visitCountMobile) > ? and (visitCountPC + visitCountMobile) < ? ");
						paramList.add(visitCountGt);
						paramList.add(visitCountLt);
					}else if(visitCountGtFlag && !visitCountLtFlag){
						subSql.append(" and (visitCountPC + visitCountMobile) > ? ");
						paramList.add(visitCountGt);
					}else if(!visitCountGtFlag && visitCountLtFlag){
						subSql.append(" and (visitCountPC + visitCountMobile) < ? ");
						paramList.add(visitCountLt);
					}
				}
			}
			// 子查询-1-咨询类型、咨询次数条件
			boolean advisoryFlag = StringUtils.isNotBlank(model.getAdvisory());
			boolean advisoryCountGtFlag = StringUtils.isNotBlank(model.getAdvisoryCountGt());
			boolean advisoryCountLtFlag = StringUtils.isNotBlank(model.getAdvisoryCountLt());
			if(advisoryFlag || advisoryCountGtFlag || advisoryCountLtFlag){
				if(!subSqlFlag){
					subSqlFlag = true;
					subSql.append(" select userid from das_user_info_ext where 1=1 ");
				}
				
				int advisoryCountGt = 0;
				int advisoryCountLt = 0;
				if(advisoryCountGtFlag){
					advisoryCountGt = Integer.parseInt(model.getAdvisoryCountGt());
				}
				if(advisoryCountLtFlag){
					advisoryCountLt = Integer.parseInt(model.getAdvisoryCountLt());
				}
				if(advisoryFlag){
					if(AdvisoryEnum.QQ.getLabelKey().equals(model.getAdvisory())){
						if(advisoryCountLtFlag){
							subSql.append(" and advisoryCountQQ > ? and advisoryCountQQ < ? ");
							paramList.add(advisoryCountGt);
							paramList.add(advisoryCountLt);
						}else{
							subSql.append(" and advisoryCountQQ > ? ");
							paramList.add(advisoryCountGt);
						}
					}else if(AdvisoryEnum.Live800.getLabelKey().equals(model.getAdvisory())){
						if(advisoryCountLtFlag){
							subSql.append(" and advisoryCountLive800 > ? and advisoryCountLive800 < ? ");
							paramList.add(advisoryCountGt);
							paramList.add(advisoryCountLt);
						}else{
							subSql.append(" and advisoryCountLive800 > ? ");
							paramList.add(advisoryCountGt);
						}
					}
				}else{
					if(advisoryCountGtFlag && advisoryCountLtFlag){
						subSql.append(" and (advisoryCountQQ + advisoryCountLive800) > ? and (advisoryCountQQ + advisoryCountLive800) < ? ");
						paramList.add(advisoryCountGt);
						paramList.add(advisoryCountLt);
					}else if(advisoryCountGtFlag && !advisoryCountLtFlag){
						subSql.append(" and (advisoryCountQQ + advisoryCountLive800) > ? ");
						paramList.add(advisoryCountGt);
					}else if(!advisoryCountGtFlag && advisoryCountLtFlag){
						subSql.append(" and (advisoryCountQQ + advisoryCountLive800) < ? ");
						paramList.add(advisoryCountLt);
					}
				}
			}
			// 子查询-1-下载客户端类型、下载次数条件
			boolean downloadFlag = StringUtils.isNotBlank(model.getDownloadClient());
			boolean downloadCountGtFlag = StringUtils.isNotBlank(model.getDownloadCountGt());
			boolean downloadCountLtFlag = StringUtils.isNotBlank(model.getDownloadCountLt());
			if(downloadFlag || downloadCountGtFlag || downloadCountLtFlag){
				if(!subSqlFlag){
					subSqlFlag = true;
					subSql.append(" select userid from das_user_info_ext where 1=1 ");
				}
				int downloadCountGt = 0;
				int downloadCountLt = 0;
				if(downloadCountGtFlag){
					downloadCountGt = Integer.parseInt(model.getDownloadCountGt());
				}
				if(downloadCountLtFlag){
					downloadCountLt = Integer.parseInt(model.getDownloadCountLt());
				}
				if(downloadFlag){
					if(ClientEnum.PC.getLabelKey().equals(model.getDownloadClient())){
						if(downloadCountLtFlag){
							subSql.append(" and downloadCountPc > ? and downloadCountPc < ? ");
							paramList.add(downloadCountGt);
							paramList.add(downloadCountLt);
						}else{
							subSql.append(" and downloadCountPc > ? ");
							paramList.add(downloadCountGt);
						}
					}else if(ClientEnum.MOBILE.getLabelKey().equals(model.getDownloadClient())){
						if(downloadCountLtFlag){
							subSql.append(" and downloadCountMobile > ? and downloadCountMobile < ? ");
							paramList.add(downloadCountGt);
							paramList.add(downloadCountLt);
						}else{
							subSql.append(" and downloadCountMobile > ? ");
							paramList.add(downloadCountGt);
						}
					}
				}else{
					if(downloadCountGtFlag && downloadCountLtFlag){
						subSql.append(" and (downloadCountPc + downloadCountMobile) > ? and (downloadCountPc + downloadCountMobile) < ? ");
						paramList.add(downloadCountGt);
						paramList.add(downloadCountLt);
					} else if(downloadCountGtFlag && !downloadCountLtFlag){
						subSql.append(" and (downloadCountPc + downloadCountMobile) > ? ");
						paramList.add(downloadCountGt);
					} else if(!downloadCountGtFlag && downloadCountLtFlag){
						subSql.append(" and (downloadCountPc + downloadCountMobile) < ? ");
						paramList.add(downloadCountLt);
					}
				}
			}
			// 子查询-1-短信发送行为、发送次数条件
			boolean smsSendFlag = StringUtils.isNotBlank(model.getSmsSendStatus());
			boolean smsSendCountGtFlag = StringUtils.isNotBlank(model.getSmsSendCountGt());
			boolean smsSendCountLtFlag = StringUtils.isNotBlank(model.getSmsSendCountLt());
			if(smsSendFlag || smsSendCountGtFlag || smsSendCountLtFlag){
				if(!subSqlFlag){
					subSqlFlag = true;
					subSql.append(" select userid from das_user_info_ext where 1=1 ");
				}
				int smsSendCountGt = 0;
				int smsSendCountLt = 0;
				if(smsSendCountGtFlag){
					smsSendCountGt = Integer.parseInt(model.getSmsSendCountGt());
				}
				if(smsSendCountLtFlag){
					smsSendCountLt = Integer.parseInt(model.getSmsSendCountLt());
				}
				if(smsSendFlag){
					if(SmsSendStatusEnum.SendSuccess.getLabelKey().equals(model.getSmsSendStatus())){
						if(smsSendCountLtFlag){
							subSql.append(" and smsSuccess > ? and smsSuccess < ? ");
							paramList.add(smsSendCountGt);
							paramList.add(smsSendCountLt);
						}else{
							subSql.append(" and smsSuccess > ? ");
							paramList.add(smsSendCountGt);
						}
					}else if(SmsSendStatusEnum.ReceiveSuccess.getLabelKey().equals(model.getSmsSendStatus())){
						if(smsSendCountLtFlag){
							subSql.append(" and smsReceiveSuccess > ? and smsReceiveSuccess < ? ");
							paramList.add(smsSendCountGt);
							paramList.add(smsSendCountLt);
						}else{
							subSql.append(" and smsReceiveSuccess > ? ");
							paramList.add(smsSendCountGt);
						}
					}else if(SmsSendStatusEnum.SendFail.getLabelKey().equals(model.getSmsSendStatus())){
						if(smsSendCountLtFlag){
							subSql.append(" and smsFail > ? and smsFail < ? ");
							paramList.add(smsSendCountGt);
							paramList.add(smsSendCountLt);
						}else{
							subSql.append(" and smsFail > ? ");
							paramList.add(smsSendCountGt);
						}
					}
				}else{
					if(smsSendCountGtFlag && smsSendCountLtFlag){
						subSql.append(" and (smsSuccess + smsReceiveSuccess + smsFail) > ? and (smsSuccess + smsReceiveSuccess + smsFail) < ? ");
						paramList.add(smsSendCountGt);
						paramList.add(smsSendCountLt);
					} else if(smsSendCountGtFlag && !smsSendCountLtFlag){
						subSql.append(" and (smsSuccess + smsReceiveSuccess + smsFail) > ? ");
						paramList.add(smsSendCountGt);
					} else if(!smsSendCountGtFlag && smsSendCountLtFlag){
						subSql.append(" and (smsSuccess + smsReceiveSuccess + smsFail) < ? ");
						paramList.add(smsSendCountLt);
					}
				}
				// 子查询-1-邮件开信行为次数条件
				boolean emailSendCountGtFlag = StringUtils.isNotBlank(model.getEmailSendCountGt());
				boolean emailSendCountLtFlag = StringUtils.isNotBlank(model.getEmailSendCountLt());
				if(emailSendCountGtFlag || emailSendCountLtFlag){
					if(!subSqlFlag){
						subSqlFlag = true;
						subSql.append(" select userid from das_user_info_ext where 1=1 ");
					}
					if(emailSendCountGtFlag && emailSendCountLtFlag){
						int emailSendCountGt = Integer.parseInt(model.getEmailSendCountGt());
						int emailSendCountLt = Integer.parseInt(model.getEmailSendCountLt());
						subSql.append(" and emailOpen > ? and emailOpen < ? ");
						paramList.add(emailSendCountGt);
						paramList.add(emailSendCountLt);
					} else if(emailSendCountGtFlag && !emailSendCountLtFlag){
						int emailSendCountGt = Integer.parseInt(model.getEmailSendCountGt());
						subSql.append(" and emailOpen > ? ");
						paramList.add(emailSendCountGt);
					} else if(!emailSendCountGtFlag && emailSendCountLtFlag){
						int emailSendCountLt = Integer.parseInt(model.getEmailSendCountLt());
						subSql.append(" and emailOpen < ? ");
						paramList.add(emailSendCountLt);
					}
				}
			}
			// 和并子查询-1
			if(subSqlFlag){
				sql.append(" and userid in ( ");
				sql.append(subSql.toString());
				sql.append(" ) ");
			}
			// 子查询-1-处理end====================
			
			// 地域条件
			if(StringUtils.isNotBlank(model.getArea())){
				String[] areaAry = model.getArea().split(",");
				if(areaAry.length >= 0){
					sql.append(" and ( ");
					for(int i=0; i<areaAry.length; i++){
						String area = areaAry[i];
						if(i != 0){
							sql.append(" or ");
						}
						if(AreaEnum.QiTa.getLabelKey().equals(area)){
							sql.append(" ( ");
							int j = 0;
							for(AreaEnum ae:AreaEnum.values()){
								if(!AreaEnum.QiTa.getLabelKey().equals(ae.getLabelKey())){
									if(j == 0){
										sql.append(" iphome not like ? ");
										paramList.add("%" + ae.getCondition() + "%"); // 排除
									}else{
										sql.append(" and iphome not like ? ");
										paramList.add("%" + ae.getCondition() + "%"); // 排除
									}
								}
								j++;
							}
							sql.append(" ) ");
						}else{
							sql.append(" (iphome like ?) ");
							paramList.add("%" + AreaEnum.getAreaEnum(area).getCondition() + "%");
						}
					}
					sql.append(" ) ");
				}
			}
			// 开户使用的浏览器条件
			if(StringUtils.isNotBlank(model.getBrowser())){
				String[] browserAry = model.getBrowser().split(",");
				if(browserAry.length >= 0){
					sql.append(" and ( ");
					for(int i=0; i<browserAry.length; i++){
						String browser = browserAry[i];
						if(i != 0){
							sql.append(" or ");
						}
						if(BrowserEnum.Safari.getLabelKey().equals(browser)){
							sql.append(" (browser like ? and browser not like ?) ");
							paramList.add("%" + BrowserEnum.valueOf(browser).getCondition() + "%");
							paramList.add("%" + BrowserEnum.Chrome.getCondition() + "%");
						}else if(BrowserEnum.QiTa.getLabelKey().equals(browser)){
							sql.append(" ( ");
							sql.append(" browser not like ? ");
							paramList.add("%" + "MSIE" + "%"); // 可排除-IE系列
							sql.append(" and browser not like ? ");
							paramList.add("%" + "Safari" + "%"); // 可排除-Chrome及Safari
							sql.append(" and browser not like ? ");
							paramList.add("%" + "Firefox" + "%"); // 可排除-Firefox系列
							sql.append(" and browser not like ? ");
							paramList.add("%" + "Opera" + "%");// 可排除-Opera
							sql.append(" ) ");
						}else{
							sql.append(" (browser like ?) ");
							paramList.add("%" + BrowserEnum.valueOf(browser).getCondition() + "%");
						}
					}
					sql.append(" ) ");
				}
			}
			
			// 根据排重类型排重
			if("tel".equals(type)){
				// 排重条件
				if("Y".equals(model.getIsDemo()) || "1".equals(model.getIsDemo())){
					if("3".equals(model.getBehaviorType())){
						sql.append(" and behaviortype != \"3\" ");
					}else{
						sql.append(" and tel not in (select tel from das_user_info_base where behaviortype = \"3\") ");
					}
				}
				if("Y".equals(model.getIsReal()) || "1".equals(model.getIsReal())){
					if("4".equals(model.getBehaviorType())){
						sql.append(" and behaviortype != \"4\" ");
					}else{
						sql.append(" and tel not in (select tel from das_user_info_base where behaviortype = \"4\") ");
					}
				}
				if("Y".equals(model.getIsDepesit()) || "1".equals(model.getIsDepesit())){
					if("5".equals(model.getBehaviorType())){
						sql.append(" and behaviortype != \"5\" ");
					}else{
						sql.append(" and tel not in (select tel from das_user_info_base where behaviortype = \"5\") ");
					}
				}
			}else{
				// 排重条件
				if("Y".equals(model.getIsDemo()) || "1".equals(model.getIsDemo())){
					if("3".equals(model.getBehaviorType())){
						sql.append(" and behaviortype != \"3\" ");
					}else{
						sql.append(" and email not in (select email from das_user_info_base where behaviortype = \"3\") ");
					}
				}
				if("Y".equals(model.getIsReal()) || "1".equals(model.getIsReal())){
					if("4".equals(model.getBehaviorType())){
						sql.append(" and behaviortype != \"4\" ");
					}else{
						sql.append(" and email not in (select email from das_user_info_base where behaviortype = \"4\") ");
					}
				}
				if("Y".equals(model.getIsDepesit()) || "1".equals(model.getIsDepesit())){
					if("5".equals(model.getBehaviorType())){
						sql.append(" and behaviortype != \"5\" ");
					}else{
						sql.append(" and email not in (select email from das_user_info_base where behaviortype = \"5\") ");
					}
				}
			}
			
			
			// 排重条件-手机号码
			if(StringUtils.isNotBlank(model.getDisPhones())){
				List<String> disPhonesList = new ArrayList<String>();
				String[] disPhones = model.getDisPhones().split(",");
				for(String disPhone:disPhones){
					disPhonesList.add(disPhone);
					disPhonesList.add("86-" + disPhone);
					disPhonesList.add("86--" + disPhone);
				}
				if(null != disPhonesList && disPhonesList.size() >= 0){
					sql.append(" and tel not in ( ");
					for(int i=0; i<disPhonesList.size(); i++){
						if(i+1 == disPhonesList.size()){
							sql.append(" ? ");
						}else{
							sql.append(" ?, ");
						}
						paramList.add(disPhonesList.get(i));
					}
					sql.append(" ) ");
				}
			}
			// 手机号码不为空
			sql.append(" and tel != '' ");
			
			// 业务平台
			if (StringUtils.isNotBlank(model.getBusinessPlatform())) {
				sql.append(" and businessplatform = ? ");
				paramList.add(model.getBusinessPlatform());
			}
			
		}
	}

	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T searchModel, List<Object> paramList) {
		// TODO Auto-generated method stub
		
	}
	
}
