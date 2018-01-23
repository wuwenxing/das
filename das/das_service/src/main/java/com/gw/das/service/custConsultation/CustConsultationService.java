package com.gw.das.service.custConsultation;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.custConsultation.entity.CustConsultationEntity;

public interface CustConsultationService {

	public CustConsultationEntity findById(Long id) throws Exception;
	public void saveOrUpdate(CustConsultationEntity entity) throws Exception;
	public void update(CustConsultationEntity entity) throws Exception;
	public List<CustConsultationEntity> findList(CustConsultationEntity entity) throws Exception;
	public PageGrid<CustConsultationEntity> findPageList(PageGrid<CustConsultationEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public List<CustConsultationEntity> findByBusinessplatform(String reportType) throws Exception;
	public CustConsultationEntity findCustConsultation(CustConsultationEntity custConsultationEntity) throws Exception ;
	
}
