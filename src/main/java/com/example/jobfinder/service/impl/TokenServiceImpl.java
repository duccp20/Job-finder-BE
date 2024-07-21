package com.example.jobfinder.service.impl;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.TokenRepository;
import com.example.jobfinder.service.TokenService;
import com.example.jobfinder.utils.enumeration.Estatus;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private StatusRepository statusRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Token generateToken(User user) {
        String uuid = UUID.randomUUID().toString();

        Status activeStatus = statusRepository
                .findByName(Estatus.Active.toString())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Collections.singletonMap("status", Estatus.Active.toString())));

        User managedUser = entityManager.merge(user);
        Token theToken = Token.builder()
                .token(uuid)
                .expirationTime(Token.getTokenExpirationTime())
                .user(managedUser)
                .status(activeStatus)
                .build();
        tokenRepository.save(theToken);
        return theToken;
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
