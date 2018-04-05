package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class EntityListInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -433267406543077662L;
	
	private int currPageNo;
	private int pageSize;// 每页查询数
	private int pageTotal;// 总页数
	private int total;// 总条数
	private List<EntitySimpleInfo> entityList;// 查询结果
	
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

	public List<EntitySimpleInfo> getEntityList() {
		return entityList;
	}

	public void setEntityList(List<EntitySimpleInfo> entityList) {
		this.entityList = entityList;
	}

	@Override
	public String toString() {
		return "EntityLisiInfo [currPageNo=" + currPageNo + ", pageSize=" + pageSize + ", pageTotal=" + pageTotal
				+ ", total=" + total + ", entityList=" + entityList + "]";
	}
	
}
