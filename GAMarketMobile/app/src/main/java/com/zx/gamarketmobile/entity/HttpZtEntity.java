package com.zx.gamarketmobile.entity;

import java.io.Serializable;

public class HttpZtEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -4848195188747233411L;
    public String guid;
    public String entityName;//主体名称
    public String creditLevel;//信用等级
    public String contactInfo;//联系方式
    public String fContactPhone;
    public String legalPerson;//法定代表人
    public String bizlicNum;//营业执照注册号
    public String orgCode;//组织机构代码
    public String licenses;//许可证
    public String address;//地址
    public String fTags;
    public String fType;
    public String getfEntityType() {
        return fEntityType;
    }

    public void setfEntityType(String fEntityType) {
        this.fEntityType = fEntityType;
    }

    public String fEntityType;
    public String longitude;
    public String latitude;
    public int wkid;//投影坐标系ID
    public boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "HttpZtEntity [guid=" + guid + ", entityName=" + entityName + ", creditLevel=" + creditLevel
                + ", contactInfo=" + contactInfo + ", legalPerson=" + legalPerson + ", bizlicNum=" + bizlicNum
                + ", orgCode=" + orgCode + ", licenses=" + licenses + ", address=" + address + ", longitude="
                + longitude + ", latitude=" + latitude + ", wkid=" + wkid + ", jyfw=" + jyfw + "]";
    }

    private String jyfw;//经营范围

    public String getfContactPhone() {
        return fContactPhone;
    }

    public void setfContactPhone(String fContactPhone) {
        this.fContactPhone = fContactPhone;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBizlicNum() {
        return bizlicNum;
    }

    public void setBizlicNum(String bizlicNum) {
        this.bizlicNum = bizlicNum;
    }

    public String getLicenses() {
        return licenses;
    }

    public void setLicenses(String licenses) {
        this.licenses = licenses;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getJyfw() {
        return jyfw;
    }

    public void setJyfw(String jyfw) {
        this.jyfw = jyfw;
    }

    public int getWkid() {
        return this.wkid;
    }

    public void setWkid(int wkid) {
        this.wkid = wkid;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }
}
