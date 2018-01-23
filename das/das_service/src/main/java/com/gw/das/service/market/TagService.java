package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.TagEntity;

public interface TagService {

	public TagEntity findById(Long id) throws Exception;
	public void saveOrUpdate(TagEntity entity) throws Exception;
	public List<TagEntity> findList(TagEntity tagEntity) throws Exception;
	public PageGrid<TagEntity> findPageList(PageGrid<TagEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkTag(String tagName, String tagUrl, Long tagId) throws Exception;
	
}
