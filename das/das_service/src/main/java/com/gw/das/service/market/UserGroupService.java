package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.UserGroupEntity;

public interface UserGroupService {

	public UserGroupEntity findById(Long id) throws Exception;
	public UserGroupEntity findByCode(String code) throws Exception;
	public UserGroupEntity findByCode(String code, Long companyId) throws Exception;
	public void saveOrUpdate(UserGroupEntity entity) throws Exception;
	public List<UserGroupEntity> findList(UserGroupEntity userGroupEntity) throws Exception;
	public PageGrid<UserGroupEntity> findPageList(PageGrid<UserGroupEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkUserGroup(String code, Long groupId) throws Exception;
	
}
