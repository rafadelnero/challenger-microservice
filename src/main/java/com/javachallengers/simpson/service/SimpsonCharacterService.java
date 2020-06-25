package com.javachallengers.simpson.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;
import com.javachallengers.simpson.repository.SimpsonCharacterRepository;

@Service
public class SimpsonCharacterService {
	private SimpsonCharacterRepository simpsonCharacterRepository;
	
	@Autowired
	public SimpsonCharacterService(SimpsonCharacterRepository simpsonCharacterRepository) {
		super();
		this.simpsonCharacterRepository = simpsonCharacterRepository;
	}
	
	public Optional<SimpsonCharacter> getCharacterById(String id) {
		Objects.requireNonNull(id, "id must not be null");
		return simpsonCharacterRepository.findById(id);
	}

	public SimpsonCharacter createNewCharacter(SimpsonCharacterRequestDTO simpsonCharacterRequestDTO) throws SimpsonCharacterException {
		Objects.requireNonNull(simpsonCharacterRequestDTO, "");
		
		String name = simpsonCharacterRequestDTO.getName();
		if(StringUtils.isEmpty(name)) {
			throw new SimpsonCharacterException("name must be provided");
		}
		
		String surname = simpsonCharacterRequestDTO.getSurname();
		if(StringUtils.isEmpty(surname)) {
			throw new SimpsonCharacterException("surname must be provided");
		}
		
		SimpsonCharacter simpsonCharacter = new SimpsonCharacter(name, surname);
		
		return simpsonCharacterRepository.save(simpsonCharacter);
	}
}
