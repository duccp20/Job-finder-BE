package com.example.jobfinder.service;

import com.example.jobfinder.data.entity.Status;

public interface StatusService {

    Status findByName(String name);
}
