package com.gw.das.service.website;

import java.util.List;
import java.util.Map;

import com.gw.das.common.easyui.PageGrid;
import com.gw.das.common.enums.AccountTypeEnum;
import com.gw.das.dao.market.entity.DasUserScreenEntity;

public interface DasUserScreenService {

	public DasUserScreenEntity findById(Long id) throws Exception;
	public void saveOrUpdate(DasUserScreenEntity entity) throws Exception;
	public List<DasUserScreenEntity> findList(DasUserScreenEntity dasUserScreenEntity) throws Exception;
	public PageGrid<DasUserScreenEntity> findPageList(PageGrid<DasUserScreenEntity> pageGrid) throws Exception;
	public void deleteByIdArray(String idArray) throws Exception;
	
	/**
	 * 根据条件筛选统计[手机号或者邮箱]集合-调用netty接口
	 * @param screenIdAry 筛选条件ID集合
	 * @param accountTypeEnum 统计类型
	 */
	public void getTelOrEmailList(String[] screenIdAry, AccountTypeEnum accountTypeEnum, Map<String, String> map) throws Exception;
	
	/**
	 * 根据条件筛选统计[手机号或者邮箱]集合-调用netty接口
	 * @param screenIdList 筛选条件ID集合
	 * @param accountTypeEnum 统计类型
	 */
	public void getTelOrEmailList(List<String> screenIdList, AccountTypeEnum accountTypeEnum, Map<String, String> map) throws Exception;
	
	/**
	 * 根据条件筛选统计[手机号与邮箱]集合-调用netty接口
	 * @param screenIdList 筛选条件ID集合
	 */
	public void getTelAndEmailList(String[] screenIdAry, Map<String, String> telMap, Map<String, String> emailMap) throws Exception;
	
	/**
	 * 根据条件筛选统计[手机号与邮箱]集合-调用netty接口
	 * @param screenIdList 筛选条件ID集合
	 */
	public void getTelAndEmailList(List<String> screenIdList, Map<String, String> telMap, Map<String, String> emailMap) throws Exception;
	
	/**
	 * 根据条件筛选统计[手机号与邮箱]集合-调用netty接口
	 */
	public void getTelAndEmailList(DasUserScreenEntity model, Map<String, String> telMap, Map<String, String> emailMap) throws Exception;
	
}
