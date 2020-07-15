package com.javachallengers.simpson.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;
import com.javachallengers.simpson.service.SimpsonCharacterService;
import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.service.SimpsonCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/characters")
public class SimpsonCharacterController {
	private SimpsonCharacterService simpsonCharacterService;
	
	@Autowired
	public SimpsonCharacterController(SimpsonCharacterService simpsonCharacterService) {
		this.simpsonCharacterService = simpsonCharacterService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SimpsonCharacter> getCharacterById(@PathVariable("id") String id) {		
		return ResponseEntity.of(simpsonCharacterService.getCharacterById(id));
	}

	@PostMapping
	public ResponseEntity<Object> createNewCharacter(@RequestBody @Valid SimpsonCharacterRequestDTO character) {
		try {
			SimpsonCharacter simpsonCharacter = simpsonCharacterService.createNewCharacter(character);
			return ResponseEntity.created(new URI("/" + simpsonCharacter.getId())).build();
		} catch (SimpsonCharacterException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		} catch (URISyntaxException exception) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), exception);
		}
	}

	@GetMapping
    public Page<SimpsonCharacter> getAll(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
										 @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return simpsonCharacterService.getAll(page, size);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCharacter(@PathVariable("id") String id, @RequestBody @Valid SimpsonCharacterRequestDTO character) {
        try {
            simpsonCharacterService.updateCharacter(id, character);
            return ResponseEntity.ok().build();
        } catch (SimpsonCharacterException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCharacter(@PathVariable("id") String id) {
        try {
            simpsonCharacterService.deleteCharacter(id);
            return ResponseEntity.ok().build();
        } catch (SimpsonCharacterException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }
}
