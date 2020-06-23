package com.javachallengers.simpson.service;

import java.util.Optional;

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;

/**
 * Service with business rules related to Simpson Characters
 */
public interface SimpsonCharacterService {
	/**
	 * Gets an Simpson Character by its unique id.
	 * 
	 * @param id character id
	 * 
	 * @return the character with the given id. {@code Optional#empty()} if not found.
	 */
	Optional<SimpsonCharacter> getCharacterById(String id);

	/**
	 * Creates a new Simpson Character
	 * 
	 * @param simpsonCharacterRequestDTO
	 * 
	 * @return Created {@link SimpsonCharacter} entity
	 * 
	 * @throws SimpsonCharacterException in case of validation error (e.g. invalid name/surname) or creation failure (e.g. database error).
	 */
	SimpsonCharacter createNewCharacter(SimpsonCharacterRequestDTO simpsonCharacterRequestDTO) throws SimpsonCharacterException;
}
