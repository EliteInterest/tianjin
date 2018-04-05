package com.zx.tjmarketmobile.entity;

import java.io.Serializable;
import java.util.List;

public class HttpMsgListEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4161151505877091983L;

	private int total;//总记录数
	private int notread;//未读记录数
	private List<MsgEntity> msgList;//查询结果
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getNoread() {
		return notread;
	}

	public void setNoread(int notread) {
		this.notread = notread;
	}
	
	public List<MsgEntity> getMsgList() {
		return this.msgList;
	}

	public void setMsgList(List<MsgEntity> msgList) {
		this.msgList = msgList;
	}
}
