package com.zx.tjmarketmobile.entity.supervise;

import java.io.Serializable;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class MyTaskListEntity implements Serializable {

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
    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

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

    public String getTypeString() {
        //0待选主体1待选检查项2待下发3任务执行中4.完成
        if (type == 0) {
            return "综合";
        } else if (type == 1) {
            return "专项";
        } else if (type == 2) {
            return "临时";
        } else {
            return "";
        }
    }


}
