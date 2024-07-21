package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.entity.CandidatePosition;

public interface CandidatePositionService {
    boolean update(long candidateId, List<PositionDTO> positionDTOs);

    List<CandidatePosition> findAllByCandidate_Id(long candidateId);
}
