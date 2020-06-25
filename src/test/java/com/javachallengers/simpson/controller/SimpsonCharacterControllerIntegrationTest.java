package com.javachallengers.simpson.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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

import com.javachallengers.simpson.application.SimpsonApplication;
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

	@BeforeEach
	public void configureRestAssured() {
		RestAssured.port = this.port;
	}

	@Test
	@Order(1)
	@DisplayName("Verify integration tests configurations")
	@Tag("IntegrationTest")
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
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
	@Order(6)
	@DisplayName("Create new simpson character without country")
	void create_new_character_passing_no_country_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
	@Order(7)
	@DisplayName("Create new simpson character without birth date")
	void create_new_character_passing_no_birth_date_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = null;
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
	@Order(8)
	@DisplayName("Create new simpson character passing birth date in the present")
	void create_new_character_passing_birth_date_in_the_present_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now();
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
	@DisplayName("Create new simpson character passing birth date in the future")
	void create_new_character_passing_birth_date_in_the_future_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().plusDays(1);
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
	@Order(10)
	@DisplayName("Create new simpson character passing valid data")
	void create_new_character_passing_valid_data_must_persist_new_character_and_return_created_response() {
		String name = "Homer";
		String surname = "Simpson";
		String city = "Springfield";
		String country = "United States";
		LocalDate birthDate = LocalDate.now().minusYears(1L);
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname, birthDate, city, country);

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
		
		given().
			header(HttpHeaders.CONTENT_TYPE, "application/json").
		when()
			.get("/characters" + location).
		then().
			statusCode(HttpStatus.OK.value()).
			body("name", equalTo(name)).
				and().
			body("surname", equalTo(surname));
	}
	
	@Test
	@Order(11)
	@DisplayName("Get character by id passing invalid id")
	void get_character_by_id_passing_invalid_id_must_return_not_found_response() {
		String id = "xxxxx";

		given().
		when()
			.get("/characters/" + id).
		then().
			statusCode(HttpStatus.NOT_FOUND.value());
	}
}
