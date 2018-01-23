package com.gw.das.service.market.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.common.utils.DateUtil;
import com.gw.das.dao.market.TimingDao;
import com.gw.das.dao.market.entity.TimingEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.TimingService;

@Service
public class TimingServiceImpl extends BaseService implements TimingService {

	@Autowired
	private TimingDao timingDao;

	@Override
	public TimingEntity findById(Long id) throws Exception {
		return (TimingEntity) timingDao.findById(id, TimingEntity.class);
	}

	@Override
	public void save(String[] startDateList
			, String[] endDateList
			, String[] hourList
			, String[] minuteList
			, String timeSwitch
			, String type
			, Long relatedId) throws Exception{
		if(null != startDateList && null != endDateList 
				 && null != hourList && null != minuteList 
				 && null != relatedId && StringUtils.isNotBlank(type) 
				 && startDateList.length > 0 
				 && startDateList.length == endDateList.length 
				 && endDateList.length == hourList.length 
				 && hourList.length== minuteList.length){
			
			// 删除之前保存的数据
			TimingEntity con = new TimingEntity();
			if("sms".equals(type)){
				con.setSmsId(relatedId);
			}else if("email".equals(type)){
				con.setEmailId(relatedId);
			}
			timingDao.deleteByCon(con);
			
			// 保存
			for(int i=0; i<startDateList.length; i++){
				String startDate = startDateList[i];
				String endDate = endDateList[i];
				String hour = hourList[i];
				String minute = minuteList[i];
				if(StringUtils.isNotBlank(startDate)){
					TimingEntity entity = new TimingEntity();
					entity.setStartDate(DateUtil.stringToDate(startDate));
					entity.setEndDate(DateUtil.stringToDate(endDate));
					entity.setHour(Integer.parseInt(hour));
					entity.setMinute(Integer.parseInt(minute));
					entity.setTimeSwitch(timeSwitch);
					if("sms".equals(type)){
						entity.setSmsId(relatedId);
					}else if("email".equals(type)){
						entity.setEmailId(relatedId);
					}
					timingDao.save(entity);
				}
			}
		}else{
			throw new Exception("定时参数错误");
		}
	}
	
	@Override
	public void saveOrUpdate(TimingEntity entity) throws Exception {
		if (null == entity.getTimingId()) {
			timingDao.save(entity);
		} else {
			TimingEntity oldEntity = findById(entity.getTimingId());
			BeanUtils.copyProperties(entity, oldEntity);
			timingDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		timingDao.deleteAllByIdArray(idArray.split(","), TimingEntity.class);
	}

	@Override
	public List<TimingEntity> findList(TimingEntity timingEntity) throws Exception {
		return timingDao.findList(timingEntity);
	}

	@Override
	public PageGrid<TimingEntity> findPageList(PageGrid<TimingEntity> pageGrid) throws Exception {
		return timingDao.findPageList(pageGrid);
	}

}