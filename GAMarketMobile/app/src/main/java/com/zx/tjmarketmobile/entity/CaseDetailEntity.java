package com.zx.tjmarketmobile.entity;

import java.util.List;

/**
 * Created by Xiangb on 2018/4/2.
 * 功能：
 */

public class CaseDetailEntity {

    /**
     * files : [{"id":"47DD52B134F84807BFED58636BB0C3AB","caseId":"38E5C9B5D6CC4B70BC2BFDAEA47C7BE4","departmentId":"09","name":"01案件来源登记表.doc","url":"/upload/tjcase/313038AA9B9B43518E6F4E0E5C8F1676.doc","type":"基本资料","updateUser":"admin","updateUserId":"f9aea146c8ea11e79fcc000c2934879e","updateDate":1516960056000},{"id":"5C89ECB150C04C1DA729C61DADF34C75","caseId":"38E5C9B5D6CC4B70BC2BFDAEA47C7BE4","departmentId":"09","name":"修改说明.doc","url":"/upload/tjcase/29135FD0562841F9B77E2AF9F26F5E53.doc","type":"基本资料","updateUser":"admin","updateUserId":"f9aea146c8ea11e79fcc000c2934879e","updateDate":1516960056000}]
     * caseUser : [{"caseId":"EE857209BD334EF1B9D37313DDB00DCD","userId":"9712FB7BBFD74431B42F86B661FA04E7","userName":"XX","phone":"15800000000","type":0},{"caseId":"EE857209BD334EF1B9D37313DDB00DCD","userId":"f9ae74aac8ea11e79fcc000c2934879e","userName":"张白","phone":"15800000000","type":1}]
     * info : {"id":"38E5C9B5D6CC4B70BC2BFDAEA47C7BE4","departmentId":"09","departmentName":"消保科","caseName":"案件名称A","caseNum":"案件编号20180126173916","foundDate":1516959556000,"typeName":"投诉、举报","typeCode":"001","provideId":null,"provideName":"李四","provideContact":"158111111","provideAddress":"天津泰达","enterpriseId":"0D4DE56979CF40BBA8844E727152C5AB","enterpriseName":"中国电子系统工程第四建设有限公司（路路达石油项目）","enterprisePerson":"万铜良","enterpriseContact":"15800000000","enterpriseAddress":"天津开发区南港工业区港北路以北、海港路以东","enterpriseCreditCode":"911300001043234377","enterpriseBizlicNum":null,"regContent":"案源登记内容--测试","regDate":1516959556000,"regUser":"张三","sysRegUser":null,"sysRegUserId":null,"completedDate":null,"endDate":null,"updateUserId":"f9aea146c8ea11e79fcc000c2934879e","updateUser":"admin","updateDate":1516959556000,"disOpinion":null,"disUser":null,"disDate":null,"sysDisUser":null,"sysDisUserId":null,"status":"00","longitude":117.589769,"latitude":38.746622}
     */

    private CaseInfoEntity info;
    private List<FileInfoEntity> files;
    private List<CaseUserBean> caseUser;

    public CaseInfoEntity getInfo() {
        return info;
    }

    public void setInfo(CaseInfoEntity info) {
        this.info = info;
    }

    public List<FileInfoEntity> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfoEntity> files) {
        this.files = files;
    }

    public List<CaseUserBean> getCaseUser() {
        return caseUser;
    }

    public void setCaseUser(List<CaseUserBean> caseUser) {
        this.caseUser = caseUser;
    }

    public static class CaseUserBean {
        /**
         * caseId : EE857209BD334EF1B9D37313DDB00DCD
         * userId : 9712FB7BBFD74431B42F86B661FA04E7
         * userName : XX
         * phone : 15800000000
         * type : 0
         */

        private String caseId;
        private String userId;
        private String userName;
        private String phone;
        private int type;

        public String getCaseId() {
            return caseId;
        }

        public void setCaseId(String caseId) {
            this.caseId = caseId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
