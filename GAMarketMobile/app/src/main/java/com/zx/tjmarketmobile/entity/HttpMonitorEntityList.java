package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class HttpMonitorEntityList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2195864546041145360L;
	
	private int pageNo;
	private int pageSize;
	private int pageTotal;
	private int total;
//	private List<HttpMonitorEntity> entityList;
	private List<SuperviseInfo> entityList;
	
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
	public List<SuperviseInfo> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<SuperviseInfo> entityList) {
		this.entityList = entityList;
	}

}
