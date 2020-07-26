package com.javachallengers.simpson.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.github.javafaker.Faker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.javachallengers.simpson.application.SimpsonApplication;
import com.javachallengers.simpson.model.SimpsonCharacter;
import com.javachallengers.simpson.model.dto.SimpsonCharacterRequestDTO;

import io.restassured.RestAssured;

/**
 * Integration tests of {@link SimpsonCharacterController}
 */
@Tag("IntegrationTest")
@DisplayName("Integration tests of /characters and /characters/{id} endpoints")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = SimpsonApplication.class)
@TestMethodOrder(OrderAnnotation.class)
class SimpsonCharacterControllerIntegrationTest {
	@LocalServerPort
	private int port;

	private static List<String> createdCharacterIds = new ArrayList<>();
	
	@BeforeEach
	public void configureRestAssured() {
		RestAssured.port = this.port;
	}

	@Test
	@Order(1)
	@DisplayName("Verify integration tests configurations")
	void test_loaded_configurations() {
		assertTrue(port > 0);
		assertEquals(this.port, RestAssured.port);
	}

	@Test
	@Order(2)
	@DisplayName("Create new simpson character without name")
	void create_new_character_passing_no_name_must_return_bad_request_response() {
		String name = "";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value())
				.and()
			.body("message", equalTo("Name cannot be empty"));
	}
	
	@Test
	@Order(3)
	@DisplayName("Create new simpson character without surname")
	void create_new_character_passing_no_surname_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value())
			.and()
		.body("message", equalTo("Surname cannot be empty"));
	}
	
	@Test
	@Order(4)
	@DisplayName("Create new simpson character without city")
	void create_new_character_passing_no_city_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value())
			.and()
		.body("message", equalTo("City cannot be empty"));
	}
	
	@Test
	@Order(5)
	@DisplayName("Create new simpson character without country")
	void create_new_character_passing_no_country_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value())
			.and()
		.body("message", equalTo("Country cannot be empty"));
	}
	
	@Test
	@Order(6)
	@DisplayName("Create new simpson character without birth date")
	void create_new_character_passing_no_birth_date_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = null;
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value())
			.and()
		.body("message", equalTo("Birth date cannot be null"));
	}
	
	@Test
	@Order(7)
	@DisplayName("Create new simpson character passing birth date in the present")
	void create_new_character_passing_birth_date_in_the_present_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value())
			.and()
		.body("message", equalTo("Birth date must be in the past"));
	}
	
	@Test
	@Order(8)
	@DisplayName("Create new simpson character passing birth date in the future")
	void create_new_character_passing_birth_date_in_the_future_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().plusDays(1);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value())
			.and()
		.body("message", equalTo("Birth date must be in the past"));
	}
	
	@Test
	@Order(9)
	@DisplayName("Create new simpson character passing valid data")
	void create_new_character_passing_valid_data_must_persist_new_character_and_return_created_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		String location = given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.CREATED.value()).
			header("location", notNullValue()).
		extract()
			.header("location");
		
		String homerSimpsonId = location.substring(location.lastIndexOf('/') + 1);
		createdCharacterIds.add(homerSimpsonId);
	}
	
	@Test
	@Order(10)
	@DisplayName("Create new character but character already exists with the same name and surname")
	void create_new_simpson_character_passing_name_and_surname_but_it_already_exists_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("message", equalTo("Another character already exists with the same name " + name + " and surname " + surname));
	}
	
	@Test
	@Order(11)
	@DisplayName("Get character by id passing valid id")
	void get_character_by_id_passing_valid_id_must_return_ok_response () {
		String homerSimpsonId = createdCharacterIds.get(0);
		
		given().
		when()
			.get("/characters/" + homerSimpsonId).
		then().
			statusCode(HttpStatus.OK.value()).
				and().
			body("name", equalTo("Homer")).
				and().
			body("surname", equalTo("Simpson")).
				and().
			body("city", equalTo("Springfield")).
				and().
			body("country", equalTo("United States"));
	}
	
	@Test
	@Order(12)
	@DisplayName("Get character by id passing invalid id")
	void get_character_by_id_passing_invalid_id_must_return_not_found_response() {
		String id = "xxxxx";

		given().
		when()
			.get("/characters/" + id).
		then().
			statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	@Order(13)
	@DisplayName("Update character non existing id")
	void update_character_non_existing_id_must_bad_request_response() {
		String id = "xxx";
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("message", equalTo("Character not found"));
	}
	
	@Test
	@Order(14)
	@DisplayName("Update character passing no name")
	void update_character_passing_no_name_must_return_bad_request_response() {
		String id = createdCharacterIds.get(0);
		String name = "";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("message", equalTo("Name cannot be empty"));
	}
	
	@Test
	@Order(15)
	@DisplayName("Update character passing no surname")
	void update_character_passing_no_surname_must_return_bad_request_response() {
		String id = createdCharacterIds.get(0);
		String name = "Homer";
		String surname = "";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("message", equalTo("Surname cannot be empty"));
	}
	
	@Test
	@Order(16)
	@DisplayName("Update character passing no birth date")
	void update_character_passing_no_birth_date_must_return_bad_request_response() {
		String id = createdCharacterIds.get(0);
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = null;
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("message", equalTo("Birth date cannot be null"));
	}
	
	@Test
	@Order(17)
	@DisplayName("Update character passing birth date in the present")
	void update_character_passing_birth_date_in_the_present_must_return_bad_request_response() {
		String id = createdCharacterIds.get(0);
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and().
			body("message", equalTo("Birth date must be in the past"));
	}
	
	@Test
	@Order(18)
	@DisplayName("Update character passing birth date in the future")
	void update_character_passing_birth_date_in_the_future_must_return_bad_request_response() {
		String id = createdCharacterIds.get(0);
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().plusYears(1);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
			and().
		body("message", equalTo("Birth date must be in the past"));
	}
	
	@Test
	@Order(19)
	@DisplayName("Update character passing no city")
	void update_character_passing_no_city_must_return_bad_request_response() {
		String id = createdCharacterIds.get(0);
		String name = "Homer";
		String surname = "Simpson";
		String city = "";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
			and().
		body("message", equalTo("City cannot be empty"));
	}
	
	@Test
	@Order(20)
	@DisplayName("Update character passing no country")
	void update_character_passing_no_country_must_return_bad_request_response() {
		String id = createdCharacterIds.get(0);
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "";
		LocalDate birthDate = LocalDate.now().minusYears(1);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
			and().
		body("message", equalTo("Country cannot be empty"));
	}
	
	@Test
	@Order(21)
	@DisplayName("Update character passing new valid data")
	void update_character_passing_new_valid_data_must_return_ok_response() {
		String id = createdCharacterIds.get(0);
		String name = "Bart";
		String surname = "Simpson Jr";
		String city = "São Paulo";
		String country = "Brazil";
		LocalDate birthDate = LocalDate.now().minusYears(10);
		
		SimpsonCharacter characterBeforeUpdate = given()
				.header(HttpHeaders.CONTENT_TYPE, "application/json").
				when()
					.get("/characters/" + id).
				then().
					statusCode(HttpStatus.OK.value())
					.extract()
					.as(SimpsonCharacter.class);
		
		SimpsonCharacterRequestDTO requestBody = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
		
		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.put("/characters/" + id).
		then().
			statusCode(HttpStatus.OK.value());
		
		SimpsonCharacter characterAfterUpdate = given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json").
		when()
			.get("/characters/" + id).
		then().
			statusCode(HttpStatus.OK.value())
			.extract()
			.as(SimpsonCharacter.class);
		
		assertEquals(characterBeforeUpdate.getId(), characterAfterUpdate.getId());
		assertNotEquals(characterBeforeUpdate.getName(), characterAfterUpdate.getName());
		assertNotEquals(characterBeforeUpdate.getBirthDate(), characterAfterUpdate.getBirthDate());
		assertNotEquals(characterBeforeUpdate.getSurname(), characterAfterUpdate.getSurname());
		assertNotEquals(characterBeforeUpdate.getCity(), characterAfterUpdate.getCity());
		assertNotEquals(characterBeforeUpdate.getCountry(), characterAfterUpdate.getCountry());
		
		assertEquals(id, characterAfterUpdate.getId());
		assertEquals(name, characterAfterUpdate.getName());
		assertEquals(birthDate, characterAfterUpdate.getBirthDate());
		assertEquals(surname, characterAfterUpdate.getSurname());
		assertEquals(city, characterAfterUpdate.getCity());
		assertEquals(country, characterAfterUpdate.getCountry());
	}
	
	@Test
	@Order(22)
	@DisplayName("Update character passing another character's name and surname")
	void update_character_passing_another_characters_name_and_surname_must_return_bad_request_response() {
		SimpsonCharacterRequestDTO lisa  = SimpsonCharacterRequestDTO.builder()
			.birthDate(LocalDate.now().minusYears(20))
			.city("Springfield")
			.country("United States")
			.name("Lisa")
			.surname("Simpson")
			.build();
		
		String location = given()
				.header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body(lisa).
			when()
				.post("/characters").
			then().
				statusCode(HttpStatus.CREATED.value()).
				header("location", notNullValue()).
			extract()
				.header("location");
			
		String lisaId = location.substring(location.lastIndexOf('/') + 1);
		createdCharacterIds.add(lisaId);
		
		SimpsonCharacterRequestDTO margaret = SimpsonCharacterRequestDTO.builder()
			.birthDate(LocalDate.now().minusYears(20))
			.city("Springfield")
			.country("United States")
			.name("Margaret")
			.surname("Simpson")
			.build();
		
		location = given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(margaret).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.CREATED.value()).
			header("location", notNullValue()).
			extract()
			.header("location");
		
		String margaretId = location.substring(location.lastIndexOf('/') + 1);
		createdCharacterIds.add(margaretId);
		
		lisa = SimpsonCharacterRequestDTO.builder()
			.birthDate(LocalDate.now().minusYears(20))
			.city("Springfield")
			.country("United States")
			.name(margaret.getName())
			.surname(margaret.getSurname())
			.build();
		
		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(lisa).
		when()
			.put("/characters/" + lisaId).
		then().
			statusCode(HttpStatus.BAD_REQUEST.value()).
				and()
			.body("message", equalTo("Another character already exists with the same name " + margaret.getName() + " and surname " + margaret.getSurname()));
	}
	
	@Test
	@Order(23)
	@DisplayName("Delete character passing invalid id")
	void delete_character_passing_invalid_id_must_return_not_found_response() {
		String id = "xxxxx";
		
		given().
		when()
			.delete("/characters/" + id).
		then().
			statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	@Order(24)
	@DisplayName("Delete character passing valid id")
	void delete_character_passing_valid_id_must_return_ok_response() {
		for(String id : createdCharacterIds) {
			given().
			when()
				.delete("/characters/" + id).
			then().
				statusCode(HttpStatus.OK.value());
			
			given().
			when()
				.get("/characters/" + id).
			then().
				statusCode(HttpStatus.NOT_FOUND.value());
		}
	}
	
	@Test
	@Order(25)
	@DisplayName("Delete character passing valid id for already deleted character")
	void delete_character_passing_valid_id_for_already_deleted_character_must_return_not_found_response () {
		String id = createdCharacterIds.get(0);
		
		given().
		when()
			.delete("/characters/" + id).
		then().
			statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	@Order(26)
	@DisplayName("Get all characters using default pagination")
	void get_all_characters_using_default_pagination () {
		// GIVEN
		createdCharacterIds.clear();
		
		int defaultSize = 10;
		int defaultPage = 0;
	
		Faker faker = new Faker();
		for(int i = 0; i < 10; i++) {
			String name = faker.name().firstName();
			String surname = faker.name().lastName();
			LocalDate birthDate = LocalDate.now().minusYears(faker.number().numberBetween(1, 30));
			String city = faker.country().capital();
			String country = faker.country().name();
			
			SimpsonCharacterRequestDTO character = SimpsonCharacterRequestDTO.builder()
				.birthDate(birthDate)
				.city(city)
				.country(country)
				.name(name)
				.surname(surname)
				.build();
			
			String location = given()
				.header(HttpHeaders.CONTENT_TYPE, "application/json")
				.body(character).
			when()
				.post("/characters").
			then().
				statusCode(HttpStatus.CREATED.value()).
				header("location", notNullValue()).
				extract()
				.header("location");
			
			String id = location.substring(location.lastIndexOf('/') + 1);
			createdCharacterIds.add(id);
		}
		
		//WHEN
		String resposeBody = given().
		when()
			.get("/characters").
		then().
			statusCode(HttpStatus.OK.value())
			.and()
			.extract().asString();
		
		JsonElement jsonElement = JsonParser.parseString(resposeBody);
		JsonArray content = jsonElement.getAsJsonObject().getAsJsonArray("content");
		JsonObject pageable = jsonElement.getAsJsonObject().getAsJsonObject("pageable");
		
		// THEN
		assertEquals(defaultSize, content.size());
		assertEquals(defaultPage, pageable.getAsJsonObject().getAsJsonPrimitive("pageNumber").getAsInt());
		assertEquals(defaultSize, pageable.getAsJsonObject().getAsJsonPrimitive("pageSize").getAsInt());
		assertTrue(jsonElement.getAsJsonObject().get("last").getAsBoolean());
		assertEquals(1, jsonElement.getAsJsonObject().get("totalPages").getAsInt());
		assertEquals(10, jsonElement.getAsJsonObject().get("totalElements").getAsInt());
		assertTrue(jsonElement.getAsJsonObject().get("first").getAsBoolean());
	}
	
	@Test
	@Order(27)
	@DisplayName("Get all characters using custom pagination")
	void get_all_characters_using_custom_pagination () {
		// GIVEN
		int page = 1;
		int size = 3;
		int totalElements = 10;
		int totalPages = (int) Math.ceil((double) totalElements / size);
		
		//WHEN
		String resposeBody = given().
			when()
				.get("/characters?page=" + page + "&size=" + size).
			then().
				statusCode(HttpStatus.OK.value())
				.and()
				.extract().asString();
		
		JsonElement jsonElement = JsonParser.parseString(resposeBody);
		JsonArray content = jsonElement.getAsJsonObject().getAsJsonArray("content");
		JsonObject pageable = jsonElement.getAsJsonObject().getAsJsonObject("pageable");
		
		//THEN			
		assertEquals(size, content.size());
		assertEquals(page, pageable.getAsJsonObject().getAsJsonPrimitive("pageNumber").getAsInt());
		assertEquals(size, pageable.getAsJsonObject().getAsJsonPrimitive("pageSize").getAsInt());
		assertFalse(jsonElement.getAsJsonObject().get("last").getAsBoolean());
		assertEquals(totalPages, jsonElement.getAsJsonObject().get("totalPages").getAsInt());
		assertEquals(totalElements, jsonElement.getAsJsonObject().get("totalElements").getAsInt());
		assertFalse(jsonElement.getAsJsonObject().get("first").getAsBoolean());
	}
	
	@Test
	@Order(28)
	@DisplayName("Check swagger page")
	void check_swagger_page() {
		given().
		when()
			.get("/swagger-ui.html").
		then().
			statusCode(HttpStatus.OK.value());
	}
}
