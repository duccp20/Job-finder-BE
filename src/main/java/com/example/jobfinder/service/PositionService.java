package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;

public interface PositionService {
    PositionDTO findById(Integer id);

    List<PositionDTO> findAll();

    ResponseMessage create(PositionDTO positionDTO);

    ResponseMessage deleteById(Integer id);

    //    Map<Position, int[]> statisticsPositionTheNumberOfPostsAndJoins();
}
