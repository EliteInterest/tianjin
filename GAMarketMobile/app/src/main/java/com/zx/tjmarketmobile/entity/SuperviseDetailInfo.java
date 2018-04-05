package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class SuperviseDetailInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -9134242971632797108L;

    private String taskCode;
    private String taskName;
    private String taskResource;
    private String taskDispatchTime;// 下发时间
    private String taskDeadlineTime;// 截止时间
    private String taskProgress;
    private String taskArea;
    private String taskRemark;
    private String guid;
    private String entityId;
    private String entityName;
    private String fLeaderUserId;
    private String fReviewUserId;

    private String fUniscid;
    private String creditLevel;// 信用等级
    private String linkPhone;
    private String legalPerson;// 法定代表
    private String disposePerson;//  负责人
    private String bizlicCode;// 营业执照注册号
    private String orgCode;// 组织机构代码
    private String licenses;// 许可证
    private String entityAddress;// 主体地址
    private String result;//办理结果（处置结论）
    private double longitude;
    private double latitude;
    private List<CheckItemFirst> menuList;
    private List<CheckInfo> checkList;// 检查指标
    private List<SuperviseDisposeInfo> taskLog;// 检查指标

    @Override
    public String toString() {
        return "SuperviseDetailInfo [taskName=" + taskName + ", taskProgress=" + taskProgress + "]";
    }

    public String getfLeaderUserId() {
        return fLeaderUserId;
    }

    public void setfLeaderUserId(String fLeaderUserId) {
        this.fLeaderUserId = fLeaderUserId;
    }

    public String getfReviewUserId() {
        return fReviewUserId;
    }

    public void setfReviewUserId(String fReviewUserId) {
        this.fReviewUserId = fReviewUserId;
    }

    public String getfUniscid() {
        return fUniscid;
    }

    public void setfUniscid(String fUniscid) {
        this.fUniscid = fUniscid;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskResource() {
        return taskResource;
    }

    public void setTaskResource(String taskResource) {
        this.taskResource = taskResource;
    }

    public String getTaskDispatchTime() {
        return taskDispatchTime;
    }

    public void setTaskDispatchTime(String taskDispatchTime) {
        this.taskDispatchTime = taskDispatchTime;
    }

    public String getTaskDeadlineTime() {
        return taskDeadlineTime;
    }

    public void setTaskDeadlineTime(String taskDeadlineTime) {
        this.taskDeadlineTime = taskDeadlineTime;
    }

    public String getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(String taskProgress) {
        this.taskProgress = taskProgress;
    }

    public String getTaskArea() {
        return taskArea;
    }

    public void setTaskArea(String taskArea) {
        this.taskArea = taskArea;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getDisposePerson() {
        return disposePerson;
    }

    public void setDisposePerson(String disposePerson) {
        this.disposePerson = disposePerson;
    }

    public String getBizlicCode() {
        return bizlicCode;
    }

    public void setBizlicCode(String bizlicCode) {
        this.bizlicCode = bizlicCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getLicenses() {
        return licenses;
    }

    public void setLicenses(String licenses) {
        this.licenses = licenses;
    }

    public String getEntityAddress() {
        return entityAddress;
    }

    public void setEntityAddress(String entityAddress) {
        this.entityAddress = entityAddress;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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

    public List<CheckItemFirst> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<CheckItemFirst> menuList) {
        this.menuList = menuList;
    }

    public List<CheckInfo> getCheckList() {
        return checkList;
    }

    public void setCheckList(List<CheckInfo> checkList) {
        this.checkList = checkList;
    }

    public List<SuperviseDisposeInfo> getTaskLog() {
        return taskLog;
    }

    public void setTaskLog(List<SuperviseDisposeInfo> taskLog) {
        this.taskLog = taskLog;
    }

}
