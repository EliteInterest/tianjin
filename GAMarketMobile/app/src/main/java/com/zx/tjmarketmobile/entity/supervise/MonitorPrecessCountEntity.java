package com.zx.tjmarketmobile.entity.supervise;

import java.io.Serializable;

/**
 * Created by zhouzq on 2017/4/7.
 */

public class MonitorPrecessCountEntity implements Serializable{


    /**
     * over : 17
     * total : 17
     * willOver : 0
     */

    private int over;
    private int total;
    private int willOver;

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getWillOver() {
        return willOver;
    }

    public void setWillOver(int willOver) {
        this.willOver = willOver;
    }
}
