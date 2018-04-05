package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

/**
 * Created by zhouzq on 2017/2/24.
 */

public class EntityFile implements Serializable {
    private String fRealName;

    public String getfRealName() {
        return fRealName;
    }

    public void setfRealName(String fRealName) {
        this.fRealName = fRealName;
    }

    public String getfSaveName() {
        return fSaveName;
    }

    public void setfSaveName(String fSaveName) {
        this.fSaveName = fSaveName;
    }

    private String fSaveName;

    public String getfGuid() {
        return fGuid;
    }

    public void setfGuid(String fGuid) {
        this.fGuid = fGuid;
    }

    private String fGuid;
}
