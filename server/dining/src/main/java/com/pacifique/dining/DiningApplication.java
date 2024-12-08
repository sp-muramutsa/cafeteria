package com.pacifique.dining;


import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EnableJpaAuditing
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
		System.setProperty("EMAIL_VERIFICATION_TOKEN_EXPIRATION", dotenv.get("EMAIL_VERIFICATION_TOKEN_EXPIRATION"));

		System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
		System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));

		System.setProperty("TWILIO_SID", dotenv.get("TWILIO_SID"));
		System.setProperty("TWILIO_AUTH_TOKEN", dotenv.get("TWILIO_AUTH_TOKEN"));
		System.setProperty("TWILIO_PHONE_NUMBER", dotenv.get("TWILIO_PHONE_NUMBER"));

		SpringApplication.run(DiningApplication.class, args);
	}
}
