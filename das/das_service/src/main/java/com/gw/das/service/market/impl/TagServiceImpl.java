package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.TagDao;
import com.gw.das.dao.market.entity.TagEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.TagService;

@Service
public class TagServiceImpl extends BaseService implements TagService {

	@Autowired
	private TagDao tagDao;

	@Override
	public TagEntity findById(Long id) throws Exception {
		return (TagEntity) tagDao.findById(id, TagEntity.class);
	}

	@Override
	public void saveOrUpdate(TagEntity entity) throws Exception {
		if (null == entity.getTagId()) {
			tagDao.save(entity);
		} else {
			TagEntity oldEntity = findById(entity.getTagId());
			BeanUtils.copyProperties(entity, oldEntity);
			tagDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		tagDao.deleteAllByIdArray(idArray.split(","), TagEntity.class);
	}

	@Override
	public boolean checkTag(String tagName, String tagUrl, Long tagId) throws Exception {
		return tagDao.checkTag(tagName, tagUrl, tagId);
	}

	@Override
	public List<TagEntity> findList(TagEntity tagEntity) throws Exception {
		return tagDao.findList(tagEntity);
	}

	@Override
	public PageGrid<TagEntity> findPageList(PageGrid<TagEntity> pageGrid) throws Exception {
		return tagDao.findPageList(pageGrid);
	}

}