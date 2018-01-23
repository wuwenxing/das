package com.gw.das.service.custConsultation.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.custConsultation.CustConsultationDao;
import com.gw.das.dao.custConsultation.entity.CustConsultationEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.custConsultation.CustConsultationService;

@Service
public class CustConsultationServiceImpl extends BaseService implements CustConsultationService {

	@Autowired
	private CustConsultationDao custConsultationDao;

	@Override
	public CustConsultationEntity findById(Long id) throws Exception {
		return (CustConsultationEntity) custConsultationDao.findById(id, CustConsultationEntity.class);
	}

	@Override
	public void saveOrUpdate(CustConsultationEntity entity) throws Exception {
		entity.setCompanyId(UserContext.get().getCompanyId());	
		if (null == entity.getConsulttationId()) {
			custConsultationDao.save(entity);
		} else {
			CustConsultationEntity oldEntity = findById(entity.getConsulttationId());
			BeanUtils.copyProperties(entity, oldEntity);
			custConsultationDao.update(oldEntity);
		}
	}
	
	@Override
	public void update(CustConsultationEntity entity) throws Exception {
		custConsultationDao.update(entity);
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		custConsultationDao.deleteAllByIdArray(idArray.split(","), CustConsultationEntity.class);
	}

	@Override
	public List<CustConsultationEntity> findList(CustConsultationEntity entity) throws Exception {
		return custConsultationDao.findList(entity);
	}

	@Override
	public PageGrid<CustConsultationEntity> findPageList(PageGrid<CustConsultationEntity> pageGrid) throws Exception {
		return custConsultationDao.findPageList(pageGrid);
	}
	
	@Override
	public List<CustConsultationEntity> findByBusinessplatform(String reportType) throws Exception{
		return custConsultationDao.findByBusinessplatform(reportType);
	}
	
	@Override
	public CustConsultationEntity findCustConsultation(CustConsultationEntity custConsultationEntity) throws Exception {
		return custConsultationDao.findCustConsultation(custConsultationEntity);
	}

}