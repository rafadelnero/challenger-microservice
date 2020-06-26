package com.javachallengers.simpson.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;
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
	void getCharacterById_given_non_null_id_and_id_does_not_exists_must_return_not_found_response() {
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
	void getCharacterById_given_non_null_id_and_id_exists_must_return_ok_response_with_character() {
		// GIVEN
		String id = "2";
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		SimpsonCharacter homerSimpson = SimpsonCharacter.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.id(id)
				.build();
		
		doReturn(Optional.ofNullable(homerSimpson)).when(simpsonCharacterService).getCharacterById(eq(id));
		
		// WHEN
		ResponseEntity<SimpsonCharacter> response = simpsonCharacterController.getCharacterById(id);
		
		// THEN
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(homerSimpson, response.getBody());
		verify(simpsonCharacterService, times(1)).getCharacterById(eq(id));
	}
	
	@Test
	void createNewCharacter_when_SimpsonCharacterException_is_thrown_must_throw_bad_request_response_exception () throws SimpsonCharacterException {
		//GIVEN
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		SimpsonCharacterRequestDTO character = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		SimpsonCharacterException mockedException = new SimpsonCharacterException("test");
		doThrow(mockedException).when(simpsonCharacterService).createNewCharacter(eq(character));
		
		//WHEN
		try {
			simpsonCharacterController.createNewCharacter(character);
			fail("must throw ResponseStatusException");
		} catch (ResponseStatusException exception) {
			//THEN
			assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
			assertEquals(mockedException, exception.getCause());
			verify(simpsonCharacterService, times(1)).createNewCharacter(eq(character));
		}
	}
	
	@Test
	void createNewCharacter_when_character_is_created_must_return_created_response () throws SimpsonCharacterException {
		//GIVEN
		String id = "123";
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		
		SimpsonCharacterRequestDTO character = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
				
		SimpsonCharacter createdCharacter = SimpsonCharacter.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.id(id)
				.build();
		
		doReturn(createdCharacter).when(simpsonCharacterService).createNewCharacter(eq(character));
		
		//WHEN
		ResponseEntity<Object> response = simpsonCharacterController.createNewCharacter(character);
		
		//THEN
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("/characters/" + createdCharacter.getId(), response.getHeaders().get("location").get(0));
		verify(simpsonCharacterService, times(1)).createNewCharacter(eq(character));
	}
	
	@Test
	void updateCharacter_when_SimpsonCharacterException_is_thrown_must_return_bad_request_response () throws SimpsonCharacterException {
		//GIVEN
		String id = "123";
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		SimpsonCharacterRequestDTO character = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		SimpsonCharacterException mockedException = new SimpsonCharacterException("test");
		doThrow(mockedException).when(simpsonCharacterService).updateCharacter(eq(id), eq(character));
		
		//WHEN
		try {
			simpsonCharacterController.updateCharacter(id, character);
			fail("must throw ResponseStatusException");
		} catch (ResponseStatusException exception) {
			//THEN
			assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
			assertEquals(mockedException, exception.getCause());
			verify(simpsonCharacterService, times(1)).updateCharacter(eq(id), eq(character));
		}
	}
	
	@Test
	void updateCharacter_when_character_is_updated_must_return_ok_response () throws SimpsonCharacterException {
		//GIVEN
		String id = "123";
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		SimpsonCharacterRequestDTO character = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		//WHEN
		ResponseEntity<Object> response = simpsonCharacterController.updateCharacter(id, character);
		
		//THEN
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(simpsonCharacterService, times(1)).updateCharacter(eq(id), eq(character));
	}
	
	@Test
	void deleteCharacter_when_SimpsonCharacterException_is_thrown_must_return_not_found_response () throws SimpsonCharacterException {
		// GIVEN
		String id = "1";
		SimpsonCharacterException exception = new SimpsonCharacterException("test");
		doThrow(exception).when(simpsonCharacterService).deleteCharacter(eq(id));
		
		try {
			//WHEN
			simpsonCharacterController.deleteCharacter(id);
			fail("must throw ResponseStatusException");
		} catch (ResponseStatusException e) {
			//THEN
			assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
			assertEquals(exception, e.getCause());
			verify(simpsonCharacterService, times(1)).deleteCharacter(eq(id));
		}
	}
	
	@Test
	void deleteCharacter_when_character_is_deleted_must_return_ok_response () {
		// GIVEN
		String id = "1";
		
		//WHEN
		ResponseEntity<Object> response = simpsonCharacterController.deleteCharacter(id);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
