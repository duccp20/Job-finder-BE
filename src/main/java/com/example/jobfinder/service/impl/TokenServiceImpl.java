package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.service.TokenRepository;
import com.example.jobfinder.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;
    @Override
    public Token generateToken(User user) {
        Token theToken = new Token();
        theToken.setToken(UUID.randomUUID().toString());
        theToken.setExpirationTime(new Token().getExpirationTime());
        theToken.setUser(user);
        tokenRepository.save(theToken);
        return theToken;
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
