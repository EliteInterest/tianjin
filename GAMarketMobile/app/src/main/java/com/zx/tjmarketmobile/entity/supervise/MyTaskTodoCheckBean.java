package com.zx.tjmarketmobile.entity.supervise;

import java.util.List;

/**
 * Created by zhouzq on 2017/5/26.
 */

public class MyTaskTodoCheckBean {
    /**
     * code : 20000
     * message : 操作成功！
     * data : [{"F_ENTITY_NAME":"贵州淼特贸易有限公司","F_LONGITUDE":106.46,"F_STATUS":"待处置","F_ENTITY_GUID":"F3827DB5455A49B5A452F7B2A089427F","F_GUID":"85267C2D14014FF39761451FEBE96A3D","F_TASK_ID":"2017-007","F_LATITUDE":26.36},{"F_ENTITY_NAME":"贵州华泽科谷科技有限公司","F_LONGITUDE":106.46,"F_STATUS":"待处置","F_ENTITY_GUID":"4DDB22AC92D5406A9E607981AC95B196","F_GUID":"06E55012D3A34800B8719B6C8F42089B","F_TASK_ID":"2017-007","F_LATITUDE":26.36}]
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
         * F_ENTITY_NAME : 贵州淼特贸易有限公司
         * F_LONGITUDE : 106.46
         * F_STATUS : 待处置
         * F_ENTITY_GUID : F3827DB5455A49B5A452F7B2A089427F
         * F_GUID : 85267C2D14014FF39761451FEBE96A3D
         * F_TASK_ID : 2017-007
         * F_LATITUDE : 26.36
         */

        private String F_ENTITY_NAME;
        private double F_LONGITUDE;
        private String F_STATUS;
        private String F_ENTITY_GUID;
        private String F_GUID;
        private String F_TASK_ID;
        private double F_LATITUDE;
        private String F_ADDRESS;

        public String getF_ADDRESS() {
            return F_ADDRESS;
        }

        public void setF_ADDRESS(String f_ADDRESS) {
            F_ADDRESS = f_ADDRESS;
        }

        public String getF_ENTITY_NAME() {
            return F_ENTITY_NAME;
        }

        public void setF_ENTITY_NAME(String F_ENTITY_NAME) {
            this.F_ENTITY_NAME = F_ENTITY_NAME;
        }

        public double getF_LONGITUDE() {
            return F_LONGITUDE;
        }

        public void setF_LONGITUDE(double F_LONGITUDE) {
            this.F_LONGITUDE = F_LONGITUDE;
        }

        public String getF_STATUS() {
            return F_STATUS;
        }

        public void setF_STATUS(String F_STATUS) {
            this.F_STATUS = F_STATUS;
        }

        public String getF_ENTITY_GUID() {
            return F_ENTITY_GUID;
        }

        public void setF_ENTITY_GUID(String F_ENTITY_GUID) {
            this.F_ENTITY_GUID = F_ENTITY_GUID;
        }

        public String getF_GUID() {
            return F_GUID;
        }

        public void setF_GUID(String F_GUID) {
            this.F_GUID = F_GUID;
        }

        public String getF_TASK_ID() {
            return F_TASK_ID;
        }

        public void setF_TASK_ID(String F_TASK_ID) {
            this.F_TASK_ID = F_TASK_ID;
        }

        public double getF_LATITUDE() {
            return F_LATITUDE;
        }

        public void setF_LATITUDE(double F_LATITUDE) {
            this.F_LATITUDE = F_LATITUDE;
        }
    }
}
