package com.zx.tjmarketmobile.entity;

/**
 * Created by zhouzq on 2017/2/7.
 */

public class DeviceSecurityRiskEntity {

    private String type;
    private String securityFile;
    private String securityContract;
    private String nextOverhaul;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecurityFile() {
        return securityFile;
    }

    public void setSecurityFile(String securityFile) {
        this.securityFile = securityFile;
    }

    public String getSecurityContract() {
        return securityContract;
    }

    public void setSecurityContract(String securityContract) {
        this.securityContract = securityContract;
    }

    public String getNextOverhaul() {
        return nextOverhaul;
    }

    public void setNextOverhaul(String nextOverhaul) {
        this.nextOverhaul = nextOverhaul;
    }


}
