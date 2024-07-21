package com.example.jobfinder.config;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import com.example.jobfinder.data.dto.request.user.UserDTO;
import com.example.jobfinder.data.entity.User;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Getter
public class JwtTokenUtils {

    private final JwtConfiguration jwtConfiguration;

    @Value("${application.security.jwt.expiration.access}")
    private long accessTokenExpiration;

    @Value("${application.security.jwt.expiration.refresh}")
    private long refreshTokenExpiration;

    public final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @Value("${application.security.jwt.secret-key}")
    private String jwtKey;

    public JwtTokenUtils(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public String createAccessToken(User user) {
        Instant now = Instant.now();

        Duration duration = Duration.ofSeconds(accessTokenExpiration);
        Instant validity = now.plus(duration);

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .build();

        // @formatter:off
        String[] listPermission = {"USER_CREATE", "USER_UPDATE", "USER_DELETE", "USER_VIEW"};

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(user.getEmail())
                .claim("user", userDTO)
                .claim("permission", listPermission)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JwtConfiguration.JWT_ALGORITHM).build();

        return this.jwtConfiguration
                .jwtEncoder()
                .encode(JwtEncoderParameters.from(jwsHeader, claims))
                .getTokenValue();
    }

    public String createRefreshToken(User user) {
        Instant now = Instant.now();

        Duration duration = Duration.ofSeconds(refreshTokenExpiration);
        Instant validity = now.plus(duration);

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .build();

        // @formatter:off
        String[] listPermission = {"USER_CREATE", "USER_UPDATE", "USER_DELETE", "USER_VIEW"};

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(user.getEmail())
                .claim("user", userDTO)
                .claim("permission", listPermission)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JwtConfiguration.JWT_ALGORITHM).build();

        return this.jwtConfiguration
                .jwtEncoder()
                .encode(JwtEncoderParameters.from(jwsHeader, claims))
                .getTokenValue();
    }

    public Jwt checkValidRefreshToken(String refreshToken) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(this.jwtConfiguration.getSecretKey())
                .macAlgorithm(JwtConfiguration.JWT_ALGORITHM)
                .build();
        try {
            return jwtDecoder.decode(refreshToken);
        } catch (Exception e) {
            System.out.println(">>> JWT refresh error: " + e.getMessage());
            throw e;
        }
    }
}
