package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class HttpTaskEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6808621964708825110L;

	private String guid;
	private String taskName;//任务名称
	private String entityGuid;//主体id
	private String entityName;//主体名称
	private String taskId;//任务编号
	private String source;//任务来源
	private String taskTime;//下发时间
	private String deadline;//办理时限
	private String remark;//任务说明
	private String submitRemark;//提交说明
	private String address;//任务地址
	private String status;//办理状态
	
	private int taskType;//任务类型 0：监管任务 1：投诉举报 2：应急任务
	private int timeType;//紧急程度 1：全部 2：即将到期 3：逾期
	
	private double longitude;
	private double latitude;
	private int wkid;//投影坐标系ID
	
	private HttpZtEntity zt;//任务相关主体
	
	private List<CheckItemInfoEntity> checkItemList;
	private ComplainInfoEntity complaintinfo;
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public String getEntityGuid() {
		return this.entityGuid;
	}
	
	public void setEntityGuid(String entityGuid) {
		this.entityGuid = entityGuid;
	}
	
	public String getEntityName() {
		return this.entityName;
	}
	
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
	public String getTaskId() {
		return this.taskId;
	}
	
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getDeadline() {
		return this.deadline;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getSubmitRemark() {
		return submitRemark;
	}

	public void setSubmitRemark(String submitRemark) {
		this.submitRemark = submitRemark;
	}
	
	public String getTaskTime() {
		return this.taskTime;
	}
	
	public void setTaskTime(String taskTime) {
		this.taskTime = taskTime;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getTaskType() {
		return this.taskType;
	}
	
	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}
	
	public int getTimeType() {
		return this.timeType;
	}
	
	public void setTimeType(int timeType) {
		this.timeType = timeType;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public int getWkid() {
		return this.wkid;
	}
	
	public void setWkid(int wkid) {
		this.wkid = wkid;
	}
	
	public HttpZtEntity getZtEntity() {
		return this.zt;
	}
	
	public void  setZtEntity(HttpZtEntity zt) {
		this.zt = zt;
	}
	
	public List<CheckItemInfoEntity> getCheckItemList() {
		return checkItemList;
	}

	public void setCheckItemList(List<CheckItemInfoEntity> checkItemList) {
		this.checkItemList = checkItemList;
	}
	
	public ComplainInfoEntity getComplaintInfo() {
		return this.complaintinfo;
	}
	
	public void  setComplaintInfo(ComplainInfoEntity complaintinfo) {
		this.complaintinfo = complaintinfo;
	}
	
}
