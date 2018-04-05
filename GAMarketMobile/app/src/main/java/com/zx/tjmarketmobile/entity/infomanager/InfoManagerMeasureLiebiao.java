package com.zx.tjmarketmobile.entity.infomanager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class InfoManagerMeasureLiebiao implements Serializable {


    private int total;
    private List<RowsBean> list;
    private int pageNo;
    private int pageSize;
    private int pages;
    private int size;
    private String tableId;
    private String describle;
    private String remark;
    private String departmentId;
    private String departmentName;


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

    public String getTableId()
    {
        return tableId;
    }

    public void setTableId(String tableId)
    {
        this.tableId =tableId;
    }

    public String getDescrible()
    {
        return describle;
    }

    public void setDescrible(String describle)
    {
        this.describle = describle;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark =remark;
    }

    public  String getDepartmentId()
    {
        return departmentId;
    }

    public void setDepartmentId(String departmentId)
    {
        this.departmentId=departmentId;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }

    public void setDepartmentName(String departmentName)
    {
        this.departmentName =departmentName;
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

        private String open_id;
        private String ranges;
        private String table_id;
        private String table_name;
        private String department_id;
        private String id;
        private String enterpriseId;
        private String enterpriseName;
        private String phone;
        private String email;
        private String serialNumber;
        private String measuringInstrumentsName;
        private String model;
        private String factory;
        private String numbering;
        private String accuracy;
        private String identificationAgencies;
        private long identificationDate;
        private int type;
        private String col1;
        private String col2;
        private List<RowsBean1> list;//??


        public String getOpen_id()

        {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }

        public String getRanges() {
            return ranges;
        }

        public void setRanges(String ranges) {
            this.ranges = ranges;
        }

        public String getTable_id() {
            return table_id;
        }

        public void setTable_id(String table_id) {
            this.table_id = table_id;
        }

        public String getTable_name() {
            return table_name;
        }

        public void setTable_name(String table_name) {
            this.table_name = table_name;
        }

        public String getDepartment_id() {
            return department_id;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEnterpriseId() {
            return enterpriseId;
        }

        public void setEnterpriseId(String enterpriseId) {
            this.enterpriseId = enterpriseId;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getMeasuringInstrumentsName() {
            return measuringInstrumentsName;
        }

        public void setMeasuringInstrumentsName(String measuringInstrumentsName) {
            this.measuringInstrumentsName = measuringInstrumentsName;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getFactory() {
            return factory;
        }

        public void setFactory(String factory) {
            this.factory = factory;
        }

        public String getNumbering() {
            return numbering;
        }

        public void setNumbering(String numbering) {
            this.numbering = numbering;
        }

        public String getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public String getIdentificationAgencies() {
            return identificationAgencies;
        }

        public void setIdentificationAgencies(String identificationAgencies) {
            this.identificationAgencies = identificationAgencies;
        }

        public long getIdentificationDate() {
            return identificationDate;
        }

        public void setIdentificationDate(long identificationDate) {
            this.identificationDate = identificationDate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getCol1() {
            return col1;
        }

        public void setCol1(String col1) {
            this.col1 = col1;
        }

        public String getCol2() {
            return col2;
        }

        public void setCol2(String col2) {
            this.col2 = col2;
        }

        public List<RowsBean1> getList()
        {
            return list;
        }

        public void setList(List<RowsBean1> list)
        {
            this.list = list;
        }

        public static class RowsBean1 implements Serializable {
            private String col1;
            private String col2;

            public String getCol1() {
                return col1;
            }

            public void setCol1(String col1) {
                this.col1 = col1;
            }

            public String getCol2() {
                return col2;
            }

            public void setCol2(String col2) {
                this.col2 = col2;
            }
        }
    }
}
