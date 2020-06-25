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
		Optional<SimpsonCharacter> simpsonCharacter = simpsonCharacterRepository.findByNameAndSurname(name, surname);
		if(simpsonCharacter.isPresent()) {
			throw new SimpsonCharacterException("Another character already exists with the same name " + name + " and surname " + surname);
		}
		
		LocalDate birthDate = simpsonCharacterRequestDTO.getBirthDate();
		String city = simpsonCharacterRequestDTO.getCity();
		String country = simpsonCharacterRequestDTO.getCountry();
		SimpsonCharacter newCharacter = new SimpsonCharacter(name, surname, birthDate, city, country);

		return simpsonCharacterRepository.save(newCharacter);
	}

	public void updateCharacter(String id, SimpsonCharacterRequestDTO data) throws SimpsonCharacterException {
		Optional<SimpsonCharacter> currentCharacter = simpsonCharacterRepository.findById(id);
		if(currentCharacter.isEmpty()) {
			throw new SimpsonCharacterException("Character not found");
		}
		
		Optional<SimpsonCharacter> anotherCharacter = simpsonCharacterRepository.findByNameAndSurname(data.getName(), data.getSurname());
		if(anotherCharacter.isPresent() && !anotherCharacter.get().getId().equals(id)) {
			throw new SimpsonCharacterException("Another character already exists with the same name " + data.getName() + " and surname " + data.getSurname());
		}
	
		SimpsonCharacter character = currentCharacter.get();
		character.setName(data.getName());
		character.setSurname(data.getSurname());
		character.setBirthDate(data.getBirthDate());
		character.setCity(data.getCity());
		character.setCountry(data.getCountry());
		
		simpsonCharacterRepository.save(character);
	}

	public void deleteCharacter(String id) throws SimpsonCharacterException {
		Optional<SimpsonCharacter> currentCharacter = simpsonCharacterRepository.findById(id);
		if(currentCharacter.isEmpty()) {
			throw new SimpsonCharacterException("Character not found");
		}
		
		simpsonCharacterRepository.delete(currentCharacter.get());
	}
}
