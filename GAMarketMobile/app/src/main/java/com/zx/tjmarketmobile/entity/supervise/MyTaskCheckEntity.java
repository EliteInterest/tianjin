package com.zx.tjmarketmobile.entity.supervise;

import java.io.Serializable;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class MyTaskCheckEntity implements Serializable {

    /**
     * id : 4cbd573ac9ed11e79fcc000c2934879e
     * departmentId : null
     * taskId : 6B317F3C5EDD48DFABE66DC0AD4234D8
     * status : 0
     * illegal : null
     * remarks : null
     * checkDate : null
     * enterpriseId : d97806f2979d11e781d1000c2934879e
     * enterpriseName : 天津市滨海新区多盈小吃部
     * licNum : null
     * bizlicNum : 92120116MA05RPCW53
     * legalPerson : 汪友胜
     * contactPerson : null
     * contactInfo :
     */

    private String id;
    private Object departmentId;
    private String taskId;
    private int status;
    private String illegal;
    private Object remarks;
    private long checkDate;
    private String enterpriseId;
    private String enterpriseName;
    private Object licNum;
    private String bizlicNum;
    private String legalPerson;
    private Object contactPerson;
    private String contactInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Object departmentId) {
        this.departmentId = departmentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIllegal() {
        return illegal;
    }

    public void setIllegal(String illegal) {
        this.illegal = illegal;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public long getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(long checkDate) {
        this.checkDate = checkDate;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public Object getLicNum() {
        return licNum;
    }

    public void setLicNum(Object licNum) {
        this.licNum = licNum;
    }

    public String getBizlicNum() {
        return bizlicNum;
    }

    public void setBizlicNum(String bizlicNum) {
        this.bizlicNum = bizlicNum;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public Object getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Object contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}