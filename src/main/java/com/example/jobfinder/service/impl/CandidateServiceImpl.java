package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.exception.ValidationException;
import com.example.jobfinder.service.CandidateService;
import com.example.jobfinder.service.StatusService;
import com.example.jobfinder.service.TokenRepository;
import com.example.jobfinder.service.TokenService;
import com.example.jobfinder.utils.enumeration.Estatus;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusService statusService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void activeCandidate(String token) {

        Token theToken = tokenService.findByToken(token).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("token", token));
        });

        Calendar calendar = Calendar.getInstance();
        boolean tokenExpired = theToken.getExpirationTime().getTime() - calendar.getTime().getTime() < 0;

        if (tokenExpired) {
           throw new ValidationException(Collections.singletonMap("token expired", token));
        }

        User user = this.userRepository.findByTokenActive(token);
        user.setStatus(this.statusService.findByName(Estatus.Active.toString()));

        user.setTokenActive("");
        this.tokenRepository.delete(theToken);
        this.userRepository.save(user);
    }
}


