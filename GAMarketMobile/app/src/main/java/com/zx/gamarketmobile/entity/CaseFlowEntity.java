package com.zx.gamarketmobile.entity;

/**
 * Created by Xiangb on 2017/3/14.
 * 功能：案件信息流程轨迹
 */
public class CaseFlowEntity {

    private String fSpsj;//审批时间
    private String fSpr;//处理人
    private String fSpjs;//处理人角色
    private String fLcZztmc;//流程环节
    private String fSpbz;//审批备注
    private String fSpyj;//审批意见
    private String fLcZzt;//流程子状态

    public String getfLcZzt() {
        return fLcZzt;
    }

    public void setfLcZzt(String fLcZzt) {
        this.fLcZzt = fLcZzt;
    }

    public String getfSpsj() {
        return fSpsj;
    }

    public void setfSpsj(String fSpsj) {
        this.fSpsj = fSpsj;
    }

    public String getfSpr() {
        return fSpr;
    }

    public void setfSpr(String fSpr) {
        this.fSpr = fSpr;
    }

    public String getfSpjs() {
        return fSpjs;
    }

    public void setfSpjs(String fSpjs) {
        this.fSpjs = fSpjs;
    }

    public String getfLcZztmc() {
        return fLcZztmc;
    }

    public void setfLcZztmc(String fLcZztmc) {
        this.fLcZztmc = fLcZztmc;
    }

    public String getfSpbz() {
        return fSpbz;
    }

    public void setfSpbz(String fSpbz) {
        this.fSpbz = fSpbz;
    }

    public String getfSpyj() {
        return fSpyj;
    }

    public void setfSpyj(String fSpyj) {
        this.fSpyj = fSpyj;
    }
}
