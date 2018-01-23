package com.gw.das.service.system;

public interface SystemMenuUserService {

	/**
	 * 根据条件，更新userId与menuIds关联信息
	 * @param userId
	 * @param menuIds
	 * @return
	 * @throws Exception
	 */
	public void updateUserIdMenuIds(Long userId, String menuCodes) throws Exception;
	
}
