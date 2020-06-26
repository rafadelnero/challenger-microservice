package com.javachallengers.simpson.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests of {@link BusinessException}
 */
class BusinessExceptionTest {
	@Test
	void messageConstructor() {
		String message = "test";
		
		BusinessException exception = new BusinessException(message);
		
		assertEquals(message, exception.getMessage());
	}
	
	@Test
	void causeConstructor() {
		Exception cause = new Exception("cause");
		
		BusinessException exception = new BusinessException(cause);
		
		assertEquals(cause, exception.getCause());
	}
	
	@Test
	void messageAndCauseConstructor() {
		String message = "test";
		Exception cause = new Exception("cause");
		
		BusinessException exception = new BusinessException(message, cause);
		
		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}
}
