package com.gw.das.business.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gw.das.business.common.page.PageGrid;

/**
 * Dao层公共方法
 * 
 * @author wayne
 */
public abstract class BaseDao {

	private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

	public abstract JdbcTemplate getJdbcTemplate();

	/**
	 * insert update
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public int insertOrUpdate(String sql) throws Exception {
		this.log(sql, null);
		return this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * insert update
	 * @param sql
	 * @param args
	 * @return
	 * @throws Exception
	 */
	public int insertOrUpdate(String sql, List<Object> list) throws Exception {
		Object[] args = getObjectArr(list);
		this.log(sql, args);
		return this.getJdbcTemplate().update(sql, args);
	}
	
	private Object[]  getObjectArr(List<Object> list){
		Object[] args = new Object[list.size()];
		for (int i = 0; i < list.size(); i++) {
			args[i] = list.get(i);
		}
		return args;
	}
	
	/**
	 * 查询List-不分页
	 */
	public List<Map<String, Object>> queryForList(String sql, List<Object> list) throws Exception {
		Object[] args = getObjectArr(list);
		this.log(sql, args);
		return this.getJdbcTemplate().queryForList(sql, args);
	}

	/**
	 * 查询List-不分页
	 */
	public List<Map<String, Object>> queryForList(String sql, Object[] args) throws Exception {
		this.log(sql, args);
		return this.getJdbcTemplate().queryForList(sql, args);
	}

	/**
	 * 查询List-不分页
	 */
	public List<Map<String, Object>> queryForList(String sql) throws Exception {
		this.log(sql, null);
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 查询一条信息
	 */
	public Map<String, Object> queryForMap(String sql,List<Object> list) throws Exception {
		Object[] args = getObjectArr(list);
		this.log(sql, args);
		List<Map<String, Object>> resList = this.getJdbcTemplate().queryForList(sql,args);
		return resList.size() > 0 ? resList.get(0) : null;
	}

	/**
	 * 查询一条信息
	 */
	public Map<String, Object> queryForMap(String sql) throws Exception {
		this.log(sql, null);
		List<Map<String, Object>> list = this.getJdbcTemplate().queryForList(sql);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 查询List-分页
	 */
	public PageGrid queryForListPage(PageGrid pg, String sql, List<Object> list) throws Exception {
		Object[] args = getObjectArr(list);
		return this.queryForListPage(pg, sql, args);
	}

	/**
	 * 查询List-分页
	 */
	public PageGrid queryForListPage(PageGrid pg, String sql, Object[] args) throws Exception {
		int countNum = this.findCountNum(sql, args);
		pg.setTotal(countNum);
		if (countNum > 0) {
			logger.info("SQL_LOG-->>page[pageNumber = " + pg.getPageNumber() + ", pageSize=" + pg.getPageSize() + "]");
			int firstResult = (pg.getPageNumber() - 1) * pg.getPageSize();
			int maxResults = pg.getPageSize();

			StringBuffer pageSQL = new StringBuffer(" SELECT * from ( ");
			pageSQL.append(sql);
			pageSQL.append(" ) COU_T ");
			pageSQL.append(this.getOrderField(pg));
			pageSQL.append(" limit " + maxResults + " offset " + firstResult);

			this.log(pageSQL.toString(), args);
			List<?> list = this.getJdbcTemplate().queryForList(pageSQL.toString(), args);
			pg.setRows(list);
		}
		return pg;
	}

	/**
	 * 查询List-分页
	 */
	public PageGrid queryForListPage(PageGrid pg, String sql) throws Exception {
		return this.queryForListPage(pg, sql, new Object[] {});
	}

	/**
	 * queryForInt
	 */
	public int queryForInt(String sql, List<Object> list) throws Exception {
		Object[] args = getObjectArr(list);
		this.log(sql, args);
		return this.getJdbcTemplate().queryForInt(sql, args);
	}

	/**
	 * queryForInt
	 */
	public int queryForInt(String sql, Object[] args) throws Exception {
		this.log(sql, args);
		return this.getJdbcTemplate().queryForInt(sql, args);
	}
	
	/**
	 * 获取总数
	 */
	private int findCountNum(String sql, Object[] args) throws Exception {
		String countSQL = this.getFindCountSQL(sql);
		this.log(countSQL, args);
		return this.getJdbcTemplate().queryForInt(countSQL, args);
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
	 * 获取排序SQL
	 */
	private String getOrderField(PageGrid pg) throws Exception {
		String orderSql = "";
		String sortStr = pg.getSort();
		String orderStr = pg.getOrder();
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
	private void log(String sql, Object[] args) {
		logger.info("SQL_LOG-->>sql[" + sql.toString() + "]");
		if (null != args) {
			for (int i = 0; i < args.length; i++) {
				logger.info("SQL_LOG-->>args[" + i + "]=" + args[i]);
			}
		} else {
			logger.info("SQL_LOG-->>args is null");
		}
	}

}
