package com.zx.tjmarketmobile.entity;

public class SelectPopDataList {

    public String fTaskStatus;
    public String fTaskId;
    public String fTaskName;

    public SelectPopDataList(String name, String id) {
        fTaskName = name;
        fTaskId = id;
    }

    public SelectPopDataList() {
        super();
    }

    public String getfTaskStatus() {
        return fTaskStatus;
    }

    public void setfTaskStatus(String fTaskStatus) {
        this.fTaskStatus = fTaskStatus;
    }

    public String getfTaskId() {
        return fTaskId;
    }

    public void setfTaskId(String fTaskId) {
        this.fTaskId = fTaskId;
    }

    public String getfTaskName() {
        return fTaskName;
    }

    public void setfTaskName(String fTaskName) {
        this.fTaskName = fTaskName;
    }
}
