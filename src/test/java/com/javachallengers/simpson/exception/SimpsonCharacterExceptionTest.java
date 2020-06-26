package com.javachallengers.simpson.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests of {@link SimpsonCharacterException}
 */
class SimpsonCharacterExceptionTest {
	@Test
	void testMessageConstructor() {
		String message = "test";
		
		SimpsonCharacterException exception = new SimpsonCharacterException(message);
		
		assertEquals(message, exception.getMessage());
	}
	
	@Test
	void testCauseConstructor() {
		Exception cause = new Exception("cause");
		
		SimpsonCharacterException exception = new SimpsonCharacterException(cause);
		
		assertEquals(cause, exception.getCause());
	}
	
	@Test
	void testMessageAndCauseConstructor() {
		String message = "test";
		Exception cause = new Exception("cause");
		
		SimpsonCharacterException exception = new SimpsonCharacterException(message, cause);
		
		assertEquals(message, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	void testInheritance() {
		SimpsonCharacterException exception = new SimpsonCharacterException("test");
		assertTrue(exception instanceof BusinessException);
	}
}
