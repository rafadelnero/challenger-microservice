package com.javachallengers.simpson.controller;

import javax.validation.Valid;

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

import com.javachallengers.simpson.exception.SimpsonCharacterException;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.ApiErrorDTO;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;
import com.javachallengers.simpson.service.SimpsonCharacterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Simpson Character API")
@RestController
@RequestMapping("/characters")
public class SimpsonCharacterController {
	private SimpsonCharacterService simpsonCharacterService;
	
	@Autowired
	public SimpsonCharacterController(SimpsonCharacterService simpsonCharacterService) {
		super();
		this.simpsonCharacterService = simpsonCharacterService;
	}
	
	@ApiOperation(value = "Returns a list of all simpson characters (with pagination)")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Returns a list of simpson characters (with pagination)")
	})
	@GetMapping
	public Page<SimpsonCharacter> getAll(
			@ApiParam(value = "The number of the page", defaultValue = "0", required = false) @RequestParam(required = false, value = "page", defaultValue = "0") int page,
			@ApiParam(value = "The size of the page", defaultValue = "10", required = false) @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
		return simpsonCharacterService.getAll(page, size);
	}
	
	@ApiOperation(value = "Returns a simpson character by id")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Returns a simpson character by id"),
	    @ApiResponse(code = 404, message = "Character not found", response = ApiErrorDTO.class)
	})
	@GetMapping("/{id}")
	public ResponseEntity<SimpsonCharacter> getCharacterById(@ApiParam(value = "The id of the character", required = true) @PathVariable("id") String id) {		
		return ResponseEntity.of(simpsonCharacterService.getCharacterById(id));
	}
	
	@ApiOperation(value = "Creates a new simpson character")
	@ApiResponses(value = {
	    @ApiResponse(code = 201, message = "Creates a new simpson character"),
	    @ApiResponse(code = 400, message = "Invalid data or another character already exists with same name and username", response = ApiErrorDTO.class)
	})
	@PostMapping
	public ResponseEntity<Object> createNewCharacter(@ApiParam(value = "The body of the request, with character data", required = true) @RequestBody @Valid SimpsonCharacterRequestDTO character) {
		try {
			SimpsonCharacter simpsonCharacater = simpsonCharacterService.createNewCharacter(character);
			String location = String.format("/characters/%s", simpsonCharacater.getId());
			return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location).build();
		} catch (SimpsonCharacterException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		}
	}
	
	@ApiOperation(value = "Updates a simpson character")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Updated simpson character"),
	    @ApiResponse(code = 400, message = "Character not found, or invalid data or tried to update character to another one (same name and username)", response = ApiErrorDTO.class)
	})
	@PutMapping("/{id}")
	public ResponseEntity<Object> updateCharacter(@ApiParam(value = "The id of the character", required = true) @PathVariable("id") String id, @ApiParam(value = "The body of the request, with character data to be updated", required = true) @RequestBody @Valid SimpsonCharacterRequestDTO character) {
		try {
			simpsonCharacterService.updateCharacter(id, character);
			return ResponseEntity.ok().build();
		} catch (SimpsonCharacterException exception) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
		}
	}
	
	@ApiOperation(value = "Deletes a simpson character")
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Deleted simpson character"),
	    @ApiResponse(code = 404, message = "Character not found", response = ApiErrorDTO.class)
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteCharacter(@ApiParam(value = "The id of the character", required = true) @PathVariable("id") String id) {
		try {
			simpsonCharacterService.deleteCharacter(id);
			return ResponseEntity.ok().build();
		} catch (SimpsonCharacterException exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
		}
	}
}
 