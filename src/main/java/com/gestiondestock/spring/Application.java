package com.gestiondestock.spring;

import com.gestiondestock.spring.Services.ServicesDataLoaded;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Operation;
import org.tensorflow.Tensor;

@SpringBootApplication
@EnableJpaAuditing
@Configuration
public class Application implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS")
				.allowedHeaders("*")
				.exposedHeaders("Authorization");
	}
}
