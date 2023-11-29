package com.vaistramanagement.vaistramanagement;

import com.vaistramanagement.vaistramanagement.repositories.UserRepository;
import com.vaistramanagement.vaistramanagement.service.AuthenticationService;
import com.vaistramanagement.vaistramanagement.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootApplication
@EnableAsync
public class VaistramanagementApplication {





	public static void main(String[] args) {
		SpringApplication.run(VaistramanagementApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public JwtService jwtService() {
		return new JwtService(); // Replace this with your actual JwtService instantiation
	}


}