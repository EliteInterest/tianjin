package com.zx.gamarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class EntityDetail implements Serializable {

    public List<BizInfoEntity> BizInfo;// 业务信息
    public List<CreditInfo> CreditInfo;// 信用信息
    public KeyEntityInfo EntityInfo;// 基本信息

    public List<BizInfoEntity> getBizInfo() {
        return BizInfo;
    }

    public void setBizInfo(List<BizInfoEntity> bizInfo) {
        BizInfo = bizInfo;
    }

    public List<com.zx.gamarketmobile.entity.CreditInfo> getCreditInfo() {
        return CreditInfo;
    }

    public void setCreditInfo(List<com.zx.gamarketmobile.entity.CreditInfo> creditInfo) {
        CreditInfo = creditInfo;
    }

    public KeyEntityInfo getEntityInfo() {
        return EntityInfo;
    }

    public void setEntityInfo(KeyEntityInfo entityInfo) {
        EntityInfo = entityInfo;
    }

    public List<EntityFile> getImg() {
        return Img;
    }

    public void setImg(List<EntityFile> img) {
        Img = img;
    }

    public List<EntityFile> Img;


}
