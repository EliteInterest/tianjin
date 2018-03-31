package com.zx.gamarketmobile.entity;

import java.io.Serializable;

public class CheckInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5973239630316062759L;
	public String fCheckResult;
	public String fGuid;
	public String fItemId;
	public String fItemName;
	public String getfCheckResult() {
		return fCheckResult;
	}

	public void setfCheckResult(String fCheckResult) {
		this.fCheckResult = fCheckResult;
	}

	public String getfGuid() {
		return fGuid;
	}

	public void setfGuid(String fGuid) {
		this.fGuid = fGuid;
	}

	public String getfItemId() {
		return fItemId;
	}

	public void setfItemId(String fItemId) {
		this.fItemId = fItemId;
	}

	public String getfItemName() {
		return fItemName;
	}

	public void setfItemName(String fItemName) {
		this.fItemName = fItemName;
	}

	public String getfValueType() {
		return fValueType;
	}

	public void setfValueType(String fValueType) {
		this.fValueType = fValueType;
	}

	public String getfValueMax() {
		return fValueMax;
	}

	public void setfValueMax(String fValueMax) {
		this.fValueMax = fValueMax;
	}

	public String getfValueMin() {
		return fValueMin;
	}

	public void setfValueMin(String fValueMin) {
		this.fValueMin = fValueMin;
	}

	public String getfDepartment() {
		return fDepartment;
	}

	public void setfDepartment(String fDepartment) {
		this.fDepartment = fDepartment;
	}

	public String getfBizType() {
		return fBizType;
	}

	public void setfBizType(String fBizType) {
		this.fBizType = fBizType;
	}

	public String fValueType;
	public String fValueMax;
	public String fValueMin;
	public String fDepartment;
	public String fBizType;
	
	@Override
	public String toString() {
		return "CheckInfo [fCheckResult=" + fCheckResult + ", fGuid=" + fGuid + ", fItemId=" + fItemId + ", fItemName="
				+ fItemName + ", fValueType=" + fValueType + ", fValueMax=" + fValueMax + ", fValueMin=" + fValueMin
				+ "]";
	}
	
}
