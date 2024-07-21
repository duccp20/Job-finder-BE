package com.example.jobfinder.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;

public interface CandidateService {

    Object activeCandidate(String token);

    Object updateProfile(long id, CandidateProfileDTO candidateProfileDTO, MultipartFile file) throws IOException;

    Object getCandidateByUserId(long id);

    boolean isCurrentAuthor(long id);

    Object updateAvatar(long id, MultipartFile fileAvatar) throws IOException;

    Object updateSearchable(long id);

    CandidateDTO findByUserId(long id);
}
