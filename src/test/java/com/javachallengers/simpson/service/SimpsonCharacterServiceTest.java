package com.javachallengers.simpson.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.repository.SimpsonCharacterRepository;

/**
 * Unit tests for {@link SimpsonCharacterService}
 */
@Tag("UnitTest")
class SimpsonCharacterServiceTest {
	@Mock
	private SimpsonCharacterRepository simpsonCharacterRepository;
	
	private SimpsonCharacterService simpsonCharacterService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.simpsonCharacterService = new SimpsonCharacterService(simpsonCharacterRepository);
	}
	
	@Test
	void getById_given_null_id_must_throw_exception() {
		// GIVEN
		String id = null;
		
		try {
			// WHEN
			simpsonCharacterService.getCharacterById(id);
			fail("NullPointerException should be thrown");
		} catch (NullPointerException e) {
			// THEN
			assertEquals("id must not be null", e.getMessage());
		}
	}
	
	@Test
	void getById_given_non_null_id_and_not_exist_must_return_empty_optional() {
		// GIVEN
		String id = "1";
		doReturn(Optional.empty()).when(simpsonCharacterRepository).findById(eq(id));
		
		// WHEN
		Optional<SimpsonCharacter> character = simpsonCharacterService.getCharacterById(id);
		
		// THEN
		assertTrue(character.isEmpty());
		verify(simpsonCharacterRepository, times(1)).findById(eq(id));
	}
	
	@Test
	void getById_given_non_null_id_and_id_exists_must_return_non_empty_optional() {
		// GIVEN
		String id = "1";
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname, birthDate, city, country);
		doReturn(Optional.ofNullable(homerSimpson)).when(simpsonCharacterRepository).findById(eq(id));
		
		// WHEN
		Optional<SimpsonCharacter> character = simpsonCharacterService.getCharacterById(id);
		
		// THEN
		assertTrue(character.isPresent());
		assertEquals(homerSimpson, character.get());
		verify(simpsonCharacterRepository, times(1)).findById(eq(id));
	}
}
