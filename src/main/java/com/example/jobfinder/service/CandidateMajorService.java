package com.example.jobfinder.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.entity.CandidateMajor;

@Service
public interface CandidateMajorService {
    boolean update(long candidateId, List<MajorDTO> majorDTOs);

    List<CandidateMajor> findAllByCandidate_Id(long candidateId);
}
