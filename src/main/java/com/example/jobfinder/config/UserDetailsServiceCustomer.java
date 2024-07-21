package com.example.jobfinder.config;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;

@Component("userDetailsService")
public class UserDetailsServiceCustomer implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceCustomer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.example.jobfinder.data.entity.User user = userRepository
                .findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("email", username)));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                //                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();
    }
}
