package com.javachallengers.simpson.exception;

public class BusinessException extends Exception {
	private static final long serialVersionUID = 5478258349839774879L;
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
}
 