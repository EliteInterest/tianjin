package com.zx.gamarketmobile.entity;

import java.io.Serializable;

/**
 * Created by Xiangb on 2017/3/10.
 * 功能：案件信息实体类
 */
public class CaseInfoEntity implements Serializable {

    private String fId;//id
    private String fAyAymc;//案源名称
    private String fAyAjly;//案件领域
    private String fSjCjsj;//创建时间
    private String fDsrMc;//当事人名称
    private String fHjBm;//环节编码
    private String fTaskName;//流程名
    private String fHjMc;//环节名称
    private String fAyXxly;//案件来源
    private String fAyWflx;//违法类型
    private String fDsrJgs;//监管地区
    private String fDsrLx;//当事人类型
    private String fAyNrzy;//内容摘要
    private String fSjCjr;//创建人
    private String fCjrDept;//创建人部门
    private String fTaskId;//任务id
    private String Longitude;
    private String Latitude;
    private String fDcqzZxrId;//执行人id
    private String fDcqzXzr;//执行人
    private String PROC_DEF_ID_;//流程定义ID
    private boolean fSfxa = false; //是否销案
    private String fSfyq;//是否延期
    private String fAyWffg;//定性依据
    private String fPunishLaw;//处罚依据
    private boolean fSfys = false;//是否移送
    private boolean fSfqzcs = false;//强制措施
    private boolean fYqzt = false;//是否到期

    public String getfAyWffg() {
        return fAyWffg;
    }

    public void setfAyWffg(String fAyWffg) {
        this.fAyWffg = fAyWffg;
    }

    public String getfPunishLaw() {
        return fPunishLaw;
    }

    public void setfPunishLaw(String fPunishLaw) {
        this.fPunishLaw = fPunishLaw;
    }

    public boolean isfYqzt() {
        return fYqzt;
    }

    public void setfYqzt(boolean fYqzt) {
        this.fYqzt = fYqzt;
    }

    public String getfDcqzZxrId() {
        return fDcqzZxrId;
    }

    public void setfDcqzZxrId(String fDcqzZxrId) {
        this.fDcqzZxrId = fDcqzZxrId;
    }

    public String getfDcqzXzr() {
        return fDcqzXzr;
    }

    public void setfDcqzXzr(String fDcqzXzr) {
        this.fDcqzXzr = fDcqzXzr;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }


    public boolean isfSfxa() {
        return fSfxa;
    }

    public void setfSfxa(boolean fSfxa) {
        this.fSfxa = fSfxa;
    }

    public boolean isfSfys() {
        return fSfys;
    }

    public void setfSfys(boolean fSfys) {
        this.fSfys = fSfys;
    }

    public boolean isfSfqzcs() {
        return fSfqzcs;
    }

    public void setfSfqzcs(boolean fSfqzcs) {
        this.fSfqzcs = fSfqzcs;
    }

    public String getPROC_DEF_ID_() {
        return PROC_DEF_ID_;
    }

    public void setPROC_DEF_ID_(String PROC_DEF_ID_) {
        this.PROC_DEF_ID_ = PROC_DEF_ID_;
    }

    public String getfTaskName() {
        return fTaskName;
    }

    public void setfTaskName(String fTaskName) {
        this.fTaskName = fTaskName;
    }

    public String getfTaskId() {
        return fTaskId;
    }

    public void setfTaskId(String fTaskId) {
        this.fTaskId = fTaskId;
    }

    public String getfAyXxly() {
        return fAyXxly;
    }

    public void setfAyXxly(String fAyXxly) {
        this.fAyXxly = fAyXxly;
    }

    public String getfAyWflx() {
        return fAyWflx;
    }

    public void setfAyWflx(String fAyWflx) {
        this.fAyWflx = fAyWflx;
    }

    public String getfDsrJgs() {
        return fDsrJgs;
    }

    public void setfDsrJgs(String fDsrJgs) {
        this.fDsrJgs = fDsrJgs;
    }

    public String getfDsrLx() {
        return fDsrLx;
    }

    public void setfDsrLx(String fDsrLx) {
        this.fDsrLx = fDsrLx;
    }

    public String getfAyNrzy() {
        return fAyNrzy;
    }

    public void setfAyNrzy(String fAyNrzy) {
        this.fAyNrzy = fAyNrzy;
    }

    public String getfSjCjr() {
        return fSjCjr;
    }

    public void setfSjCjr(String fSjCjr) {
        this.fSjCjr = fSjCjr;
    }

    public String getfCjrDept() {
        return fCjrDept;
    }

    public void setfCjrDept(String fCjrDept) {
        this.fCjrDept = fCjrDept;
    }

    public String getfSfyq() {
        return fSfyq;
    }

    public void setfSfyq(String fSfyq) {
        this.fSfyq = fSfyq;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public String getfAyAymc() {
        return fAyAymc;
    }

    public void setfAyAymc(String fAyAymc) {
        this.fAyAymc = fAyAymc;
    }

    public String getfAyAjly() {
        return fAyAjly;
    }

    public void setfAyAjly(String fAyAjly) {
        this.fAyAjly = fAyAjly;
    }

    public String getfSjCjsj() {
        return fSjCjsj;
    }

    public void setfSjCjsj(String fSjCjsj) {
        this.fSjCjsj = fSjCjsj;
    }

    public String getfDsrMc() {
        return fDsrMc;
    }

    public void setfDsrMc(String fDsrMc) {
        this.fDsrMc = fDsrMc;
    }

    public String getfHjBm() {
        return fHjBm;
    }

    public void setfHjBm(String fHjBm) {
        this.fHjBm = fHjBm;
    }

    public String getfHjMc() {
        return fHjMc;
    }

    public void setfHjMc(String fHjMc) {
        this.fHjMc = fHjMc;
    }
}
