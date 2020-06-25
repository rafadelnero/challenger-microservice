package com.javachallengers.simpson.service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		String name = simpsonCharacterRequestDTO.getName();
		String surname = simpsonCharacterRequestDTO.getSurname();
		LocalDate birthDate = simpsonCharacterRequestDTO.getBirthDate();
		String city = simpsonCharacterRequestDTO.getCity();
		String country = simpsonCharacterRequestDTO.getCountry();
		SimpsonCharacter simpsonCharacter = new SimpsonCharacter(name, surname, birthDate, city, country);
		
		return simpsonCharacterRepository.save(simpsonCharacter);
	}
}
