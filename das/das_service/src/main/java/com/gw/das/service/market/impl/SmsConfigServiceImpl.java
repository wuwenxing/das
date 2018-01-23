package com.gw.das.service.market.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.SmsChannelEnum;
import com.gw.das.common.enums.SmsSignEnum;
import com.gw.das.common.utils.BeanUtils;
import com.gw.das.dao.market.SmsConfigDao;
import com.gw.das.dao.market.entity.SmsConfigEntity;
import com.gw.das.service.base.BaseService;
import com.gw.das.service.market.SmsConfigService;

@Service
public class SmsConfigServiceImpl extends BaseService implements SmsConfigService {

	@Autowired
	private SmsConfigDao smsConfigDao;

	@Override
	public SmsConfigEntity findById(Long id) throws Exception {
		return (SmsConfigEntity) smsConfigDao.findById(id, SmsConfigEntity.class);
	}

	@Override
	public SmsConfigEntity findBySign(String sign) throws Exception {
		return smsConfigDao.findBySign(sign);
	}
	
	@Override
	public void saveOrUpdate(String[] signs, String[] smsChannels) throws Exception {
		for(int i=0; i<signs.length; i++){
			String sign = signs[i];
			String smsChannel = smsChannels[i];
			SmsConfigEntity model = smsConfigDao.findBySign(sign);
			if(model != null){
				model.setSign(sign);
				model.setSmsChannel(smsChannel);
				smsConfigDao.update(model);
			}else{
				model = new SmsConfigEntity();
				model.setSign(sign);
				model.setSmsChannel(smsChannel);
				smsConfigDao.save(model);
			}
		}
	}

	@Override
	public void saveOrUpdate(SmsConfigEntity entity) throws Exception {
		if (null == entity.getSmsConfigId()) {
			smsConfigDao.save(entity);
		} else {
			SmsConfigEntity oldEntity = findById(entity.getSmsConfigId());
			BeanUtils.copyProperties(entity, oldEntity);
			smsConfigDao.update(oldEntity);
		}
	}

	@Override
	public void deleteByIdArray(String idArray) throws Exception {
		smsConfigDao.deleteAllByIdArray(idArray.split(","), SmsConfigEntity.class);
	}

	@Override
	public boolean checkSmsConfig(String sign, String smsChannel, Long smsConfigId) throws Exception {
		return smsConfigDao.checkSmsConfig(sign, smsChannel, smsConfigId);
	}

	@Override
	public List<SmsConfigEntity> findList(SmsConfigEntity smsConfigEntity) throws Exception {
		return smsConfigDao.findList(smsConfigEntity);
	}

	@Override
	public PageGrid<SmsConfigEntity> findPageList(PageGrid<SmsConfigEntity> pageGrid) throws Exception {
		return smsConfigDao.findPageList(pageGrid);
	}
	
	@Override
	public String getSmsChannel(String sign) throws Exception {
		// 查询签名对应的短信通道，如果签名为空-默认查询签名为【金道】对应的短信通道
		if(sign.equals(SmsSignEnum.jd.getValue())){
			sign = SmsSignEnum.jd.getLabelKey();
		}else if(sign.equals(SmsSignEnum.jdgwfx.getValue())){
			sign = SmsSignEnum.jdgwfx.getLabelKey();
		}else if(sign.equals(SmsSignEnum.jdgjs.getValue())){
			sign = SmsSignEnum.jdgjs.getLabelKey();
		}else if(sign.equals(SmsSignEnum.hxgjs.getValue())){
			sign = SmsSignEnum.hxgjs.getLabelKey();
		}else if(sign.equals(SmsSignEnum.cf.getValue())){
			sign = SmsSignEnum.cf.getLabelKey();
		}else{
			sign = SmsSignEnum.jd.getLabelKey();
		}
		SmsConfigEntity smsConfig = this.findBySign(sign);
		// 如果没设置短信通道，默认为短信通道为-发财鱼
		String type = SmsChannelEnum.fcy.getLabelKey();
		if(null != smsConfig){
			type = smsConfig.getSmsChannel();
		}
		return type;
	}
	
}