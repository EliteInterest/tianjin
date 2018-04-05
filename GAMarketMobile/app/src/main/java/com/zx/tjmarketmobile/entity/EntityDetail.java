package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class EntityDetail implements Serializable {


    /**
     * baseInfo : {"id":"031B7B96E57C4D45908734547FB4FDCE","creditCode":null,"bizlicNum":"120191000069330","enterpriseName":"天津开发区北塘周记餐饮有限公司北塘渔村周记名都分公司","enterpriseType":null,"enterpriseTypeName":null,"enterpriseRegType":null,"enterpriseRegTypeName":null,"legalPerson":"周殿杰","foundDate":null,"industry":null,"industryCode":null,"industryType":null,"industryTypeCode":null,"contactInfo":null,"status":null,"station":null,"stationCode":null,"regOrg":null,"regType":null,"businessType":null,"regMoney":null,"address":"天津开发区第二大街新天地华庭底商A2－101号","stree":null,"businessScope":null,"tags":null,"shopName":null,"longitude":null,"latitude":null,"gridCode":null,"grid":null,"addressMatch":1}
     * business : {"licNum":1,"annNum":0,"lic":[{"code":"foodSale","num":1,"name":"食品售卖许可证"}],"meteringNum":0,"equipmentNum":0,"check":1,"standardNum":0,"metering":[{"code":"trading","num":0,"name":"商贸计量器具"},{"code":"medical","num":0,"name":"医疗计量器具"},{"code":"tanker","num":0,"name":"加油机计量"}],"trademarkNum":0,"supplier":0,"inspect":0,"sampled":1,"drugNum":0}
     * credit : null
     * update : []
     * grade : [{"level":"C","departmentId":null,"id":"D15B5DCAC0DE42459C981E6887427D45","enterpriseId":"031B7B96E57C4D45908734547FB4FDCE","type":"食品"}]
     * check : []
     * isCollect : false
     */

    private BaseInfoBean baseInfo;
    private BusinessBean business;
    private Object credit;
    private boolean isCollect;
    private List<?> update;
    private List<GradeBean> grade;
    private List<?> check;

    public BaseInfoBean getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(BaseInfoBean baseInfo) {
        this.baseInfo = baseInfo;
    }

    public BusinessBean getBusiness() {
        return business;
    }

    public void setBusiness(BusinessBean business) {
        this.business = business;
    }

    public Object getCredit() {
        return credit;
    }

    public void setCredit(Object credit) {
        this.credit = credit;
    }

    public boolean isIsCollect() {
        return isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }

    public List<?> getUpdate() {
        return update;
    }

    public void setUpdate(List<?> update) {
        this.update = update;
    }

    public List<GradeBean> getGrade() {
        return grade;
    }

    public void setGrade(List<GradeBean> grade) {
        this.grade = grade;
    }

    public List<?> getCheck() {
        return check;
    }

    public void setCheck(List<?> check) {
        this.check = check;
    }

    public static class BaseInfoBean implements Serializable{
        /**
         * id : 031B7B96E57C4D45908734547FB4FDCE
         * creditCode : null
         * bizlicNum : 120191000069330
         * enterpriseName : 天津开发区北塘周记餐饮有限公司北塘渔村周记名都分公司
         * enterpriseType : null
         * enterpriseTypeName : null
         * enterpriseRegType : null
         * enterpriseRegTypeName : null
         * legalPerson : 周殿杰
         * foundDate : null
         * industry : null
         * industryCode : null
         * industryType : null
         * industryTypeCode : null
         * contactInfo : null
         * status : null
         * station : null
         * stationCode : null
         * regOrg : null
         * regType : null
         * businessType : null
         * regMoney : null
         * address : 天津开发区第二大街新天地华庭底商A2－101号
         * stree : null
         * businessScope : null
         * tags : null
         * shopName : null
         * longitude : null
         * latitude : null
         * gridCode : null
         * grid : null
         * addressMatch : 1
         */

        private String id;
        private String creditCode;
        private String bizlicNum;
        private String enterpriseName;
        private String enterpriseType;
        private String enterpriseTypeName;
        private String enterpriseRegType;
        private String enterpriseRegTypeName;
        private String legalPerson;
        private String foundDate;
        private String industry;
        private String industryCode;
        private String industryType;
        private String industryTypeCode;
        private String contactInfo;
        private String status;
        private String station;
        private String stationCode;
        private String regOrg;
        private String regType;
        private String businessType;
        private String regMoney;
        private String address;
        private String stree;
        private String businessScope;
        private String tags;
        private String shopName;
        private String longitude;
        private String latitude;
        private String gridCode;
        private String grid;
        private int addressMatch;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreditCode() {
            return creditCode;
        }

        public void setCreditCode(String creditCode) {
            this.creditCode = creditCode;
        }

        public String getBizlicNum() {
            return bizlicNum;
        }

        public void setBizlicNum(String bizlicNum) {
            this.bizlicNum = bizlicNum;
        }

        public String getEnterpriseName() {
            return enterpriseName;
        }

        public void setEnterpriseName(String enterpriseName) {
            this.enterpriseName = enterpriseName;
        }

        public String getEnterpriseType() {
            return enterpriseType;
        }

        public void setEnterpriseType(String enterpriseType) {
            this.enterpriseType = enterpriseType;
        }

        public String getEnterpriseTypeName() {
            return enterpriseTypeName;
        }

        public void setEnterpriseTypeName(String enterpriseTypeName) {
            this.enterpriseTypeName = enterpriseTypeName;
        }

        public String getEnterpriseRegType() {
            return enterpriseRegType;
        }

        public void setEnterpriseRegType(String enterpriseRegType) {
            this.enterpriseRegType = enterpriseRegType;
        }

        public String getEnterpriseRegTypeName() {
            return enterpriseRegTypeName;
        }

        public void setEnterpriseRegTypeName(String enterpriseRegTypeName) {
            this.enterpriseRegTypeName = enterpriseRegTypeName;
        }

        public String getLegalPerson() {
            return legalPerson;
        }

        public void setLegalPerson(String legalPerson) {
            this.legalPerson = legalPerson;
        }

        public String getFoundDate() {
            return foundDate;
        }

        public void setFoundDate(String foundDate) {
            this.foundDate = foundDate;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getIndustryCode() {
            return industryCode;
        }

        public void setIndustryCode(String industryCode) {
            this.industryCode = industryCode;
        }

        public String getIndustryType() {
            return industryType;
        }

        public void setIndustryType(String industryType) {
            this.industryType = industryType;
        }

        public String getIndustryTypeCode() {
            return industryTypeCode;
        }

        public void setIndustryTypeCode(String industryTypeCode) {
            this.industryTypeCode = industryTypeCode;
        }

        public String getContactInfo() {
            return contactInfo;
        }

        public void setContactInfo(String contactInfo) {
            this.contactInfo = contactInfo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getStationCode() {
            return stationCode;
        }

        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }

        public String getRegOrg() {
            return regOrg;
        }

        public void setRegOrg(String regOrg) {
            this.regOrg = regOrg;
        }

        public String getRegType() {
            return regType;
        }

        public void setRegType(String regType) {
            this.regType = regType;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getRegMoney() {
            return regMoney;
        }

        public void setRegMoney(String regMoney) {
            this.regMoney = regMoney;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStree() {
            return stree;
        }

        public void setStree(String stree) {
            this.stree = stree;
        }

        public String getBusinessScope() {
            return businessScope;
        }

        public void setBusinessScope(String businessScope) {
            this.businessScope = businessScope;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getGridCode() {
            return gridCode;
        }

        public void setGridCode(String gridCode) {
            this.gridCode = gridCode;
        }

        public String getGrid() {
            return grid;
        }

        public void setGrid(String grid) {
            this.grid = grid;
        }

        public int getAddressMatch() {
            return addressMatch;
        }

        public void setAddressMatch(int addressMatch) {
            this.addressMatch = addressMatch;
        }
    }

    public static class BusinessBean  implements Serializable{
        /**
         * licNum : 1
         * annNum : 0
         * lic : [{"code":"foodSale","num":1,"name":"食品售卖许可证"}]
         * meteringNum : 0
         * equipmentNum : 0
         * check : 1
         * standardNum : 0
         * metering : [{"code":"trading","num":0,"name":"商贸计量器具"},{"code":"medical","num":0,"name":"医疗计量器具"},{"code":"tanker","num":0,"name":"加油机计量"}]
         * trademarkNum : 0
         * supplier : 0
         * inspect : 0
         * sampled : 1
         * drugNum : 0
         */

        private int licNum;
        private int annNum;
        private int meteringNum;
        private int equipmentNum;
        private int check;
        private int standardNum;
        private int trademarkNum;
        private int supplier;
        private int inspect;
        private int sampled;
        private int drugNum;
        private List<LicBean> lic;
        private List<MeteringBean> metering;

        public int getLicNum() {
            return licNum;
        }

        public void setLicNum(int licNum) {
            this.licNum = licNum;
        }

        public int getAnnNum() {
            return annNum;
        }

        public void setAnnNum(int annNum) {
            this.annNum = annNum;
        }

        public int getMeteringNum() {
            return meteringNum;
        }

        public void setMeteringNum(int meteringNum) {
            this.meteringNum = meteringNum;
        }

        public int getEquipmentNum() {
            return equipmentNum;
        }

        public void setEquipmentNum(int equipmentNum) {
            this.equipmentNum = equipmentNum;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        public int getStandardNum() {
            return standardNum;
        }

        public void setStandardNum(int standardNum) {
            this.standardNum = standardNum;
        }

        public int getTrademarkNum() {
            return trademarkNum;
        }

        public void setTrademarkNum(int trademarkNum) {
            this.trademarkNum = trademarkNum;
        }

        public int getSupplier() {
            return supplier;
        }

        public void setSupplier(int supplier) {
            this.supplier = supplier;
        }

        public int getInspect() {
            return inspect;
        }

        public void setInspect(int inspect) {
            this.inspect = inspect;
        }

        public int getSampled() {
            return sampled;
        }

        public void setSampled(int sampled) {
            this.sampled = sampled;
        }

        public int getDrugNum() {
            return drugNum;
        }

        public void setDrugNum(int drugNum) {
            this.drugNum = drugNum;
        }

        public List<LicBean> getLic() {
            return lic;
        }

        public void setLic(List<LicBean> lic) {
            this.lic = lic;
        }

        public List<MeteringBean> getMetering() {
            return metering;
        }

        public void setMetering(List<MeteringBean> metering) {
            this.metering = metering;
        }

        public static class LicBean implements Serializable{
            /**
             * code : foodSale
             * num : 1
             * name : 食品售卖许可证
             */

            private String code;
            private int num;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class MeteringBean implements Serializable{
            /**
             * code : trading
             * num : 0
             * name : 商贸计量器具
             */

            private String code;
            private int num;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }

    public static class GradeBean  implements Serializable{
        /**
         * level : C
         * departmentId : null
         * id : D15B5DCAC0DE42459C981E6887427D45
         * enterpriseId : 031B7B96E57C4D45908734547FB4FDCE
         * type : 食品
         */

        private String level;
        private String departmentId;
        private String id;
        private String enterpriseId;
        private String type;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
