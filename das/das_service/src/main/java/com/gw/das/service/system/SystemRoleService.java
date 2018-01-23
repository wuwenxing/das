package com.gw.das.service.system;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.system.entity.SystemRoleEntity;

public interface SystemRoleService {

	public String findCompanyIdsByRoleId(Long roleId) throws Exception;
	public SystemRoleEntity findById(Long id) throws Exception;
	public SystemRoleEntity findByRoleCode(String roleCode) throws Exception;
	public void saveOrUpdate(SystemRoleEntity entity) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkRoleCode(String roleCode, Long roleId) throws Exception;
	public PageGrid<SystemRoleEntity> findPageList(PageGrid<SystemRoleEntity> pageGrid, String companyIds) throws Exception;
	public List<SystemRoleEntity> findList(SystemRoleEntity entity, String companyIds) throws Exception;
}
