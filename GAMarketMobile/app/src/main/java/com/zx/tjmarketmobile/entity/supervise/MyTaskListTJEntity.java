package com.zx.tjmarketmobile.entity.supervise;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class MyTaskListTJEntity implements Serializable {


    private int total;
    private List<RowsBean> list;
    private int pageNo;
    private int pageSize;
    private int pages;
    private int size;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getList() {
        return list;
    }

    public void setList(List<RowsBean> rows) {
        this.list = list;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPagesize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public static class RowsBean implements Serializable {
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

        private String id;
        private String departmentId;
        private String taskName;
        private String taskNum;
        private String source;
        private int type;
        private int status;
        private long startDate;
        private long deadline;
        private String reamrk;
        private String userName;
        private String userId;
        private int overdue;
        private long remindDate;
        private int remindDay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public String getTaskNum() {
            return taskNum;
        }

        public void setTaskNum(String taskNum) {
            this.taskNum = taskNum;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getStartDate() {
            return startDate;
        }

        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getDeadline() {
            return deadline;
        }

        public void setDeadline(long deadline) {
            this.deadline = deadline;
        }


        public String getReamrk() {
            return reamrk;
        }

        public void setReamrk(String reamrk) {
            this.reamrk = reamrk;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userName) {
            this.userId = userId;
        }

        public void setOverdue(int overdue) {
            this.overdue = overdue;
        }

        public long getOverdue() {
            return overdue;
        }

        public void setRemindDate(long remindDate) {
            this.remindDate = remindDate;
        }

        public long getRemindDate() {
            return remindDate;
        }

        public void setRemindDay(int remindDay) {
            this.remindDay = remindDay;
        }

        public int getRemindDay() {
            return remindDay;
        }

        @Override
        public String toString() {
            return id + departmentId + taskName + taskNum + source + "";
        }
    }


    /**
     * total : 8
     * pageTotal : 1
     * currPageNo : 1
     * pageSize : 10
     * rows : [{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"0FD32E7EE622481AB45699B4840DB81B","ROWNUM_":1,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-009","F_CREATE_TIME":1490861732000,"fTaskName":"测试分页"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"41EE6CC68C7747688558EBEBB22B1D79","ROWNUM_":2,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-012","F_CREATE_TIME":1490861790000,"fTaskName":"测试分页6"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"9670DBDB538F4DEFBBD640B2FD70C9E1","ROWNUM_":3,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-011","F_CREATE_TIME":1490861771000,"fTaskName":"测试分页5"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"E884F94595454CBABF3F3505DFA948CC","ROWNUM_":4,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-007","F_CREATE_TIME":1490861690000,"fTaskName":"测试分页"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"9869143CCD8440589B54A4EFA3628D42","ROWNUM_":5,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-008","F_CREATE_TIME":1490861710000,"fTaskName":"测试分页1"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"AFAF77B6026E4624BB2143A43A1CD5D1","ROWNUM_":6,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-013","F_CREATE_TIME":1490861810000,"fTaskName":"测试分页7"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"CE8C2F71F30144C4BFC0C5E9FB9347EA","ROWNUM_":7,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-014","F_CREATE_TIME":1490861826000,"fTaskName":"测试分页9"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"985483FB98FF467B85D9BE59F273835A","ROWNUM_":8,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-010","F_CREATE_TIME":1490861753000,"fTaskName":"测试分页4"}]
     */

}
