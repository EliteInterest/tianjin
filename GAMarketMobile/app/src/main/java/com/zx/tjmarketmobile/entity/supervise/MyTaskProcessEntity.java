package com.zx.tjmarketmobile.entity.supervise;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/5.
 */

public class MyTaskProcessEntity {


    /**
     * code : 20000
     * message : Success
     * data : [{"fUserId":"AC6FFF1323BA4946BAFCAF00F52CE70A","fDepartment":"市场监管处","fRealName":"何音"},{"fUserId":"8B7330FECD0D4112AB2974AE4FFE5804","fDepartment":"市场监管处","fRealName":"胡笑"},{"fUserId":"CD0D0D79B33F4C42A826B4C288B7D529","fDepartment":"市场监管处","fRealName":"李梅"},{"fUserId":"4220EA5A8507423DA2D33B138A462FC2","fDepartment":"市场监管处","fRealName":"邓光平"},{"fUserId":"CF1A4E1C5F5E4A24BBDF8F59AFD96BBB","fDepartment":"市场监管处","fRealName":"邓长艳"},{"fUserId":"63866A8CB8254ED18F9BED285592C366","fDepartment":"市场监管处","fRealName":"张应"}]
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
         * fUserId : AC6FFF1323BA4946BAFCAF00F52CE70A
         * fDepartment : 市场监管处
         * fRealName : 何音
         */

        private String fUserId;
        private String fDepartment;
        private String fRealName;

        public String getFUserId() {
            return fUserId;
        }

        public void setFUserId(String fUserId) {
            this.fUserId = fUserId;
        }

        public String getFDepartment() {
            return fDepartment;
        }

        public void setFDepartment(String fDepartment) {
            this.fDepartment = fDepartment;
        }

        public String getFRealName() {
            return fRealName;
        }

        public void setFRealName(String fRealName) {
            this.fRealName = fRealName;
        }
    }
}
