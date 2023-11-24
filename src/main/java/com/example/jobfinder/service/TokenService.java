package com.example.jobfinder.service;

import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;

import java.util.Optional;

public interface TokenService {

    Token generateToken(User user);

    Optional<Token> findByToken(String token);


}
