package com.lets_play;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lets_play.model.Role;
import com.lets_play.model.User;
import com.lets_play.repository.UserRepository;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.lets_play.repository")
public class LetsPlayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LetsPlayApplication.class, args);
    }

    // Automatically runs on startup to seed the database
    @Bean
    public CommandLineRunner initAdminAccount(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Change the email here to force a fresh creation since your old admin is
            // cached
            String adminEmail = "admin@gmail.com";

            if (!userRepository.existsByEmail(adminEmail)) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail(adminEmail);

                // CRUCIAL: Spring Security hasRole('ADMIN') expects "ROLE_ADMIN" in the
                // database
                admin.setRole(Role.ADMIN);

                admin.setPassword(passwordEncoder.encode("admin123"));

                userRepository.save(admin);
                System.out.println(">> Built-in Admin created successfully with ROLE_ADMIN!");
            }
        };
    }

    // Force Spring Boot to use the correct authenticated database engine explicitly
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(
                "mongodb://admin:password123@localhost:27017/letsplay_db?authSource=admin");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory());
    }
}