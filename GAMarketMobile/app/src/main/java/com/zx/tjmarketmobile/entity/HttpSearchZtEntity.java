package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 命名方式:Http + 协议名 + Entity
 * 
 */
public class HttpSearchZtEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currPageNo;
	private int pageSize;//每页查询数
	private int pageTotal;//总页数
	private int total;//总条数
	private List<HttpZtEntity> ztList;//查询结果
	
	public int getCurrPageNo() {
		return currPageNo;
	}

	public void setCurrPageNo(int currPageNo) {
		this.currPageNo = currPageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public List<HttpZtEntity> getZtList() {
		return ztList;
	}

	public void setZtList(List<HttpZtEntity> ztList) {
		this.ztList = ztList;
	}
	
	public boolean isFirstPage() {
		return currPageNo == 1;
	}
	
	public boolean isLastPage() {
		return currPageNo == pageTotal;
	}
}
