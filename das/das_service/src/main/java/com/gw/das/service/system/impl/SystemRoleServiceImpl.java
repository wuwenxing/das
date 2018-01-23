package com.gw.das.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.Constants;
import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.system.SystemRoleDao;
import com.gw.das.dao.system.entity.SystemRoleEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.system.SystemRoleService;

@Service
public class SystemRoleServiceImpl extends BaseService implements SystemRoleService {

	@Autowired
	private SystemRoleDao systemRoleDao;

	@Override
	public SystemRoleEntity findById(Long id) throws Exception {
		return (SystemRoleEntity) systemRoleDao.findById(id, SystemRoleEntity.class);
	}

	public String findCompanyIdsByRoleId(Long roleId) throws Exception{
		if(!Constants.superAdmin.equals(UserContext.get().getLoginNo())){
			if(null != roleId){
				return this.findById(roleId).getCompanyIds();
			}else{
				return "";
			}
		}else{
			return CompanyEnum.getAllCompanyIds();
		}
	}
	
	@Override
	public SystemRoleEntity findByRoleCode(String roleCode) throws Exception {
		return systemRoleDao.findByRoleCode(roleCode);
	}

	@Override
	public void saveOrUpdate(SystemRoleEntity entity) throws Exception {
		if (null == entity.getRoleId()) {
			systemRoleDao.save(entity);
		} else {
			SystemRoleEntity oldEntity = findById(entity.getRoleId());
			BeanUtils.copyProperties(entity, oldEntity);
			systemRoleDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception{
		systemRoleDao.deleteAllByIdArray(idArray.split(","), SystemRoleEntity.class);
	}

	@Override
	public boolean checkRoleCode(String roleCode, Long roleId) throws Exception{
		return systemRoleDao.checkRoleCode(roleCode, roleId);
	}
	
	@Override
	public PageGrid<SystemRoleEntity> findPageList(PageGrid<SystemRoleEntity> pageGrid, String companyIds) throws Exception {
		return systemRoleDao.findPageList(pageGrid, companyIds);
	}

	@Override
	public List<SystemRoleEntity> findList(SystemRoleEntity entity, String companyIds) throws Exception{
		return systemRoleDao.findList(entity, companyIds);
	}
}