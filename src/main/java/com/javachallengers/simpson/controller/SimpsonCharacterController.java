package com.javachallengers.simpson.controller;


import javax.validation.Valid;

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;
import com.javachallengers.simpson.service.SimpsonCharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SimpsonCharacter>> getAllCharacters() {
        var allCharacterBy = simpsonCharacterService.getAllCharacterBy();
        return new ResponseEntity<>(allCharacterBy, HttpStatus.OK);
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

//    @GetMapping
//    public Page<SimpsonCharacter> getAll(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
//                                         @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
//        return simpsonCharacterService.getAll(page, size);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Object> updateCharacter(@PathVariable("id") String id, @RequestBody @Valid SimpsonCharacterRequestDTO character) {
//        try {
//            simpsonCharacterService.updateCharacter(id, character);
//            return ResponseEntity.ok().build();
//        } catch (SimpsonCharacterException exception) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Object> deleteCharacter(@PathVariable("id") String id) {
//        try {
//            simpsonCharacterService.deleteCharacter(id);
//            return ResponseEntity.ok().build();
//        } catch (SimpsonCharacterException exception) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
//        }
//    }
}
