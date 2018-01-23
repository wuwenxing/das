package com.gw.das.mongodb;

import java.io.Serializable;
import java.util.HashMap;

import com.gw.das.common.utils.StringUtil;

/**
 * 摘要：参数查询对象
 * @author Gavin.guo
 * @date   2015年2月4日
 */
public class DetachedCriteria<T> implements Serializable{
	private static final long serialVersionUID = -5919547734875134104L;
	private int pageNo;//页号
	private int pageSize;//每页大小
	private int currRecordSize;//当前记录数
	private T searchModel;//查询对象
	private HashMap<String,SortDirection> orderbyMap;//排序对象
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public T getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(T searchModel) {
		this.searchModel = searchModel;
	}
	public HashMap<String, SortDirection> getOrderbyMap() {
		return orderbyMap;
	}
	public void setOrderbyMap(HashMap<String, SortDirection> orderbyMap) {
		if(orderbyMap!=null){
			HashMap<String, SortDirection> newOrderbyMap=new HashMap<String, SortDirection>();
	    	for(String key:orderbyMap.keySet()){
	    		newOrderbyMap.put(StringUtil.underscoreName(key), orderbyMap.get(key));
	    	}
	    	this.orderbyMap = newOrderbyMap;
	    }else{
	    	this.orderbyMap = orderbyMap;
	    }
	}
	
	public int getCurrRecordSize() {
		currRecordSize=(pageNo-1)*pageSize;
		return currRecordSize;
	}
	public void setCurrRecordSize(int currRecordSize) {
		this.currRecordSize = currRecordSize;
	}
}
