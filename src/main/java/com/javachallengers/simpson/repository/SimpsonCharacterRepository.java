package com.javachallengers.simpson.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javachallengers.simpson.model.SimpsonCharacter;

@Repository
public interface SimpsonCharacterRepository extends MongoRepository<SimpsonCharacter, String> {

}
