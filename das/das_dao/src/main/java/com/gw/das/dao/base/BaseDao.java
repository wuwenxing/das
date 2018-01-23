package com.gw.das.dao.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gw.das.common.context.UserContext;
import com.gw.das.common.easyui.PageGrid;

/**
 * Dao层公共方法
 * @author wayne
 */
public class BaseDao {

	@Autowired
	protected SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Object findById(Long id, Class<?> c) {
		return this.getCurrentSession().get(c, id);
	}

	public Object loadById(Long id, Class<?> c) {
		return this.getCurrentSession().load(c, id);
	}

	/**
	 * 新增时，设置公共的属性
	 * @param obj
	 * @return
	 */
	public Long save(BaseEntity obj) {
		Date date = new Date();
		obj.setCreateUser(UserContext.get().getLoginName());
		obj.setCreateDate(date);
		obj.setCreateIp(UserContext.get().getLoginIp());
		obj.setUpdateUser(UserContext.get().getLoginName());
		obj.setUpdateDate(date);
		obj.setUpdateIp(UserContext.get().getLoginIp());
		obj.setCompanyId(UserContext.get().getCompanyId());
		obj.setVersionNo(0L);
		obj.setEnableFlag("Y");
		obj.setDeleteFlag("N");
		Object ob = this.getCurrentSession().save(obj);
		return ob == null ? 0L : Long.parseLong(ob.toString());
	}

	public void saveAll(List<BaseEntity> objList) {
		for (int i = 0; i < objList.size(); i++) {
			this.save(objList.get(i));
		}
	}

	/**
	 * 更新时，设置公共的属性
	 * @param obj
	 * @return
	 */
	public void update(BaseEntity obj) {
		Date date = new Date();
		obj.setUpdateUser(UserContext.get().getLoginName());
		obj.setUpdateDate(date);
		obj.setUpdateIp(UserContext.get().getLoginIp());
		obj.setVersionNo((obj.getVersionNo()==null? 0:obj.getVersionNo()) + 1);
		this.getCurrentSession().update(obj);
	}

	public void updateAll(List<BaseEntity> objList) {
		for (int i = 0; i < objList.size(); i++) {
			this.update(objList.get(i));
		}
	}

	public void delete(Object obj) {
		this.getCurrentSession().delete(obj);
	}

	public void deleteById(Long id, Class<?> c) {
		this.delete(this.loadById(id, c));
	}

	public void deleteAll(List<?> objList) {
		for (int i = 0; i < objList.size(); i++) {
			this.delete(objList.get(i));
		}
	}

	public void deleteAllByIdList(List<String> idList, Class<?> c) {
		for (int i = 0; i < idList.size(); i++) {
			this.deleteById(Long.parseLong(idList.get(i)), c);
		}
	}

	public void deleteAllByIdArray(String[] idArray, Class<?> c) {
		for (int i = 0; i < idArray.length; i++) {
			this.deleteById(Long.parseLong(idArray[i]), c);
		}
	}

	/**
	 * sql批量更新语句
	 */
	public int executeUpdateBySql(String sql, Object paramObj) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		this.setParameterHandle(query, paramObj);
		return query.executeUpdate();
	}
	
	/**
	 * hql批量更新语句
	 */
	public int executeUpdateByHql(String hql, Object paramObj) {
		Query query = this.getCurrentSession().createQuery(hql);
		this.setParameterHandle(query, paramObj);
		return query.executeUpdate();
	}
	
	/**
	 * hql查询实体
	 */
	public Object findEntityByHql(String hql, Object paramObj) {
		Query query = this.getCurrentSession().createQuery(hql);
		this.setParameterHandle(query, paramObj);
		return query.uniqueResult();
	}

	/**
	 * hql查询List
	 */
	public List findListByHql(String hql, Object paramObj) {
		Query query = this.getCurrentSession().createQuery(hql);
		this.setParameterHandle(query, paramObj);
		return query.list();
	}

	/**
	 * hql分页查询,返回PageGrid
	 */
	public PageGrid findPageListByHql(String hql, Object paramObj, PageGrid pg) {
		int countNum = this.findCountNum(true, hql, paramObj);
		pg.setTotal(countNum);
		if (countNum > 0) {
			List list = this.findPageList(true, hql, paramObj, pg);
			pg.setRows(list);
		}
		return pg;
	}

	/**
	 * sql查询实体
	 */
	public Object findEntityBySql(String sql, Object paramObj) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		this.setParameterHandle(query, paramObj);
		return query.uniqueResult();
	}

	/**
	 * sql查询List 返回List<Object[]>对象
	 */
	public List<Object[]> findListBySql(String sql, Object paramObj) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		this.setParameterHandle(query, paramObj);
		List<Object[]> list = query.list();
		return list;
	}

	/**
	 * sql查询List 返回List<Map<String, String>>对象
	 */
	public List<Map<String, String>> findListBySql(String sql, Object paramObj, String... field) {
		Query query = this.getCurrentSession().createSQLQuery(sql);
		this.setParameterHandle(query, paramObj);
		List<Object[]> list = query.list();
		return toMapList(list, field);
	}

	/**
	 * sql分页查询,返回PageGrid PageGrid中的row为list<Object[]>对象
	 */
	public PageGrid findPageListBySql(String sql, Object paramObj, PageGrid pg) {
		int countNum = this.findCountNum(false, sql, paramObj);
		pg.setTotal(countNum);
		if (countNum > 0) {
			List<Object[]> list = this.findPageList(false, sql, paramObj, pg);
			pg.setRows(list);
		}
		return pg;
	}

	/**
	 * sql分页查询,返回PageGrid PageGrid中的row为List<Map<String, String>>对象
	 */
	public PageGrid findPageListBySql(String sql, Object paramObj, PageGrid pg, String... field) {
		int countNum = this.findCountNum(false, sql, paramObj);
		pg.setTotal(countNum);
		if (countNum > 0) {
			List<Object[]> list = this.findPageList(false, sql, paramObj, pg);
			pg.setRows(toMapList(list, field));
		}
		return pg;
	}

	/**
	 * 获取总数
	 */
	private int findCountNum(boolean hqlFlag, String hql, Object paramObj) {
		Query query = null;
		if (hqlFlag) {
			query = this.getCurrentSession().createQuery(this.getFindCountSQL(hql));
		} else {
			query = this.getCurrentSession().createSQLQuery(this.getFindCountSQL(hql));
		}
		
		this.setParameterHandle(query, paramObj);
		List list = query.list();
		if (null != list && list.size() > 0) {
			return Integer.parseInt(list.get(0).toString());
		} else {
			return 0;
		}
	}

	/**
	 * 分页查询
	 */
	private List findPageList(boolean hqlFlag, String hql, Object paramObj, PageGrid pg) {
		Query query = null;
		if (hqlFlag) {
			query = this.getCurrentSession().createQuery(this.setOrderField(pg, hql));
		} else {
			query = this.getCurrentSession().createSQLQuery(this.setOrderField(pg, hql));
		}

		this.setParameterHandle(query, paramObj);
		query.setFirstResult((pg.getPageNumber() - 1) * pg.getPageSize());
		query.setMaxResults(pg.getPageSize());
		return query.list();
	}

	/**
	 * 获取查询总数sql语句
	 */
	private String getFindCountSQL(String sql) {
		StringBuffer sb = new StringBuffer("SELECT COUNT(*) ");
		int index = sql.toUpperCase().indexOf("FROM");
		sb.append(sql.substring(index, sql.length()));
		return sb.toString();
	}

	/**
	 * 设置排序字段
	 */
	private String setOrderField(PageGrid pg, String hql) {
		String orderSql = "";
		if (StringUtils.isNotBlank(pg.getSort())) {
			if (StringUtils.isNotBlank(pg.getOrder())) {
				orderSql = " order by " + pg.getSort() + " " + pg.getOrder();
			} else {
				orderSql = " order by " + pg.getSort();
			}
		}
		return hql + orderSql;
	}

	/**
	 * 将list<Object[]> 转换为List<Map<String, String>>对象
	 */
	private List<Map<String, String>> toMapList(List<Object[]> list, String... field) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < field.length; j++) {
				String fieldName = field[j];
				Object fieldValueObj = obj[j];
				String fieldValue = fieldValueObj == null ? "" : fieldValueObj.toString();
				map.put(fieldName, fieldValue);
			}
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 参数处理
	 */
	private void setParameterHandle(Query query, Object paramObj){
		if(paramObj instanceof Map){// Map集合类型参数
			Map<String, Object> paramMap = (Map<String, Object>)paramObj;
			for(Map.Entry<String, Object> entry : paramMap.entrySet()){
				if(entry.getValue() instanceof List){
					query.setParameterList(entry.getKey(), (List<Object>)entry.getValue());
				}else{
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}else if(paramObj instanceof List){// List集合类型参数
			List<Object> paramList = (List<Object>)paramObj;
			if (null != paramList && paramList.size() > 0) {
				for (int i = 0; i < paramList.size(); i++) {
					query.setParameter(i, paramList.get(i));
				}
			}
		}
	}
	
	
}
