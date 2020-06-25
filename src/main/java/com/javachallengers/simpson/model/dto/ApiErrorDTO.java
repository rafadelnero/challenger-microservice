package com.javachallengers.simpson.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public final class ApiErrorDTO implements Serializable {
	private static final long serialVersionUID = 5160147982546412023L;

	private final String error;
	private final int status;
	private final String message;
	private final LocalDateTime timestamp;
	
	public ApiErrorDTO(String error, int status, String message, LocalDateTime timestamp) {
		super();
		this.error = error;
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

	public String getError() {
		return error;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}
