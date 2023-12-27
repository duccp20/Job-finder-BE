package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.entity.CandidatePosition;


import java.util.List;

public interface CandidatePositionService {
    boolean update(long candidateId, List<PositionDTO> positionDTOs);

    List<CandidatePosition> findAllByCandidate_Id(long candidateId);
}
