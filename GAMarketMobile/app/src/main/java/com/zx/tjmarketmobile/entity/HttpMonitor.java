package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class HttpMonitor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3729164032190768794L;
	
	private int infoType;//返回信息类型 0：主体entity 1：任务task
	private int total;//任务监控-全部数量
	private int theOverdue;//任务监控-即将到期数量
	private int overdue;//任务监控-逾期数量
	private HttpMonitorEntityList monitorEntityList;
	private HttpMonitorTaskList monitorTaskList;
	
	public int getInfoType() {
		return infoType;
	}
	public void setInfoType(int infoType) {
		this.infoType = infoType;
	}
	public HttpMonitorEntityList getMonitorEntityList() {
		return monitorEntityList;
	}
	public void setMonitorEntityList(HttpMonitorEntityList monitorEntityList) {
		this.monitorEntityList = monitorEntityList;
	}
	public HttpMonitorTaskList getMonitorTaskList() {
		return monitorTaskList;
	}
	public void setMonitorTaskList(HttpMonitorTaskList monitorTaskList) {
		this.monitorTaskList = monitorTaskList;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTheOverdue() {
		return theOverdue;
	}
	public void setTheOverdue(int theOverdue) {
		this.theOverdue = theOverdue;
	}
	public int getOverdue() {
		return overdue;
	}
	public void setOverdue(int overdue) {
		this.overdue = overdue;
	}

	
}
