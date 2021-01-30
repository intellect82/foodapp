package com.usc.foodordering;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author venkateswarlusayana
 * GreatLearning-Food Order application
 *
 */
// Food Order Main 
@SpringBootApplication
public class FoodOrderingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderingApplication.class, args);
	}
}
