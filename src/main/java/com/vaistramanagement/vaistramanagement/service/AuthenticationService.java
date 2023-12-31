package com.vaistramanagement.vaistramanagement.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaistramanagement.vaistramanagement.config.security.AuthenticationRequest;
import com.vaistramanagement.vaistramanagement.config.security.AuthenticationResponse;
import com.vaistramanagement.vaistramanagement.config.security.JwtAuthenticationEntryPoint;
import com.vaistramanagement.vaistramanagement.config.security.RegisterRequest;

import com.vaistramanagement.vaistramanagement.entity.Role;
import com.vaistramanagement.vaistramanagement.entity.User;
import com.vaistramanagement.vaistramanagement.exception.ResourceNotFoundException;
import com.vaistramanagement.vaistramanagement.repositories.UserRepository;
import com.vaistramanagement.vaistramanagement.token.Token;
import com.vaistramanagement.vaistramanagement.token.TokenRepository;
import com.vaistramanagement.vaistramanagement.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Service
@RequiredArgsConstructor

public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtService jwtService;

    private TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;




    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

       var saveduser= repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder().accessToken(jwtToken)
                .token(jwtToken).build();

    }
//
//
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),request.getPassword()));

        var users=User.builder().email(request.getEmail()).password(request.getPassword());


        RuntimeException JwtAuthenticationEntryPoint;
        var user=repository.findByEmail(request.getEmail())
                .orElseThrow(()->new ResourceNotFoundException("Email is invalid"));

        var jwtToken=jwtService.generateToken(users.build());

//        String refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
//        saveUserToken(user,jwtToken);

        return AuthenticationResponse.builder().token(jwtToken)
                .build();


    }



   
    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }



}







