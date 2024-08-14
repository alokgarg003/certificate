package com.cdac;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cdac.model.User;
import com.cdac.repository.UserRepository;

@SpringBootApplication
public class OnlineCertificateManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineCertificateManagementSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User dex = userRepository.findByUsername("admin");
		if (dex == null) {
			return args -> {
				userRepository.save(new User("admin", passwordEncoder.encode("admin"), "ADMIN"));
			};
		}
		return args -> {
			userRepository.findByUsername("admin");
		};

	}

}