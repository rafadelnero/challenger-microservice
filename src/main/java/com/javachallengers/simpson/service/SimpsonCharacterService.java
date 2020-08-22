package com.javachallengers.simpson.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<SimpsonCharacter> getAllCharacterBy() {
        return simpsonCharacterRepository.findAll();
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

		SimpsonCharacter newCharacter = SimpsonCharacter.builder()
				.name(name)
				.surname(surname)
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.build();

		return simpsonCharacterRepository.save(newCharacter);
	}

	public void updateCharacter(String id, SimpsonCharacterRequestDTO data) throws SimpsonCharacterException {
		SimpsonCharacter currentCharacter = simpsonCharacterRepository.findById(id).orElseThrow(() -> new SimpsonCharacterException("Character not found"));

		Optional<SimpsonCharacter> anotherCharacter = simpsonCharacterRepository.findByNameAndSurname(data.getName(), data.getSurname());
		if(anotherCharacter.isPresent() && !anotherCharacter.get().getId().equals(id)) {
			throw new SimpsonCharacterException("Another character already exists with the same name " + data.getName() + " and surname " + data.getSurname());
		}

		currentCharacter = SimpsonCharacter.builder()
				.id(currentCharacter.getId())
				.name(data.getName())
				.surname(data.getSurname())
				.birthDate(data.getBirthDate())
				.city(data.getCity())
				.country(data.getCountry())
				.build();

		simpsonCharacterRepository.save(currentCharacter);
	}

	public void deleteCharacter(String id) throws SimpsonCharacterException {
		Optional<SimpsonCharacter> currentCharacter = simpsonCharacterRepository.findById(id);
		if(currentCharacter.isPresent()) {
			simpsonCharacterRepository.delete(currentCharacter.get());
		}else {
			throw new SimpsonCharacterException("Character not found");
		}
	}

	public Page<SimpsonCharacter> getAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
		return this.simpsonCharacterRepository.findAll(pageRequest);
	}
}
