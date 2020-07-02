package com.javachallengers.simpson.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.javachallengers.simpson.model.dto.ApiErrorDTO;

@ControllerAdvice
public class RestControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);
	
	@ExceptionHandler(value = { ResponseStatusException.class })
	public ResponseEntity<ApiErrorDTO> handleResponseStatusException(ResponseStatusException exception) {
		logger.error(exception.getMessage(), exception);
		
		String error = exception.getStatus().getReasonPhrase();
		int status = exception.getStatus().value();
		String message = exception.getReason();
		LocalDateTime timestamp = LocalDateTime.now();
		
		ApiErrorDTO body = ApiErrorDTO.builder()
				.error(error)
				.message(message)
				.status(status)
				.timestamp(timestamp)
				.build();
		
		logger.error("Response Status: {} {}", exception.getStatus().value(), exception.getStatus().getReasonPhrase());
		logger.error("Response Body: {}", body);
		
		return ResponseEntity.status(exception.getStatus()).contentType(MediaType.APPLICATION_JSON).body(body);
	}
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
		logger.error(exception.getMessage(), exception);
		
		String error = exception.getMessage();
		int status = HttpStatus.BAD_REQUEST.value();
		String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		LocalDateTime timestamp = LocalDateTime.now();
		
		ApiErrorDTO body = ApiErrorDTO.builder()
				.error(error)
				.message(message)
				.status(status)
				.timestamp(timestamp)
				.build();
		
		logger.error("Response Status: {} {}", status, HttpStatus.BAD_REQUEST.getReasonPhrase());
		logger.error("Response Body: {}", body);
		
		return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(body);
	}
}
