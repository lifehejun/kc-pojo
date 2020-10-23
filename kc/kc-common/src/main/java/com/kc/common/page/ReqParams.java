package com.kc.common.page;

import java.io.Serializable;

public class ReqParams<T> implements Serializable {

	/**  
	 * @Fields field:{todo}(用一句话描述这个变量表示什么)  
	 */  
	private static final long serialVersionUID = -6888361125282377704L;

	/**
	 * 开始条数
	 */
	private Integer startRow;


	private Integer page;

	private Integer limit;

	/**
	 * 每页显示数量
	 */
	private Integer pageSize;
	
	
	/**
	 * 实体类对象
	 */
	private T t;


	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
