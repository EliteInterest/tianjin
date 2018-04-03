package com.zx.gamarketmobile.entity.supervise;

/**
 * Created by admin on 2017/3/31.
 */

public class MyTaskBaseInfo {


    private String id;
    private String departmentId;
    private String taskName;
    private String taskNum;
    private String source;
    private int type;
    private int status;
    private long startDate;
    private long deadline;
    private String reamrk;
    private String userName;
    private String userId;
    private int overdue;
    private long remindDate;
    private int remindDay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(String taskNum) {
        this.taskNum = taskNum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }


    public String getReamrk() {
        return reamrk;
    }

    public void setReamrk(String reamrk) {
        this.reamrk = reamrk;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userName) {
        this.userId = userId;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public long getOverdue() {
        return overdue;
    }

    public void setRemindDate(long remindDate) {
        this.remindDate = remindDate;
    }

    public long getRemindDate() {
        return remindDate;
    }

    public void setRemindDay(int remindDay) {
        this.remindDay = remindDay;
    }

    public int getRemindDay() {
        return remindDay;
    }

    @Override
    public String toString() {
        return id + departmentId + taskName + taskNum + source + "";
    }

    /**
     * fCreateTime : 2017-03-02
     * fCreateDepartment : 质监处
     * fTaskStatus : 待指派
     * fCreateName : zhangsan
     * fGuid : 700E9F820D8D4D3587A5DC69284BF35B
     * fSource : 年报抽查
     * fCreateUserId : 49BDD4216B044BDAADB5259CD990C921
     * fRemark : 123
     * fTaskId : 2017-001
     * fDispatchTime : 1490336780000
     * fTipsTime : 2017-03-12
     * fDispatchUser : 49BDD4216B044BDAADB5259CD990C921
     * fDeadline : 2017-03-12
     * fTaskName : 001
     * fHandleDepartment : B08FC6CD15194E47BDD9DE8B2FB194A7
     * fIsIssue : 0
     */

//    private String fCreateTime;
//    private String fCreateDepartment;
//    private String fTaskStatus;
//    private String fCreateName;
//    private String fGuid;
//    private String fSource;
//    private String fCreateUserId;
//
//    public String getfRemark() {
//        return fRemark;
//    }
//
//    public void setfRemark(String fRemark) {
//        this.fRemark = fRemark;
//    }
//
//    private String fRemark;
//    private String fTaskId;
//    private long fDispatchTime;
//    private String fTipsTime;
//    private String fDispatchUser;
//    private String fDeadline;
//    private String fTaskName;
//    private String fHandleDepartment;
//    private String fIsIssue;
//
//    public String getFCreateTime() {
//        return fCreateTime;
//    }
//
//    public void setFCreateTime(String fCreateTime) {
//        this.fCreateTime = fCreateTime;
//    }
//
//    public String getFCreateDepartment() {
//        return fCreateDepartment;
//    }
//
//    public void setFCreateDepartment(String fCreateDepartment) {
//        this.fCreateDepartment = fCreateDepartment;
//    }
//
//    public String getFTaskStatus() {
//        return fTaskStatus;
//    }
//
//    public void setFTaskStatus(String fTaskStatus) {
//        this.fTaskStatus = fTaskStatus;
//    }
//
//    public String getFCreateName() {
//        return fCreateName;
//    }
//
//    public void setFCreateName(String fCreateName) {
//        this.fCreateName = fCreateName;
//    }
//
//    public String getFGuid() {
//        return fGuid;
//    }
//
//    public void setFGuid(String fGuid) {
//        this.fGuid = fGuid;
//    }
//
//    public String getFSource() {
//        return fSource;
//    }
//
//    public void setFSource(String fSource) {
//        this.fSource = fSource;
//    }
//
//    public String getFCreateUserId() {
//        return fCreateUserId;
//    }
//
//    public void setFCreateUserId(String fCreateUserId) {
//        this.fCreateUserId = fCreateUserId;
//    }
//
//
//    public String getFTaskId() {
//        return fTaskId;
//    }
//
//    public void setFTaskId(String fTaskId) {
//        this.fTaskId = fTaskId;
//    }
//
//    public long getFDispatchTime() {
//        return fDispatchTime;
//    }
//
//    public void setFDispatchTime(long fDispatchTime) {
//        this.fDispatchTime = fDispatchTime;
//    }
//
//    public String getFTipsTime() {
//        return fTipsTime;
//    }
//
//    public void setFTipsTime(String fTipsTime) {
//        this.fTipsTime = fTipsTime;
//    }
//
//    public String getFDispatchUser() {
//        return fDispatchUser;
//    }
//
//    public void setFDispatchUser(String fDispatchUser) {
//        this.fDispatchUser = fDispatchUser;
//    }
//
//    public String getFDeadline() {
//        return fDeadline;
//    }
//
//    public void setFDeadline(String fDeadline) {
//        this.fDeadline = fDeadline;
//    }
//
//    public String getFTaskName() {
//        return fTaskName;
//    }
//
//    public void setFTaskName(String fTaskName) {
//        this.fTaskName = fTaskName;
//    }
//
//    public String getFHandleDepartment() {
//        return fHandleDepartment;
//    }
//
//    public void setFHandleDepartment(String fHandleDepartment) {
//        this.fHandleDepartment = fHandleDepartment;
//    }
//
//    public String getFIsIssue() {
//        return fIsIssue;
//    }
//
//    public void setFIsIssue(String fIsIssue) {
//        this.fIsIssue = fIsIssue;
//    }
}
