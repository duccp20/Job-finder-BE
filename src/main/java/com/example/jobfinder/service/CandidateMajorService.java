package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.major.MajorDTO;


import java.util.List;

public interface CandidateMajorService {
    boolean update(long candidateId, List<MajorDTO> majorDTOs);
}
