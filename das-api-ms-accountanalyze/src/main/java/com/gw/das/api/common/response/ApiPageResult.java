package com.gw.das.api.common.response;

/**
 * 分页返回对象
 * 
 * @author darren
 *
 */
public class ApiPageResult extends ApiResult{

	private static final long serialVersionUID = 1L;
	
	// 每页多少条数据
	private int pageSize = 10;
	// 第几页
	private int pageNumber = 1;
	
	// 查询记录数
	private int total;
	
	public ApiPageResult() {
		super();
	}

	public ApiPageResult(ApiFailEnum failEnum) {
		super(failEnum);
	}

	public ApiPageResult(ApiStatusEnum statusEnum) {
		super(statusEnum);
	}
	
	public ApiPageResult(ApiStatusEnum statusEnum, Object result) {
		super(statusEnum, result);
	}

	public ApiPageResult(String errorMsg) {
		super(errorMsg);
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

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
}
