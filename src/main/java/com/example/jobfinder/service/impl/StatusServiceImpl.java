package com.example.jobfinder.service.impl;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.exception.ResourceNotFoundException;
import com.example.jobfinder.service.StatusService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusServiceImpl implements StatusService {
    private final StatusRepository statusRepository;

    @Override
    public Status findByName(String name) {
        return this.statusRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(Collections.singletonMap("name", name)));
    }
}
