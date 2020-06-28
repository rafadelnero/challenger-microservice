# Spring boot challenger-microservice

Backend application with a basic CRUD using Java 11, Spring Boot, Spring Data and MongoDB.

This project is also containerised with Docker and automated by Jenkins to deploy on dev, qa, staging and prod.

# Please follow these steps to start your application 

- mvn clean package 
- docker-compose up

# To test an application call the backend from postman:

- http://localhost:8080/characters (POST)

`{
 	"name" : "name",
 	"surname" : "surname",
 	"birthDate" : "1983-03-29",
 	"city" : "city",
 	"country" : "country"
 }`

- http://localhost:8080/characters (GET) # will give back all the characters 
