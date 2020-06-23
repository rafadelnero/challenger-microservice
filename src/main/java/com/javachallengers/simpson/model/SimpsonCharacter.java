package com.javachallengers.simpson.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "simpson-character")
public class SimpsonCharacter implements Serializable {
	private static final long serialVersionUID = 303948671266404097L;

	@Id
	private String id;
	
	private String name;
	
	private String surname;
	
	public SimpsonCharacter() {
		super();
	}
	
	public SimpsonCharacter(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		SimpsonCharacter other = (SimpsonCharacter) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(surname, other.surname);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, name, surname);
	}

	@Override
	public String toString() {
		return "SimpsonCharacter [id=" + id + ", name=" + name + ", surname=" + surname + "]";
	}
}
