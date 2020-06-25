package com.javachallengers.simpson.exception;

public class SimpsonCharacterException extends BusinessException {
	private static final long serialVersionUID = -2880758825940468033L;

	public SimpsonCharacterException(String message) {
		super(message);
	}
	
	public SimpsonCharacterException(Throwable cause) {
		super(cause);
	}
	
	public SimpsonCharacterException(String message, Throwable cause) {
		super(message, cause);
	}
}
