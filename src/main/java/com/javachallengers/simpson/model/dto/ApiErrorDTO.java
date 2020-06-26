package com.javachallengers.simpson.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ApiErrorDTO implements Serializable {
	private static final long serialVersionUID = 5160147982546412023L;

	private final String error;
	private final int status;
	private final String message;
	private final LocalDateTime timestamp;
}
