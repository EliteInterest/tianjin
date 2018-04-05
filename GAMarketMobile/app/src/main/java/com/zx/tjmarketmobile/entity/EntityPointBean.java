package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

/**
 * Created by Xiangb on 2017/12/19.
 * 功能：
 */

public class EntityPointBean implements Serializable{

    /**
     * fEntityGuid : 0CAD4EF739BF47ED83A16E300377D9E1
     * fType : 默认坐标
     * fLatitude : 26.44
     * fLongitude : 106.5
     */

    private String fEntityGuid;
    private String fType;
    private String fLatitude;
    private String fLongitude;

    public String getFEntityGuid() {
        return fEntityGuid;
    }

    public void setFEntityGuid(String fEntityGuid) {
        this.fEntityGuid = fEntityGuid;
    }

    public String getFType() {
        return fType;
    }

    public void setFType(String fType) {
        this.fType = fType;
    }

    public String getFLatitude() {
        return fLatitude;
    }

    public void setFLatitude(String fLatitude) {
        this.fLatitude = fLatitude;
    }

    public String getFLongitude() {
        return fLongitude;
    }

    public void setFLongitude(String fLongitude) {
        this.fLongitude = fLongitude;
    }
}
