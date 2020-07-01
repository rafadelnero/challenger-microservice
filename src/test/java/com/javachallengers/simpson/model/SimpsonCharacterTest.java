package com.javachallengers.simpson.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Unit testes for {@link SimpsonCharacter}
 */
@Tag("UnitTest")
class SimpsonCharacterTest {
	@Test
	public void allArgsConstructor() {
		// GIVEN
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.of(2010, 1, 1);
		
		// WHEN
		SimpsonCharacter homerSimpson = SimpsonCharacter.builder()
			.name(name)
			.surname(surname)
			.birthDate(birthDate)
			.city(city)
			.country(country)
			.build();
		
		// THEN
		assertEquals(name, homerSimpson.getName());
		assertEquals(surname, homerSimpson.getSurname());
		assertNull(homerSimpson.getId());
		assertEquals(birthDate, homerSimpson.getBirthDate());
	}

	@Test
	public void noArgsConstructor() {
		// GIVEN
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.of(2010, 1, 1);
		
		// WHEN
		SimpsonCharacter homerSimpson = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		// THEN
		assertEquals(name, homerSimpson.getName());
		assertEquals(surname, homerSimpson.getSurname());
		assertEquals(city, homerSimpson.getCity());
		assertEquals(country, homerSimpson.getCountry());
		assertNull(homerSimpson.getId());
	}

	@Test
	@RepeatedTest(2)
	public void testHashCode() {
		// GIVEN
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.of(2010, 1, 1);
		int hashCode = 1662714471;
		
		// WHEN
		SimpsonCharacter homerSimpson = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		// THEN		
		assertEquals(hashCode, homerSimpson.hashCode());
	}

	@Test
	@SuppressWarnings("unlikely-arg-type")
	public void testEquals() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.of(2010, 1, 1);
		
		SimpsonCharacter homerSimpson = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		SimpsonCharacter homerSimpsonClone = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();
		
		SimpsonCharacter bartSimpson = SimpsonCharacter.builder()
			.name("Bart")
			.surname(surname)
			.birthDate(birthDate)
			.city(city)
			.country(country)
			.build();
		
		SimpsonCharacter srBurns = SimpsonCharacter.builder()
			.name("Sr")
			.surname("Burns")
			.birthDate(birthDate)
			.city(city)
			.country(country)
			.build();
		
		assertTrue(homerSimpson.equals(homerSimpsonClone));
		assertFalse(homerSimpson.equals(bartSimpson));
		assertFalse(homerSimpson.equals(srBurns));
		assertFalse(homerSimpson.equals(null));
		assertFalse(homerSimpson.equals(new Exception()));
	}
}
