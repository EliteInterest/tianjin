package com.zx.tjmarketmobile.entity.infomanager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouzq on 2017/3/24.
 */

public class InfoManagerDevice implements Serializable {


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
         * directorPerson : null
         * directorPhone : null
         * latitude : 39.026658
         * typeName : 场(厂)内专用机动车辆
         * name : 蓄电池平衡重式叉车
         * isMajor : 1
         * id : e21914c4c86f11e79fcc000c2934879e
         * enterpriseId : d974639e979d11e781d1000c2934879e
         * enterpriseName : 鑫鑫同达（天津）精密模具研发制造有限公司
         * categoryName : 叉车
         * enterpriseType : null
         * longitude : 117.702828
         */

        private String directorPerson;
        private String directorPhone;
        private String latitude;
        private String typeName;
        private String name;
        private String isMajor;
        private String id;
        private String enterpriseId;
        private String enterpriseName;
        private String categoryName;
        private String enterpriseType;
        private String longitude;

        public String getDirectorPerson() {
            return directorPerson;
        }

        public void setDirectorPerson(String directorPerson) {
            this.directorPerson = directorPerson;
        }

        public String getDirectorPhone() {
            return directorPhone;
        }

        public void setDirectorPhone(String directorPhone) {
            this.directorPhone = directorPhone;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIsMajor() {
            return isMajor;
        }

        public void setIsMajor(String isMajor) {
            this.isMajor = isMajor;
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

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getEnterpriseType() {
            return enterpriseType;
        }

        public void setEnterpriseType(String enterpriseType) {
            this.enterpriseType = enterpriseType;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
