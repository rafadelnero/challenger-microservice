package com.javachallengers.simpson.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.javachallengers.simpson.model.dto.ApiErrorDTO;

@ControllerAdvice
public class RestControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
	
	@ExceptionHandler(value = { ResponseStatusException.class })
	public ResponseEntity<ApiErrorDTO> handleResponseStatusException(ResponseStatusException ex) {
		logger.error(ex.getMessage(), ex);
		
		String error = ex.getStatus().getReasonPhrase();
		int status = ex.getStatus().value();
		String message = ex.getReason();
		long timestamp = new Date().getTime();
		
		ApiErrorDTO body = new ApiErrorDTO(error, status, message, timestamp);
		
		logger.error("Response Status: {} {}", ex.getStatus().value(), ex.getStatus().getReasonPhrase());
		logger.error("Response Body: {}", body);
		
		return ResponseEntity.status(ex.getStatus()).contentType(MediaType.APPLICATION_JSON).body(body);
	}
}
