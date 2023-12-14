package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.position.PositionDTO;


import java.util.List;

public interface CandidatePositionService {
    boolean update(long candidateId, List<PositionDTO> positionDTOs);
}
