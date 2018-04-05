package com.zx.tjmarketmobile.entity;

import java.util.List;

/**
 * Created by Xiangb on 2017/12/28.
 * 功能：
 */

public class SynergyDTInfoEntity {

    /**
     * img : null
     * checkResult : [{"fCheckResult":"无此项","fCateGory":"机房及其设备","fItemId":"00FBFDB95D534341AAA9583408C40AB3","fRemark":"null","fItemType":"1","fItemName":"机房干净无杂物"},{"fCheckResult":"无此项","fCateGory":"机房及其设备","fItemId":"FFAFDC2644BE4BF5BDF29552FF036BF3","fRemark":"null","fItemType":"1","fItemName":"机房照明及空调正常"},{"fCheckResult":"无此项","fCateGory":"机房及其设备","fItemId":"2C8C707880A041F5BB56C032EA28588D","fRemark":"null","fItemType":"1","fItemName":"机房门窗、警示标志完好"},{"fCheckResult":"无此项","fCateGory":"机房及其设备","fItemId":"25C7C53C66CA4DD8B12687FB073C0AFA","fRemark":"null","fItemType":"1","fItemName":"灭火器材未过期"},{"fCheckResult":"无此项","fCateGory":"机房及其设备","fItemId":"FADBFBB4D34D430A80E6941F01818A14","fRemark":"null","fItemType":"1","fItemName":"设备无异响、异味"},{"fCheckResult":"无此项","fCateGory":"机房及其设备","fItemId":"DA7E2E92A0994A0FAF7E1863B2B30C1C","fRemark":"null","fItemType":"1","fItemName":"紧急救援设备齐全"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"D78CD84A98B44E68A9094729DC5242F0","fRemark":"null","fItemType":"1","fItemName":"电梯运行无异响异动"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"8103AAE832DF47389BE96D3388F26187","fRemark":"null","fItemType":"1","fItemName":"电梯使用标识完好、在有效期"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"0282CE4A06DB4C2AAC5AEBF5D559144B","fRemark":"null","fItemType":"1","fItemName":"电梯困人救援提示牌完好"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"F59E163A41B241098627F3ABA5077E78","fRemark":"null","fItemType":"1","fItemName":"安全注意事项和警示标志齐全清晰"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"B397D3AB66AD453091221A1A96857D9E","fRemark":"null","fItemType":"1","fItemName":"照明系统及风扇系统正常"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"B112B08D85B947A7A524C5755B6E3966","fRemark":"null","fItemType":"1","fItemName":"五方通话系统正常使用"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"720B89501B05493685D6332FD3094FE5","fRemark":"null","fItemType":"1","fItemName":"紧急报警装置有效"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"9A46FD3465C744FD9687E5A741C2ABC9","fRemark":"null","fItemType":"1","fItemName":"轿门防夹人装置有效"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"4DBCE43899894BBFBA96974761322397","fRemark":"null","fItemType":"1","fItemName":"开关门运行无卡阻"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"B4B5A4D1FD664EA981D4526545561397","fRemark":"null","fItemType":"1","fItemName":"选层按钮及显示正常"},{"fCheckResult":"无此项","fCateGory":"轿厢及层站","fItemId":"1D23CE4A3EC843DFAA2426E4FE2AD6A5","fRemark":"null","fItemType":"1","fItemName":"消防开关玻璃完好"}]
     * info : {"fGuid":"FD003D56FADF489D86DAFCA2C64188A6","fMaintenanceDate":"2017-08-28","fHandleUser":"出来了","fMaintenanceQuality":"满意","fEntityGuid":"DF2953DB8CBD454791E755EDAC4DB9E3","fElevatoeNumber":"他咯","fStatus":"1","fCreateUserId":"DF2953DB8CBD454791E755EDAC4DB9E3","fInsertTime":"2017-08-30","fEntityName":"测量","fRemark":"某","fCheckDate":"2017-08-16"}
     */

    private String img;
    private InfoBean info;
    private List<CheckResultBean> checkResult;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<CheckResultBean> getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(List<CheckResultBean> checkResult) {
        this.checkResult = checkResult;
    }

    public static class InfoBean {
        /**
         * fGuid : FD003D56FADF489D86DAFCA2C64188A6
         * fMaintenanceDate : 2017-08-28
         * fHandleUser : 出来了
         * fMaintenanceQuality : 满意
         * fEntityGuid : DF2953DB8CBD454791E755EDAC4DB9E3
         * fElevatoeNumber : 他咯
         * fStatus : 1
         * fCreateUserId : DF2953DB8CBD454791E755EDAC4DB9E3
         * fInsertTime : 2017-08-30
         * fEntityName : 测量
         * fRemark : 某
         * fCheckDate : 2017-08-16
         */

        private String fGuid;
        private String fMaintenanceDate;
        private String fHandleUser;
        private String fMaintenanceQuality;
        private String fEntityGuid;
        private String fElevatoeNumber;
        private String fStatus;
        private String fCreateUserId;
        private String fInsertTime;
        private String fEntityName;
        private String fRemark;
        private String fCheckDate;

        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public String getFMaintenanceDate() {
            return fMaintenanceDate;
        }

        public void setFMaintenanceDate(String fMaintenanceDate) {
            this.fMaintenanceDate = fMaintenanceDate;
        }

        public String getFHandleUser() {
            return fHandleUser;
        }

        public void setFHandleUser(String fHandleUser) {
            this.fHandleUser = fHandleUser;
        }

        public String getFMaintenanceQuality() {
            return fMaintenanceQuality;
        }

        public void setFMaintenanceQuality(String fMaintenanceQuality) {
            this.fMaintenanceQuality = fMaintenanceQuality;
        }

        public String getFEntityGuid() {
            return fEntityGuid;
        }

        public void setFEntityGuid(String fEntityGuid) {
            this.fEntityGuid = fEntityGuid;
        }

        public String getFElevatoeNumber() {
            return fElevatoeNumber;
        }

        public void setFElevatoeNumber(String fElevatoeNumber) {
            this.fElevatoeNumber = fElevatoeNumber;
        }

        public String getFStatus() {
            return fStatus;
        }

        public void setFStatus(String fStatus) {
            this.fStatus = fStatus;
        }

        public String getFCreateUserId() {
            return fCreateUserId;
        }

        public void setFCreateUserId(String fCreateUserId) {
            this.fCreateUserId = fCreateUserId;
        }

        public String getFInsertTime() {
            return fInsertTime;
        }

        public void setFInsertTime(String fInsertTime) {
            this.fInsertTime = fInsertTime;
        }

        public String getFEntityName() {
            return fEntityName;
        }

        public void setFEntityName(String fEntityName) {
            this.fEntityName = fEntityName;
        }

        public String getFRemark() {
            return fRemark;
        }

        public void setFRemark(String fRemark) {
            this.fRemark = fRemark;
        }

        public String getFCheckDate() {
            return fCheckDate;
        }

        public void setFCheckDate(String fCheckDate) {
            this.fCheckDate = fCheckDate;
        }
    }

    public static class CheckResultBean {
        /**
         * fCheckResult : 无此项
         * fCateGory : 机房及其设备
         * fItemId : 00FBFDB95D534341AAA9583408C40AB3
         * fRemark : null
         * fItemType : 1
         * fItemName : 机房干净无杂物
         */

        private String fCheckResult;
        private String fCateGory;
        private String fItemId;
        private String fRemark;
        private String fItemType;
        private String fItemName;

        public String getFCheckResult() {
            return fCheckResult;
        }

        public void setFCheckResult(String fCheckResult) {
            this.fCheckResult = fCheckResult;
        }

        public String getFCateGory() {
            return fCateGory;
        }

        public void setFCateGory(String fCateGory) {
            this.fCateGory = fCateGory;
        }

        public String getFItemId() {
            return fItemId;
        }

        public void setFItemId(String fItemId) {
            this.fItemId = fItemId;
        }

        public String getFRemark() {
            return fRemark;
        }

        public void setFRemark(String fRemark) {
            this.fRemark = fRemark;
        }

        public String getFItemType() {
            return fItemType;
        }

        public void setFItemType(String fItemType) {
            this.fItemType = fItemType;
        }

        public String getFItemName() {
            return fItemName;
        }

        public void setFItemName(String fItemName) {
            this.fItemName = fItemName;
        }
    }
}
