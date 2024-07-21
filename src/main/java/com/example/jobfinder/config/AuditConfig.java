// package com.example.jobfinder.config;
//
// import com.example.jobfinder.data.entity.User;
// import com.example.jobfinder.data.repository.UserRepository;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.domain.AuditorAware;
// import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
//
// import java.util.Map;
// import java.util.Optional;
// import java.util.concurrent.ConcurrentHashMap;
//
// @Configuration
// public class AuditConfig {
//
//    private final UserRepository userRepository;
//
//    private static final Map<String, Long> emailToIdCache = new ConcurrentHashMap<>();
//
//    public AuditConfig(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Bean
//    public AuditorAware<Long> auditorAware() {
//        return () -> {
//            Authentication currentAuth = SecurityContextHolder.getContext().getAuthentication();
//            if (currentAuth != null && currentAuth.isAuthenticated() && !(currentAuth instanceof
// AnonymousAuthenticationToken)) {
//                String email = currentAuth.getName();
//                Long userId = emailToIdCache.get(email);
//                if (userId != null) {
//                    return Optional.of(userId);
//                } else {
//                    // Avoid recursive calls by checking if the current operation is already in progress
//                    if (!isAuditingOperationInProgress()) {
//                        try {
//                            setAuditingOperationInProgress(true);
//                            Optional<User> user = userRepository.findByEmail(email);
//                            user.ifPresent(u -> emailToIdCache.put(email, u.getId()));
//                            return user.map(User::getId);
//                        } finally {
//                            setAuditingOperationInProgress(false);
//                        }
//                    }
//                }
//            }
//            return Optional.empty();
//        };
//    }
//
//    private final ThreadLocal<Boolean> auditingOperationInProgress = new ThreadLocal<>();
//
//    private boolean isAuditingOperationInProgress() {
//        Boolean inProgress = auditingOperationInProgress.get();
//        return inProgress != null && inProgress;
//    }
//
//    private void setAuditingOperationInProgress(boolean inProgress) {
//        auditingOperationInProgress.set(inProgress);
//    }
// }
