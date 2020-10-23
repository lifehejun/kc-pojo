package com.kc.common.page;

import java.util.List;

/**
 * @ClassName: Page  
 * @Description: TODO 分页数据
 * @author jason  
 * @date 2017-8-31  
 * @param <T>
 */
public class Page<T> {
	
	/**
	 * 总数
	 */
	private int count;
	
	
	/**
	 * 数据
	 */
	private List<T> data;

	
	public Page() {
		
	}
	
    public Page(List<T> data, int count) {
		this.data = data;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
