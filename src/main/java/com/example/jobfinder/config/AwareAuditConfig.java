package com.example.jobfinder.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Configuration
public class AwareAuditConfig implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        try {
            user = (User) authentication.getPrincipal();
        } catch (NullPointerException e) {
            return null;
        }
        return Optional.of(user.getUsername());
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AwareAuditConfig();
    }
}
