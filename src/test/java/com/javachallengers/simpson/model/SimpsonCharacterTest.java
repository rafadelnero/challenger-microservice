package com.javachallengers.simpson.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname, birthDate, city, country);
		
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
		SimpsonCharacter homerSimpson = new SimpsonCharacter();
		homerSimpson.setName(name);
		homerSimpson.setSurname(surname);
		homerSimpson.setBirthDate(birthDate);
		homerSimpson.setCity(city);
		homerSimpson.setCountry(country);
		
		// THEN
		assertEquals(name, homerSimpson.getName());
		assertEquals(surname, homerSimpson.getSurname());
		assertEquals(city, homerSimpson.getCity());
		assertEquals(country, homerSimpson.getCountry());
		assertNull(homerSimpson.getId());
	}
	
	@Test
	public void testHashCode() {
		// GIVEN
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.of(2010, 1, 1);
		int hashCode = 1003308916;
		
		// WHEN
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname, birthDate, city, country);
		
		// THEN		
		assertEquals(hashCode, homerSimpson.hashCode());
	}
	
	@Test
	public void testToString() {
		// GIVEN
		String id = null;
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.of(2010, 1, 1);
		
		// WHEN
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname, birthDate, city, country);
		
		// THEN
		String toString = "SimpsonCharacter [id=" + id + ", name=" + name + ", surname=" + surname + ", birthDate=" + birthDate + ", city=" + city + ", country=" + country + "]";
		assertEquals(toString, homerSimpson.toString());
	}
	
	@Test
	@SuppressWarnings("unlikely-arg-type")
	public void testEquals() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.of(2010, 1, 1);
		
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname, birthDate, city, country);
		SimpsonCharacter homerSimpsonClone = new SimpsonCharacter(name, surname, birthDate, city, country);
		SimpsonCharacter bartSimpson = new SimpsonCharacter("Bart", surname, birthDate, city, country);
		SimpsonCharacter srBurns = new SimpsonCharacter("Sr", "Burns", birthDate, city, country);
		
		assertTrue(homerSimpson.equals(homerSimpsonClone));
		assertFalse(homerSimpson.equals(bartSimpson));
		assertFalse(homerSimpson.equals(srBurns));
		assertFalse(homerSimpson.equals(null));
		assertFalse(homerSimpson.equals(new Exception()));
	}
}
