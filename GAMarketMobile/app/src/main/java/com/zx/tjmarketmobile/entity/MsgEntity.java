package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class MsgEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5769825819366102587L;
	private String guid;
	private String name;//消息名称
	private String type;//消息类型
	private String time;//发送时间
	private String content;//消息内容
	private String msgUserid;//消息相关联的用户id
//	private String userid;
	private long eventid;//若为应急事件则为应急事件id，否则为空
	private int status;//已读状态 0：未读 1：已读
	
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getMsgUserid() {
		return msgUserid;
	}

	public void setMsgUserid(String msgUserid) {
		this.msgUserid = msgUserid;
	}
	
	public long getEventId() {
		return eventid;
	}

	public void setEventId(long eventid) {
		this.eventid = eventid;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
