package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.entity.Candidate;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface CandidateService {

    Object activeCandidate(String token);

    @Transactional
    Object updateProfile(CandidateProfileDTO candidateProfileDTO);

    Object getCandidateByUserId(long id);
}
