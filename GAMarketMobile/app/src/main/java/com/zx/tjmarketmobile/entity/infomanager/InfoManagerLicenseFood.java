package com.zx.tjmarketmobile.entity.infomanager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class InfoManagerLicenseFood implements Serializable {


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
         * licNum : JY21200160080690
         * address : null
         * latitude : 39.026658
         * legalPerson : 史亭亮
         * id : 01F5CF7761FD4148B099D4C8CB516D2A
         * enterpriseId : d97b92f4979d11e781d1000c2934879e
         * enterpriseName : 天津市滨海新区旭日发拉面馆
         * longitude : 117.702828
         */

        private String licNum;
        private String address;
        private String latitude;
        private String legalPerson;
        private String id;
        private String enterpriseId;
        private String enterpriseName;
        private String longitude;

        public String getLicNum() {
            return licNum;
        }

        public void setLicNum(String licNum) {
            this.licNum = licNum;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
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

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
