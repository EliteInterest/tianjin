package com.zx.tjmarketmobile.entity;

/**
 * Created by Xiangb on 2017/3/22.
 * 功能：投诉举报处理流程
 */
public class CompFlowEntity {

    private String fGuid;//编号
    private String fRegId;//登记id
    private String fStatus;//状态
    private String fHandleDate;//处理日期
    private String fHandleAdvice;//处理建议
    private String fHandleUser;//处理人
    private String fSlr;//受理人
    private String fRemark;//备注

    public String getfGuid() {
        return fGuid;
    }

    public void setfGuid(String fGuid) {
        this.fGuid = fGuid;
    }

    public String getfRegId() {
        return fRegId;
    }

    public void setfRegId(String fRegId) {
        this.fRegId = fRegId;
    }

    public String getfStatus() {
        return fStatus;
    }

    public void setfStatus(String fStatus) {
        this.fStatus = fStatus;
    }

    public String getfHandleDate() {
        return fHandleDate;
    }

    public void setfHandleDate(String fHandleDate) {
        this.fHandleDate = fHandleDate;
    }

    public String getfHandleAdvice() {
        return fHandleAdvice;
    }

    public void setfHandleAdvice(String fHandleAdvice) {
        this.fHandleAdvice = fHandleAdvice;
    }

    public String getfHandleUser() {
        return fHandleUser;
    }

    public void setfHandleUser(String fHandleUser) {
        this.fHandleUser = fHandleUser;
    }

    public String getfSlr() {
        return fSlr;
    }

    public void setfSlr(String fSlr) {
        this.fSlr = fSlr;
    }

    public String getfRemark() {
        return fRemark;
    }

    public void setfRemark(String fRemark) {
        this.fRemark = fRemark;
    }
}
