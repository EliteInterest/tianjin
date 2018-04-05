package com.zx.tjmarketmobile.http;

public class CustomException extends Exception {
	public String message;

	public CustomException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	private static final long serialVersionUID = 5498773265020269143L;
}
