package com.javachallengers.simpson.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;
import com.javachallengers.simpson.service.SimpsonCharacterService;

@RestController
@RequestMapping("/characters")
public class SimpsonCharacterController {
	private SimpsonCharacterService simpsonCharacterService;
	
	@Autowired
	public SimpsonCharacterController(SimpsonCharacterService simpsonCharacterService) {
		super();
		this.simpsonCharacterService = simpsonCharacterService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SimpsonCharacter> getCharacterById(@PathVariable("id") String id) {		
		return ResponseEntity.of(simpsonCharacterService.getCharacterById(id));
	}
	
	@PostMapping
	public ResponseEntity<Object> createNewCharacter(@RequestBody @Valid SimpsonCharacterRequestDTO character) {
		try {
			SimpsonCharacter simpsonCharacater = simpsonCharacterService.createNewCharacter(character);
			return ResponseEntity.created(new URI("/" + simpsonCharacater.getId())).build();
		} catch (SimpsonCharacterException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		} catch (URISyntaxException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}
}
 