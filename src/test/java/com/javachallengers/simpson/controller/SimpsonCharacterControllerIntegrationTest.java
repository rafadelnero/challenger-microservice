package com.javachallengers.simpson.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
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
	@Tag("IntegrationTest")
	void test_loaded_configurations() {
		assertTrue(port > 0);
		assertEquals(this.port, RestAssured.port);
	}

	@Test
	@Order(2)
	void create_new_character_passing_no_name_must_return_bad_request_response() {
		String name = "";
		String surname = "Simpson";
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname);

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@Order(3)
	void create_new_character_passing_no_surname_must_return_bad_request_response() {
		String name = "Homer";
		String surname = "";
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname);

		given()
			.header(HttpHeaders.CONTENT_TYPE, "application/json")
			.body(requestBody).
		when()
			.post("/characters").
		then().
			statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	@Order(4)
	void create_new_character_passing_valid_data_must_persist_new_character_and_return_created_response() {
		String name = "Homer";
		String surname = "Simpson";
		
		SimpsonCharacterRequestDTO requestBody = new SimpsonCharacterRequestDTO(name, surname);

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
}
