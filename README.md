# Spring boot challenger-microservice

Backend application with a basic CRUD using Java 11, Spring Boot, Spring Data and MongoDB.

This project is also containerised with Docker and automated by Jenkins to deploy on dev, qa, staging and prod.

## Please follow these steps to start your application 

- mvn clean package 
- docker-compose up

## To test an application call the backend from postman:

- http://localhost:8081/characters (POST)

`{
 	"name" : "name",
 	"surname" : "surname",
 	"birthDate" : "1983-03-29",
 	"city" : "city",
 	"country" : "country"
 }`

- http://localhost:8081/characters (GET) # will give back all the characters 

## To test the use of OAuth:

Create your [Google Developer account](https://console.developers.google.com/) and create your credentials for
the OAuth client. Then populate the following environment variables with your client-id and client-secret:

`CLIENT_ID`
`CLIENT_SECRET`

Finally, access the following endpoints:

http://localhost:8081/oauth-test/
http://localhost:8081/oauth-test/restricted 
