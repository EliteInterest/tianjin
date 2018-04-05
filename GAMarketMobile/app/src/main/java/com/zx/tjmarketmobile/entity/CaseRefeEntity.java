package com.zx.tjmarketmobile.entity;

import java.util.List;

/**
 * Created by Xiangb on 2017/11/2.
 * 功能：
 */

public class CaseRefeEntity {

    /**
     * total : 5
     * pageTotal : 1
     * currPageNo : 1
     * pageSize : 10
     * rows : []
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
         * fGuid : 5896BD645FEA4C5FBD0197CA65125FAE
         * fNum : null
         * fType : 工商
         * fIllegalAct :
         * fViolateLaw :
         * fViolateContent : null
         * fViolateRule : null
         * fPunishLaw :
         * fPunishContent : null
         * fPunishRule : null
         * fDiscretionStandard :
         */

        private String fGuid;
        private String fNum;
        private String fType;
        private String fIllegalAct;
        private String fViolateLaw;
        private String fViolateContent;
        private String fViolateRule;
        private String fPunishLaw;
        private String fPunishContent;
        private String fPunishRule;
        private String fDiscretionStandard;
        private boolean hasChecked = false;

        public boolean isHasChecked() {
            return hasChecked;
        }

        public void setHasChecked(boolean hasChecked) {
            this.hasChecked = hasChecked;
        }

        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public String getFNum() {
            return fNum;
        }

        public void setFNum(String fNum) {
            this.fNum = fNum;
        }

        public String getFType() {
            return fType;
        }

        public void setFType(String fType) {
            this.fType = fType;
        }

        public String getFIllegalAct() {
            return fIllegalAct;
        }

        public void setFIllegalAct(String fIllegalAct) {
            this.fIllegalAct = fIllegalAct;
        }

        public String getFViolateLaw() {
            return fViolateLaw;
        }

        public void setFViolateLaw(String fViolateLaw) {
            this.fViolateLaw = fViolateLaw;
        }

        public String getFViolateContent() {
            return fViolateContent;
        }

        public void setFViolateContent(String fViolateContent) {
            this.fViolateContent = fViolateContent;
        }

        public String getFViolateRule() {
            return fViolateRule;
        }

        public void setFViolateRule(String fViolateRule) {
            this.fViolateRule = fViolateRule;
        }

        public String getFPunishLaw() {
            return fPunishLaw;
        }

        public void setFPunishLaw(String fPunishLaw) {
            this.fPunishLaw = fPunishLaw;
        }

        public String getFPunishContent() {
            return fPunishContent;
        }

        public void setFPunishContent(String fPunishContent) {
            this.fPunishContent = fPunishContent;
        }

        public String getFPunishRule() {
            return fPunishRule;
        }

        public void setFPunishRule(String fPunishRule) {
            this.fPunishRule = fPunishRule;
        }

        public String getFDiscretionStandard() {
            return fDiscretionStandard;
        }

        public void setFDiscretionStandard(String fDiscretionStandard) {
            this.fDiscretionStandard = fDiscretionStandard;
        }
    }
}
