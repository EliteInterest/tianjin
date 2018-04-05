package com.zx.tjmarketmobile.entity.supervise;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/6.
 */

public class MyTaskCheckResultEntity {

    /**
     * code : 0
     * message : success
     * data : [{"fGuid":"F5339C5EB7744E439213B8D313B1608C","fItemId":"05000FC382FF446CA560DE3A5EC73723","fItemName":"发现经营者经营不符合食品安全标准的食品或者有其他违法行为的，是否及时制止并报告食药监部门","fCheckResult":1,"fValueType":0,"fValueMin":null,"fValueMax":null}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * fGuid : F5339C5EB7744E439213B8D313B1608C
         * fItemId : 05000FC382FF446CA560DE3A5EC73723
         * fItemName : 发现经营者经营不符合食品安全标准的食品或者有其他违法行为的，是否及时制止并报告食药监部门
         * fCheckResult : 1
         * fValueType : 0
         * fValueMin : null
         * fValueMax : null
         */

        private String fGuid;
        private String fItemId;
        private String fItemName;
        private String fCheckResult;
        private int fValueType;
        private Object fValueMin;
        private Object fValueMax;

        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public String getFItemId() {
            return fItemId;
        }

        public void setFItemId(String fItemId) {
            this.fItemId = fItemId;
        }

        public String getFItemName() {
            return fItemName;
        }

        public void setFItemName(String fItemName) {
            this.fItemName = fItemName;
        }

        public String getFCheckResult() {
            return fCheckResult;
        }

        public void setFCheckResult(String fCheckResult) {
            this.fCheckResult = fCheckResult;
        }

        public int getFValueType() {
            return fValueType;
        }

        public void setFValueType(int fValueType) {
            this.fValueType = fValueType;
        }

        public Object getFValueMin() {
            return fValueMin;
        }

        public void setFValueMin(Object fValueMin) {
            this.fValueMin = fValueMin;
        }

        public Object getFValueMax() {
            return fValueMax;
        }

        public void setFValueMax(Object fValueMax) {
            this.fValueMax = fValueMax;
        }
    }
}
