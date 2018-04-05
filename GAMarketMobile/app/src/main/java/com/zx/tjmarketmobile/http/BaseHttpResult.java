package com.zx.tjmarketmobile.http;


public class BaseHttpResult {
	private boolean isSuccess =false;
	private String message;
	private Object entry;
	private int code;

	public Object getEntry() {
		return entry;
	}

	void setEntry(Object entry) {
//		if(entry!=null){
//			isSuccess=true;
//		}
		this.entry = entry;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
