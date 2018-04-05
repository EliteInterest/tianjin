package com.zx.tjmarketmobile.entity.supervise;

import java.util.List;

/**
 * Created by Xiangb on 2017/11/1.
 * 功能：
 */

public class MyTaskInfoEntity {


    /**
     * total : 3933
     * pageTotal : 394
     * currPageNo : 1
     * pageSize : 10
     * rows : [{"F_ADDRESS":"贵州省贵安新区马场镇下桥路10号","fEntityGuid":"FB5A664300DA4209B197E8576FE27787","F_STATION":"马场镇","fEntityName":"贵安新区马场镇脚板鞋店","ROWNUM_":1,"fEntityType":"个体"},{"F_ADDRESS":"高端装备制造产业南部园区标准厂房","fEntityGuid":"D76D763506AE4C4C969A4342DACDF05D","F_STATION":"马场镇","fEntityName":"贵州泰豪电力科技有限公司","ROWNUM_":2,"fEntityType":"企业"},{"F_ADDRESS":"电子信息产业园电商科创园","fEntityGuid":"28865946676046678C1C7DEDABFA2B5E","F_STATION":"马场镇","fEntityName":"贵州众合毅力商贸有限公司","ROWNUM_":3,"fEntityType":"企业"},{"F_ADDRESS":"马场镇佳林村二组","fEntityGuid":"4835A6A012FF4324984FA3634FD7FECE","F_STATION":"马场镇","fEntityName":"贵州贵安新区栗木医药咨询有限公司","ROWNUM_":4,"fEntityType":"企业"},{"F_ADDRESS":"电子信息产业园电商科创园","fEntityGuid":"4BF44405CD2E441584C3B7CB3D948C55","F_STATION":"马场镇","F_MARK":"食品经营企业","fEntityName":"贵州喵了个咪贸易有限公司","ROWNUM_":5,"fEntityType":"企业"},{"F_ADDRESS":"贵安新区马场镇川心村代家坡组","fEntityGuid":"AFB4E55DC04748C1B9E0C8732BE0D2F8","F_STATION":"马场镇","fEntityName":"贵安新区马场镇李静电动车销售店","ROWNUM_":6,"fEntityType":"个体"},{"F_ADDRESS":"贵安新区马场镇盐泉路21号","fEntityGuid":"ADE292BBE1F84DCE9EE40D83B799C9C9","F_STATION":"马场镇","fEntityName":"贵安新区马场镇秀菊杂货店","ROWNUM_":7,"fEntityType":"个体"},{"F_ADDRESS":"贵州省贵安新区马场镇卫生院门面","fEntityGuid":"2550F93798804C70A764746C5009E9E7","F_STATION":"马场镇","fEntityName":"贵安新区马场镇和鞋人生鞋店","ROWNUM_":8,"fEntityType":"个体"},{"F_ADDRESS":"电子信息产业园电商科创园","fEntityGuid":"ED19500FA1C646219E0D29A3DE6FAB17","F_STATION":"马场镇","fEntityName":"贵州贵华兄弟机械设备租赁有限公司","ROWNUM_":9,"fEntityType":"企业"},{"F_ADDRESS":"贵州省贵安新区马场镇下桥路","fEntityGuid":"792F6E9FB071428FB887EC4B5FA1844F","F_STATION":"马场镇","fEntityName":"贵安新区马场镇衣品人生服装店","ROWNUM_":10,"fEntityType":"个体"}]
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

    public static class RowsBean {
        /**
         * F_ADDRESS : 贵州省贵安新区马场镇下桥路10号
         * fEntityGuid : FB5A664300DA4209B197E8576FE27787
         * F_STATION : 马场镇
         * fEntityName : 贵安新区马场镇脚板鞋店
         * ROWNUM_ : 1
         * fEntityType : 个体
         * F_MARK : 食品经营企业
         */

        private String F_ADDRESS;
        private String fEntityGuid;
        private String F_STATION;
        private String fEntityName;
        private int ROWNUM_;
        private String fEntityType;
        private String F_MARK;

        public String getF_ADDRESS() {
            return F_ADDRESS;
        }

        public void setF_ADDRESS(String F_ADDRESS) {
            this.F_ADDRESS = F_ADDRESS;
        }

        public String getFEntityGuid() {
            return fEntityGuid;
        }

        public void setFEntityGuid(String fEntityGuid) {
            this.fEntityGuid = fEntityGuid;
        }

        public String getF_STATION() {
            return F_STATION;
        }

        public void setF_STATION(String F_STATION) {
            this.F_STATION = F_STATION;
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

        public String getFEntityType() {
            return fEntityType;
        }

        public void setFEntityType(String fEntityType) {
            this.fEntityType = fEntityType;
        }

        public String getF_MARK() {
            return F_MARK;
        }

        public void setF_MARK(String F_MARK) {
            this.F_MARK = F_MARK;
        }
    }
}
