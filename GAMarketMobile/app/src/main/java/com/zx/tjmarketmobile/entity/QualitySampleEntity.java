package com.zx.tjmarketmobile.entity;

/**
 * Created by zhouzq on 2016/11/22.
 */
public class QualitySampleEntity {

    /**
     * 药品和工业产品检验
     */
    private String checkoutPass;
    private String type;
    private String checkoutTotal;


    public String getCheckoutTotal() {
        return checkoutTotal;
    }

    public void setCheckoutTotal(String checkoutTotal) {
        this.checkoutTotal = checkoutTotal;
    }

    public String getCheckoutPass() {
        return checkoutPass;
    }

    public void setCheckoutPass(String checkoutPass) {
        this.checkoutPass = checkoutPass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    /**
     *食品抽检
     */

    private String station;
    private String provincePass;
    private String provinceTotal;
    private String countryPass;
    private String countryTotal;

    public String getCountryTotal() {
        return countryTotal;
    }

    public void setCountryTotal(String countryTotal) {
        this.countryTotal = countryTotal;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getProvincePass() {
        return provincePass;
    }

    public void setProvincePass(String provincePass) {
        this.provincePass = provincePass;
    }

    public String getProvinceTotal() {
        return provinceTotal;
    }

    public void setProvinceTotal(String provinceTotal) {
        this.provinceTotal = provinceTotal;
    }

    public String getCountryPass() {
        return countryPass;
    }

    public void setCountryPass(String countryPass) {
        this.countryPass = countryPass;
    }



}
