package com.zx.tjmarketmobile.entity;

/**
 * Created by Xiangb on 2018/4/9.
 * 功能：
 */

public class ImageEntity {

    /**
     * id : 39783631B06D42AAA8E0BFC8070A3D8D
     * path : /upload/TJSupervise/BBA636714D4E42349FDD12AD83942C33.jpg
     * name : Screenshot_20180402-100551.jpg
     * taskId : 8553ac17333011e89469000c2962b4b5
     * updateDate : 1523246032000
     * updateUserName : 外网测试
     * updateUserId : 51FCDDE6504C4CCF848BD2B7FD7F3D9C
     */

    private String id;
    private String path;
    private String name;
    private String taskId;
    private long updateDate;
    private String updateUserName;
    private String updateUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }
}
