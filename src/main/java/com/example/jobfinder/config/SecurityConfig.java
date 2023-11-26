package com.example.jobfinder.config;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity // đừng nhầm vs @EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> userRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    throw new ResourceNotFoundException(Collections.singletonMap("email", email));
                });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}


//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtAuthEntryPoint authenticationEntryPoint;
//
//    @Autowired
//    private JwtAuthFilter authenticationFilter;
//
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
////    @Bean
////    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        //Tất cả request phải authen nếu muốn vào web
////        http
////                .csrf()
////                .disable()
////                .authorizeHttpRequests
////                        (
////                                authorize -> authorize.anyRequest().authenticated()
////                        )
////                .httpBasic(Customizer.withDefaults());
////
////
////        return http.build();
////    }
//
//    //tạo ra Authentication Manager để authenticate
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//        //Kể từ version 5.2 spring security sẽ automatically authenticate = database authentication
//        // khi ta inject UserDetailService và PasswordEncode vào config.
//    }
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeHttpRequests((authorize) ->
//                        //authorize.anyRequest().authenticated()
//                        authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
//                                .requestMatchers("/api/v1/auth/**").permitAll()
//                                .requestMatchers("/swagger-ui/**").permitAll()
//                                .requestMatchers("/v3/api-docs/**").permitAll()
//                                .requestMatchers("/register/**").permitAll()
//                                .requestMatchers(HttpMethod.POST,"/api/v1/auth/password-reset/**").permitAll()
//                                .anyRequest().authenticated()
//                )
//                .exceptionHandling( exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
//                .sessionManagement( session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
//        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }


    //Tạo 2 user mẫu để test = in memory user
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails duc = User.builder()
//                .username("duc")
//                .password(passwordEncoder().encode("duc"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(duc, admin);
//    }

