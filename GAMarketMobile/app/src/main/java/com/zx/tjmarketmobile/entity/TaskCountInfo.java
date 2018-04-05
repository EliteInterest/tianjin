package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class TaskCountInfo implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 6321080383158877412L;
    public String status;// 任务状态
    public int allCount = 0;// 任务总数
    public int soonCount = 0;// 即将到期任务数
    public int expireCount = 0;// 逾期任务数

    public int emergencyNum = 0;//预警信息数量
    public String emergencyName = "";//预警信息类别

    public int getEmergencyNum() {
        return emergencyNum;
    }

    public void setEmergencyNum(int emergencyNum) {
        this.emergencyNum = emergencyNum;
    }

    public String getEmergencyName() {
        return emergencyName;
    }

    public void setEmergencyName(String emergencyName) {
        this.emergencyName = emergencyName;
    }

}
