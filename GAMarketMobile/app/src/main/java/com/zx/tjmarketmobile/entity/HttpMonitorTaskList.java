package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class HttpMonitorTaskList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6230062612551635930L;
	
	private List<SuperviseCountInfo> taskList;
	
	public List<SuperviseCountInfo> getTaskList() {
		return taskList;
	}
	public void setTaskList(List<SuperviseCountInfo> taskList) {
		this.taskList = taskList;
	}

}
