package com.javachallengers.simpson.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javachallengers.simpson.model.SimpsonCharacter;

@Repository
public interface SimpsonCharacterRepository extends MongoRepository<SimpsonCharacter, String> {
	Optional<SimpsonCharacter> findByNameAndSurname(String name, String surname);
	
	Page<SimpsonCharacter> findAll(Pageable pageable);
}
