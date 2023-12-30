package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.candidate.CandidateDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.entity.Candidate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface CandidateService {

    Object activeCandidate(String token);

    Object updateProfile(long id, CandidateProfileDTO candidateProfileDTO , MultipartFile file) throws IOException;

    Object getCandidateByUserId(long id);

    boolean isCurrentAuthor(long id);

    Object updateAvatar(long id, MultipartFile fileAvatar) throws IOException;

    Object updateSearchable(long id);


    CandidateDTO findByUserId(long id);
}
