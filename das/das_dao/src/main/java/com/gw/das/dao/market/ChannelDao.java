package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.utils.SqlUtil;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.ChannelEntity;

@Repository
public class ChannelDao extends BaseDao {

	public List<ChannelEntity> findListByName(ChannelTypeEnum channelType, String channelName) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from ChannelEntity where channelType = ? and channelName in ( ");
		paramList.add(channelType.getLabelKey());
		String[] channelNames =  channelName.split(",");
		if(null != channelNames && channelNames.length >0){
			for(int i=0; i<channelNames.length; i++){
				if(i+1 == channelNames.length){
					hql.append(" ? ");
				}else{
					hql.append(" ?, ");
				}
				paramList.add(channelNames[i]);
			}
			hql.append(" ) ");
		}
		hql.append(" and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<ChannelEntity> findList(ChannelEntity channelEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, channelEntity, paramList);
		hql.append(SqlUtil.getOrderField(channelEntity.getSort(), channelEntity.getOrder()));
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<ChannelEntity> findPageList(PageGrid<ChannelEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		ChannelEntity channelEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, channelEntity, paramList);
		return super.findPageListByHql(hql.toString(), paramList, pageGrid);
	}

	/**
	 * list与分页查询,公共方法
	 * 
	 * @param hql
	 * @param entity
	 * @param paramList
	 * @return
	 */
	private StringBuffer pubQueryConditions(StringBuffer hql, ChannelEntity channelEntity, List<Object> paramList) {
		hql.append("from ChannelEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != channelEntity) {
			if (StringUtils.isNotBlank(channelEntity.getChannelType())) {
				hql.append(" and channelType = ? ");
				paramList.add(channelEntity.getChannelType());
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelName())) {
				hql.append(" and channelName like ? ");
				paramList.add("%" + channelEntity.getChannelName() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcsr())) {
				hql.append(" and utmcsr like ? ");
				paramList.add("%" + channelEntity.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcmd())) {
				hql.append(" and utmcmd like ? ");
				paramList.add("%" + channelEntity.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelGroup())) {
				hql.append(" and channelGroup like ? ");
				paramList.add("%" + channelEntity.getChannelGroup() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelLevel())) {
				hql.append(" and channelLevel like ? ");
				paramList.add("%" + channelEntity.getChannelLevel() + "%");
			}
			if (null != channelEntity.getIsPay()) {
				hql.append(" and isPay = ? ");
				paramList.add(channelEntity.getIsPay());
			}
		}
		return hql;
	}

	public boolean checkChannel(ChannelTypeEnum channelType, String utmcsr, String utmcmd, Long channelId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from ChannelEntity where channelType = ? and utmcsr = ? and utmcmd = ? and companyId = ? ");
		paramList.add(channelType.getLabelKey());
		paramList.add(utmcsr);
		paramList.add(utmcmd);
		paramList.add(UserContext.get().getCompanyId());
		if (null != channelId && channelId > 0) {
			hql.append(" and channelId != ? ");
			paramList.add(channelId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}
	
	public List<String> findListGroupName() throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select channelName from ChannelEntity where companyId = ? group by channelName ");
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}

	public List<String> findListGroupName(ChannelTypeEnum channelType) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select channelName from ChannelEntity where channelType = ? and companyId = ? group by channelName ");
		paramList.add(channelType.getLabelKey());
		paramList.add(UserContext.get().getCompanyId());
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<String> findChannelList(ChannelEntity channelEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select channelName from ChannelEntity where companyId = ?");
		paramList.add(UserContext.get().getCompanyId());
		if (null != channelEntity) {
			if (StringUtils.isNotBlank(channelEntity.getChannelType())) {
				hql.append(" and channelType = ? ");
				paramList.add(channelEntity.getChannelType());
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcsr())) {
				hql.append(" and utmcsr like ? ");
				paramList.add("%" + channelEntity.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcmd())) {
				hql.append(" and utmcmd like ? ");
				paramList.add("%" + channelEntity.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelGroup())) {
				hql.append(" and channelGroup like ? ");
				paramList.add("%" + channelEntity.getChannelGroup() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelLevel())) {
				hql.append(" and channelLevel like ? ");
				paramList.add("%" + channelEntity.getChannelLevel() + "%");
			}
			if (null != channelEntity.getIsPay()) {
				hql.append(" and isPay = ? ");
				paramList.add(channelEntity.getIsPay());
			}
		}
		hql.append(" group by channelName ");		
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<String> findChannelUtmcsrList(ChannelEntity channelEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select lower(utmcsr) from ChannelEntity where companyId = ?");
		paramList.add(UserContext.get().getCompanyId());
		if (null != channelEntity) {
			if (StringUtils.isNotBlank(channelEntity.getChannelType())) {
				hql.append(" and channelType = ? ");
				paramList.add(channelEntity.getChannelType());
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcsr())) {
				hql.append(" and utmcsr like ? ");
				paramList.add("%" + channelEntity.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcmd())) {
				hql.append(" and utmcmd like ? ");
				paramList.add("%" + channelEntity.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelGroup())) {
				hql.append(" and channelGroup like ? ");
				paramList.add("%" + channelEntity.getChannelGroup() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelLevel())) {
				hql.append(" and channelLevel like ? ");
				paramList.add("%" + channelEntity.getChannelLevel() + "%");
			}
			if (null != channelEntity.getIsPay()) {
				hql.append(" and isPay = ? ");
				paramList.add(channelEntity.getIsPay());
			}
		}
		hql.append(" group by utmcsr ");		
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<String> findChannelGroupList(ChannelEntity channelEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select distinct lower(channelGroup) from ChannelEntity where companyId = ?");
		paramList.add(UserContext.get().getCompanyId());
		if (null != channelEntity) {
			if (StringUtils.isNotBlank(channelEntity.getChannelType())) {
				hql.append(" and channelType = ? ");
				paramList.add(channelEntity.getChannelType());
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcsr())) {
				hql.append(" and utmcsr like ? ");
				paramList.add("%" + channelEntity.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcmd())) {
				hql.append(" and utmcmd like ? ");
				paramList.add("%" + channelEntity.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelGroup())) {
				hql.append(" and channelGroup like ? ");
				paramList.add("%" + channelEntity.getChannelGroup() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelLevel())) {
				hql.append(" and channelLevel like ? ");
				paramList.add("%" + channelEntity.getChannelLevel() + "%");
			}
			if (null != channelEntity.getIsPay()) {
				hql.append(" and isPay = ? ");
				paramList.add(channelEntity.getIsPay());
			}
		}
		hql.append(" group by utmcsr ");		
		return super.findListByHql(hql.toString(), paramList);
	}
	
	public List<String> findChannelLevelList(ChannelEntity channelEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("select distinct lower(channelLevel) from ChannelEntity where companyId = ?");
		paramList.add(UserContext.get().getCompanyId());
		if (null != channelEntity) {
			if (StringUtils.isNotBlank(channelEntity.getChannelType())) {
				hql.append(" and channelType = ? ");
				paramList.add(channelEntity.getChannelType());
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcsr())) {
				hql.append(" and utmcsr like ? ");
				paramList.add("%" + channelEntity.getUtmcsr() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getUtmcmd())) {
				hql.append(" and utmcmd like ? ");
				paramList.add("%" + channelEntity.getUtmcmd() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelGroup())) {
				hql.append(" and channelGroup like ? ");
				paramList.add("%" + channelEntity.getChannelGroup() + "%");
			}
			if (StringUtils.isNotBlank(channelEntity.getChannelLevel())) {
				hql.append(" and channelLevel like ? ");
				paramList.add("%" + channelEntity.getChannelLevel() + "%");
			}
			if (null != channelEntity.getIsPay()) {
				hql.append(" and isPay = ? ");
				paramList.add(channelEntity.getIsPay());
			}
		}
		hql.append(" group by utmcsr ");		
		return super.findListByHql(hql.toString(), paramList);
	}
	
	/**
	 * 根据条件删除
	 */
	public void deleteByCompanyId(ChannelTypeEnum channelType) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete ChannelEntity where channelType = ? and companyId = ? ");
		paramList.add(channelType.getLabelKey());
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}
	
}
