package com.javachallengers.simpson.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.service.SimpsonCharacterService;

/**
 * Unit tests of {@link SimpsonCharacterControllerTest}
 */
@Tag("UnitTest")
class SimpsonCharacterControllerTest {
	@Mock
	private SimpsonCharacterService simpsonCharacterService; 
	
	private SimpsonCharacterController simpsonCharacterController;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.simpsonCharacterController = new SimpsonCharacterController(simpsonCharacterService);
	}

	@Test
	void getCharacterById_GIVEN_non_null_id_AND_id_does_not_exists_MUST_return_not_found_response() {
		// GIVEN
		String id = "2";
		doReturn(Optional.empty()).when(simpsonCharacterService).getCharacterById(eq(id));
		
		// WHEN
		ResponseEntity<SimpsonCharacter> response = simpsonCharacterController.getCharacterById(id);
		
		// THEN
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		verify(simpsonCharacterService, times(1)).getCharacterById(eq(id));
	}
	
	@Test
	void getCharacterById_GIVEN_non_null_id_AND_id_exists_MUST_return_OK_response_with_character() {
		// GIVEN
		String id = "2";
		String name = "Homer";
		String surname = "Simpson";
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname);
		doReturn(Optional.ofNullable(homerSimpson)).when(simpsonCharacterService).getCharacterById(eq(id));
		
		// WHEN
		ResponseEntity<SimpsonCharacter> response = simpsonCharacterController.getCharacterById(id);
		
		// THEN
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(homerSimpson, response.getBody());
		
		verify(simpsonCharacterService, times(1)).getCharacterById(eq(id));
	}
}
