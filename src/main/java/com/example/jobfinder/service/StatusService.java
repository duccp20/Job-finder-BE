package com.example.jobfinder.service;

import com.example.jobfinder.data.entity.Status;

import java.util.Optional;

public interface StatusService {

    Optional<Status> findByName(String name);
}
