//package com.example.jobfinder.security;
//
//import com.example.jobfinder.data.entity.Role;
//import com.example.jobfinder.data.entity.User;
//import com.example.jobfinder.data.repository.UserRepository;
//import com.example.jobfinder.exception.ResourceNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        User user = userRepository.findByEmail(email).orElseThrow(() -> {
//            throw new ResourceNotFoundException(Collections.singletonMap("email", email));
//        });
//
//
//        List<GrantedAuthority> authorities = new SimpleGrantedAuthority(user.getRole().getName());
//
//        return UserPrincipal
//                .builder()
//                .userName(user.getUsername())
//                .password(user.getPassword())
//                .isEnabled(user.isEnable())
//                .authorities(authorities)
//                .build();
//    }
//}
