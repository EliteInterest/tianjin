package com.zx.gamarketmobile.entity.infomanager;

import java.io.Serializable;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class InfoManagerLicenseDetail implements Serializable {

    private String id;
    private String licNum;
    private String typeName;
    private String enterpriseId;
    private String enterpriseName;
    private String productedAddress;
    private String legalPerson;
    private String enterprisePrincipal;
    private String enterpriseType;
    private String categoryCode;
    private String productedScope;
    private long issueDate;
    private long expireStartDate;
    private long expireEndDate;
    private String issueOrg;
    private String contactPerson;
    private String contactInfo;
    private String fax;
    private String updateDescrible;
    private String gmpNum;
    private String certificationScope;
    private String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLicNum() {
        return licNum;
    }

    public void setLicNum(String licNum) {
        this.licNum = licNum;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getProductedAddress() {
        return productedAddress;
    }

    public void setProductedAddress(String productedAddress) {
        this.productedAddress = id;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getEnterprisePrincipal() {
        return enterprisePrincipal;
    }

    public void setEnterprisePrincipal(String enterprisePrincipal) {
        this.enterprisePrincipal = enterprisePrincipal;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }


    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getProductedScope() {
        return productedScope;
    }

    public void setProductedScope(String productedScope) {
        this.productedScope = productedScope;
    }

    public String getIssueOrg() {
        return issueOrg;
    }

    public void setIssueOrg(String issueOrg) {
        this.issueOrg = issueOrg;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getUpdateDescrible() {
        return updateDescrible;
    }

    public void setUpdateDescrible(String updateDescrible) {
        this.updateDescrible = updateDescrible;
    }

    public String getGmpNum() {
        return gmpNum;
    }

    public void setGmpNum(String gmpNum) {
        this.gmpNum = gmpNum;
    }

    public String getCertificationScope() {
        return certificationScope;
    }

    public void setCertificationScope(String certificationScope) {
        this.certificationScope = certificationScope;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getIssueDate()
    {
        return issueDate;
    }

    public void setIssueDate(long issueDate)
    {
        this.issueDate = issueDate;
    }

    public long getExpireStartDate()
    {
        return expireStartDate;
    }

    public void setExpireStartDate(long expireStartDate)
    {
        this.expireStartDate = expireStartDate;
    }

    public long getExpireEndDate()
    {
        return expireEndDate;
    }

    public void setExpireEndDate(long expireEndDate)
    {
        this.expireEndDate = expireEndDate;
    }

}
