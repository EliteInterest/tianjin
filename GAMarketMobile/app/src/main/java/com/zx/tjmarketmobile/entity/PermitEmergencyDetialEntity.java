package com.zx.tjmarketmobile.entity;

/**
 * Created by zhouzq on 2016/12/5.
 */
public class PermitEmergencyDetialEntity {


//    "fGuid": "6B78A6073AEE4041B702033C6053A9E8",
//            "fLicNum": "渝餐证字【2015】500123-000622",
//            "fEntityName": "重庆华宇酒店管理有限公司西餐厅",
//            "fLegalPerson": "胡端    （法定代表人）",
//            "fAddress": "重庆市渝北区泰山大道东段116号（负二层部分、负一层部分、一层部分、二层部分、十六层部分）",
//            "fType": "大型餐馆",
//            "fBizScope": "西餐类制售：含凉菜，含冷热饮品制售，含裱花蛋糕，不含生食海产品。",
//            "fRegOrg": null,
//            "fRegDate": "2015-12-10 00:00:00",
//            "fExpireDate": "2016-12-07 00:00:00",
//            "fEntityGuid": "ECEC054DB9D6406DAC4DD05E14E43CDB",
//            "fTelepone": "86268888 15123388867 （胡端） 13883851578 （李晓柏）",
//            "fFsName": "姚晓茂雷强",
//            "fFsCode": "CQSP1513888  CQSP1513890",
//            "fLicFile": ""

//    "fGuid": "43E8B0B824C9433BA86E026E088A7BB8",
//            "fEntityGuid": "ECEC054DB9D6406DAC4DD05E14E43CDB",
//            "fEntityName": "桐君阁大药房北部新区中冶药店",
//            "fLicNum": "GSP编号：CQ41-Cb-2014001",
//            "fAddress": "重庆市北部新区金开大道1122号附45号",
//            "fLegalPerson": "/",
//            "fManager": "/",
//            "fQcManager": "/",
//            "fDepotAddr": "/",
//            "fContactInfo": "63014802 18623558604 （吴玲玲） 15213137731 （李艳萍）",
//            "fBizType": "零售连锁",
//            "fBizScope": "化学药制剂、抗生素制剂、生化药品、中成药、中药材、中药饮片、生物制品（限口服、外用制剂）。",
//            "fExpireDate": "2017-03-27 00:00:00"

//    "fGuid": "3173C5AD9B644C1A8DE462783258CB13",
//            "fLicNumber": null,
//            "fYearLimit": null,
//            "fFileBox": "810",
//            "fLicType": null,
//            "fEntityName": "重庆川仪自动化股份有限公司波纹管分公司",
//            "fAddr": "重庆市北碚区水土镇劳动新村49号",
//            "fRegNum": "78422006-X",
//            "fOrgName": "重庆市特种设备质量安全检测中心",
//            "fApprovalProject": "压力管道元件制造",
//            "fApprovalScope": "B级",
//            "fApprovalDate": "2016-04-05 00:00:00",
//            "fExpireDate": "2017-05-03 00:00:00",
//            "fLicNum": "TS2750027-2013",
//            "fMark": "换证",
//            "fChange": null,
//            "fEntityGuid": "ECEC054DB9D6406DAC4DD05E14E43CDB"



    private String fGuid;
    private String fLicNum;
    private String fEntityName;
    private String fLegalPerson;
    private String fAddress;
    private String fType;
    private String fBizScope;
    private String fRegOrg;
    private String fRegDate;
    private String fExpireDate;
    private String fEntityGuid;
    private String fTelepone;
    private String fFsName;
    private String fFsCode;
    private String fLicFile;


    private String fManager;
    private String fQcManager;
    private String fDepotAddr;
    private String fContactInfo;
    private String fBizType;

    private String fLicType;
    private String fAddr;
    private String fOrgName;
    private String fApprovalProject;
    private String fApprovalScope;
    private String fApprovalDate;
    private String fRegNum;
    private String fMark;

    public String getfMark() {
        return fMark;
    }

    public void setfMark(String fMark) {
        this.fMark = fMark;
    }

    public String getfLicType() {
        return fLicType;
    }

    public void setfLicType(String fLicType) {
        this.fLicType = fLicType;
    }

    public String getfAddr() {
        return fAddr;
    }

    public void setfAddr(String fAddr) {
        this.fAddr = fAddr;
    }

    public String getfOrgName() {
        return fOrgName;
    }

    public void setfOrgName(String fOrgName) {
        this.fOrgName = fOrgName;
    }

    public String getfApprovalProject() {
        return fApprovalProject;
    }

    public void setfApprovalProject(String fApprovalProject) {
        this.fApprovalProject = fApprovalProject;
    }

    public String getfApprovalScope() {
        return fApprovalScope;
    }

    public void setfApprovalScope(String fApprovalScope) {
        this.fApprovalScope = fApprovalScope;
    }

    public String getfApprovalDate() {
        return fApprovalDate;
    }

    public void setfApprovalDate(String fApprovalDate) {
        this.fApprovalDate = fApprovalDate;
    }

    public String getfRegNum() {
        return fRegNum;
    }

    public void setfRegNum(String fRegNum) {
        this.fRegNum = fRegNum;
    }





    public String getfBizType() {
        return fBizType;
    }

    public void setfBizType(String fBizType) {
        this.fBizType = fBizType;
    }

    public String getfManager() {
        return fManager;
    }

    public void setfManager(String fManager) {
        this.fManager = fManager;
    }

    public String getfQcManager() {
        return fQcManager;
    }

    public void setfQcManager(String fQcManager) {
        this.fQcManager = fQcManager;
    }

    public String getfDepotAddr() {
        return fDepotAddr;
    }

    public void setfDepotAddr(String fDepotAddr) {
        this.fDepotAddr = fDepotAddr;
    }

    public String getfContactInfo() {
        return fContactInfo;
    }

    public void setfContactInfo(String fContactInfo) {
        this.fContactInfo = fContactInfo;
    }



    public String getfLicFile() {
        return fLicFile;
    }

    public void setfLicFile(String fLicFile) {
        this.fLicFile = fLicFile;
    }

    public String getfGuid() {
        return fGuid;
    }

    public void setfGuid(String fGuid) {
        this.fGuid = fGuid;
    }

    public String getfLicNum() {
        return fLicNum;
    }

    public void setfLicNum(String fLicNum) {
        this.fLicNum = fLicNum;
    }

    public String getfEntityName() {
        return fEntityName;
    }

    public void setfEntityName(String fEntityName) {
        this.fEntityName = fEntityName;
    }

    public String getfLegalPerson() {
        return fLegalPerson;
    }

    public void setfLegalPerson(String fLegalPerson) {
        this.fLegalPerson = fLegalPerson;
    }

    public String getfAddress() {
        return fAddress;
    }

    public void setfAddress(String fAddress) {
        this.fAddress = fAddress;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getfBizScope() {
        return fBizScope;
    }

    public void setfBizScope(String fBizScope) {
        this.fBizScope = fBizScope;
    }

    public String getfRegOrg() {
        return fRegOrg;
    }

    public void setfRegOrg(String fRegOrg) {
        this.fRegOrg = fRegOrg;
    }

    public String getfRegDate() {
        return fRegDate;
    }

    public void setfRegDate(String fRegDate) {
        this.fRegDate = fRegDate;
    }

    public String getfExpireDate() {
        return fExpireDate;
    }

    public void setfExpireDate(String fExpireDate) {
        this.fExpireDate = fExpireDate;
    }

    public String getfEntityGuid() {
        return fEntityGuid;
    }

    public void setfEntityGuid(String fEntityGuid) {
        this.fEntityGuid = fEntityGuid;
    }

    public String getfTelepone() {
        return fTelepone;
    }

    public void setfTelepone(String fTelepone) {
        this.fTelepone = fTelepone;
    }

    public String getfFsName() {
        return fFsName;
    }

    public void setfFsName(String fFsName) {
        this.fFsName = fFsName;
    }

    public String getfFsCode() {
        return fFsCode;
    }

    public void setfFsCode(String fFsCode) {
        this.fFsCode = fFsCode;
    }


}
