package com.example.jobfinder.security.jwt;


import com.example.jobfinder.exception.ExceptionCustom;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtils {

    @Value("${application.security.jwt.expiration}")
    private long expirationTime;

    @Value("${application.security.jwt.refresh.expiration}")
    private long refreshExpirationTime;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String generateToken(com.example.jobfinder.data.entity.User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                    .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (Exception e) {
           log.info("Can't create token" + e.getMessage());
           throw new InternalServerErrorException(e.getMessage());
        }
    }

    private SecretKey getSignInKey() {
        byte[] bytes;
        bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public String generateRefreshToken(com.example.jobfinder.data.entity.User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());

        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime * 1000))
                    .signWith(this.getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        } catch (Exception e) {
            log.info("Can't create refresh token" + e.getMessage());
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(this.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //check expiration
    public boolean isTokenExpired(String token) {
        Date dateExpiration = this.extractClaim(token, Claims::getExpiration);
        return dateExpiration.before(new Date());
    }

}
