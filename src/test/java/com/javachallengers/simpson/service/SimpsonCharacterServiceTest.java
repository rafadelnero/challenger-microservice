package com.javachallengers.simpson.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;
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
		SimpsonCharacter homerSimpson = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		doReturn(Optional.ofNullable(homerSimpson)).when(simpsonCharacterRepository).findById(eq(id));
		
		// WHEN
		Optional<SimpsonCharacter> character = simpsonCharacterService.getCharacterById(id);
		
		// THEN
		assertTrue(character.isPresent());
		assertEquals(homerSimpson, character.get());
		verify(simpsonCharacterRepository, times(1)).findById(eq(id));
	}
	
	@Test
	void createNewCharacter_when_character_with_name_and_surname_already_exists_must_throw_exception() {
		// GIVEN
		String name = "Homer";
		String surname = "Simpson";
		LocalDate birthDate = LocalDate.now().minusYears(10);
		String city = "Springfield";
		String country = "United States";

		SimpsonCharacter homerSimpson = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		doReturn(Optional.ofNullable(homerSimpson)).when(simpsonCharacterRepository).findByNameAndSurname(eq(name), eq(surname));
		
		SimpsonCharacterRequestDTO simpsonCharacterRequestDTO = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		try {
			// WHEN
			simpsonCharacterService.createNewCharacter(simpsonCharacterRequestDTO);
			fail("must throw SimpsonCharacterException");
		} catch (SimpsonCharacterException exception) {
			// THEN
			String message = "Another character already exists with the same name " + name + " and surname " + surname;
			assertEquals(message, exception.getMessage());
			verify(simpsonCharacterRepository, times(1)).findByNameAndSurname(eq(name), eq(surname));
			verify(simpsonCharacterRepository, times(0)).save(any());
		}
	}
	
	@Test
	void createNewCharacter_when_character_with_name_and_surname_not_exists_must_persist_new_character_and_return_it() throws SimpsonCharacterException {
		// GIVEN
		String name = "Homer";
		String surname = "Simpson";
		LocalDate birthDate = LocalDate.now().minusYears(10);
		String city = "Springfield";
		String country = "United States";
		doReturn(Optional.empty()).when(simpsonCharacterRepository).findByNameAndSurname(eq(name), eq(surname));
		
		SimpsonCharacter simpsonCharacter = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		doReturn(simpsonCharacter).when(simpsonCharacterRepository).save(any(SimpsonCharacter.class));
		
		SimpsonCharacterRequestDTO simpsonCharacterRequestDTO = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		// WHEN
		SimpsonCharacter createdCharacter = simpsonCharacterService.createNewCharacter(simpsonCharacterRequestDTO);
		
		// THEN
		assertEquals(simpsonCharacter, createdCharacter);
		verify(simpsonCharacterRepository, times(1)).findByNameAndSurname(eq(name), eq(surname));
		verify(simpsonCharacterRepository, times(1)).save(any(SimpsonCharacter.class));
	}
	
	@Test
	void updateCharacter_when_character_with_id_does_not_exists_must_throw_exception() {
		// GIVEN
		String id = "122";
		String name = "Homer";
		String surname = "Simpson";
		LocalDate birthDate = LocalDate.now().minusYears(10);
		String city = "Springfield";
		String country = "United States";
		SimpsonCharacterRequestDTO simpsonCharacterRequestDTO = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		doReturn(Optional.empty()).when(simpsonCharacterRepository).findById(eq(id));
		
		try {
			// WHEN
			simpsonCharacterService.updateCharacter(id, simpsonCharacterRequestDTO);
			fail("must throw SimpsonCharacterException");
		} catch (SimpsonCharacterException exception) {
			// THEN
			assertEquals("Character not found", exception.getMessage());
			verify(simpsonCharacterRepository, times(1)).findById(eq(id));
			verify(simpsonCharacterRepository, times(0)).findByNameAndSurname(eq(name), eq(surname));
			verify(simpsonCharacterRepository, times(0)).save(any(SimpsonCharacter.class));
		}
	}
	
	@Test
	void updateCharacter_when_another_character_with_same_name_and_surname_already_exists_must_throw_exception() {
		// GIVEN
		String id = "122";
		String name = "Homer";
		String surname = "Simpson";
		LocalDate birthDate = LocalDate.now().minusYears(10);
		String city = "Springfield";
		String country = "United States";
		SimpsonCharacter character = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		character.setId("444");
		SimpsonCharacterRequestDTO simpsonCharacterRequestDTO = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		doReturn(Optional.ofNullable(character)).when(simpsonCharacterRepository).findById(eq(id));
		doReturn(Optional.ofNullable(character)).when(simpsonCharacterRepository).findByNameAndSurname(eq(name), eq(surname));
		
		try {
			// WHEN
			simpsonCharacterService.updateCharacter(id, simpsonCharacterRequestDTO);
			fail("must throw SimpsonCharacterException");
		} catch (SimpsonCharacterException exception) {
			// THEN
			String message = "Another character already exists with the same name " + name + " and surname " + surname;
			assertEquals(message, exception.getMessage());
			verify(simpsonCharacterRepository, times(1)).findById(eq(id));
			verify(simpsonCharacterRepository, times(1)).findByNameAndSurname(eq(name), eq(surname));
			verify(simpsonCharacterRepository, times(0)).save(any(SimpsonCharacter.class));
		}
	}
	
	@Test
	void updateCharacter_passing_valid_data_must_persist_character() throws SimpsonCharacterException {
		// GIVEN
		String id = "122";
		String name = "Homer";
		String surname = "Simpson";
		LocalDate birthDate = LocalDate.now().minusYears(10);
		String city = "Springfield";
		String country = "United States";
		SimpsonCharacter character = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.id(id)
				.build();
		
		SimpsonCharacterRequestDTO simpsonCharacterRequestDTO = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		doReturn(Optional.ofNullable(character)).when(simpsonCharacterRepository).findById(eq(id));
		doReturn(Optional.ofNullable(character)).when(simpsonCharacterRepository).findByNameAndSurname(eq(name), eq(surname));
		
		// WHEN
		simpsonCharacterService.updateCharacter(id, simpsonCharacterRequestDTO);
		
		//THEN
		verify(simpsonCharacterRepository, times(1)).findById(eq(id));
		verify(simpsonCharacterRepository, times(1)).findByNameAndSurname(eq(name), eq(surname));
		verify(simpsonCharacterRepository, times(1)).save(any(SimpsonCharacter.class));
	}
	
	@Test
	void deleteCharacter_when_character_by_id_not_found_must_throw_exception() {
		// GIVEN
		String id = "122";
		doReturn(Optional.empty()).when(simpsonCharacterRepository).findById(eq(id));
		
		//WHEN
		try {
			simpsonCharacterService.deleteCharacter(id);
			fail("must throw SimpsonCharacterException");
		} catch (SimpsonCharacterException exception) {
			// THEN
			assertEquals("Character not found", exception.getMessage());
			verify(simpsonCharacterRepository, times(1)).findById(eq(id));
			verify(simpsonCharacterRepository, times(0)).delete(any(SimpsonCharacter.class));
		}
	}
	
	@Test
	void deleteCharacter_when_character_by_id_is_found_must_delete_character() throws SimpsonCharacterException {
		// GIVEN
		String id = "122";
		String name = "Homer";
		String surname = "Simpson";
		LocalDate birthDate = LocalDate.now().minusYears(10);
		String city = "Springfield";
		String country = "United States";
		SimpsonCharacter character = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		doReturn(Optional.ofNullable(character)).when(simpsonCharacterRepository).findById(eq(id));
		
		//WHEN
		simpsonCharacterService.deleteCharacter(id);
		
		// THEN					
		verify(simpsonCharacterRepository, times(1)).findById(eq(id));
		verify(simpsonCharacterRepository, times(1)).delete(any(SimpsonCharacter.class));
	}
}
