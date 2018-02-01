package com.loki.server.entity;

import java.util.List;
import java.util.Map;

public class PagedResult <T> extends BaseEntity{
	private static final long serialVersionUID = 1L;
	//查询条件
	private Map queryConditions;
	
	//数据
	private List<T> rows;
	
	//当前页
	private long pageNo;
	
	//条数
	private long pageSize;
	
	//总条数
	private long total;
	
	//总页面数目
	private long pages;


	public long getPageNo() {
		return pageNo;
	}

	public void setPageNo(long pageNo) {
		this.pageNo = pageNo;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public Map getQueryConditions() {
		return queryConditions;
	}

	public void setQueryConditions(Map queryConditions) {
		this.queryConditions = queryConditions;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
