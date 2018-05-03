package com.zx.tjmarketmobile.entity.supervise;

import java.io.Serializable;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class MyTaskCheckEntity implements Serializable {


    /**
     * id : e908acd1322511e8b6a2000c2962b4b5
     * departmentId : 09
     * taskId : 7E0BB1434F194A3FA3386D4B660586F1
     * status : 1
     * illegal : null
     * remarks : null
     * checkDate : null
     * enterpriseId : 00F8C72595304D4C950022F86C221CBA
     * enterpriseName : 天津聚信食品贸易有限公司
     * licNum : null
     * bizlicNum : 91120116MA05JX9B2F
     * legalPerson : 单君
     * contactPerson : null
     * contactInfo : null
     * address : 天津经济技术开发区第二大街金业广场项目2号楼108
     */

    private String id;
    private String departmentId;
    private String taskId;
    private int status;
    private String illegal;
    private String remarks;
    private String checkDate;
    private String enterpriseId;
    private String enterpriseName;
    private String licNum;
    private String bizlicNum;
    private String legalPerson;
    private String contactPerson;
    private String contactInfo;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
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

    public String getLicNum() {
        return licNum;
    }

    public void setLicNum(String licNum) {
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

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}