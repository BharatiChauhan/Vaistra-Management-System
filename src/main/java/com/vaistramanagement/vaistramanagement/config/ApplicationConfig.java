package com.vaistramanagement.vaistramanagement.config;

import com.vaistramanagement.vaistramanagement.config.security.RegisterRequest;
import com.vaistramanagement.vaistramanagement.repositories.UserRepository;
import com.vaistramanagement.vaistramanagement.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.vaistramanagement.vaistramanagement.entity.Role.USER;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig
{
    private final UserRepository repository;

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        return username -> repository.findByEmail(username)
//                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider()
//    {
//        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//    @Bean
//   public PasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegisterRequest.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("admin@mail.com")
//                    .password("123456789")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//        };

}
