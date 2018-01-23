package com.gw.das.api.common.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gw.das.api.common.response.ApiPageResult;
import com.gw.das.api.common.response.ApiResult;

/**
 * Dao基础类
 * 
 * @author darren
 *
 */
public class BaseDao{

	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);
	
	@PersistenceContext
	private EntityManager em;
	
	
	/**
	 * 查询List-不分页
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiResult queryForList(ApiResult result, String sql, Map<String, Object> args) throws Exception {	
		this.log(sql, args);
		Query query = em.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);		
		query = getParameter(query,args);			
		
		List list = query.getResultList();		
		result.setResult(list);
		return result;
	}
	/**
	 * 查询List-分页
	 * 
	 * @param result
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public ApiPageResult queryForListPage(ApiPageResult result, String sql, Map<String, Object> args) throws Exception {
		String sort = null;
		String order = null;
		if(args.containsKey("sort")){
			sort = (String) args.get("sort");
			order = (String) args.get("order");
			args.remove("sort");
			args.remove("order");
		}
		int countNum = this.findCountNum(sql, args);
		result.setTotal(countNum);
		if (countNum > 0) {
			logger.info("SQL_LOG-->>page[pageNumber = " + result.getPageNumber() + ", pageSize=" + result.getPageSize() + "]");
			int firstResult = (result.getPageNumber() - 1) * result.getPageSize();
			int maxResults = result.getPageSize();	
			StringBuffer pageSQL = new StringBuffer(sql);
			if(StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)){
				pageSQL.append(this.getOrderField(sort,order));
			}
			pageSQL.append(" LIMIT :rows OFFSET :offset ");
			
			this.log(pageSQL.toString(), args);
			Query query = em.createNativeQuery(pageSQL.toString());
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			
			
			query = getParameter(query,args);			
			query.setParameter("rows", maxResults);
			query.setParameter("offset", firstResult);
			
			List list = query.getResultList();		
			result.setResult(list);
		}
		return result;
	}
	
	/**
	 * 获取总数
	 */
	private int findCountNum(String sql, Map<String, Object> args) throws Exception {
		String countSQL = this.getFindCountSQL(sql);
		Query query = em.createNativeQuery(countSQL);
		query = getParameter(query,args);
		int total = Integer.valueOf(query.getSingleResult().toString());
	    return total;
	}
	
	/**
	 * 获取查询总数sql语句
	 */
	private String getFindCountSQL(String sql) throws Exception {
		StringBuffer pageSQL = new StringBuffer(" SELECT COUNT(*) from ( ");
		pageSQL.append(sql);
		pageSQL.append(" ) COU_T ");
		return pageSQL.toString();
	}
	
	/**
	 * 获取查询sql语句参数
	 */
	private Query getParameter(Query query, Map<String, Object> args) throws Exception {
		for (Map.Entry<String, Object> entry : args.entrySet()) { 
			query.setParameter(entry.getKey(), entry.getValue());
		}
		return query;
	}
	
	/**
	 * 获取排序SQL
	 */
	private String getOrderField(String sortStr,String orderStr) throws Exception {
		String orderSql = "";
		if (StringUtils.isNotBlank(sortStr)) {
			orderSql += " order by ";
			if (StringUtils.isNotBlank(orderStr)) {
				String[] sortAry = sortStr.split(",");
				String[] orderAry = orderStr.split(",");
				for(int i=0; i<sortAry.length; i++){
					String sort = sortAry[i];
					String order = "";
					if(i<orderAry.length){
						order = orderAry[i];
					}else{
						order = "ASC";
					}
					if((i+1) == sortAry.length){
						orderSql += " " + sort + " " + order;
					}else{
						orderSql += " " + sort + " " + order + ",";
					}
				}
			} else {
				String[] sortAry = sortStr.split(",");
				for(int i=0; i<sortAry.length; i++){
					String sort = sortAry[i];
					if((i+1) == sortAry.length){
						orderSql += " " + sort;
					}else{
						orderSql += " " + sort + ",";
					}
				}
			}
		}
		return orderSql;
	}
	
	/**
	 * 打印sql日志
	 * 
	 * @return
	 */
	private void log(String sql, Map<String, Object> args) {
		logger.info("SQL_LOG-->>sql[" + sql.toString() + "]");
		if (null != args) {
			int i = 0;
			for (Map.Entry<String, Object> entry : args.entrySet()) { 
				logger.info("SQL_LOG-->>args[" + i + "]=" + entry.getValue());
				i++;
			}
		} else {
			logger.info("SQL_LOG-->>args is null");
		}
	}
	
}
