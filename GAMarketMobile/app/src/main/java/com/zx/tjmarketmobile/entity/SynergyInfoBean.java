package com.zx.tjmarketmobile.entity;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/26.
 * 功能：
 */

public class SynergyInfoBean {

    /**
     * fEntityGuid : DF2953DB8CBD454791E755EDAC4DB9E3
     * num : 1
     * fEntityName : 湖潮乡湖潮中心小学
     */

    private String fEntityGuid;
    private String num;
    private String fEntityName;
    private String fInsertDate;
    private String fStatusDes;
    private boolean open = false;
    private int itemType = 0;
    private String fGuid;

    public String getfGuid() {
        return fGuid;
    }

    public void setfGuid(String fGuid) {
        this.fGuid = fGuid;
    }

    private List<SynergyInfoBean> childList;

    public List<SynergyInfoBean> getChildList() {
        return childList;
    }

    public void setChildList(List<SynergyInfoBean> childList) {
        this.childList = childList;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getfInsertDate() {
        return fInsertDate;
    }

    public void setfInsertDate(String fInsertDate) {
        this.fInsertDate = fInsertDate;
    }

    public String getfStatusDes() {
        return fStatusDes;
    }

    public void setfStatusDes(String fStatusDes) {
        this.fStatusDes = fStatusDes;
    }

    public String getFEntityGuid() {
        return fEntityGuid;
    }

    public void setFEntityGuid(String fEntityGuid) {
        this.fEntityGuid = fEntityGuid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFEntityName() {
        return fEntityName;
    }

    public void setFEntityName(String fEntityName) {
        this.fEntityName = fEntityName;
    }

}
