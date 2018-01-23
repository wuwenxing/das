package com.gw.das.service.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.enums.CompanyEnum;
import com.gw.das.common.lazy.LazyRefreshable;
import com.gw.das.common.utils.SystemCacheUtil;
import com.gw.das.dao.system.entity.SystemMenuEntity;
import com.gw.das.service.system.SystemMenuService;

/**
 * 菜单缓存处理
 * @author wayne
 *
 */
public class MenuCache {

	/**
	 * 缓存每个公司的菜单的Url
	 * Map<companyId, LazyRefreshable<Map<菜单Url,菜单Code>> dictMap
	 */
	private static Map<String, LazyRefreshable<Map<String, String>>> menuUrlMap = new HashMap<String, LazyRefreshable<Map<String, String>>>();

	/**
	 * 缓存每个公司的菜单的对象
	 * Map<companyId, LazyRefreshable<List<菜单对象>> dictMap
	 */
	private static Map<String, LazyRefreshable<List<SystemMenuEntity>>> menuObjMap = new HashMap<String, LazyRefreshable<List<SystemMenuEntity>>>();

	/**
	 * 缓存每个用户的菜单的对象
	 * Map<userId, LazyRefreshable<Map<companyId,List<菜单对象>>>> dictMap
	 */
	private static Map<String, LazyRefreshable<Map<String,List<SystemMenuEntity>>>> menuObjMapByUser = new HashMap<String, LazyRefreshable<Map<String,List<SystemMenuEntity>>>>();
	
	/**
	 * 功能：提取菜单Url列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<菜单Url,菜单Code>
	 */
	public static Map<String, String> getMenuUrlList(final String key) throws Exception{
		LazyRefreshable<Map<String, String>> lazy = MenuCache.menuUrlMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<Map<String, String>>(60*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected Map<String, String> refresh() throws Exception{
					Map<String, String> resultList = new HashMap<String, String>();
					SystemMenuService systemMenuService = (SystemMenuService)SystemCacheUtil.springContext.getBean("systemMenuServiceImpl");
					List<SystemMenuEntity> menuList = systemMenuService.findListByCompanyId(true, Long.parseLong(key));
					for (SystemMenuEntity menu : menuList) {
						if(null != menu && StringUtils.isNotBlank(menu.getMenuUrl())){
							resultList.put(menu.getMenuUrl(), menu.getMenuCode());
						}
			    	}
					return resultList;
				}
			};
			MenuCache.menuUrlMap.put(key, lazy);
		}
		Map<String, String> menuUrlList = lazy.get();
		return menuUrlList;
	}
	
	/**
	 * 默认当前登录的companyId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getMenuUrlList() throws Exception{
		return MenuCache.getMenuUrlList(UserContext.get().getCompanyId()+"");
	}

	/**
	 * 功能：提取菜单列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<code, String> map
	 */
	public static List<SystemMenuEntity> getMenuObjList(final String key) throws Exception{
		LazyRefreshable<List<SystemMenuEntity>> lazy = MenuCache.menuObjMap.get(key);
		if(lazy == null){
			lazy = new LazyRefreshable<List<SystemMenuEntity>>(60*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected List<SystemMenuEntity> refresh() throws Exception{
					SystemMenuService systemMenuService = (SystemMenuService)SystemCacheUtil.springContext.getBean("systemMenuServiceImpl");
					List<SystemMenuEntity> menuList = systemMenuService.findListByCompanyId(true, Long.parseLong(key));
					if(null != menuList && menuList.size() > 0){
						return menuList;
					}else{
						return new ArrayList<SystemMenuEntity>();
					}
				}
			};
			MenuCache.menuObjMap.put(key, lazy);
		}
		List<SystemMenuEntity> menuObjList = lazy.get();
		return menuObjList;
	}

	/**
	 * 默认当前登录的companyId
	 * @return
	 * @throws Exception
	 */
	public static List<SystemMenuEntity> getMenuObjList() throws Exception{
		return MenuCache.getMenuObjList(UserContext.get().getCompanyId()+"");
	}
	
	/**
	 * 功能：提取菜单列表(备注：先在缓存中提取，不存在再通过接口查询提取列表数据)
	 * return:Map<code, String> map
	 */
	public static Map<String, List<SystemMenuEntity>> getMenuObjListByUser(final String userId) throws Exception{
		LazyRefreshable<Map<String,List<SystemMenuEntity>>> lazy = MenuCache.menuObjMapByUser.get(userId);
		if(lazy == null){
			lazy = new LazyRefreshable<Map<String,List<SystemMenuEntity>>>(60*60*1000){
				private static final long serialVersionUID = 1L;
				@Override
				protected Map<String,List<SystemMenuEntity>> refresh() throws Exception{
					Map<String,List<SystemMenuEntity>> map = new HashMap<String,List<SystemMenuEntity>>();
					SystemMenuService systemMenuService = (SystemMenuService)SystemCacheUtil.springContext.getBean("systemMenuServiceImpl");
					boolean hasTopTag = true;// 是否包含顶部页签
					boolean hasMenu = true;//是否包含菜单
					boolean hasFun = true;// 是否包含功能
					for(CompanyEnum companyEnum: CompanyEnum.getList()){
						String comanyId = companyEnum.getLabelKey();
						List<SystemMenuEntity> menuList = systemMenuService.findListByUserIdAndCompanyId(hasTopTag, hasMenu, hasFun, Long.parseLong(userId), Long.parseLong(comanyId));
						map.put(comanyId, menuList);
					}
					return map;
				}
			};
			MenuCache.menuObjMapByUser.put(userId, lazy);
		}
		Map<String,List<SystemMenuEntity>> retu = lazy.get();
		return retu;
	}

	/**
	 * 默认当前登录的companyId
	 * @return
	 * @throws Exception
	 */
	public static List<SystemMenuEntity> getMenuObjListByUser2(String userId) throws Exception{
		String companyId = UserContext.get().getCompanyId() + "";
		Map<String, List<SystemMenuEntity>> map = MenuCache.getMenuObjListByUser(userId);
		return map.get(companyId);
	}
	
	/**
	 * 指定的companyId
	 * @return
	 * @throws Exception
	 */
	public static List<SystemMenuEntity> getMenuObjListByUser2(String userId, String companyId) throws Exception{
		Map<String, List<SystemMenuEntity>> map = MenuCache.getMenuObjListByUser(userId);
		return map.get(companyId);
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refresh() throws Exception{
		String key = UserContext.get().getCompanyId() + "";
		MenuCache.menuUrlMap.remove(key);
		MenuCache.menuObjMap.remove(key);
	}
	
	/**
	 * 当修改时调用此方法进行强制刷新
	 */
	public static void refreshByUser(String userId) throws Exception{
		MenuCache.menuObjMapByUser.remove(userId);
	}
	
}
