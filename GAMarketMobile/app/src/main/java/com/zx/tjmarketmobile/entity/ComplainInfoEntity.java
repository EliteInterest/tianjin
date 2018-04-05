package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

/**
 * Created by Xiangb on 2017/3/21.
 * 功能：投诉举报信息
 */
public class ComplainInfoEntity implements Serializable {

    /**
     * fGuid : ed902abc92c948e1810909a0d1a32711
     * fName : 张元中
     * fType : 投诉
     * fStatus : 10
     * sInfo : {"x":106.567433,"y":29.643714,"spatialReference":4490}
     * fRegTime : 1439949411000
     * fEntityName : 中豪雪弗兰4s店
     */

    private String fGuid;
    private String fName;
    private String fType;
    private int fStatus;
    private SInfoBean sInfo;
    private long fRegTime;
    private String fEntityName;

    public String getFGuid() {
        return fGuid;
    }

    public void setFGuid(String fGuid) {
        this.fGuid = fGuid;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getFType() {
        return fType;
    }

    public void setFType(String fType) {
        this.fType = fType;
    }

    public int getFStatus() {
        return fStatus;
    }

    public void setFStatus(int fStatus) {
        this.fStatus = fStatus;
    }

    public SInfoBean getSInfo() {
        return sInfo;
    }

    public void setSInfo(SInfoBean sInfo) {
        this.sInfo = sInfo;
    }

    public long getFRegTime() {
        return fRegTime;
    }

    public void setFRegTime(long fRegTime) {
        this.fRegTime = fRegTime;
    }

    public String getFEntityName() {
        return fEntityName;
    }

    public void setFEntityName(String fEntityName) {
        this.fEntityName = fEntityName;
    }

    public static class SInfoBean {
        /**
         * x : 106.567433
         * y : 29.643714
         * spatialReference : 4490
         */

        private double x;
        private double y;
        private String spatialReference;

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public String getSpatialReference() {
            return spatialReference;
        }

        public void setSpatialReference(String spatialReference) {
            this.spatialReference = spatialReference;
        }
    }
}
