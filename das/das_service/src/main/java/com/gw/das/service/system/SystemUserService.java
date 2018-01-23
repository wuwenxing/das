package com.gw.das.service.system;

import java.util.List;
import java.util.Map;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.system.entity.SystemUserEntity;

public interface SystemUserService {

	public SystemUserEntity findById(Long id) throws Exception;
	public SystemUserEntity findByUserNo(String userNo) throws Exception;
	public void saveOrUpdate(SystemUserEntity entity) throws Exception;
	public void updatePassword(Long userId, String password) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkUserNo(String userNo, Long userId) throws Exception;
	public void resetPassword(Long userId) throws Exception;
	public List<SystemUserEntity> findList(SystemUserEntity userEntity, String companyIds) throws Exception;
	public PageGrid<SystemUserEntity> findPageList(PageGrid<SystemUserEntity> pageGrid, String companyIds) throws Exception;
	public List<SystemUserEntity> findUserListByRoleId(Long roleId) throws Exception;
	public void updateRoleIdByUserIdArray(Long roleId, String selectIds, String unSelectIds) throws Exception;
	/**
	 * 返回有权限的菜单集合
	 * Map<key=user_id+","+menu_id, user_no>
	 * @param companyId
	 * @return
	 */
	public Map<String, String> findUserMenuMap();
	
	
}
