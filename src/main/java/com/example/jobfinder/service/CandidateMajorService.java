package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface CandidateMajorService {
    boolean update(long candidateId, List<MajorDTO> majorDTOs);
}
