package com.vaistramanagement.vaistramanagement.service;

import com.vaistramanagement.vaistramanagement.entity.User;
import com.vaistramanagement.vaistramanagement.token.Token;
import com.vaistramanagement.vaistramanagement.token.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.vaistramanagement.vaistramanagement.token.TokenRepository;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService
{


    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private static final String SECRET_KEY="afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    private TokenRepository tokenRepository;
    public String extractUsername(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }

    public String generateToken(User user)
    {
        return generateToken(new HashMap<>(),user);
    }

    public String generateToken(

            Map<String,Object> extraClaims,
            User user)
    {
       return Jwts
               .builder()
               .setClaims(extraClaims)
               .setSubject(user.getEmail())
               .setIssuedAt(new Date(System.currentTimeMillis()))
//               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
               .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
               .signWith(getSignInKey(), SignatureAlgorithm.HS256)
               .compact();
    }


    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token)
    {
        return extractClaim(token,Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey()
    {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    private void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);

    }


    public String generateRefreshToken(User user) {
      return generateToken(user);
    }
}
