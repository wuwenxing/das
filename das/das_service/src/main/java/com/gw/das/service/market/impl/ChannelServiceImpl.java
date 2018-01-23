package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.ChannelDao;
import com.gw.das.dao.market.entity.ChannelEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.ChannelService;

@Service
public class ChannelServiceImpl extends BaseService implements ChannelService {

	@Autowired
	private ChannelDao channelDao;

	@Override
	public ChannelEntity findById(Long id) throws Exception {
		return (ChannelEntity) channelDao.findById(id, ChannelEntity.class);
	}

	@Override
	public void saveOrUpdate(ChannelEntity entity) throws Exception {
		if (null == entity.getChannelId()) {
			channelDao.save(entity);
		} else {
			ChannelEntity oldEntity = findById(entity.getChannelId());
			BeanUtils.copyProperties(entity, oldEntity);
			channelDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		channelDao.deleteAllByIdArray(idArray.split(","), ChannelEntity.class);
	}

	@Override
	public boolean checkChannel(ChannelTypeEnum channelType, String utmcsr, String utmcmd, Long channelId)
			throws Exception {
		return channelDao.checkChannel(channelType, utmcsr, utmcmd, channelId);
	}

	@Override
	public List<ChannelEntity> findList(ChannelEntity channelEntity) throws Exception {
		return channelDao.findList(channelEntity);
	}

	@Override
	public PageGrid<ChannelEntity> findPageList(PageGrid<ChannelEntity> pageGrid) throws Exception {
		return channelDao.findPageList(pageGrid);
	}

	@Override
	public List<String> findListGroupName() throws Exception{
		return channelDao.findListGroupName();
	}
	
	@Override
	public List<String> findListGroupName(ChannelTypeEnum channelType) throws Exception {
		return channelDao.findListGroupName(channelType);
	}

	@Override
	public List<ChannelEntity> findListByName(ChannelTypeEnum channelType, String channelName) throws Exception {
		return channelDao.findListByName(channelType, channelName);
	}

	/**
	 * 根据条件删除
	 */
	@Override
	public void deleteByCompanyId(ChannelTypeEnum channelType) throws Exception {
		channelDao.deleteByCompanyId(channelType);
	}

	@Override
	public List<String> findChannelList(ChannelEntity channelEntity) throws Exception {
		return channelDao.findChannelList(channelEntity);
	}
	
	@Override
	public List<String> findChannelUtmcsrList(ChannelEntity channelEntity) throws Exception {
		return channelDao.findChannelUtmcsrList(channelEntity);
	}
	
	@Override
	public List<String> findChannelGroupList(ChannelEntity channelEntity) throws Exception {
		return channelDao.findChannelGroupList(channelEntity);
	}
	
	@Override
	public List<String> findChannelLevelList(ChannelEntity channelEntity) throws Exception {
		return channelDao.findChannelLevelList(channelEntity);
	}

}