package com.example.jobfinder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);

    List<Token> findAllByUser(User existingUser);
}
