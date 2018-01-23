package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.dao.market.entity.UserGroupDetailEntity;

public interface UserGroupDetailService {
	
	public UserGroupDetailEntity findOne(Long groupId, String account, Long companyId) throws Exception;
	public UserGroupDetailEntity findById(Long id) throws Exception;
	public void saveOrUpdate(UserGroupDetailEntity entity) throws Exception;
	public List<UserGroupDetailEntity> findList(UserGroupDetailEntity userGroupDetailEntity) throws Exception;
	public PageGrid<UserGroupDetailEntity> findPageList(PageGrid<UserGroupDetailEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	
	/**
	 * 根据用户分组ID集合查询[手机号或者邮箱]集合
	 * @param groupIdAry 用户分组ID集合
	 * @param accountTypeEnum 账号类型
	 */
	public void getAccountList(String[] groupIdAry, AccountTypeEnum accountTypeEnum, List<String> list) throws Exception;
	
}
