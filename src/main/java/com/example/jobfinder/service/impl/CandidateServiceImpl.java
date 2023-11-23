package com.example.jobfinder.service.impl;

import com.example.jobfinder.data.entity.User;
import com.example.jobfinder.data.repository.UserRepository;
import com.example.jobfinder.service.CandidateService;
import com.example.jobfinder.service.StatusService;
import com.example.jobfinder.utils.enumeration.Estatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusService statusService;

    @Override
    public void activeCandidate(String token) {

        User user = this.userRepository.findByTokenActive(token);
        user.setStatus(this.statusService.findByName(Estatus.Active.toString()));
        user.setTokenActive("");
        this.userRepository.save(user);

    }
}


