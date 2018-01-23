package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.BusinessTagEntity;

public interface BusinessTagService {

	public BusinessTagEntity findById(Long id) throws Exception;
	public void saveOrUpdate(BusinessTagEntity entity) throws Exception;
	public List<BusinessTagEntity> findList(BusinessTagEntity BusinessTagEntity) throws Exception;
	public PageGrid<BusinessTagEntity> findPageList(PageGrid<BusinessTagEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkTag(String tagName, String tagUrl, Long tagId) throws Exception;
	public void deleteByTagType(Integer tagContent ,Integer tagType) throws Exception;
	
}
