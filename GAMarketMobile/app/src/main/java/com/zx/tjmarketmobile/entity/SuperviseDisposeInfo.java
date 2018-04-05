package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class SuperviseDisposeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632095700449517663L;
	public String getfHandleOpt() {
		return fHandleOpt;
	}
	public void setfHandleOpt(String fHandleOpt) {
		this.fHandleOpt = fHandleOpt;
	}
	public String getfRealName() {
		return fRealName;
	}
	public void setfRealName(String fRealName) {
		this.fRealName = fRealName;
	}
	public String getfHandleTime() {
		return fHandleTime;
	}
	public void setfHandleTime(String fHandleTime) {
		this.fHandleTime = fHandleTime;
	}
	public String getfHandleRemark() {
		return fHandleRemark;
	}
	public void setfHandleRemark(String fHandleRemark) {
		this.fHandleRemark = fHandleRemark;
	}
	public String fHandleOpt;
	public String fRealName;
	public String fHandleTime;
	public String fHandleRemark;
}
