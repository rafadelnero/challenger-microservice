package com.javachallengers.simpson.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class SimpsonCharacterRequestDTO implements Serializable {
	private static final long serialVersionUID = 8840536784759714847L;

	@JsonInclude(Include.NON_NULL)
	private String id;

	@NotEmpty(message = "Name cannot be empty")
	private final String name;
	
	@NotEmpty(message = "Surname cannot be empty")
	private final String surname;
	
	@NotNull(message = "Birth date cannot be null")
	@Past(message = "Birth date must be in the past")
	private final LocalDate birthDate;
	
	@NotEmpty(message = "City cannot be empty")
	private final String city;
	
	@NotEmpty(message = "Country cannot be empty")
	private final String country;
}
