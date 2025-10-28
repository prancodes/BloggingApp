package com.blog.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// --- Swagger Configuration (optional)
// import io.swagger.v3.oas.annotations.OpenAPIDefinition;
// import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
// @OpenAPIDefinition(
// 	info = @Info(
// 		title = "Blogging Project API",
// 		version = "1.0.0",
// 		description = "A simple API for managing blog articles."
// 	)
// )
public class BloggingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloggingProjectApplication.class, args);
	}

}
