package com.example.jobfinder.service;

import java.util.List;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;

public interface MajorService {

    MajorDTO findById(Integer id);

    List<MajorDTO> findAll();

    ApiResponse create(MajorDTO majorDTO);

    ApiResponse deleteById(Integer id);
}
