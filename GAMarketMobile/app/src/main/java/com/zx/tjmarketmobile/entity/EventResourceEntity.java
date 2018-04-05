package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class EventResourceEntity implements Serializable {

	private static final long serialVersionUID = -3824638806731017213L;

	public String id;
	public int eventId;
	public String name;
	public String type;
	public String addr;
	public String telephone;

	@Override
	public String toString() {
		return "[ type=" + type + "]";
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
