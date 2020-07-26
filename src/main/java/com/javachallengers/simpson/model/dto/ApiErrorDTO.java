package com.javachallengers.simpson.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(value = "ApiErrorDTO", description = "Default model of a API error response")
@Getter
@Builder
public final class ApiErrorDTO implements Serializable {
	private static final long serialVersionUID = 5160147982546412023L;

	@ApiModelProperty(value = "error", required = false, example = "java.lang.NumberFormatException")
	private final String error;
	
	@ApiModelProperty(value = "status", required = false, example = "404")
	private final int status;
	
	@ApiModelProperty(value = "message", required = false, example = "Character not found")
	private final String message;
	
	@ApiModelProperty(value = "timestamp", required = false, example = "2020-12-01T10:15:30")
	private final LocalDateTime timestamp;
}
