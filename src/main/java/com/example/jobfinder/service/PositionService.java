package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;

public interface PositionService {
    PositionDTO findById(Integer id);

    List<PositionDTO> findAll();

    ApiResponse create(PositionDTO positionDTO);

    ApiResponse deleteById(Integer id);

    //    Map<Position, int[]> statisticsPositionTheNumberOfPostsAndJoins();
}
