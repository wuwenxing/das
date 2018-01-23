package com.gw.das.dao.market;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.base.BaseDao;
import com.gw.das.dao.market.entity.TagEntity;

@Repository
public class TagDao extends BaseDao {

	public List<TagEntity> findList(TagEntity tagEntity) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql = this.pubQueryConditions(hql, tagEntity, paramList);
		return super.findListByHql(hql.toString(), paramList);
	}

	public PageGrid<TagEntity> findPageList(PageGrid<TagEntity> pageGrid) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		TagEntity tagEntity = pageGrid.getSearchModel();
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
	private StringBuffer pubQueryConditions(StringBuffer hql, TagEntity tagEntity, List<Object> paramList) {
		hql.append("from TagEntity where deleteFlag != 'Y' and companyId = ? ");
		paramList.add(UserContext.get().getCompanyId());
		if (null != tagEntity) {
			if (StringUtils.isNotBlank(tagEntity.getTagName())) {
				hql.append(" and tagName like ? ");
				paramList.add("%" + tagEntity.getTagName() + "%");
			}
			if (StringUtils.isNotBlank(tagEntity.getTagUrl())) {
				hql.append(" and tagUrl like ? ");
				paramList.add("%" + tagEntity.getTagUrl() + "%");
			}
		}
		return hql;
	}

	public boolean checkTag(String tagName, String tagUrl, Long tagId) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer("");
		hql.append("from TagEntity where tagName = ? and tagUrl = ? and companyId = ? ");
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

}
