package com.gw.das.service.market.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.UserGroupDetailDao;
import com.gw.das.dao.market.entity.UserGroupDetailEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.UserGroupDetailService;

@Service
public class UserGroupDetailServiceImpl extends BaseService implements UserGroupDetailService {

	@Autowired
	private UserGroupDetailDao userGroupDetailDao;

	@Override
	public UserGroupDetailEntity findById(Long id) throws Exception {
		return (UserGroupDetailEntity) userGroupDetailDao.findById(id, UserGroupDetailEntity.class);
	}
	
	@Override
	public UserGroupDetailEntity findOne(Long groupId, String account, Long companyId) throws Exception {
		return userGroupDetailDao.findOne(groupId, account, companyId);
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		userGroupDetailDao.deleteAllByIdArray(idArray.split(","), UserGroupDetailEntity.class);
	}
	
	@Override
	public void saveOrUpdate(UserGroupDetailEntity entity) throws Exception {
		if (null == entity.getDetailId()) {
			userGroupDetailDao.save(entity);
		} else {
			UserGroupDetailEntity oldEntity = findById(entity.getDetailId());
			BeanUtils.copyProperties(entity, oldEntity);
			userGroupDetailDao.update(oldEntity);
		}
	}

	@Override
	public List<UserGroupDetailEntity> findList(UserGroupDetailEntity userGroupDetailEntity) throws Exception {
		return userGroupDetailDao.findList(userGroupDetailEntity);
	}

	@Override
	public PageGrid<UserGroupDetailEntity> findPageList(PageGrid<UserGroupDetailEntity> pageGrid) throws Exception {
		return userGroupDetailDao.findPageList(pageGrid);
	}
	
	/**
	 * 根据用户分组ID集合查询[手机号或者邮箱]集合
	 * @param groupIdAry 用户分组ID集合
	 * @param accountTypeEnum 账号类型
	 */
	@Override
	public void getAccountList(String[] groupIdAry, AccountTypeEnum accountTypeEnum, List<String> list) throws Exception{
		UserGroupDetailEntity entity = new UserGroupDetailEntity();
		entity.setAccountType(accountTypeEnum.getLabelKey());
		
		for(int i=0; i<groupIdAry.length; i++){
			String groupId = groupIdAry[i];
			if(StringUtils.isNotBlank(groupId)){
				entity.setGroupId(Long.parseLong(groupId));
				 List<UserGroupDetailEntity> tempList = userGroupDetailDao.findList(entity);
				 for(UserGroupDetailEntity record: tempList){
					 list.add(record.getAccount());
				 }
			}
		}
		
	}
	
}