package com.zx.gamarketmobile.entity.supervise;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class MyTaskListEntity implements Serializable{


    /**
     * total : 8
     * pageTotal : 1
     * currPageNo : 1
     * pageSize : 10
     * rows : [{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"0FD32E7EE622481AB45699B4840DB81B","ROWNUM_":1,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-009","F_CREATE_TIME":1490861732000,"fTaskName":"测试分页"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"41EE6CC68C7747688558EBEBB22B1D79","ROWNUM_":2,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-012","F_CREATE_TIME":1490861790000,"fTaskName":"测试分页6"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"9670DBDB538F4DEFBBD640B2FD70C9E1","ROWNUM_":3,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-011","F_CREATE_TIME":1490861771000,"fTaskName":"测试分页5"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"E884F94595454CBABF3F3505DFA948CC","ROWNUM_":4,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-007","F_CREATE_TIME":1490861690000,"fTaskName":"测试分页"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"9869143CCD8440589B54A4EFA3628D42","ROWNUM_":5,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-008","F_CREATE_TIME":1490861710000,"fTaskName":"测试分页1"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"AFAF77B6026E4624BB2143A43A1CD5D1","ROWNUM_":6,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-013","F_CREATE_TIME":1490861810000,"fTaskName":"测试分页7"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"CE8C2F71F30144C4BFC0C5E9FB9347EA","ROWNUM_":7,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-014","F_CREATE_TIME":1490861826000,"fTaskName":"测试分页9"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"985483FB98FF467B85D9BE59F273835A","ROWNUM_":8,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-010","F_CREATE_TIME":1490861753000,"fTaskName":"测试分页4"}]
     */

    private int total;
    private int pageTotal;
    private int currPageNo;
    private int pageSize;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getCurrPageNo() {
        return currPageNo;
    }

    public void setCurrPageNo(int currPageNo) {
        this.currPageNo = currPageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable{
        /**
         * fIsOver : 1
         * fCreateDepartment : 市场监管处
         * fTaskStatus : 待下发
         * fCreateName : 刘天
         * fDeadLine : 2017-03-30
         * F_GUID : 0FD32E7EE622481AB45699B4840DB81B
         * ROWNUM_ : 1
         * fSource : 年报抽查
         * F_DEADLINE : 1490803200000
         * fCreateDate : 2017-03-30
         * fTaskId : 2017-009
         * F_CREATE_TIME : 1490861732000
         * fTaskName : 测试分页
         */

        private String fIsOver;
        private String fCreateDepartment;
        private String fTaskStatus;
        private String fCreateName;
        private String fDeadLine;
        private String F_GUID;
        private int ROWNUM_;
        private String fSource;
        private long F_DEADLINE;
        private String fCreateDate;
        private String fTaskId;
        private long F_CREATE_TIME;
        private String fTaskName;
        private String fIsBack;

        public String getfIsBack() {
            return fIsBack;
        }

        public void setfIsBack(String fIsBack) {
            this.fIsBack = fIsBack;
        }

        public String getFIsOver() {
            return fIsOver;
        }

        public void setFIsOver(String fIsOver) {
            this.fIsOver = fIsOver;
        }

        public String getFCreateDepartment() {
            return fCreateDepartment;
        }

        public void setFCreateDepartment(String fCreateDepartment) {
            this.fCreateDepartment = fCreateDepartment;
        }

        public String getFTaskStatus() {
            return fTaskStatus;
        }

        public void setFTaskStatus(String fTaskStatus) {
            this.fTaskStatus = fTaskStatus;
        }

        public String getFCreateName() {
            return fCreateName;
        }

        public void setFCreateName(String fCreateName) {
            this.fCreateName = fCreateName;
        }

        public String getFDeadLine() {
            return fDeadLine;
        }

        public void setFDeadLine(String fDeadLine) {
            this.fDeadLine = fDeadLine;
        }

        public String getF_GUID() {
            return F_GUID;
        }

        public void setF_GUID(String F_GUID) {
            this.F_GUID = F_GUID;
        }

        public int getROWNUM_() {
            return ROWNUM_;
        }

        public void setROWNUM_(int ROWNUM_) {
            this.ROWNUM_ = ROWNUM_;
        }

        public String getFSource() {
            return fSource;
        }

        public void setFSource(String fSource) {
            this.fSource = fSource;
        }

        public long getF_DEADLINE() {
            return F_DEADLINE;
        }

        public void setF_DEADLINE(long F_DEADLINE) {
            this.F_DEADLINE = F_DEADLINE;
        }

        public String getFCreateDate() {
            return fCreateDate;
        }

        public void setFCreateDate(String fCreateDate) {
            this.fCreateDate = fCreateDate;
        }

        public String getFTaskId() {
            return fTaskId;
        }

        public void setFTaskId(String fTaskId) {
            this.fTaskId = fTaskId;
        }

        public long getF_CREATE_TIME() {
            return F_CREATE_TIME;
        }

        public void setF_CREATE_TIME(long F_CREATE_TIME) {
            this.F_CREATE_TIME = F_CREATE_TIME;
        }

        public String getFTaskName() {
            return fTaskName;
        }

        public void setFTaskName(String fTaskName) {
            this.fTaskName = fTaskName;
        }
    }
}
