package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

/**
 * Created by Xiangb on 2016/12/12.
 * 功能：
 */
public class BizInfoEntity implements Serializable {

    private String number;
    private String type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
