package com.example.jobfinder.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.data.entity.Major;
import com.example.jobfinder.data.mapper.MajorMapper;
import com.example.jobfinder.data.repository.MajorRepository;
import com.example.jobfinder.exception.InternalServerErrorException;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService {
    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public List<MajorDTO> findAll() {
        return this.majorRepository.findAll().stream()
                .map(item -> this.majorMapper.toDTO(item))
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponse create(MajorDTO majorDTO) {
        Major major = majorMapper.toEntity(majorDTO);
        if (majorRepository.existsByName(major.getName()))
            throw new InternalServerErrorException(String.format("Exists major named %s", major.getName()));
        ;

        return ApiResponse.builder()
                .httpCode(HttpServletResponse.SC_CREATED)
                .message("Create major successfully")
                .data(majorMapper.toDTO(majorRepository.save(major)))
                .build();
    }

    @Override
    public ApiResponse deleteById(Integer id) {
        this.majorRepository.delete(this.majorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("major_id", id))));

        return ApiResponse.builder()
                .httpCode(HttpServletResponse.SC_CREATED)
                .message("Delete major successfully")
                .data(null)
                .build();
    }

    @Override
    public MajorDTO findById(Integer id) {
        return this.majorMapper.toDTO(this.majorRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("major_id", id))));
    }
}
