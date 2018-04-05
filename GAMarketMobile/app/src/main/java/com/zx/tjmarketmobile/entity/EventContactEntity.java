package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class EventContactEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3409240385490114267L;

	private String id;
	private String name;
	private String orgname;
	private String duty;
	private String remark;
	private String telephone;
	private String cellphone;
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOrgName() {
		return this.orgname;
	}
	
	public void setOrgName(String orgname) {
		this.orgname = orgname;
	}
	
	public String getDuty() {
		return this.duty;
	}
	
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	public String getCellphone() {
		return this.cellphone;
	}
	
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
}
