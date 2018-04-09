package com.zx.tjmarketmobile.entity.infomanager;

import java.io.Serializable;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class InfoManagerBiaozhunDetail implements Serializable {

    private String id;
    private String uniquelyIdentifies;
    private String agencyCode;
    private String contactPhone;
    private String administrativeDivisions;
    private String enterpriseName;
    private String registeredAddress;
    private String starandNumber;
    private String standardName;
    private String standardType;
    private String standardStatus;
    private long releaseDate;
    private String enterpriseUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniquelyIdentifies() {
        return uniquelyIdentifies;
    }

    public void setUniquelyIdentifies(String uniquelyIdentifies) {
        this.uniquelyIdentifies = uniquelyIdentifies;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAdministrativeDivisions() {
        return administrativeDivisions;
    }

    public void setAdministrativeDivisions(String administrativeDivisions) {
        this.administrativeDivisions = administrativeDivisions;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getStarandNumber() {
        return starandNumber;
    }

    public void setStarandNumber(String standardName) {
        this.starandNumber = starandNumber;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public String getStandardType() {
        return standardType;
    }

    public void setStandardType(String standardType) {
        this.standardType = standardType;
    }

    public String getStandardStatus() {
        return standardStatus;
    }

    public void setStandardStatus(String standardStatus) {
        this.standardStatus = standardStatus;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getEnterpriseUrl() {
        return enterpriseUrl;
    }


    public void setEnterpriseUrl(String enterpriseUrl) {
        this.enterpriseUrl = enterpriseUrl;
    }
}
