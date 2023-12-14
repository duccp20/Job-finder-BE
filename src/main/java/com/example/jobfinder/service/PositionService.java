package com.example.jobfinder.service;

import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.data.entity.Position;


import java.util.List;
import java.util.Map;

public interface PositionService {
    PositionDTO findById(Integer id);

    List<PositionDTO> findAll();

    ResponseMessage create(PositionDTO positionDTO);

    ResponseMessage deleteById(Integer id);

//    Map<Position, int[]> statisticsPositionTheNumberOfPostsAndJoins();
}
