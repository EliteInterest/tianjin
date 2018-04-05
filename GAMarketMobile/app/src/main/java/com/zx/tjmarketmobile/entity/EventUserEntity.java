package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class EventUserEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8991902928893683756L;
	
	private String id;
	private String name;
	private String realname;
	private String status;
	private String department;
	private String telephone;
	
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
	
	public String getRealName() {
		return this.realname;
	}
	
	public void setRealName(String realname) {
		this.realname = realname;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDepartment() {
		return this.department;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getTelephone() {
		return this.telephone;
	}
	
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
