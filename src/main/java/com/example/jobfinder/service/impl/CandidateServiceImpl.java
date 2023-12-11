package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import com.example.jobfinder.data.entity.Candidate;
import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.entity.Token;
import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.mapper.CandidateMapper;
import com.example.jobfinder.data.mapper.UserMapper;
import com.example.jobfinder.data.repository.CandidateRepository;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.*;
import com.example.jobfinder.utils.enumeration.Estatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;


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


    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CandidateMapper candidateMapper;

    @Autowired
    private UserService userService;

    private HttpServletRequest httpServletRequest;

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

    @Override
    @Transactional
    public Object updateProfile(CandidateProfileDTO candidateProfileDTO) {

        ShowUserDTO showUserDTO = userService.update(candidateProfileDTO.getUserProfileDTO().getUserId(), candidateProfileDTO.getUserProfileDTO(), null);

        Candidate oldCandidate = candidateRepository.findByUserId(candidateProfileDTO.getUserProfileDTO().getUserId()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("id", candidateProfileDTO.getUserProfileDTO().getUserId()))
        );

        Candidate updatedCandidate = candidateMapper.toEntity(candidateProfileDTO.getCandidateDTO());
        updatedCandidate.setUser(oldCandidate.getUser());
        updatedCandidate.setId(oldCandidate.getId());

        CandidateDTO candidateDTO = candidateMapper.toDTO(candidateRepository.save(updatedCandidate)) ;
        return ResponseMessage.builder()
                .httpCode(200)
                .message("Profile updated successfully")
                .data(candidateMapper.toShowCandidateDTO(showUserDTO, candidateDTO))
                .build();

    }

    @Override
    public Object getCandidateByUserId(long id) {

        Candidate candidate = candidateRepository.findByUserId(id).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("id", id))
        );

        return ResponseMessage.builder()
                .message("Get candidate successfully")
                .httpCode(200)
                .data(candidateMapper.toDTO(candidate))
                .build();
    }


}


