package com.lets_play;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.lets_play.model.Product;
import com.lets_play.repository.ProductRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.lets_play.repository")
public class LetsPlayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LetsPlayApplication.class, args);
	}

	// Temporary bean to test DB connection
	// @Bean
	// CommandLineRunner runner(ProductRepository repository) {
	// return args -> {
	// Product testProduct = Product.builder()
	// .name("Test Console")
	// .price(499.99)
	// .description("Gaming starts here")
	// .build();

	// repository.save(testProduct);
	// System.out.println("MongoDB Connection Successful. Saved: " +
	// testProduct.getName());
	// };
	// }

}
