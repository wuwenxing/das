package com.gw.das.business.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * easyui对应的分页实体类封装
 * @author wayne
 */
public class PageGrid<T> implements Serializable {

	private static final long serialVersionUID = -6299786215016725835L;
	
	// 结果集
	@SuppressWarnings("rawtypes")
	private List rows = new ArrayList();
	// 查询记录数
	private int total;
	// 每页多少条数据
	private int pageSize = 10;
	// 第几页
	private int pageNumber = 1;
	// 排序字段名，多个字段逗号分隔
	private String sort;
	// 排序字段顺序，多个字段逗号分隔
	private String order;
	// 查询对象
	private T searchModel;
		
	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getTotalPages() {
		return (total + pageSize - 1) / pageSize;
	}

	/**
	 * 取得首页
	 * 
	 * @return
	 */
	public int getTopPageNo() {
		return 1;
	}

	/**
	 * 上一页
	 * 
	 * @return
	 */
	public int getUpPageNo() {
		if (pageNumber <= 1) {
			return 1;
		}
		return pageNumber - 1;
	}

	/**
	 * 下一页
	 * 
	 * @return
	 */
	public int getNextPageNo() {
		if (pageNumber >= getBottomPageNo()) {
			return getBottomPageNo();
		}
		return pageNumber + 1;
	}

	/**
	 * 取得尾页
	 * 
	 * @return
	 */
	public int getBottomPageNo() {
		return getTotalPages();
	}

	public void setRows(@SuppressWarnings("rawtypes") List rows) {
		this.rows = rows;
	}

	@SuppressWarnings("rawtypes")
	public List getRows() {
		return rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public T getSearchModel() {
		return searchModel;
	}

	public void setSearchModel(T searchModel) {
		this.searchModel = searchModel;
	}
}