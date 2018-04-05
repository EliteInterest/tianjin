package com.zx.tjmarketmobile.entity;

/**
 * Created by Xiangb on 2017/3/20.
 * 功能：
 */
public class FileInfoEntity {


    /**
     * id : 47DD52B134F84807BFED58636BB0C3AB
     * caseId : 38E5C9B5D6CC4B70BC2BFDAEA47C7BE4
     * departmentId : 09
     * name : 01案件来源登记表.doc
     * url : /upload/tjcase/313038AA9B9B43518E6F4E0E5C8F1676.doc
     * type : 基本资料
     * updateUser : admin
     * updateUserId : f9aea146c8ea11e79fcc000c2934879e
     * updateDate : 1516960056000
     */

    private String id;
    private String caseId;
    private String departmentId;
    private String name;
    private String url;
    private String type;
    private String updateUser;
    private String updateUserId;
    private long updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }
}
