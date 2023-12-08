package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.exception.ValidationException;
import com.example.jobfinder.service.CandidateService;
import com.example.jobfinder.service.StatusService;
import com.example.jobfinder.service.TokenRepository;
import com.example.jobfinder.service.TokenService;
import com.example.jobfinder.utils.enumeration.Estatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.Collections;


@Service
@Slf4j
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
    public Object activeCandidate(String token) {

        Token theToken = tokenService.findByToken(token).orElseThrow(() -> {
            throw new ResourceNotFoundException(Collections.singletonMap("token", token));
        });

        if (theToken.getStatus().getName().equals(Estatus.Delete.toString())) {
            String redirectUrl = "http://localhost:3000/verify-email?status=completed";
            return new RedirectView(redirectUrl);
        }


        Instant expirationTime = theToken.getExpirationTime().toInstant();
        Instant now = Instant.now();

        boolean tokenExpired = expirationTime.isBefore(now);

        if (tokenExpired) {
            return new RedirectView("http://localhost:3000/verify-email?status=fail");
        }

        User user = this.userRepository.findByTokenActive(token);

        Status statusActive = this.statusService.findByName(Estatus.Active.toString());
        user.setStatus(statusActive);

        user.setTokenActive("");
        theToken.setStatus(this.statusService.findByName(Estatus.Delete.toString()));
        this.userRepository.save(user);
        return new RedirectView("http://localhost:3000/verify-email?status=success");
    }
}


