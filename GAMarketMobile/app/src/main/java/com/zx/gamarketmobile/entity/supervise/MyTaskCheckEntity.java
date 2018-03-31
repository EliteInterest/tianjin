package com.zx.gamarketmobile.entity.supervise;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouzq on 2017/4/1.
 */

public class MyTaskCheckEntity implements Serializable {


    /**
     * total : 4
     * pageTotal : 1
     * currPageNo : 1
     * pageSize : 10
     * rows : [{"fGuid":"011233F9AA4642A4A8DBDDB2E25C79C7","fEntityGuid":"EABC5DC60A7D442BB011119EE9E9F239","fTaskId":"2017-011","FSTATUS":"待指派","fEntityName":"贵安新区湖潮乡凯旋五金灯饰","ROWNUM_":1},{"fGuid":"B943C40BD33643B4AF5240CD4CE0C13D","fEntityGuid":"C31A28EBCC0D41A181C175EE2C771B42","fLatitude":26.44,"fTaskId":"2017-011","FSTATUS":"待指派","fLongitude":106.5,"fEntityName":"贵州融汇百川贸易有限公司","ROWNUM_":2},{"fGuid":"1A2E6EA0101140D9AD1BFC8D7DCD609C","fEntityGuid":"AB15DB08B6E84A62A0C658C8748A9F21","fLatitude":26.44,"fTaskId":"2017-011","FSTATUS":"待指派","fLongitude":106.5,"fEntityName":"贵州小米果汽车租赁服务有限公司","ROWNUM_":3},{"fGuid":"4EBFBCD90800411E8DD7B2EB2463120A","fEntityGuid":"F1084164984B4BC5BCFFC93586759464","fLatitude":26.44,"fTaskId":"2017-011","FSTATUS":"待指派","fLongitude":106.5,"fEntityName":"贵州贵安新区家腾房屋征收服务有限公司","ROWNUM_":4}]
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

    public static class RowsBean implements Serializable {
        /**
         * fGuid : 011233F9AA4642A4A8DBDDB2E25C79C7
         * fEntityGuid : EABC5DC60A7D442BB011119EE9E9F239
         * fTaskId : 2017-011
         * FSTATUS : 待指派
         * fEntityName : 贵安新区湖潮乡凯旋五金灯饰
         * ROWNUM_ : 1
         * fLatitude : 26.44
         * fLongitude : 106.5
         */

        private String fGuid;
        private String fEntityGuid;
        private String fTaskId;
        private String FSTATUS;
        private String fAddress;//F_ADDRESS
        private String fEntityName;
        private int ROWNUM_;
        private double fLatitude;
        private double fLongitude;

        public String getfAddress() {
            return fAddress;
        }

        public void setfAddress(String fAddress) {
            this.fAddress = fAddress;
        }


        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public String getFEntityGuid() {
            return fEntityGuid;
        }

        public void setFEntityGuid(String fEntityGuid) {
            this.fEntityGuid = fEntityGuid;
        }

        public String getFTaskId() {
            return fTaskId;
        }

        public void setFTaskId(String fTaskId) {
            this.fTaskId = fTaskId;
        }

        public String getFSTATUS() {
            return FSTATUS;
        }

        public void setFSTATUS(String FSTATUS) {
            this.FSTATUS = FSTATUS;
        }

        public String getFEntityName() {
            return fEntityName;
        }

        public void setFEntityName(String fEntityName) {
            this.fEntityName = fEntityName;
        }

        public int getROWNUM_() {
            return ROWNUM_;
        }

        public void setROWNUM_(int ROWNUM_) {
            this.ROWNUM_ = ROWNUM_;
        }

        public double getFLatitude() {
            return fLatitude;
        }

        public void setFLatitude(double fLatitude) {
            this.fLatitude = fLatitude;
        }

        public double getFLongitude() {
            return fLongitude;
        }

        public void setFLongitude(double fLongitude) {
            this.fLongitude = fLongitude;
        }
    }
}
