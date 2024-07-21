package com.example.jobfinder.service.impl;

import java.io.IOException;
import java.util.*;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.request.user.UserDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.data.entity.*;
import com.example.jobfinder.data.mapper.CandidateMapper;
import com.example.jobfinder.data.mapper.UserMapper;
import com.example.jobfinder.data.repository.*;
import com.example.jobfinder.exception.AccessDeniedException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.*;
import com.example.jobfinder.utils.common.UpdateFile;
import com.example.jobfinder.utils.enumeration.Estatus;

import lombok.extern.slf4j.Slf4j;

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

    @Autowired
    private MajorService majorService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private CandidateMajorRepository candidateMajorRepository;

    @Autowired
    private CandidateScheduleRepository candidateScheduleRepository;

    @Autowired
    private CandidatePositionRepository candidatePositionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UpdateFile updateFile;

    @Override
    public Object activeCandidate(String token) {

        User user = userRepository.findByTokenActive(token);

        if (user.getStatus().getName().equals(Estatus.Active.toString())) {
            String redirectUrl = "http://localhost:3000/verify-email?status=completed";
            return new RedirectView(redirectUrl);
        }

        String activeToken = user.getTokenActive();

        //        boolean tokenExpired = jwtTokenUtils.isTokenExpired(activeToken);

        //        if (tokenExpired) {
        //            return new RedirectView("http://localhost:3000/verify-email?email=" + user.getEmail() +
        // "&status=fail&message=token-expired");
        //        }

        Status statusActive = this.statusService.findByName(Estatus.Active.toString());

        // set password encode when active
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setStatus(statusActive);
        user.setTokenActive("");

        this.userRepository.save(user);
        return new RedirectView("http://localhost:3000/verify-email?status=success&code=" + token);
    }

    @Override
    @Transactional
    public Object updateProfile(long id, CandidateProfileDTO candidateProfileDTO, MultipartFile fileCV)
            throws IOException {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Candidate oldCandidate = candidateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        Map<String, Object> errors = new HashMap<>();

        if (!oldCandidate.getUser().getEmail().equals(email)) {
            errors.put("email", oldCandidate.getUser().getEmail());
            errors.put("id", id);
            throw new AccessDeniedException(errors);
        }

        UserDTO showUserDTO =
                userService.update(oldCandidate.getUser().getId(), candidateProfileDTO.getUserProfileDTO(), null);

        oldCandidate.setSearchable(
                candidateProfileDTO.getCandidateOtherInfoDTO().isSearchable());
        oldCandidate.setUniversity(
                candidateProfileDTO.getCandidateOtherInfoDTO().getUniversity());
        oldCandidate.setReferenceLetter(
                candidateProfileDTO.getCandidateOtherInfoDTO().getReferenceLetter());
        oldCandidate.setDesiredJob(
                candidateProfileDTO.getCandidateOtherInfoDTO().getDesiredJob());
        oldCandidate.setDesiredWorkingProvince(
                candidateProfileDTO.getCandidateOtherInfoDTO().getDesiredWorkingProvince());

        // check update file CV
        if (fileCV != null) {
            //            fileService.deleteFile(oldCandidate.getCV());
            //            oldCandidate.setCV(fileService.uploadFile(fileCV));
            oldCandidate.setCV(updateFile.uploadCV(fileCV));
        } else {
            oldCandidate.setCV(oldCandidate.getCV());
        }

        candidatePositionService.update(
                oldCandidate.getId(),
                candidateProfileDTO.getCandidateOtherInfoDTO().getPositionDTOs());
        candidateMajorService.update(
                oldCandidate.getId(),
                candidateProfileDTO.getCandidateOtherInfoDTO().getMajorDTOs());
        candidateScheduleService.update(
                oldCandidate.getId(),
                candidateProfileDTO.getCandidateOtherInfoDTO().getScheduleDTOs());

        CandidateDTO updatedCandidateDTO = candidateMapper.toDTO(candidateRepository.save(oldCandidate));
        updatedCandidateDTO.setUserDTO(showUserDTO);
        updatedCandidateDTO
                .getCandidateOtherInfoDTO()
                .setPositionDTOs(candidateProfileDTO.getCandidateOtherInfoDTO().getPositionDTOs());
        updatedCandidateDTO
                .getCandidateOtherInfoDTO()
                .setMajorDTOs(candidateProfileDTO.getCandidateOtherInfoDTO().getMajorDTOs());
        updatedCandidateDTO
                .getCandidateOtherInfoDTO()
                .setScheduleDTOs(candidateProfileDTO.getCandidateOtherInfoDTO().getScheduleDTOs());

        return ApiResponse.builder()
                .httpCode(200)
                .message("Profile updated successfully")
                .data(updatedCandidateDTO)
                .build();
    }

    @Override
    public Object getCandidateByUserId(long id) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Candidate candidate = candidateRepository
                .findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        if (!candidate.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException(Collections.singletonMap("id", id));
        }

        List<CandidateMajor> existingCandidateMajors =
                new ArrayList<>(candidateMajorRepository.findAllByCandidate_Id(candidate.getId()));
        List<CandidatePosition> existingCandidatePositions =
                new ArrayList<>(candidatePositionRepository.findAllByCandidate_Id(candidate.getId()));
        List<CandidateSchedule> existingCandidateSchedules =
                new ArrayList<>(candidateScheduleRepository.findAllByCandidate_Id(candidate.getId()));

        candidate.setCandidatePositions(existingCandidatePositions);
        candidate.setCandidateMajors(existingCandidateMajors);
        candidate.setCandidateSchedules(existingCandidateSchedules);
        return ApiResponse.builder()
                .message("Get candidate successfully")
                .httpCode(200)
                .data(candidateMapper.toDTO(candidate))
                .build();
    }

    @Override
    public boolean isCurrentAuthor(long id) {
        Long candidateUserId =
                candidateRepository.findById(id).map(c -> c.getUser().getId()).orElse(null);
        Long currentUserId = userService.getCurrentUserId();

        return (candidateUserId != null) && (currentUserId != null) && candidateUserId.equals(currentUserId);
    }

    @Override
    public Object updateAvatar(long id, MultipartFile fileAvatar) throws IOException {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Candidate candidate = candidateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        if (!candidate.getUser().getEmail().equals(email)) {
            throw new AccessDeniedException(Collections.singletonMap("id", id));
        }

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("email", email)));

        if (fileAvatar != null) {
            //            fileService.deleteFile(candidate.getUser().getAvatar());
            //            candidate.getUser().setAvatar(fileService.uploadFile(fileAvatar));
            updateFile.uploadImage(fileAvatar);
            user.setAvatar(updateFile.uploadImage(fileAvatar));
        } else {
            user.setAvatar(candidate.getUser().getAvatar());
        }

        userRepository.save(user);

        candidate.setUser(user);

        return ApiResponse.builder()
                .httpCode(200)
                .message("Update avatar successfully")
                .data(user.getAvatar())
                .build();
    }

    @Override
    public ApiResponse updateSearchable(long id) {

        Candidate candidate = candidateRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("id", id)));

        candidate.setSearchable(!candidate.isSearchable());
        candidateRepository.save(candidate);

        return ApiResponse.builder()
                .httpCode(200)
                .data(candidate.isSearchable())
                .message("Update searchable successfully")
                .build();
    }

    @Override
    public CandidateDTO findByUserId(long userId) {

        return candidateMapper.toDTO(candidateRepository
                .findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("userId", userId))));
    }

    @Override
    public Candidate handleCreateCandidate(Candidate candidate) {

        return candidateRepository.save(candidate);
    }
}
