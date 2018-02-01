package com.loki.server.utils;

import java.io.Serializable;
import java.util.List;

public class BTView<E> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/** 列表数据 **/
	private List<E> rows;
	/** 数据总条数 **/
	private long total = 0;
	
	/**每页显示条数，页码**/
	private Integer pageSize, pageNumber;
	/**搜索文本，排序列名，排序方式**/
	private String searchText, sortName, sortOrder;

	
	public BTView() {}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Integer getPageSize() {
		if(pageSize==null){
			this.pageSize = 10 ;
		}
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		if(pageNumber==null){
			this.pageNumber = 1 ;
		}
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
}
