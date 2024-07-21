package com.example.jobfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.experimental.NonFinal;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @NonFinal
    private final String[] WHITE_LIST_URLS = {
        "/api/v1/**",
        // swagger
        // "/v3/api-docs",
        // "/v3/api-docs/**",
        "/api-docs",
        "/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/webjars/swagger-ui/**",
        "/swagger-ui/index.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> requests.requestMatchers(WHITE_LIST_URLS)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                // oauth2 jwt default để mở BearerTokenAuthenticationFilter, tự động trích xuất lấy token từ header
                // (khỏi viết tay)
                // encode bằng JwtEncoder viết ở file JwtConfiguration.
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())
                        // custom response lỗi authentication (401), default là empty response)
                        .authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }
}
