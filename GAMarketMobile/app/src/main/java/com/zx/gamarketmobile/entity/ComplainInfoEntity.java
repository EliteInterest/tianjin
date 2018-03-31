package com.zx.gamarketmobile.entity;

import java.io.Serializable;

/**
 * Created by Xiangb on 2017/3/21.
 * 功能：投诉举报信息
 */
public class ComplainInfoEntity implements Serializable {

    private String fReportedPerson;//投诉人
    private String fType;//投诉类型
    private String fByReportedAddress;//投诉地址
    private String fRegId;//投诉id
    private String fByReportedTel;//通过**电话投诉
    private String fRegPerson;//
    private String fLongitude;//
    private String fLatitude;//
    private String fClassification;//
    private String fStation;//投诉区域
    private String fByReportedName;//投诉企业名
    private String fByReportedGuid;//投诉企业id
    private String fContent;//内容
    private String fRegUnit;//
    private String fRegTime;//投诉时间
    private String fResource;//
    private String fReportedTel;//
    private String fStatus;//状态

    public boolean isfYqzt() {
        return fYqzt;
    }

    public void setfYqzt(boolean fYqzt) {
        this.fYqzt = fYqzt;
    }

    private boolean fYqzt;


    public String getfStatus() {
        return fStatus;
    }

    public void setfStatus(String fStatus) {
        this.fStatus = fStatus;
    }

    public String getfReportedTel() {
        return fReportedTel;
    }

    public void setfReportedTel(String fReportedTel) {
        this.fReportedTel = fReportedTel;
    }

    public String getfRegPerson() {
        return fRegPerson;
    }

    public void setfRegPerson(String fRegPerson) {
        this.fRegPerson = fRegPerson;
    }

    public String getfReportedPerson() {
        return fReportedPerson;
    }

    public void setfReportedPerson(String fReportedPerson) {
        this.fReportedPerson = fReportedPerson;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getfByReportedAddress() {
        return fByReportedAddress;
    }

    public void setfByReportedAddress(String fByReportedAddress) {
        this.fByReportedAddress = fByReportedAddress;
    }

    public String getfRegId() {
        return fRegId;
    }

    public void setfRegId(String fRegId) {
        this.fRegId = fRegId;
    }

    public String getfByReportedTel() {
        return fByReportedTel;
    }

    public void setfByReportedTel(String fByReportedTel) {
        this.fByReportedTel = fByReportedTel;
    }

    public String getfLongitude() {
        return fLongitude;
    }

    public void setfLongitude(String fLongitude) {
        this.fLongitude = fLongitude;
    }

    public String getfLatitude() {
        return fLatitude;
    }

    public void setfLatitude(String fLatitude) {
        this.fLatitude = fLatitude;
    }

    public String getfClassification() {
        return fClassification;
    }

    public void setfClassification(String fClassification) {
        this.fClassification = fClassification;
    }

    public String getfStation() {
        return fStation;
    }

    public void setfStation(String fStation) {
        this.fStation = fStation;
    }

    public String getfByReportedName() {
        return fByReportedName;
    }

    public void setfByReportedName(String fByReportedName) {
        this.fByReportedName = fByReportedName;
    }

    public String getfByReportedGuid() {
        return fByReportedGuid;
    }

    public void setfByReportedGuid(String fByReportedGuid) {
        this.fByReportedGuid = fByReportedGuid;
    }

    public String getfContent() {
        return fContent;
    }

    public void setfContent(String fContent) {
        this.fContent = fContent;
    }

    public String getfRegUnit() {
        return fRegUnit;
    }

    public void setfRegUnit(String fRegUnit) {
        this.fRegUnit = fRegUnit;
    }

    public String getfRegTime() {
        return fRegTime;
    }

    public void setfRegTime(String fRegTime) {
        this.fRegTime = fRegTime;
    }

    public String getfResource() {
        return fResource;
    }

    public void setfResource(String fResource) {
        this.fResource = fResource;
    }
}
