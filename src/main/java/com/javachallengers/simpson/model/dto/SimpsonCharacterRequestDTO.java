package com.javachallengers.simpson.model.dto;

import java.io.Serializable;

public final class SimpsonCharacterRequestDTO implements Serializable {
	private static final long serialVersionUID = 8840536784759714847L;

	private final String name;
	private final String surname;
	
	public SimpsonCharacterRequestDTO(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}
}
