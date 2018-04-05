package com.zx.tjmarketmobile.entity.supervise;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/6.
 */

public class MyTaskPageEntity {

    /**
     * code : 20000
     * message : Success
     * data : [{"fGuid":"3126D179D9574690901D84E1E6B74B18","fSource":"年报抽查","fCreateDepartment":"市场监管处","fCreateDate":"2017-03-31 05:58:56","fTaskStatus":"待指派","fDeaddate":"2017-03-31 12:00:00","fCreateName":"刘天","fTaskId":"2017-004","fTaskName":"测试打包4"}]
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
         * fGuid : 3126D179D9574690901D84E1E6B74B18
         * fSource : 年报抽查
         * fCreateDepartment : 市场监管处
         * fCreateDate : 2017-03-31 05:58:56
         * fTaskStatus : 待指派
         * fDeaddate : 2017-03-31 12:00:00
         * fCreateName : 刘天
         * fTaskId : 2017-004
         * fTaskName : 测试打包4
         */

        private String fGuid;
        private String fSource;
        private String fCreateDepartment;
        private String fCreateDate;
        private String fTaskStatus;
        private String fDeaddate;
        private String fCreateName;
        private String fTaskId;
        private String fTaskName;

        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public String getFSource() {
            return fSource;
        }

        public void setFSource(String fSource) {
            this.fSource = fSource;
        }

        public String getFCreateDepartment() {
            return fCreateDepartment;
        }

        public void setFCreateDepartment(String fCreateDepartment) {
            this.fCreateDepartment = fCreateDepartment;
        }

        public String getFCreateDate() {
            return fCreateDate;
        }

        public void setFCreateDate(String fCreateDate) {
            this.fCreateDate = fCreateDate;
        }

        public String getFTaskStatus() {
            return fTaskStatus;
        }

        public void setFTaskStatus(String fTaskStatus) {
            this.fTaskStatus = fTaskStatus;
        }

        public String getFDeaddate() {
            return fDeaddate;
        }

        public void setFDeaddate(String fDeaddate) {
            this.fDeaddate = fDeaddate;
        }

        public String getFCreateName() {
            return fCreateName;
        }

        public void setFCreateName(String fCreateName) {
            this.fCreateName = fCreateName;
        }

        public String getFTaskId() {
            return fTaskId;
        }

        public void setFTaskId(String fTaskId) {
            this.fTaskId = fTaskId;
        }

        public String getFTaskName() {
            return fTaskName;
        }

        public void setFTaskName(String fTaskName) {
            this.fTaskName = fTaskName;
        }
    }
}
