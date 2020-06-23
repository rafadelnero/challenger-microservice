package com.javachallengers.simpson.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link SimpsonCharacterRequestDTO}
 */
@Tag("UnitTest")
class SimpsonCharacterRequestDTOTest {
	@Test
	void allArgsConstructor() {
		String name = "Homer";
		String surname = "Simpson";
		
		SimpsonCharacterRequestDTO dto = new SimpsonCharacterRequestDTO(name, surname);
		
		assertEquals(name, dto.getName());
		assertEquals(surname, dto.getSurname());
	}
}
