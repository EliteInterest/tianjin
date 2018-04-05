package com.zx.tjmarketmobile.entity;

/**
 * Created by Xiangb on 2017/3/14.
 * 功能：案件信息流程轨迹
 */
public class CaseFlowEntity {


    /**
     * id : FC87154B963340648A08F830228EDE9C
     * caseId : EE857209BD334EF1B9D37313DDB00DCD
     * name : 案件查处
     * process : null
     * handleUser : 管理员
     * handleUserId : f9aea146c8ea11e79fcc000c2934879e
     * handleDate : 1520496310000
     * acceptUserId : f9aea146c8ea11e79fcc000c2934879e
     * acceptUser : 管理员
     * isAgree : null
     * remark : 中止调查
     */

    private String id;
    private String caseId;
    private String name;
    private String process;
    private String handleUser;
    private String handleUserId;
    private long handleDate;
    private String acceptUserId;
    private String acceptUser;
    private Object isAgree;
    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(String handleUser) {
        this.handleUser = handleUser;
    }

    public String getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(String handleUserId) {
        this.handleUserId = handleUserId;
    }

    public long getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(long handleDate) {
        this.handleDate = handleDate;
    }

    public String getAcceptUserId() {
        return acceptUserId;
    }

    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    public String getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(String acceptUser) {
        this.acceptUser = acceptUser;
    }

    public Object getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(Object isAgree) {
        this.isAgree = isAgree;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
