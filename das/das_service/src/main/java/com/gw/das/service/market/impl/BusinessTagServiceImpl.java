package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.BusinessTagDao;
import com.gw.das.dao.market.entity.BusinessTagEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.BusinessTagService;

@Service
public class BusinessTagServiceImpl extends BaseService implements BusinessTagService {

	@Autowired
	private BusinessTagDao tagDao;

	@Override
	public BusinessTagEntity findById(Long id) throws Exception {
		return (BusinessTagEntity) tagDao.findById(id, BusinessTagEntity.class);
	}

	@Override
	public void saveOrUpdate(BusinessTagEntity entity) throws Exception {
		if (null == entity.getTagId()) {
			tagDao.save(entity);
		} else {
			BusinessTagEntity oldEntity = findById(entity.getTagId());
			BeanUtils.copyProperties(entity, oldEntity);
			tagDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		tagDao.deleteAllByIdArray(idArray.split(","), BusinessTagEntity.class);
	}

	@Override
	public boolean checkTag(String tagName, String tagUrl, Long tagId) throws Exception {
		return tagDao.checkTag(tagName, tagUrl, tagId);
	}

	@Override
	public List<BusinessTagEntity> findList(BusinessTagEntity BusinessTagEntity) throws Exception {
		return tagDao.findList(BusinessTagEntity);
	}

	@Override
	public PageGrid<BusinessTagEntity> findPageList(PageGrid<BusinessTagEntity> pageGrid) throws Exception {
		return tagDao.findPageList(pageGrid);
	}

	@Override
	public void deleteByTagType(Integer tagContent ,Integer tagType) throws Exception {
		  tagDao.deleteByTagType(tagContent,tagType);
	}

}