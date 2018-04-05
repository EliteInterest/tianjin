package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

/**
 * Created by Xiangb on 2017/12/27.
 * 功能：
 */

public class SynergyNumEntity implements Serializable{
    private String name;
    private String dataKey;
    private int num;

    public SynergyNumEntity(String name, String dataKey) {
        this.name = name;
        this.dataKey = dataKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
