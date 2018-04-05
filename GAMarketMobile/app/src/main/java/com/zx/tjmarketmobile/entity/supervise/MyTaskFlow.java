package com.zx.tjmarketmobile.entity.supervise;

import java.util.List;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class MyTaskFlow {
    /**
     * code : 20000
     * message : Success
     * data : [{"fGuid":"DEA86A88D41E464B9574287135747E4C","fLink":"待处置","fHandleUser":"付朝魁","fTaskGuid":"2017-009","fName":"指派任务","fIsPass":"1","fReamrk":"指派给分局的人员处置","fHandleDate":"2017-03-29 03:47:48","fNextHandleUser":"舒平","fHandleDepartment":"马场分局"},{"fGuid":"5780891906CE48679C6E9DDB0F2A2909","fLink":"待下发","fHandleUser":"刘天","fTaskGuid":"2017-009","fName":"任务制定","fReamrk":"请下发","fHandleDate":"2017-03-29 02:32:17","fNextHandleUser":"刘天","fHandleDepartment":"市场监管处"}]
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
         * fGuid : DEA86A88D41E464B9574287135747E4C
         * fLink : 待处置
         * fHandleUser : 付朝魁
         * fTaskGuid : 2017-009
         * fName : 指派任务
         * fIsPass : 1
         * fReamrk : 指派给分局的人员处置
         * fHandleDate : 2017-03-29 03:47:48
         * fNextHandleUser : 舒平
         * fHandleDepartment : 马场分局
         */

        private String fGuid;
        private String fLink;
        private String fHandleUser;
        private String fTaskGuid;
        private String fName;
        private String fIsPass;
        private String fReamrk;
        private String fHandleDate;
        private String fNextHandleUser;
        private String fHandleDepartment;

        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public String getFLink() {
            return fLink;
        }

        public void setFLink(String fLink) {
            this.fLink = fLink;
        }

        public String getFHandleUser() {
            return fHandleUser;
        }

        public void setFHandleUser(String fHandleUser) {
            this.fHandleUser = fHandleUser;
        }

        public String getFTaskGuid() {
            return fTaskGuid;
        }

        public void setFTaskGuid(String fTaskGuid) {
            this.fTaskGuid = fTaskGuid;
        }

        public String getFName() {
            return fName;
        }

        public void setFName(String fName) {
            this.fName = fName;
        }

        public String getFIsPass() {
            return fIsPass;
        }

        public void setFIsPass(String fIsPass) {
            this.fIsPass = fIsPass;
        }

        public String getFReamrk() {
            return fReamrk;
        }

        public void setFReamrk(String fReamrk) {
            this.fReamrk = fReamrk;
        }

        public String getFHandleDate() {
            return fHandleDate;
        }

        public void setFHandleDate(String fHandleDate) {
            this.fHandleDate = fHandleDate;
        }

        public String getFNextHandleUser() {
            return fNextHandleUser;
        }

        public void setFNextHandleUser(String fNextHandleUser) {
            this.fNextHandleUser = fNextHandleUser;
        }

        public String getFHandleDepartment() {
            return fHandleDepartment;
        }

        public void setFHandleDepartment(String fHandleDepartment) {
            this.fHandleDepartment = fHandleDepartment;
        }
    }
}
