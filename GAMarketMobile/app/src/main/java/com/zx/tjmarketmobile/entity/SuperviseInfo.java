package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class SuperviseInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1137224446941893434L;
	
	public String taskId;
	public String taskName;
	public int timeType;
	public String dispatchTime;
	public String address;
	public String entityName;
	public String guid;
	public String entityGuid;
	public String fCreditLevel;
	public double longitude;
	public double latitude;
	
	@Override
	public String toString() {
		return "SuperviseInfo [taskId=" + taskId + ", taskName=" + taskName + ", timeType=" + timeType
				+ ", dispatchTime=" + dispatchTime + ", address=" + address + ", entityName=" + entityName + ", guid="
				+ guid + ", entityGuid=" + entityGuid + ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}

}
