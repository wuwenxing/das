package com.gw.das.service.market;

import java.util.List;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.ChannelTypeEnum;
import com.gw.das.dao.market.entity.ChannelEntity;

public interface ChannelService {

	public ChannelEntity findById(Long id) throws Exception;
	public List<ChannelEntity> findListByName(ChannelTypeEnum channelType, String channelName) throws Exception;
	public void saveOrUpdate(ChannelEntity entity) throws Exception;
	public List<ChannelEntity> findList(ChannelEntity channelEntity) throws Exception;
	public PageGrid<ChannelEntity> findPageList(PageGrid<ChannelEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	public boolean checkChannel(ChannelTypeEnum channelType, String utmcsr, String utmcmd, Long channelId) throws Exception;
	public List<String> findListGroupName(ChannelTypeEnum channelType) throws Exception;
	public List<String> findListGroupName() throws Exception;
	public List<String> findChannelList(ChannelEntity channelEntity) throws Exception;
	public List<String> findChannelUtmcsrList(ChannelEntity channelEntity) throws Exception;
	public List<String> findChannelGroupList(ChannelEntity channelEntity) throws Exception;
	public List<String> findChannelLevelList(ChannelEntity channelEntity) throws Exception;
	/**
	 * 根据条件删除
	 */
	public void deleteByCompanyId(ChannelTypeEnum channelType) throws Exception;
	
}
