package com.pacifique.dining;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class DiningApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().load();


		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("SECRET_KEY", dotenv.get("SECRET_KEY"));
		System.setProperty("ACCESS_TOKEN_EXPIRATION", dotenv.get("ACCESS_TOKEN_EXPIRATION"));
		System.setProperty("REFRESH_TOKEN_EXPIRATION", dotenv.get("REFRESH_TOKEN_EXPIRATION"));


		SpringApplication.run(DiningApplication.class, args);
	}
}
