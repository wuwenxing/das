package com.gw.das.business.dao.trade;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.enums.DimBlackListTypeEnum;
import com.gw.das.business.common.utils.DateUtil;
import com.gw.das.business.common.utils.MD5;
import com.gw.das.business.dao.base.BaseSearchModel;
import com.gw.das.business.dao.base.TradeSiteReportDao;
import com.gw.das.business.dao.trade.entity.DimBlackListSearchBean;

/**
 * 风控要素列表
 */
@Repository
public class DimBlackListDao extends TradeSiteReportDao {

	/**
	 * 风控要素列表
	 */
	@Override
	public <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T t, List<Object> paramList){
		DimBlackListSearchBean model = (DimBlackListSearchBean) t;
		sql.append(" SELECT t.type, t.value, t.risk_type"
				+ ", to_char(t.create_time, 'yyyy-mm-dd HH24:MI:SS') as create_time"
				+ ", to_char(t.update_time, 'yyyy-mm-dd HH24:MI:SS') as update_time"
				+ ", to_char(t.mark_time, 'yyyy-mm-dd') as mark_time"
				+ ", t.source, t.remark, t.remark_en ");
		sql.append(" FROM rds.dim_blacklist t ");
		sql.append(" WHERE 1=1 ");
		if(StringUtils.isNotBlank(model.getStartTime())){
			sql.append(" and t.create_time >= ? ");
			paramList.add(DateUtil.getDateFromStr(model.getStartTime()));
		}
		if(StringUtils.isNotBlank(model.getEndTime())){
			sql.append(" and t.create_time <= ? ");
			paramList.add(DateUtil.getDateFromStr(model.getEndTime()));
		}
		if(StringUtils.isNotBlank(model.getStartMarkTime())){
			sql.append(" and t.mark_time >= ? ");
			paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getStartMarkTime()));
		}
		if(StringUtils.isNotBlank(model.getEndMarkTime())){
			sql.append(" and t.mark_time <= ? ");
			paramList.add(DateUtil.getDateFromStrByyyyMMdd(model.getEndMarkTime()));
		}
		if(StringUtils.isNotBlank(model.getSource())){
			sql.append(" and t.source = ? ");
			paramList.add(Integer.parseInt(model.getSource()));
		}
		if(StringUtils.isNotBlank(model.getType())){
			sql.append(" and t.type = ? ");
			paramList.add(model.getType());
		}
		if(StringUtils.isNotBlank(model.getValue())){
			if(StringUtils.isNotBlank(model.getType()) 
					&& DimBlackListTypeEnum.idCardMd5.getLabelKey().equals(model.getType())){
				sql.append(" and (t.value like ? or t.value like ? )");
				paramList.add("%" + model.getValue() + "%");
				paramList.add("%" + MD5.getMd5(model.getValue()) + "%");
			}else{
				sql.append(" and t.value like ? ");
				paramList.add("%" + model.getValue() + "%");
			}
		}
		if(StringUtils.isNotBlank(model.getRiskType())){
			sql.append(" and t.risk_type = ? ");
			paramList.add(model.getRiskType());
		}
		if(StringUtils.isNotBlank(model.getRemark())){
			sql.append(" and t.remark like ? ");
			paramList.add("%" + model.getRemark() + "%");
		}
		if(StringUtils.isNotBlank(model.getRemarkEn())){
			sql.append(" and t.remarkEn like ? ");
			paramList.add("%" + model.getRemarkEn() + "%");
		}
	}
	
}
