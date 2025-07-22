package com.ochwada.jwt_auth_lab;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtAuthLabApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtAuthLabApplication.class, args);
	}


	static {
		// Load environment variables from .env (ignore if .env is missing, e.g., on Heroku)
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		// List of environment variables
		String[] envVars = {
				"JWT_SECRET",
		};

		// Iterate through each variable and set it if present
		for (String key: envVars){

			String value = dotenv.get(key);

			if (value != null ){
				System.setProperty(key, value);
				System.out.println("✅ " + key + " loaded and set.");
			}else {
				System.out.println("⚠️" + key + " not found in .env file. Skipping System.");
			}
		}


	}

}
