package com.example.jobfinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jobfinder.data.entity.Role;
import com.example.jobfinder.data.repository.RoleRepository;
import com.example.jobfinder.service.RoleService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {


    private final RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public Role findById(Integer id) {
        return roleRepository.findById(Long.valueOf(id)).orElse(null);
    }

}
