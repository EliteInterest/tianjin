package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckItemSecond implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3094796468367028691L;
	public String name;
	public boolean isExpand = false;
	public List<CheckInfo> threeList = new ArrayList<CheckInfo>();
	
	public CheckItemSecond(CheckItemSecond second) {
		this.name = second.name;
		this.threeList = second.threeList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isExpand() {
		return isExpand;
	}

	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

	public List<CheckInfo> getThreeList() {
		return threeList;
	}

	public void setThreeList(List<CheckInfo> threeList) {
		this.threeList = threeList;
	}
 
}
