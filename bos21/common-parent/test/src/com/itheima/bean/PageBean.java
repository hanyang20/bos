package com.itheima.bean;

import java.util.ArrayList;
import java.util.List;

public class PageBean<T> {
	//当前
	// 显示当前页数
	private int currentPage;
	// 显示当前页数显示的条数
	private int currentCount;
	// 总页数
	private int totalPage;
	// 总条数
	private int totalCount;
	//每页显示的商品集合
	private List<T> list;
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageBean [currentPage=" + currentPage + ", currentCount=" + currentCount + ", totalPage=" + totalPage
				+ ", totalCount=" + totalCount + ", list=" + list + "]";
	}
	
	
	
	
	
	
}
