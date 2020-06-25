package com.javachallengers.simpson.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

public final class SimpsonCharacterRequestDTO implements Serializable {
	private static final long serialVersionUID = 8840536784759714847L;

	@NotEmpty(message = "Name cannot be empty")
	private final String name;
	
	@NotEmpty(message = "Surname cannot be empty")
	private final String surname;
	
	@NotNull(message = "Birth date cannot be null")
	@Past(message = "Birth date must be in the past")
	private final LocalDate birthDate;
	
	@NotEmpty(message = "City cannot be empty")
	private String city;
	
	@NotEmpty(message = "Country cannot be empty")
	private String country;
	
	public SimpsonCharacterRequestDTO(String name, String surname, LocalDate birthDate, String city, String country) {
		super();
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.city = city;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}
}
