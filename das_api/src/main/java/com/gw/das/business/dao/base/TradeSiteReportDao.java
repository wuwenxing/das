package com.gw.das.business.dao.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.gw.das.business.common.orm.OrmUtil;
import com.gw.das.business.common.page.PageGrid;
import com.gw.das.business.common.utils.SqlUtil;

/**
 * 交易报表数据Dao
 * 
 * @author darren
 *
 */
@Repository
public abstract class TradeSiteReportDao extends BaseDao {

	@Autowired
	private JdbcTemplate jdbcTradeSiteReportTemplate;

	@Override
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTradeSiteReportTemplate;
	}

	/**
	 * 不分页查询
	 * 
	 * @throws Exception
	 */
	public <T extends BaseSearchModel, E extends BaseModel> List<E> queryForList(T searchModel, E e) throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, searchModel, paramList);
		sql.append(SqlUtil.getOrderField(searchModel.getSort(), searchModel.getOrder()));
		List<Map<String, Object>> list = super.queryForList(sql.toString(), paramList);
		List<E> detailList = (List<E>) OrmUtil.reflectList(e.getClass(), list);
		return detailList;
	}

	/**
	 * 分页查询
	 * 
	 * @throws Exception
	 */
	public <T extends BaseSearchModel, E extends BaseModel> PageGrid<T> queryForPage(PageGrid<T> pg, E e)
			throws Exception {
		List<Object> paramList = new ArrayList<Object>();
		BaseSearchModel searchModel = pg.getSearchModel();
		StringBuffer sql = new StringBuffer();
		this.getPubParam(sql, searchModel, paramList);
		pg = super.queryForListPage(pg, sql.toString(), paramList);
		List<Map<String, Object>> list = pg.getRows();
		List<E> detailList = (List<E>) OrmUtil.reflectList(e.getClass(), list);
		pg.setRows(detailList);
		return pg;
	}

	public abstract <T extends BaseSearchModel> void getPubParam(StringBuffer sql, T searchModel,
			List<Object> paramList);

}
