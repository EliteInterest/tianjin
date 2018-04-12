package com.zx.tjmarketmobile.entity;

import java.util.List;

/**
 * 命名方式:Http + 协议名 + Entity
 */
public class HttpLoginEntity {
    private String id;
    private String realname;
    private String gender;
    private String age;
    private String username;
    private String duty;
    private String department;
    private String departmentAlias;
    private String desc;
    private String password;//未加密
    private String telephone;
    private boolean isLogin;
    private String authority;//用户权限
    private String grid;//管辖片区
    private String departmentCode;
    private List<String> role;
    private String tokenId;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public List<String> getRole() {
        return role;
    }

    public String getRoleString() {
        String roleString = "";
        for (String roleItem : role) {
            roleString = roleString + roleItem + ",";
        }
        return roleString.length() > 0 ? roleString.substring(0, roleString.length() - 1) : roleString;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = ("null").equals(grid) ? null : grid;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = ("null").equals(authority) ? null : authority;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = ("null").equals(password) ? null : password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = ("null").equals(id) ? null : id;
    }

    public String getRealName() {
        return realname;
    }

    public void setRealName(String realname) {
        this.realname = ("null").equals(realname) ? null : realname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = ("null").equals(gender) ? null : gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = ("null").equals(age) ? null : age;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = ("null").equals(username) ? null : username;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = ("null").equals(duty) ? null : duty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = ("null").equals(department) ? null : department;
    }

    public String getDepartmentAlias() {
        return departmentAlias;
    }

    public void setDepartmentAlias(String departmentAlias) {
        this.departmentAlias = departmentAlias;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = ("null").equals(desc) ? null : desc;
    }

    public String getTelphone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = ("null").equals(telephone) ? null : telephone;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    @Override
    public String toString() {
        return "HttpLoginEntity [id=" + id + ", realname=" + realname + ", gender=" + gender + ", age=" + age
                + ", username=" + username + ", duty=" + duty + ", department=" + department + ", desc=" + desc
                + ", password=" + password + ", telephone=" + telephone + ", isLogin=" + isLogin + ", authority="
                + authority + "]";
    }

}
