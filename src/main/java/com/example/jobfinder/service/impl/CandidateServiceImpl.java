package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
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
import com.example.jobfinder.exception.AccessDeniedException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.*;
import com.example.jobfinder.utils.enumeration.Estatus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

    @Autowired
    private FileService fileService;

    @Autowired
    private CandidatePositionService candidatePositionService;

    @Autowired
    private CandidateMajorService candidateMajorService;

    @Autowired
    private CandidateScheduleService candidateScheduleService;



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

        Status statusActive = this.statusService.findByName(Estatus.Active.toString()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("name", Estatus.Active.name()))
        );
        user.setStatus(statusActive);

        user.setTokenActive("");
        theToken.setStatus(this.statusService.findByName(Estatus.Delete.toString()).orElseThrow(
                () -> new ResourceNotFoundException(Collections.singletonMap("name", Estatus.Delete.name()))
        ));
        this.userRepository.save(user);
        return new RedirectView("http://localhost:3000/verify-email?status=success");
    }

    @Override
    @Transactional
    public Object updateProfile(long id, CandidateProfileDTO candidateProfileDTO, MultipartFile fileCV) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Candidate oldCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        Map <String, Object> errors = new HashMap<>();

        if (!oldCandidate.getUser().getEmail().equals(email)) {
            errors.put("email", oldCandidate.getUser().getEmail());
            errors.put("id", id);
            throw new AccessDeniedException(errors);
        }

        UserDTO showUserDTO = userService.update(oldCandidate.getUser().getId(),
                candidateProfileDTO.getUserProfileDTO(), null);

        oldCandidate.setSearchable(candidateProfileDTO.getCandidateOtherInfoDTO().isSearchable());
        oldCandidate.setUniversity(candidateProfileDTO.getCandidateOtherInfoDTO().getUniversity());
        oldCandidate.setCV(candidateProfileDTO.getCandidateOtherInfoDTO().getCV());
        oldCandidate.setReferenceLetter(candidateProfileDTO.getCandidateOtherInfoDTO().getReferenceLetter());
        oldCandidate.setDesiredJob(candidateProfileDTO.getCandidateOtherInfoDTO().getDesiredJob());
        oldCandidate.setDesiredWorkingProvince(candidateProfileDTO.getCandidateOtherInfoDTO().getDesiredWorkingProvince());

        // check update file CV
        if (fileCV != null) {
            fileService.deleteFile(oldCandidate.getCV());
            oldCandidate.setCV(fileService.uploadFile(fileCV));
        }

        candidatePositionService.update(oldCandidate.getId(),
                candidateProfileDTO.getCandidateOtherInfoDTO().getPositionDTOs());
        candidateMajorService.update(oldCandidate.getId(),
                candidateProfileDTO.getCandidateOtherInfoDTO().getMajorDTOs());
        candidateScheduleService.update(oldCandidate.getId(),
                candidateProfileDTO.getCandidateOtherInfoDTO().getScheduleDTOs());


        return ResponseMessage.builder()
                .httpCode(200)
                .message("Profile updated successfully")
                .data(candidateMapper.toDTO(candidateRepository.save(oldCandidate)))
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

    @Override
    public boolean isCurrentAuthor(long id) {
        Long candidateUserId = candidateRepository.findById(id).map(c -> c.getUser().getId()).orElse(null);
        Long currentUserId = userService.getCurrentUserId();

        return (candidateUserId != null) && (currentUserId != null) && candidateUserId.equals(currentUserId);
    }


}


