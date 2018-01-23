package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.market.entity.TimingEntity;

public interface TimingService {

	public TimingEntity findById(Long id) throws Exception;
	public void save(String[] startDateList
			, String[] endDateList
			, String[] hourList
			, String[] minuteList
			, String timeSwitch
			, String type
			, Long relatedId) throws Exception;
	public void saveOrUpdate(TimingEntity entity) throws Exception;
	public List<TimingEntity> findList(TimingEntity timingEntity) throws Exception;
	public PageGrid<TimingEntity> findPageList(PageGrid<TimingEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	
}
