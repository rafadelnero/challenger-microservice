package com.javachallengers.simpson.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		
		// WHEN
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname);
		
		// THEN
		assertEquals(name, homerSimpson.getName());
		assertEquals(surname, homerSimpson.getSurname());
		assertNull(homerSimpson.getId());
	}
	
	@Test
	public void noArgsConstructor() {
		// GIVEN
		String name = "Homer";
		String surname = "Simpson";
		
		// WHEN
		SimpsonCharacter homerSimpson = new SimpsonCharacter();
		
		// THEN
		homerSimpson.setName(name);
		homerSimpson.setSurname(surname);
		
		assertEquals(name, homerSimpson.getName());
		assertEquals(surname, homerSimpson.getSurname());
		assertNull(homerSimpson.getId());
	}
	
	@Test
	public void testHashCode() {
		String name = "Homer";
		String surname = "Simpson";
		
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname);
		
		int hashCode = 1630756805;
		
		assertEquals(hashCode, homerSimpson.hashCode());
	}
	
	@Test
	public void testToString() {
		// GIVEN
		String id = null;
		String name = "Homer";
		String surname = "Simpson";
		
		// WHEN
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname);
		
		// THEN
		String toString = "SimpsonCharacter [id=" + id + ", name=" + name + ", surname=" + surname + "]";
		assertEquals(toString, homerSimpson.toString());
	}
	
	@Test
	@SuppressWarnings("unlikely-arg-type")
	public void testEquals() {
		String name = "Homer";
		String surname = "Simpson";
		
		SimpsonCharacter homerSimpson = new SimpsonCharacter(name, surname);
		SimpsonCharacter homerSimpsonClone = new SimpsonCharacter(name, surname);
		SimpsonCharacter bartSimpson = new SimpsonCharacter("Bart", surname);
		SimpsonCharacter srBurns = new SimpsonCharacter("Sr", "Burns");
		
		assertTrue(homerSimpson.equals(homerSimpsonClone));
		assertFalse(homerSimpson.equals(bartSimpson));
		assertFalse(homerSimpson.equals(srBurns));
		assertFalse(homerSimpson.equals(null));
		assertFalse(homerSimpson.equals(new Exception()));
	}
}
