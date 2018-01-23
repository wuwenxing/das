package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.UserGroupLogEntity;

public interface UserGroupLogService {

	public UserGroupLogEntity findById(Long id) throws Exception;
	public void saveOrUpdate(UserGroupLogEntity entity) throws Exception;
	public List<UserGroupLogEntity> findList(UserGroupLogEntity userGroupLogEntity) throws Exception;
	public PageGrid<UserGroupLogEntity> findPageList(PageGrid<UserGroupLogEntity> pageGrid) throws Exception;
	
}
