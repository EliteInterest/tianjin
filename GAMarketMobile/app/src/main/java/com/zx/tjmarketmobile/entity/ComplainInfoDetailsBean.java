package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

/**
 * ================================================
 * 作    者：FANGYI <87649669@qq.com>
 * 版    本：1.0.0
 * 日    期：2018/4/2
 * 说    明：
 * ================================================
 */
public class ComplainInfoDetailsBean implements Serializable {

    /**
     * baseInfo : {"fGuid":"e423a67202e045e09e9f8f6b558f9a84","fRegId":"20151297","fRegTime":1435284393000,"fRegName":"陆建军","fRegOrg":"消保科","fReceiveType":null,"fSource":"12331","fName":"王先生","fGender":"男","fJob":null,"fRegPhone":"15023655736","fNation":"汉族","fLocalConsumer":"是","fNationality":"中国","fTelephone":null,"fCellphone":"15023655736","fFax":null,"fEmail":null,"fContactInfo":null,"fAge":null,"fDisabled":null,"fProviderType":null,"fLicType":"身份证","fLicNum":null,"fWorkOrg":null,"fAddress":null,"fZipcode":null,"fEntityName":"华荣货运市场生活服务区鲜鱼庄","fEntityPhone":"0","fEntityIndustry":"餐饮服务","fEntityAddress":"华荣货运市场生活服务区","fProductName":null,"fBrandName":null,"fProductType":null,"fKeyword":null,"fConsumeType":"服务","fNature":"质量","fUrgency":"一般事件","fSignificant":"否","fSpotNeed":"否","fMoney":"0.0000000","fCaseValue":null,"fEconomyLost":null,"fEventAddress":"华荣货运市场生活服务区","fEventTime":null,"fAffectedPeople":null,"fFollow":"否","fRepeatedly":"否","fGroup":"否","fResponse":"否","fSecrecy":"否","fOpenWeb":"否","fType":"投诉","fLongitude":106.527254,"fLatitude":29.633288,"fPushed":0,"fEntityGuid":null,"fInputUser":"1","fInputTime":1435285352000,"fCount":null,"fLimitTime":1440468393000,"fConsumeType1":"餐饮和住宿服务","fContactBool":"是","fContactAvenue":"电话","fAutocomId":null,"fFilename":null,"fFilepath":null,"fSegment":"流通","fRecoupedeconomyLost":"0","fEntityType":null,"fCompanyType":null,"fMarketName":null,"fGoodsMark":null,"fProducerGrid":null,"fProducer":null,"fParts":null,"fSaleGrid":null,"fSale":null,"fMediationEnddate":null,"fInfringementType":null,"fObligationsFails":null,"fAdjustWay":null,"fFraudLogo":null,"fDisputeAmount":null,"fSpiritAmount":null,"fDoubleCompensation":null,"fMediationId":null,"fAcceptType":null,"fEmergencyState":null,"fHandleDepartment":null,"fUnitType":null,"fAgent":null,"fFeedbackRegistrar":null,"fCaseClues":null,"fConsumeType2":null,"fConsumeType3":null,"fConsumeType4":null,"fNature1":null,"fNature2":null,"fAgentPhone":null,"fTNum":null,"fDelayFlag":"0","fRefundAmount":null,"fConfiscateAmount":null,"fFineAmount":null,"fRefundPeoplenum":null,"fCaseRegister":null,"fCaseType":null,"fCenepunish":null,"fCaseId":null,"fPrograme":null,"fCaseFillingDate":null,"fCaseCloseDate":null,"fCaseGrid":null,"fCaseAmount":null,"fCaseCharacter":null,"fLostMoney":null,"fGainMoney":null,"fIllegalType":null,"fFineMoney":null,"fPunishType":null,"fFake":null,"fForceMeasure":null,"fAzh":null,"fAcrossType":null,"fAcrossAmount":null,"fCasePlace":null,"fHurtType":null,"fHurtNumber":null,"fChangeNumber":null,"fLawerNumber":null,"fVehicleNumber":null,"fMarketNumber":null,"fSaleNumber":null,"fViolence":null,"fInjured":null,"fDead":null,"fCustomType":null,"fReduce":null,"fNone":null,"fCosumeConfirm":null,"fExcuteType":null,"fExcuteDate":null,"fStopDate":null,"fStatus":20,"fNextDeadline":null,"fDisposeUser":null,"fStation":null,"fGrid":null,"fProof":"无","fBriefInfo":null,"fContent":"投诉举报人王先生举报人和华荣市场生活服务区鲜鱼庄卫生条件极差，鱼不清洗就下锅，望调查处理。","fAdvice":null,"fMediationResult":null,"fFeedbackContent":null,"fGist":null,"fPunishResult":null,"fPunishGist":null,"fStopReason":null,"fCancelCondition":null}
     * statusInfo : [{"fGuid":"e423a67202e045e09e9f8f6b558f9a84","fStatus":10,"fDispose":"分流","fDisposeDate":1506394657000,"fUserId":"1","fRealName":null,"fDisposeRemark":null}]
     */

    private BaseInfoBean baseInfo;
    private List<StatusInfoBean> statusInfo;

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<StatusInfoBean> getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(List<StatusInfoBean> statusInfo) {
        this.statusInfo = statusInfo;
    }

    public static class BaseInfoBean implements Serializable {
        /**
         * fGuid : e423a67202e045e09e9f8f6b558f9a84
         * fRegId : 20151297
         * fRegTime : 1435284393000
         * fRegName : 陆建军
         * fRegOrg : 消保科
         * fReceiveType : null
         * fSource : 12331
         * fName : 王先生
         * fGender : 男
         * fJob : null
         * fRegPhone : 15023655736
         * fNation : 汉族
         * fLocalConsumer : 是
         * fNationality : 中国
         * fTelephone : null
         * fCellphone : 15023655736
         * fFax : null
         * fEmail : null
         * fContactInfo : null
         * fAge : null
         * fDisabled : null
         * fProviderType : null
         * fLicType : 身份证
         * fLicNum : null
         * fWorkOrg : null
         * fAddress : null
         * fZipcode : null
         * fEntityName : 华荣货运市场生活服务区鲜鱼庄
         * fEntityPhone : 0
         * fEntityIndustry : 餐饮服务
         * fEntityAddress : 华荣货运市场生活服务区
         * fProductName : null
         * fBrandName : null
         * fProductType : null
         * fKeyword : null
         * fConsumeType : 服务
         * fNature : 质量
         * fUrgency : 一般事件
         * fSignificant : 否
         * fSpotNeed : 否
         * fMoney : 0.0000000
         * fCaseValue : null
         * fEconomyLost : null
         * fEventAddress : 华荣货运市场生活服务区
         * fEventTime : null
         * fAffectedPeople : null
         * fFollow : 否
         * fRepeatedly : 否
         * fGroup : 否
         * fResponse : 否
         * fSecrecy : 否
         * fOpenWeb : 否
         * fType : 投诉
         * fLongitude : 106.527254
         * fLatitude : 29.633288
         * fPushed : 0
         * fEntityGuid : null
         * fInputUser : 1
         * fInputTime : 1435285352000
         * fCount : null
         * fLimitTime : 1440468393000
         * fConsumeType1 : 餐饮和住宿服务
         * fContactBool : 是
         * fContactAvenue : 电话
         * fAutocomId : null
         * fFilename : null
         * fFilepath : null
         * fSegment : 流通
         * fRecoupedeconomyLost : 0
         * fEntityType : null
         * fCompanyType : null
         * fMarketName : null
         * fGoodsMark : null
         * fProducerGrid : null
         * fProducer : null
         * fParts : null
         * fSaleGrid : null
         * fSale : null
         * fMediationEnddate : null
         * fInfringementType : null
         * fObligationsFails : null
         * fAdjustWay : null
         * fFraudLogo : null
         * fDisputeAmount : null
         * fSpiritAmount : null
         * fDoubleCompensation : null
         * fMediationId : null
         * fAcceptType : null
         * fEmergencyState : null
         * fHandleDepartment : null
         * fUnitType : null
         * fAgent : null
         * fFeedbackRegistrar : null
         * fCaseClues : null
         * fConsumeType2 : null
         * fConsumeType3 : null
         * fConsumeType4 : null
         * fNature1 : null
         * fNature2 : null
         * fAgentPhone : null
         * fTNum : null
         * fDelayFlag : 0
         * fRefundAmount : null
         * fConfiscateAmount : null
         * fFineAmount : null
         * fRefundPeoplenum : null
         * fCaseRegister : null
         * fCaseType : null
         * fCenepunish : null
         * fCaseId : null
         * fPrograme : null
         * fCaseFillingDate : null
         * fCaseCloseDate : null
         * fCaseGrid : null
         * fCaseAmount : null
         * fCaseCharacter : null
         * fLostMoney : null
         * fGainMoney : null
         * fIllegalType : null
         * fFineMoney : null
         * fPunishType : null
         * fFake : null
         * fForceMeasure : null
         * fAzh : null
         * fAcrossType : null
         * fAcrossAmount : null
         * fCasePlace : null
         * fHurtType : null
         * fHurtNumber : null
         * fChangeNumber : null
         * fLawerNumber : null
         * fVehicleNumber : null
         * fMarketNumber : null
         * fSaleNumber : null
         * fViolence : null
         * fInjured : null
         * fDead : null
         * fCustomType : null
         * fReduce : null
         * fNone : null
         * fCosumeConfirm : null
         * fExcuteType : null
         * fExcuteDate : null
         * fStopDate : null
         * fStatus : 20
         * fNextDeadline : null
         * fDisposeUser : null
         * fStation : null
         * fGrid : null
         * fProof : 无
         * fBriefInfo : null
         * fContent : 投诉举报人王先生举报人和华荣市场生活服务区鲜鱼庄卫生条件极差，鱼不清洗就下锅，望调查处理。
         * fAdvice : null
         * fMediationResult : null
         * fFeedbackContent : null
         * fGist : null
         * fPunishResult : null
         * fPunishGist : null
         * fStopReason : null
         * fCancelCondition : null
         * fShuntRole
         */

        private String fGuid;
        private String fRegId;
        private long fRegTime;
        private String fRegName;
        private String fRegOrg;
        private String fReceiveType;
        private String fSource;
        private String fName;
        private String fGender;
        private String fJob;
        private String fRegPhone;
        private String fNation;
        private String fLocalConsumer;
        private String fNationality;
        private String fTelephone;
        private String fCellphone;
        private String fFax;
        private String fEmail;
        private String fContactInfo;
        private String fAge;
        private String fDisabled;
        private String fProviderType;
        private String fLicType;
        private String fLicNum;
        private String fWorkOrg;
        private String fAddress;
        private String fZipcode;
        private String fEntityName;
        private String fEntityPhone;
        private String fEntityIndustry;
        private String fEntityAddress;
        private String fProductName;
        private String fBrandName;
        private String fProductType;
        private String fDepartMent;
        private String fKeyword;
        private String fConsumeType;
        private String fNature;
        private String fUrgency;
        private String fSignificant;
        private String fSpotNeed;
        private String fMoney;
        private String fCaseValue;
        private String fEconomyLost;
        private String fEventAddress;
        private String fEventTime;
        private String fAffectedPeople;
        private String fFollow;
        private String fRepeatedly;
        private String fGroup;
        private String fResponse;
        private String fSecrecy;
        private String fOpenWeb;
        private String fType;
        private double fLongitude;
        private double fLatitude;
        private int fPushed;
        private String fEntityGuid;
        private String fInputUser;
        private String fInputTime;
        private String fCount;
        private String fLimitTime;
        private String fConsumeType1;
        private String fContactBool;
        private String fContactAvenue;
        private String fAutocomId;
        private String fFilename;
        private String fFilepath;
        private String fSegment;
        private String fRecoupedeconomyLost;
        private String fEntityType;
        private String fCompanyType;
        private String fMarketName;
        private String fGoodsMark;
        private String fProducerGrid;
        private String fProducer;
        private String fParts;
        private String fSaleGrid;
        private String fSale;
        private String fMediationEnddate;
        private String fInfringementType;
        private String fObligationsFails;
        private String fAdjustWay;
        private String fFraudLogo;
        private String fDisputeAmount;
        private String fSpiritAmount;
        private String fDoubleCompensation;
        private String fMediationId;
        private String fAcceptType;
        private String fEmergencyState;
        private String fHandleDepartment;
        private String fUnitType;
        private String fAgent;
        private String fFeedbackRegistrar;
        private String fCaseClues;
        private String fConsumeType2;
        private String fConsumeType3;
        private String fConsumeType4;
        private String fNature1;
        private String fNature2;
        private String fAgentPhone;
        private String fTNum;
        private String fDelayFlag;
        private String fRefundAmount;
        private String fConfiscateAmount;
        private String fFineAmount;
        private String fRefundPeoplenum;
        private String fCaseRegister;
        private String fCaseType;
        private String fCenepunish;
        private String fCaseId;
        private String fPrograme;
        private String fCaseFillingDate;
        private String fCaseCloseDate;
        private String fCaseGrid;
        private String fCaseAmount;
        private String fCaseCharacter;
        private String fLostMoney;
        private String fGainMoney;
        private String fIllegalType;
        private String fFineMoney;
        private String fPunishType;
        private String fFake;
        private String fForceMeasure;
        private String fAzh;
        private String fAcrossType;
        private String fAcrossAmount;
        private String fCasePlace;
        private String fHurtType;
        private String fHurtNumber;
        private String fChangeNumber;
        private String fLawerNumber;
        private String fVehicleNumber;
        private String fMarketNumber;
        private String fSaleNumber;
        private String fViolence;
        private String fInjured;
        private String fDead;
        private String fCustomType;
        private String fReduce;
        private String fNone;
        private String fCosumeConfirm;
        private String fExcuteType;
        private String fExcuteDate;
        private String fStopDate;
        private int fStatus;
        private String fNextDeadline;
        private String fDisposeUser;
        private String fStation;
        private String fGrid;
        private String fProof;
        private String fBriefInfo;
        private String fContent;
        private String fAdvice;
        private String fMediationResult;
        private String fFeedbackContent;
        private String fGist;
        private String fPunishResult;
        private String fPunishGist;
        private String fStopReason;
        private String fCancelCondition;
        private String fBusinessSource;
        private String fDisposeUnit;
        private String fReplyContent;
        private String fEntityPerson;
        private String fSalesWay;
        private String fInfoSource;
        private String fReviewResult;
        private String fShuntRole;

        public String getfShuntRole() {
            return fShuntRole;
        }

        public void setfShuntRole(String fShuntRole) {
            this.fShuntRole = fShuntRole;
        }

        public String getfReviewResult() {
            return fReviewResult;
        }

        public void setfReviewResult(String fReviewResult) {
            this.fReviewResult = fReviewResult;
        }

        public String getfInfoSource() {
            return fInfoSource;
        }

        public void setfInfoSource(String fInfoSource) {
            this.fInfoSource = fInfoSource;
        }

        public String getfSalesWay() {
            return fSalesWay;
        }

        public void setfSalesWay(String fSalesWay) {
            this.fSalesWay = fSalesWay;
        }

        public String getfEntityPerson() {
            return fEntityPerson;
        }

        public void setfEntityPerson(String fEntityPerson) {
            this.fEntityPerson = fEntityPerson;
        }

        public String getfReplyContent() {
            return fReplyContent;
        }

        public void setfReplyContent(String fReplyContent) {
            this.fReplyContent = fReplyContent;
        }

        public String getfDisposeUnit() {
            return fDisposeUnit;
        }

        public void setfDisposeUnit(String fDisposeUnit) {
            this.fDisposeUnit = fDisposeUnit;
        }

        public String getfBusinessSource() {
            return fBusinessSource;
        }

        public void setfBusinessSource(String fBusinessSource) {
            this.fBusinessSource = fBusinessSource;
        }

        public String getfDepartMent() {
            return fDepartMent;
        }

        public void setfDepartMent(String fDepartMent) {
            this.fDepartMent = fDepartMent;
        }

        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public String getFRegId() {
            return fRegId;
        }

        public void setFRegId(String fRegId) {
            this.fRegId = fRegId;
        }

        public long getFRegTime() {
            return fRegTime;
        }

        public void setFRegTime(long fRegTime) {
            this.fRegTime = fRegTime;
        }

        public String getFRegName() {
            return fRegName;
        }

        public void setFRegName(String fRegName) {
            this.fRegName = fRegName;
        }

        public String getFRegOrg() {
            return fRegOrg;
        }

        public void setFRegOrg(String fRegOrg) {
            this.fRegOrg = fRegOrg;
        }

        public String getFReceiveType() {
            return fReceiveType;
        }

        public void setFReceiveType(String fReceiveType) {
            this.fReceiveType = fReceiveType;
        }

        public String getFSource() {
            return fSource;
        }

        public void setFSource(String fSource) {
            this.fSource = fSource;
        }

        public String getFName() {
            return fName;
        }

        public void setFName(String fName) {
            this.fName = fName;
        }

        public String getFGender() {
            return fGender;
        }

        public void setFGender(String fGender) {
            this.fGender = fGender;
        }

        public String getFJob() {
            return fJob;
        }

        public void setFJob(String fJob) {
            this.fJob = fJob;
        }

        public String getFRegPhone() {
            return fRegPhone;
        }

        public void setFRegPhone(String fRegPhone) {
            this.fRegPhone = fRegPhone;
        }

        public String getFNation() {
            return fNation;
        }

        public void setFNation(String fNation) {
            this.fNation = fNation;
        }

        public String getFLocalConsumer() {
            return fLocalConsumer;
        }

        public void setFLocalConsumer(String fLocalConsumer) {
            this.fLocalConsumer = fLocalConsumer;
        }

        public String getFNationality() {
            return fNationality;
        }

        public void setFNationality(String fNationality) {
            this.fNationality = fNationality;
        }

        public String getFTelephone() {
            return fTelephone;
        }

        public void setFTelephone(String fTelephone) {
            this.fTelephone = fTelephone;
        }

        public String getFCellphone() {
            return fCellphone;
        }

        public void setFCellphone(String fCellphone) {
            this.fCellphone = fCellphone;
        }

        public String getFFax() {
            return fFax;
        }

        public void setFFax(String fFax) {
            this.fFax = fFax;
        }

        public String getFEmail() {
            return fEmail;
        }

        public void setFEmail(String fEmail) {
            this.fEmail = fEmail;
        }

        public String getFContactInfo() {
            return fContactInfo;
        }

        public void setFContactInfo(String fContactInfo) {
            this.fContactInfo = fContactInfo;
        }

        public String getFAge() {
            return fAge;
        }

        public void setFAge(String fAge) {
            this.fAge = fAge;
        }

        public String getFDisabled() {
            return fDisabled;
        }

        public void setFDisabled(String fDisabled) {
            this.fDisabled = fDisabled;
        }

        public String getFProviderType() {
            return fProviderType;
        }

        public void setFProviderType(String fProviderType) {
            this.fProviderType = fProviderType;
        }

        public String getFLicType() {
            return fLicType;
        }

        public void setFLicType(String fLicType) {
            this.fLicType = fLicType;
        }

        public String getFLicNum() {
            return fLicNum;
        }

        public void setFLicNum(String fLicNum) {
            this.fLicNum = fLicNum;
        }

        public String getFWorkOrg() {
            return fWorkOrg;
        }

        public void setFWorkOrg(String fWorkOrg) {
            this.fWorkOrg = fWorkOrg;
        }

        public String getFAddress() {
            return fAddress;
        }

        public void setFAddress(String fAddress) {
            this.fAddress = fAddress;
        }

        public String getFZipcode() {
            return fZipcode;
        }

        public void setFZipcode(String fZipcode) {
            this.fZipcode = fZipcode;
        }

        public String getFEntityName() {
            return fEntityName;
        }

        public void setFEntityName(String fEntityName) {
            this.fEntityName = fEntityName;
        }

        public String getFEntityPhone() {
            return fEntityPhone;
        }

        public void setFEntityPhone(String fEntityPhone) {
            this.fEntityPhone = fEntityPhone;
        }

        public String getFEntityIndustry() {
            return fEntityIndustry;
        }

        public void setFEntityIndustry(String fEntityIndustry) {
            this.fEntityIndustry = fEntityIndustry;
        }

        public String getFEntityAddress() {
            return fEntityAddress;
        }

        public void setFEntityAddress(String fEntityAddress) {
            this.fEntityAddress = fEntityAddress;
        }

        public String getFProductName() {
            return fProductName;
        }

        public void setFProductName(String fProductName) {
            this.fProductName = fProductName;
        }

        public String getFBrandName() {
            return fBrandName;
        }

        public void setFBrandName(String fBrandName) {
            this.fBrandName = fBrandName;
        }

        public String getFProductType() {
            return fProductType;
        }

        public void setFProductType(String fProductType) {
            this.fProductType = fProductType;
        }

        public String getFKeyword() {
            return fKeyword;
        }

        public void setFKeyword(String fKeyword) {
            this.fKeyword = fKeyword;
        }

        public String getFConsumeType() {
            return fConsumeType;
        }

        public void setFConsumeType(String fConsumeType) {
            this.fConsumeType = fConsumeType;
        }

        public String getFNature() {
            return fNature;
        }

        public void setFNature(String fNature) {
            this.fNature = fNature;
        }

        public String getFUrgency() {
            return fUrgency;
        }

        public void setFUrgency(String fUrgency) {
            this.fUrgency = fUrgency;
        }

        public String getFSignificant() {
            return fSignificant;
        }

        public void setFSignificant(String fSignificant) {
            this.fSignificant = fSignificant;
        }

        public String getFSpotNeed() {
            return fSpotNeed;
        }

        public void setFSpotNeed(String fSpotNeed) {
            this.fSpotNeed = fSpotNeed;
        }

        public String getFMoney() {
            return fMoney;
        }

        public void setFMoney(String fMoney) {
            this.fMoney = fMoney;
        }

        public String getFCaseValue() {
            return fCaseValue;
        }

        public void setFCaseValue(String fCaseValue) {
            this.fCaseValue = fCaseValue;
        }

        public String getFEconomyLost() {
            return fEconomyLost;
        }

        public void setFEconomyLost(String fEconomyLost) {
            this.fEconomyLost = fEconomyLost;
        }

        public String getFEventAddress() {
            return fEventAddress;
        }

        public void setFEventAddress(String fEventAddress) {
            this.fEventAddress = fEventAddress;
        }

        public String getFEventTime() {
            return fEventTime;
        }

        public void setFEventTime(String fEventTime) {
            this.fEventTime = fEventTime;
        }

        public String getFAffectedPeople() {
            return fAffectedPeople;
        }

        public void setFAffectedPeople(String fAffectedPeople) {
            this.fAffectedPeople = fAffectedPeople;
        }

        public String getFFollow() {
            return fFollow;
        }

        public void setFFollow(String fFollow) {
            this.fFollow = fFollow;
        }

        public String getFRepeatedly() {
            return fRepeatedly;
        }

        public void setFRepeatedly(String fRepeatedly) {
            this.fRepeatedly = fRepeatedly;
        }

        public String getFGroup() {
            return fGroup;
        }

        public void setFGroup(String fGroup) {
            this.fGroup = fGroup;
        }

        public String getFResponse() {
            return fResponse;
        }

        public void setFResponse(String fResponse) {
            this.fResponse = fResponse;
        }

        public String getFSecrecy() {
            return fSecrecy;
        }

        public void setFSecrecy(String fSecrecy) {
            this.fSecrecy = fSecrecy;
        }

        public String getFOpenWeb() {
            return fOpenWeb;
        }

        public void setFOpenWeb(String fOpenWeb) {
            this.fOpenWeb = fOpenWeb;
        }

        public String getFType() {
            return fType;
        }

        public void setFType(String fType) {
            this.fType = fType;
        }

        public double getFLongitude() {
            return fLongitude;
        }

        public void setFLongitude(double fLongitude) {
            this.fLongitude = fLongitude;
        }

        public double getFLatitude() {
            return fLatitude;
        }

        public void setFLatitude(double fLatitude) {
            this.fLatitude = fLatitude;
        }

        public int getFPushed() {
            return fPushed;
        }

        public void setFPushed(int fPushed) {
            this.fPushed = fPushed;
        }

        public String getFEntityGuid() {
            return fEntityGuid;
        }

        public void setFEntityGuid(String fEntityGuid) {
            this.fEntityGuid = fEntityGuid;
        }

        public String getFInputUser() {
            return fInputUser;
        }

        public void setFInputUser(String fInputUser) {
            this.fInputUser = fInputUser;
        }

        public String getFInputTime() {
            return fInputTime;
        }

        public void setFInputTime(String fInputTime) {
            this.fInputTime = fInputTime;
        }

        public String getFCount() {
            return fCount;
        }

        public void setFCount(String fCount) {
            this.fCount = fCount;
        }

        public String getFLimitTime() {
            return fLimitTime;
        }

        public void setFLimitTime(String fLimitTime) {
            this.fLimitTime = fLimitTime;
        }

        public String getFConsumeType1() {
            return fConsumeType1;
        }

        public void setFConsumeType1(String fConsumeType1) {
            this.fConsumeType1 = fConsumeType1;
        }

        public String getFContactBool() {
            return fContactBool;
        }

        public void setFContactBool(String fContactBool) {
            this.fContactBool = fContactBool;
        }

        public String getFContactAvenue() {
            return fContactAvenue;
        }

        public void setFContactAvenue(String fContactAvenue) {
            this.fContactAvenue = fContactAvenue;
        }

        public String getFAutocomId() {
            return fAutocomId;
        }

        public void setFAutocomId(String fAutocomId) {
            this.fAutocomId = fAutocomId;
        }

        public String getFFilename() {
            return fFilename;
        }

        public void setFFilename(String fFilename) {
            this.fFilename = fFilename;
        }

        public String getFFilepath() {
            return fFilepath;
        }

        public void setFFilepath(String fFilepath) {
            this.fFilepath = fFilepath;
        }

        public String getFSegment() {
            return fSegment;
        }

        public void setFSegment(String fSegment) {
            this.fSegment = fSegment;
        }

        public String getFRecoupedeconomyLost() {
            return fRecoupedeconomyLost;
        }

        public void setFRecoupedeconomyLost(String fRecoupedeconomyLost) {
            this.fRecoupedeconomyLost = fRecoupedeconomyLost;
        }

        public String getFEntityType() {
            return fEntityType;
        }

        public void setFEntityType(String fEntityType) {
            this.fEntityType = fEntityType;
        }

        public String getFCompanyType() {
            return fCompanyType;
        }

        public void setFCompanyType(String fCompanyType) {
            this.fCompanyType = fCompanyType;
        }

        public String getFMarketName() {
            return fMarketName;
        }

        public void setFMarketName(String fMarketName) {
            this.fMarketName = fMarketName;
        }

        public String getFGoodsMark() {
            return fGoodsMark;
        }

        public void setFGoodsMark(String fGoodsMark) {
            this.fGoodsMark = fGoodsMark;
        }

        public String getFProducerGrid() {
            return fProducerGrid;
        }

        public void setFProducerGrid(String fProducerGrid) {
            this.fProducerGrid = fProducerGrid;
        }

        public String getFProducer() {
            return fProducer;
        }

        public void setFProducer(String fProducer) {
            this.fProducer = fProducer;
        }

        public String getFParts() {
            return fParts;
        }

        public void setFParts(String fParts) {
            this.fParts = fParts;
        }

        public String getFSaleGrid() {
            return fSaleGrid;
        }

        public void setFSaleGrid(String fSaleGrid) {
            this.fSaleGrid = fSaleGrid;
        }

        public String getFSale() {
            return fSale;
        }

        public void setFSale(String fSale) {
            this.fSale = fSale;
        }

        public String getFMediationEnddate() {
            return fMediationEnddate;
        }

        public void setFMediationEnddate(String fMediationEnddate) {
            this.fMediationEnddate = fMediationEnddate;
        }

        public String getFInfringementType() {
            return fInfringementType;
        }

        public void setFInfringementType(String fInfringementType) {
            this.fInfringementType = fInfringementType;
        }

        public String getFObligationsFails() {
            return fObligationsFails;
        }

        public void setFObligationsFails(String fObligationsFails) {
            this.fObligationsFails = fObligationsFails;
        }

        public String getFAdjustWay() {
            return fAdjustWay;
        }

        public void setFAdjustWay(String fAdjustWay) {
            this.fAdjustWay = fAdjustWay;
        }

        public String getFFraudLogo() {
            return fFraudLogo;
        }

        public void setFFraudLogo(String fFraudLogo) {
            this.fFraudLogo = fFraudLogo;
        }

        public String getFDisputeAmount() {
            return fDisputeAmount;
        }

        public void setFDisputeAmount(String fDisputeAmount) {
            this.fDisputeAmount = fDisputeAmount;
        }

        public String getFSpiritAmount() {
            return fSpiritAmount;
        }

        public void setFSpiritAmount(String fSpiritAmount) {
            this.fSpiritAmount = fSpiritAmount;
        }

        public String getFDoubleCompensation() {
            return fDoubleCompensation;
        }

        public void setFDoubleCompensation(String fDoubleCompensation) {
            this.fDoubleCompensation = fDoubleCompensation;
        }

        public String getFMediationId() {
            return fMediationId;
        }

        public void setFMediationId(String fMediationId) {
            this.fMediationId = fMediationId;
        }

        public String getFAcceptType() {
            return fAcceptType;
        }

        public void setFAcceptType(String fAcceptType) {
            this.fAcceptType = fAcceptType;
        }

        public String getFEmergencyState() {
            return fEmergencyState;
        }

        public void setFEmergencyState(String fEmergencyState) {
            this.fEmergencyState = fEmergencyState;
        }

        public String getFHandleDepartment() {
            return fHandleDepartment;
        }

        public void setFHandleDepartment(String fHandleDepartment) {
            this.fHandleDepartment = fHandleDepartment;
        }

        public String getFUnitType() {
            return fUnitType;
        }

        public void setFUnitType(String fUnitType) {
            this.fUnitType = fUnitType;
        }

        public String getFAgent() {
            return fAgent;
        }

        public void setFAgent(String fAgent) {
            this.fAgent = fAgent;
        }

        public String getFFeedbackRegistrar() {
            return fFeedbackRegistrar;
        }

        public void setFFeedbackRegistrar(String fFeedbackRegistrar) {
            this.fFeedbackRegistrar = fFeedbackRegistrar;
        }

        public String getFCaseClues() {
            return fCaseClues;
        }

        public void setFCaseClues(String fCaseClues) {
            this.fCaseClues = fCaseClues;
        }

        public String getFConsumeType2() {
            return fConsumeType2;
        }

        public void setFConsumeType2(String fConsumeType2) {
            this.fConsumeType2 = fConsumeType2;
        }

        public String getFConsumeType3() {
            return fConsumeType3;
        }

        public void setFConsumeType3(String fConsumeType3) {
            this.fConsumeType3 = fConsumeType3;
        }

        public String getFConsumeType4() {
            return fConsumeType4;
        }

        public void setFConsumeType4(String fConsumeType4) {
            this.fConsumeType4 = fConsumeType4;
        }

        public String getFNature1() {
            return fNature1;
        }

        public void setFNature1(String fNature1) {
            this.fNature1 = fNature1;
        }

        public String getFNature2() {
            return fNature2;
        }

        public void setFNature2(String fNature2) {
            this.fNature2 = fNature2;
        }

        public String getFAgentPhone() {
            return fAgentPhone;
        }

        public void setFAgentPhone(String fAgentPhone) {
            this.fAgentPhone = fAgentPhone;
        }

        public String getFTNum() {
            return fTNum;
        }

        public void setFTNum(String fTNum) {
            this.fTNum = fTNum;
        }

        public String getFDelayFlag() {
            return fDelayFlag;
        }

        public void setFDelayFlag(String fDelayFlag) {
            this.fDelayFlag = fDelayFlag;
        }

        public String getFRefundAmount() {
            return fRefundAmount;
        }

        public void setFRefundAmount(String fRefundAmount) {
            this.fRefundAmount = fRefundAmount;
        }

        public String getFConfiscateAmount() {
            return fConfiscateAmount;
        }

        public void setFConfiscateAmount(String fConfiscateAmount) {
            this.fConfiscateAmount = fConfiscateAmount;
        }

        public String getFFineAmount() {
            return fFineAmount;
        }

        public void setFFineAmount(String fFineAmount) {
            this.fFineAmount = fFineAmount;
        }

        public String getFRefundPeoplenum() {
            return fRefundPeoplenum;
        }

        public void setFRefundPeoplenum(String fRefundPeoplenum) {
            this.fRefundPeoplenum = fRefundPeoplenum;
        }

        public String getFCaseRegister() {
            return fCaseRegister;
        }

        public void setFCaseRegister(String fCaseRegister) {
            this.fCaseRegister = fCaseRegister;
        }

        public String getFCaseType() {
            return fCaseType;
        }

        public void setFCaseType(String fCaseType) {
            this.fCaseType = fCaseType;
        }

        public String getFCenepunish() {
            return fCenepunish;
        }

        public void setFCenepunish(String fCenepunish) {
            this.fCenepunish = fCenepunish;
        }

        public String getFCaseId() {
            return fCaseId;
        }

        public void setFCaseId(String fCaseId) {
            this.fCaseId = fCaseId;
        }

        public String getFPrograme() {
            return fPrograme;
        }

        public void setFPrograme(String fPrograme) {
            this.fPrograme = fPrograme;
        }

        public String getFCaseFillingDate() {
            return fCaseFillingDate;
        }

        public void setFCaseFillingDate(String fCaseFillingDate) {
            this.fCaseFillingDate = fCaseFillingDate;
        }

        public String getFCaseCloseDate() {
            return fCaseCloseDate;
        }

        public void setFCaseCloseDate(String fCaseCloseDate) {
            this.fCaseCloseDate = fCaseCloseDate;
        }

        public String getFCaseGrid() {
            return fCaseGrid;
        }

        public void setFCaseGrid(String fCaseGrid) {
            this.fCaseGrid = fCaseGrid;
        }

        public String getFCaseAmount() {
            return fCaseAmount;
        }

        public void setFCaseAmount(String fCaseAmount) {
            this.fCaseAmount = fCaseAmount;
        }

        public String getFCaseCharacter() {
            return fCaseCharacter;
        }

        public void setFCaseCharacter(String fCaseCharacter) {
            this.fCaseCharacter = fCaseCharacter;
        }

        public String getFLostMoney() {
            return fLostMoney;
        }

        public void setFLostMoney(String fLostMoney) {
            this.fLostMoney = fLostMoney;
        }

        public String getFGainMoney() {
            return fGainMoney;
        }

        public void setFGainMoney(String fGainMoney) {
            this.fGainMoney = fGainMoney;
        }

        public String getFIllegalType() {
            return fIllegalType;
        }

        public void setFIllegalType(String fIllegalType) {
            this.fIllegalType = fIllegalType;
        }

        public String getFFineMoney() {
            return fFineMoney;
        }

        public void setFFineMoney(String fFineMoney) {
            this.fFineMoney = fFineMoney;
        }

        public String getFPunishType() {
            return fPunishType;
        }

        public void setFPunishType(String fPunishType) {
            this.fPunishType = fPunishType;
        }

        public String getFFake() {
            return fFake;
        }

        public void setFFake(String fFake) {
            this.fFake = fFake;
        }

        public String getFForceMeasure() {
            return fForceMeasure;
        }

        public void setFForceMeasure(String fForceMeasure) {
            this.fForceMeasure = fForceMeasure;
        }

        public String getFAzh() {
            return fAzh;
        }

        public void setFAzh(String fAzh) {
            this.fAzh = fAzh;
        }

        public String getFAcrossType() {
            return fAcrossType;
        }

        public void setFAcrossType(String fAcrossType) {
            this.fAcrossType = fAcrossType;
        }

        public String getFAcrossAmount() {
            return fAcrossAmount;
        }

        public void setFAcrossAmount(String fAcrossAmount) {
            this.fAcrossAmount = fAcrossAmount;
        }

        public String getFCasePlace() {
            return fCasePlace;
        }

        public void setFCasePlace(String fCasePlace) {
            this.fCasePlace = fCasePlace;
        }

        public String getFHurtType() {
            return fHurtType;
        }

        public void setFHurtType(String fHurtType) {
            this.fHurtType = fHurtType;
        }

        public String getFHurtNumber() {
            return fHurtNumber;
        }

        public void setFHurtNumber(String fHurtNumber) {
            this.fHurtNumber = fHurtNumber;
        }

        public String getFChangeNumber() {
            return fChangeNumber;
        }

        public void setFChangeNumber(String fChangeNumber) {
            this.fChangeNumber = fChangeNumber;
        }

        public String getFLawerNumber() {
            return fLawerNumber;
        }

        public void setFLawerNumber(String fLawerNumber) {
            this.fLawerNumber = fLawerNumber;
        }

        public String getFVehicleNumber() {
            return fVehicleNumber;
        }

        public void setFVehicleNumber(String fVehicleNumber) {
            this.fVehicleNumber = fVehicleNumber;
        }

        public String getFMarketNumber() {
            return fMarketNumber;
        }

        public void setFMarketNumber(String fMarketNumber) {
            this.fMarketNumber = fMarketNumber;
        }

        public String getFSaleNumber() {
            return fSaleNumber;
        }

        public void setFSaleNumber(String fSaleNumber) {
            this.fSaleNumber = fSaleNumber;
        }

        public String getFViolence() {
            return fViolence;
        }

        public void setFViolence(String fViolence) {
            this.fViolence = fViolence;
        }

        public String getFInjured() {
            return fInjured;
        }

        public void setFInjured(String fInjured) {
            this.fInjured = fInjured;
        }

        public String getFDead() {
            return fDead;
        }

        public void setFDead(String fDead) {
            this.fDead = fDead;
        }

        public String getFCustomType() {
            return fCustomType;
        }

        public void setFCustomType(String fCustomType) {
            this.fCustomType = fCustomType;
        }

        public String getFReduce() {
            return fReduce;
        }

        public void setFReduce(String fReduce) {
            this.fReduce = fReduce;
        }

        public String getFNone() {
            return fNone;
        }

        public void setFNone(String fNone) {
            this.fNone = fNone;
        }

        public String getFCosumeConfirm() {
            return fCosumeConfirm;
        }

        public void setFCosumeConfirm(String fCosumeConfirm) {
            this.fCosumeConfirm = fCosumeConfirm;
        }

        public String getFExcuteType() {
            return fExcuteType;
        }

        public void setFExcuteType(String fExcuteType) {
            this.fExcuteType = fExcuteType;
        }

        public String getFExcuteDate() {
            return fExcuteDate;
        }

        public void setFExcuteDate(String fExcuteDate) {
            this.fExcuteDate = fExcuteDate;
        }

        public String getFStopDate() {
            return fStopDate;
        }

        public void setFStopDate(String fStopDate) {
            this.fStopDate = fStopDate;
        }

        public int getFStatus() {
            return fStatus;
        }

        public String getfStatusString() {
            if (fStatus == 0) {
                return "信息录入";
            } else if (fStatus == 10) {
                return "待受理";
            } else if (fStatus == 20) {
                return "待分流";
            } else if (fStatus == 30) {
                return "待指派";
            } else if (fStatus == 50) {
                return "待处置";
            } else if (fStatus == 60) {
                return "待审核";
            } else if (fStatus == 80) {
                return "待办结";
            } else if (fStatus == 90) {
                return "已办结";
            }
            return fStatus + "";
        }

        public void setFStatus(int fStatus) {
            this.fStatus = fStatus;
        }

        public String getFNextDeadline() {
            return fNextDeadline;
        }

        public void setFNextDeadline(String fNextDeadline) {
            this.fNextDeadline = fNextDeadline;
        }

        public String getFDisposeUser() {
            return fDisposeUser;
        }

        public void setFDisposeUser(String fDisposeUser) {
            this.fDisposeUser = fDisposeUser;
        }

        public String getFStation() {
            return fStation;
        }

        public void setFStation(String fStation) {
            this.fStation = fStation;
        }

        public String getFGrid() {
            return fGrid;
        }

        public void setFGrid(String fGrid) {
            this.fGrid = fGrid;
        }

        public String getFProof() {
            return fProof;
        }

        public void setFProof(String fProof) {
            this.fProof = fProof;
        }

        public String getFBriefInfo() {
            return fBriefInfo;
        }

        public void setFBriefInfo(String fBriefInfo) {
            this.fBriefInfo = fBriefInfo;
        }

        public String getFContent() {
            return fContent;
        }

        public void setFContent(String fContent) {
            this.fContent = fContent;
        }

        public String getFAdvice() {
            return fAdvice;
        }

        public void setFAdvice(String fAdvice) {
            this.fAdvice = fAdvice;
        }

        public String getFMediationResult() {
            return fMediationResult;
        }

        public void setFMediationResult(String fMediationResult) {
            this.fMediationResult = fMediationResult;
        }

        public String getFFeedbackContent() {
            return fFeedbackContent;
        }

        public void setFFeedbackContent(String fFeedbackContent) {
            this.fFeedbackContent = fFeedbackContent;
        }

        public String getFGist() {
            return fGist;
        }

        public void setFGist(String fGist) {
            this.fGist = fGist;
        }

        public String getFPunishResult() {
            return fPunishResult;
        }

        public void setFPunishResult(String fPunishResult) {
            this.fPunishResult = fPunishResult;
        }

        public String getFPunishGist() {
            return fPunishGist;
        }

        public void setFPunishGist(String fPunishGist) {
            this.fPunishGist = fPunishGist;
        }

        public String getFStopReason() {
            return fStopReason;
        }

        public void setFStopReason(String fStopReason) {
            this.fStopReason = fStopReason;
        }

        public String getFCancelCondition() {
            return fCancelCondition;
        }

        public void setFCancelCondition(String fCancelCondition) {
            this.fCancelCondition = fCancelCondition;
        }
    }

    public static class StatusInfoBean implements Serializable {
        /**
         * fGuid : e423a67202e045e09e9f8f6b558f9a84
         * fStatus : 10
         * fDispose : 分流
         * fDisposeDate : 1506394657000
         * fUserId : 1
         * fRealName : null
         * fDisposeRemark : null
         */

        private String fGuid;
        private int fStatus;
        private String fDispose;
        private long fDisposeDate;
        private String fUserId;
        private String fRealName;
        private String fDisposeRemark;

        public String getFGuid() {
            return fGuid;
        }

        public void setFGuid(String fGuid) {
            this.fGuid = fGuid;
        }

        public int getFStatus() {
            return fStatus;
        }

        public void setFStatus(int fStatus) {
            this.fStatus = fStatus;
        }

        public String getFDispose() {
            return fDispose;
        }

        public void setFDispose(String fDispose) {
            this.fDispose = fDispose;
        }

        public long getFDisposeDate() {
            return fDisposeDate;
        }

        public void setFDisposeDate(long fDisposeDate) {
            this.fDisposeDate = fDisposeDate;
        }

        public String getFUserId() {
            return fUserId;
        }

        public void setFUserId(String fUserId) {
            this.fUserId = fUserId;
        }

        public String getFRealName() {
            return fRealName;
        }

        public void setFRealName(String fRealName) {
            this.fRealName = fRealName;
        }

        public String getFDisposeRemark() {
            return fDisposeRemark;
        }

        public void setFDisposeRemark(String fDisposeRemark) {
            this.fDisposeRemark = fDisposeRemark;
        }
    }
}
