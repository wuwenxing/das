package com.gw.das.service.system;

public interface SystemMenuRoleService {

	/**
	 * 根据条件，更新roleId与menuIds关联信息
	 * @param roleId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	public void updateRoleIdMenuIds(Long roleId, String menuCodes) throws Exception;
}
