package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiangb on 2017/12/25.
 * 功能：
 */

public class SynergyTabEntity implements Serializable {
    private String typeName;
    private String code;
    private List<SynergyNumEntity> smallTypes;

    public SynergyTabEntity(String typeName, String code, List<SynergyNumEntity> smallTypes) {
        this.typeName = typeName;
        this.code = code;
        this.smallTypes = smallTypes;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<SynergyNumEntity> getSmallTypes() {
        if (smallTypes == null) {
            return new ArrayList<>();
        } else {
            return smallTypes;
        }
    }

    public void setSmallTypes(List<SynergyNumEntity> smallTypes) {
        this.smallTypes = smallTypes;
    }
}
