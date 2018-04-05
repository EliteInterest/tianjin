package com.zx.tjmarketmobile.entity.infomanager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class InfoManagerLegalSelect implements Serializable {


    private List<LawBean> commonLaw;
    private List<LawBean> departmentLaw;
    private List<LawBean> userLaw;

    public List<LawBean> getCommonLaw() {
        return commonLaw;
    }

    public void setCommonLaw(List<LawBean> commonLaw) {
        this.commonLaw = commonLaw;
    }

    public List<LawBean> getDepartmentLaw() {
        return this.departmentLaw;
    }

    public void setDepartmentLaw(List<LawBean> departmentLaw) {
        this.departmentLaw = departmentLaw;
    }

    public List<LawBean> getUserLaw() {
        return userLaw;
    }

    public void setUserLaw(List<LawBean> userLaw) {
        this.userLaw = userLaw;
    }

    public static class LawBean implements Serializable {
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
        private String name;
        private int parentId;
        private String url;
        private String icon;
        private int orders;
        private String departmentId;
        private String userId;
        private String fileId;
        private List<LawBean> childMenus;
        private int childMenuTotal;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getFileId() {
            return fileId;
        }

        public void setFileId(String fileId) {
            this.fileId = fileId;
        }

        public List<LawBean> getChildMenus() {
            return childMenus;
        }

        public void setChildMenus(List<LawBean> childMenus) {
            this.childMenus = childMenus;
        }

        public int getChildMenuTotal() {
            return childMenuTotal;
        }

        public void setChildMenuTotal(int childMenuTotal) {
            this.childMenus = childMenus;
        }

        /**
         * total : 8
         * pageTotal : 1
         * currPageNo : 1
         * pageSize : 10
         * rows : [{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"0FD32E7EE622481AB45699B4840DB81B","ROWNUM_":1,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-009","F_CREATE_TIME":1490861732000,"fTaskName":"测试分页"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"41EE6CC68C7747688558EBEBB22B1D79","ROWNUM_":2,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-012","F_CREATE_TIME":1490861790000,"fTaskName":"测试分页6"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"9670DBDB538F4DEFBBD640B2FD70C9E1","ROWNUM_":3,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-011","F_CREATE_TIME":1490861771000,"fTaskName":"测试分页5"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"E884F94595454CBABF3F3505DFA948CC","ROWNUM_":4,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-007","F_CREATE_TIME":1490861690000,"fTaskName":"测试分页"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"9869143CCD8440589B54A4EFA3628D42","ROWNUM_":5,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-008","F_CREATE_TIME":1490861710000,"fTaskName":"测试分页1"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"AFAF77B6026E4624BB2143A43A1CD5D1","ROWNUM_":6,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-013","F_CREATE_TIME":1490861810000,"fTaskName":"测试分页7"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"CE8C2F71F30144C4BFC0C5E9FB9347EA","ROWNUM_":7,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-014","F_CREATE_TIME":1490861826000,"fTaskName":"测试分页9"},{"fIsOver":"1","fCreateDepartment":"市场监管处","fTaskStatus":"待下发","fCreateName":"刘天","fDeadLine":"2017-03-30","F_GUID":"985483FB98FF467B85D9BE59F273835A","ROWNUM_":8,"fSource":"年报抽查","F_DEADLINE":1490803200000,"fCreateDate":"2017-03-30","fTaskId":"2017-010","F_CREATE_TIME":1490861753000,"fTaskName":"测试分页4"}]
         */

    }
}
