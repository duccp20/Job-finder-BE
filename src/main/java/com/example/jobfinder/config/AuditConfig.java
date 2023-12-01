package com.example.jobfinder.config;


import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditConfig {
    @Autowired
    private UserRepository userRepository;

    // Maintain a cache for email-to-ID mapping (to avoid loop between AuditorAware and repository)
    private static Map<String, Long> emailToIdCache = new ConcurrentHashMap<>();


    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> {
            try {
                // Retrieve the current Authentication from the SecurityContextHolder
                Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();

                // Check if the Authentication is not null, authenticated, and not an instance of AnonymousAuthenticationToken
                if (currentAuth != null
                        && currentAuth.isAuthenticated()
                        && !(currentAuth instanceof AnonymousAuthenticationToken)) {
                    String email = currentAuth.getName();

                    // Retrieve the user id from the emailToIdCache
                    Long userId = emailToIdCache.get(email);

                    // If the user id is found in the cache, return it
                    if (userId != null) {
                        return Optional.ofNullable(userId);
                    } else {
                        // Retrieve the user from the userRepository by email
                        return userRepository.findByEmail(email)
                                .map(User::getId)
                                .map(id -> {
                                    // Cache the user id in the emailToIdCache
                                    emailToIdCache.put(email, id);
                                    return id;
                                });
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return Optional.empty();
        };
    }

}