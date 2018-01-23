package com.gw.das.business.dao.trade;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.BusinessPlatformEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.MD5;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.trade.entity.DimAccountBlackListSearchBean;

/**
 * 账户黑名单列表
 */
@Repository
public class DimAccountBlackListDao extends TradeSiteReportDao {

	/**
	 * 账户黑名单列表
	 */
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		DimAccountBlackListSearchBean model = (DimAccountBlackListSearchBean) t;

		sql.append(" select ");
		sql.append(" t.rowkey, ");
		sql.append(" t.accountNo, ");
		sql.append(" t.accountId, ");
		sql.append(" t.companyName, ");
		sql.append(" t.companyId, ");
		sql.append(" t.platform, ");
		sql.append(" t.accountNameCn, ");
		sql.append(" t.mobile, ");
		sql.append(" t.idCardEncrypt, ");
		sql.append(" to_char(t.markTime, 'yyyy-MM-dd') as markTime, ");
		sql.append(" t.createIp, ");
		sql.append(" t.behavior, ");
		sql.append(" to_char(t.behaviorDate, 'yyyy-MM-dd HH:mm:ss') as behaviorDate, ");
		sql.append(" t.behaviorRemark, ");
		sql.append(" t.riskFactors ");
		sql.append(" from( ");
		sql.append(" select row_number() over() as rowkey, ");
		sql.append(" concat(blt.account_no, '') as accountNo, ");
		sql.append(" blt.account_id as accountId, ");
		sql.append(" acc.company_name as companyName, ");
		sql.append(" acc.company_id as companyId, ");
		sql.append(" blt.platform, ");
		sql.append(" acc.account_name_cn as accountNameCn, ");
		sql.append(" acc.mobile, ");
		sql.append(" acc.id_card_encrypt as idCardEncrypt, ");
		sql.append(" acc.create_ip as createIp, ");
		sql.append(" blt.mark_time as markTime, ");
		sql.append(" case when open_id_card_double_cnt >1 then '开户' ");
		sql.append("      when open_mobile_double_cnt >1 then '开户' ");
		sql.append("      when active_id_card_double_cnt >1 then '激活' ");
		sql.append("      when active_mobile_double_cnt >1 then '激活' end  as behavior, ");
		sql.append("  ");
		sql.append(" case when open_id_card_double_cnt >1 then acc.account_open_time ");
		sql.append("      when open_mobile_double_cnt >1 then acc.account_open_time ");
		sql.append("      when active_id_card_double_cnt >1 then acc.active_time ");
		sql.append("      when active_mobile_double_cnt >1 then acc.active_time end  as behaviorDate, ");
		sql.append("  ");
		sql.append(" case when open_id_card_double_cnt>1 then '开户_身份证重复' ");
		sql.append("      when open_mobile_double_cnt>1 then '开户_手机重复' ");
		sql.append("      when active_id_card_double_cnt>1 then '激活_身份证重复' ");
		sql.append("      when active_mobile_double_cnt>1 then '激活_手机重复' end  as behaviorRemark, ");
		sql.append("  ");
		sql.append(" concat(case when open_id_card_double_cnt>1 then concat('开户_身份证重复', open_id_card_double_cnt, '次数') ");
		sql.append("      when open_mobile_double_cnt>1 then concat('开户_手机重复', open_mobile_double_cnt, '次数') ");
		sql.append("      when active_id_card_double_cnt>1 then concat('激活_身份证重复', active_id_card_double_cnt, '次数') ");
		sql.append("      when active_mobile_double_cnt>1 then concat('激活_手机重复', active_mobile_double_cnt, '次数') ");
		sql.append("  end , '_', list_remark) as riskFactors ");
		
		sql.append("  FROM rds.dim_account_blacklist blt ");
		
		sql.append(" left join rds.dim_account acc "
				+ "on blt.account_no=acc.account_no and blt.platform=acc.platform ");
		sql.append(" where (open_id_card_double_cnt>1 "
				+ "or open_mobile_double_cnt>1 "
				+ "or active_id_card_double_cnt>1 "
				+ "or active_mobile_double_cnt>1) ");
		if(StringUtils.isNotBlank(model.getCompanyId())&&!BusinessPlatformEnum.gw.getLabelKey().equals(model.getCompanyId())){
			sql.append(" and acc.company_id = ? ");
			paramList.add(Long.parseLong(BusinessPlatformEnum.getAPICompanyId(model.getCompanyId())));
		}
		if(StringUtils.isNotBlank(model.getAccountName())){
			sql.append(" and acc.account_name_cn like ? ");
			paramList.add("%" + model.getAccountName() + "%");
		}
		if(StringUtils.isNotBlank(model.getMobile())){
			sql.append(" and acc.mobile like ? ");
			paramList.add("%" + model.getMobile() + "%");
		}
		if(StringUtils.isNotBlank(model.getIdCard())){
			sql.append(" and acc.id_card_md5 = ? ");
			paramList.add(MD5.getMd5(model.getIdCard()));
		}
		if(StringUtils.isNotBlank(model.getCreateIp())){
			sql.append(" and acc.create_ip like ? ");
			paramList.add("%" + model.getCreateIp() + "%");
		}
		if(StringUtils.isNotBlank(model.getPlatform())){
			sql.append(" and blt.platform = ? ");
			paramList.add(model.getPlatform());
		}
		sql.append("  ) t WHERE 1=1 ");
		if(StringUtils.isNotBlank(model.getStartTime())){
			sql.append(" and t.behaviorDate >= ? ");
			paramList.add(DateUtil.getDateFromStr(model.getStartTime()));
		}
		if(StringUtils.isNotBlank(model.getEndTime())){
			sql.append(" and t.behaviorDate <= ? ");
			paramList.add(DateUtil.getDateFromStr(model.getEndTime()));
		}
		if(StringUtils.isNotBlank(model.getStartMarkTime())){
			sql.append(" and t.markTime >= ? ");
			paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getStartMarkTime()));
		}
		if(StringUtils.isNotBlank(model.getEndMarkTime())){
			sql.append(" and t.markTime <= ? ");
			paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getEndMarkTime()));
		}
		if(StringUtils.isNotBlank(model.getAccountNo())){
			sql.append(" and t.accountNo like ? ");
			paramList.add("%" + model.getAccountNo() + "%");
		}
		
	}
	
}
