package com.javachallengers.simpson.model;

import java.io.Serializable;
import java.time.LocalDate;
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
	
	private LocalDate birthDate;
	
	private String city;
	
	private String country;
	
	public SimpsonCharacter() {
		super();
	}
	
	public SimpsonCharacter(String name, String surname, LocalDate birthDate, String city, String country) {
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

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public String getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthDate, city, country, id, name, surname);
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
		return Objects.equals(birthDate, other.birthDate) && Objects.equals(city, other.city)
				&& Objects.equals(country, other.country) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "SimpsonCharacter [id=" + id + ", name=" + name + ", surname=" + surname + ", birthDate=" + birthDate + ", city=" + city + ", country=" + country + "]";
	}
}
