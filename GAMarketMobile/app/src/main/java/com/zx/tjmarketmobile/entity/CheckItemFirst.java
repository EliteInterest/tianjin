package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckItemFirst implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5771012889680510030L;
	
	private String name;
	private boolean isExpand = false;
	private List<CheckItemSecond> secondList = new ArrayList<CheckItemSecond>();
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
	public List<CheckItemSecond> getSecondList() {
		return secondList;
	}
	public void setSecondList(List<CheckItemSecond> secondList) {
		this.secondList = secondList;
	}

}
