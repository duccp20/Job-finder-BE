package com.example.jobfinder.service;

import java.util.Optional;

import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;

public interface TokenService {

    Token generateToken(User user);

    Optional<Token> findByToken(String token);
}
