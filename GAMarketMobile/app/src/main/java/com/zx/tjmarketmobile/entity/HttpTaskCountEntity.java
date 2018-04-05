package com.zx.tjmarketmobile.entity;

import java.io.Serializable;

public class HttpTaskCountEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7934591477768974721L;

	private int processingAll;//处理中_全部任务数
	private int processingJjdq;//处理中_即将到期任务数
	private int processingYq;//处理中_逾期任务数
	private int pendingAll;//待处理_全部任务数
	private int pendingJjdq;//待处理_即将到期任务数
	private int pendingYq;//待处理_逾期任务数
	
	private int msgTotal;//消息总数
	private int msgNotread;//消息未读数
	
	public int getProcessingAll() {
		return processingAll;
	}

	public void setProcessingAll(int processingAll) {
		this.processingAll = processingAll;
	}
	
	public int getProcessingJjdq() {
		return processingJjdq;
	}

	public void setProcessingJjdq(int processingJjdq) {
		this.processingJjdq = processingJjdq;
	}
	
	public int getProcessingYq() {
		return processingYq;
	}

	public void setProcessingYq(int processingYq) {
		this.processingYq = processingYq;
	}
	
	public int getPendingAll() {
		return pendingAll;
	}

	public void setPendingAll(int pendingAll) {
		this.pendingAll = pendingAll;
	}
	
	public int getPendingJjdq() {
		return pendingJjdq;
	}

	public void setPendingJjdq(int pendingJjdq) {
		this.pendingJjdq = pendingJjdq;
	}
	
	public int getPendingYq() {
		return pendingYq;
	}

	public void setPendingYq(int pendingYq) {
		this.pendingYq = pendingYq;
	}
	
	public int getMsgTotal() {
		return msgTotal;
	}

	public void setMsgTotal(int msgTotal) {
		this.msgTotal = msgTotal;
	}
	
	public int getMsgNotread() {
		return msgNotread;
	}

	public void setMsgNotread(int msgNotread) {
		this.msgNotread = msgNotread;
	}
	
}
