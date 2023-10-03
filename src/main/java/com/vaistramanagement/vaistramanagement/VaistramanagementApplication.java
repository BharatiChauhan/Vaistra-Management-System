package com.vaistramanagement.vaistramanagement;

import com.vaistramanagement.vaistramanagement.config.security.RegisterRequest;
import com.vaistramanagement.vaistramanagement.service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import static com.vaistramanagement.vaistramanagement.entity.Role.USER;

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


}