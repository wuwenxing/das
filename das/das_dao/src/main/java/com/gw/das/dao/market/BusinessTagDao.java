package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.BusinessTagEntity;

@Repository
public class BusinessTagDao extends BaseDao {

	public List<BusinessTagEntity> findList(BusinessTagEntity tagEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, tagEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<BusinessTagEntity> findPageList(PageGrid<BusinessTagEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		BusinessTagEntity tagEntity = pageGrid.getSearchModel();
		hql = this.pubQueryConditions(hql, tagEntity, paramList);
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
	private StringBuffer pubQueryConditions(StringBuffer hql, BusinessTagEntity tagEntity, List<Object> paramList) {
		hql.append("from BusinessTagEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != tagEntity) {
			if (null != tagEntity.getTagContent()) {
				hql.append(" and tagContent = ? ");
				paramList.add(tagEntity.getTagContent());
			}
			if (StringUtils.isNotBlank(tagEntity.getTagUrl())) {
				hql.append(" and tagUrl like ? ");
				paramList.add("%" + tagEntity.getTagUrl() + "%");
			}
			if (StringUtils.isNotBlank(tagEntity.getTagEvent())) {
				hql.append(" and ( eventCategory like ? or eventAction like ? or eventLabel like ? or eventValue like ? ) ");
				paramList.add("%" + tagEntity.getTagEvent() + "%");
				paramList.add("%" + tagEntity.getTagEvent() + "%");
				paramList.add("%" + tagEntity.getTagEvent() + "%");
				paramList.add("%" + tagEntity.getTagEvent() + "%");
			}
		}
		return hql;
	}

	public boolean checkTag(String tagName, String tagUrl, Long tagId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from BusinessTagEntity where tagContent = ? and tagUrl = ? and companyId = ? ");
		paramList.add(tagName);
		paramList.add(tagUrl);
		paramList.add(UserContext.get().getCompanyId());
		if (null != tagId && tagId > 0) {
			hql.append(" and tagId != ? ");
			paramList.add(tagId);
		}
		Object obj = super.findEntityByHql(hql.toString(), paramList);
		if (null != obj) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据条件删除
	 */
	public void deleteByTagType(Integer tagContent ,Integer tagType) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("delete BusinessTagEntity where tagContent = ? and tagType = ? and companyId = ? ");
		paramList.add(tagContent);
		paramList.add(tagType);
		paramList.add(UserContext.get().getCompanyId());
		super.executeUpdateByHql(hql.toString(), paramList);
	}

}
