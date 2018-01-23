package com.gw.das.service.system;

import java.util.List;

import com.gw.das.common.easyui.DictTreeBean;
import com.gw.das.common.easyui.PageGrid;
import com.gw.das.dao.system.entity.SystemDictEntity;

public interface SystemDictService {

	public SystemDictEntity findById(Long id) throws Exception;
	public SystemDictEntity findByDictCode(String dictCode) throws Exception;
	public List<SystemDictEntity> findListAllCompany() throws Exception;
	public List<SystemDictEntity> findList() throws Exception;
	public List<SystemDictEntity> findList(SystemDictEntity dictEntity) throws Exception;
	public PageGrid<SystemDictEntity> findPageList(PageGrid<SystemDictEntity> pageGrid) throws Exception;
	/**
	 * @param parentDictCode
	 * @param enableFlag 是否查询启用的数据字典,查询之前先判断父数据字典是否启用
	 * @return
	 * @throws Exception
	 */
	public List<SystemDictEntity> findListByParentDictCode(String parentDictCode, boolean enableFlag) throws Exception;
	public void saveOrUpdate(SystemDictEntity entity) throws Exception;
	public void deleteById(Long id) throws Exception;
	public boolean checkDictCode(String dictCode, Long dictId) throws Exception;
	/**
	 * 提取树形菜单
	 */
	public PageGrid<SystemDictEntity> getDictTreeJson(PageGrid<SystemDictEntity> pageGrid) throws Exception;
	/**
	 * 提取树形子菜单
	 */
	public List<DictTreeBean> getSubDictTreeJson(String dictCode) throws Exception;
}
