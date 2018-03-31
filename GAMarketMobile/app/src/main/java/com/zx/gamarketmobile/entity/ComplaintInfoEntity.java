package com.zx.gamarketmobile.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 投诉举报详情实体类
 */
public class ComplaintInfoEntity implements Serializable {

	private static final long serialVersionUID = 2102074386116758598L;
	public String fTaskName;
	public String fRegTime;// 登记时间
	public String fRegName;// 登记人
	public String fRegOrg;// 登记单位
	public String fName;// 投诉人
	public String fCellPhone;// 投诉人联系方式
	public String fContent;// 投诉内容
	public String fStatus;// 办理状态
	public String fRegId;// 登记编号
	public String fBriefInfo;// 简要信息
	public String fSource;// 信息来源
	public String fEntityName = "";
	public String fEntityPhone = "";
	public String fEntityType = "";
	public String fEntityAddress = "";
	public String fProductName = "";
	public String fBrandName = "";
	public String fcount = "";
	public String fMoney = "";
	public String fType = "";
	public String fEventAddress = "";
	public String fEventtime = "";
	public String fEconomyLost = "";
	public String fNature = "";
	public String fUrgency = "";
	public String fSignificant = "";
	public String fSpotNeed = "";
	public String fFollow = "";
	public String fRepeatedly = "";
	public String fGroup = "";
	public String fStation="";
	public List<DisposeInfo> processDynamic;
	
	public String getfStation() {
		return fStation;
	}

	public void setfStation(String fStation) {
		this.fStation = fStation;
	}


	public String getfType() {
		return fType;
	}

	public void setfType(String fType) {
		this.fType = fType;
	}

	public String getfEventAddress() {
		return fEventAddress;
	}

	public void setfEventAddress(String fEventAddress) {
		this.fEventAddress = fEventAddress;
	}

	public String getfEventtime() {
		return fEventtime;
	}

	public void setfEventtime(String fEventtime) {
		this.fEventtime = fEventtime;
	}

	public String getfEconomyLost() {
		return fEconomyLost;
	}

	public void setfEconomyLost(String fEconomyLost) {
		this.fEconomyLost = fEconomyLost;
	}

	public String getfNature() {
		return fNature;
	}

	public void setfNature(String fNature) {
		this.fNature = fNature;
	}

	public String getfUrgency() {
		return fUrgency;
	}

	public void setfUrgency(String fUrgency) {
		this.fUrgency = fUrgency;
	}

	public String getfSignificant() {
		return fSignificant;
	}

	public void setfSignificant(String fSignificant) {
		this.fSignificant = fSignificant;
	}

	public String getfSpotNeed() {
		return fSpotNeed;
	}

	public void setfSpotNeed(String fSpotNeed) {
		this.fSpotNeed = fSpotNeed;
	}

	public String getfFollow() {
		return fFollow;
	}

	public void setfFollow(String fFollow) {
		this.fFollow = fFollow;
	}

	public String getfRepeatedly() {
		return fRepeatedly;
	}

	public void setfRepeatedly(String fRepeatedly) {
		this.fRepeatedly = fRepeatedly;
	}

	public String getfGroup() {
		return fGroup;
	}

	public void setfGroup(String fGroup) {
		this.fGroup = fGroup;
	}

	public String getfEntityName() {
		return fEntityName;
	}

	public void setfEntityName(String fEntityName) {
		this.fEntityName = fEntityName;
	}

	public String getfEntityPhone() {
		return fEntityPhone;
	}

	public void setfEntityPhone(String fEntityPhone) {
		this.fEntityPhone = fEntityPhone;
	}

	public String getfEntityType() {
		return fEntityType;
	}

	public void setfEntityType(String fEntityType) {
		this.fEntityType = fEntityType;
	}

	public String getfEntityAddress() {
		return fEntityAddress;
	}

	public void setfEntityAddress(String fEntityAddress) {
		this.fEntityAddress = fEntityAddress;
	}

	public String getfProductName() {
		return fProductName;
	}

	public void setfProductName(String fProductName) {
		this.fProductName = fProductName;
	}

	public String getfBrandName() {
		return fBrandName;
	}

	public void setfBrandName(String fBrandName) {
		this.fBrandName = fBrandName;
	}

	public String getFcount() {
		return fcount;
	}

	public void setFcount(String fcount) {
		this.fcount = fcount;
	}

	public String getfMoney() {
		return fMoney;
	}

	public void setfMoney(String fMoney) {
		this.fMoney = fMoney;
	}

	public String getStatus() {
		return fStatus;
	}

	public String getSource() {
		return fSource;
	}

	public void setSource(String source) {
		this.fSource = source;
	}

	public void setStatus(String status) {
		this.fStatus = status;
	}

	public String getRegCode() {
		return fRegId;
	}

	public void setRegCode(String regCode) {
		this.fRegId = regCode;
	}

	public String getBriefInfo() {
		return fBriefInfo;
	}

	public void setBriefInfo(String briefInfo) {
		this.fBriefInfo = briefInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTaskName() {
		return this.fTaskName;
	}

	public void setTaskName(String taskName) {
		this.fTaskName = taskName;
	}

	public String getRegTime() {
		return this.fRegTime;
	}

	public void setRegTime(String regTime) {
		this.fRegTime = regTime;
	}

	public String getRegName() {
		return this.fRegName;
	}

	public void setRegName(String regName) {
		this.fRegName = regName;
	}

	public String getRegOrg() {
		return this.fRegOrg;
	}

	public void setRegOrg(String regOrg) {
		this.fRegOrg = regOrg;
	}

	public String getComplaintName() {
		return this.fName;
	}

	public void setComplaintName(String complaintName) {
		this.fName = complaintName;
	}

	public String getCellPhone() {
		return this.fCellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.fCellPhone = cellPhone;
	}

	public String getContent() {
		return this.fContent;
	}

	public void setContent(String content) {
		this.fContent = content;
	}

	@Override
	public String toString() {
		return "ComplaintInfoEntity [taskName=" + fTaskName + ", regTime=" + fRegTime + ", regName=" + fRegName
				+ ", regOrg=" + fRegOrg + ", complaintName=" + fName + ", cellPhone=" + fCellPhone + ", content="
				+ fContent + ", status=" + fStatus + ", regCode=" + fRegId + ", briefInfo=" + fBriefInfo + ", source="
				+ fSource + ", fEntityName=" + fEntityName + ", fEntityPhone=" + fEntityPhone + ", fEntityType="
				+ fEntityType + ", fEntityAddress=" + fEntityAddress + ", fProductName=" + fProductName
				+ ", fBrandName=" + fBrandName + ", fcount=" + fcount + ", fMoney=" + fMoney + "]";
	}

}
