package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.position.PositionDTO;

public interface JobPositionService {

    void deleteById(Integer id);

    long count();

    boolean existsById(Integer id);

    PositionDTO findById(Integer id);

    List<PositionDTO> findAll();

    PositionDTO create(PositionDTO jobPositionDTO);

    boolean existsByName(String name);
}
