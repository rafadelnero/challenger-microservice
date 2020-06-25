package com.javachallengers.simpson.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan( basePackages = {
	"com.javachallengers.simpson.application",
	"com.javachallengers.simpson.controller",
	"com.javachallengers.simpson.exception",
	"com.javachallengers.simpson.model",
	"com.javachallengers.simpson.model.dto",
	"com.javachallengers.simpson.repository",
	"com.javachallengers.simpson.service"
})
@EnableMongoRepositories("com.javachallengers.simpson.repository")
@EntityScan(basePackages = "com.javachallengers.simpson.model")
@PropertySource(value = "classpath:application.properties")
public class SimpsonApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(SimpsonApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SimpsonApplication.class);
	}
}
