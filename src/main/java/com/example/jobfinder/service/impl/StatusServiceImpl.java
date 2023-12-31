package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.entity.Status;
import com.example.jobfinder.data.repository.StatusRepository;
import com.example.jobfinder.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StatusServiceImpl implements StatusService {


    @Autowired
    private StatusRepository statusRepository;
    @Override
    public Optional<Status> findByName(String name) {
        return statusRepository.findByName(name);
    }
}
