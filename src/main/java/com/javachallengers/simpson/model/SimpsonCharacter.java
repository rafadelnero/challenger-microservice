package com.javachallengers.simpson.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "simpson-character")
public class SimpsonCharacter implements Serializable {
	private static final long serialVersionUID = 303948671266404097L;

	@Id
	private String id;
	
	private String name;
	
	private String surname;
	
	private LocalDate birthDate;
	
	private String city;
	
	private String country;
}
