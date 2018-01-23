package com.gw.das.service.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.lazy.LazyRefreshable;
import com.gw.das.common.utils.SystemCacheUtil;
import com.gw.das.dao.system.entity.SystemDictEntity;
import com.gw.das.service.system.SystemDictService;

/**
 * 数据库字典缓存处理
 * 
 * @author wayne
 *
 */
public class DictCache {

	private static final Logger logger = LoggerFactory.getLogger(DictCache.class);

	/**
	 * Map<companyId + "_" + dictCode, LazyRefreshable<数据字典实体> dictMap
	 */
	private static Map<String, LazyRefreshable<SystemDictEntity>> dictMap = new HashMap<String, LazyRefreshable<SystemDictEntity>>();
	
	/**
	 * Map<companyId + "_" + dictCode, LazyRefreshable<List<子节点数据字典实体>> subDictListMap
	 */
	private static Map<String, LazyRefreshable<List<SystemDictEntity>>> subDictListMap = new HashMap<String, LazyRefreshable<List<SystemDictEntity>>>();

	/**
	 * 功能：提取字典列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return: SystemDictEntity
	 */
	public static SystemDictEntity getDictEntity(final String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		LazyRefreshable<SystemDictEntity> lazy = DictCache.dictMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<SystemDictEntity>(10*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected SystemDictEntity refresh() throws Exception{
					SystemDictService systemDictService = (SystemDictService)SystemCacheUtil.springContext.getBean("systemDictServiceImpl");
					SystemDictEntity dictEntity = systemDictService.findByDictCode(dictCode);
					return dictEntity;
				}
			};
			DictCache.dictMap.put(key, lazy);
		}
		SystemDictEntity dictEntity = lazy.get();
		return dictEntity;
	}
	
	/**
	 * 功能：提取子字典列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:List<SystemDictEntity>
	 */
	public static List<SystemDictEntity> getSubDictList(final String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		LazyRefreshable<List<SystemDictEntity>> lazy = DictCache.subDictListMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<List<SystemDictEntity>>(10*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected List<SystemDictEntity> refresh() throws Exception{
					SystemDictService systemDictService = (SystemDictService)SystemCacheUtil.springContext.getBean("systemDictServiceImpl");
					List<SystemDictEntity> dictList = systemDictService.findListByParentDictCode(dictCode, true);
					return dictList;
				}
			};
			DictCache.subDictListMap.put(key, lazy);
		}
		List<SystemDictEntity> subDictListMap = lazy.get();
		return subDictListMap;
	}
	
	public static String getDictNameByDictCode(String dictCode) throws Exception {
		SystemDictEntity entity = getDictEntity(dictCode);
		if (null != entity) {
			return entity.getDictName();
		}
		logger.info("该数据字典code不存在:" + dictCode);
		return dictCode;
	}
	
	/**
	 * 当修改是调用此方法进行强制刷新
	 */
	public static void refresh(String dictCode) throws Exception{
		String key = UserContext.get().getCompanyId() + "_" + dictCode;
		DictCache.dictMap.remove(key);
	}
	
	/**
	 * 当修改是调用此方法进行强制刷新
	 */
	public static void refreshSubDictListMap(String dictCode) throws Exception{
		if(StringUtils.isNotBlank(dictCode)){
			String key = UserContext.get().getCompanyId() + "_" + dictCode;
			DictCache.subDictListMap.remove(key);
		}
	}

}
